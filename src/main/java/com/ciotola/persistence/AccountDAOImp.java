package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Suppliers;
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
    public int addSupplier(Suppliers supplier) throws SQLException 
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
    public ArrayList<Suppliers> findAllSuppliers() throws SQLException 
    {
        ArrayList<Suppliers> supplierList = new ArrayList<>();
        Suppliers supplier;
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
    public int updateExpense(Expense expense) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateClient(Client client) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateSupplier(Suppliers supplier) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateMainDescription(MainDescription mainDescription) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updatesubDescription(SubDescription subDescription) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    private Suppliers createSupplier(ResultSet rs) throws SQLException
    {
        Suppliers supplier = new Suppliers();
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
}