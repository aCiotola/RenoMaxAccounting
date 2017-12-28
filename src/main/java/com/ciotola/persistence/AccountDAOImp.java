package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import com.ciotola.properties.PropsManager;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shado
 */
public class AccountDAOImp implements IAccountingDAO
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());   
    private final PropsManager pm = new PropsManager();
    private final PropertiesBean propsBean;
    private String url = "";
    private String user = "";
    private String password = "";
    
    public AccountDAOImp() throws IOException
    {
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }
    
    @Override
    public int addExpense(Expense expense) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO EXPENSES VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setDate(1, expense.getDateTime());
            pStmt.setInt(2, expense.getSupplierID());
            pStmt.setInt(3, expense.getMainDescriptionID());
            pStmt.setInt(4, expense.getSubDescriptionID());
            pStmt.setBigDecimal(5, expense.getSubtotal());
            pStmt.setBigDecimal(6, expense.getGst());
            pStmt.setBigDecimal(7, expense.getQst());
            pStmt.setBigDecimal(8, expense.getTotal());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                expense.setExpenseID(recordNum);
                log.debug("New record added to EXPENSES: " + expense.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING EXPENSE: " + ex.getMessage());
            throw ex;
        }
        return records;        
    }

    @Override
    public int addClient(Client client) throws SQLException
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO CLIENTS VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setString(1, client.getClientName());
            pStmt.setString(2, client.getStreet());
            pStmt.setString(3, client.getCity());
            pStmt.setString(4, client.getProvince());
            pStmt.setString(5, client.getPostalCode());
            pStmt.setString(6, client.getHomePhone());
            pStmt.setString(7, client.getCellPhone());
            pStmt.setString(8, client.getEmail());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                client.setClientID(recordNum);
                log.debug("New record added to CLIENTS: " + client.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING CLIENTS: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public int addSupplier(Supplier supplier) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO SUPPLIERS VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setString(1, supplier.getSupplierName());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                supplier.setSupplierID(recordNum);
                log.debug("New record added to SUPPLIERS: " + supplier.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING SUPPLIERS: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public int addMainDescription(MainDescription mainDescription) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO MAINDESCRIPTION VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setString(1, mainDescription.getMainDescriptionName());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                mainDescription.setMainDescriptionID(recordNum);
                log.debug("New record added to MAINDESCRIPTION: " + mainDescription.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING MAINDESCRIPTION: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public int addsubDescription(SubDescription subDescription) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO SUBDESCRIPTION VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setString(1, subDescription.getSubDescriptionName());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                subDescription.setSubDescriptionID(recordNum);
                log.debug("New record added to SUBDESCRIPTION: " + subDescription.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING SUBDESCRIPTION: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }
    
    @Override
    public int addInvoice(Invoice invoice) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO INVOICES VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setInt(1, invoice.getInvoiceNumber());
            pStmt.setDate(2, invoice.getInvoiceDate());
            pStmt.setInt(3, invoice.getClientID());
            pStmt.setBigDecimal(4, invoice.getSubtotal());
            pStmt.setBigDecimal(5, invoice.getGst());
            pStmt.setBigDecimal(6, invoice.getQst());
            pStmt.setBigDecimal(7, invoice.getTotal());
            pStmt.setBoolean(8, invoice.getInvoiceSent());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();)
            {
                if(rs.next())
                    recordNum = rs.getInt(1);
                invoice.setInvoiceID(recordNum);
                log.debug("New record added to INVOICES: " + invoice.toString());
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception thrown ADDING INVOICES: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public ArrayList<Expense> findAllExpenses() throws SQLException 
    {
        ArrayList<Expense> expenseList = new ArrayList<>();
        Expense expense;
        String query = "SELECT * FROM EXPENSES";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    expense = createExpense(rs);
                    expenseList.add(expense);
                    log.debug("Found EXPENSES: " + expense.getTotal());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL EXPENSES: " + ex.getMessage());
            throw ex; 
        }    
        return expenseList;
    }

    @Override
    public ArrayList<Client> findAllClients() throws SQLException
    {
        ArrayList<Client> clientList = new ArrayList<>();
        Client client;
        String query = "SELECT * FROM CLIENTS";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    client = createClient(rs);
                    clientList.add(client);
                    log.debug("Found CLIENTS: " + client.getClientName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL CLIENTS: " + ex.getMessage());
            throw ex; 
        }    
        return clientList;
    }
    
    @Override
    public ArrayList<Supplier> findAllSuppliers() throws SQLException 
    {
        ArrayList<Supplier> supplierList = new ArrayList<>();
        Supplier supplier;
        String query = "SELECT * FROM SUPPLIERS";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    supplier = createSupplier(rs);
                    supplierList.add(supplier);
                    log.debug("Found SUPPLIERS: " + supplier.getSupplierName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL SUPPLIERS: " + ex.getMessage());
            throw ex; 
        }    
        return supplierList;
    }

    @Override
    public ArrayList<MainDescription> findAllMainDescriptions() throws SQLException 
    {
        ArrayList<MainDescription> mainDescriptionList = new ArrayList<>();
        MainDescription mainDescription;
        String query = "SELECT * FROM MAINDESCRIPTION";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    mainDescription = createMainDescription(rs);
                    mainDescriptionList.add(mainDescription);
                    log.debug("Found MAINDESCRIPTIONS: " + mainDescription.getMainDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL MAINDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescriptionList;
    }

    @Override
    public ArrayList<SubDescription> findAllSubDescriptions() throws SQLException
    {
        ArrayList<SubDescription> subDescriptionList = new ArrayList<>();
        SubDescription subDescription;
        String query = "SELECT * FROM SUBDESCRIPTION";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    subDescription = createSubDescription(rs);
                    subDescriptionList.add(subDescription);
                    log.debug("Found SUBDESCRIPTIONS: " + subDescription.getSubDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL SUBDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }    
        return subDescriptionList;
    }
    
    @Override
    public ArrayList<Invoice> findAllInvoices() throws SQLException 
    {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        Invoice invoice;
        String query = "SELECT * FROM INVOICES";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                while(rs.next())
                {                    
                    invoice = createInvoice(rs);
                    invoiceList.add(invoice);
                    log.debug("Found INVOICES: " + invoice.getInvoiceNumber());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING ALL INVOICES: " + ex.getMessage());
            throw ex; 
        }    
        return invoiceList;
    }

    @Override
    public int updateExpense(Expense expense) throws SQLException
    {
        int records;
        String query = "UPDATE EXPENSES SET DATETIME = ?, SUPPLIERID = ?, MAINDESCRIPTIONID = ?," +
            "SUBDESCRIPTIONID = ?, SUBTOTAL = ?, GST = ?, QST = ?, TOTAL = ? WHERE EXPENSEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setDate(1, expense.getDateTime());
            pStmt.setInt(2, expense.getSupplierID());
            pStmt.setInt(3, expense.getMainDescriptionID());
            pStmt.setInt(4, expense.getSubDescriptionID());
            pStmt.setBigDecimal(5, expense.getSubtotal());
            pStmt.setBigDecimal(6, expense.getGst());
            pStmt.setBigDecimal(7, expense.getQst());
            pStmt.setBigDecimal(8, expense.getTotal());
            pStmt.setInt(9, expense.getExpenseID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from EXPENSES is: " + expense.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING EXPENSES: " + ex.getMessage());
            throw ex;
        }   
        return records;
    }

    @Override
    public int updateClient(Client client) throws SQLException
    {
        int records;
        String query = "UPDATE CLIENTS SET CLIENTNAME = ?, STREET = ?, CITY = ?, PROVINCE = ?." +
                "POSTALCODE = ?, HOMEPHONE = ?, CELLPHONE = ?, EMAIL = ? WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setString(1, client.getClientName());
            pStmt.setString(2, client.getStreet());
            pStmt.setString(3, client.getCity());
            pStmt.setString(4, client.getProvince());
            pStmt.setString(5, client.getProvince());
            pStmt.setString(6, client.getHomePhone());
            pStmt.setString(7, client.getCellPhone());
            pStmt.setString(8, client.getEmail());
            pStmt.setInt(9, client.getClientID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from CLIENTS is: " + client.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING CLIENTS: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    @Override
    public int updateSupplier(Supplier supplier) throws SQLException
    {
        int records;
        String query = "UPDATE SUPPLIERS SET SUPPLIERNAME = ? WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setString(1, supplier.getSupplierName());
            pStmt.setInt(2, supplier.getSupplierID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from SUPPLIERS is: " + supplier.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING SUPPLIERS: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    @Override
    public int updateMainDescription(MainDescription mainDescription) throws SQLException 
    {
        int records;
        String query = "UPDATE MAINDESCRIPTION SET MAINDESCRIPTIONNAME = ? WHERE MAINDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setString(1, mainDescription.getMainDescriptionName());
            pStmt.setInt(2, mainDescription.getMainDescriptionID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from MAINDESCRIPTION is: " + mainDescription.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING MAINDESCRIPTION: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    @Override
    public int updatesubDescription(SubDescription subDescription) throws SQLException
    {
        int records; 
        String query = "UPDATE SUBDESCRIPTION SET SUBDESCRIPTIONNAME = ? WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setString(1, subDescription.getSubDescriptionName());
            pStmt.setInt(2, subDescription.getSubDescriptionID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from SUBDESCRIPTION is: " + subDescription.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING SUBDESCRIPTION: " + ex.getMessage());
            throw ex;
        }   
        return records;
    }
    
    @Override
    public int updateInvoice(Invoice invoice) throws SQLException
    {
        int records;
        String query = "UPDATE INVOICES SET CLIENTNAME = ?, STREET = ?, CITY = ?, PROVINCE = ?." +
                "POSTALCODE = ?, HOMEPHONE = ?, CELLPHONE = ?, EMAIL = ? WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setInt(1, invoice.getInvoiceNumber());
            pStmt.setDate(2, invoice.getInvoiceDate());
            pStmt.setInt(3, invoice.getClientID());
            pStmt.setBigDecimal(4, invoice.getSubtotal());
            pStmt.setBigDecimal(5, invoice.getGst());
            pStmt.setBigDecimal(6, invoice.getQst());
            pStmt.setBigDecimal(7, invoice.getTotal());
            pStmt.setBoolean(8, invoice.getInvoiceSent());
            pStmt.setInt(9, invoice.getInvoiceID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from INVOICE is: " + invoice.toString());
        }
        catch(SQLException ex)
        {
            log.debug("Exception UPDATING INVOICE: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    @Override
    public int deleteExpense(int id) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteClient(int id) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteSupplier(int id) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteMainDescription(int id) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deletesubDescription(int id) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int deleteInvoice(int id) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Expense createExpense(ResultSet rs) throws SQLException
    {
        Expense expense = new Expense();
        expense.setExpenseID(rs.getInt("EXPENSEID"));                    
        expense.setDateTime(rs.getDate("DATETIME"));
        expense.setSupplierID(rs.getInt("SUPPLIERID"));
        expense.setMainDescriptionID(rs.getInt("MAINDESCRIPTIONID"));
        expense.setSubDescriptionID(rs.getInt("SUBDESCRIPTIONID"));
        expense.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        expense.setGst(rs.getBigDecimal("GST"));
        expense.setQst(rs.getBigDecimal("QST"));        
        expense.setTotal(rs.getBigDecimal("TOTAL"));  
        return expense;
    }
    
    private Client createClient(ResultSet rs) throws SQLException
    {
        Client client = new Client();
        client.setClientID(rs.getInt("CLIENTID"));                    
        client.setClientName(rs.getString("CLIENTNAME"));
        client.setStreet(rs.getString("STREET"));
        client.setCity(rs.getString("CITY"));
        client.setProvince(rs.getString("PROVINCE"));
        client.setPostalCode(rs.getString("POSTALCODE"));
        client.setHomePhone(rs.getString("HOMEPHONE"));
        client.setCellPhone(rs.getString("CELLPHONE"));        
        client.setEmail(rs.getString("EMAIL"));  
        return client;
    }    
    
    private Supplier createSupplier(ResultSet rs) throws SQLException
    {
        Supplier supplier = new Supplier();
        supplier.setSupplierID(rs.getInt("SUPPLIERID"));                    
        supplier.setSupplierName(rs.getString("SUPPLIERNAME")); 
        return supplier;
    }  
    
    private MainDescription createMainDescription(ResultSet rs) throws SQLException
    {
        MainDescription mainDescription = new MainDescription();
        mainDescription.setMainDescriptionID(rs.getInt("MAINDESCRIPTIONID"));                    
        mainDescription.setMainDescriptionName(rs.getString("MAINDESCRIPTIONNAME")); 
        return mainDescription;
    }  
    
    private SubDescription createSubDescription(ResultSet rs) throws SQLException
    {
        SubDescription subDescription = new SubDescription();
        subDescription.setSubDescriptionID(rs.getInt("SUBDESCRIPTIONID"));                    
        subDescription.setSubDescriptionName(rs.getString("SUBDESCRIPTIONNAME")); 
        return subDescription;
    }  
    
    private Invoice createInvoice(ResultSet rs) throws SQLException
    {
        Invoice invoice = new Invoice();
        invoice.setInvoiceID(rs.getInt("INVOICEID"));                    
        invoice.setInvoiceNumber(rs.getInt("INVOICENUMBER"));
        invoice.setInvoiceDate(rs.getDate("INVOICEDATE"));  
        invoice.setClientID(rs.getInt("CLIENTID"));
        invoice.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        invoice.setGst(rs.getBigDecimal("GST"));
        invoice.setQst(rs.getBigDecimal("QST"));
        invoice.setTotal(rs.getBigDecimal("TOTAL"));
        invoice.setInvoiceSent(rs.getBoolean("INVOICESENT"));            
        return invoice;
    }   
}