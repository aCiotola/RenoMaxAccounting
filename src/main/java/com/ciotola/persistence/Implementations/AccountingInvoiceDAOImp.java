package com.ciotola.persistence.Implementations;

import com.ciotola.entities.Invoice;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import com.ciotola.properties.PropsManager;
import com.ciotola.utils.CustomDate;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO implementation class which contains the Invoice methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingInvoiceDAOImp implements IAccountingInvoiceDAO{
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
    public AccountingInvoiceDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }
    
    /**
     * Method which will insert the Invoice data into the Invoices Table.
     * 
     * @param invoice
     * @return
     * @throws SQLException 
     */    
    @Override
    public int addInvoice(Invoice invoice) throws SQLException {
        int records = 0;
        String query = "INSERT INTO INVOICES(INVOICEDATE, CLIENT, SUBTOTAL, GST, QST, TOTAL, INVOICESENT, INVOICEPAID) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            pStmt.setDate(1, invoice.getInvoiceDate());
            pStmt.setString(2, invoice.getClient());
            pStmt.setBigDecimal(3, invoice.getSubtotal());
            pStmt.setBigDecimal(4, invoice.getGst());
            pStmt.setBigDecimal(5, invoice.getQst());
            pStmt.setBigDecimal(6, invoice.getTotal());
            pStmt.setBoolean(7, invoice.getInvoiceSent());
            pStmt.setString(8, invoice.getInvoicePaid());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    invoice.setInvoiceID(recordNum);
                    log.debug("New record added to INVOICES: " + invoice.toString());
                }                
            }
        }catch(SQLException ex){
            log.error("Exception thrown ADDING INVOICES: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }   

    /**
     * Method which will Search the Invoices table for a record with a certain invoiceID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Invoice findInvoiceById(int id) throws SQLException{
        Invoice Invoice = new Invoice();
        String query = "SELECT * FROM INVOICES WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    Invoice = createInvoice(rs);                    
                    log.debug("Found INVOICE: " + Invoice.getInvoiceID());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING INVOICE BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return Invoice;
    }

    /**
     * Method which will return an ObservableList containing every record in the Invoices table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Invoice> findAllInvoices() throws SQLException{
        ObservableList<Invoice> invoiceList = FXCollections.observableArrayList();
        String query = "SELECT * FROM INVOICES";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    Invoice invoice = createInvoice(rs);
                    invoiceList.add(invoice);                    
                }
                log.debug("Found INVOICES: " + invoiceList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL INVOICES: " + ex.getMessage());
            throw ex; 
        }    
        return invoiceList;
    }

    /**
     * Method which will update a record in the Invoices table.
     * 
     * @param invoice
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateInvoice(Invoice invoice) throws SQLException{
        int records = 0;
        String query = "UPDATE INVOICES SET INVOICEDATE = ?, CLIENT = ?, SUBTOTAL = ?," +
                "GST = ?, QST = ?, TOTAL = ?, INVOICESENT = ?, INVOICEPAID = ? WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){      
            pStmt.setDate(1, invoice.getInvoiceDate());
            pStmt.setString(2, invoice.getClient());
            pStmt.setBigDecimal(3, invoice.getSubtotal());
            pStmt.setBigDecimal(4, invoice.getGst());
            pStmt.setBigDecimal(5, invoice.getQst());
            pStmt.setBigDecimal(6, invoice.getTotal());
            pStmt.setBoolean(7, invoice.getInvoiceSent());
            pStmt.setString(8, invoice.getInvoicePaid());
            pStmt.setInt(9, invoice.getInvoiceID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from INVOICE is: " + invoice.toString());
        }catch(SQLException ex){
            log.debug("Exception UPDATING INVOICE: " + ex.getMessage());
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
    public int deleteInvoice(int id) throws SQLException{
        int records = 0;
        String query = "DELETE FROM INVOICES WHERE INVOICEID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from INVOICES: " + records + " id: " + id);
        }catch(SQLException ex){
            log.debug("Exception DELETING INVOICES: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create a Invoice object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Invoice createInvoice(ResultSet rs) throws SQLException{
        Invoice invoice = new Invoice();
        invoice.setInvoiceID(rs.getInt("INVOICEID"));        
        invoice.setInvoiceDate(new CustomDate(rs.getDate("INVOICEDATE").getTime()));
        invoice.setClient(rs.getString("CLIENT"));
        invoice.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        invoice.setGst(rs.getBigDecimal("GST"));
        invoice.setQst(rs.getBigDecimal("QST"));
        invoice.setTotal(rs.getBigDecimal("TOTAL"));
        invoice.setInvoiceSent(rs.getBoolean("INVOICESENT"));   
        invoice.setInvoicePaid(rs.getString("INVOICEPAID")); 
        return invoice;
    }   
}