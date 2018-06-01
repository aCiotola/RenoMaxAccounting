package com.ciotola.tests;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import com.ciotola.persistence.Implementations.AccountingClientDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingClientDAO;
import com.ciotola.persistence.Implementations.AccountingExpenseDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingExpenseDAO;
import com.ciotola.persistence.Implementations.AccountingInvoiceDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import com.ciotola.persistence.Implementations.AccountingInvoiceDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingMainDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingMainDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingSubDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSubDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingSupplierDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSupplierDAO;
import com.ciotola.properties.PropsManager;
import com.ciotola.utils.CustomDate;
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
public class RenomaxDBTests {    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingClientDAO cDAO;
    private IAccountingExpenseDAO eDAO;
    private IAccountingInvoiceDAO iDAO;
    private IAccountingInvoiceDescriptionDAO idDAO;
    private IAccountingMainDescriptionDAO mdDAO;
    private IAccountingSubDescriptionDAO sdDAO;
    private IAccountingSupplierDAO sDAO;    
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
    public RenomaxDBTests() throws IOException{
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        url = this.url.replace("\\", "");
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
        cDAO = new AccountingClientDAOImp();
        eDAO = new AccountingExpenseDAOImp();
        iDAO = new AccountingInvoiceDAOImp();
        idDAO = new AccountingInvoiceDescriptionDAOImp();
        mdDAO = new AccountingMainDescriptionDAOImp();
        sdDAO = new AccountingSubDescriptionDAOImp();
        sDAO = new AccountingSupplierDAOImp();
    }
    
