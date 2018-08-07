package com.ciotola.controller;

import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.persistence.Implementations.AccountingExpenseDAOImp;
import com.ciotola.persistence.Implementations.AccountingInvoiceDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingExpenseDAO;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class which contains the methods used for displaying the GST
 * Reports.
 *
 * @author Alessandro Ciotola
 * @version 2018/06/08
 *
 */
public class GSTReportFXMLController {
    private IAccountingExpenseDAO eDAO;
    private IAccountingInvoiceDAO iDAO;
    private int year = -1;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

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

    @FXML // fx:id="firstAmount"
    private Label firstAmount; // Value injected by FXMLLoader

    @FXML // fx:id="yearField"
    private Label yearField; // Value injected by FXMLLoader

    @FXML // fx:id="secDateFrom"
    private Label secDateFrom; // Value injected by FXMLLoader

    @FXML // fx:id="secDateTo"
    private Label secDateTo; // Value injected by FXMLLoader

    @FXML // fx:id="secSupplies"
    private Label secSupplies; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney105"
    private Label secMoney105; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney205"
    private Label secMoney205; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney108"
    private Label secMoney108; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney208"
    private Label secMoney208; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney113"
    private Label secMoney113; // Value injected by FXMLLoader

    @FXML // fx:id="secMoney213"
    private Label secMoney213; // Value injected by FXMLLoader

    @FXML // fx:id="secNetRef"
    private Label secNetRef; // Value injected by FXMLLoader

    @FXML // fx:id="secAmount"
    private Label secAmount; // Value injected by FXMLLoader

    @FXML // fx:id="thirdDateFrom"
    private Label thirdDateFrom; // Value injected by FXMLLoader

    @FXML // fx:id="thirdDateTo"
    private Label thirdDateTo; // Value injected by FXMLLoader

    @FXML // fx:id="thirdSupplies"
    private Label thirdSupplies; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney105"
    private Label thirdMoney105; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney205"
    private Label thirdMoney205; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney108"
    private Label thirdMoney108; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney208"
    private Label thirdMoney208; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney113"
    private Label thirdMoney113; // Value injected by FXMLLoader

    @FXML // fx:id="thirdMoney213"
    private Label thirdMoney213; // Value injected by FXMLLoader

    @FXML // fx:id="thirdNetRef"
    private Label thirdNetRef; // Value injected by FXMLLoader

    @FXML // fx:id="thirdAmount"
    private Label thirdAmount; // Value injected by FXMLLoader

    @FXML // fx:id="fourthDateFrom"
    private Label fourthDateFrom; // Value injected by FXMLLoader

    @FXML // fx:id="fourthDateTo"
    private Label fourthDateTo; // Value injected by FXMLLoader

    @FXML // fx:id="fourthSupplies"
    private Label fourthSupplies; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney105"
    private Label fourthMoney105; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney205"
    private Label fourthMoney205; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney108"
    private Label fourthMoney108; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney208"
    private Label fourthMoney208; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney113"
    private Label fourthMoney113; // Value injected by FXMLLoader

    @FXML // fx:id="fourthMoney213"
    private Label fourthMoney213; // Value injected by FXMLLoader

    @FXML // fx:id="fourthNetRef"
    private Label fourthNetRef; // Value injected by FXMLLoader

