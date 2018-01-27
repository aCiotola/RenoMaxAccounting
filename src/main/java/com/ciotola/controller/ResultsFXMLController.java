package com.ciotola.controller;

import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultsFXMLController 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="expenseResults"
    private Label expenseResults; // Value injected by FXMLLoader

    /**
     * Method which will update all results on the page.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onRefreshData(ActionEvent event) throws SQLException 
    {
        log.debug("Refresh button pressed!");
        calculateExpenseTotal();
    }
    
    /**
     * No parameter constructor which calls the super's constructor.
     * 
     */
    public ResultsFXMLController()
    {
        super();
    }
    
    /**
     * This method is called by the FXMLLoader when initialization is complete.
     * Will initialize the DAO object.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    @FXML 
    void initialize() throws IOException, SQLException
    {
        assert expenseResults != null : "fx:id=\"expenseResults\" was not injected: check your FXML file 'ResultsFXML.fxml'.";

        accountDAO = new AccountingDAOImp();
        calculateExpenseTotal();
    }
    
    /**
     * Method which will get the total amount of expenses and display it on the 
     * application.
     * 
     * @throws SQLException 
     */
    private void calculateExpenseTotal() throws SQLException
    {
        double total = accountDAO.calculateExpenseTotal();
        expenseResults.setText("$" + total);
    }
}