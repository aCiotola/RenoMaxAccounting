package com.ciotola.persistence.Implementations;

import com.ciotola.entities.Expense;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.persistence.Interfaces.IAccountingExpenseDAO;
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
 * DAO implementation class which contains the Expense methods required to
 * interact with the database. Gets database information from the properties
 * file.
 *
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingExpenseDAOImp implements IAccountingExpenseDAO {

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
    public AccountingExpenseDAOImp() throws IOException {
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }

    /**
     * Method which will insert the expense data into the Expenses Table.
     *
     * @param expense
     * @return number of records added.
     * @throws SQLException
     */
    @Override
    public int addExpense(Expense expense) throws SQLException {
        int records = 0;
        String query = "INSERT INTO EXPENSES(DATETIME, SUPPLIER, MAINDESCRIPTION, SUBDESCRIPTION, SUBTOTAL, GST, QST, TOTAL)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            pStmt.setDate(1, expense.getDateTime());
            pStmt.setString(2, expense.getSupplier());
            pStmt.setString(3, expense.getMainDescription());
            pStmt.setString(4, expense.getSubDescription());
            pStmt.setBigDecimal(5, expense.getSubtotal());
            pStmt.setBigDecimal(6, expense.getGst());
            pStmt.setBigDecimal(7, expense.getQst());
            pStmt.setBigDecimal(8, expense.getTotal());

            records = pStmt.executeUpdate();
            try (ResultSet rs = pStmt.getGeneratedKeys();) {
                if (rs.next()) {
                    int recordNum = rs.getInt(1);
                    expense.setExpenseID(recordNum);
                    log.debug("New record added to EXPENSES: " + expense.toString());
                }
            }
        } catch (SQLException ex) {
            log.error("Exception thrown ADDING EXPENSE: " + ex.getMessage());
            throw ex;
        }
        return records;
    }

    /**
     * Method which will Search the Expenses table for a record with a certain
     * expenseID.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Expense findExpenseById(int id) throws SQLException {
        Expense expense = new Expense();
        String query = "SELECT * FROM EXPENSES WHERE EXPENSEID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    expense = createExpense(rs);
                    log.debug("Found EXPENSE: " + expense.getExpenseID());
                }
            }
        } catch (SQLException ex) {
            log.debug("Exception FINDING EXPENSE BY ID: " + ex.getMessage());
            throw ex;
        }
        return expense;
    }

    /**
     * Method which will return an ObservableList containing every record in the
     * Expenses table.
     *
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Expense> findAllExpenses() throws SQLException {
        ObservableList<Expense> expenseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM EXPENSES";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = createExpense(rs);
                    expenseList.add(expense);
                }
                log.debug("Found EXPENSES: " + expenseList.size());
            }
        } catch (SQLException ex) {
            log.error("Exception FINDING ALL EXPENSES: " + ex.getMessage());
            throw ex;
        }
        return expenseList;
    }

    /**
     * Method which will return expenses by a certain MainDescription
     *
     * @param md
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Expense> findExpensesByMainDescription(String md) throws SQLException {
        ObservableList<Expense> expenseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM EXPENSES WHERE MAINDESCRIPTION = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            pStmt.setString(1, md);
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = createExpense(rs);
                    expenseList.add(expense);
                }
                log.debug("Found EXPENSES: " + expenseList.size());
            }
        } catch (SQLException ex) {
            log.error("Exception FINDING ALL EXPENSES: " + ex.getMessage());
            throw ex;
        }
        return expenseList;
    }
    
    /**
     * Method responsible for finding expenses between 2 dates.
     * 
     * @param from
     * @param to
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Expense> findExpensesBetweenDate(Date from, Date to) throws SQLException {
        ObservableList<Expense> expenseList = FXCollections.observableArrayList();
        String query = "SELECT * FROM EXPENSES WHERE DATETIME BETWEEN ? AND ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            pStmt.setDate(1, from);
            pStmt.setDate(2, to);
            try (ResultSet rs = pStmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = createExpense(rs);
                    expenseList.add(expense);
                }
                log.debug("Found EXPENSES: " + expenseList.size());
            }
        } catch (SQLException ex) {
            log.error("Exception FINDING ALL EXPENSES: " + ex.getMessage());
            throw ex;
        }
        return expenseList;
    }

    /**
     * Method which will update a record in the Expenses table.
     *
     * @param expense
     * @return
     * @throws SQLException
     */
    @Override
    public int updateExpense(Expense expense) throws SQLException {
        int records = 0;
        String query = "UPDATE EXPENSES SET DATETIME = ?, SUPPLIER = ?, MAINDESCRIPTION = ?,"
                + "SUBDESCRIPTION = ?, SUBTOTAL = ?, GST = ?, QST = ?, TOTAL = ? WHERE EXPENSEID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            pStmt.setDate(1, expense.getDateTime());
            pStmt.setString(2, expense.getSupplier());
            pStmt.setString(3, expense.getMainDescription());
            pStmt.setString(4, expense.getSubDescription());
            pStmt.setBigDecimal(5, expense.getSubtotal());
            pStmt.setBigDecimal(6, expense.getGst());
            pStmt.setBigDecimal(7, expense.getQst());
            pStmt.setBigDecimal(8, expense.getTotal());
            pStmt.setInt(9, expense.getExpenseID());

            records = pStmt.executeUpdate();
            log.debug("Record updated from EXPENSES is: " + expense.toString());
        } catch (SQLException ex) {
            log.debug("Exception UPDATING EXPENSES: " + ex.getMessage());
            throw ex;
        }
        return records;
    }

    /**
     * Method which will delete a record in the Expenses table.
     *
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteExpense(int id) throws SQLException {
        int records = 0;
        String query = "DELETE FROM EXPENSES WHERE EXPENSEID = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query);) {
            pStmt.setInt(1, id);

            records = pStmt.executeUpdate();
            log.debug("Records Deleted from EXPENSES: " + records + " id: " + id);
        } catch (SQLException ex) {
            log.debug("Exception DELETING EXPENSES: " + ex.getMessage());
            throw ex;
        }
        return records;
    }

    /**
     * Method which will take data from the ResultSet and create an Expense
     * object.
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Expense createExpense(ResultSet rs) throws SQLException {
        Expense expense = new Expense();
        expense.setExpenseID(rs.getInt("EXPENSEID"));
        expense.setDateTime(new CustomDate(rs.getDate("DATETIME").getTime()));
        expense.setSupplier(rs.getString("SUPPLIER"));
        expense.setMainDescription(rs.getString("MAINDESCRIPTION"));
        expense.setSubDescription(rs.getString("SUBDESCRIPTION"));
        expense.setSubtotal(rs.getBigDecimal("SUBTOTAL"));
        expense.setGst(rs.getBigDecimal("GST"));
        expense.setQst(rs.getBigDecimal("QST"));
        expense.setTotal(rs.getBigDecimal("TOTAL"));
        return expense;
    }
}