    @FXML // fx:id="fourthAmount"
    private Label fourthAmount; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException {
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
        assert firstAmount != null : "fx:id=\"firstAmount\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert yearField != null : "fx:id=\"yearField\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secDateFrom != null : "fx:id=\"secDateFrom\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secDateTo != null : "fx:id=\"secDateTo\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secSupplies != null : "fx:id=\"secSupplies\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney105 != null : "fx:id=\"secMoney105\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney205 != null : "fx:id=\"secMoney205\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney108 != null : "fx:id=\"secMoney108\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney208 != null : "fx:id=\"secMoney208\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney113 != null : "fx:id=\"secMoney113\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secMoney213 != null : "fx:id=\"secMoney213\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secNetRef != null : "fx:id=\"secNetRef\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert secAmount != null : "fx:id=\"secAmount\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdDateFrom != null : "fx:id=\"thirdDateFrom\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdDateTo != null : "fx:id=\"thirdDateTo\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdSupplies != null : "fx:id=\"thirdSupplies\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney105 != null : "fx:id=\"thirdMoney105\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney205 != null : "fx:id=\"thirdMoney205\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney108 != null : "fx:id=\"thirdMoney108\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney208 != null : "fx:id=\"thirdMoney208\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney113 != null : "fx:id=\"thirdMoney113\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdMoney213 != null : "fx:id=\"thirdMoney213\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdNetRef != null : "fx:id=\"thirdNetRef\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert thirdAmount != null : "fx:id=\"thirdAmount\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthDateFrom != null : "fx:id=\"fourthDateFrom\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthDateTo != null : "fx:id=\"fourthDateTo\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthSupplies != null : "fx:id=\"fourthSupplies\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney105 != null : "fx:id=\"fourthMoney105\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney205 != null : "fx:id=\"fourthMoney205\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney108 != null : "fx:id=\"fourthMoney108\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney208 != null : "fx:id=\"fourthMoney208\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney113 != null : "fx:id=\"fourthMoney113\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthMoney213 != null : "fx:id=\"fourthMoney213\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthNetRef != null : "fx:id=\"fourthNetRef\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        assert fourthAmount != null : "fx:id=\"fourthAmount\" was not injected: check your FXML file 'GSTReportFXML.fxml'.";
        
        eDAO = new AccountingExpenseDAOImp();
        iDAO = new AccountingInvoiceDAOImp();
        
        setupDates();
        setupFirstQuarter();
        setupSecondQuarter();
        setupThirdQuarter();
        setupFourthQuarter();
    }
    
    /**
     * Method responsible for setting up GST report dates.
     * 
     */
    public void setupDates(){
        LocalDate date = LocalDate.now();         
        year = (date.getYear());
        
        yearField.setText(year+"");
        firstDateFrom.setText("January 1, " + year);
        firstDateTo.setText("March 31, " + year);
        secDateFrom.setText("April 1, " + year);
        secDateTo.setText("June 30, " + year);
        thirdDateFrom.setText("July 1, " + year);
        thirdDateTo.setText("September 30, " + year);
        fourthDateFrom.setText("October 1, " + year);
        fourthDateTo.setText("December 31, " + year);
    }
    
    /**
     * Method responsible for setting up the first quarter of the GST report.
     * 
     */
    public void setupFirstQuarter() throws SQLException{                
        Date from = Date.valueOf(year + "-" + 01 + "-" + 01);
        Date to = Date.valueOf(year + "-" + 03 + "-" + 31);
        
        List<Expense> expenseList = eDAO.findExpensesBetweenDate(from, to);
        List<Invoice> invoiceList = iDAO.findInvoicesBetweenDate(from, to);
        
        double invoiceSubtotal = 0.0;
        double expenseGst = 0.0;
        double expenseQst = 0.0;
        double invoiceGst = 0.0;
        double invoiceQst = 0.0;
        double netGst = 0.0;
        double netQst = 0.0;
        double total = 0.0;

        for (int i = 0; i < expenseList.size(); i++) {
            expenseGst += expenseList.get(i).getGst().doubleValue();
            expenseQst += expenseList.get(i).getQst().doubleValue();
        }
        
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceSubtotal += invoiceList.get(i).getSubtotal().doubleValue();
            invoiceGst += invoiceList.get(i).getGst().doubleValue();
            invoiceQst += invoiceList.get(i).getQst().doubleValue();
        }

        invoiceSubtotal = Math.round(invoiceSubtotal * 100.0) / 100.0;
        expenseQst = Math.round(expenseQst * 100.0) / 100.0;
        expenseGst = Math.round(expenseGst * 100.0) / 100.0;        
        invoiceQst = Math.round(invoiceQst * 100.0) / 100.0;
        invoiceGst = Math.round(invoiceGst * 100.0) / 100.0;
        netGst = invoiceGst - expenseGst;
        netQst = invoiceQst - expenseQst;        
        netGst = Math.round(netGst * 100.0) / 100.0;
        netQst = Math.round(netQst * 100.0) / 100.0;
        total = netGst + netQst;
        
