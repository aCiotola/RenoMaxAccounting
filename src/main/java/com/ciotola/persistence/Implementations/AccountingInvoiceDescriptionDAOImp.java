package com.ciotola.persistence.Implementations;

import com.ciotola.entities.InvoiceDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDescriptionDAO;
import com.ciotola.properties.PropsManager;
import com.mysql.jdbc.Statement;
import java.io.IOException;
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
 * DAO implementation class which contains the InvoiceDescriptions methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingInvoiceDescriptionDAOImp implements IAccountingInvoiceDescriptionDAO{
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
    public AccountingInvoiceDescriptionDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }

    /**
     * Method which will insert the Invoice Description data into the Invoice Description Table.
     * 
     * @param invoiceDescription
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public int addInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException{
        int records = 0;
        String query = "INSERT INTO INVOICEDESCRIPTIONS(INVOICENUMBER, INVOICEDESCRIPTION, PRICE) VALUES(?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            pStmt.setInt(1, invoiceDescription.getInvoiceNumber());
            pStmt.setString(2, invoiceDescription.getInvoiceDescription());
            pStmt.setBigDecimal(3, invoiceDescription.getPrice());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    invoiceDescription.setInvoiceDescriptionID(recordNum);
                    log.debug("New record added to INVOICEDESCRIPTION: " + invoiceDescription.toString());
                }
            }
        }catch(SQLException ex){
            log.error("Exception thrown ADDING INVOICEDESCRIPTION: " + ex.getMessage());
            throw ex;
        }
        return records;
    }

    /**
     * Method which will return an Invoice Description found by ID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    public InvoiceDescription findInvoiceDescriptionById(int id) throws SQLException{
        InvoiceDescription invoiceDescription = new InvoiceDescription();
        String query = "SELECT * FROM INVOICEDESCRIPTIONS WHERE INVOICEDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    invoiceDescription = createInvoiceDescription(rs);                    
                    log.debug("Found INVOICEDESCRIPTIONS: " + invoiceDescription.getInvoiceDescription());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING INVOICEDESCRIPTIONS BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return invoiceDescription;
    }

    /**
     * Method responsible for returning the invoice description when searching by name.
     * 
     * @param name
     * @param invoiceNumber
     * @return
     * @throws SQLException 
     */
    @Override
    public InvoiceDescription findInvoiceDescriptionByName(String name, int invoiceNumber) throws SQLException{
        InvoiceDescription invoiceDescription = new InvoiceDescription();
        String query = "SELECT * FROM INVOICEDESCRIPTIONS WHERE INVOICEDESCRIPTION = ? AND INVOICENUMBER = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, name);
            pStmt.setInt(2, invoiceNumber);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    invoiceDescription = createInvoiceDescription(rs);                    
                    log.debug("Found INVOICEDESCRIPTION: " + invoiceDescription.getInvoiceDescription());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING INVOICEDESCRIPTION BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return invoiceDescription;
    }

    /**
     * Method which will return an ObservableList containing every record in the
     * Invoice Description table by Invoice number.
     * 
     * @param invoiceNumber
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<InvoiceDescription> findInvoiceDescriptionByInvoiceNumber(int invoiceNumber) throws SQLException{
        ObservableList<InvoiceDescription> invoiceDescriptionList = FXCollections.observableArrayList();
        String query = "SELECT * FROM INVOICEDESCRIPTIONS WHERE INVOICENUMBER = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, invoiceNumber);
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    InvoiceDescription invoiceDesction = createInvoiceDescription(rs);
                    invoiceDescriptionList.add(invoiceDesction);
                }
                log.debug("Found INVOICEDESCRIPTIONS: " + invoiceDescriptionList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL INVOICEDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }    
        return invoiceDescriptionList;
    }

    /**
     * Method which will update a record in the Invoice Description table.
     * 
     * @param invoiceDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException{
        int records = 0;
        String query = "UPDATE INVOICEDESCRIPTIONS SET INVOICENUMBER = ?, INVOICEDESCRIPTION = ?, PRICE = ? WHERE INVOICEDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){            
            pStmt.setInt(1, invoiceDescription.getInvoiceNumber());
            pStmt.setString(2, invoiceDescription.getInvoiceDescription());
            pStmt.setBigDecimal(3, invoiceDescription.getPrice());
            pStmt.setInt(4, invoiceDescription.getInvoiceDescriptionID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from INVOICEDESCRIPTIONS is: " + invoiceDescription.toString());
        }
        catch(SQLException ex){
            log.debug("Exception UPDATING INVOICEDESCRIPTIONS: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    /**
     * Method which will delete a record in the Invoice Description table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteInvoiceDescription(int id) throws SQLException{
        int records = 0;
        String query = "DELETE FROM INVOICEDESCRIPTIONS WHERE INVOICEDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from INVOICEDESCRIPTIONS: " + records + " id: " + id);
        }
        catch(SQLException ex){
            log.debug("Exception DELETING INVOICEDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create a Invoice Description object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private InvoiceDescription createInvoiceDescription(ResultSet rs) throws SQLException{
        InvoiceDescription invoiceDescription = new InvoiceDescription();
        invoiceDescription.setInvoiceDescriptionID(rs.getInt("INVOICEDESCRIPTIONID"));                    
        invoiceDescription.setInvoiceNumber(rs.getInt("INVOICENUMBER"));
        invoiceDescription.setInvoiceDescription(rs.getString("INVOICEDESCRIPTION"));     
        invoiceDescription.setPrice(rs.getBigDecimal("PRICE"));             
        return invoiceDescription;
    }   
}