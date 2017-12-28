package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alessandro Ciotola
 */
public interface IAccountingDAO 
{
    //create methods
    public int addExpense(Expense expense) throws SQLException;
    public int addClient(Client client) throws SQLException;
    public int addSupplier(Supplier supplier) throws SQLException;
    public int addMainDescription(MainDescription mainDescription) throws SQLException;
    public int addsubDescription(SubDescription subDescription) throws SQLException;
    public int addInvoice(Invoice invoice) throws SQLException;
    
    //read methods
    public ArrayList<Expense> findAllExpenses() throws SQLException;
    public ArrayList<Client> findAllClients() throws SQLException;
    public ArrayList<Supplier> findAllSuppliers() throws SQLException;
    public ArrayList<MainDescription> findAllMainDescriptions() throws SQLException;
    public ArrayList<SubDescription> findAllSubDescriptions() throws SQLException;
    public ArrayList<Invoice> findAllInvoices() throws SQLException;
    
    //update methods
    public int updateExpense(Expense expense) throws SQLException;
    public int updateClient(Client client) throws SQLException;
    public int updateSupplier(Supplier supplier) throws SQLException;
    public int updateMainDescription(MainDescription mainDescription) throws SQLException;
    public int updatesubDescription(SubDescription subDescription) throws SQLException;
    public int updateInvoice(Invoice invoice) throws SQLException;
    
    //delete methods
    public int deleteExpense(int id) throws SQLException;
    public int deleteClient(int id) throws SQLException;
    public int deleteSupplier(int id) throws SQLException;
    public int deleteMainDescription(int id) throws SQLException;
    public int deletesubDescription(int id) throws SQLException;
    public int deleteInvoice(int id) throws SQLException;
}