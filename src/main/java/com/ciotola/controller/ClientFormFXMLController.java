package com.ciotola.controller;

import com.ciotola.entities.Client;
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

/**
 *
 * @author shado
 */
public class ClientFormFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="clientNameField"
    private TextField clientNameField; // Value injected by FXMLLoader

    @FXML // fx:id="streetField"
    private TextField streetField; // Value injected by FXMLLoader

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="provinceField"
    private TextField provinceField; // Value injected by FXMLLoader

    @FXML // fx:id="postalCodeField"
    private TextField postalCodeField; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneField"
    private TextField hPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="cPhoneField"
    private TextField cPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader
    
    @FXML
    void onSaveClient(ActionEvent event) throws SQLException 
    {
        if(!clientNameField.getText().equals(""))
        {            
            Client client = new Client();     
            client.setClientName(clientNameField.getText());
            client.setStreet(streetField.getText());
            client.setCity(cityField.getText());
            client.setProvince(provinceField.getText());
            client.setPostalCode(postalCodeField.getText());
            client.setHomePhone(hPhoneField.getText());
            client.setCellPhone(cPhoneField.getText());
            client.setEmail(emailField.getText());
            accountDAO.addClient(client);
            
            log.debug("Client created!");
            
            onClearClient(new ActionEvent());
        }
        else        
            clientNameField.setText("Please enter a name!");
        
    }
    
    @FXML
    void onClearClient(ActionEvent event) 
    {
        clientNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        provinceField.setText("");
        postalCodeField.setText("");
        hPhoneField.setText("");
        cPhoneField.setText("");
        emailField.setText("");
    }
    
    public ClientFormFXMLController() throws IOException
    {
        super();
        accountDAO = new AccountingDAOImp();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException 
    {
        assert clientNameField != null : "fx:id=\"clientNameField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert streetField != null : "fx:id=\"streetField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cityField != null : "fx:id=\"cityField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert provinceField != null : "fx:id=\"provinceField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert hPhoneField != null : "fx:id=\"hPhoneField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cPhoneField != null : "fx:id=\"cPhoneField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'MainFXML.fxml'.";
    }
}
