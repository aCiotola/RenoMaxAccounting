package com.ciotola.tests;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.PropertiesBean;
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
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author shado
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

    public RenomaxDBTests() throws IOException 
    {
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        url = this.url.replace("\\", "");
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }
    
    @Test(timeout = 1000)
    public void testAddExpense() throws SQLException, IOException 
    {        
        Expense expense = new Expense();        
        expense.setDateTime(Date.valueOf(LocalDate.now()));
        expense.setSupplierID(1);
        expense.setMainDescriptionID(1);
        expense.setSubDescriptionID(1);
        expense.setSubtotal(new BigDecimal("25.00"));
        expense.setGst(new BigDecimal("2.000"));
        expense.setQst(new BigDecimal("2.000"));
        expense.setTotal(new BigDecimal("29.00"));
        
        int records = accountDAO.addExpense(expense);
        log.info("Records created: " + records + " " + expense.getExpenseID());
        Expense expense2 = accountDAO.findExpenseById(expense.getExpenseID());
        
        assertEquals("The records are not equal!", expense, expense2);
    }
    
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddExpenseFail() throws SQLException, IOException  
    {
        Expense expense = null;        
        accountDAO.addExpense(expense);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    @Test(timeout = 1000)
    public void testAddClient() throws SQLException, IOException 
    {        
        Client client = new Client();        
        client.setClientName("Alfonso");
        client.setStreet("tempst");
        client.setCity("tempcty");
        client.setProvince("tempprov");
        client.setPostalCode("temppost");
        client.setHomePhone("temphome");
        client.setCellPhone("tempcell");
        client.setEmail("tempemail");
        
        int records = accountDAO.addClient(client);
        log.info("Records created: " + records + " " + client.getClientName());
        Client client2 = accountDAO.findClientById(client.getClientID());
        
        assertEquals("The records are not equal!", client, client2);
    }
    
    @Test(timeout = 1000, expected = NullPointerException.class)
    public void testAddClientFail() throws SQLException, IOException  
    {
        Client client = null;        
        accountDAO.addClient(client);
        
        // If an exception was not thrown then the test failed
        fail("The Object that was null did not throw an exception");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Before
    public void createVariables() throws IOException
    {
        accountDAO = new AccountingDAOImp();
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
