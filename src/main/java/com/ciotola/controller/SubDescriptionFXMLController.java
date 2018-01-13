package com.ciotola.controller;

import com.ciotola.entities.SubDescription;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubDescriptionFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private String sdName = "";
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="sdNameField"
    private TextField sdNameField; // Value injected by FXMLLoader

    @FXML // fx:id="sdTable"
    private TableView<SubDescription> sdTable; // Value injected by FXMLLoader

    @FXML // fx:id="sdNumColumn"
    private TableColumn<SubDescription, Integer> sdNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="sdNameColumn"
    private TableColumn<SubDescription, String> sdNameColumn; // Value injected by FXMLLoader

    @FXML
    void onDeleteSubDescription(ActionEvent event) throws SQLException
    {
        if(!sdNameField.getText().equals(""))
        {     
            int id = accountDAO.findSubDescriptionByName(sdNameField.getText()).getSubDescriptionID();
            accountDAO.deleteSubDescription(id);
            sdNameField.setText("");
            
            log.debug("Sub Description deleted!");            
            displayTable();
        }
        else        
            sdNameField.setText("Please select a Sub Description!");
    }

    @FXML
    void onSaveSubDescription(ActionEvent event) throws SQLException 
    {
        if(!sdNameField.getText().equals(""))
        {            
            SubDescription subDescription = accountDAO.findSubDescriptionByName(sdName);
            subDescription.setSubDescriptionName(sdNameField.getText());
            
            if(subDescription.getSubDescriptionID() != -1)
                accountDAO.updateSubDescription(subDescription);
            else
                accountDAO.addSubDescription(subDescription);
            
            log.debug("Sub Description created/updated!");            
            sdNameField.setText("");     
            sdName = "";
            displayTable();
        }
        else        
            sdNameField.setText("Please enter a description!");
    }

    public SubDescriptionFXMLController()
    {
        super();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException
    {
        assert sdNameField != null : "fx:id=\"sdNameField\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdTable != null : "fx:id=\"sdTable\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdNumColumn != null : "fx:id=\"sdNumColumn\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";
        assert sdNameColumn != null : "fx:id=\"sdNameColumn\" was not injected: check your FXML file 'SubDescriptionFXML.fxml'.";

        accountDAO = new AccountingDAOImp();
        displayTable();
        
        sdNumColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionIDProperty().asObject());
        sdNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionNameProperty());     
        sdTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSubDescriptionDetails(newValue));
    }
    
    public void displayTable() throws SQLException
    {
        sdTable.setItems(accountDAO.findAllSubDescriptions());
    }
    
    private void showSubDescriptionDetails(SubDescription sd) 
    {
        if(sd != null)        
        {
            sdNameField.setText(sd.getSubDescriptionName());
            sdName = sd.getSubDescriptionName();
        }
    }
}
