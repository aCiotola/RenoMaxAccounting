package com.ciotola.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author shado
 */
public class MainFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    void onMenuAbout(ActionEvent event) 
    {

    }

    @FXML
    void onMenuAddClient(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Client");
        stage.setScene(new Scene(parent));  
        stage.show();        
    }

    @FXML
    void onMenuAddExpense(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExpenseFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Expense");
        stage.setScene(new Scene(parent));  
        stage.show(); 
    }

    @FXML
    void onMenuAddInvoice(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InvoiceFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Invoice");
        stage.setScene(new Scene(parent));  
        stage.show(); 
    }

    @FXML
    void onMenuAddMainDescription(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainDescriptionFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Main Description");
        stage.setScene(new Scene(parent));  
        stage.show(); 
    }

    @FXML
    void onMenuAddSubDescription(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SubDescriptionFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Sub Description");
        stage.setScene(new Scene(parent));  
        stage.show(); 
    }

    @FXML
    void onMenuAddSupplier(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SupplierFormFXML.fxml"));
        Parent parent = (BorderPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a new Supplier");
        stage.setScene(new Scene(parent));  
        stage.show();    
    }

    @FXML
    void onMenuClose(ActionEvent event) 
    {
        log.debug("Program Terminated!");
        Platform.exit();
    }

    @FXML
    void onMenuSave(ActionEvent event) {

    }

    @FXML
    void onMenuSaveAs(ActionEvent event) {

    }

    @FXML
    void onMenuSearchClient(ActionEvent event) {

    }

    @FXML
    void onMenuSearchMainDescription(ActionEvent event) {

    }

    @FXML
    void onMenuSearchSubDescription(ActionEvent event) {

    }

    @FXML
    void onMenuSearchSupplier(ActionEvent event) {

    }

    @FXML
    void onMenuViewClients(ActionEvent event) {

    }

    @FXML
    void onMenuViewMainDescription(ActionEvent event) {

    }

    @FXML
    void onMenuViewSubDescription(ActionEvent event) {

    }

    @FXML
    void onMenuViewSuppliers(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
