/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import inventorysystem.MainApp;
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
import static model.Inventory.*;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Joseph Richardson
 */
public class ModifyPartScreen_Controller  {
    
    private Alert alert;
    // holds the index recieved from MainScreen_Controller
    private Integer selectedPart;
    private String partOrigin;


    @FXML
    private RadioButton outsourced,inhouse;
    
    @FXML
    private Button save, cancel;

    @FXML
    private ToggleGroup radioToggle;
    
    @FXML
    private Label mechineLabel;


    @FXML
    private TextField partPrice,
            partInventory,
            partName,
            partMax,
            partMin,
            mechineId,
            partId,
            companyName;
    
    // Reference to the main app.
    private MainApp mainApp;
    
    @FXML
    public void escape(ActionEvent event) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("You will loose your work!");
            
        // User is given an option
        Optional<ButtonType> ok = alert.showAndWait();
        //Do nothing
        if (ok.get() == ButtonType.CANCEL) {
            } else {
                    Stage partStage = (Stage) cancel.getScene().getWindow();
                    partStage.close();
            }
        // stage is closed and user is presented with mainscreen
        Stage partStage = (Stage) cancel.getScene().getWindow();
        // close window
        partStage.close();
    }
    
    @FXML
    // sets radio selection based on radio selection
    public void radioSelection(ActionEvent event) {
        if(this.selectedPart == null){
            return;
        } else {
            // change view
            if (inhouse.isSelected()) {
                if (mechineId.getText().trim().isEmpty()){
                    mechineId.setText("");  
                }
                companyName.setVisible(false);
                companyName.setDisable(true);
                mechineId.setVisible(true);
                mechineId.setDisable(false);
                mechineLabel.setText("Mechine ID");
            }             
            if (this.outsourced.isSelected()) {
                if (companyName.getText().trim().isEmpty()){
                    companyName.setText("");  
                }
                this.mechineId.setVisible(false);
                this.companyName.setDisable(false);
                this.companyName.setVisible(true);
                this.mechineLabel.setText("Company Name");
            } 
        }
    }
    
    @FXML
    public void save(ActionEvent event){
        // set variables
        int inv,min,max, mechine, id;
        Double price;
        String name, company;
        //  Change types of view textFields
        id        = strToInt(partId.getText().trim());
        inv       = strToInt(partInventory.getText().trim());
        min       = strToInt(partMin.getText().trim());
        max       = strToInt(partMax.getText().trim());
        mechine   = strToInt(mechineId.getText().trim());
        name      = partName.getText().trim();
        company   = companyName.getText().trim();
        price     = strToDbl(partPrice.getText().trim());
        
        //  I am deleting a part because I cant change it to an instance from different model
        Part part = lookupPart(selectedPart);
        deletePart(part.getPartID());

        

        //If user has made this selection and it isn't empty then update
        if(inhouse.isSelected() &&!(mechineId.getText().trim().isEmpty())) {
            addPart(new Inhouse(id,name,price,inv,min,max,mechine));
        } else {
            //must have selected outsourced
            addPart(new Outsourced(id,name,price,inv,min,max,company));
        }
                // used the Button cancel to get the scene I am in 
        Stage partStage = (Stage) save.getScene().getWindow();
        // close window
        partStage.close();
    }

    public ModifyPartScreen_Controller() {
    }
   
    // The instance of the item is evaluated to setup the view
    private void setRadioOption() {
        if (selectedPart == null){
        } else {
            //  Used for cross referencing model name.
            Part part = lookupPart(selectedPart);
            partOrigin = part.getClass().getName();

            //  inhouse check
            if(partOrigin.equals(Inhouse.class.getName())) {
                this.inhouse.setSelected(true);
                this.mechineId.setVisible(true);
                this.mechineLabel.setText("Mechine ID");
                this.companyName.setText("");
            }
            //  outsourced check
            if(partOrigin.equals(Outsourced.class.getName())) {
                this.inhouse.setSelected(false);
                this.outsourced.setSelected(true);
                this.companyName.setVisible(true);
                this.companyName.setDisable(false);
                this.mechineId.setDisable(true);          
                this.mechineId.setVisible(false);
                this.mechineId.setText("");
                this.mechineLabel.setText("Company Name");
            }
        System.out.println(part.getClass().getName());  
        }
       
    }
    
    @FXML
    public void initialize(Integer partIndex){
        if(partIndex == null){
            //  removes the null pointer exception
        } else {
            //  
            selectedPart = partIndex;
            Part part = lookupPart(selectedPart);
            
            partId.setText(Integer.toString(part.getPartID()));
            partName.setText(part.getName());
            partInventory.setText(Integer.toString(part.getInStock()));
            partPrice.setText(Double.toString(part.getPrice()));
            partMax.setText(Integer.toString(part.getMax()));
            partMin.setText(Integer.toString(part.getMin()));
            companyName.setText(part.getCompanyName());
            
            // invocation to set view textfields
            setRadioOption();
        }
    }
    
    /*
    * Helper methods
    */

    // misc type helpers.
    private int strToInt(String data) {
        int i;
        i = Integer.parseInt(data);
        return i;
    }
    
    private double strToDbl(String data) {
        double d = Double.parseDouble(data);
        return d;
    }
    
    //  End
}