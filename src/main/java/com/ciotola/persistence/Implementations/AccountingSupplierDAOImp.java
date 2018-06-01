package com.ciotola.persistence.Implementations;

import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.Supplier;
import com.ciotola.persistence.Interfaces.IAccountingSupplierDAO;
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
 * DAO implementation class which contains the Supplier methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingSupplierDAOImp implements IAccountingSupplierDAO{
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
    public AccountingSupplierDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }

    /**
     * Method which will insert the Supplier data into the Suppliers Table.
     * 
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Override
    public int addSupplier(Supplier supplier) throws SQLException {
        int records = 0;        
        String query = "INSERT INTO SUPPLIERS(SUPPLIERNAME) VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            pStmt.setString(1, supplier.getSupplierName());

            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    supplier.setSupplierID(recordNum);
                    log.debug("New record added to SUPPLIERS: " + supplier.toString());
                }
            }            
        }catch(SQLException ex){
            log.error("Exception thrown ADDING SUPPLIERS: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    /**
     * Method which will Search the Suppliers table for a record with a certain supplierID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Supplier findSupplierById(int id) throws SQLException{
        Supplier supplier = new Supplier();
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    supplier = createSupplier(rs);                    
                    log.debug("Found SUPPLIER: " + supplier.getSupplierName());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING SUPPLIER BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return supplier;
    }

    /**
     * Method which will return an ObservableList containing every record in the Suppliers table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Supplier> findAllSuppliers() throws SQLException{
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        String query = "SELECT * FROM SUPPLIERS";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    Supplier supplier = createSupplier(rs);
                    supplierList.add(supplier);                    
                }
                log.debug("Found SUPPLIERS: " + supplierList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL SUPPLIERS: " + ex.getMessage());
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
    public Supplier findSupplierByName(String name) throws SQLException{
        Supplier supplier = new Supplier();
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()){
                if(rs.next()){                    
                    supplier = createSupplier(rs);
                    log.debug("Found SUPPLIER: " + supplier.getSupplierName());
                }
            }
        }catch(SQLException ex){
            log.error("Exception FINDING SUPPLIER BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return supplier;
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
    public ObservableList<Supplier> findSupplierLikeName(String name) throws SQLException{
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        String query = "SELECT * FROM SUPPLIERS WHERE SUPPLIERNAME LIKE ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, name + "%");
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    Supplier supplier = createSupplier(rs);
                    supplierList.add(supplier);
                }
                log.debug("Found SUPPLIERS: " + supplierList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING SUPPLIER LIKE NAME: " + ex.getMessage());
            throw ex; 
        }    
        return supplierList;
    }

    /**
     * Method which will update a record in the Suppliers table.
     * 
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateSupplier(Supplier supplier) throws SQLException{
        int records = 0;
        String query = "UPDATE SUPPLIERS SET SUPPLIERNAME = ? WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){            
            pStmt.setString(1, supplier.getSupplierName());
            pStmt.setInt(2, supplier.getSupplierID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from SUPPLIERS is: " + supplier.toString());
        }catch(SQLException ex){
            log.debug("Exception UPDATING SUPPLIERS: " + ex.getMessage());
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
    public int deleteSupplier(int id) throws SQLException{
        int records = 0;
        String query = "DELETE FROM SUPPLIERS WHERE SUPPLIERID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from SUPPLIERS: " + records + " id: " + id);
        }catch(SQLException ex){
            log.debug("Exception DELETING SUPPLIERS: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create a Supplier object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Supplier createSupplier(ResultSet rs) throws SQLException{
        Supplier supplier = new Supplier();
        supplier.setSupplierID(rs.getInt("SUPPLIERID"));                    
        supplier.setSupplierName(rs.getString("SUPPLIERNAME")); 
        return supplier;
    }  
}