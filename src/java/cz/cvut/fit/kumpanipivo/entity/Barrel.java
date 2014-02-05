/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Adam
 */
@Entity
public class Barrel implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull
    private Date bought;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date taped;          
    @NotNull
    private double price;
    @ManyToOne            
    @NotNull
    private BarrelKind kind;
                
    @NotNull
    @Enumerated(EnumType.STRING)
    private BarrelState barrelState;
    
    public Barrel() {
        this.barrelState = BarrelState.STOCK;
    }

   
    
    

    public Barrel(Date bought, double price, BarrelKind kind) {
        this.bought = bought;
        this.price = price;
        this.kind = kind;
    }

    public int getId() {
        return id;
    }

    public Date getBought() {
        return bought;
    }

    public void setBought(Date bought) {
        this.bought = bought;
    }

    public Date getTaped() {
        return taped;
    }

    public void setTaped(Date taped) {
        this.taped = taped;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BarrelKind getKind() {
        return kind;
    }

    public void setKind(BarrelKind kind) {
        this.kind = kind;
    }

    public BarrelState getBarrelState() {
        return barrelState;
    }

    public void setBarrelState(BarrelState state) {
        this.barrelState = state;
    }
    
    
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.bought);
        hash = 89 * hash + Objects.hashCode(this.taped);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.kind);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Barrel other = (Barrel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.bought, other.bought)) {
            return false;
        }
        if (!Objects.equals(this.taped, other.taped)) {
            return false;
        }
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.kind, other.kind)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  bought + " - " + kind + ", " + price + " Kč";
    }
    
    public String getToString() {
        return toString();
    }
    
    
    
    
    
    
    
    
    
    
    
}
