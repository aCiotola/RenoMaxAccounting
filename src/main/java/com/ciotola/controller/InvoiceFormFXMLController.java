
package com.ciotola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InvoiceFormFXMLController 
{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="clientNameField"
    private TextField clientNameField; // Value injected by FXMLLoader

    @FXML // fx:id="streetField"
    private TextField streetField; // Value injected by FXMLLoader

    @FXML // fx:id="cityField"
    private TextField cityField; // Value injected by FXMLLoader

    @FXML // fx:id="provinceField"
    private TextField provinceField; // Value injected by FXMLLoader

    @FXML // fx:id="postalCodeField"
    private TextField postalCodeField; // Value injected by FXMLLoader

    @FXML // fx:id="hPhoneField"
    private TextField hPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="cPhoneField"
    private TextField cPhoneField; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader

    @FXML
    void onClearInvoice(ActionEvent event) {

    }

    @FXML
    void onSaveInvoice(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert clientNameField != null : "fx:id=\"clientNameField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert streetField != null : "fx:id=\"streetField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert cityField != null : "fx:id=\"cityField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert provinceField != null : "fx:id=\"provinceField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert hPhoneField != null : "fx:id=\"hPhoneField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert cPhoneField != null : "fx:id=\"cPhoneField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'InvoiceFormFXML.fxml'.";

    }
}