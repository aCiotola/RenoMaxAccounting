package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.Invoice;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the Invoice methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingInvoiceDAO {
    //Create Methods
    public int addInvoice(Invoice invoice) throws SQLException;
    
    //Read Methods
    public Invoice findInvoiceById(int id) throws SQLException; 
    public ObservableList<Invoice> findAllInvoices() throws SQLException;
    
    //Update Methods
    public int updateInvoice(Invoice invoice) throws SQLException;
    
    //Delete Methods
    public int deleteInvoice(int id) throws SQLException;
}