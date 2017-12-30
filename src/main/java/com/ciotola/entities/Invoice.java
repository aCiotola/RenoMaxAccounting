/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ciotola.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author shado
 */
public class Invoice 
{
    private IntegerProperty invoiceID;
    private IntegerProperty invoiceNumber;
    private ObjectProperty<Date> invoiceDate;
    private IntegerProperty clientID;
    private ObjectProperty<BigDecimal> subtotal;
    private ObjectProperty<BigDecimal> gst;
    private ObjectProperty<BigDecimal> qst;
    private ObjectProperty<BigDecimal> total;
    private BooleanProperty invoiceSent;
    
    public Invoice()
    {
        this(-1, -1, Date.valueOf(LocalDate.now()), -1, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false);
    }
    
    public Invoice(final int invoiceID, final int invoiceNumber, final Date invoiceDate, final int clientID,
            final BigDecimal subtotal, final BigDecimal gst, final BigDecimal qst, final BigDecimal total, final boolean invoiceSent)
    {
        super();
        this.invoiceID = new SimpleIntegerProperty(invoiceID);
        this.invoiceNumber = new SimpleIntegerProperty(invoiceNumber);
        this.invoiceDate = new SimpleObjectProperty(invoiceDate);
        this.clientID = new SimpleIntegerProperty(clientID);
        this.subtotal = new SimpleObjectProperty(subtotal);
        this.gst = new SimpleObjectProperty(gst);
        this.qst = new SimpleObjectProperty(qst);
        this.total = new SimpleObjectProperty(total);
        this.invoiceSent = new SimpleBooleanProperty(invoiceSent);
    }
    
    public int getInvoiceID()
    {
        return invoiceID.get();
    }
    
    public IntegerProperty getInvoiceIDProperty()
    {
        return invoiceID;
    }
    
    public void setInvoiceID(final int invoiceID)
    {
         this.invoiceID.set(invoiceID);
    }
    
    public int getInvoiceNumber()
    {
        return invoiceNumber.get();
    }
    
    public IntegerProperty getInvoiceNumberProperty()
    {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(final int invoiceNumber)
    {
         this.invoiceNumber.set(invoiceNumber);
    }

    public Date getInvoiceDate()
    {
        return invoiceDate.get();
    }
    
    public ObjectProperty<Date> getInvoiceDateProperty()
    {
        return invoiceDate;
    }
    
    public void setInvoiceDate(final Date invoiceDate)
    {
         this.invoiceDate.set(invoiceDate);
    }
    
    public int getClientID()
    {
        return clientID.get();
    }
    
    public IntegerProperty getClientIDProperty()
    {
        return clientID;
    }
    
    public void setClientID(final int clientID)
    {
         this.clientID.set(clientID);
    }
    
    public BigDecimal getSubtotal()
    {
        return subtotal.get();
    }
    
    public ObjectProperty<BigDecimal> getSubtotalProperty()
    {
        return subtotal;
    }
    
    public void setSubtotal(final BigDecimal subtotal)
    {
         this.subtotal.set(subtotal);
    }
    
    public BigDecimal getGst()
    {
        return gst.get();
    }
    
    public ObjectProperty<BigDecimal> getGstProperty()
    {
        return gst;
    }
    
    public void setGst(final BigDecimal gst)
    {
         this.gst.set(gst);
    }
    
    public BigDecimal getQst()
    {
        return qst.get();
    }
    
    public ObjectProperty<BigDecimal> getQstProperty()
    {
        return qst;
    }
    
    public void setQst(final BigDecimal qst)
    {
         this.qst.set(qst);
    }
    
    public BigDecimal getTotal()
    {
        return total.get();
    }
    
    public ObjectProperty<BigDecimal> getTotalProperty()
    {
        return total;
    }
    
    public void setTotal(final BigDecimal total)
    {
         this.total.set(total);
    }
    
    public boolean getInvoiceSent()
    {
        return invoiceSent.get();
    }
    
    public BooleanProperty getInvoiceSentProperty()
    {
        return invoiceSent;
    }
    
    public void setInvoiceSent(final boolean invoiceSent)
    {
         this.invoiceSent.set(invoiceSent);
    }
    
    @Override
    public String toString()
    {
        return invoiceID.get() + " " + invoiceNumber.get() + " " + invoiceDate.get() + " " + clientID.get() + " " + 
                subtotal.get() + " " + gst.get() + " " + qst.get() + " " + total.get() + " " + invoiceSent.get(); 
    }
}
