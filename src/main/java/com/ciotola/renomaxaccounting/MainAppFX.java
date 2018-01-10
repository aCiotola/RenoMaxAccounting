package com.ciotola.renomaxaccounting;

import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shado
 */
public class MainAppFX extends Application
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private Stage primaryStage;
    
    private final IAccountingDAO accountDAO;
    
    public MainAppFX() throws IOException
    {
        super();
        accountDAO = new AccountingDAOImp();
    }
    
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
    
    private void configureStage() throws IOException, SQLException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/MainFXML.fxml"));
        
        Parent parent = (BorderPane)loader.load();
        Scene scene = new Scene(parent);
        
        primaryStage.setScene(scene);
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
