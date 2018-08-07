package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.Expense;
import java.sql.Date;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the Expense methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingExpenseDAO {
    //Create Methods
    public int addExpense(Expense expense) throws SQLException;
    
    //Read Methods
    public Expense findExpenseById(int id) throws SQLException; 
    public ObservableList<Expense> findAllExpenses() throws SQLException; 
    public ObservableList<Expense> findExpensesByMainDescription(String md) throws SQLException;
    public ObservableList<Expense> findExpensesBetweenDate(Date from, Date to) throws SQLException;
    
    //Update Methods
    public int updateExpense(Expense expense) throws SQLException;
    
    //Delete Methods
    public int deleteExpense(int id) throws SQLException;
}