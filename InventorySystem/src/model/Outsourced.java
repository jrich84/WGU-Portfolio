/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Joseph Richardson
 */
public class Outsourced extends Part {
    private final StringProperty companyName;

    public Outsourced(int partID,
            String name,
            double price,
            int inStock,
            int min,
            int max,
            String companyName) {
        
        super(partID, name, price, inStock, min, max);
        
        this.companyName = new SimpleStringProperty(companyName);
    }

    
    // Getter
    public String getCompanyName() {
        return companyName.get();
    }
    
    // Setter
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    //Binding for javafx/java collections
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
}
