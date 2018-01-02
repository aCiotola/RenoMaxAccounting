package com.ciotola.controller;

import com.ciotola.entities.MainDescription;
import com.ciotola.entities.SubDescription;
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

public class SubDescriptionFormFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="subDescriptionField"
    private TextField subDescriptionField; // Value injected by FXMLLoader

    @FXML
    void onClearSubDescription(ActionEvent event) 
    {
        subDescriptionField.setText("");
    }

    @FXML
    void onSaveSubDescription(ActionEvent event) throws SQLException 
    {
        if(!subDescriptionField.getText().equals(""))
        {            
            SubDescription subDescription = new SubDescription();     
            subDescription.setSubDescriptionName(subDescriptionField.getText());
            accountDAO.addSubDescription(subDescription);
            
            log.debug("Sub Description created!");
            
            onClearSubDescription(new ActionEvent());
        }
        else        
            subDescriptionField.setText("Please enter a Description!");     
    }
    
     @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert subDescriptionField != null : "fx:id=\"subDescriptionField\" was not injected: check your FXML file 'SubDescriptionFormFXML.fxml'.";
    }
    
    public SubDescriptionFormFXMLController() throws IOException
    {
        super();
        accountDAO = new AccountingDAOImp();
    }
}