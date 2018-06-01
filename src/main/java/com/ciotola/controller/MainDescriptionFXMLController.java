package com.ciotola.controller;

import com.ciotola.entities.MainDescription;
import com.ciotola.persistence.Implementations.AccountingMainDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingMainDescriptionDAO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller class which contains the methods used for creating/reading Main Descriptions.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class MainDescriptionFXMLController {
    private IAccountingMainDescriptionDAO mDAO;

    @FXML // fx:id="mdField"
    private TextField mdField; // Value injected by FXMLLoader

    @FXML // fx:id="mdTable"
    private TableView<MainDescription> mdTable; // Value injected by FXMLLoader

    @FXML // fx:id="mdNumField"
    private TableColumn<MainDescription, Integer> mdNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="mdNameField"
    private TableColumn<MainDescription, String> mdNameColumn; // Value injected by FXMLLoader

    /**
     * Method which will check if the Main Description already exists. If it does, call the
     * update methods, else, call the add method.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSaveMainDescription(ActionEvent event) throws SQLException {
        if(!mdField.getText().equals("")) {                        
            MainDescription mainDescription = mDAO.findMainDescriptionByName(mdField.getText());   
            
            if(mainDescription.getMainDescriptionID() == -1) {
                mainDescription.setMainDescriptionName(mdField.getText());

                mDAO.addMainDescription(mainDescription);                
                mdField.setText("");
                displayTable();
            }
        }
        else        
            displayAlert("Please enter a description!");
    }

    /**
     * No parameter constructor which calls the supers' constructor.
     * 
     */
    public MainDescriptionFXMLController(){
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
    void initialize() throws SQLException, IOException{
        assert mdField != null : "fx:id=\"mdField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdTable != null : "fx:id=\"mdTable\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdNumColumn != null : "fx:id=\"mdNumField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdNameColumn != null : "fx:id=\"mdNameField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";

        mDAO = new AccountingMainDescriptionDAOImp();
        displayTable();
        
        mdNumColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionIDProperty().asObject());
        mdNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionNameProperty());     
        mdTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMainDescriptionDetails(newValue));
    }
    
    /**
     * Will display all Main Descriptions in the table.
     * 
     * @throws SQLException 
     */
    public void displayTable() throws SQLException{
        mdTable.setItems(mDAO.findAllMainDescriptions());
    }
    
    /**
     * Listener which will fill the fields with the selected Main Description.
     * 
     * @param md 
     */
    private void showMainDescriptionDetails(MainDescription md) {
        if(md != null)
            mdField.setText(md.getMainDescriptionName());        
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
}