package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/13
 * 
 */
public interface IAccountingDAO 
{
    //create methods
    public int addExpense(Expense expense) throws SQLException;
    public int addClient(Client client) throws SQLException;
    public int addSupplier(Supplier supplier) throws SQLException;
    public int addMainDescription(MainDescription mainDescription) throws SQLException;
    public int addSubDescription(SubDescription subDescription) throws SQLException;
    public int addInvoice(Invoice invoice) throws SQLException;
    public int addInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException;
    
    //read methods
    public Expense findExpenseById(int id) throws SQLException; 
    public Client findClientById(int id) throws SQLException; 
    public Invoice findInvoiceById(int id) throws SQLException; 
    public MainDescription findMainDescriptionById(int id) throws SQLException; 
    public SubDescription findSubDescriptionById(int id) throws SQLException; 
    public Supplier findSupplierById(int id) throws SQLException; 
    public ObservableList<Expense> findAllExpenses() throws SQLException;
    public ObservableList<Client> findAllClients() throws SQLException;
    public ObservableList<Supplier> findAllSuppliers() throws SQLException;
    public ObservableList<MainDescription> findAllMainDescriptions() throws SQLException;
    public ObservableList<SubDescription> findAllSubDescriptions() throws SQLException;
    public ObservableList<Invoice> findAllInvoices() throws SQLException;
    public ObservableList<Client> findClientLikeName(String name) throws SQLException;
    public Expense findExpenseByNumber(int expenseNum) throws SQLException;
    public Client findClientByName(String name) throws SQLException;
    public Supplier findSupplierByName(String name) throws SQLException;
    public ObservableList<Supplier> findSupplierLikeName(String name) throws SQLException;
    public MainDescription findMainDescriptionByName(String name) throws SQLException;
    public SubDescription findSubDescriptionByName(String name) throws SQLException;
    public InvoiceDescription findInvoiceDescriptionById(int id) throws SQLException;
    public int findNewInvoiceNumber() throws SQLException;
    public ObservableList<InvoiceDescription> findInvoiceDescriptionByInvoiceNumber(int invoiceNumber) throws SQLException;   
    
    //update methods
    public int updateExpense(Expense expense) throws SQLException;
    public int updateClient(Client client) throws SQLException;
    public int updateSupplier(Supplier supplier) throws SQLException;
    public int updateMainDescription(MainDescription mainDescription) throws SQLException;
    public int updateSubDescription(SubDescription subDescription) throws SQLException;
    public int updateInvoice(Invoice invoice) throws SQLException;
    public int updateInvoiceDescription(InvoiceDescription invoiceDescription) throws SQLException;
    
    //delete methods
    public int deleteExpense(int id) throws SQLException;
    public int deleteClient(int id) throws SQLException;
    public int deleteSupplier(int id) throws SQLException;
    public int deleteMainDescription(int id) throws SQLException;
    public int deleteSubDescription(int id) throws SQLException;
    public int deleteInvoice(int id) throws SQLException;
    public int deleteInvoiceDescription(int id) throws SQLException;
}