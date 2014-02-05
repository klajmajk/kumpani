/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.kumpanipivo.ejbs;

import cz.cvut.fit.kumpanipivo.entity.Consumer;
import cz.cvut.fit.kumpanipivo.entity.DrinkRecord;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Adam
 */
@Stateless
public class ConsumerFacade extends AbstractFacade<Consumer> {

    @PersistenceContext(unitName = "KumpaniEEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsumerFacade() {
        super(Consumer.class);
    }

    public List<DrinkRecord> findDrinkRecords(Consumer consumer) {
        Query query = em.createQuery("SELECT d FROM DrinkRecord d WHERE d.consumer = :consumer");
        query.setParameter("consumer", consumer);
        System.out.println("Consumer: "+ consumer);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

}
