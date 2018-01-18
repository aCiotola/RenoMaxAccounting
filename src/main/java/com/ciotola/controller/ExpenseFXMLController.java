package com.ciotola.controller;

import com.ciotola.entities.Expense;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import com.ciotola.persistence.AccountingDAOImp;
import com.ciotola.persistence.IAccountingDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class which contains the methods used for creating/updating/deleting Expenses.
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/15
 * 
 */
public class ExpenseFXMLController
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IAccountingDAO accountDAO;
    private ObservableList<String> elements;
    
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

    @FXML // fx:id="expenseSupplierCombo"
    private ComboBox<String> expenseSupplierCombo; // Value injected by FXMLLoader

    @FXML // fx:id="expenseMDCombo"
    private ComboBox<String> expenseMDCombo; // Value injected by FXMLLoader

    @FXML // fx:id="expenseSDCombo"
    private ComboBox<String> expenseSDCombo; // Value injected by FXMLLoader

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

    
    /**
     * Method which will clear all of the fields.
     * 
     * @param event 
     */
    @FXML
    void onClearExpense(ActionEvent event) 
    {
        expenseNumberField.setText("");
        expenseSupplierCombo.setValue("");
        expenseMDCombo.setValue("");
        expenseSDCombo.setValue("");
        expenseSTField.setText("");
        expenseGSTField.setText("");
        expenseQSTField.setText("");
        expenseTotalField.setText("");            
    }

    /**
     * Method which will delete the expense chosen.
     * 
     * @param event
     * @throws SQLException 
     */
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

    /**
     * Method which will check if the expense already exists. If it does, call the
     * update methods, else, call the add method.
     * 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onSaveExpense(ActionEvent event) throws SQLException 
    {
        if(!expenseMDCombo.getValue().equals("") && !expenseSTField.getText().equals("") && !expenseSupplierCombo.getValue().equals("") &&
                !expenseTotalField.getText().equals("") && expenseDateField.getValue() != null)
        {             
            Expense expense = new Expense();            
            if(!expenseNumberField.getText().equals(""))
                expense = accountDAO.findExpenseByNumber(Integer.parseInt(expenseNumberField.getText()));                         
            
            expense.setDateTime(Date.valueOf(expenseDateField.getValue()));
            expense.setSupplier(expenseSupplierCombo.getValue());
            expense.setMainDescription(expenseMDCombo.getValue());
            expense.setSubDescription(expenseSDCombo.getValue());
            expense.setSubtotal(new BigDecimal(expenseSTField.getText()));            
            if(!expenseGSTField.getText().equals("") && !expenseQSTField.getText().equals(""))
            {
                expense.setGst(new BigDecimal(expenseGSTField.getText()));
                expense.setQst(new BigDecimal(expenseQSTField.getText()));
            }            
            expense.setTotal(new BigDecimal(expenseTotalField.getText()));
            
            if(expense.getExpenseID() != -1)            
                accountDAO.updateExpense(expense);            
            else
                accountDAO.addExpense(expense);
            
            log.debug("Expense created!");            
            onClearExpense(new ActionEvent());            
            displayTable();
        }
        else        
            expenseNumberField.setText("Please enter the Date, Supplier name, main description, subtotal and total!!");            
    }
    
    /**
     * When checked, no taxes are added to the total. The total is equal to the subtotal.
     * 
     * @param event 
     */
    @FXML
    void onNoTaxes(ActionEvent event) 
    {
        if(expenseGSTField.isDisabled())
        {
            expenseGSTField.setDisable(false);
            expenseQSTField.setDisable(false);
        }
        else
        {
            expenseGSTField.setDisable(true);
            expenseQSTField.setDisable(true);
        }
    }

    /**
     * No parameter constructor which calls the super's constructor.
     * 
     */
    public ExpenseFXMLController()
    {
        super();
    }
    
    /**
     * This method is called by the FXMLLoader when initialization is complete.
     * Will initialize the DAO object and display the table.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    @FXML 
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
        assert expenseSupplierCombo != null : "fx:id=\"expenseSupplierCombo\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseMDCombo != null : "fx:id=\"expenseMDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSDCombo != null : "fx:id=\"expenseSDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSTField != null : "fx:id=\"expenseSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseGSTField != null : "fx:id=\"expenseGSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseQSTField != null : "fx:id=\"expenseQSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseTotalField != null : "fx:id=\"expenseTotalField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";        
            
        accountDAO = new AccountingDAOImp();
        fillSupplierComboBox();
        fillMainDescriptionComboBox();
        fillSubDescriptionComboBox();        
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
        expenseSTField.textProperty().addListener((observable, oldValue, newValue) -> fillTotal(newValue));   
        expenseSupplierCombo.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try { showSupplierLikeName(newValue);} 
            catch (SQLException ex) 
            {log.debug("EXCEPTION IN SUPPLEIR LIKE NAME LISTENER: " + ex.getMessage());}});
    }
    
    /**
     * Will display all expenses in the table.
     * 
     * @throws SQLException 
     */
    public void displayTable() throws SQLException
    {
        expenseTable.setItems(accountDAO.findAllExpenses());
    }
    
    /**
     * Listener which will fill the fields with the selected expense.
     * 
     * @param expense 
     */
    private void showExpenseDetails(Expense expense)
    {
        if(expense != null)
        {
            expenseNumberField.setText(expense.getExpenseNumber() + "");
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM dd");
            expenseDateField.setPromptText(expense.getDateTime().toLocalDate().format(formatter));
            
            expenseSupplierCombo.setValue(expense.getSupplier());
            expenseMDCombo.setValue(expense.getMainDescription());
            expenseSDCombo.setValue(expense.getSubDescription());
            expenseSTField.setText(expense.getSubtotal().toString());
            expenseGSTField.setText(expense.getGst().toString());
            expenseQSTField.setText(expense.getQst().toString());
            expenseTotalField.setText(expense.getTotal().toString());
        }
    }  
    
    /**
     * Method which will fill the Supplier Drop down list with all suppliers based on
     * what is inputted in the editable field.
     * 
     * @param name
     * @throws SQLException 
     */
    private void showSupplierLikeName(String name) throws SQLException
    {        
        if(!expenseSupplierCombo.getEditor().getText().equals("") && !elements.contains(name))
        {
            ObservableList<Supplier> suppliers = accountDAO.findSupplierLikeName(name);   
            ObservableList<String> likeElements = FXCollections.observableArrayList();
            
            for(int i = 0; i < suppliers.size(); i++)
            {
                likeElements.add(suppliers.get(i).getSupplierName());
            }
            expenseSupplierCombo.setItems(likeElements);
        }
        else
            expenseSupplierCombo.setItems(elements);
    }
    
    private void fillTotal(String subTotal) 
    {              
        if(!expenseSTField.getText().equals(""))
        {
            if(expenseGSTField.isDisabled())
                expenseTotalField.setText(subTotal);
            else
            {
                BigDecimal sub = new BigDecimal(subTotal);
                
                BigDecimal gst = new BigDecimal(sub.doubleValue() * 0.05);
                gst = gst.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                
                BigDecimal qst = new BigDecimal(sub.doubleValue() * 0.09975);
                qst = qst.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                
                BigDecimal total = new BigDecimal(sub.doubleValue() + gst.doubleValue() + qst.doubleValue());
                total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                
                expenseGSTField.setText(gst.toString());
                expenseQSTField.setText(qst.toString());
                expenseTotalField.setText(total.toString());
            }
        }
    }   
    
    /**
     * Method which will fill the Supplier Drop down list with all suppliers.
     * 
     * @throws SQLException 
     */
    private void fillSupplierComboBox() throws SQLException
    {
        ObservableList<Supplier> suppliers = accountDAO.findAllSuppliers();
        elements = FXCollections.observableArrayList();
        for(int i = 0; i < suppliers.size(); i++)
            elements.add(suppliers.get(i).getSupplierName());
        expenseSupplierCombo.setItems(elements);
    }
    
    /**
     * Method which will fill the Main Description Drop down list with all suppliers.
     * 
     * @throws SQLException 
     */
    private void fillMainDescriptionComboBox() throws SQLException
    {
        ObservableList<MainDescription> suppliers = accountDAO.findAllMainDescriptions();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(int i = 0; i < suppliers.size() - 1; i++)
            elements.add(suppliers.get(i).getMainDescriptionName());
        expenseMDCombo.getItems().addAll(elements);
    }
    
    /**
     * Method which will fill the Sub Description Drop down list with all suppliers.
     * 
     * @throws SQLException 
     */
    private void fillSubDescriptionComboBox() throws SQLException
    {
        ObservableList<SubDescription> suppliers = accountDAO.findAllSubDescriptions();
        ObservableList<String> elements = FXCollections.observableArrayList();
        for(int i = 0; i < suppliers.size() - 1; i++)
            elements.add(suppliers.get(i).getSubDescriptionName());
        expenseSDCombo.getItems().addAll(elements);
    }
}