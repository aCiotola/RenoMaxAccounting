package com.ciotola.controller;

import com.ciotola.entities.Supplier;
import com.ciotola.persistence.Implementations.AccountingSupplierDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSupplierDAO;
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
 * Controller class which contains the methods used for creating/reading/updating/deleting Suppliers.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class SupplierFXMLController{
    private IAccountingSupplierDAO sDAO;
    private int sID = -1;

    @FXML // fx:id="supplierNameField"
    private TextField supplierNameField; // Value injected by FXMLLoader

    @FXML // fx:id="supplierTable"
    private TableView<Supplier> supplierTable; // Value injected by FXMLLoader

    @FXML // fx:id="supplierNumColumn"
    private TableColumn<Supplier, Integer> supplierNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="supplierNameColumn"
    private TableColumn<Supplier, String> supplierNameColumn; // Value injected by FXMLLoader

    /**
     * Method which will delete the Supplier chosen.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onDeleteSupplier(ActionEvent event) throws SQLException {
        if(!supplierNameField.getText().equals("")) {  
            displayConfirmation("Are you sure you to delete this supplier?");            
        }
        else        
            displayAlert("Please select a supplier!");
    }

    /**
     * Method which will check if the Supplier  already exists. If it does, call the
     * update methods, else, call the add method.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSaveSupplier(ActionEvent event) throws SQLException {
        if(!supplierNameField.getText().equals("")) {            
            Supplier supplier = sDAO.findSupplierById(sID);
            supplier.setSupplierName(supplierNameField.getText());
            
            if(supplier.getSupplierID() != -1)            
                sDAO.updateSupplier(supplier);            
            else
                 sDAO.addSupplier(supplier);
                    
            supplierNameField.setText("");   
            displayTable();
        }
        else        
            displayAlert("Please enter a name!");
    }
    
    /**
     * No parameter constructor which calls the super's constructor.
     * 
     */
    public SupplierFXMLController(){
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
    void initialize() throws IOException, SQLException {
        assert supplierNameField != null : "fx:id=\"supplierNameField\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierTable != null : "fx:id=\"supplierTable\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierNumColumn != null : "fx:id=\"supplierNumColumn\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierNameColumn != null : "fx:id=\"supplierNameColumn\" was not injected: check your FXML file 'SupplierFXML.fxml'.";

        sDAO = new AccountingSupplierDAOImp();
        displayTable();
        
        supplierNumColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierIDProperty().asObject());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierNameProperty());     
        supplierTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSupplierDetails(newValue));
    }
    
    /**
     * Will display all Suppliers in the table.
     * 
     * @throws SQLException 
     */
    public void displayTable() throws SQLException {
        sID = -1;
        supplierTable.setItems(sDAO.findAllSuppliers());
    }
    
    /**
     * Listener which will fill the fields with the selected Supplier.
     * 
     * @param supplier 
     */
    private void showSupplierDetails(Supplier supplier) {
        if(supplier != null){
            supplierNameField.setText(supplier.getSupplierName());
            sID = supplier.getSupplierID();
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
     * to delete a supplier.
     * 
     * @param msg 
     */
    private void displayConfirmation(String msg) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Supplier");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            sDAO.deleteSupplier(sID);
            supplierNameField.setText("");                      
            displayTable();
        }
    }
}