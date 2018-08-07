package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.Implementations.AccountingInvoiceDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import com.ciotola.persistence.Implementations.AccountingInvoiceDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDescriptionDAO;
import com.ciotola.persistence.Implementations.AccountingClientDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingClientDAO;
import com.ciotola.renomaxaccounting.MainAppFX;
import com.ciotola.utils.CustomDate;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * Controller class which contains the methods used for
 * creating/reading/updating/deleting Invoices.
 *
 * @author Alessandro Ciotola
 * @version 2018/05/20
 *
 */
public class InvoiceFXMLController {

    private IAccountingInvoiceDAO iDAO;
    private IAccountingInvoiceDescriptionDAO idDAO;
    private IAccountingClientDAO cDAO;
    private ClientFXMLController clientController;
    private GSTReportFXMLController gstController;
    private TabPane tabPane;
    private boolean totalChosen = false;
    private boolean subTotalChosen = false;
    private int latestNum = -1;

    private String invoiceDescriptionText = "";
    private int id = -1;

    @FXML // fx:id="invoiceNumberField"
    private TextField invoiceNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateField"
    private DatePicker invoiceDateField; // Value injected by FXMLLoader

    @FXML // fx:id="clientComboBox"
    private ComboBox<String> clientComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private TextField descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionBtn"
    private Button descriptionBtn; // Value injected by FXMLLoader

    @FXML // fx:id="priceField"
    private TextField priceField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTable"
    private TableView<InvoiceDescription> descriptionTable; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionCol"
    private TableColumn<InvoiceDescription, String> descriptionCol; // Value injected by FXMLLoader

    @FXML // fx:id="priceCol"
    private TableColumn<InvoiceDescription, BigDecimal> priceCol; // Value injected by FXMLLoader

    @FXML // fx:id="subtotalField"
    private TextField subtotalField; // Value injected by FXMLLoader

    @FXML // fx:id="gstField"
    private TextField gstField; // Value injected by FXMLLoader

    @FXML // fx:id="qstField"
    private TextField qstField; // Value injected by FXMLLoader

    @FXML // fx:id="totalField"
    private TextField totalField; // Value injected by FXMLLoader

    @FXML // fx:id="sentCheckBox"
    private CheckBox sentCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="paidField"
    private TextField paidField; // Value injected by FXMLLoader

    @FXML // fx:id="clientTable"
    private TableView<Invoice> clientTable; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumCol"
    private TableColumn<Invoice, Integer> invoiceNumCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceDateCol"
    private TableColumn<Invoice, CustomDate> invoiceDateCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceClientCol"
    private TableColumn<Invoice, String> invoiceClientCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSubTotalCol"
    private TableColumn<Invoice, BigDecimal> invoiceSubTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceGstCol"
    private TableColumn<Invoice, BigDecimal> invoiceGstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceQstCol"
    private TableColumn<Invoice, BigDecimal> invoiceQstCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceTotalCol"
    private TableColumn<Invoice, BigDecimal> invoiceTotalCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceSentCol"
    private TableColumn<Invoice, String> invoiceSentCol; // Value injected by FXMLLoader

    @FXML // fx:id="invoicePaidCol"
    private TableColumn<Invoice, String> invoicePaidCol; // Value injected by FXMLLoader

    @FXML
    void onClearInvoice(ActionEvent event) throws SQLException {        
        clientComboBox.setValue("");
        descriptionField.setText("");
        priceField.setText("");

        List<InvoiceDescription> invoiceDescriptionList = idDAO.findInvoiceDescriptionByInvoiceNumber(id);
        for (int i = 0; i < invoiceDescriptionList.size(); i++) {
            idDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceNumber());
        }

        subtotalField.setText("");
        gstField.setText("");
        qstField.setText("");
        totalField.setText("");
        sentCheckBox.setSelected(false);
        paidField.setText("");

        LocalDate date = LocalDate.now();

        invoiceNumberField.setText(latestNum + "");
        invoiceDateField.setValue(date);
        id = -1;

