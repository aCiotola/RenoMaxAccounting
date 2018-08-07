package com.ciotola.controller;

import com.ciotola.entities.SubDescription;
import com.ciotola.persistence.Implementations.AccountingSubDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSubDescriptionDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class which contains the methods used for creating/reading/updating/deleting SubDescriptions.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class SubDescriptionFXMLController{
    private IAccountingSubDescriptionDAO sdDAO;
    private int sdID = -1;

    @FXML // fx:id="sdNameField"
    private TextField sdNameField; // Value injected by FXMLLoader

    @FXML // fx:id="sdTable"
    private TableView<SubDescription> sdTable; // Value injected by FXMLLoader

    @FXML // fx:id="sdNumColumn"
    private TableColumn<SubDescription, Integer> sdNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="sdNameColumn"
    private TableColumn<SubDescription, String> sdNameColumn; // Value injected by FXMLLoader

    /**
     * Method which will delete the Sub Description chosen.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onDeleteSubDescription(ActionEvent event) throws SQLException {
        if(!sdNameField.getText().equals("")){  
            displayConfirmation("Are you sure you want to delete this SubDescription?");            
        }
        else        
            displayAlert("Please select a Sub Description!");
    }

    /**
     * Method which will check if the Sub Description already exists. If it does, call the
     * update methods, else, call the add method.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSaveSubDescription(ActionEvent event) throws SQLException {
        if(!sdNameField.getText().equals("")){            
            SubDescription subDescription = sdDAO.findSubDescriptionById(sdID);
            subDescription.setSubDescriptionName(sdNameField.getText());
            
            if(subDescription.getSubDescriptionID() != -1)
                sdDAO.updateSubDescription(subDescription);
            else
                sdDAO.addSubDescription(subDescription);
                 
            sdNameField.setText("");    
            displayTable();
        }
        else        
            displayAlert("Please select a Sub Description!");
    }

    /**
     * No parameter constructor which calls the super's constructor.
     * 
     */
    public SubDescriptionFXMLController(){
        super();
    }
    
    /**
     * This method is called by the FXMLLoader when initialization is complete.
     * Will initialize the DAO object and display the table.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    void initialize() throws IOException, SQLException{
        assert sdNameField != null : "fx:id=\"sdNameField\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdTable != null : "fx:id=\"sdTable\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdNumColumn != null : "fx:id=\"sdNumColumn\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdNameColumn != null : "fx:id=\"sdNameColumn\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";

        sdDAO = new AccountingSubDescriptionDAOImp();
        displayTable();
        
        sdNumColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionIDProperty().asObject());
        sdNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionNameProperty());     
        sdTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSubDescriptionDetails(newValue));
    }
    
    /**
     * Will display all Sub Descriptions in the table.
     * 
     * @throws SQLException 
     */
    public void displayTable() throws SQLException{
        sdID = -1;
        sdTable.setItems(sdDAO.findAllSubDescriptions());
    }
    
    /**
     * Listener which will fill the fields with the selected Sub Description.
     * 
     * @param sd 
     */
    private void showSubDescriptionDetails(SubDescription sd) {
        if(sd != null) {
            sdNameField.setText(sd.getSubDescriptionName());
            sdID = sd.getSubDescriptionID();
        }
    }
    
    /**
     * Method responsible for displaying an alert when an error occurs.
     * 
     * @param msg 
     */
    private void displayAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    
    /**
     * Method responsible for displaying a confirmation message when the user attempts 
     * to delete an SubDescription.
     * 
     * @param msg 
     */
    private void displayConfirmation(String msg) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete SubDescription");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            sdDAO.deleteSubDescription(sdID);
            sdNameField.setText("");                 
            displayTable();
        }
    }
}
