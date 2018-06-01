package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.InvoiceDescription;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the InvoiceDescription methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingInvoiceDescriptionDAO {
    //Create Methods
    public int addInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException;
    
    //Read Methods
    public InvoiceDescription findInvoiceDescriptionById(int id) throws SQLException;
    public InvoiceDescription findInvoiceDescriptionByName(String name, int invoiceNumber) throws SQLException;
    public ObservableList<InvoiceDescription> findInvoiceDescriptionByInvoiceNumber(int invoiceNumber) throws SQLException; 
    
    //Update Methods
    public int updateInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException;
    
    //Delete Methods
    public int deleteInvoiceDescription(int id) throws SQLException;
}