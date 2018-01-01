package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFXMLController 
{    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private Client client;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="clientTable"
    private TableView<Client> clientTable; // Value injected by FXMLLoader

    @FXML // fx:id="clientNumberColumn"
    private TableColumn<Client, Integer> clientNumberColumn; // Value injected by FXMLLoader

    @FXML // fx:id="clientNameColumn"
    private TableColumn<Client, String> clientNameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="streetColumn"
    private TableColumn<Client, String> streetColumn; // Value injected by FXMLLoader

    @FXML // fx:id="cityColumn"
    private TableColumn<Client, String> cityColumn; // Value injected by FXMLLoader

    @FXML // fx:id="provinceColumn"
    private TableColumn<Client, String> provinceColumn; // Value injected by FXMLLoader

    @FXML // fx:id="pCodeColumn"
    private TableColumn<Client, String> pCodeColumn; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneColumn"
    private TableColumn<Client, String> hPhoneColumn; // Value injected by FXMLLoader

    @FXML // fx:id="cPhoneColumn"
    private TableColumn<Client, String> cPhoneColumn; // Value injected by FXMLLoader

    @FXML // fx:id="emailColumn"
    private TableColumn<Client, String> emailColumn; // Value injected by FXMLLoader

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
            
            displayTable();
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
    
    public ClientFXMLController()
    {
        super();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException 
    {
        assert clientNumberColumn != null : "fx:id=\"clientNumberColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert clientNameColumn != null : "fx:id=\"clientNameColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert streetColumn != null : "fx:id=\"streetColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cityColumn != null : "fx:id=\"cityColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert provinceColumn != null : "fx:id=\"provinceColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert pCodeColumn != null : "fx:id=\"pCodeColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert hPhoneColumn != null : "fx:id=\"hPhoneColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cPhoneColumn != null : "fx:id=\"cPhoneColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert emailColumn != null : "fx:id=\"emailColumn\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert clientNameField != null : "fx:id=\"clientNameField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert streetField != null : "fx:id=\"streetField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cityField != null : "fx:id=\"cityField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert provinceField != null : "fx:id=\"provinceField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert hPhoneField != null : "fx:id=\"hPhoneField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert cPhoneField != null : "fx:id=\"cPhoneField\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'MainFXML.fxml'.";

        clientNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getClientIDProperty().asObject());
        clientNameColumn.setCellValueFactory(cellData -> cellData.getValue().getClientNameProperty());
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().getStreetProperty());
        cityColumn.setCellValueFactory(cellData -> cellData.getValue().getCityProperty());
        provinceColumn.setCellValueFactory(cellData -> cellData.getValue().getProvinceProperty());     
        pCodeColumn.setCellValueFactory(cellData -> cellData.getValue().getPostalCodeProperty());     
        hPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getHomePhoneProperty());
        cPhoneColumn.setCellValueFactory(cellData -> cellData.getValue().getCellPhoneProperty());      
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());      
        
        clientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showClientDetails(newValue));
    }
    
    public void setClientDAOData(Client client, IAccountingDAO accountDAO)
    {
        this.client = client;
        this.accountDAO = accountDAO;
    }
    
    public void displayTable() throws SQLException
    {
        clientTable.setItems(accountDAO.findAllClients());
    }
    
    private void showClientDetails(Client client) 
    {
        if(client != null)
        {
            clientNameField.setText(client.getClientName());
            streetField.setText(client.getStreet());
            cityField.setText(client.getCity());
            provinceField.setText(client.getProvince());
            postalCodeField.setText(client.getPostalCode());
            hPhoneField.setText(client.getHomePhone());
            cPhoneField.setText(client.getCellPhone());
            emailField.setText(client.getEmail());
        }
    }
}