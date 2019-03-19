/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import inventorysystem.MainApp;
import java.util.Optional;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Inventory;
import static model.Inventory.*;
import model.Part;
import model.Product;


/**
 * FXML Controller class
 *
 * @author Joseph Richardson
 */
public class MainScreen_Controller {
    //  This let me user an abstraact method and reduced amount of code.
    private final String AddPartScreen = "AddPartScreen.fxml";
    private final String AddProductScreen = "AddProductScreen.fxml";
    protected Alert alert;
    
    // Data to cast to ModifyPartScreen_Controller
    private Integer selectedPart;
    
    // Data to cast to ModifyProductScreen_Controller
    private Integer selectedProduct;
    
    /*
    *   Observable list is checked against a predicate and piped into a filtered list
    *   This filtered list is then assigned to a sortedList for out given type
    *   The change lisener is firing while typing in search field
    *   The sorted list is set when the Search button has an actionevent.
    */
    // Filtered for Observable part list
    private final FilteredList<Part> filteredPartData = new FilteredList<>(allParts, p -> true);
    private final FilteredList<Product> filteredProductData = new FilteredList<>(products, p -> true);
    
    // Move filtered data to a sorted list
    private final SortedList<Part> sortedPartData = new SortedList<>(filteredPartData);
    private final SortedList<Product> sortedProductData = new SortedList<>(filteredProductData);
    
    

    //Helper Method for adding parts and products "not used for modify"
    protected void sceneLoader(String resources) {
        try {
        // Loads parameters layout from fxml file.
        FXMLLoader layout = new FXMLLoader(getClass().getResource(resources));
        
        //Establish parent object of new node
        Parent resource = (Parent) layout.load();
       
        //Set a new stage with a new scene
        Stage stage = new Stage();
        stage.setScene(new Scene(resource));
        stage.show();
        
        } catch (IOException e) {
        // new alert object
        alert = new Alert(AlertType.ERROR);
        alert.setTitle("Resource");
        alert.setHeaderText(null);
        alert.setContentText("System fault occured inform System Administrator");
        }
    }
    
    
    @FXML
    private Button addPart, modifyPart, 
            addProduct, modifyProduct, 
            exit, deletePart, deleteProduct,
            searchPart, searchProduct;
    
    @FXML
    private TextField partSearchText, productSearchText;
    
    //Products Table
    @FXML
    private TableView<Product> productsTable;
    
    @FXML
    private TableColumn<Product, Integer> productId, productInvLvl;
    
    @FXML
    private TableColumn<Product, String> productName;
    
    @FXML
    private TableColumn<Product, Double> productPricePerUnit;
    
    //Parts Table
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partId,partInvLvl;
    
    @FXML
    private TableColumn<Part, String> partName;
    
    
    @FXML
    private TableColumn<Part, Double> partPricePerUnit;
        

    // Action events
    @FXML
    public void close(ActionEvent event) {
        Platform.exit();
    }
    
