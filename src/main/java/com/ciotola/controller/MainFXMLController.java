package com.ciotola.controller;

import java.net.URL;
import java.util.ResourceBundle;
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
public class MainFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    /**
     * Event handler which displays the About form.
     * 
     * @param event 
     */
    @FXML
    void onMenuAbout(ActionEvent event) 
    {

    }

    /**
     * Event handler which closes the application.
     * 
     * @param event 
     */
    @FXML
    void onMenuClose(ActionEvent event) 
    {
        log.debug("Program Terminated!");
        Platform.exit();
    }

    /**
     * Event handler which saves the work done to the current file.
     * 
     * @param event 
     */
    @FXML
    void onMenuSave(ActionEvent event) 
    {

    }

    /**
     * Event handler which saves the work done to a new file.
     * 
     * @param event 
     */
    @FXML
    void onMenuSaveAs(ActionEvent event) 
    {

    }


    /**
     * This method is called by the FXMLLoader when initialization is complete
     * 
     */
    @FXML 
    void initialize() 
    {

    }
}