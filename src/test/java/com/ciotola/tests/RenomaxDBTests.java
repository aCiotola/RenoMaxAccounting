package com.ciotola.tests;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import com.ciotola.properties.PropsManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUNIT test for the DAO class.
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/15
 * 
 */
public class RenomaxDBTests 
{    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private String url = "";
    private String user = "";
    private String password = "";
    PropsManager pm = new PropsManager();
    PropertiesBean propsBean;

    /**
     * No parameter constructor which initializes the DAO object and database credentials.
     * 
     * @throws IOException 
     */
    public RenomaxDBTests() throws IOException 
    {
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        url = this.url.replace("\\", "");
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
        accountDAO = new AccountingDAOImp();
    }
    
    /**
     * JUnit test to check if the expense has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddExpense() throws SQLException, IOException 
    {        
        Expense expense = new Expense();   
        expense.setExpenseNumber(2);
        expense.setDateTime(Date.valueOf(LocalDate.now()));
        expense.setSupplier("Walmart");
        expense.setMainDescription("Materials");
        expense.setSubDescription("");
        expense.setSubtotal(new BigDecimal("25.00"));
        expense.setGst(new BigDecimal("2.000"));
        expense.setQst(new BigDecimal("2.000"));
        expense.setTotal(new BigDecimal("29.00"));
        
        int records = accountDAO.addExpense(expense);
        log.info("Records created: " + records + " " + expense.getExpenseID());
        Expense expense2 = accountDAO.findExpenseById(expense.getExpenseID());
        
        assertEquals("The records are not equal!", expense, expense2);
    }
    
    /**
     * JUnit test to check if the expense throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddExpenseFail() throws SQLException, IOException  
    {
        Expense expense = null;     
        log.info("Expected Exception: Null Expense");
        accountDAO.addExpense(expense);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the client has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddClient() throws SQLException, IOException 
    {        
        Client client = new Client();        
        client.setClientName("Alfonso");
        client.setStreet("tempst");
        client.setCity("tempcty");
        client.setProvince("tempprov");
        client.setPostalCode("tmp-pst");
        client.setHomePhone("temphome");
        client.setCellPhone("tempcell");
        client.setEmail("tempemail");
        
        int records = accountDAO.addClient(client);
        log.info("Records created: " + records + " " + client.getClientName());
        Client client2 = accountDAO.findClientById(client.getClientID());
        
        assertEquals("The records are not equal!", client, client2);
    }
    
    /**
     * JUnit test to check if the client throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddClientFail() throws SQLException, IOException  
    {
        Client client = null;   
        log.info("Expected Exception: Null Client");
        accountDAO.addClient(client);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the supplier has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddSupplier() throws SQLException, IOException 
    {        
        Supplier supplier = new Supplier();        
        supplier.setSupplierName("Reno Depot");
        
        int records = accountDAO.addSupplier(supplier);
        log.info("Records created: " + records + " " + supplier.getSupplierName());
        Supplier supplier2 = accountDAO.findSupplierById(supplier.getSupplierID());
        
        assertEquals("The records are not equal!", supplier, supplier2);
    }
    
    /**
     * JUnit test to check if the supplier throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddSupplierFail() throws SQLException, IOException  
    {
        Supplier supplier = null;      
        log.info("Expected Exception: Null Supplier");
        accountDAO.addSupplier(supplier);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the Main Description has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddMainDescription() throws SQLException, IOException 
    {        
        MainDescription mainDescription = new MainDescription();        
        mainDescription.setMainDescriptionName("Auto-Maintenance");
        
        int records = accountDAO.addMainDescription(mainDescription);
        log.info("Records created: " + records + " " + mainDescription.getMainDescriptionName());
        MainDescription mainDescription2 = accountDAO.findMainDescriptionById(mainDescription.getMainDescriptionID());
        
        assertEquals("The records are not equal!", mainDescription, mainDescription2);
    }
    
    /**
     * JUnit test to check if the Main Description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddMainDescriptionFail() throws SQLException, IOException  
    {
        MainDescription mainDescription = null;      
        log.info("Expected Exception: Null Main Description");
        accountDAO.addMainDescription(mainDescription);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    /**
     * JUnit test to check if the Sub Description has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    
    @Test(timeout = 1000)
    public void testAddSubDescription() throws SQLException, IOException 
    {        
        SubDescription subDescription = new SubDescription();        
        subDescription.setSubDescriptionName("Restaurant");
        
        int records = accountDAO.addSubDescription(subDescription);
        log.info("Records created: " + records + " " + subDescription.getSubDescriptionName());
        SubDescription subDescription2 = accountDAO.findSubDescriptionById(subDescription.getSubDescriptionID());
        
        assertEquals("The records are not equal!", subDescription, subDescription2);
    }
    
    /**
     * JUnit test to check if the Sub Description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddSubDescriptionFail() throws SQLException, IOException  
    {
        SubDescription subDescription = null;      
        log.info("Expected Exception: Null Sub Description");
        accountDAO.addSubDescription(subDescription);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the invoice has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddInvoice() throws SQLException, IOException 
    {        
        Invoice invoice = new Invoice();        
        invoice.setInvoiceNumber(2017001);
        invoice.setInvoiceDate(Date.valueOf(LocalDate.now()));
        invoice.setClient("Doctor Miller");
        invoice.setSubtotal(new BigDecimal("41.28"));
        invoice.setGst(new BigDecimal("1.000"));
        invoice.setQst(new BigDecimal("4.000"));
        invoice.setTotal(new BigDecimal("46.28"));
        invoice.setInvoiceSent(false);
        
        int records = accountDAO.addInvoice(invoice);
        log.info("Records created: " + records + " " + invoice.getInvoiceNumber());
        Invoice invoice2 = accountDAO.findInvoiceById(invoice.getInvoiceID());
        
        assertEquals("The records are not equal!", invoice, invoice2);
    }
    
    /**
     * JUnit test to check if the invoice throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddInvoiceFail() throws SQLException, IOException  
    {
        Invoice invoice = null;  
        log.info("Expected Exception: Null Invoice");
        accountDAO.addInvoice(invoice);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the invoice description has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddInvoiceDescription() throws SQLException, IOException 
    {        
        InvoiceDescription invoiceDescription = new InvoiceDescription();        
        invoiceDescription.setInvoiceNumber(2017001);
        invoiceDescription.setInvoiceDescription("Description Test");        
        
        int records = accountDAO.addInvoiceDescription(invoiceDescription);
        log.info("Records created: " + records + " " + invoiceDescription.getInvoiceNumber());
        InvoiceDescription invoiceDescription2 = accountDAO.findInvoiceDescriptionById(invoiceDescription.getInvoiceDescriptionID());
        
        assertEquals("The records are not equal!", invoiceDescription, invoiceDescription2);
    }
    
    /**
     * JUnit test to check if the invoice description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddInvoiceDescriptionFail() throws SQLException, IOException  
    {
        InvoiceDescription invoiceDescription = null;  
        log.info("Expected Exception: Null Invoice Description");
        accountDAO.addInvoiceDescription(invoiceDescription);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindExpenseByID() throws SQLException, IOException  
    {
        Expense expense = accountDAO.findExpenseById(1);
        log.info(expense.toString());
        
        assertEquals("Expense found: ", new BigDecimal("26.99"), expense.getTotal());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindExpenseByIDFail() throws SQLException, IOException  
    {
        Expense expense = null;
        log.info("Expected Exception: Null Expense");
        expense = accountDAO.findExpenseById(expense.getExpenseID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindClientByID() throws SQLException, IOException  
    {
        Client client = accountDAO.findClientById(20);
        log.info(client.toString());
        
        assertEquals("Client found: ", "Dr. Miller Richard", client.getClientName());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindClientByIDFail() throws SQLException, IOException  
    {
        Client client = null;
        log.info("Expected Exception: Null Client");
        client = accountDAO.findClientById(client.getClientID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindInvoiceByID() throws SQLException, IOException  
    {
        Invoice invoice = accountDAO.findInvoiceById(1);
        log.info(invoice.toString());
        
        assertEquals("Invoice found: ", new BigDecimal("26.99"), invoice.getTotal());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindInvoiceByIDFail() throws SQLException, IOException  
    {
        Invoice invoice = null;
        log.info("Expected Exception: Null Invoice");
        invoice = accountDAO.findInvoiceById(invoice.getInvoiceID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindMainDescriptionByID() throws SQLException, IOException  
    {
        MainDescription mainDescription = accountDAO.findMainDescriptionById(5);
        log.info(mainDescription.toString());
        
        assertEquals("Main Description found: ", "Gas", mainDescription.getMainDescriptionName());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindMainDescriptionByIDFail() throws SQLException, IOException  
    {
        MainDescription mainDescription = null;
        log.info("Expected Exception: Null Main Description");
        mainDescription = accountDAO.findMainDescriptionById(mainDescription.getMainDescriptionID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindSubDescriptionByID() throws SQLException, IOException  
    {
        SubDescription subDescription = accountDAO.findSubDescriptionById(2);
        log.info(subDescription.toString());
        
        assertEquals("Sub Description found: ", "Electricity", subDescription.getSubDescriptionName());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindSubDescriptionByIDFail() throws SQLException, IOException  
    {
        SubDescription subDescription = null;
        log.info("Expected Exception: Null Sub Description");
        subDescription = accountDAO.findSubDescriptionById(subDescription.getSubDescriptionID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the ID can be found in the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindSupplierByID() throws SQLException, IOException  
    {
        Supplier supplier = accountDAO.findSupplierById(33);
        log.info(supplier.toString());
        
        assertEquals("Supplier found: ", "Shell", supplier.getSupplierName());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindSupplierByIDFail() throws SQLException, IOException  
    {
        Supplier supplier = null;
        log.info("Expected Exception: Null Supplier");
        supplier = accountDAO.findSupplierById(supplier.getSupplierID());     
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if all expenses are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllExpenses() throws SQLException, IOException  
    {
        ObservableList<Expense> expenseList = accountDAO.findAllExpenses();
        log.info("Expense list size: " + expenseList.size());
        
        assertEquals("Expenses found: ", 1, expenseList.size());
    }
    
    /**
     * JUnit test to check if all clients are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllClients() throws SQLException, IOException  
    {
        ObservableList<Client> clientList = accountDAO.findAllClients();
        log.info("Client list size: " + clientList.size());
        
        assertEquals("Clients found: ", 60, clientList.size());
    }
    
    /**
     * JUnit test to check if all suppliers are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllSuppliers() throws SQLException, IOException  
    {
        ObservableList<Supplier> supplierList = accountDAO.findAllSuppliers();
        log.info("Supplier list size: " + supplierList.size());
        
        assertEquals("Suppliers found: ", 39, supplierList.size());
    }
    
    /**
     * JUnit test to check if all main description are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllMainDescription() throws SQLException, IOException  
    {
        ObservableList<MainDescription> mainDescriptionList = accountDAO.findAllMainDescriptions();
        log.info("Main Description list size: " + mainDescriptionList.size());
        
        assertEquals("Main Descriptions found: ", 10, mainDescriptionList.size());
    }
    
    /**
     * JUnit test to check if all sub descriptions are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllSubDescription() throws SQLException, IOException  
    {
        ObservableList<SubDescription> subDescriptionList = accountDAO.findAllSubDescriptions();
        log.info("Sub Description list size: " + subDescriptionList.size());
        
        assertEquals("Sub Descriptions found: ", 5, subDescriptionList.size());
    }
    
    /**
     * JUnit test to check if all invoices are returned from the database.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testFindAllInvoices() throws SQLException, IOException  
    {
        ObservableList<Invoice> invoiceList = accountDAO.findAllInvoices();
        log.info("Invoice list size: " + invoiceList.size());
        
        assertEquals("Invoices found: ", 1, invoiceList.size());
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateExpense() throws SQLException, IOException  
    {      
        Expense expense =  accountDAO.findExpenseById(1);   
        expense.setDateTime(Date.valueOf("2017-12-31"));
        accountDAO.updateExpense(expense);
        
        expense =  accountDAO.findExpenseById(1);             
        log.info("Expense date should be changed: " + expense.getDateTime());

        assertEquals("Expense date has been updated", "2017-12-31", expense.getDateTime().toString());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateExpenseFail() throws SQLException, IOException  
    {
        Expense expense = null;        
        log.info("Expected Exception: Null Expense");
        int records = accountDAO.updateExpense(expense);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateClient() throws SQLException, IOException  
    {      
        Client client =  accountDAO.findClientById(1);   
        client.setEmail("newtempemail");
        accountDAO.updateClient(client);
        
        client =  accountDAO.findClientById(1);             
        log.info("Client email should be changed: " + client.getEmail());

        assertEquals("Client email has been updated", "newtempemail", client.getEmail());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateClientFail() throws SQLException, IOException  
    {
        Client client = null;        
        log.info("Expected Exception: Null Client");
        int records = accountDAO.updateClient(client);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateSupplier() throws SQLException, IOException  
    {      
        Supplier supplier =  accountDAO.findSupplierById(1);   
        supplier.setSupplierName("newsuppliername");
        accountDAO.updateSupplier(supplier);
        
        supplier =  accountDAO.findSupplierById(1);             
        log.info("Supplier name should be changed: " + supplier.getSupplierName());

        assertEquals("Supplier name has been updated", "newsuppliername", supplier.getSupplierName());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateSupplierFail() throws SQLException, IOException  
    {
        Supplier supplier = null;      
        log.info("Expected Exception: Null Supplier");
        int records = accountDAO.updateSupplier(supplier);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateMainDescription() throws SQLException, IOException  
    {      
        MainDescription mainDescription =  accountDAO.findMainDescriptionById(1);   
        mainDescription.setMainDescriptionName("newmdname");
        accountDAO.updateMainDescription(mainDescription);
        
        mainDescription =  accountDAO.findMainDescriptionById(1);             
        log.info("Main Description should be changed: " + mainDescription.getMainDescriptionName());

        assertEquals("Main Description has been updated", "newmdname", mainDescription.getMainDescriptionName());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateMainDescriptionFail() throws SQLException, IOException  
    {
        MainDescription mainDescription = null; 
        log.info("Expected Exception: Null Main Description");
        int records = accountDAO.updateMainDescription(mainDescription);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateSubDescription() throws SQLException, IOException  
    {      
        SubDescription subDescription =  accountDAO.findSubDescriptionById(1);   
        subDescription.setSubDescriptionName("newsdname");
        accountDAO.updateSubDescription(subDescription);
        
        subDescription =  accountDAO.findSubDescriptionById(1);             
        log.info("Sub Description should be changed: " + subDescription.getSubDescriptionName());

        assertEquals("Sub Description has been updated", "newsdname", subDescription.getSubDescriptionName());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateSubDescriptionFail() throws SQLException, IOException  
    {
        SubDescription subDescription = null;      
        log.info("Expected Exception: Null Sub Description");
        int records = accountDAO.updateSubDescription(subDescription);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateInvoice() throws SQLException, IOException  
    {      
        Invoice invoice =  accountDAO.findInvoiceById(1);   
        invoice.setInvoiceNumber(2017002);
        accountDAO.updateInvoice(invoice);
        
        invoice =  accountDAO.findInvoiceById(1);             
        log.info("Invoice number should be changed: " + invoice.getInvoiceNumber());

        assertEquals("Invoice number has been updated", 2017002, invoice.getInvoiceNumber());
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateInvoiceFail() throws SQLException, IOException  
    {
        Invoice invoice = null;        
        log.info("Expected Exception: Null Invoice");
        int records = accountDAO.updateInvoice(invoice);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteExpense() throws SQLException, IOException  
    {        
        Expense expense =  accountDAO.findExpenseById(1); 
        log.info("Expense shouldn't be null: " + expense.toString());
        accountDAO.deleteExpense(expense.getExpenseID());
        expense =  accountDAO.findExpenseById(1);            
        log.info("Expense should be null: " + expense.toString());

        assertEquals("Expense shouldn't be found: ", -1, expense.getExpenseID());
    }

    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteExpenseFail() throws SQLException, IOException  
    {
        Expense expense = null;   
        log.info("Expected Exception: Null Expense");
        int records = accountDAO.deleteExpense(expense.getExpenseID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteClient() throws SQLException, IOException  
    {        
        Client client =  accountDAO.findClientById(1); 
        log.info("Client shouldn't be null: " + client.toString());
        accountDAO.deleteClient(client.getClientID());
        client =  accountDAO.findClientById(1);            
        log.info("Client should be null: " + client.toString());

        assertEquals("Client shouldn't be found: ", -1, client.getClientID());
    }

    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteClientFail() throws SQLException, IOException  
    {
        Client client = null;        
        log.info("Expected Exception: Null Client");
        int records = accountDAO.deleteClient(client.getClientID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteSupplier() throws SQLException, IOException  
    {        
        Supplier supplier =  accountDAO.findSupplierById(1); 
        log.info("Supplier shouldn't be null: " + supplier.toString());
        accountDAO.deleteSupplier(supplier.getSupplierID());
        supplier =  accountDAO.findSupplierById(1);            
        log.info("Supplier should be null: " + supplier.toString());

        assertEquals("Supplier shouldn't be found: ", -1, supplier.getSupplierID());
    }

    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteSupplierFail() throws SQLException, IOException  
    {
        Supplier supplier = null;    
        log.info("Expected Exception: Null Supplier");
        int records = accountDAO.deleteSupplier(supplier.getSupplierID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteMainDescription() throws SQLException, IOException  
    {        
        MainDescription mainDescription =  accountDAO.findMainDescriptionById(1); 
        log.info("Main Description shouldn't be null: " + mainDescription.toString());
        accountDAO.deleteMainDescription(mainDescription.getMainDescriptionID());
        mainDescription =  accountDAO.findMainDescriptionById(1);            
        log.info("Main Description should be null: " + mainDescription.toString());

        assertEquals("Main Description shouldn't be found: ", -1, mainDescription.getMainDescriptionID());
    }

    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteMainDescriptionFail() throws SQLException, IOException  
    {
        MainDescription supplier = null;      
        log.info("Expected Exception: Null Main Description");
        int records = accountDAO.deleteMainDescription(supplier.getMainDescriptionID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteSubDescription() throws SQLException, IOException  
    {        
        SubDescription subDescription =  accountDAO.findSubDescriptionById(1); 
        log.info("Sub Description shouldn't be null: " + subDescription.toString());
        accountDAO.deleteSubDescription(subDescription.getSubDescriptionID());
        subDescription =  accountDAO.findSubDescriptionById(1);            
        log.info("Sub Description should be null: " + subDescription.toString());

        assertEquals("Sub Description shouldn't be found: ", -1, subDescription.getSubDescriptionID());
    }

    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteSubDescriptionFail() throws SQLException, IOException  
    {
        SubDescription subDescription = null;        
        log.info("Expected Exception: Null Sub Description");
        int records = accountDAO.deleteSubDescription(subDescription.getSubDescriptionID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    /**
     * JUnit test to check if the record is deleted.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testDeleteInvoice() throws SQLException, IOException  
    {        
        Invoice invoice =  accountDAO.findInvoiceById(1); 
        log.info("Invoice shouldn't be null: " + invoice.toString());
        accountDAO.deleteInvoice(invoice.getInvoiceID());
        invoice =  accountDAO.findInvoiceById(1);            
        log.info("Invoice should be null: " + invoice.toString());

        assertEquals("Invoice shouldn't be found: ", -1, invoice.getInvoiceID());
    }
    
    /**
     * JUnit test to check if the record that must be deleted throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testDeleteInvoiceFail() throws SQLException, IOException  
    {
        Invoice invoice = null;        
        log.info("Expected Exception: Null Invoice");
        int records = accountDAO.deleteInvoice(invoice.getInvoiceID());
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }

    /**
     * This routine recreates the database before every test. This makes sure
     * that a destructive test will not interfere with any other test. Does not
     * support stored procedures.
     *
     * This routine is courtesy of Bartosz Majsak, the lead Arquillian developer
     * at JBoss
     */
    @Before
    public void seedDatabase()
    {
        log.debug("Start seed!");
        final String seedDataScript = loadAsString("RenomaxAccountingDBTables.sql");
        try (Connection connection = DriverManager.getConnection(url, user, password)) 
        {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) 
            {
                connection.prepareStatement(statement).execute();
            }
        } 
        catch (SQLException e)
        {
            throw new RuntimeException("Failed seeding database", e);
        }
    }
    
    /**
     * The following methods support the seedDatabase method
     */
    private String loadAsString(final String path) 
    {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);) 
        {
            return scanner.useDelimiter("\\A").next();
        } 
        catch (IOException e)
        {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }
    
    private List<String> splitStatements(Reader reader, String statementDelimiter) 
    {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try 
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) 
                {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter))
                {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) 
    {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}
