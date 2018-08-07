package com.ciotola.controller;

import com.ciotola.entities.Client;
import com.ciotola.entities.Invoice;
import com.ciotola.entities.InvoiceDescription;
import com.ciotola.persistence.Implementations.AccountingInvoiceDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDAO;
import com.ciotola.persistence.Implementations.AccountingInvoiceDescriptionDAOImp;
import com.ciotola.persistence.Interfaces.IAccountingInvoiceDescriptionDAO;
import com.ciotola.renomaxaccounting.MainAppFX;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import jodd.mail.Email;
import jodd.mail.EmailAttachment;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class which contains the methods used for displaying the Invoice Template.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 * 
 */
public class InvoiceTemplateFXMLController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private IAccountingInvoiceDAO iDAO;
    private IAccountingInvoiceDescriptionDAO idDAO;
    private int id = -1;
    private String date;
    private Client client;
    private Stage iStage;

    @FXML // fx:id="dateField"
    private Label dateField; // Value injected by FXMLLoader

    @FXML // fx:id="invoiceNumberField"
    private Label invoiceNumberField; // Value injected by FXMLLoader

    @FXML // fx:id="locationField"
    private Label locationField; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionField"
    private Label descriptionField; // Value injected by FXMLLoader

    @FXML // fx:id="expenseField"
    private Label expenseField; // Value injected by FXMLLoader

    @FXML // fx:id="paidField"
    private Label paidField; // Value injected by FXMLLoader

    @FXML // fx:id="subtotalField"
    private Label subtotalField; // Value injected by FXMLLoader

    @FXML // fx:id="gstField"
    private Label gstField; // Value injected by FXMLLoader

    @FXML // fx:id="tvqField"
    private Label tvqField; // Value injected by FXMLLoader

    @FXML // fx:id="totalField"
    private Label totalField; // Value injected by FXMLLoader

    @FXML
    void onPrintPage(ActionEvent event) throws IOException, SQLException {
        // Create the Printer Job
        PrinterJob job = PrinterJob.createPrinterJob();
        JobSettings jobSettings = job.getJobSettings();
        Printer printer = job.getPrinter();

        // Create the Page Layout of the Printer
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        jobSettings.setPageLayout(pageLayout);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));
        Parent parent = (AnchorPane) loader.load();

        InvoiceTemplateFXMLController controller = loader.getController();
        controller.setInvoiceTemplateFXMLController(client, id, iStage);
        
        job.showPrintDialog(iStage); 
        job.endJob();   
    }

    @FXML
    void onSaveInvoice(ActionEvent event) throws IOException, SQLException {
        saveInvoiceAsImage();   
    }
    
    @FXML
    void onSendEmail(ActionEvent event) throws IOException, SQLException {        
        saveInvoiceAsImage();
    
        Email email = Email.create()
        .from("shadowman2100000@gmail.com")
        .to("alessandromciotola@gmail.com")
        .subject("Email test")
        .textMessage("Hello!")
        .attachment(EmailAttachment.with()
            .content("Invoices/" + client.getClientName() + " - " + date + ".jpg"));
        
        SmtpServer smtpServer = MailServer.create()
            .ssl(true)
            .host("smtp.gmail.com")
            .auth("shadowman2100000@gmail.com", "shadowx15")
            .buildSmtpMailServer();
        
        try (SendMailSession session = smtpServer.createSession()) {
            session.open();
            session.sendMail(email);
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws IOException {
        iDAO = new AccountingInvoiceDAOImp();
        idDAO = new AccountingInvoiceDescriptionDAOImp();
    }

    /**
     * Method responsible for setting up the invoice template, ready for
     * printing or sending.
     *
     * @param client
     * @param id
     * @param iStage
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public void setInvoiceTemplateFXMLController(Client client, int id, Stage iStage) throws IOException, SQLException {
        if (id != -1) {
            this.id = id;
            this.client = client;
            this.iStage = iStage;
        }

        Invoice invoice = iDAO.findInvoiceById(id);
        List<InvoiceDescription> invoiceDescriptions = idDAO.findInvoiceDescriptionByInvoiceNumber(id);

        dateField.setText(invoice.getInvoiceDate().toString());
        date = invoice.getInvoiceDate().toString();
        invoiceNumberField.setText(invoice.getInvoiceID()+ "");
        locationField.setText(client.getClientName() + "\n" + client.getStreet() + "\n" + client.getCity() + ", " + client.getProvince() + "\n" + client.getPostalCode());

        String descriptions = "";
        String prices = "";
        for (int i = 0; i < invoiceDescriptions.size(); i++) {
            descriptions += invoiceDescriptions.get(i).getInvoiceDescription() + "\n\n";
            prices += invoiceDescriptions.get(i).getPrice().toString() + "\n\n";
        }

        descriptionField.setText(descriptions);
        expenseField.setText(prices);

        subtotalField.setText(invoice.getSubtotal().toString());
        gstField.setText(invoice.getGst().toString());
        tvqField.setText(invoice.getQst().toString());
        totalField.setText(invoice.getTotal().toString());
        paidField.setText(invoice.getInvoicePaid());
    }
    
    /**
     * Method responsible for saving the invoice as an image in order to be used for 
     * sending emails or saving the image.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void saveInvoiceAsImage() throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainAppFX.class.getResource("/fxml/InvoiceTemplateFXML.fxml"));
        Parent parent = (AnchorPane) loader.load();

        InvoiceTemplateFXMLController controller = loader.getController();
        controller.setInvoiceTemplateFXMLController(client, id, iStage);
        
        Image fxmlImage = new Scene(parent).snapshot(null);
        
        BufferedImage image = SwingFXUtils.fromFXImage(fxmlImage, null);
        BufferedImage imageRGB = new BufferedImage(image.getWidth(), image.getHeight() - 100, BufferedImage.OPAQUE);
        Graphics2D graphics = imageRGB.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        
        File dir = new File("Invoices");
        if (!dir.exists())
            dir.mkdirs();
        
        ImageIO.write(imageRGB, "jpg", new File("Invoices/" + client.getClientName() + " - " + date + ".jpg"));
        graphics.dispose();  
    }            
}