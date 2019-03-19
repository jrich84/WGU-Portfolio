/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;


import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Inhouse;
import model.Outsourced;
import model.Inventory;
import static model.Inventory.addPart;
import static model.Inventory.allParts;


public class AddPartScreen_Controller {
    protected Alert alert;

    @FXML   //Looks cleaner!
    private RadioButton outsourced, inhouse;

    @FXML
    private ToggleGroup radioToggle;

    @FXML   //Looks cleaner!
    private TextField partPrice,
            partInventory, partName,
            partMax, partMin,
            mechineId, partId, companyName;

    @FXML
    private Label mechineLabel;
    
    @FXML 
    private Button save, cancel; 
    
    @FXML
    public void radioSelection(ActionEvent event) {
        if (inhouse.isSelected() &&  !(outsourced.isSelected())) {
           // System.out.println(inHouse.isVisible());
            companyName.setVisible(false);
            mechineId.setVisible(true);
            mechineLabel.setText("Machine ID");        
        } else {
            mechineId.setVisible(false);
            companyName.setVisible(true);
            mechineLabel.setText("Company Name");
        }
    }
    @FXML
    public void escape(ActionEvent event) {
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
    }
    
    @FXML
    public void save(ActionEvent event) {
        
        // Parse View
        int inv, min, max, mechine;
            inv = Integer.parseInt(partInventory.getText().trim());
            min       = Integer.parseInt(partMin.getText().trim());
            max       = Integer.parseInt(partMax.getText().trim());
            
        String name, company;
        name = partName.getText().trim();
        Double price = Double.parseDouble(partPrice.getText().trim());
        
        
        // Create Part
        if (inhouse.isSelected() &&  !(outsourced.isSelected())) {
            mechine = Integer.parseInt(this.mechineId.getText().trim());
            // call method from Inventory class, so logic stays out of our method.
            Inventory.addPart(new Inhouse(allParts.size(), name, price, inv, min, max, mechine));
        } else {
            company = companyName.getText().trim();
            addPart(new Outsourced(allParts.size(), name, price, inv, min, max, company));
        }
            // used the Button "savePart" to get the current stage
        Stage partStage = (Stage) save.getScene().getWindow();
        // close window
        partStage.close();
    }
    
    @FXML
    public void initialize() {
        partId.setText(Integer.toString(allParts.size()));
    }
    

}

