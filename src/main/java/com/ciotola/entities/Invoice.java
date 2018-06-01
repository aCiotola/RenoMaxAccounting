package com.ciotola.entities;

import com.ciotola.utils.CustomDate;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Invoice class which contains the methods used for getting and setting Invoice data.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 * 
 */
public class Invoice {
    private IntegerProperty invoiceID;
    private ObjectProperty<CustomDate> invoiceDate;
    private StringProperty client;
    private ObjectProperty<BigDecimal> subtotal;
    private ObjectProperty<BigDecimal> gst;
    private ObjectProperty<BigDecimal> qst;
    private ObjectProperty<BigDecimal> total;
    private BooleanProperty invoiceSent;
    private StringProperty invoicePaid;
    
    public Invoice(){
        
        this(-1, new CustomDate(Date.valueOf(LocalDate.now()).getTime()), "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false, "");
    }
    
    public Invoice(final int invoiceID, final CustomDate invoiceDate, final String client,
            final BigDecimal subtotal, final BigDecimal gst, final BigDecimal qst, final BigDecimal total, final boolean invoiceSent, final String invoicePaid){
        super();
        this.invoiceID = new SimpleIntegerProperty(invoiceID);
        this.invoiceDate = new SimpleObjectProperty(invoiceDate);
        this.client = new SimpleStringProperty(client);
        this.subtotal = new SimpleObjectProperty(subtotal);
        this.gst = new SimpleObjectProperty(gst);
        this.qst = new SimpleObjectProperty(qst);
        this.total = new SimpleObjectProperty(total);
        this.invoiceSent = new SimpleBooleanProperty(invoiceSent);
        this.invoicePaid = new SimpleStringProperty(invoicePaid);
    }
    
    public int getInvoiceID(){
        return invoiceID.get();
    }
    
    public IntegerProperty getInvoiceIDProperty(){
        return invoiceID;
    }
    
    public void setInvoiceID(final int invoiceID){
         this.invoiceID.set(invoiceID);
    }

    public CustomDate getInvoiceDate(){
        return invoiceDate.get();
    }
    
    public ObjectProperty<CustomDate> getInvoiceDateProperty(){
        return invoiceDate;
    }
    
    public void setInvoiceDate(final CustomDate invoiceDate){
        this.invoiceDate.set(invoiceDate);
    }
    
    public String getClient(){
        return client.get();
    }
    
    public StringProperty getClientProperty(){
        return client;
    }
    
    public void setClient(final String client){
         this.client.set(client);
    }
    
    public BigDecimal getSubtotal(){
        return subtotal.get();
    }
    
    public ObjectProperty<BigDecimal> getSubtotalProperty(){
        return subtotal;
    }
    
    public void setSubtotal(final BigDecimal subtotal){
         this.subtotal.set(subtotal);
    }
    
    public BigDecimal getGst(){
        return gst.get();
    }
    
    public ObjectProperty<BigDecimal> getGstProperty(){
        return gst;
    }
    
    public void setGst(final BigDecimal gst){
         this.gst.set(gst);
    }
    
    public BigDecimal getQst(){
        return qst.get();
    }
    
    public ObjectProperty<BigDecimal> getQstProperty(){
        return qst;
    }
    
    public void setQst(final BigDecimal qst){
         this.qst.set(qst);
    }
    
    public BigDecimal getTotal(){
        return total.get();
    }
    
    public ObjectProperty<BigDecimal> getTotalProperty(){
        return total;
    }
    
    public void setTotal(final BigDecimal total){
         this.total.set(total);
    }
    
    public boolean getInvoiceSent(){
        return invoiceSent.get();
    }
    
    public BooleanProperty getInvoiceSentProperty(){
        return invoiceSent;
    }
    
    public void setInvoiceSent(final boolean invoiceSent){
         this.invoiceSent.set(invoiceSent);
    }
    
    public String getInvoicePaid(){
        return invoicePaid.get();
    }
    
    public StringProperty getInvoicePaidProperty(){
        return invoicePaid;
    }
    
    public void setInvoicePaid(final String invoicePaid){
         this.invoicePaid.set(invoicePaid);
    }
    
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 37 * hash + invoiceID.get();
        hash = 37 * hash + Objects.hashCode(this.invoiceDate);
        hash = 37 * hash + Objects.hashCode(this.client);
        hash = 37 * hash + Objects.hashCode(this.subtotal);
        hash = 37 * hash + Objects.hashCode(this.gst);
        hash = 37 * hash + Objects.hashCode(this.qst);
        hash = 37 * hash + Objects.hashCode(this.total);
        hash = 37 * hash + Objects.hashCode(this.invoiceSent);
        hash = 37 * hash + Objects.hashCode(this.invoicePaid);
        return hash;
    }

    @Override
    public boolean equals(Object obj){        
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (invoiceID.get() != other.invoiceID.get()) {
            return false;
        }
        if (!invoiceDate.get().equals(other.invoiceDate.get())) {
            return false;
        }
        if (!client.get().equals(other.client.get())) {
            return false;
        }
        if (!subtotal.get().equals(other.subtotal.get())) {
            return false;
        }
        if (!gst.get().equals(other.gst.get())) {
            return false;
        }
         if (!qst.get().equals(other.qst.get())) {
            return false;
        }
        if (!total.get().equals(other.total.get())) {
            return false;
        }
        if (invoiceSent.get() != other.invoiceSent.get()) {
            return false;
        }
        if (!invoicePaid.get().equals(other.invoicePaid.get())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return invoiceID.get() + " " + invoiceDate.get() + " " + client.get() + " " + subtotal.get() + " " + 
                gst.get() + " " + qst.get() + " " + total.get() + " " + invoiceSent.get() + " " + invoicePaid.get(); 
    }
}