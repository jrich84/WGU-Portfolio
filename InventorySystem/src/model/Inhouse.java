/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Joseph Richardson
 */
public class Inhouse extends Part {
    private final IntegerProperty machineID;

    public Inhouse(int partID,
            String name,
            double price,
            int inStock,
            int min,
            int max,
            int machineID) {
        
        super(partID, name, price, inStock, min, max);
        
        this.machineID = new SimpleIntegerProperty(machineID);
    }

    
    // Getter
    public int getMachineID() {
        return machineID.get();
    }
    
    // Setter
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
    //Binding for javafx/java collections
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
    
}
