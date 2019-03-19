/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import inventorysystem.MainApp;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.*;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Joseph Richardson
 */
public class ModifyProductScreen_Controller {
    
    private Alert alert;
    // holds object from getselection method
    private Object selectedPart;
    private ObservableList associatedFacade;
    private ArrayList parts;
    
    // holds the index recieved from MainScreen_Controller
    protected Integer selectedProduct = null;
    
    // Filtered for our Observable part list
    private final FilteredList<Part> filteredPartData = new FilteredList<>(allParts, p -> true);
    
    // Move filtered data to a sorted list
    private final SortedList<Part> sortedPartData = new SortedList<>(filteredPartData);

    /*
    * View Varables
    */
    
    @FXML
    protected TextField productId,
            productName, productInventory,
            productPrice, productMax,
            productMin, partSearchTxt;

    @FXML
    protected Button search, add,
            delete, cancel, save;
    
    // Parts
    @FXML
    private TableView<Part> partsTable;
        
    @FXML
    private TableColumn<Part, Integer> partId, partInventory;
    
    @FXML
    private TableColumn<Part, String> partName;
    
    @FXML
    private TableColumn<Part, Double> partUnitCost;
    
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
    
    // Initialize controller
    public ModifyProductScreen_Controller() {
    } 

    @FXML
    protected void add(ActionEvent event) {
        associatedFacade.add(selectedPart);
    }

    @FXML
    protected void delete(ActionEvent event) {
        associatedFacade.remove(associatedTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    protected void save(ActionEvent event) {
        // parse view
        int inv, max, min, id;
        id  = strToInt(this.productId.getText().trim());
        inv = strToInt(this.productInventory.getText().trim());
        max = strToInt(this.productMax.getText().trim());
        min = strToInt(this.productMin.getText().trim());
        String name = this.productName.getText().trim();
        Double price = strToDbl(this.productPrice.getText().trim());
        
        // Calls abstract helper method
        observableListToArray(associatedFacade);
        // Set Product Data
        Product product = lookupProduct(selectedProduct);
        product.setInStock(id);
        product.setMax(max);
        product.setMin(min);
        product.setName(name);
        product.setPrice(price);
        product.setProductID(id);
        product.setAssociatedParts(parts);
        
        // Close stage
        Stage partStage = (Stage) save.getScene().getWindow();
        // close window
        partStage.close();
    }

    @FXML
    protected void search(ActionEvent event) {

    }
    
    @FXML
    protected void escape(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("You will loose your work!");
            
        Optional<ButtonType> ok = alert.showAndWait();
        //Do nothing
            if (ok.get() == ButtonType.CANCEL) {
            } else {
                // used the Button cancel to get the scene I am in 
                    Stage partStage = (Stage) cancel.getScene().getWindow();
                // close window
                    partStage.close();
            }
        // used the Button cancel to get the scene I am in 
        Stage partStage = (Stage) cancel.getScene().getWindow();
        // close window
        partStage.close();
    }
    
    // Global Object var is passed the selected Object item
    @FXML
    protected void getSelection(MouseEvent event) {
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    void findPart(ActionEvent event) {
        partsTable.setItems(sortedPartData);
    }
    
    // Search Parts
    private void SearchParts() {
    // Observable predicate for our part search
        partSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartData.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Make everyvalue lowercase
                String lowerCaseFilter = newValue.toLowerCase();
                
            if(part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches part name
                } else if(Integer.toString(part.getPartID()).contains(lowerCaseFilter)) {
                    return true; // Filter matches part Id
                }
                    return false; // Not match.
            });
        });
        
        // Right, its all sorted but now we need to bind sortedData to the view
        sortedPartData.comparatorProperty().bind(partsTable.comparatorProperty());
        
        // Observable predicate for our product search
        partSearchTxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPartData.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Make everyvalue lowercase
                String lowerCaseFilter = newValue.toLowerCase();
                
            if(part.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches part name
            } else if(Integer.toString(part.getPartID()).contains(lowerCaseFilter)) {
                return true; // Filter matches part Id
            }
                return false; // Not match.
            });
        });

        sortedPartData.comparatorProperty().bind(partsTable.comparatorProperty());
    }
    
    // Reference to the main app.
    private MainApp mainApp;
    
    public void initialize(Integer productIndex) {
        selectedProduct = productIndex;
        
        if(selectedProduct == null){
        } else {
            // Get Product Object
            Product product = lookupProduct(productIndex);
            // Facade
            associatedFacade = FXCollections.observableArrayList();
            //Price data
            partId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
            partName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            partInventory.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
            partUnitCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
            
            //  Invoke search filter methods
            SearchParts();
        
            productId.setText(Integer.toString(product.getProductID()));
            productName.setText(product.getName());            
            productInventory.setText(Integer.toString(product.getInStock()));
            productPrice.setText(Double.toString(product.getPrice()));
            productMax.setText(Integer.toString(product.getMax()));
            productMin.setText(Integer.toString(product.getMin()));

            // assoc Data
            associatedId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
            associatedInventory.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
            associatedName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            associatedUnitCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

//            //Bind to view
            associatedFacade.setAll(product.getAssociatedParts());
            associatedTable.setItems(associatedFacade);
            partsTable.setItems(Inventory.getParts()); 
        }
    }
    
    /*
    * Helper methods
    */
    
    // Pipes ObservableList Objects to parts ArrayList
    private void observableListToArray(ObservableList item) {
        parts = new ArrayList();
        item.forEach(partObject -> parts.add(partObject)); 
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
    
    //  helper ends
    
        public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
