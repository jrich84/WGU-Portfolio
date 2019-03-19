/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Author
 * @Joseph Richardson
 */
public class Inventory {
    /**
     * The data as an observable list of Products, Parts.
     */
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();


    public static void Inventory() {

        products.add(new Product(0,"Dewalt Hammer",1.50,1,1,1,Arrays.asList()));
        products.add(new Product(1,"Dewalt Drill",90.00,1,1,1,Arrays.asList()));

        
        //Add Inhouse Part data
        allParts.add(new Inhouse(0,"AxeHandle",1.50,1,1,1,01));
        allParts.add(new Inhouse(1,"12v Battery",65.00,1,1,1,03));
        
        //Add Outsourced Part data
        allParts.add(new Outsourced(2,"Handle",1.50,1,1,1,"Hobart"));
        allParts.add(new Outsourced(3,"20v Battery",65.00,1,1,1,"Dewalt"));
    }
    
    // Product methods
    public static void updateProduct(int product){
        //  worthless method just use setters
        products.set(product, products.get(product));
    }
    
    public static void addProduct(Product product){
      products.add(product);
    }
    
    public static boolean removeProduct(int product){
        products.remove(product);
        return false;
    }
    
    public static Product lookupProduct(int product){
        return products.get(product);
    }
    
    // Part Methods
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    public static boolean deletePart(int part){
        allParts.remove(part);
        return false;
    }
  
    public static Part lookupPart(int part){
        return allParts.get(part);
    }
    
    public static void updatePart(int part){
        //  worthless method just use setters
        allParts.set(part, allParts.get(part));
    }

    // Observable methods
    public static ObservableList<Product> getProducts() {
        return products;
    }
    
    public static ObservableList<Part> getParts() {
        return allParts;
    }
}
