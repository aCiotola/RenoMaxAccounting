package com.ciotola.controller;

import com.ciotola.entities.MainDescription;
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

public class MainDescriptionFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private String mdName = "";
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mdField"
    private TextField mdField; // Value injected by FXMLLoader

    @FXML // fx:id="mdTable"
    private TableView<MainDescription> mdTable; // Value injected by FXMLLoader

    @FXML // fx:id="mdNumField"
    private TableColumn<MainDescription, Integer> mdNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="mdNameField"
    private TableColumn<MainDescription, String> mdNameColumn; // Value injected by FXMLLoader

    @FXML
    void onDeleteMainDescription(ActionEvent event) throws SQLException 
    {
        if(!mdField.getText().equals(""))
        {     
            int id = accountDAO.findMainDescriptionByName(mdField.getText()).getMainDescriptionID();
            accountDAO.deleteMainDescription(id);
            mdField.setText("");
            
            log.debug("Main Description deleted!");            
            displayTable();
        }
        else        
            mdField.setText("Please select a Main Description!");
    }

    @FXML
    void onSaveMainDescription(ActionEvent event) throws SQLException 
    {
        if(!mdField.getText().equals(""))
        {            
            MainDescription mainDescription = accountDAO.findMainDescriptionByName(mdName);     
            mainDescription.setMainDescriptionName(mdField.getText());
            
            if(mainDescription.getMainDescriptionID() != -1)
                accountDAO.updateMainDescription(mainDescription);
            else
                accountDAO.addMainDescription(mainDescription);
            
            log.debug("Main Description created/updated!");            
            mdField.setText("");
            mdName = "";
            displayTable();
        }
        else        
            mdField.setText("Please enter a description!");
    }

    public MainDescriptionFXMLController()
    {
        super();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws SQLException, IOException
    {
        assert mdField != null : "fx:id=\"mdField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdTable != null : "fx:id=\"mdTable\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdNumColumn != null : "fx:id=\"mdNumField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";
        assert mdNameColumn != null : "fx:id=\"mdNameField\" was not injected: check your FXML file 'MainDescriptionFXML.fxml'.";

        accountDAO = new AccountingDAOImp();
        displayTable();
        
        mdNumColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionIDProperty().asObject());
        mdNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionNameProperty());     
        mdTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMainDescriptionDetails(newValue));
    }
    
    public void displayTable() throws SQLException
    {
        mdTable.setItems(accountDAO.findAllMainDescriptions());
    }
    
    private void showMainDescriptionDetails(MainDescription md) 
    {
        if(md != null) 
        {
            mdField.setText(md.getMainDescriptionName());
            mdName = md.getMainDescriptionName();
        }
    }
}
