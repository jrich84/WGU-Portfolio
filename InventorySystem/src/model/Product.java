/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * @ Joseph Richardson
 */
public class Product {

    private ArrayList<Part> associatedParts;
    
    private final IntegerProperty productID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    /**
     * Default constructor
     * Constructor for our Observable list to indirectly reference our private variables
     * @param productID
     * @param name
     * @param price
     * @param inStock
     * @param min
     * @param max
     * @param associatedParts
     */
    
    public Product(int productID, String name, Double price, int inStock, int min, int max, List associatedParts) {
        
	this.productID = new SimpleIntegerProperty(productID);
        this.name = new SimpleStringProperty(name);
        
        //Sample
        this.price = new SimpleDoubleProperty(price);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
        this.associatedParts = new ArrayList(associatedParts);
    }

    // getters
    public int getProductID() {
        return productID.get();
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
    
    public List<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    // Setters
    public void setProductID(int productID) {
        this.productID.set(productID);
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
    
    public void setAssociatedParts(ArrayList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    //Binding for javafx/java collections
    public IntegerProperty productIDProperty() {
        return productID;
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
    
    // Gooey methods to figure out
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int part) {
        if(associatedParts.contains(part)) {
            associatedParts.remove(part);
            return true;
        } else {
            return false;
        }
    }
    
    // Ask Malcom
    public boolean loopupAssociatedPart(int part) {
        if(associatedParts.contains(part)) {
            associatedParts.remove(part);
            return true;
        } else {
            return false;
        }
    }
}
