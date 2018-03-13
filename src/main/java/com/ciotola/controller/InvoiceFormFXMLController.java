package com.ciotola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class InvoiceFormFXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="invoiceNumberField"
    private TextField invoiceNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateField"
    private DatePicker invoiceDateField; // Value injected by FXMLLoader

    @FXML // fx:id="clientComboBox"
    private ComboBox<?> clientComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextField descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionBtn"
    private Button descriptionBtn; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTable"
    private TableView<?> descriptionTable; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionCol"
    private TableColumn<?, ?> descriptionCol; // Value injected by FXMLLoader

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

    @FXML
    void onClearInvoice(ActionEvent event) {

    }

    @FXML
    void onSaveInvoice(ActionEvent event) {

    }

    @FXML
    void onShowInvoice(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
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

    }
}
