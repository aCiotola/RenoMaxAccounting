package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import com.ciotola.renomaxaccounting.MainAppFX;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class InvoiceFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private int invoiceNumber = -1;
    private String invoiceDescriptionText = "";
    
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
    
    @FXML // fx:id="clientTable"
    private TableView<Invoice> clientTable; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumCol"
    private TableColumn<Invoice, Integer> invoiceNumCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateCol"
    private TableColumn<Invoice, Date> invoiceDateCol; // Value injected by FXMLLoader

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

    @FXML
    void onClearInvoice(ActionEvent event) throws SQLException 
    {
        clientComboBox.setValue("");
        descriptionField.setText("");
        priceField.setText("");
        
        List<InvoiceDescription> invoiceDescriptionList = accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber);
        for(int i = 0; i < invoiceDescriptionList.size(); i++)
            accountDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceNumber());
        
        subtotalField.setText("");
        gstField.setText("");
        qstField.setText("");
        totalField.setText("");
        sentCheckBox.setSelected(false);
        
        LocalDate date = LocalDate.now();  
        int year = (date.getYear() - 1) * 1000;
        invoiceNumber = year + accountDAO.findNewInvoiceNumber();
        
        invoiceNumberField.setText(invoiceNumber+"");
        invoiceDateField.setValue(date); 
        
        displayDescriptionTable(invoiceNumber);
    }

    @FXML
    void onSaveDescription(ActionEvent event) throws SQLException {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals(""))
        {            
            InvoiceDescription invoiceDescription = new InvoiceDescription();
            invoiceDescription.setInvoiceDescription(descriptionField.getText());
            invoiceDescription.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));
            invoiceDescription.setInvoiceNumber(invoiceNumber);
            
            accountDAO.addInvoiceDescription(invoiceDescription);
            
            log.debug("Invoice Description created!");            
            descriptionField.setText("");     
            priceField.setText("");
            displayDescriptionTable(invoiceNumber);
        }
        else        
            descriptionField.setText("Please enter a description/price!");
    }
    
    @FXML
    void onDeleteDescription(ActionEvent event) throws SQLException 
    {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals(""))
        {                                  
            InvoiceDescription invoiceDescription = accountDAO.findInvoiceDescriptionByName(descriptionField.getText(), invoiceNumber);
            accountDAO.deleteInvoiceDescription(invoiceDescription.getInvoiceDescriptionID());
            descriptionField.setText("");
            priceField.setText("");
            
            log.debug("Invoice Description deleted!");            
            displayDescriptionTable(invoiceNumber);
        }
        else        
            descriptionField.setText("Please select a description!");
    }

    @FXML
    void onEditDescription(ActionEvent event) throws SQLException
    {
        if(!descriptionField.getText().equals("") && !priceField.getText().equals(""))
        {                                  
            InvoiceDescription invoiceDescription = accountDAO.findInvoiceDescriptionByName(invoiceDescriptionText, invoiceNumber);
            invoiceDescription.setInvoiceDescription(descriptionField.getText());
            invoiceDescription.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));
            accountDAO.updateInvoiceDescription(invoiceDescription);
            descriptionField.setText("");
            priceField.setText("");
            
            log.debug("Invoice Description updated!");            
            displayDescriptionTable(invoiceNumber);
        }
        else        
            descriptionField.setText("Please select a description!");
    }
    
    @FXML
    void onSaveInvoice(ActionEvent event) throws SQLException 
    {
        if(!invoiceNumberField.getText().equals("") && invoiceDateField.getValue() != null && clientComboBox.getValue() != null
                && !subtotalField.getText().equals("") && !gstField.getText().equals("") && !qstField.getText().equals("")
                && !totalField.getText().equals(""))
        {             
            Invoice invoice = accountDAO.findInvoiceByInvoiceNumber(invoiceNumber);    
                                    
            invoice.setInvoiceNumber(Integer.parseInt(invoiceNumberField.getText()));
            invoice.setInvoiceDate(Date.valueOf(invoiceDateField.getValue()));
            invoice.setClient(clientComboBox.getValue());
            invoice.setSubtotal(BigDecimal.valueOf(Double.parseDouble(subtotalField.getText())));
            invoice.setGst(BigDecimal.valueOf(Double.parseDouble(gstField.getText())));
            invoice.setQst(BigDecimal.valueOf(Double.parseDouble(qstField.getText())));
            invoice.setTotal(BigDecimal.valueOf(Double.parseDouble(totalField.getText())));
            invoice.setInvoiceSent(sentCheckBox.isSelected());
            
            if(invoice.getInvoiceID() != -1)            
                accountDAO.updateInvoice(invoice);            
            else
                accountDAO.addInvoice(invoice);
            
            log.debug("Invoice created/Updated!");  
            
            onClearInvoice(new ActionEvent());     
            displayClientTable();
        }
        else        
            descriptionField.setText("Please enter the client name, subtotal, taxes and total!");  
    }
    
    @FXML
    void onDeleteInvoice(ActionEvent event) throws SQLException 
    {
        if(!invoiceNumberField.getText().equals("") && invoiceDateField.getValue() != null && clientComboBox.getValue() != null
                && !subtotalField.getText().equals("") && !gstField.getText().equals("") && !qstField.getText().equals("")
                && !totalField.getText().equals(""))
        {
            Invoice invoice = accountDAO.findInvoiceByInvoiceNumber(invoiceNumber);
            
            List<InvoiceDescription> invoiceDescriptionList = accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber);
            for(int i = 0; i < invoiceDescriptionList.size(); i++)
                accountDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceDescriptionID());
            
            accountDAO.deleteInvoice(invoice.getInvoiceID());
            
            onClearInvoice(new ActionEvent());
            displayClientTable();            
            log.debug("Invoice deleted!");    
        }
        else        
            descriptionField.setText("Please enter the client name, subtotal, taxes and total!");  
    }

    @FXML
    void onShowInvoice(ActionEvent event) throws IOException, SQLException 
    {
        Stage invoiveStage = new Stage();
        invoiveStage.setTitle("RenoMax Accounting 2018");
        invoiveStage.resizableProperty().setValue(false);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));       
        
        Parent parent = (AnchorPane)loader.load();
        Scene scene = new Scene(parent);        
        
        invoiveStage.setScene(scene);
        
        InvoiceTemplateFXMLController controller = loader.getController();
        controller.setInvoiceTemplateFXMLController(invoiceNumber);
        
        invoiveStage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException 
    {
        assert invoiceNumberField != null : "fx:id=\"invoiceNumberField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
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
        assert clientTable != null : "fx:id=\"clientTable\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceNumCol != null : "fx:id=\"invoiceNumCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceDateCol != null : "fx:id=\"invoiceDateCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceClientCol != null : "fx:id=\"invoiceClientCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceSubTotalCol != null : "fx:id=\"invoiceSubTotalCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceGstCol != null : "fx:id=\"invoiceGstCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceQstCol != null : "fx:id=\"invoiceQstCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceTotalCol != null : "fx:id=\"invoiceTotalCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert invoiceSentCol != null : "fx:id=\"invoiceSentCol\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        
        accountDAO = new AccountingDAOImp();    
        fillClientComboBox();
        setupInvoice();

        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDescriptionProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());        
        invoiceNumCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceNumberProperty().asObject());
        invoiceDateCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDateProperty());
        invoiceClientCol.setCellValueFactory(cellData -> cellData.getValue().getClientProperty());
        invoiceSubTotalCol.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
        invoiceGstCol.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
        invoiceQstCol.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
        invoiceTotalCol.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
        invoiceSentCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceSentProperty().asString());
        
        descriptionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDescriptionDetails(newValue));    
        clientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> 
        {
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
    public void displayDescriptionTable(int invoiceNumber) throws SQLException
    {
        descriptionTable.setItems(accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber));
    }
    
    /**
     * Will display invoice clients in the table.
     * 
     * @throws SQLException 
     */
    public void displayClientTable() throws SQLException
    {
        clientTable.setItems(accountDAO.findAllInvoices());        
    }
    
    /**
     * Listener which will fill the field with the selected Invoice Description.
     * 
     * @param invoiceDescription 
     */
    private void showDescriptionDetails(InvoiceDescription invoiceDescription)
    {
        if(invoiceDescription != null)
        {
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
    private void showInvoiceDetails(Invoice invoice) throws SQLException
    {
        if(invoice != null)
        {
            invoiceNumber = invoice.getInvoiceNumber();
            invoiceNumberField.setText(invoice.getInvoiceNumber()+"");
            invoiceDateField.setValue(invoice.getInvoiceDate().toLocalDate());
            clientComboBox.setValue(invoice.getClient());
            displayDescriptionTable(invoiceNumber);
            subtotalField.setText(invoice.getSubtotal().toString());
            gstField.setText(invoice.getGst().toString());
            qstField.setText(invoice.getQst().toString());
            totalField.setText(invoice.getTotal().toString());
            sentCheckBox.setSelected(invoice.getInvoiceSent());
        }
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
        LocalDate date = LocalDate.now();  
        int year = (date.getYear() - 1)  * 1000;
        invoiceNumber = year + accountDAO.findNewInvoiceNumber();
        
        invoiceNumberField.setText(invoiceNumber+"");
        invoiceDateField.setValue(date);      
        
        displayClientTable();
        displayDescriptionTable(invoiceNumber);
    }
}
