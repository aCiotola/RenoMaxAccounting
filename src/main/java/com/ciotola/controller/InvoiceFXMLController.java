package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.Implementations.AccountingInvoiceDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import com.ciotola.persistence.Implementations.AccountingInvoiceDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingClientDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingClientDAO;
import com.ciotola.renomaxaccounting.MainAppFX;
import com.ciotola.utils.CustomDate;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class which contains the methods used for creating/reading/updating/deleting Invoices.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class InvoiceFXMLController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingInvoiceDAO iDAO;
    private IAccountingInvoiceDescriptionDAO idDAO;
    private IAccountingClientDAO cDAO;
    private int latestNum = -1;
    
    private String invoiceDescriptionText = "";
    private int id = -1;    
    
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
    
    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTable"
    private TableView<InvoiceDescription> descriptionTable; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionCol"
    private TableColumn<InvoiceDescription, String> descriptionCol; // Value injected by FXMLLoader
    
     @FXML // fx:id="priceCol"
    private TableColumn<InvoiceDescription, BigDecimal> priceCol; // Value injected by FXMLLoader

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
    
    @FXML // fx:id="paidField"
    private TextField paidField; // Value injected by FXMLLoader
    
    @FXML // fx:id="clientTable"
    private TableView<Invoice> clientTable; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumCol"
    private TableColumn<Invoice, Integer> invoiceNumCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateCol"
    private TableColumn<Invoice, CustomDate> invoiceDateCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceClientCol"
    private TableColumn<Invoice, String> invoiceClientCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSubTotalCol"
    private TableColumn<Invoice, BigDecimal> invoiceSubTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceGstCol"
    private TableColumn<Invoice, BigDecimal> invoiceGstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceQstCol"
    private TableColumn<Invoice, BigDecimal> invoiceQstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceTotalCol"
    private TableColumn<Invoice, BigDecimal> invoiceTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSentCol"
    private TableColumn<Invoice, String> invoiceSentCol; // Value injected by FXMLLoader
    
    @FXML // fx:id="invoicePaidCol"
    private TableColumn<Invoice, String> invoicePaidCol; // Value injected by FXMLLoader

    @FXML
    void onClearInvoice(ActionEvent event) throws SQLException {
        clientComboBox.setValue("");
        descriptionField.setText("");
        priceField.setText("");
        
        List<InvoiceDescription> invoiceDescriptionList = idDAO.findInvoiceDescriptionByInvoiceNumber(id);
        for(int i = 0; i < invoiceDescriptionList.size(); i++)
            idDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceNumber());
        
        subtotalField.setText("");
        gstField.setText("");
        qstField.setText("");
        totalField.setText("");
        sentCheckBox.setSelected(false);
        paidField.setText("");
        
        LocalDate date = LocalDate.now();  
        
        invoiceNumberField.setText(latestNum+"");
        invoiceDateField.setValue(date); 
        
        displayDescriptionTable(id);
    }

    @FXML
    void onSaveDescription(ActionEvent event) throws SQLException {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals("")){            
            InvoiceDescription invoiceDescription = new InvoiceDescription();
            invoiceDescription.setInvoiceDescription(descriptionField.getText());
            invoiceDescription.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));
            invoiceDescription.setInvoiceNumber(id);
            
            idDAO.addInvoiceDescription(invoiceDescription);
            
            log.debug("Invoice Description created!");            
            descriptionField.setText("");     
            priceField.setText("");
            displayDescriptionTable(id);
        }
        else        
            displayAlert("Please enter a description/price!");
    }
    
    @FXML
    void onDeleteDescription(ActionEvent event) throws SQLException {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals("")) {                                  
            InvoiceDescription invoiceDescription = idDAO.findInvoiceDescriptionByName(descriptionField.getText(), id);
            
            idDAO.deleteInvoiceDescription(invoiceDescription.getInvoiceDescriptionID());
            descriptionField.setText("");
            priceField.setText("");                     
            displayDescriptionTable(id);
        }
        else        
            displayAlert("Please select a description!");
    }

    @FXML
    void onEditDescription(ActionEvent event) throws SQLException {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals("")){                                  
            InvoiceDescription invoiceDescription = idDAO.findInvoiceDescriptionByName(invoiceDescriptionText, id);
            invoiceDescription.setInvoiceDescription(descriptionField.getText());
            invoiceDescription.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));
            
            idDAO.updateInvoiceDescription(invoiceDescription);
            descriptionField.setText("");
            priceField.setText("");                   
            displayDescriptionTable(id);
        }
        else        
            displayAlert("Please select a description!");
    }
    
    @FXML
    void onSaveInvoice(ActionEvent event) throws SQLException {
        if(invoiceDateField.getValue() != null && clientComboBox.getValue() != null && !subtotalField.getText().equals("") && 
                !gstField.getText().equals("") && !qstField.getText().equals("") && !totalField.getText().equals("")){                   
            Invoice invoice = iDAO.findInvoiceById(id);                                 
            invoice.setInvoiceDate(new CustomDate(Date.valueOf(invoiceDateField.getValue()).getTime()));
            invoice.setClient(clientComboBox.getValue());
            invoice.setSubtotal(BigDecimal.valueOf(Double.parseDouble(subtotalField.getText())));
            invoice.setGst(BigDecimal.valueOf(Double.parseDouble(gstField.getText())));
            invoice.setQst(BigDecimal.valueOf(Double.parseDouble(qstField.getText())));
            invoice.setTotal(BigDecimal.valueOf(Double.parseDouble(totalField.getText())));
            invoice.setInvoiceSent(sentCheckBox.isSelected());
            invoice.setInvoicePaid(paidField.getText());
            
            if(invoice.getInvoiceID() != -1)            
                iDAO.updateInvoice(invoice);            
            else
                iDAO.addInvoice(invoice);
                         
            displayClientTable();
        }
        else        
            displayAlert("Please enter the client name, subtotal, taxes and total!");  
    }
    
    @FXML
    void onDeleteInvoice(ActionEvent event) throws SQLException {
        if(invoiceDateField.getValue() != null && clientComboBox.getValue() != null && !subtotalField.getText().equals("") && 
                !gstField.getText().equals("") && !qstField.getText().equals("") && !totalField.getText().equals("")){            
            Invoice invoice = iDAO.findInvoiceById(id);            
            List<InvoiceDescription> invoiceDescriptionList = idDAO.findInvoiceDescriptionByInvoiceNumber(id);
            for(int i = 0; i < invoiceDescriptionList.size(); i++)
                idDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceDescriptionID());      
            
            iDAO.deleteInvoice(invoice.getInvoiceID());            
            onClearInvoice(new ActionEvent());
            displayClientTable();            
        }
        else        
            displayAlert("Please enter the client name, subtotal, taxes and total!");  
    }

    @FXML
    void onShowInvoice(ActionEvent event) throws IOException, SQLException {
        Stage invoiceStage = new Stage();
        invoiceStage.setTitle("RenoMax Accounting 2018");
        invoiceStage.resizableProperty().setValue(false);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));       
        
        Parent parent = (AnchorPane)loader.load();
        Scene scene = new Scene(parent);        
        
        invoiceStage.setScene(scene);
        
        InvoiceTemplateFXMLController controller = loader.getController();
        Client client = cDAO.findClientByName(clientComboBox.getValue());
        controller.setInvoiceTemplateFXMLController(client, id);
        
        invoiceStage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException {
        assert invoiceDateField != null : "fx:id=\"invoiceDateField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert clientComboBox != null : "fx:id=\"clientComboBox\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert priceField != null : "fx:id=\"priceField\" was not injected: check your FXML file 'InvoiceFXML.fxml'.";        
        assert descriptionBtn != null : "fx:id=\"descriptionBtn\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionTable != null : "fx:id=\"descriptionTable\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert descriptionCol != null : "fx:id=\"descriptionCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert priceCol != null : "fx:id=\"priceCol\" was not injected: check your FXML file 'InvoiceFXML.fxml'.";        
        assert subtotalField != null : "fx:id=\"subtotalField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert gstField != null : "fx:id=\"gstField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert qstField != null : "fx:id=\"qstField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert totalField != null : "fx:id=\"totalField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert sentCheckBox != null : "fx:id=\"sentCheckBox\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert paidField != null : "fx:id=\"paidField\" was not injected: check your FXML file 'InvoiceFXML.fxml'.";        
        assert clientTable != null : "fx:id=\"clientTable\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceNumCol != null : "fx:id=\"invoiceNumCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceDateCol != null : "fx:id=\"invoiceDateCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceClientCol != null : "fx:id=\"invoiceClientCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceSubTotalCol != null : "fx:id=\"invoiceSubTotalCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceGstCol != null : "fx:id=\"invoiceGstCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceQstCol != null : "fx:id=\"invoiceQstCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceTotalCol != null : "fx:id=\"invoiceTotalCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceSentCol != null : "fx:id=\"invoiceSentCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoicePaidCol != null : "fx:id=\"invoicePaidCol\" was not injected: check your FXML file 'InvoiceFXML.fxml'.";
        
        iDAO = new AccountingInvoiceDAOImp();   
        idDAO = new AccountingInvoiceDescriptionDAOImp();
        cDAO = new AccountingClientDAOImp();
        
        fillClientComboBox();
        setupInvoice();      

        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDescriptionProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());        
        invoiceNumCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceIDProperty().asObject());
        invoiceDateCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDateProperty());
        invoiceClientCol.setCellValueFactory(cellData -> cellData.getValue().getClientProperty());
        invoiceSubTotalCol.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
        invoiceGstCol.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
        invoiceQstCol.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
        invoiceTotalCol.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
        invoiceSentCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceSentProperty().asString());
        invoicePaidCol.setCellValueFactory(cellData -> cellData.getValue().getInvoicePaidProperty());
        
        descriptionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDescriptionDetails(newValue));    
        clientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showInvoiceDetails(newValue);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(InvoiceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }); 
    }
    
    /**
     * Will display invoice descriptions in the table.
     * 
     * @param invoiceNumber
     * @throws SQLException 
     */
    public void displayDescriptionTable(int invoiceNumber) throws SQLException {
        descriptionTable.setItems(idDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber));
    }
    
    /**
     * Will display invoice clients in the table.
     * 
     * @throws SQLException 
     */
    public void displayClientTable() throws SQLException {
        clientTable.setItems(iDAO.findAllInvoices());        
    }
    
    /**
     * Listener which will fill the field with the selected Invoice Description.
     * 
     * @param invoiceDescription 
     */
    private void showDescriptionDetails(InvoiceDescription invoiceDescription){
        if(invoiceDescription != null){
            descriptionField.setText(invoiceDescription.getInvoiceDescription());
            priceField.setText(invoiceDescription.getPrice().doubleValue()+"");
            invoiceDescriptionText = descriptionField.getText();
        }
    }  
    
    /**
     * Listener which will fill the field with the selected Invoice.
     * 
     * @param invoice
     */
    private void showInvoiceDetails(Invoice invoice) throws SQLException {
        if(invoice != null) {
            id = invoice.getInvoiceID();
            invoiceDateField.setValue(invoice.getInvoiceDate().toLocalDate());
            clientComboBox.setValue(invoice.getClient());
            displayDescriptionTable(id);
            subtotalField.setText(invoice.getSubtotal().toString());
            gstField.setText(invoice.getGst().toString());
            qstField.setText(invoice.getQst().toString());
            totalField.setText(invoice.getTotal().toString());
            sentCheckBox.setSelected(invoice.getInvoiceSent());
            paidField.setText(invoice.getInvoicePaid());
        }
    } 
    
    /**
     * Method which will fill the Client Drop down list with all clients.
     * 
     * @throws SQLException 
     */
    private void fillClientComboBox() throws SQLException {
        ObservableList<Client> clients = cDAO.findAllClients();
        ObservableList<String> cElements = FXCollections.observableArrayList();
        for(int i = 0; i < clients.size(); i++)
            cElements.add(clients.get(i).getClientName());
        clientComboBox.setItems(cElements);
    }
    
    /**
     * Method responsible for setting up the invoice page.
     * 
     */
    private void setupInvoice() throws SQLException {
        LocalDate date = LocalDate.now();  
        ObservableList<Invoice> invoices = iDAO.findAllInvoices();
        
        if(invoices.size() > 0)        
            id = invoices.get(invoices.size() - 1).getInvoiceID() + 1;
        else{
            int year = (date.getYear() - 1)  * 1000;
            id = year + 1;
        }
            
        latestNum = id;
        
        invoiceNumberField.setText(id+"");
        invoiceDateField.setValue(date);      
        
        displayClientTable();
        displayDescriptionTable(id);
    }
    
    /**
     * Method responsible for displaying an alert when an error occurs.
     * 
     * @param msg 
     */
    private void displayAlert(String msg){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}