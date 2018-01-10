package com.ciotola.controller;

import com.ciotola.entities.Expense;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpenseFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TableView<Expense> expenseTable;
    
    @FXML // fx:id="expenseNumColumn"
    private TableColumn<Expense, Integer> expenseNumColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseDateColumn"
    private TableColumn<Expense, Date> expenseDateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSupplierColumn"
    private TableColumn<Expense, String> expenseSupplierColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseMDColumn"
    private TableColumn<Expense, String> expenseMDColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSDColumn"
    private TableColumn<Expense, String> expenseSDColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSTColumn"
    private TableColumn<Expense, BigDecimal> expenseSTColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseGSTColumn"
    private TableColumn<Expense, BigDecimal> expenseGSTColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseQSTColumn"
    private TableColumn<Expense, BigDecimal> expenseQSTColumn; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTotalColumn"
    private TableColumn<Expense, BigDecimal> expenseTotalColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="expenseNumberField"
    private TextField expenseNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSupplierField"
    private TextField expenseSupplierField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseMDField"
    private TextField expenseMDField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSDField"
    private TextField expenseSDField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSTField"
    private TextField expenseSTField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseGSTField"
    private TextField expenseGSTField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseQSTField"
    private TextField expenseQSTField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseTotalField"
    private TextField expenseTotalField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseDateField"
    private DatePicker expenseDateField; // Value injected by FXMLLoader

    @FXML
    void onClearExpense(ActionEvent event) 
    {
        expenseNumberField.setText("");
        expenseSupplierField.setText("");
        expenseMDField.setText("");
        expenseSDField.setText("");
        expenseSTField.setText("");
        expenseGSTField.setText("");
        expenseQSTField.setText("");
        expenseTotalField.setText("");            
    }

    @FXML
    void onDeleteExpense(ActionEvent event) throws SQLException 
    {
        if(!expenseNumberField.getText().equals(""))
        {                                  
            int id = accountDAO.findExpenseByNumber(Integer.parseInt(expenseNumberField.getText())).getExpenseID();
            accountDAO.deleteExpense(id);
            onClearExpense(new ActionEvent());  
            
            log.debug("Expense deleted!");            
            displayTable();
        }
        else        
            expenseNumberField.setText("Please select a date, supplier, description, subtotal and total!");
    }

    @FXML
    void onSaveExpense(ActionEvent event) throws SQLException 
    {
        if(!expenseMDField.getText().equals("") && !expenseSTField.getText().equals("") && 
                !expenseTotalField.getText().equals("") && expenseDateField.getValue() != null)
        {             
            Expense expense = new Expense();   
            
            if(!expenseNumberField.getText().equals(""))
                expense.setExpenseNumber(Integer.parseInt(expenseNumberField.getText()));
                
            expense.setDateTime(Date.valueOf(expenseDateField.getValue()));
            expense.setSupplier(expenseSupplierField.getText());
            expense.setMainDescription(expenseMDField.getText());
            expense.setSubDescription(expenseSDField.getText());
            expense.setSubtotal(new BigDecimal(expenseSTField.getText()));
            
            if(!expenseGSTField.getText().equals("") && !expenseQSTField.getText().equals(""))
            {
                expense.setGst(new BigDecimal(expenseGSTField.getText()));
                expense.setQst(new BigDecimal(expenseQSTField.getText()));
            }
            
            expense.setTotal(new BigDecimal(expenseTotalField.getText()));
            accountDAO.addExpense(expense);
            
            log.debug("Expense created!");
            
            onClearExpense(new ActionEvent());
            
            displayTable();
        }
        else        
            expenseNumberField.setText("Please enter the Date, Supplier name, main description, subtotal and total!!");            
    }

    public ExpenseFXMLController()
    {
        super();
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException 
    {
        assert expenseNumColumn != null : "fx:id=\"expenseNumColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseDateColumn != null : "fx:id=\"expenseDateColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSupplierColumn != null : "fx:id=\"expenseSupplierColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseMDColumn != null : "fx:id=\"expenseMDColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSDColumn != null : "fx:id=\"expenseSDColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSTColumn != null : "fx:id=\"expenseSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseGSTColumn != null : "fx:id=\"expenseGSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseQSTColumn != null : "fx:id=\"expenseQSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseTotalColumn != null : "fx:id=\"expenseTotalColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseNumberField != null : "fx:id=\"expenseNumberField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseDateField != null : "fx:id=\"expenseDateField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSupplierField != null : "fx:id=\"expenseSupplierField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseMDField != null : "fx:id=\"expenseMDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSDField != null : "fx:id=\"expenseSDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSTField != null : "fx:id=\"expenseSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseGSTField != null : "fx:id=\"expenseGSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseQSTField != null : "fx:id=\"expenseQSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseTotalField != null : "fx:id=\"expenseTotalField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";        
            
        accountDAO = new AccountingDAOImp();
        displayTable();
            
        expenseNumColumn.setCellValueFactory(cellData -> cellData.getValue().getExpenseNumberProperty().asObject());
        expenseDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        expenseSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierProperty());
        expenseMDColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionProperty());
        expenseSDColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionProperty());
        expenseSTColumn.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
        expenseGSTColumn.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
        expenseQSTColumn.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
        expenseTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
                    
        expenseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showExpenseDetails(newValue));
    }
    
    public void displayTable() throws SQLException
    {
        expenseTable.setItems(accountDAO.findAllExpenses());
    }
    
    private void showExpenseDetails(Expense expense)
    {
        if(expense != null)
        {
            expenseNumberField.setText(expense.getExpenseNumber() + "");
            expenseDateField.setValue(expense.getDateTime().toLocalDate());
            expenseSupplierField.setText(expense.getSupplier());
            expenseMDField.setText(expense.getMainDescription());
            expenseSDField.setText(expense.getSubDescription());
            expenseSTField.setText(expense.getSubtotal().toString());
            expenseGSTField.setText(expense.getGst().toString());
            expenseQSTField.setText(expense.getQst().toString());
            expenseTotalField.setText(expense.getTotal().toString());
        }
    }
}
