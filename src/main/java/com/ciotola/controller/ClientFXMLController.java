package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.persistence.Implementations.AccountingClientDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingClientDAO;
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
 * Controller class which contains the methods used for creating/reading/updating/deleting clients.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class ClientFXMLController {    
    private IAccountingClientDAO cDAO;
    private int cID = -1;

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
    
    @FXML // fx:id="seachClientField"
    private TextField seachClientField; // Value injected by FXMLLoader
    
    /**
     * Method which will check if the client already exists. If it does, call the
     * update methods, else, call the add method.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSaveClient(ActionEvent event) throws SQLException {
        if(!clientNameField.getText().equals("")){            
            Client client = cDAO.findClientById(cID);
            client.setClientName(clientNameField.getText());
            client.setClientName(clientNameField.getText());
            client.setStreet(streetField.getText());
            client.setCity(cityField.getText());
            client.setProvince(provinceField.getText());
            client.setPostalCode(postalCodeField.getText());
            client.setHomePhone(hPhoneField.getText());
            client.setCellPhone(cPhoneField.getText());
            client.setEmail(emailField.getText());
            
            if(client.getClientID() != -1)
                cDAO.updateClient(client);
            else
                cDAO.addClient(client);
                   
            onClearClient(new ActionEvent());       
            displayTable();
        }
        else        
            displayAlert("Please enter a name!");
        
    }
    
    /**
     * Method which will clear all of the fields
     * 
     * @param event 
     */
    @FXML
    void onClearClient(ActionEvent event) {
        clientNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        provinceField.setText("");
        postalCodeField.setText("");
        hPhoneField.setText("");
        cPhoneField.setText("");
        emailField.setText("");
        cID = -1;
    }
    
    /**
     * Method which will search the client table for names similar to the one given
     * in the search field.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSeachClient(ActionEvent event) throws SQLException {
        onClearClient(new ActionEvent());
        if(!seachClientField.getText().equals("")){          
            clientTable.setItems(cDAO.findClientLikeName(seachClientField.getText()));
        }
        else        
            displayTable();
    }
    
    /**
     * Method which will delete the client chosen.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onDeleteClient(ActionEvent event) throws SQLException {
        if(!clientNameField.getText().equals("")){    
            displayConfirmation("Are you sure you want to delete this client?");
        }
        else        
            displayAlert("Please select a client that you wish to delete!");
    }
    
    /**
     * No parameter constructor which calls the super's constructor.
     * 
     */
    public ClientFXMLController(){
        super();
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete.
     * Will initialize the DAO object and display the table.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    @FXML 
    void initialize() throws IOException, SQLException {
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
        
        cDAO = new AccountingClientDAOImp();
        displayTable();

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
    
    /**
     * Will display all clients in the table.
     * 
     * @throws SQLException 
     */
    public void displayTable() throws SQLException{
        cID = -1;
        clientTable.setItems(cDAO.findAllClients());
    }
    
    /**
     * Listener which will fill the fields with the selected client.
     * 
     * @param client 
     */
    private void showClientDetails(Client client) {
        if(client != null){
            clientNameField.setText(client.getClientName());
            streetField.setText(client.getStreet());
            cityField.setText(client.getCity());
            provinceField.setText(client.getProvince());
            postalCodeField.setText(client.getPostalCode());
            hPhoneField.setText(client.getHomePhone());
            cPhoneField.setText(client.getCellPhone());
            emailField.setText(client.getEmail());
            
            cID = client.getClientID();
        }
    }
    
    /**
     * Method responsible for setting the client name when the invoice has a new client
     * 
     * @param cName 
     */
    public void setClientName(String cName){
        clientNameField.setText(cName);
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
     * to delete an Client.
     * 
     * @param msg 
     */
    private void displayConfirmation(String msg) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Client");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Client client = cDAO.findClientById(cID);            
            cDAO.deleteClient(client.getClientID());
            onClearClient(new ActionEvent());                   
            displayTable();
        }
    }
}