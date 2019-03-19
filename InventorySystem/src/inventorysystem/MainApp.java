/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem;

import java.io.IOException;
import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Inventory;

import view_controller.MainScreen_Controller;



/**
 *
 * @author Joseph Richardson
 */
public class MainApp extends Application {
    
    private Stage primaryStage;
    private Parent root;
    

    public MainApp() {
        Inventory.Inventory();
    }
    
    @Override
    public void start(Stage primaryStage ) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Managment System");
        
        //initialize
        initRootLayout(); 
    }
            
     /**
     * Initializes the main root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view_controller/MainScreen.fxml"));
            root = (AnchorPane)loader.load();
            
            // Give the controller access to the main app.
            MainScreen_Controller controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void mainApp(String[] args) {
        launch(args);
    }
    
}
