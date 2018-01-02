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
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupplierFormFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="supplierName"
    private TextField supplierName; // Value injected by FXMLLoader

    @FXML
    void onClearSupplier(ActionEvent event)
    {
        supplierName.setText("");
    }

    @FXML
    void onSaveSupplier(ActionEvent event) throws SQLException 
    {        
        if(!supplierName.getText().equals(""))
        {            
            Supplier supplier = new Supplier();     
            supplier.setSupplierName(supplierName.getText());
            accountDAO.addSupplier(supplier);
            
            log.debug("Supplier created!");
            
            onClearSupplier(new ActionEvent());
        }
        else        
            supplierName.setText("Please enter a supplier name!");     
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        assert supplierName != null : "fx:id=\"supplierName\" was not injected: check your FXML file 'SupplierFormFXML.fxml'.";
    }
    
    public SupplierFormFXMLController() throws IOException
    {
        super();
        accountDAO = new AccountingDAOImp();
    }
}