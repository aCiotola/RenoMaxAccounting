package com.ciotola.controller;

import com.ciotola.entities.Expense;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.SubDescription;
import com.ciotola.entities.Supplier;
import com.ciotola.persistence.Implementations.AccountingExpenseDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingExpenseDAO;
import com.ciotola.persistence.Implementations.AccountingSupplierDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSupplierDAO;
import com.ciotola.persistence.Implementations.AccountingMainDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingMainDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingSubDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingSubDescriptionDAO;
import com.ciotola.utils.CustomDate;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * Controller class which contains the methods used for creating/reading/updating/deleting Expenses.
 *
 * @author Alessandro Ciotola
 * @version 2018/05/20
 *
 */
public class ExpenseFXMLController {
    private IAccountingExpenseDAO eDAO;
    private IAccountingSupplierDAO sDAO;
    private IAccountingMainDescriptionDAO mdDAO;
    private IAccountingSubDescriptionDAO sdDAO;
    @FXML private SupplierFXMLController supController;
    @FXML private MainDescriptionFXMLController mdController;
    @FXML private SubDescriptionFXMLController sdController;
    @FXML private GSTReportFXMLController gstController;
    @FXML private ReportsFXMLController reportController;
    private boolean totalChosen = false;
    private boolean subTotalChosen = false;
    private int eID = -1;

    @FXML // fx:id="expenseTable"
    private TableView<Expense> expenseTable; // Value injected by FXMLLoader    

    @FXML // fx:id="expenseNumColumn"
    private TableColumn<Expense, Integer> expenseNumColumn; // Value injected by FXMLLoader
    
    @FXML // fx:id="expenseDateColumn"
    private TableColumn<Expense, CustomDate> expenseDateColumn; // Value injected by FXMLLoader

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

    @FXML // fx:id="totalTotal"
    private Label totalTotal; // Value injected by FXMLLoader

    @FXML // fx:id="totalQst"
    private Label totalQst; // Value injected by FXMLLoader

    @FXML // fx:id="totalGst"
    private Label totalGst; // Value injected by FXMLLoader

    @FXML // fx:id="totalSubTotal"
    private Label totalSubTotal; // Value injected by FXMLLoader

    /**
     * Method which will clear all of the fields.
     *
     * @param event
     */
    @FXML
    void onClearExpense(ActionEvent event) {
        expenseSupplierCombo.setValue("");
        expenseMDCombo.setValue("");
        expenseSDCombo.setValue("");
        expenseSTField.setText("");
        expenseGSTField.setText("");
        expenseQSTField.setText("");
        expenseTotalField.setText("");
        eID = -1;
    }

