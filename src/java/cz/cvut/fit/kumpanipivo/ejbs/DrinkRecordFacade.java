/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.ejbs;

import cz.cvut.fit.kumpanipivo.entity.DrinkRecord;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Adam
 */
@Stateless
public class DrinkRecordFacade extends AbstractFacade<DrinkRecord> {
    @PersistenceContext(unitName = "KumpaniEEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DrinkRecordFacade() {
        super(DrinkRecord.class);
    }
    
}
