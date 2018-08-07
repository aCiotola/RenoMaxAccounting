package com.ciotola.controller;

import com.ciotola.entities.Expense;
import com.ciotola.entities.MainDescription;
import com.ciotola.entities.Report;
import com.ciotola.persistence.Implementations.AccountingExpenseDAOImp;
import com.ciotola.persistence.Implementations.AccountingMainDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingExpenseDAO;
import com.ciotola.persistence.Interfaces.IAccountingMainDescriptionDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Controller class which contains the methods used for displaying the Expenses
 * Reports.
 *
 * @author Alessandro Ciotola
 * @version 2018/05/19
 *
 */
public class ReportsFXMLController {

    private IAccountingExpenseDAO eDAO;
    private IAccountingMainDescriptionDAO mdDAO;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="currentYear"
    private Label currentYear; // Value injected by FXMLLoader

    @FXML // fx:id="mdTable"
    private TableView<Report> mdTable; // Value injected by FXMLLoader

    @FXML // fx:id="mdColumn"
    private TableColumn<Report, String> mdColumn; // Value injected by FXMLLoader

    @FXML // fx:id="subTotalColumn"
    private TableColumn<Report, BigDecimal> subTotalColumn; // Value injected by FXMLLoader

    @FXML // fx:id="gstColumn"
    private TableColumn<Report, BigDecimal> gstColumn; // Value injected by FXMLLoader

    @FXML // fx:id="qstColumn"
    private TableColumn<Report, BigDecimal> qstColumn; // Value injected by FXMLLoader

    @FXML // fx:id="totalColumn"
    private TableColumn<Report, BigDecimal> totalColumn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        try {
            assert currentYear != null : "fx:id=\"currentYear\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert mdTable != null : "fx:id=\"mdTable\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert mdColumn != null : "fx:id=\"mdColumn\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert subTotalColumn != null : "fx:id=\"subTotalColumn\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert gstColumn != null : "fx:id=\"gstColumn\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert qstColumn != null : "fx:id=\"qstColumn\" was not injected: check your FXML file 'ReportsFXML.fxml'.";
            assert totalColumn != null : "fx:id=\"totalColumn\" was not injected: check your FXML file 'ReportsFXML.fxml'.";

            eDAO = new AccountingExpenseDAOImp();
            mdDAO = new AccountingMainDescriptionDAOImp();

            mdColumn.setCellValueFactory(cellData -> cellData.getValue().getMainDescriptionProperty());
            subTotalColumn.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
            gstColumn.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
            qstColumn.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
            totalColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
            
            int year = Calendar.getInstance().get(Calendar.YEAR);
            currentYear.setText(year+"");

            setExpenses();
            
        } catch (SQLException ex) {
            Logger.getLogger(ReportsFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setExpenses() throws SQLException {
        ObservableList<MainDescription> mds = mdDAO.findAllMainDescriptions();
        ObservableList<Report> reports = FXCollections.observableArrayList();;

        for (int i = 0; i < mds.size(); i++) {
            double sub = 0;
            double gst = 0;
            double qst = 0;
            double total = 0;            
            
            ObservableList<Expense> expenses = eDAO.findExpensesByMainDescription(mds.get(i).getMainDescriptionName());
            
            for(int j = 0; j < expenses.size(); j++) {
                sub += expenses.get(j).getSubtotal().doubleValue();
                gst += expenses.get(j).getGst().doubleValue();
                qst += expenses.get(j).getQst().doubleValue();
                total += expenses.get(j).getTotal().doubleValue();
            }
            Report report = new Report();
            report.setMainDescription(mds.get(i).getMainDescriptionName());
            report.setSubtotal(BigDecimal.valueOf(sub).setScale(2, RoundingMode.CEILING));
            report.setGst(BigDecimal.valueOf(gst).setScale(2, RoundingMode.CEILING));
            report.setQst(BigDecimal.valueOf(qst).setScale(2, RoundingMode.CEILING));
            report.setTotal(BigDecimal.valueOf(total).setScale(2, RoundingMode.CEILING));           
            
            reports.add(report);            
        }

        mdTable.setItems(reports);
    }
}
