/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Adam
 */
@Entity
public class DrinkRecord implements Serializable {
    @Id
    @GeneratedValue
    private int id;                
    @NotNull
    private double volume;
    @ManyToOne            
    @NotNull
    private Consumer consumer;
    @ManyToOne            
    @NotNull
    private Barrel barrel;
    private double price;

    public DrinkRecord() {
    }

    public int getId() {
        return id;
    }

    public double getVolume() {
        return volume;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Barrel getBarrel() {
        return barrel;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public void setBarrel(Barrel barrel) {
        this.barrel = barrel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

    @Override
    public String toString() {
        return "DrinkRecord{" + "id=" + id + ", volume=" + volume + ", consumer=" + consumer + ", barrel=" + barrel + '}';
    }
    
    
    
    
    
    
    
    
    
}
