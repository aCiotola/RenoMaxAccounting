package com.ciotola.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class which contains the methods used for displaying the GST Reports.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/08
 * 
 */
public class GSTReportFXMLController{
    @FXML // fx:id="firstDateFrom"
    private Label firstDateFrom; // Value injected by FXMLLoader

    @FXML // fx:id="firstDateTo"
    private Label firstDateTo; // Value injected by FXMLLoader

    @FXML // fx:id="firstSupplies"
    private Label firstSupplies; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney105"
    private Label firstMoney105; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney205"
    private Label firstMoney205; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney108"
    private Label firstMoney108; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney208"
    private Label firstMoney208; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney113"
    private Label firstMoney113; // Value injected by FXMLLoader

    @FXML // fx:id="firstMoney213"
    private Label firstMoney213; // Value injected by FXMLLoader

    @FXML // fx:id="firstNetRef"
    private Label firstNetRef; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert firstDateFrom != null : "fx:id=\"firstDateFrom\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstDateTo != null : "fx:id=\"firstDateTo\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstSupplies != null : "fx:id=\"firstSupplies\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney105 != null : "fx:id=\"firstMoney105\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney205 != null : "fx:id=\"firstMoney205\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney108 != null : "fx:id=\"firstMoney108\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney208 != null : "fx:id=\"firstMoney208\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney113 != null : "fx:id=\"firstMoney113\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstMoney213 != null : "fx:id=\"firstMoney213\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert firstNetRef != null : "fx:id=\"firstNetRef\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";

    }
}