    /*
    *   Product Logic
    *   I tried combining get selection for part/product
    *   It may be clever..but I decided encapsulation
    *   is better and easier to maintain.
    */ 
    @FXML
    public void getProductSelection(MouseEvent event) {
        /*
        *   A few checks are in place to remove exceptions
        *   But a well planned data flow shouldn't have exceptions
        */
        if (productsTable.getItems().isEmpty()) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Please, create a new Product!");
            alert.showAndWait();
                
            // Disabled until a Part is added
                deleteProduct.setDisable(true);
                modifyProduct.setDisable(true);
        
        
        } else if(event.getSource().equals(productsTable) && !(productsTable.getSelectionModel().getSelectedItem() == null)){

            // Buttons will be available
                deleteProduct.setDisable(false);
                modifyProduct.setDisable(false);
                
            // The scope is of the type Products, so partsTable is set disabled
                deletePart.setDisable(true);
                modifyPart.setDisable(true);
            // Ensures the productstable is cleared when parts are selected
            if(!(selectedPart == null)){
                partsTable.getSelectionModel().clearSelection(selectedPart);
            }
                selectedPart =null;
                // When Table isn't empty it should be selectable
                    deleteProduct.setDisable(false);

                // Gathers and sets global variable

                    selectedProduct = productsTable.getSelectionModel().getSelectedIndex();   
                    System.out.println(selectedProduct); 
        }
          //If empty Notify and disable
    }
    
    @FXML
    public void deleteSelectedProduct(ActionEvent event) {
        /*
        *   A few checks are in place to remove exceptions
        *   But a well planned data flow shouldn't have exceptions
        */
        if(event.getSource().equals(deleteProduct) && !(selectedProduct == null)) {
        // Trap: Check if they are commited to the act
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you wish to delete this Product?");
            
            // Spin and wait for user
            Optional<ButtonType> cancel = alert.showAndWait();
            //Do nothing
            if (cancel.get() == ButtonType.CANCEL) {
                return;
            } else {
            //  Chaining methods to grab AssociatedParts().size()
                int numberOfParts = lookupProduct(selectedProduct).getAssociatedParts().size();
        
        // check if product has parts if not proceed
            if(!(numberOfParts >= 1)) {
                removeProduct(selectedProduct);
                // Let user know why they cannot remove product
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setHeaderText("Warning!");
                    alert.setContentText("Cannot delete a product with associated parts!");
                    alert.showAndWait();                         
            }

        }
        // When delete delete and modify options are disabled
            if (productsTable.getItems().isEmpty()) {
                deleteProduct.setDisable(true);
                modifyProduct.setDisable(true);
            }
        }
    }
    
    @FXML // Sets sorted data when pressed
    public void findProduct(ActionEvent event) {
        productsTable.setItems(sortedProductData);
    }
    
    @FXML // Sets sorted data when pressed
    public void findPart(ActionEvent event) {
        partsTable.setItems(sortedPartData);
    }
    
    @FXML //    implicit sceneLoader method used
    public void AddProduct(ActionEvent event) {
        sceneLoader(AddProductScreen);
    }
    
    @FXML // explicit method
    public void ModifyProduct(ActionEvent event) {
        try {
        // Loads parameters layout from fxml file.
        FXMLLoader layout = new FXMLLoader(getClass().getResource("ModifyProductScreen.fxml"));
        
        //Establish parent object of new node
        Parent resource = (Parent) layout.load();
        
        //ModifyPartScreen_Controller
        ModifyProductScreen_Controller modify = layout.getController();
        modify.initialize(selectedProduct);

       
        //Set a new stage with a new scene
        Stage stage = new Stage();
        stage.setScene(new Scene(resource));
        stage.show();
        
        } catch (IOException e) {
            // Nice allert Message
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Resource load failure! Contact System Admin");
            alert.showAndWait();
        }
    }

    /*
    *   Part Logic
    */ 
    @FXML
    public void getPartSelection(MouseEvent event) {
        //Reset selected Object
            selectedPart = null;
        //If empty Notify and disable
        if (partsTable.getItems().isEmpty()) {
            alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Please, create a new Part!");
            alert.showAndWait();
                
            // Disabled until a Part is added
                deletePart.setDisable(true);
                modifyPart.setDisable(true);
        
        } else if(event.getSource().equals(partsTable) && !(partsTable.getSelectionModel().getSelectedItem() == null)){
            
            // Buttons will be available
                deletePart.setDisable(false);
                modifyPart.setDisable(false);
                
            // The scope is of the type Part, so productsTable is set disabled
                deleteProduct.setDisable(true);
                modifyProduct.setDisable(true);
                
            // Ensures the productstable is cleared when parts are selected
            if((event.getSource().equals(partsTable))){
                        System.out.println("test");
                
            // Ensures the productstable is cleared when parts are selected
            if(!(selectedProduct == null)){
                productsTable.getSelectionModel().clearSelection(selectedProduct);   
            }
                // When Table isn't empty it should be selectable
                    deletePart.setDisable(false);

                // Gathers and sets global variable
                    // not sure why but getSelectedIndex() works for parts and not products
                    selectedPart = partsTable.getSelectionModel().getSelectedIndex();   
                    System.out.println(selectedPart); 
            }
        }
    }
    
    @FXML
    public void deleteSelectedPart(ActionEvent event) {
    // Verify source and contents
        if(event.getSource().equals(deletePart) && !(selectedPart == null)) {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you wish to delete this Part?");
            
            Optional<ButtonType> cancel = alert.showAndWait();
            //Do nothing
            if (cancel.get() == ButtonType.CANCEL) {
                return;
            } else {
            
        // They are commited to the act
            deletePart(selectedPart);
        }
        // Disable delete and modify options "Prevents null  pointer exceptions"
            if (partsTable.getItems().isEmpty()) {
                deletePart.setDisable(true);
                modifyPart.setDisable(true);
            }
        }
    }
    
    @FXML // implicit sceneLoader method used
    public void AddPart(ActionEvent event) {
        sceneLoader(AddPartScreen);
    }
    
    @FXML // explicit method
    public void ModifyPart(ActionEvent event) {
        try {
        // Loads parameters layout from fxml file.
        FXMLLoader layout = new FXMLLoader(getClass().getResource("ModifyPartScreen.fxml"));
        
        //Establish parent object of new node
        Parent resource = (Parent) layout.load();
        
        //ModifyPartScreen_Controller
        ModifyPartScreen_Controller modify = layout.getController();
        modify.initialize(selectedPart);
        //modify.setRadioOption(partOrigin);
       
        //Set a new stage with a new scene
        Stage stage = new Stage();
        stage.setScene(new Scene(resource));
        stage.show();
        
        } catch (IOException e) {
            // Nice Alert Message
            alert = new Alert(AlertType.ERROR);
            alert.setContentText("Resource load failure! Contact System Admin");
            alert.showAndWait();
        }
    }
    
    /*
    *   Helper Methods
    *   They evaluate Name and Id 
    *   I did a little research on Anonymous funtions in lamba aexpressions...
    *   These search methods arent Listening until the Actionevent happens..
    *   I find it helpful that the search button doesn't need to be used again..
    *   to keep searching.
    */
    
    private void SearchParts() {
    // Observable predicate for our part search
        partSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
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
        partSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
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
    
    private void SearchProducts() {
    // Observable predicate for our part search
        productSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Make everyvalue lowercase
                String lowerCaseFilter = newValue.toLowerCase();
                
            if(product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches part name
                } else if(Integer.toString(product.getProductID()).contains(lowerCaseFilter)) {
                    return true; // Filter matches part Id
                }
                    return false; // Not match.
            });
        });
        
        // Right, its all sorted but now we need to bind sortedData to the view
        sortedProductData.comparatorProperty().bind(productsTable.comparatorProperty());
        
        // Observable predicate for our product search
        productSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProductData.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Make everyvalue lowercase
                String lowerCaseFilter = newValue.toLowerCase();
                
            if(product.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filter matches part name
            } else if(Integer.toString(product.getProductID()).contains(lowerCaseFilter)) {
                return true; // Filter matches part Id
            }
                return false; // Not match.
            });
        });

        sortedProductData.comparatorProperty().bind(productsTable.comparatorProperty());
    }
    
    
    // End of Helpers
    
    

    
    // Reference to the main app.
    private MainApp mainApp;
    
    //  If I dont add this the compiler will
    public MainScreen_Controller() {
    }
   
    
    /*  Not needed anymore unless setting view, so Oracle docs say anyways 
    *   Java beans binds primatives in classes into wrapper for use here, and
    *   we access/bind it to a setCellValueFactory.
    */
    @FXML
    public void initialize() {

        //  Product data binding of propert types
        productId.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productInvLvl.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        productPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        //  Part data binding of propert types
        partId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvLvl.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        partPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        /*  Invoke filter methods here, else this would be a mess of filter logic
        *   We want to initialize here not define a method
        */
        SearchParts();
        SearchProducts();
    };
    
    /**
     * reference to main class
     * 
     * @param mainApp
     */
    
    //  The root Parent is buried in the MainApp class and this controller is set to it.
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        productsTable.setItems(Inventory.getProducts());
        partsTable.setItems(Inventory.getParts());
    }
}  