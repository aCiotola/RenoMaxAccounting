package com.ciotola.controller;

import com.ciotola.entities.Supplier;
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

public class SupplierFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private String sdName = "";
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="supplierNameField"
    private TextField supplierNameField; // Value injected by FXMLLoader

    @FXML // fx:id="supplierTable"
    private TableView<Supplier> supplierTable; // Value injected by FXMLLoader

    @FXML // fx:id="supplierNumColumn"
    private TableColumn<Supplier, Integer> supplierNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="supplierNameColumn"
    private TableColumn<Supplier, String> supplierNameColumn; // Value injected by FXMLLoader

    @FXML
    void onDeleteSupplier(ActionEvent event) throws SQLException
    {
        if(!supplierNameField.getText().equals(""))
        {     
            int id = accountDAO.findSupplierByName(supplierNameField.getText()).getSupplierID();
            accountDAO.deleteSupplier(id);
            supplierNameField.setText("");
            
            log.debug("Supplier deleted!");            
            displayTable();
        }
        else        
            supplierNameField.setText("Please select a supplier!");
    }

    @FXML
    void onSaveSupplier(ActionEvent event) throws SQLException
    {
        if(!supplierNameField.getText().equals(""))
        {            
            Supplier supplier = accountDAO.findSupplierByName(sdName);
            supplier.setSupplierName(supplierNameField.getText());
            
            if(supplier.getSupplierID() != -1)            
                accountDAO.updateSupplier(supplier);            
            else
                 accountDAO.addSupplier(supplier);
            
            log.debug("Supplier created/updated!");            
            supplierNameField.setText("");     
            sdName = "";
            displayTable();
        }
        else        
            supplierNameField.setText("Please enter a name!");
    }
    
    public SupplierFXMLController()
    {
        super();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException 
    {
        assert supplierNameField != null : "fx:id=\"supplierNameField\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierTable != null : "fx:id=\"supplierTable\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierNumColumn != null : "fx:id=\"supplierNumColumn\" was not injected: check your FXML file 'SupplierFXML.fxml'.";
        assert supplierNameColumn != null : "fx:id=\"supplierNameColumn\" was not injected: check your FXML file 'SupplierFXML.fxml'.";

        accountDAO = new AccountingDAOImp();
        displayTable();
        
        supplierNumColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierIDProperty().asObject());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierNameProperty());     
        supplierTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSupplierDetails(newValue));
    }
    
    public void displayTable() throws SQLException
    {
        supplierTable.setItems(accountDAO.findAllSuppliers());
    }
    
    private void showSupplierDetails(Supplier supplier) 
    {
        if(supplier != null)    
        {
            supplierNameField.setText(supplier.getSupplierName());
            sdName = supplier.getSupplierName();
        }
    }
}
