package com.ciotola.controller;

import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import com.ciotola.renomaxaccounting.MainAppFX;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceTemplateFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private int invoiceNumber = -1;
    private IAccountingDAO accountDAO;
    private Invoice invoice;
    private List<InvoiceDescription> invoiceDescriptions;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dateField"
    private Label dateField; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumberField"
    private Label invoiceNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="locationField"
    private Label locationField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private Label descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseField"
    private Label expenseField; // Value injected by FXMLLoader

    @FXML // fx:id="paidField"
    private Label paidField; // Value injected by FXMLLoader

    @FXML // fx:id="subtotalField"
    private Label subtotalField; // Value injected by FXMLLoader

    @FXML // fx:id="gstField"
    private Label gstField; // Value injected by FXMLLoader

    @FXML // fx:id="tvqField"
    private Label tvqField; // Value injected by FXMLLoader

    @FXML // fx:id="totalField"
    private Label totalField; // Value injected by FXMLLoader

    @FXML
    void onPrintPage(ActionEvent event) throws IOException, SQLException
    {
        // Create the Printer Job
        PrinterJob job = PrinterJob.createPrinterJob();
         
        // Get The Printer Job Settings
        JobSettings jobSettings = job.getJobSettings();
        
        // Get The Printer
        Printer printer = job.getPrinter();
        
        // Create the Page Layout of the Printer
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        jobSettings.setPageLayout(pageLayout);
        
        //Create the Node
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));        
        Parent parent = (AnchorPane)loader.load();
        
        InvoiceTemplateFXMLController controller = loader.getController();
        controller.setInvoiceTemplateFXMLController(invoiceNumber);
        
        // Print the node
        log.info("Printing page!");
        job.printPage(parent);

        // End the printer job
        job.endJob();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        assert dateField != null : "fx:id=\"dateField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert invoiceNumberField != null : "fx:id=\"invoiceNumberField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert locationField != null : "fx:id=\"locationField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert expenseField != null : "fx:id=\"expenseField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert paidField != null : "fx:id=\"paidField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert subtotalField != null : "fx:id=\"subtotalField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert gstField != null : "fx:id=\"gstField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert tvqField != null : "fx:id=\"tvqField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";
        assert totalField != null : "fx:id=\"totalField\" was not injected: check your FXML file 'InvoiceTemplateFXML.fxml'.";

        accountDAO = new AccountingDAOImp();
    }
    
    public void setInvoiceTemplateFXMLController(int invoiceNum) throws IOException, SQLException
    {
        if(invoiceNum != -1)
            this.invoiceNumber = invoiceNum;
        
         Invoice invoice = accountDAO.findInvoiceByInvoiceNumber(invoiceNumber);
         List<InvoiceDescription> invoiceDescriptions = accountDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber);
         
         dateField.setText(invoice.getInvoiceDate().toString());
         invoiceNumberField.setText(invoice.getInvoiceNumber()+"");
    }
}
