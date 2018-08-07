package com.ciotola.renomaxaccounting;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Main application which launches the Main Application along with the tabs.                                                
 *                                                                             
 * @author Alessandro Ciotola                                                   
 * @version 2018/05/20
 */
public class MainAppFX extends Application
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private Stage primaryStage;
    
    /**
     * Constructor which initializes the DAO object.    
     * 
     * @throws IOException 
     */
    public MainAppFX() throws IOException{
        super();
    }
    
    /**
     * Method which sets up the primary stage and shows the stage.
     * 
     * @param primaryStage
     * @throws Exception 
     */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        log.info("Program Begins!");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RenoMax Accounting 2018");
        this.primaryStage.resizableProperty().setValue(false);
        
        configureStage();        
        primaryStage.show();
    }
    
    /**
     * Method which gets the FXML file and creates the scene.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void configureStage() throws IOException, SQLException
    {
        BorderPane root = FXMLLoader.load(MainAppFX.class.getResource("/fxml/MainAppFXML.fxml"));
        primaryStage.setScene(new Scene(root, 1024, 890));
        primaryStage.show();
    }
    
    /**
     * Main method which begins the Program.
     * 
     * @param args 
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