    /**
     * JUnit test to check if the expense has been added.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testAddExpense() throws SQLException, IOException{        
        Expense expense = new Expense();   
        expense.setDateTime(new CustomDate(Date.valueOf(LocalDate.now()).getTime()));
        expense.setSupplier("Walmart");
        expense.setMainDescription("Materials");
        expense.setSubDescription("");
        expense.setSubtotal(new BigDecimal("25.00"));
        expense.setGst(new BigDecimal("2.000"));
        expense.setQst(new BigDecimal("2.000"));
        expense.setTotal(new BigDecimal("29.00"));
        
        int records = eDAO.addExpense(expense);
        log.info("Records created: " + records + " " + expense.getExpenseID());
        Expense expense2 = eDAO.findExpenseById(expense.getExpenseID());
        
        assertEquals("The records are not equal!", expense, expense2);
    }
    
    /**
     * JUnit test to check if the expense throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddExpenseFail() throws SQLException, IOException{
        Expense expense = null;     
        log.info("Expected Exception: Null Expense");
        eDAO.addExpense(expense);
        
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
    public void testAddClient() throws SQLException, IOException{        
        Client client = new Client();        
        client.setClientName("Alfonso");
        client.setStreet("tempst");
        client.setCity("tempcty");
        client.setProvince("tempprov");
        client.setPostalCode("tmp-pst");
        client.setHomePhone("temphome");
        client.setCellPhone("tempcell");
        client.setEmail("tempemail");
        
        int records = cDAO.addClient(client);
        log.info("Records created: " + records + " " + client.getClientName());
        Client client2 = cDAO.findClientById(client.getClientID());
        
        assertEquals("The records are not equal!", client, client2);
    }
    
    /**
     * JUnit test to check if the client throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddClientFail() throws SQLException, IOException{
        Client client = null;   
        log.info("Expected Exception: Null Client");
        cDAO.addClient(client);
        
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
    public void testAddSupplier() throws SQLException, IOException{        
        Supplier supplier = new Supplier();        
        supplier.setSupplierName("Reno Depot");
        
        int records = sDAO.addSupplier(supplier);
        log.info("Records created: " + records + " " + supplier.getSupplierName());
        Supplier supplier2 = sDAO.findSupplierById(supplier.getSupplierID());
        
        assertEquals("The records are not equal!", supplier, supplier2);
    }
    
    /**
     * JUnit test to check if the supplier throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddSupplierFail() throws SQLException, IOException{
        Supplier supplier = null;      
        log.info("Expected Exception: Null Supplier");
        sDAO.addSupplier(supplier);
        
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
    public void testAddMainDescription() throws SQLException, IOException{        
        MainDescription mainDescription = new MainDescription();        
        mainDescription.setMainDescriptionName("Auto-Maintenance");
        
        int records = mdDAO.addMainDescription(mainDescription);
        log.info("Records created: " + records + " " + mainDescription.getMainDescriptionName());
        MainDescription mainDescription2 = mdDAO.findMainDescriptionById(mainDescription.getMainDescriptionID());
        
        assertEquals("The records are not equal!", mainDescription, mainDescription2);
    }
    
    /**
     * JUnit test to check if the Main Description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddMainDescriptionFail() throws SQLException, IOException{
        MainDescription mainDescription = null;      
        log.info("Expected Exception: Null Main Description");
        mdDAO.addMainDescription(mainDescription);
        
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
    public void testAddSubDescription() throws SQLException, IOException{        
        SubDescription subDescription = new SubDescription();        
        subDescription.setSubDescriptionName("Restaurant");
        
        int records = sdDAO.addSubDescription(subDescription);
        log.info("Records created: " + records + " " + subDescription.getSubDescriptionName());
        SubDescription subDescription2 = sdDAO.findSubDescriptionById(subDescription.getSubDescriptionID());
        
        assertEquals("The records are not equal!", subDescription, subDescription2);
    }
    
    /**
     * JUnit test to check if the Sub Description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddSubDescriptionFail() throws SQLException, IOException{
        SubDescription subDescription = null;      
        log.info("Expected Exception: Null Sub Description");
        sdDAO.addSubDescription(subDescription);
        
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
    public void testAddInvoice() throws SQLException, IOException{        
        Invoice invoice = new Invoice();       
        invoice.setInvoiceDate(new CustomDate(Date.valueOf(LocalDate.now()).getTime()));
        invoice.setClient("Doctor Miller");
        invoice.setSubtotal(new BigDecimal("41.28"));
        invoice.setGst(new BigDecimal("1.000"));
        invoice.setQst(new BigDecimal("4.000"));
        invoice.setTotal(new BigDecimal("46.28"));
        invoice.setInvoiceSent(false);
        
        int records = iDAO.addInvoice(invoice);
        log.info("Records created: " + records + " " + invoice.getInvoiceID());
        Invoice invoice2 = iDAO.findInvoiceById(invoice.getInvoiceID());
        
        assertEquals("The records are not equal!", invoice, invoice2);
    }
    
    /**
     * JUnit test to check if the invoice throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddInvoiceFail() throws SQLException, IOException{
        Invoice invoice = null;  
        log.info("Expected Exception: Null Invoice");
        iDAO.addInvoice(invoice);
        
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
    public void testAddInvoiceDescription() throws SQLException, IOException{        
        InvoiceDescription invoiceDescription = new InvoiceDescription();        
        invoiceDescription.setInvoiceNumber(2017001);
        invoiceDescription.setInvoiceDescription("Description Test");       
        invoiceDescription.setPrice(BigDecimal.valueOf(0.00));   
        
        int records = idDAO.addInvoiceDescription(invoiceDescription);
        log.info("Records created: " + records + " " + invoiceDescription.getInvoiceNumber());
        InvoiceDescription invoiceDescription2 = idDAO.findInvoiceDescriptionById(invoiceDescription.getInvoiceDescriptionID());
        
        assertEquals("The records are not equal!", invoiceDescription.getInvoiceNumber(), invoiceDescription2.getInvoiceNumber());
    }
    
    /**
     * JUnit test to check if the invoice description throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddInvoiceDescriptionFail() throws SQLException, IOException{
        InvoiceDescription invoiceDescription = null;  
        log.info("Expected Exception: Null Invoice Description");
        idDAO.addInvoiceDescription(invoiceDescription);
        
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
    public void testFindExpenseByID() throws SQLException, IOException{
        Expense expense = eDAO.findExpenseById(1);
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
    public void testFindExpenseByIDFail() throws SQLException, IOException{
        Expense expense = null;
        log.info("Expected Exception: Null Expense");
        expense = eDAO.findExpenseById(expense.getExpenseID());     
        
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
    public void testFindClientByID() throws SQLException, IOException{
        Client client = cDAO.findClientById(20);
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
    public void testFindClientByIDFail() throws SQLException, IOException{
        Client client = null;
        log.info("Expected Exception: Null Client");
        client = cDAO.findClientById(client.getClientID());     
        
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
    public void testFindInvoiceByID() throws SQLException, IOException{
        Invoice invoice = iDAO.findInvoiceById(2017001);
        log.info("The invoice is: " + invoice.toString());
        
        assertEquals("Invoice found: ", new BigDecimal("26.99"), invoice.getTotal());
    }

    /**
     * JUnit test to check if the ID throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testFindInvoiceByIDFail() throws SQLException, IOException{
        Invoice invoice = null;
        log.info("Expected Exception: Null Invoice");
        invoice = iDAO.findInvoiceById(invoice.getInvoiceID());     
        
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
    public void testFindMainDescriptionByID() throws SQLException, IOException{
        MainDescription mainDescription = mdDAO.findMainDescriptionById(5);
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
    public void testFindMainDescriptionByIDFail() throws SQLException, IOException{
        MainDescription mainDescription = null;
        log.info("Expected Exception: Null Main Description");
        mainDescription = mdDAO.findMainDescriptionById(mainDescription.getMainDescriptionID());     
        
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
    public void testFindSubDescriptionByID() throws SQLException, IOException{
        SubDescription subDescription = sdDAO.findSubDescriptionById(2);
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
    public void testFindSubDescriptionByIDFail() throws SQLException, IOException{
        SubDescription subDescription = null;
        log.info("Expected Exception: Null Sub Description");
        subDescription = sdDAO.findSubDescriptionById(subDescription.getSubDescriptionID());     
        
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
    public void testFindSupplierByID() throws SQLException, IOException{
        Supplier supplier = sDAO.findSupplierById(33);
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
    public void testFindSupplierByIDFail() throws SQLException, IOException{
        Supplier supplier = null;
        log.info("Expected Exception: Null Supplier");
        supplier = sDAO.findSupplierById(supplier.getSupplierID());     
        
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
    public void testFindAllExpenses() throws SQLException, IOException{
        ObservableList<Expense> expenseList = eDAO.findAllExpenses();
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
    public void testFindAllClients() throws SQLException, IOException{
        ObservableList<Client> clientList = cDAO.findAllClients();
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
    public void testFindAllSuppliers() throws SQLException, IOException{
        ObservableList<Supplier> supplierList = sDAO.findAllSuppliers();
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
    public void testFindAllMainDescription() throws SQLException, IOException{
        ObservableList<MainDescription> mainDescriptionList = mdDAO.findAllMainDescriptions();
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
    public void testFindAllSubDescription() throws SQLException, IOException{
        ObservableList<SubDescription> subDescriptionList = sdDAO.findAllSubDescriptions();
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
    public void testFindAllInvoices() throws SQLException, IOException{
        ObservableList<Invoice> invoiceList = iDAO.findAllInvoices();
        log.info("Invoice list size: " + invoiceList.size());
        
        assertEquals("Invoices found: ", 2, invoiceList.size());
    }
    
    /**
     * JUnit test to check if the record has been updated.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000)
    public void testUpdateExpense() throws SQLException, IOException{      
        Expense expense =  eDAO.findExpenseById(1);   
        expense.setDateTime(new CustomDate(Date.valueOf("2017-12-31").getTime()));
        eDAO.updateExpense(expense);
        
        expense =  eDAO.findExpenseById(1);             
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
    public void testUpdateExpenseFail() throws SQLException, IOException{
        Expense expense = null;        
        log.info("Expected Exception: Null Expense");
        int records = eDAO.updateExpense(expense);
        
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
    public void testUpdateClient() throws SQLException, IOException{      
        Client client =  cDAO.findClientById(1);   
        client.setEmail("newtempemail");
        cDAO.updateClient(client);
        
        client =  cDAO.findClientById(1);             
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
    public void testUpdateClientFail() throws SQLException, IOException{
        Client client = null;        
        log.info("Expected Exception: Null Client");
        int records = cDAO.updateClient(client);
        
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
    public void testUpdateSupplier() throws SQLException, IOException{      
        Supplier supplier =  sDAO.findSupplierById(1);   
        supplier.setSupplierName("newsuppliername");
        sDAO.updateSupplier(supplier);
        
        supplier =  sDAO.findSupplierById(1);             
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
    public void testUpdateSupplierFail() throws SQLException, IOException{
        Supplier supplier = null;      
        log.info("Expected Exception: Null Supplier");
        int records = sDAO.updateSupplier(supplier);
        
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
    public void testUpdateSubDescription() throws SQLException, IOException{      
        SubDescription subDescription =  sdDAO.findSubDescriptionById(1);   
        subDescription.setSubDescriptionName("newsdname");
        sdDAO.updateSubDescription(subDescription);
        
        subDescription =  sdDAO.findSubDescriptionById(1);             
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
    public void testUpdateSubDescriptionFail() throws SQLException, IOException{
        SubDescription subDescription = null;      
        log.info("Expected Exception: Null Sub Description");
        int records = sdDAO.updateSubDescription(subDescription);
        
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
    public void testUpdateInvoice() throws SQLException, IOException{      
        Invoice invoice =  iDAO.findInvoiceById(2017001);  
        invoice.setTotal(BigDecimal.valueOf(25.00));
        iDAO.updateInvoice(invoice);
        
        invoice =  iDAO.findInvoiceById(2017001);             
        log.info("Invoice total should be changed: " + invoice.getTotal());

        assertEquals("Invoice total has been updated", 25.0, invoice.getTotal().doubleValue(), 0);
    }
  
    /**
     * JUnit test to check if the record that must be updated throws an exception.
     * 
     * @throws SQLException
     * @throws IOException 
     */
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testUpdateInvoiceFail() throws SQLException, IOException{
        Invoice invoice = null;        
        log.info("Expected Exception: Null Invoice");
        int records = iDAO.updateInvoice(invoice);
        
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
    public void testDeleteExpense() throws SQLException, IOException{        
        Expense expense =  eDAO.findExpenseById(1); 
        log.info("Expense shouldn't be null: " + expense.toString());
        eDAO.deleteExpense(expense.getExpenseID());
        expense =  eDAO.findExpenseById(1);            
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
    public void testDeleteExpenseFail() throws SQLException, IOException{
        Expense expense = null;   
        log.info("Expected Exception: Null Expense");
        int records = eDAO.deleteExpense(expense.getExpenseID());
        
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
    public void testDeleteClient() throws SQLException, IOException{        
        Client client =  cDAO.findClientById(1); 
        log.info("Client shouldn't be null: " + client.toString());
        cDAO.deleteClient(client.getClientID());
        client =  cDAO.findClientById(1);            
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
    public void testDeleteClientFail() throws SQLException, IOException{
        Client client = null;        
        log.info("Expected Exception: Null Client");
        int records = cDAO.deleteClient(client.getClientID());
        
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
    public void testDeleteSupplier() throws SQLException, IOException{        
        Supplier supplier =  sDAO.findSupplierById(1); 
        log.info("Supplier shouldn't be null: " + supplier.toString());
        sDAO.deleteSupplier(supplier.getSupplierID());
        supplier =  sDAO.findSupplierById(1);            
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
    public void testDeleteSupplierFail() throws SQLException, IOException{
        Supplier supplier = null;    
        log.info("Expected Exception: Null Supplier");
        int records = sDAO.deleteSupplier(supplier.getSupplierID());
        
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
    public void testDeleteSubDescription() throws SQLException, IOException{        
        SubDescription subDescription =  sdDAO.findSubDescriptionById(1); 
        log.info("Sub Description shouldn't be null: " + subDescription.toString());
        sdDAO.deleteSubDescription(subDescription.getSubDescriptionID());
        subDescription =  sdDAO.findSubDescriptionById(1);            
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
    public void testDeleteSubDescriptionFail() throws SQLException, IOException{
        SubDescription subDescription = null;        
        log.info("Expected Exception: Null Sub Description");
        int records = sdDAO.deleteSubDescription(subDescription.getSubDescriptionID());
        
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
    public void testDeleteInvoice() throws SQLException, IOException{        
        Invoice invoice = iDAO.findInvoiceById(1); 
        log.info("Invoice shouldn't be null: " + invoice.toString());
        iDAO.deleteInvoice(invoice.getInvoiceID());
        invoice =  iDAO.findInvoiceById(1);            
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
    public void testDeleteInvoiceFail() throws SQLException, IOException{
        Invoice invoice = null;        
        log.info("Expected Exception: Null Invoice");
        int records = iDAO.deleteInvoice(invoice.getInvoiceID());
        
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
    public void seedDatabase(){
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
    private String loadAsString(final String path){
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);){
            return scanner.useDelimiter("\\A").next();
        } 
        catch (IOException e){
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }
    
    private List<String> splitStatements(Reader reader, String statementDelimiter){
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try{
            String line;
            while ((line = bufferedReader.readLine()) != null){
                line = line.trim();
                if (line.isEmpty() || isComment(line)){
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)){
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } 
        catch (IOException e){
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line){
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}