package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceFormFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private int invoiceNumber;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="invoiceNumberField"
    private TextField invoiceNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateField"
    private DatePicker invoiceDateField; // Value injected by FXMLLoader

    @FXML // fx:id="clientComboBox"
    private ComboBox<String> clientComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextField descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionBtn"
    private Button descriptionBtn; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTable"
    private TableView<InvoiceDescription> descriptionTable; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionCol"
    private TableColumn<InvoiceDescription, String> descriptionCol; // Value injected by FXMLLoader

    @FXML // fx:id="subtotalField"
    private TextField subtotalField; // Value injected by FXMLLoader

    @FXML // fx:id="gstField"
    private TextField gstField; // Value injected by FXMLLoader

    @FXML // fx:id="qstField"
    private TextField qstField; // Value injected by FXMLLoader

    @FXML // fx:id="totalField"
    private TextField totalField; // Value injected by FXMLLoader

    @FXML // fx:id="sentCheckBox"
    private CheckBox sentCheckBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="clientTable"
    private TableView<Invoice> clientTable; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumCol"
    private TableColumn<Invoice, String> invoiceNumCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateCol"
    private TableColumn<Invoice, String> invoiceDateCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceClientCol"
    private TableColumn<Invoice, String> invoiceClientCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSubTotalCol"
    private TableColumn<Invoice, String> invoiceSubTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceGstCol"
    private TableColumn<Invoice, String> invoiceGstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceQstCol"
    private TableColumn<Invoice, String> invoiceQstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceTotalCol"
    private TableColumn<Invoice, String> invoiceTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSentCol"
    private TableColumn<Invoice, String> invoiceSentCol; // Value injected by FXMLLoader

    @FXML
    void onClearInvoice(ActionEvent event) throws SQLException {
        clientComboBox.setValue("");
        descriptionField.setText("");
        
        List<InvoiceDescription> invoiceDescriptionList = accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber);
        for(int i = 0; i < invoiceDescriptionList.size(); i++)
            accountDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceDescriptionID());
        
        subtotalField.setText("");
        gstField.setText("");
        qstField.setText("");
        totalField.setText("");
        sentCheckBox.setSelected(false);
        
        displayTable(invoiceNumber);
    }

    @FXML
    void onSaveDescription(ActionEvent event) throws SQLException {
        if(!descriptionField.getText().equals(""))
        {            
            InvoiceDescription invoiceDescription = new InvoiceDescription();
            invoiceDescription.setInvoiceDescription(descriptionField.getText());
            invoiceNumber = accountDAO.findNewInvoiceNumber();
            invoiceDescription.setInvoiceNumber(invoiceNumber);
            
            accountDAO.addInvoiceDescription(invoiceDescription);
            
            log.debug("Invoice Description created!");            
            descriptionField.setText("");     
            displayTable(invoiceNumber);
        }
        else        
            descriptionField.setText("Please enter a description!");
    }
    
    @FXML
    void onDeleteDescription(ActionEvent event) {

    }

    @FXML
    void onEditDescription(ActionEvent event) {

    }
    
    @FXML
    void onSaveInvoice(ActionEvent event) {

    }

    @FXML
    void onShowInvoice(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException 
    {
        assert invoiceNumberField != null : "fx:id=\"invoiceNumberField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceDateField != null : "fx:id=\"invoiceDateField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert clientComboBox != null : "fx:id=\"clientComboBox\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionBtn != null : "fx:id=\"descriptionBtn\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionTable != null : "fx:id=\"descriptionTable\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionCol != null : "fx:id=\"descriptionCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert subtotalField != null : "fx:id=\"subtotalField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert gstField != null : "fx:id=\"gstField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert qstField != null : "fx:id=\"qstField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert totalField != null : "fx:id=\"totalField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert sentCheckBox != null : "fx:id=\"sentCheckBox\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        
        accountDAO = new AccountingDAOImp();    
        fillClientComboBox();
        setupInvoice();

        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDescriptionProperty());
        
    }
    
    /**
     * Will display invoice descriptions in the table.
     * 
     * @param invoiceNumber
     * @throws SQLException 
     */
    public void displayTable(int invoiceNumber) throws SQLException
    {
        descriptionTable.setItems(accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber));
    }
    
    /**
     * Method which will fill the Client Drop down list with all clients.
     * 
     * @throws SQLException 
     */
    private void fillClientComboBox() throws SQLException
    {
        ObservableList<Client> clients = accountDAO.findAllClients();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(int i = 0; i < clients.size(); i++)
            elements.add(clients.get(i).getClientName());
        clientComboBox.setItems(elements);
    }
    
    /**
     * Method responsible for setting up the invoice page.
     * 
     */
    private void setupInvoice() throws SQLException
    {
        invoiceNumber = accountDAO.findNewInvoiceNumber();
        LocalDate date = LocalDate.now();            
        
        invoiceNumberField.setText(invoiceNumber+"");
        invoiceDateField.setValue(date);      
    }
}
