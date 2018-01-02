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
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainDescriptionFormFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="MainDescriptionField"
    private TextField MainDescriptionField; // Value injected by FXMLLoader

    @FXML
    void onClearMainDescription(ActionEvent event)
    {
        MainDescriptionField.setText("");
    }

    @FXML
    void onSaveMainDescription(ActionEvent event) throws SQLException 
    {
        if(!MainDescriptionField.getText().equals(""))
        {            
            MainDescription mainDescription = new MainDescription();     
            mainDescription.setMainDescriptionName(MainDescriptionField.getText());
            accountDAO.addMainDescription(mainDescription);
            
            log.debug("Main Description created!");
            
            onClearMainDescription(new ActionEvent());
        }
        else        
            MainDescriptionField.setText("Please enter a Description!");                 
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert MainDescriptionField != null : "fx:id=\"MainDescriptionField\" was not injected: check your FXML file 'MainDescriptionFormFXML.fxml'.";
    }
    
    public MainDescriptionFormFXMLController() throws IOException
    {
        super();
        accountDAO = new AccountingDAOImp();
    }
}