        if(total >= 0){
            firstAmount.setText("$" + total);
            firstNetRef.setText("$0.00");
        }
        else{
            firstAmount.setText("$0.00");
            firstNetRef.setText("$" + total);
        }
        
        firstSupplies.setText("$" + invoiceSubtotal);
        firstMoney208.setText("$" + expenseQst);
        firstMoney108.setText("$" + expenseGst);
        firstMoney205.setText("$" + invoiceQst);
        firstMoney105.setText("$" + invoiceGst);
        firstMoney113.setText("$" + netGst);
        firstMoney213.setText("$" + netQst);
    }
    
    /**
     * Method responsible for setting up the second quarter of the GST report.
     * 
     */
    public void setupSecondQuarter() throws SQLException{
        Date from = Date.valueOf(year + "-" + 04 + "-" + 01);
        Date to = Date.valueOf(year + "-" + 06 + "-" + 30);
        
        List<Expense> expenseList = eDAO.findExpensesBetweenDate(from, to);
        List<Invoice> invoiceList = iDAO.findInvoicesBetweenDate(from, to);
        
        double invoiceSubtotal = 0.0;
        double expenseGst = 0.0;
        double expenseQst = 0.0;
        double invoiceGst = 0.0;
        double invoiceQst = 0.0;
        double netGst = 0.0;
        double netQst = 0.0;
        double total = 0.0;

        for (int i = 0; i < expenseList.size(); i++) {
            expenseGst += expenseList.get(i).getGst().doubleValue();
            expenseQst += expenseList.get(i).getQst().doubleValue();
        }
        
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceSubtotal += invoiceList.get(i).getSubtotal().doubleValue();
            invoiceGst += invoiceList.get(i).getGst().doubleValue();
            invoiceQst += invoiceList.get(i).getQst().doubleValue();
        }

        invoiceSubtotal = Math.round(invoiceSubtotal * 100.0) / 100.0;
        expenseQst = Math.round(expenseQst * 100.0) / 100.0;
        expenseGst = Math.round(expenseGst * 100.0) / 100.0;        
        invoiceQst = Math.round(invoiceQst * 100.0) / 100.0;
        invoiceGst = Math.round(invoiceGst * 100.0) / 100.0;
        netGst = invoiceGst - expenseGst;
        netQst = invoiceQst - expenseQst;        
        netGst = Math.round(netGst * 100.0) / 100.0;
        netQst = Math.round(netQst * 100.0) / 100.0;
        total = netGst + netQst;
        
        if(total >= 0){
            secAmount.setText("$" + total);
            secNetRef.setText("$0.00");
        }
        else{
            secAmount.setText("$0.00");
            secNetRef.setText("$" + total);
        }
        
        secSupplies.setText("$" + invoiceSubtotal);
        secMoney208.setText("$" + expenseQst);
        secMoney108.setText("$" + expenseGst);
        secMoney205.setText("$" + invoiceQst);
        secMoney105.setText("$" + invoiceGst);
        secMoney113.setText("$" + netGst);
        secMoney213.setText("$" + netQst);        
    }
    
    /**
     * Method responsible for setting up the third quarter of the GST report.
     * 
     */
    public void setupThirdQuarter() throws SQLException{
        Date from = Date.valueOf(year + "-" + 7 + "-" + 01);
        Date to = Date.valueOf(year + "-" + 9 + "-" + 30);
        
        List<Expense> expenseList = eDAO.findExpensesBetweenDate(from, to);
        List<Invoice> invoiceList = iDAO.findInvoicesBetweenDate(from, to);
        
        double invoiceSubtotal = 0.0;
        double expenseGst = 0.0;
        double expenseQst = 0.0;
        double invoiceGst = 0.0;
        double invoiceQst = 0.0;
        double netGst = 0.0;
        double netQst = 0.0;
        double total = 0.0;

        for (int i = 0; i < expenseList.size(); i++) {
            expenseGst += expenseList.get(i).getGst().doubleValue();
            expenseQst += expenseList.get(i).getQst().doubleValue();
        }
        
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceSubtotal += invoiceList.get(i).getSubtotal().doubleValue();
            invoiceGst += invoiceList.get(i).getGst().doubleValue();
            invoiceQst += invoiceList.get(i).getQst().doubleValue();
        }

        invoiceSubtotal = Math.round(invoiceSubtotal * 100.0) / 100.0;
        expenseQst = Math.round(expenseQst * 100.0) / 100.0;
        expenseGst = Math.round(expenseGst * 100.0) / 100.0;        
        invoiceQst = Math.round(invoiceQst * 100.0) / 100.0;
        invoiceGst = Math.round(invoiceGst * 100.0) / 100.0;
        netGst = invoiceGst - expenseGst;
        netQst = invoiceQst - expenseQst;        
        netGst = Math.round(netGst * 100.0) / 100.0;
        netQst = Math.round(netQst * 100.0) / 100.0;
        total = netGst + netQst;
        
        if(total >= 0){
            thirdAmount.setText("$" + total);
            thirdNetRef.setText("$0.00");
        }
        else{
            thirdAmount.setText("$0.00");
            thirdNetRef.setText("$" + total);
        }
        
        thirdSupplies.setText("$" + invoiceSubtotal);
        thirdMoney208.setText("$" + expenseQst);
        thirdMoney108.setText("$" + expenseGst);
        thirdMoney205.setText("$" + invoiceQst);
        thirdMoney105.setText("$" + invoiceGst);
        thirdMoney113.setText("$" + netGst);
        thirdMoney213.setText("$" + netQst);
    }
    
    /**
     * Method responsible for setting up the fourth quarter of the GST report.
     * 
     */
    public void setupFourthQuarter() throws SQLException{
        Date from = Date.valueOf(year + "-" + 7 + "-" + 01);
        Date to = Date.valueOf(year + "-" + 9 + "-" + 30);
        
        List<Expense> expenseList = eDAO.findExpensesBetweenDate(from, to);
        List<Invoice> invoiceList = iDAO.findInvoicesBetweenDate(from, to);
        
        double invoiceSubtotal = 0.0;
        double expenseGst = 0.0;
        double expenseQst = 0.0;
        double invoiceGst = 0.0;
        double invoiceQst = 0.0;
        double netGst = 0.0;
        double netQst = 0.0;
        double total = 0.0;

        for (int i = 0; i < expenseList.size(); i++) {
            expenseGst += expenseList.get(i).getGst().doubleValue();
            expenseQst += expenseList.get(i).getQst().doubleValue();
        }
        
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceSubtotal += invoiceList.get(i).getSubtotal().doubleValue();
            invoiceGst += invoiceList.get(i).getGst().doubleValue();
            invoiceQst += invoiceList.get(i).getQst().doubleValue();
        }

        invoiceSubtotal = Math.round(invoiceSubtotal * 100.0) / 100.0;
        expenseQst = Math.round(expenseQst * 100.0) / 100.0;
        expenseGst = Math.round(expenseGst * 100.0) / 100.0;        
        invoiceQst = Math.round(invoiceQst * 100.0) / 100.0;
        invoiceGst = Math.round(invoiceGst * 100.0) / 100.0;
        netGst = invoiceGst - expenseGst;
        netQst = invoiceQst - expenseQst;        
        netGst = Math.round(netGst * 100.0) / 100.0;
        netQst = Math.round(netQst * 100.0) / 100.0;
        total = netGst + netQst;
        
        if(total >= 0){
            fourthAmount.setText("$" + total);
            fourthNetRef.setText("$0.00");
        }
        else{
            fourthAmount.setText("$0.00");
            fourthNetRef.setText("$" + total);
        }
        
        fourthSupplies.setText("$" + invoiceSubtotal);
        fourthMoney208.setText("$" + expenseQst);
        fourthMoney108.setText("$" + expenseGst);
        fourthMoney205.setText("$" + invoiceQst);
        fourthMoney105.setText("$" + invoiceGst);
        fourthMoney113.setText("$" + netGst);
        fourthMoney213.setText("$" + netQst);
    }
    
    /**
     * Method responsible for setting up the GST report
     * 
     * @throws SQLException 
     */
    public void setupGST() throws SQLException{
        setupDates();
        setupFirstQuarter();
        setupSecondQuarter();
        setupThirdQuarter();
        setupFourthQuarter();
    }
}
