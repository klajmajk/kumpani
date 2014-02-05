/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.jsf;

import cz.cvut.fit.kumpanipivo.ejbs.ActiveBarrelBean;
import cz.cvut.fit.kumpanipivo.entity.Barrel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Adam
 */
@Named(value = "activeBarrelController")
@SessionScoped
public class ActiveBarrelController implements Serializable{
    @EJB
    private ActiveBarrelBean ejb;
    private Barrel barrel;

    /**
     * Creates a new instance of ActiveBarrelController
     */
    public ActiveBarrelController() {
    }
    
    public String getActiveBarrel(){
        if(ejb.getActiveBarrel()!=null) return ejb.getActiveBarrel().toString();
        else return "Nic";
    }
    
    public void finishBarrel(){
        ejb.setActiveFinished();
    }
    
    public List<Barrel> getBarrelsStock(){
        return ejb.getBarrelsStock();
    }
    
    public String setNewTapedBarrel(){
        //System.out.println("Barrel: "+ barrel);
        ejb.setNewActiveBarrel(barrel);
        return "index";
    }

    public Barrel getBarrel() {
        return barrel;
    }

    public void setBarrel(Barrel barrel) {
        //System.out.println(barrel);
        this.barrel = barrel;
    }
    
    
    
}
