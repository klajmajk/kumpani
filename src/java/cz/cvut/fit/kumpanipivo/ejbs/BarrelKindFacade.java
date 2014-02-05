/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.ejbs;

import cz.cvut.fit.kumpanipivo.entity.BarrelKind;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Adam
 */
@Stateless
public class BarrelKindFacade extends AbstractFacade<BarrelKind> {
    @PersistenceContext(unitName = "KumpaniEEPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BarrelKindFacade() {
        super(BarrelKind.class);
    }
    
}
