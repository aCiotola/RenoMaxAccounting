package com.ciotola.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author shado
 */
public class Expense 
{
    private IntegerProperty expenseID;
    private ObjectProperty<Date> dateTime;
    private IntegerProperty supplierID;
    private IntegerProperty mainDescriptionID;
    private IntegerProperty subDescriptionID;
    private ObjectProperty<BigDecimal> subtotal;
    private ObjectProperty<BigDecimal> gst;
    private ObjectProperty<BigDecimal> qst;
    private ObjectProperty<BigDecimal> total;
    
    public Expense()
    {
        this(-1, Date.valueOf(LocalDate.now()), -1, -1, -1, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public Expense(final int expenseID, final Date dateTime, final int supplierID, final int mainDescriptionID, 
            final int subDescriptionID, final BigDecimal subtotal, final BigDecimal gst, final BigDecimal qst, final BigDecimal total)
    {
        super();
        this.expenseID = new SimpleIntegerProperty(expenseID);
        this.dateTime = new SimpleObjectProperty(dateTime);
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.mainDescriptionID = new SimpleIntegerProperty(mainDescriptionID);
        this.subDescriptionID = new SimpleIntegerProperty(subDescriptionID);
        this.subtotal = new SimpleObjectProperty(subtotal);
        this.gst = new SimpleObjectProperty(gst);
        this.qst = new SimpleObjectProperty(qst);
        this.total = new SimpleObjectProperty(total);
    }
    
    public int getExpenseID()
    {
        return expenseID.get();
    }
    
    public IntegerProperty getExpenseIDProperty()
    {
        return expenseID;
    }
    
    public void setExpenseID(final int expenseID)
    {
         this.expenseID.set(expenseID);
    }

    public Date getDateTime()
    {
        return dateTime.get();
    }
    
    public ObjectProperty<Date> getDateTimeProperty()
    {
        return dateTime;
    }
    
    public void setDateTime(final Date dateTime)
    {
         this.dateTime.set(dateTime);
    }
    
    public int getSupplierID()
    {
        return supplierID.get();
    }
    
    public IntegerProperty getSupplierIDProperty()
    {
        return supplierID;
    }
    
    public void setSupplierID(final int supplierID)
    {
         this.supplierID.set(supplierID);
    }
    
    public int getMainDescriptionID()
    {
        return mainDescriptionID.get();
    }
    
    public IntegerProperty getMainDescriptionIDProperty()
    {
        return mainDescriptionID;
    }
    
    public void setMainDescriptionID(final int mainDescriptionID)
    {
         this.mainDescriptionID.set(mainDescriptionID);
    }
    
    public int getSubDescriptionID()
    {
        return subDescriptionID.get();
    }
    
    public IntegerProperty getSubDescriptionIDProperty()
    {
        return subDescriptionID;
    }
    
    public void setSubDescriptionID(final int subDescriptionID)
    {
         this.subDescriptionID.set(subDescriptionID);
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
    
    @Override
    public String toString()
    {
        return expenseID + " " + dateTime + " " + supplierID + " " + mainDescriptionID + " " + subDescriptionID +
                " " + subtotal + " " + gst + " " + qst + " " + total; 
    }
}