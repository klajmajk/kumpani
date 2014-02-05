/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fit.kumpanipivo.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Adam
 */
@Entity
public class Consumer implements Serializable {

    
    @Id
    @GeneratedValue
    private int id;                
    @NotNull
    private String name;                
    @NotNull
    private double credit;

    public Consumer() {
    }

    public int getId() {
        return id;
    }
    
    

    public Consumer(String name) {
        this.name = name;
        this.credit = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
    
    

    public String getName() {
        return name;
    }
    
    public void creditChange (double change){
        credit += change;
    }
    
    
    public double getCredit() {
        return credit;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
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
        final Consumer other = (Consumer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public String getToString(){
        return name;
    }
    
    

    
    
    
    
    
    
}
