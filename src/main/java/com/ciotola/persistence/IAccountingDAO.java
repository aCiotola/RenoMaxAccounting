package com.ciotola.persistence;

import com.ciotola.entities.Client;
import com.ciotola.entities.Expense;
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
    public int addSupplier(String name) throws SQLException;
    public int addMainDescription(String mainDescription) throws SQLException;
    public int addsubDescription(String subDescription) throws SQLException;
    
    //read methods
    public ArrayList<Expense> findAllExpenses() throws SQLException;
    public ArrayList<Expense> findAllClients() throws SQLException;
    
    //update methods
    public int updateExpense(Expense expense) throws SQLException;
    public int updateClient(Client client) throws SQLException;
    public int updateSupplier(String name) throws SQLException;
    public int updateMainDescription(String mainDescription) throws SQLException;
    public int updatesubDescription(String subDescription) throws SQLException;
    
    //delete methods
    public int deleteExpense(int id) throws SQLException;
    public int deleteClient(int id) throws SQLException;
    public int deleteSupplier(int id) throws SQLException;
    public int deleteMainDescription(int id) throws SQLException;
    public int deletesubDescription(int id) throws SQLException;
}