/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author defaultuser0
 */
public abstract class Part {
    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    /**
     * Default constructor
     */
    public Part() {
        partID = null;
        name = null;
        price = null;
        inStock = null;
        min = null;
        max = null;
    }
    
    // Constructor to initialize object
    public Part(int partID, String name, double price, int inStock, int min, int max) {
        
	this.partID  = new SimpleIntegerProperty(partID);
	this.name    =  new SimpleStringProperty(name);
        this.price   = new SimpleDoubleProperty(price);
	this.inStock = new SimpleIntegerProperty(inStock);
	this.min     = new SimpleIntegerProperty(min);
	this.max     = new SimpleIntegerProperty(max);
    }

    // Getters
    public int getPartID() {
        return partID.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public int getInStock() {
        return inStock.get();
    }

    public int getMin() {
        return min.get();
    }

    public int getMax() {
        return max.get();
    }
    
    // Setters
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }
    
    public void setMin(int min) {
        this.min.set(min);
    }
    
    public void setMax(int max) {
        this.max.set(max);
    }
    
    //Binding for javafx/java collections
    public IntegerProperty partIDProperty() {
        return partID;
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public DoubleProperty priceProperty() {
        return price;
    }
    
    public IntegerProperty inStockProperty() {
        return inStock;
    }
    
   public IntegerProperty minProperty() {
        return min;
    }
        
    public IntegerProperty maxProperty() {
        return max;
    }
    
    
    // To be overridden methods..binding will happen in child classes
    /*
    / Inhouse Getters & Setters
    */
    public int getMachineID() {
        int machineID = 0;
        return machineID;
    }
    
    public void setMachineID(int machineID) {
        this.getMachineID();
    }
    
    /*
    / Outsourced Getters & Setters
    */
    public String getCompanyName() {
        String companyName = "";
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.getCompanyName();
    }
}
