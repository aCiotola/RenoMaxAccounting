package com.ciotola.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class which contains the methods used for handling the menu bar.
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/15
 * 
 */
public class MainFXMLController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    
    /**
     * Event handler which displays the About form.
     * 
     * @param event 
     */
    @FXML
    void onMenuAbout(ActionEvent event) {

    }

    /**
     * Event handler which closes the application.
     * 
     * @param event 
     */
    @FXML
    void onMenuClose(ActionEvent event) {
        log.debug("Program Terminated!");
        Platform.exit();
    }

    /**
     * Event handler which saves the work done to the current file.
     * 
     * @param event 
     */
    @FXML
    void onMenuSave(ActionEvent event) {

    }

    /**
     * Event handler which saves the work done to a new file.
     * 
     * @param event 
     */
    @FXML
    void onMenuSaveAs(ActionEvent event) {

    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     * 
     */
    @FXML 
    void initialize() {

    }
}