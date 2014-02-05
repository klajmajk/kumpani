/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fit.kumpanipivo.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Adam
 */
@Entity
@Table(name="BarrelKind",  uniqueConstraints={
   @UniqueConstraint(columnNames={"volume", "name"})
})
public class BarrelKind implements Serializable {

    @Id
    @GeneratedValue
    int id;
    @NotNull
    @Min(1)
    int volume;
    @NotNull
    @Size(min=1)
    String name;

    public BarrelKind() {
    }

    public int getId() {
        return id;
    }
    

    public BarrelKind(int volume, String name) {
        this.volume = volume;
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
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
        final BarrelKind other = (BarrelKind) obj;
        if (this.volume != other.volume) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " - " + volume;
    }
    
    public String getToString(){
        return toString();
    }

}
