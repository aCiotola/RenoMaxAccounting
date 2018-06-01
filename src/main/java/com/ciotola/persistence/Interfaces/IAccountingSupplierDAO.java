package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.Supplier;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the Supplier methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingSupplierDAO {
    //Create Methods
    public int addSupplier(Supplier supplier) throws SQLException;
    
    //Read Methods
    public Supplier findSupplierById(int id) throws SQLException;
    public ObservableList<Supplier> findAllSuppliers() throws SQLException;
    public Supplier findSupplierByName(String name) throws SQLException;
    public ObservableList<Supplier> findSupplierLikeName(String name) throws SQLException;
    
    //Update Methods
    public int updateSupplier(Supplier supplier) throws SQLException;
    
    //Delete Methods
    public int deleteSupplier(int id) throws SQLException;
}