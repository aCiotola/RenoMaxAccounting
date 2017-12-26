package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.PropertiesBean;
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
            log.debug("Exception thrown: " + ex.getMessage());
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
            log.debug("Exception thrown: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public int addSupplier(String name) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO CLIENTS VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setString(1, client.getClientName());
            
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
            log.debug("Exception thrown: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    @Override
    public int addMainDescription(String mainDescription) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addsubDescription(String subDescription) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Expense> findAllExpenses() throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Expense> findAllClients() throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public int updateSupplier(String name) throws SQLException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateMainDescription(String mainDescription) throws SQLException 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updatesubDescription(String subDescription) throws SQLException
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
}