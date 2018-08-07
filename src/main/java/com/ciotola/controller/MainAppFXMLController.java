package com.ciotola.controller;

import com.ciotola.renomaxaccounting.MainAppFX;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Controller class for the Main App.
 * 
 * @author Alessandro Ciotola
 * @version 2018/06/27
 * 
 */
public class MainAppFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tabPane"
    private TabPane tabPane; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException {
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'MainAppFXML.fxml'.";

        tabPane.getTabs().add(new Tab("Expenses"));
        tabPane.getTabs().add(new Tab("Invoices"));
        tabPane.getTabs().add(new Tab("GST Report"));
        tabPane.getTabs().add(new Tab("Reports"));
        tabPane.getTabs().add(new Tab("Clients"));
        tabPane.getTabs().add(new Tab("Suppliers"));
        tabPane.getTabs().add(new Tab("Main Description"));
        tabPane.getTabs().add(new Tab("Sub Description"));
        
        FXMLLoader expenseLoader = new FXMLLoader();        
        expenseLoader.setLocation(MainAppFX.class.getResource("/fxml/ExpenseFXML.fxml"));             
        tabPane.getTabs().get(0).setContent((BorderPane)expenseLoader.load());
        ExpenseFXMLController expenseController = expenseLoader.getController();
        
        FXMLLoader invoiceLoader = new FXMLLoader();        
        invoiceLoader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceFXML.fxml"));             
        tabPane.getTabs().get(1).setContent((AnchorPane)invoiceLoader.load());
        InvoiceFXMLController invoiceController = invoiceLoader.getController();
        
        FXMLLoader gstLoader = new FXMLLoader();
        gstLoader.setLocation(MainAppFX.class.getResource("/fxml/GSTReportFXML.fxml"));        
        tabPane.getTabs().get(2).setContent((ScrollPane)gstLoader.load());        
        GSTReportFXMLController gstController = gstLoader.getController();
        
        FXMLLoader reportLoader = new FXMLLoader();
        reportLoader.setLocation(MainAppFX.class.getResource("/fxml/ReportsFXML.fxml"));        
        tabPane.getTabs().get(3).setContent((BorderPane)reportLoader.load());        
        ReportsFXMLController reportController = reportLoader.getController();
        
        FXMLLoader clientLoader = new FXMLLoader();
        clientLoader.setLocation(MainAppFX.class.getResource("/fxml/ClientFXML.fxml"));        
        tabPane.getTabs().get(4).setContent((BorderPane)clientLoader.load());        
        ClientFXMLController clientController = clientLoader.getController();
        
        FXMLLoader supLoader = new FXMLLoader();
        supLoader.setLocation(MainAppFX.class.getResource("/fxml/SupplierFXML.fxml"));          
        tabPane.getTabs().get(5).setContent((BorderPane)supLoader.load());
        SupplierFXMLController supplierController = supLoader.getController();
        
        FXMLLoader mdLoader = new FXMLLoader();
        mdLoader.setLocation(MainAppFX.class.getResource("/fxml/MainDescriptionFXML.fxml"));          
        tabPane.getTabs().get(6).setContent((BorderPane)mdLoader.load());
        MainDescriptionFXMLController mdController = mdLoader.getController();
        
        FXMLLoader sdLoader = new FXMLLoader();
        sdLoader.setLocation(MainAppFX.class.getResource("/fxml/SubDescriptionFXML.fxml"));          
        tabPane.getTabs().get(7).setContent((BorderPane)sdLoader.load());
        SubDescriptionFXMLController sdController = sdLoader.getController();
        
        expenseController.setControllers(supplierController, mdController, sdController, gstController, reportController);
        invoiceController.setControllers(clientController, gstController, tabPane);
    }
}