    /**
     * Method which will delete the expense chosen.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onDeleteExpense(ActionEvent event) throws SQLException {
        if (eID != -1) {
            displayConfirmation("Are you sure you want to delete this expense?");            
        } else {
            displayAlert("Please select an expense!");
        }
    }

    /**
     * Method which will check if the expense already exists. If it does, call
     * the update methods, else, call the add method.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onSaveExpense(ActionEvent event) throws SQLException {
        if (expenseDateField != null && expenseDateField.getValue() != null && expenseSupplierCombo != null && expenseMDCombo != null && expenseSTField != null
                && expenseTotalField != null && expenseSupplierCombo.getValue() != null && !expenseSupplierCombo.getValue().equals("") 
                && expenseMDCombo.getValue() != null && !expenseMDCombo.getValue().equals("") && !expenseSTField.getText().equals("")
                && !expenseTotalField.getText().equals("") && expenseTotalField.getText().length() < 11 && expenseSTField.getText().length() < 11){
            Expense expense = new Expense(); 
            if (eID != -1)
                expense = eDAO.findExpenseById(eID);            

            expense.setDateTime(new CustomDate(Date.valueOf(expenseDateField.getValue()).getTime()));
            expense.setSupplier(expenseSupplierCombo.getValue());
            expense.setMainDescription(expenseMDCombo.getValue());

            if (expenseSDCombo != null && expenseSDCombo.getValue() != null && !expenseSDCombo.getValue().equals(""))
                expense.setSubDescription(expenseSDCombo.getValue());

            expense.setSubtotal(new BigDecimal(expenseSTField.getText()));
            if (!expenseGSTField.getText().equals("") && !expenseQSTField.getText().equals("")) {
                expense.setGst(new BigDecimal(expenseGSTField.getText()));
                expense.setQst(new BigDecimal(expenseQSTField.getText()));
            }
            expense.setTotal(new BigDecimal(expenseTotalField.getText()));

            //Check if supplier exists, if not add to supplier list            
            if (supplierExists(expense.getSupplier()) == false) {
                Supplier supplier = new Supplier();
                supplier.setSupplierName(expense.getSupplier());
                sDAO.addSupplier(supplier);
                
                fillSupplierComboBox();
                supController.displayTable();
            }
            
            //Check if main description exists, if not add to main description list            
            if (mainDescriptionExists(expense.getMainDescription()) == false) {
                MainDescription md = new MainDescription();
                md.setMainDescriptionName(expense.getMainDescription());
                mdDAO.addMainDescription(md);
                
                fillMainDescriptionComboBox();
                mdController.displayTable();
            }
            
            //Check if sub description exists, if not add to sub description list            
            if (subDescriptionExists(expense.getSubDescription()) == false) {
                SubDescription sd = new SubDescription();
                sd.setSubDescriptionName(expense.getSubDescription());
                sdDAO.addSubDescription(sd);
                
                fillSubDescriptionComboBox();
                sdController.displayTable();
            }

            if (expense.getExpenseID() != -1) {
                eDAO.updateExpense(expense);
            } else {
                eDAO.addExpense(expense);
            }

            onClearExpense(new ActionEvent());
            gstController.setupGST();
            reportController.setExpenses();
            displayTable();
        } else {
            displayAlert(checkMissing());
        }
    }

    /**
     * When checked, no taxes are added to the total. The total is equal to the
     * subtotal.
     *
     * @param event
     */
    @FXML
    void onToggleTaxes(ActionEvent event) {
        if (expenseGSTField.isDisabled()) {
            expenseGSTField.setDisable(false);
            expenseQSTField.setDisable(false);

            if (!expenseTotalField.getText().equals("") && !expenseSTField.getText().equals("")) {
                BigDecimal sub = new BigDecimal(expenseSTField.getText());

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
        } else {
            expenseGSTField.setDisable(true);
            expenseQSTField.setDisable(true);

            expenseGSTField.setText("");
            expenseQSTField.setText("");
            expenseTotalField.setText(expenseSTField.getText());
        }
    }

    /**
     * No parameter constructor which calls the super's constructor.
     *
     */
    public ExpenseFXMLController() {
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
    void initialize() throws IOException, SQLException {
        assert expenseDateColumn != null : "fx:id=\"expenseDateColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSupplierColumn != null : "fx:id=\"expenseSupplierColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseMDColumn != null : "fx:id=\"expenseMDColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSDColumn != null : "fx:id=\"expenseSDColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSTColumn != null : "fx:id=\"expenseSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseGSTColumn != null : "fx:id=\"expenseGSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseQSTColumn != null : "fx:id=\"expenseQSTColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseTotalColumn != null : "fx:id=\"expenseTotalColumn\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseDateField != null : "fx:id=\"expenseDateField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSupplierCombo != null : "fx:id=\"expenseSupplierCombo\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseMDCombo != null : "fx:id=\"expenseMDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSDCombo != null : "fx:id=\"expenseSDField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseSTField != null : "fx:id=\"expenseSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseGSTField != null : "fx:id=\"expenseGSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseQSTField != null : "fx:id=\"expenseQSTField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert expenseTotalField != null : "fx:id=\"expenseTotalField\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert totalTotal != null : "fx:id=\"totalTotal\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert totalQst != null : "fx:id=\"totalQst\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert totalGst != null : "fx:id=\"totalGst\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";
        assert totalSubTotal != null : "fx:id=\"totalSubTotal\" was not injected: check your FXML file 'ExpenseFXML.fxml'.";

        eDAO = new AccountingExpenseDAOImp();
        sDAO = new AccountingSupplierDAOImp();
        mdDAO = new AccountingMainDescriptionDAOImp();
        sdDAO = new AccountingSubDescriptionDAOImp();

        fillSupplierComboBox();
        fillMainDescriptionComboBox();
        fillSubDescriptionComboBox();
        displayTable();

        expenseNumColumn.setCellValueFactory(cellData -> cellData.getValue().getExpenseIDProperty().asObject());
        expenseDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateTimeProperty());
        expenseSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplierProperty());
        expenseMDColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionProperty());
        expenseSDColumn.setCellValueFactory(cellData -> cellData.getValue().getSubDescriptionProperty());
        expenseSTColumn.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
        expenseGSTColumn.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
        expenseQSTColumn.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
        expenseTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());

        expenseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showExpenseDetails(newValue));
        expenseTotalField.textProperty().addListener((observable, oldValue, newValue) -> fillSubTotal(newValue));
        expenseSTField.textProperty().addListener((observable, oldValue, newValue) -> fillTotal(newValue));
        TextFields.bindAutoCompletion(expenseSupplierCombo.getEditor(), expenseSupplierCombo.getItems());    
        TextFields.bindAutoCompletion(expenseMDCombo.getEditor(), expenseMDCombo.getItems());    
        TextFields.bindAutoCompletion(expenseSDCombo.getEditor(), expenseSDCombo.getItems());    
    }

    /**
     * Method responsible for setting the totals for the prices when an expense
     * is added or modified.
     *
     * @throws SQLException
     */
    private void setTotals() throws SQLException {
        List<Expense> expenseList = eDAO.findAllExpenses();
        double tSubtotal = 0.0;
        double tGst = 0.0;
        double tQst = 0.0;
        double tTotal = 0.0;

        for (int i = 0; i < expenseList.size(); i++) {
            tSubtotal += expenseList.get(i).getSubtotal().doubleValue();
            tGst += expenseList.get(i).getGst().doubleValue();
            tQst += expenseList.get(i).getQst().doubleValue();
            tTotal += expenseList.get(i).getTotal().doubleValue();
        }

        tSubtotal = Math.round(tSubtotal * 100.0) / 100.0;
        tQst = Math.round(tQst * 100.0) / 100.0;
        tGst = Math.round(tGst * 100.0) / 100.0;
        tTotal = Math.round(tTotal * 100.0) / 100.0;

        totalTotal.setText("$" + tTotal);
        totalQst.setText("$" + tQst);
        totalGst.setText("$" + tGst);
        totalSubTotal.setText("$" + tSubtotal);
    }

    /**
     * Will display all expenses in the table.
     *
     * @throws SQLException
     */
    public void displayTable() throws SQLException {
        setTotals();
        expenseTable.setItems(eDAO.findAllExpenses());
        eID = -1;
    }

    /**
     * Listener which will fill the fields with the selected expense.
     *
     * @param expense
     */
    private void showExpenseDetails(Expense expense) {
        if (expense != null) {            
            expenseDateField.setValue(expense.getDateTime().toLocalDate());

            expenseSupplierCombo.setValue(expense.getSupplier());
            expenseMDCombo.setValue(expense.getMainDescription());
            expenseSDCombo.setValue(expense.getSubDescription());
            expenseSTField.setText(expense.getSubtotal().doubleValue() + "");
            expenseGSTField.setText(expense.getGst().doubleValue() + "");
            expenseQSTField.setText(expense.getQst().doubleValue() + "");
            expenseTotalField.setText(expense.getTotal().doubleValue() + "");
            
            eID = expense.getExpenseID();
        }
    }

    /**
     * Method which will set the subtotal, gst and qst when the total is
     * entered.
     *
     * @param total
     */
    private void fillSubTotal(String total) {
        totalChosen = true;
        if(subTotalChosen)
            totalChosen = false;
        
        if(total.matches("[0-9.]*")){
            if (!expenseTotalField.getText().equals("") && totalChosen && subTotalChosen == false) {
                if (expenseGSTField.isDisabled()) {
                    expenseSTField.setText(total);
                } else {
                    BigDecimal tot = new BigDecimal(total);

                    BigDecimal subTotal = new BigDecimal(tot.doubleValue() / 1.14975);
                    subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal gst = new BigDecimal(subTotal.doubleValue() * 0.05);
                    gst = gst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal qst = new BigDecimal(subTotal.doubleValue() * 0.09975);
                    qst = qst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    expenseGSTField.setText(gst.doubleValue() + "");
                    expenseQSTField.setText(qst.doubleValue() + "");
                    expenseSTField.setText(subTotal.doubleValue() + "");      
                }
            }
        }   
        totalChosen = false;
    }
    
    /**
     * Method which will set the total, gst and qst when the subtotal is
     * entered.
     *
     * @param total
     */
    private void fillTotal(String subtotal) {
        subTotalChosen = true;
        if(totalChosen)
            subTotalChosen = false;
        if(subtotal.matches("[0-9.]*")){
            if (!expenseSTField.getText().equals("") && totalChosen == false && subTotalChosen) {
                if (expenseGSTField.isDisabled()) {
                    expenseTotalField.setText(subtotal);
                } else {
                    BigDecimal st = new BigDecimal(subtotal);

                    BigDecimal total = new BigDecimal(st.doubleValue() * 1.14975);
                    total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal gst = new BigDecimal(st.doubleValue() * 0.05);
                    gst = gst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal qst = new BigDecimal(st.doubleValue() * 0.09975);
                    qst = qst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    expenseGSTField.setText(gst.doubleValue() + "");
                    expenseQSTField.setText(qst.doubleValue() + "");
                    expenseTotalField.setText(total.doubleValue() + "");
                }
            }
        }       
        subTotalChosen = false;
    }

    /**
     * Method which will fill the Supplier Drop down list with all suppliers.
     *
     * @throws SQLException
     */
    private void fillSupplierComboBox() throws SQLException {
        ObservableList<Supplier> suppliers = sDAO.findAllSuppliers();
        ObservableList<String> sElements = FXCollections.observableArrayList();
        for (int i = 0; i < suppliers.size(); i++) {
            sElements.add(suppliers.get(i).getSupplierName());
        }
        expenseSupplierCombo.setItems(sElements);
    }

    /**
     * Method which will fill the Main Description Drop down list with all
     * suppliers.
     *
     * @throws SQLException
     */
    private void fillMainDescriptionComboBox() throws SQLException {
        ObservableList<MainDescription> mainDescriptions = mdDAO.findAllMainDescriptions();
        ObservableList<String> mdElements = FXCollections.observableArrayList();
        for (int i = 0; i < mainDescriptions.size() - 1; i++) {
            mdElements.add(mainDescriptions.get(i).getMainDescriptionName());
        }
        expenseMDCombo.setItems(mdElements);
    }

    /**
     * Method which will fill the Sub Description Drop down list with all
     * suppliers.
     *
     * @throws SQLException
     */
    private void fillSubDescriptionComboBox() throws SQLException {
        ObservableList<SubDescription> subDescriptions = sdDAO.findAllSubDescriptions();
        ObservableList<String> sdElements = FXCollections.observableArrayList();
        for (int i = 0; i < subDescriptions.size() - 1; i++) {
            sdElements.add(subDescriptions.get(i).getSubDescriptionName());
        }
        expenseSDCombo.setItems(sdElements);
    }
    
    /**
     * Method responsible for checking if the supplier exists in the database or not.
     * 
     * @param supplier
     * @return 
     */
    private boolean supplierExists(String supplier) throws SQLException {
        List<Supplier> suppliers = sDAO.findAllSuppliers();
        for (int i = 0; i < suppliers.size(); i++) {
            if (supplier.equalsIgnoreCase(suppliers.get(i).getSupplierName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method responsible for checking if the main description exists in the database or not.
     * 
     * @param md
     * @return 
     */
    private boolean mainDescriptionExists(String md) throws SQLException {
        List<MainDescription> mds = mdDAO.findAllMainDescriptions();
        for (int i = 0; i < mds.size(); i++) {
            if (md.equalsIgnoreCase(mds.get(i).getMainDescriptionName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method responsible for checking if the sub description exists in the database or not.
     * 
     * @param sd
     * @return 
     */
    private boolean subDescriptionExists(String sd) throws SQLException {
        List<SubDescription> sds = sdDAO.findAllSubDescriptions();
        for (int i = 0; i < sds.size(); i++) {
            if (sd.equalsIgnoreCase(sds.get(i).getSubDescriptionName())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method responsible for setting the controllers
     * 
     * @param supController
     * @param mdController
     * @param sdController
     * @param gstController
     * @param reportController 
     */
    public void setControllers(SupplierFXMLController supController, MainDescriptionFXMLController mdController, SubDescriptionFXMLController sdController, 
            GSTReportFXMLController gstController, ReportsFXMLController reportController){
        this.supController = supController;
        this.mdController = mdController;
        this.sdController = sdController;
        this.gstController = gstController;
        this.reportController = reportController;
    }
    
    /***
     * Method responsible for checking if any required field values are missing
     * 
     * @return 
     */
    private String checkMissing(){
        String msg = "Please enter the following information:\n";
        if (expenseDateField.getValue() == null)
            msg += "- Date\n";
        if (expenseSupplierCombo.getValue() == null || expenseSupplierCombo.getValue().equals(""))
            msg += "- Supplier\n";
        if (expenseMDCombo.getValue() == null || expenseMDCombo.getValue().equals(""))
            msg += "- Main Description\n";
        if (expenseSTField.getText().equals(""))
            msg += "- Sub Total\n";
        if (!expenseSTField.getText().equals("") && expenseSTField.getText().length() >= 11)
            msg += "- Sub Total is too large\n";
        if (expenseTotalField.getText().equals(""))
            msg += "- Total\n";
        if (!expenseTotalField.getText().equals("") && expenseTotalField.getText().length() >= 11)
            msg += "- Total is too large\n";
        
        return msg;
    }
    
    /**
     * Method responsible for displaying an alert when an error occurs.
     * 
     * @param msg 
     */
    private void displayAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Missing Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    
    /**
     * Method responsible for displaying a confirmation message when the user attempts 
     * to delete an expense.
     * 
     * @param msg 
     */
    private void displayConfirmation(String msg) throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Expense");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            eDAO.deleteExpense(eID);
            onClearExpense(new ActionEvent());
            displayTable();
        }
    }
}
