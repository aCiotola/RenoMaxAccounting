package com.ciotola.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

/**
 * Controller class which contains the methods used for displaying the Expenses Reports.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 * 
 */
public class ReportsFXMLController {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
        setGridPane();
    }
    
    /**
     * Method responsible for filling the Grid Pane
     */
    private void setGridPane(){
        
    }
}