        setupInvoice();
    }

    @FXML
    void onSaveDescription(ActionEvent event) throws SQLException {
        if (!descriptionField.getText().equals("") && priceField.getText().matches("[0-9.]*")) {
            InvoiceDescription invoiceDescription = idDAO.findInvoiceDescriptionByName(invoiceDescriptionText, id);
            invoiceDescription.setInvoiceDescription(descriptionField.getText());

            if (!priceField.getText().equals("")) {
                invoiceDescription.setPrice(BigDecimal.valueOf(Double.parseDouble(priceField.getText())));
            }
            invoiceDescription.setInvoiceNumber(id);

            if (invoiceDescription.getInvoiceDescriptionID() == -1) {
                idDAO.addInvoiceDescription(invoiceDescription);
            } else {
                idDAO.updateInvoiceDescription(invoiceDescription);
            }

            descriptionField.setText("");
            priceField.setText("");
            displayDescriptionTable(id);
        } else {
            displayAlert("Please enter a description and/or a valid price!");
        }
    }

    @FXML
    void onDeleteDescription(ActionEvent event) throws SQLException {
        if (!descriptionField.getText().equals("")) {
            displayConfirmation("Are you sure you want to delete this description?");
        } else {
            displayAlert("Please select an invoice description!");
        }
    }

    @FXML
    void onSaveInvoice(ActionEvent event) throws SQLException {
        if (invoiceDateField.getValue() != null && clientComboBox.getValue() != null && !subtotalField.getText().equals("")
                && !gstField.getText().equals("") && !qstField.getText().equals("") && !totalField.getText().equals("")
                && totalField.getText().length() < 11 && subtotalField.getText().length() < 11) {
            Invoice invoice = iDAO.findInvoiceById(id);
            invoice.setInvoiceDate(new CustomDate(Date.valueOf(invoiceDateField.getValue()).getTime()));

            invoice.setClient(clientComboBox.getValue());
            Client client = cDAO.findClientByName(clientComboBox.getValue());
            if (client.getClientID() == -1) {
                displayClientConfirmation("Would you like to add this new clients' information?");
            }

            invoice.setSubtotal(BigDecimal.valueOf(Double.parseDouble(subtotalField.getText())));
            invoice.setGst(BigDecimal.valueOf(Double.parseDouble(gstField.getText())));
            invoice.setQst(BigDecimal.valueOf(Double.parseDouble(qstField.getText())));
            invoice.setTotal(BigDecimal.valueOf(Double.parseDouble(totalField.getText())));
            invoice.setInvoiceSent(sentCheckBox.isSelected());
            invoice.setInvoicePaid(paidField.getText());

            if (invoice.getInvoiceID() != -1) {
                iDAO.updateInvoice(invoice);
            } else {
                iDAO.addInvoice(invoice);
            }

            setupInvoice();
            onClearInvoice(new ActionEvent());
            gstController.setupGST();
        } else {
            displayAlert(checkMissing());
        }
    }

    @FXML
    void onDeleteInvoice(ActionEvent event) throws SQLException {
        if (invoiceDateField.getValue() != null && clientComboBox.getValue() != null && !subtotalField.getText().equals("")
                && !gstField.getText().equals("") && !qstField.getText().equals("") && !totalField.getText().equals("")) {
            displayInvoiceConfirmation("Are you sure you want to delete this invoice?");
        } else {
            displayAlert("Please select an Invoice!");
        }
    }

    @FXML
    void onShowInvoice(ActionEvent event) throws IOException, SQLException {
        Stage invoiceStage = new Stage();
        invoiceStage.setTitle("RenoMax Accounting 2018");
        invoiceStage.resizableProperty().setValue(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));

        Parent parent = (AnchorPane) loader.load();
        Scene scene = new Scene(parent);

        invoiceStage.setScene(scene);

        InvoiceTemplateFXMLController controller = loader.getController();
        Client client = cDAO.findClientByName(clientComboBox.getValue());
        controller.setInvoiceTemplateFXMLController(client, id, invoiceStage);

        invoiceStage.show();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException, SQLException {
        iDAO = new AccountingInvoiceDAOImp();
        idDAO = new AccountingInvoiceDescriptionDAOImp();
        cDAO = new AccountingClientDAOImp();

        fillClientComboBox();
        setupInvoice();

        descriptionCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDescriptionProperty());
        priceCol.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
        invoiceNumCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceIDProperty().asObject());
        invoiceDateCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceDateProperty());
        invoiceClientCol.setCellValueFactory(cellData -> cellData.getValue().getClientProperty());
        invoiceSubTotalCol.setCellValueFactory(cellData -> cellData.getValue().getSubtotalProperty());
        invoiceGstCol.setCellValueFactory(cellData -> cellData.getValue().getGstProperty());
        invoiceQstCol.setCellValueFactory(cellData -> cellData.getValue().getQstProperty());
        invoiceTotalCol.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
        invoiceSentCol.setCellValueFactory(cellData -> cellData.getValue().getInvoiceSentProperty().asString());
        invoicePaidCol.setCellValueFactory(cellData -> cellData.getValue().getInvoicePaidProperty());

        descriptionTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDescriptionDetails(newValue));
        clientTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showInvoiceDetails(newValue);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(InvoiceFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        totalField.textProperty().addListener((observable, oldValue, newValue) -> fillSubTotal(newValue));
        subtotalField.textProperty().addListener((observable, oldValue, newValue) -> fillTotal(newValue));
        TextFields.bindAutoCompletion(clientComboBox.getEditor(), clientComboBox.getItems());
    }

    /**
     * Will display invoice descriptions in the table.
     *
     * @param invoiceNumber
     * @throws SQLException
     */
    public void displayDescriptionTable(int invoiceNumber) throws SQLException {
        descriptionTable.setItems(idDAO.findInvoiceDescriptionByInvoiceNumber(invoiceNumber));
    }

    /**
     * Will display invoice clients in the table.
     *
     * @throws SQLException
     */
    public void displayClientTable() throws SQLException {
        clientTable.setItems(iDAO.findAllInvoices());
    }

    /**
     * Listener which will fill the field with the selected Invoice Description.
     *
     * @param invoiceDescription
     */
    private void showDescriptionDetails(InvoiceDescription invoiceDescription) {
        if (invoiceDescription != null) {
            descriptionField.setText(invoiceDescription.getInvoiceDescription());
            priceField.setText(invoiceDescription.getPrice().doubleValue() + "");
            invoiceDescriptionText = descriptionField.getText();
        }
    }

    /**
     * Listener which will fill the field with the selected Invoice.
     *
     * @param invoice
     */
    private void showInvoiceDetails(Invoice invoice) throws SQLException {
        if (invoice != null) {
            id = invoice.getInvoiceID();
            invoiceNumberField.setText(id + "");
            invoiceDateField.setValue(invoice.getInvoiceDate().toLocalDate());
            clientComboBox.setValue(invoice.getClient());
            displayDescriptionTable(id);
            subtotalField.setText(invoice.getSubtotal().toString());
            gstField.setText(invoice.getGst().toString());
            qstField.setText(invoice.getQst().toString());
            totalField.setText(invoice.getTotal().toString());
            sentCheckBox.setSelected(invoice.getInvoiceSent());
            paidField.setText(invoice.getInvoicePaid());
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
        if (subTotalChosen) {
            totalChosen = false;
        }

        if (total.matches("[0-9.]*")) {
            if (!totalField.getText().equals("") && totalChosen && subTotalChosen == false) {
                if (gstField.isDisabled()) {
                    subtotalField.setText(total);
                } else {
                    BigDecimal tot = new BigDecimal(total);

                    BigDecimal subTotal = new BigDecimal(tot.doubleValue() / 1.14975);
                    subTotal = subTotal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal gst = new BigDecimal(subTotal.doubleValue() * 0.05);
                    gst = gst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal qst = new BigDecimal(subTotal.doubleValue() * 0.09975);
                    qst = qst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    gstField.setText(gst.doubleValue() + "");
                    qstField.setText(qst.doubleValue() + "");
                    subtotalField.setText(subTotal.doubleValue() + "");
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
        if (totalChosen) {
            subTotalChosen = false;
        }
        if (subtotal.matches("[0-9.]*")) {
            if (!subtotalField.getText().equals("") && totalChosen == false && subTotalChosen) {
                if (gstField.isDisabled()) {
                    totalField.setText(subtotal);
                } else {
                    BigDecimal st = new BigDecimal(subtotal);

                    BigDecimal total = new BigDecimal(st.doubleValue() * 1.14975);
                    total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal gst = new BigDecimal(st.doubleValue() * 0.05);
                    gst = gst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    BigDecimal qst = new BigDecimal(st.doubleValue() * 0.09975);
                    qst = qst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                    gstField.setText(gst.doubleValue() + "");
                    qstField.setText(qst.doubleValue() + "");
                    totalField.setText(total.doubleValue() + "");
                }
            }
        }
        subTotalChosen = false;
    }

    /**
     * *
     * Method responsible for checking if any required field values are missing
     *
     * @return
     */
    private String checkMissing() {
        String msg = "Please enter the following information:\n";
        if (invoiceDateField == null || invoiceDateField.getValue() == null || invoiceDateField.getValue().equals("")) {
            msg += "- Date\n";
        }
        if (clientComboBox == null || clientComboBox.getValue() == null || clientComboBox.getValue().equals("")) {
            msg += "- Client\n";
        }
        if (subtotalField == null || subtotalField.getText().equals("")) {
            msg += "- Sub Total\n";
        }
        if (totalField == null || totalField.getText().equals("")) {
            msg += "- Total\n";
        }
        if (!subtotalField.getText().equals("") && subtotalField.getText().length() >= 11) {
            msg += "- Sub Total is too large\n";
        }
        if (!totalField.getText().equals("") && totalField.getText().length() >= 11) {
            msg += "- Total is too large\n";
        }

        return msg;
    }

    /**
     * Method which will fill the Client Drop down list with all clients.
     *
     * @throws SQLException
     */
    private void fillClientComboBox() throws SQLException {
        ObservableList<Client> clients = cDAO.findAllClients();
        ObservableList<String> cElements = FXCollections.observableArrayList();
        for (int i = 0; i < clients.size(); i++) {
            cElements.add(clients.get(i).getClientName());
        }
        clientComboBox.setItems(cElements);
    }

    /**
     * Method responsible for setting up the invoice page.
     *
     */
    private void setupInvoice() throws SQLException {
        LocalDate date = LocalDate.now();
        ObservableList<Invoice> invoices = iDAO.findAllInvoices();

        if (invoices.size() > 0) {
            id = invoices.get(invoices.size() - 1).getInvoiceID() + 1;
        } else {
            int year = (date.getYear()) * 1000;
            id = year + 1;
        }

        latestNum = id;

        invoiceNumberField.setText(id + "");
        invoiceDateField.setValue(date);

        displayClientTable();
        displayDescriptionTable(id);
    }

    /**
     * Method responsible for setting the controllers
     *
     * @param clientController
     * @param gstController
     * @param tabPane
     */
    public void setControllers(ClientFXMLController clientController, GSTReportFXMLController gstController, TabPane tabPane) {
        this.clientController = clientController;
        this.gstController = gstController;
        this.tabPane = tabPane;
    }

    /**
     * Method responsible for displaying an alert when an error occurs.
     *
     * @param msg
     */
    private void displayAlert(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Missing Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    /**
     * Method responsible for displaying a confirmation message when the user
     * attempts to delete an invoice description.
     *
     * @param msg
     */
    private void displayConfirmation(String msg) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Invoice Decription");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            InvoiceDescription invoiceDescription = idDAO.findInvoiceDescriptionByName(descriptionField.getText(), id);

            idDAO.deleteInvoiceDescription(invoiceDescription.getInvoiceDescriptionID());
            descriptionField.setText("");
            priceField.setText("");
            displayDescriptionTable(id);
        }
    }

    /**
     * Method responsible for displaying a confirmation message when the user
     * attempts to delete an invoice.
     *
     * @param msg
     */
    private void displayInvoiceConfirmation(String msg) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Invoice");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Invoice invoice = iDAO.findInvoiceById(id);
            List<InvoiceDescription> invoiceDescriptionList = idDAO.findInvoiceDescriptionByInvoiceNumber(id);
            for (int i = 0; i < invoiceDescriptionList.size(); i++) {
                idDAO.deleteInvoiceDescription(invoiceDescriptionList.get(i).getInvoiceDescriptionID());
            }

            iDAO.deleteInvoice(invoice.getInvoiceID());
            onClearInvoice(new ActionEvent());
        }
    }

    /**
     * Method responsible for displaying a confirmation message when the user
     * attempts to save an invoice with a client that's not in the database.
     *
     * @param msg
     */
    private void displayClientConfirmation(String msg) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Create Client");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            clientController.displayTable();
            clientController.setClientName(clientComboBox.getValue());
            setupInvoice();
            tabPane.getSelectionModel().select(4);
        }
    }
}
