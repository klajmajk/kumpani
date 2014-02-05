/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.kumpanipivo.ejbs;

import cz.cvut.fit.kumpanipivo.entity.Barrel;
import cz.cvut.fit.kumpanipivo.entity.BarrelState;
import cz.cvut.fit.kumpanipivo.entity.Consumer;
import cz.cvut.fit.kumpanipivo.entity.DrinkRecord;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Adam
 */
@Singleton
public class ActiveBarrelBean {

    private Barrel activeBarrel = null;

    @PersistenceContext(unitName = "KumpaniEEPU")

    private EntityManager em;

    public Barrel getActiveBarrel() {
        if (activeBarrel == null) {
            // WHERE b.BarrelState = 'TAPED'
            List<Barrel> list = em.createQuery("SELECT b FROM Barrel b WHERE b.barrelState = cz.cvut.fit.kumpanipivo.entity.BarrelState.TAPED").getResultList();
            if (list.size() == 1) {
                activeBarrel = list.get(0);
            }
        }
        return activeBarrel;
    }

    public List<Barrel> getBarrelsStock() {
        return em.createQuery("SELECT b FROM Barrel b WHERE b.barrelState = cz.cvut.fit.kumpanipivo.entity.BarrelState.STOCK").getResultList();
    }

    public void setActiveFinished() {
        if (activeBarrel != null) {
            activeBarrel.setBarrelState(BarrelState.FINISHED);
            em.persist(em.merge(activeBarrel));
            em.flush();
            countBarrel();
            activeBarrel = null;
        }
    }

    public void setNewActiveBarrel(Barrel b) {
        if ((activeBarrel == null) && (b.getBarrelState() == BarrelState.STOCK)) {
            activeBarrel = b;
            activeBarrel.setBarrelState(BarrelState.TAPED);
            activeBarrel.setTaped(new Date(System.currentTimeMillis()));
            em.merge(activeBarrel);
        } else if ((b.getBarrelState() != BarrelState.STOCK)) {
            System.err.println("Tento sud nemůže být naražen.");
        } else if (activeBarrel != null) {
            System.err.println("Stále je naražen jiný sud");
        }
    }

    private void countBarrel() {
        Query query = em.createQuery("SELECT d FROM DrinkRecord d WHERE d.barrel = :barrel");
        query.setParameter("barrel", activeBarrel);
        List<DrinkRecord> records = query.getResultList();
        double volumeSum = 0;
        for (DrinkRecord drinkRecord : records) {
            volumeSum += drinkRecord.getVolume();
        }
        if (volumeSum != 0) {
            double pricePerLiter = activeBarrel.getPrice() / volumeSum;

            for (DrinkRecord drinkRecord : records) {
                Consumer consumer = drinkRecord.getConsumer();
                double price = drinkRecord.getVolume() * pricePerLiter;
                consumer.creditChange(-1 * price);
                em.persist(consumer);
                drinkRecord.setPrice(price);
                em.persist(drinkRecord);
            }
        }
    }

}
