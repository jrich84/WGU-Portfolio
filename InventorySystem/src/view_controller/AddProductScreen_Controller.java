/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import static model.Inventory.*;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author defaultuser0
 */
public class AddProductScreen_Controller {
    
    // holds object from getselection method
    private Object selectedPart = null;
    private ObservableList associatedFacade;
    private ArrayList parts = null;
    private Alert alert;
    
    /*
    * View Variables
    */
            
    @FXML
    protected TextField productId, productName,
            productInventory, productPrice,
            productMax, productMin, partTxt;

    @FXML
    protected Button search, add,
            delete, escape, save;
    
    // Parts
    @FXML
    private TableView<Part> partsTable;
        
    @FXML
    private TableColumn<Part, Integer> partId,partInventory;
    
    @FXML
    private TableColumn<Part, String> partName;
    
    @FXML
    private TableColumn<Part, Double> partUnitCost;
    
    // Initialize controller
    public AddProductScreen_Controller() {
    }
    
    /*
    *   View Handlers
    */
    
    // Associated parts
    @FXML
    private TableView<Part> associatedTable;
    
    @FXML
    private TableColumn<Part, Integer>  associatedId, associatedInventory;
    
    @FXML
    private TableColumn<Part, String> associatedName;
    
    @FXML
    private TableColumn<Part, Double> associatedUnitCost;
    
    // Global Object var is passed the selected Object item
    @FXML
    public void getSelection(MouseEvent event) {
        if(selectedPart == null) {
            selectedPart = partsTable.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    public void addAssociatedPart(ActionEvent event) {
        if(selectedPart == null) {
        } else {   
        associatedTable.setDisable(false);
        associatedFacade.add(selectedPart);
        }
        selectedPart = null;
    }

    @FXML
    public void deleteAssociatedPart(ActionEvent event) {
        if(associatedTable.isDisable()) {
        } else {   
        associatedFacade.remove(associatedTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void save(ActionEvent event) {

        //associatedTable.getItems().forEach(partObject -> System.out.println(partObject.getPrice()));
        System.out.println(associatedPartsTotal());

        if (associatedFacade.isEmpty() || productName.getText().isEmpty() || productPrice.getText().isEmpty()) {
            // let user know that the Fields cannot be empty
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Empty Fields");
            alert.setContentText("Inventory, Name, and Price cannot be blank");
            alert.showAndWait();
            return;
        } else if(Double.parseDouble(productPrice.getText().trim()) >= associatedPartsTotal()) {

            // Calls abstract helper method "seperating logic"
                observableListToArray(associatedFacade);
            // parse view
            int inv, max, min, id;
            id  = getProducts().size();
            inv = strToInt(this.productInventory.getText().trim());
            max = strToInt(this.productMax.getText().trim());
            min = strToInt(this.productMin.getText().trim());
            String name = this.productName.getText().trim();
            Double price = strToDbl(this.productPrice.getText().trim());
            
            addProduct(new Product(id,name,price,inv,min,max,parts));
        // Set Product Data;    
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("A products price cannot be less than the sum of its associated parts!");
            alert.showAndWait();
            return;
        }
        
        // Close stage
        Stage partStage = (Stage) save.getScene().getWindow();
        
        // close window
        partStage.close();
    }

    @FXML
    public void search(ActionEvent event) {

    }
    
    @FXML
    public void escape(ActionEvent event) {
        // used the Button cancel to get the scene I am in 
        Stage partStage = (Stage) escape.getScene().getWindow();
        // close window
        partStage.close();
    }
    
    public void initialize() {
        // Facade
        associatedFacade = FXCollections.observableArrayList();
        
        //Price data
        this.partId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        this.partName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.partInventory.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        this.partUnitCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        this.productId.setText(Integer.toString(getProducts().size()));
        
        // assoc Data facade
        associatedId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        associatedInventory.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        associatedName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        associatedUnitCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        
        //Bind to view
        partsTable.setItems(getParts());
        associatedTable.setItems(associatedFacade);
    }
    
    /*
    * Helper methods "abstaction for the wind"
    */
    
    // Pipes ObservableList Objects to parts ArrayList
    private void observableListToArray(ObservableList item) {
        parts = new ArrayList();
        item.forEach(partObject -> parts.add(partObject)); 
    }
    
    // Returns sum as double integer
    private Double associatedPartsTotal() {
        Double sum = 0.00;
        for (Part price : associatedTable.getItems()) {
        sum += price.getPrice();
        }
        return sum;
    }

    // misc type helpers
    private int strToInt(String data) {
        int i;
        i = Integer.parseInt(data);
        return i;
    }
    
    private double strToDbl(String data) {
        double d = Double.parseDouble(data);
        return d;
    }
}
