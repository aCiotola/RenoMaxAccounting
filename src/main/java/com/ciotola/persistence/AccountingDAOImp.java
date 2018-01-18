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
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO implementation class which contains the methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/10
 * 
 */
public class AccountingDAOImp implements IAccountingDAO
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());   
    private final PropsManager pm = new PropsManager();
    private final PropertiesBean propsBean;
    private String url = "";
    private String user = "";
    private String password = "";
    
    /**
     * No parameter constructor which gets the database information from the
     * properties bean and sets the database variables.
     * 
     * @throws IOException 
     */
    public AccountingDAOImp() throws IOException
    {
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }
    
    /**
     * Method which will insert the expense data into the Expenses Table.
     * 
     * @param expense
     * @return number of records added.
     * @throws SQLException 
     */
    @Override
    public int addExpense(Expense expense) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
          
        String query = "INSERT INTO EXPENSES(EXPENSENUMBER, DATETIME, SUPPLIER, MAINDESCRIPTION, SUBDESCRIPTION, SUBTOTAL, GST, QST, TOTAL)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {            
            pStmt.setInt(1, getLastExpenseNumber());
            pStmt.setDate(2, expense.getDateTime());
            pStmt.setString(3, expense.getSupplier());
            pStmt.setString(4, expense.getMainDescription());
            pStmt.setString(5, expense.getSubDescription());
            pStmt.setBigDecimal(6, expense.getSubtotal());
            pStmt.setBigDecimal(7, expense.getGst());
            pStmt.setBigDecimal(8, expense.getQst());
            pStmt.setBigDecimal(9, expense.getTotal());

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

    /**
     * Method which will insert the client data into the Clients Table.
     * 
     * @param client
     * @return
     * @throws SQLException 
     */
    @Override
    public int addClient(Client client) throws SQLException
    {
        int records = -1;
        int recordNum = -1;
        
        String query = "INSERT INTO CLIENTS(CLIENTNAME, STREET, CITY, PROVINCE, POSTALCODE, HOMEPHONE, CELLPHONE, EMAIL) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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

    /**
     * Method which will insert the Supplier data into the Suppliers Table.
     * 
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Override
    public int addSupplier(Supplier supplier) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        
        String query = "INSERT INTO SUPPLIERS(SUPPLIERNAME) VALUES(?)";
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

    /**
     * Method which will insert the Main Description data into the Main Descriptions Table.
     * 
     * @param mainDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int addMainDescription(MainDescription mainDescription) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO MAINDESCRIPTION(MAINDESCRIPTIONNAME) VALUES(?)";
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

    /**
     * Method which will insert the Sub Description data into the Sub Descriptions Table.
     * 
     * @param subDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int addSubDescription(SubDescription subDescription) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO SUBDESCRIPTION(SUBDESCRIPTIONNAME) VALUES(?)";
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
    
    /**
     * Method which will insert the Invoice data into the Invoices Table.
     * 
     * @param invoice
     * @return
     * @throws SQLException 
     */    
    @Override
    public int addInvoice(Invoice invoice) throws SQLException 
    {
        int records = -1;
        int recordNum = -1;
        String query = "INSERT INTO INVOICES(INVOICENUMBER, INVOICEDATE, CLIENT, SUBTOTAL, GST, QST, TOTAL, INVOICESENT) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
        {
            pStmt.setInt(1, invoice.getInvoiceNumber());
            pStmt.setDate(2, invoice.getInvoiceDate());
            pStmt.setString(3, invoice.getClient());
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
    
    /**
     * Method which will collect all of the totals in the Expense table and
     * return the total amount of expenses.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public double calculateExpenseTotal() throws SQLException
    {
        double total = 0;
        String query = "SELECT SUM(TOTAL) as rTotal FROM EXPENSES";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {             
                    total = rs.getDouble("rTotal");
                    log.debug("Calcluated TOTAL EXPENSE: " + total);
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception CALCULATING TOTAL EXPENSE: " + ex.getMessage());
            throw ex; 
        }    
        return total;
    }

    /**
     * Method which will Search the Expenses table for a record with a certain expenseID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Expense findExpenseById(int id) throws SQLException
    {
        Expense expense = new Expense();
        String query = "SELECT * FROM EXPENSES WHERE EXPENSEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    expense = createExpense(rs);                    
                    log.debug("Found EXPENSE: " + expense.getExpenseID());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING EXPENSE BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return expense;
    }
    
    /**
     * Method which will Search the Clients table for a record with a certain clientID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Client findClientById(int id) throws SQLException
    {
        Client client = new Client();
        String query = "SELECT * FROM CLIENTS WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    client = createClient(rs);                    
                    log.debug("Found CLIENT: " + client.getClientName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING CLIENT BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return client;
    }
    
    /**
     * Method which will Search the Invoices table for a record with a certain invoiceID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Invoice findInvoiceById(int id) throws SQLException
    {
        Invoice Invoice = new Invoice();
        String query = "SELECT * FROM INVOICES WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    Invoice = createInvoice(rs);                    
                    log.debug("Found INVOICE: " + Invoice.getInvoiceNumber());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING INVOICE BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return Invoice;
    }
    
    /**
     * Method which will Search the Main Descriptions table for a record with a certain mainDescriptionID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public MainDescription findMainDescriptionById(int id) throws SQLException
    {
        MainDescription mainDescription = new MainDescription();
        String query = "SELECT * FROM MAINDESCRIPTION WHERE MAINDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    mainDescription = createMainDescription(rs);                    
                    log.debug("Found MAINDESCRIPTION: " + mainDescription.getMainDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING MAINDESCRIPTION BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescription;
    }
    
    /**
     * Method which will Search the Sub Descriptions table for a record with a certain subDescriptionID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public SubDescription findSubDescriptionById(int id) throws SQLException
    {
        SubDescription subDescription = new SubDescription();
        String query = "SELECT * FROM SUBDESCRIPTION WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    subDescription = createSubDescription(rs);                    
                    log.debug("Found SUBDESCRIPTION: " + subDescription.getSubDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING SUBDESCRIPTION BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return subDescription;
    }
    
    /**
     * Method which will Search the Suppliers table for a record with a certain supplierID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Supplier findSupplierById(int id) throws SQLException
    {
        Supplier supplier = new Supplier();
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    supplier = createSupplier(rs);                    
                    log.debug("Found SUPPLIER: " + supplier.getSupplierName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING SUPPLIER BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return supplier;
    }
    
    /**
     * Method which will return an ObservableList containing every record in the Expenses table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Expense> findAllExpenses() throws SQLException 
    {
        ObservableList<Expense> expenseList = FXCollections.observableArrayList();
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

    /**
     * Method which will return an ObservableList containing every record in the Clients table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Client> findAllClients() throws SQLException
    {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Client client;
        String query = "SELECT * FROM CLIENTS ORDER BY CLIENTNAME ASC";
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
    
    /**
     * Method which will return an ObservableList containing every record in the Suppliers table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Supplier> findAllSuppliers() throws SQLException 
    {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
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

    /**
     * Method which will return an ObservableList containing every record in the Main Descriptions table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<MainDescription> findAllMainDescriptions() throws SQLException 
    {
        ObservableList<MainDescription> mainDescriptionList = FXCollections.observableArrayList();
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

    /**
     * Method which will return an ObservableList containing every record in the Sub Descriptions table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<SubDescription> findAllSubDescriptions() throws SQLException
    {
        ObservableList<SubDescription> subDescriptionList = FXCollections.observableArrayList();
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
    
    /**
     * Method which will return an ObservableList containing every record in the Invoices table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Invoice> findAllInvoices() throws SQLException 
    {
        ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();
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
    
    /**
     * Method which will return an ObservableList containing every record in the Clients table
     * which has a similar name to the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Client> findClientLikeName(String name) throws SQLException
    {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Client client;
        String query = "SELECT * FROM CLIENTS WHERE CLIENTNAME LIKE ? ORDER BY CLIENTNAME ASC";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, "%" + name + "%");
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
            log.error("Exception FINDING CLIENT LIKE NAME: " + ex.getMessage());
            throw ex; 
        }    
        return clientList;
    }
    
    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public Client findClientByName(String name) throws SQLException
    {
        Client client = new Client();
        String query = "SELECT * FROM CLIENTS WHERE CLIENTNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if(rs.next())
                {                    
                    client = createClient(rs);
                    log.debug("Found CLIENT: " + client.getClientName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING CLIENT BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return client;
    }
    
    /**
     * Method which will return an ObservableList containing every record in the Clients table
     * which has a similar name to the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Supplier> findSupplierLikeName(String name) throws SQLException
    {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        Supplier supplier;
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERNAME LIKE ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, name + "%");
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
            log.error("Exception FINDING SUPPLIER LIKE NAME: " + ex.getMessage());
            throw ex; 
        }    
        return supplierList;
    }
    
    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public Supplier findSupplierByName(String name) throws SQLException
    {
        Supplier supplier = new Supplier();
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if(rs.next())
                {                    
                    supplier = createSupplier(rs);
                    log.debug("Found SUPPLIER: " + supplier.getSupplierName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING SUPPLIER BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return supplier;
    }
    
    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public MainDescription findMainDescriptionByName(String name) throws SQLException
    {
        MainDescription mainDescription = new MainDescription();
        String query = "SELECT * FROM MAINDESCRIPTION WHERE MAINDESCRIPTIONNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if(rs.next())
                {                    
                    mainDescription = createMainDescription(rs);
                    log.debug("Found MAIN DESCRIPTION: " + mainDescription.getMainDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING MAIN DESCRIPTION BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescription;
    }
    
    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public SubDescription findSubDescriptionByName(String name) throws SQLException
    {
        SubDescription subDescription = new SubDescription();
        String query = "SELECT * FROM SUBDESCRIPTION WHERE SUBDESCRIPTIONNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if(rs.next())
                {                    
                    subDescription = createSubDescription(rs);
                    log.debug("Found SUB DESCRIPTION: " + subDescription.getSubDescriptionName());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING SUB DESCRIPTION BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return subDescription;
    }
    
    /**
     * Method which will return the record which contains the given expense number.
     * 
     * @param expenseNumber
     * @return
     * @throws SQLException 
     */
    @Override
    public Expense findExpenseByNumber(int expenseNumber) throws SQLException
    {
        Expense expense = new Expense();
        String query = "SELECT * FROM EXPENSES WHERE EXPENSENUMBER = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, expenseNumber);
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if(rs.next())
                {                    
                    expense = createExpense(rs);
                    log.debug("Found EXPENSE: " + expense.getTotal());
                }
            }
        }
        catch(SQLException ex)
        {
            log.error("Exception FINDING EXCPENSE BY NUMBER: " + ex.getMessage());
            throw ex; 
        }    
        return expense;
    }

    /**
     * Method which will update a record in the Expenses table.
     * 
     * @param expense
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateExpense(Expense expense) throws SQLException
    {
        int records;
        String query = "UPDATE EXPENSES SET EXPENSENUMBER = ?, DATETIME = ?, SUPPLIER = ?, MAINDESCRIPTION = ?," +
            "SUBDESCRIPTION = ?, SUBTOTAL = ?, GST = ?, QST = ?, TOTAL = ? WHERE EXPENSEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setInt(1, expense.getExpenseNumber());
            pStmt.setDate(2, expense.getDateTime());
            pStmt.setString(3, expense.getSupplier());
            pStmt.setString(4, expense.getMainDescription());
            pStmt.setString(5, expense.getSubDescription());
            pStmt.setBigDecimal(6, expense.getSubtotal());
            pStmt.setBigDecimal(7, expense.getGst());
            pStmt.setBigDecimal(8, expense.getQst());
            pStmt.setBigDecimal(9, expense.getTotal());
            pStmt.setInt(10, expense.getExpenseID());
            
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

    /**
     * Method which will update a record in the Clients table.
     * 
     * @param client
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateClient(Client client) throws SQLException
    {
        int records;
        String query = "UPDATE CLIENTS SET CLIENTNAME = ?, STREET = ?, CITY = ?, PROVINCE = ?, " +
                "POSTALCODE = ?, HOMEPHONE = ?, CELLPHONE = ?, EMAIL = ? WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setString(1, client.getClientName());
            pStmt.setString(2, client.getStreet());
            pStmt.setString(3, client.getCity());
            pStmt.setString(4, client.getProvince());
            pStmt.setString(5, client.getPostalCode());
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

    /**
     * Method which will update a record in the Suppliers table.
     * 
     * @param supplier
     * @return
     * @throws SQLException 
     */
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

    /**
     * Method which will update a record in the Main Descriptions table.
     * 
     * @param mainDescription
     * @return
     * @throws SQLException 
     */
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

    /**
     * Method which will update a record in the Sub Descriptions table.
     * 
     * @param subDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateSubDescription(SubDescription subDescription) throws SQLException
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
    
    /**
     * Method which will update a record in the Invoices table.
     * 
     * @param invoice
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateInvoice(Invoice invoice) throws SQLException
    {
        int records;
        String query = "UPDATE INVOICES SET INVOICENUMBER = ?, INVOICEDATE = ?, CLIENT = ?, SUBTOTAL = ?," +
                "GST = ?, QST = ?, TOTAL = ?, INVOICESENT = ? WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {            
            pStmt.setInt(1, invoice.getInvoiceNumber());
            pStmt.setDate(2, invoice.getInvoiceDate());
            pStmt.setString(3, invoice.getClient());
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

    /**
     * Method which will delete a record in the Expenses table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteExpense(int id) throws SQLException 
    {
        int records;
        String query = "DELETE FROM EXPENSES WHERE EXPENSEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from EXPENSES: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING EXPENSES: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }

    /**
     * Method which will delete a record in the Clients table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteClient(int id) throws SQLException
    {
        int records;
        String query = "DELETE FROM CLIENTS WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from CLIENTS: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING CLIENTS: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }

    /**
     * Method which will delete a record in the Suppliers table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteSupplier(int id) throws SQLException
    {
        int records;
        String query = "DELETE FROM SUPPLIERS WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from SUPPLIERS: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING SUPPLIERS: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }

    /**
     * Method which will delete a record in the Main Descriptions table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteMainDescription(int id) throws SQLException 
    {
        int records;
        String query = "DELETE FROM MAINDESCRIPTION WHERE MAINDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from MAINDESCRIPTIONNAME: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING MAINDESCRIPTIONNAME: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }

    /**
     * Method which will delete a record in the Sub Descriptions table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteSubDescription(int id) throws SQLException 
    {
        int records;
        String query = "DELETE FROM SUBDESCRIPTION WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from SUBDESCRIPTION: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING SUBDESCRIPTION: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will delete a record in the Invoices table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteInvoice(int id) throws SQLException 
    {
        int records;
        String query = "DELETE FROM INVOICES WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from INVOICES: " + records + " id: " + id);
        }
        catch(SQLException ex)
        {
            log.debug("Exception DELETING INVOICES: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create an Expense object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Expense createExpense(ResultSet rs) throws SQLException
    {
        Expense expense = new Expense();
        expense.setExpenseID(rs.getInt("EXPENSEID"));     
        expense.setExpenseNumber(rs.getInt("EXPENSENUMBER"));         
        expense.setDateTime(rs.getDate("DATETIME"));
        expense.setSupplier(rs.getString("SUPPLIER"));
        expense.setMainDescription(rs.getString("MAINDESCRIPTION"));
        expense.setSubDescription(rs.getString("SUBDESCRIPTION"));
        expense.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        expense.setGst(rs.getBigDecimal("GST"));
        expense.setQst(rs.getBigDecimal("QST"));        
        expense.setTotal(rs.getBigDecimal("TOTAL"));  
        return expense;
    }
    
    /**
     * Method which will take data from the ResultSet and create a Client object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
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
    
    /**
     * Method which will take data from the ResultSet and create a Supplier object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Supplier createSupplier(ResultSet rs) throws SQLException
    {
        Supplier supplier = new Supplier();
        supplier.setSupplierID(rs.getInt("SUPPLIERID"));                    
        supplier.setSupplierName(rs.getString("SUPPLIERNAME")); 
        return supplier;
    }  
    
    /**
     * Method which will take data from the ResultSet and create a MainDescription object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private MainDescription createMainDescription(ResultSet rs) throws SQLException
    {
        MainDescription mainDescription = new MainDescription();
        mainDescription.setMainDescriptionID(rs.getInt("MAINDESCRIPTIONID"));                    
        mainDescription.setMainDescriptionName(rs.getString("MAINDESCRIPTIONNAME")); 
        return mainDescription;
    }  
    
    /**
     * Method which will take data from the ResultSet and create a SubDescription object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private SubDescription createSubDescription(ResultSet rs) throws SQLException
    {
        SubDescription subDescription = new SubDescription();
        subDescription.setSubDescriptionID(rs.getInt("SUBDESCRIPTIONID"));                    
        subDescription.setSubDescriptionName(rs.getString("SUBDESCRIPTIONNAME")); 
        return subDescription;
    }  
    
    /**
     * Method which will take data from the ResultSet and create a Invoice object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Invoice createInvoice(ResultSet rs) throws SQLException
    {
        Invoice invoice = new Invoice();
        invoice.setInvoiceID(rs.getInt("INVOICEID"));                    
        invoice.setInvoiceNumber(rs.getInt("INVOICENUMBER"));
        invoice.setInvoiceDate(rs.getDate("INVOICEDATE"));  
        invoice.setClient(rs.getString("CLIENT"));
        invoice.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        invoice.setGst(rs.getBigDecimal("GST"));
        invoice.setQst(rs.getBigDecimal("QST"));
        invoice.setTotal(rs.getBigDecimal("TOTAL"));
        invoice.setInvoiceSent(rs.getBoolean("INVOICESENT"));            
        return invoice;
    }   
    
    /**
     * Method which will increment the expense number when a new expense is created.
     * 
     * @return
     * @throws SQLException 
     */
    private int getLastExpenseNumber() throws SQLException
    {
        int expenseNumber = 0;
        String query = "SELECT MAX(EXPENSENUMBER) as MAX_ENUMBER FROM EXPENSES";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);)          
        {
            try (ResultSet rs = pStmt.executeQuery()) 
            {
                if (rs.next())
                {                    
                    expenseNumber = rs.getInt("MAX_ENUMBER");
                    log.debug("Found EXPENSE NUMBER: " + expenseNumber);
                }
            }
        }
        catch(SQLException ex)
        {
            log.debug("Exception FINDING EXPENSE NUMBER: " + ex.getMessage());
            throw ex; 
        }    
        return expenseNumber + 1;
    }
}