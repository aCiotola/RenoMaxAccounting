package com.ciotola.entities;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shado
 */
public class Expense 
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());  
    private IntegerProperty expenseID;
    private ObjectProperty<Date> dateTime;
    private StringProperty supplier;
    private StringProperty mainDescription;
    private StringProperty subDescription;
    private ObjectProperty<BigDecimal> subtotal;
    private ObjectProperty<BigDecimal> gst;
    private ObjectProperty<BigDecimal> qst;
    private ObjectProperty<BigDecimal> total;
    
    public Expense()
    {
        this(-1, Date.valueOf(LocalDate.now()), "", "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public Expense(final int expenseID, final Date dateTime, final String supplier, final String mainDescription, 
            final String subDescription, final BigDecimal subtotal, final BigDecimal gst, final BigDecimal qst, final BigDecimal total)
    {
        super();
        this.expenseID = new SimpleIntegerProperty(expenseID);
        this.dateTime = new SimpleObjectProperty(dateTime);
        this.supplier = new SimpleStringProperty(supplier);
        this.mainDescription = new SimpleStringProperty(mainDescription);
        this.subDescription = new SimpleStringProperty(subDescription);
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
    
    public String getSupplier()
    {
        return supplier.get();
    }
    
    public StringProperty getSupplierProperty()
    {
        return supplier;
    }
    
    public void setSupplier(final String supplier)
    {
         this.supplier.set(supplier);
    }
    
    public String getMainDescription()
    {
        return mainDescription.get();
    }
    
    public StringProperty getMainDescriptionProperty()
    {
        return mainDescription;
    }
    
    public void setMainDescription(final String mainDescription)
    {
         this.mainDescription.set(mainDescription);
    }
    
    public String getSubDescription()
    {
        return subDescription.get();
    }
    
    public StringProperty getSubDescriptionProperty()
    {
        return subDescription;
    }
    
    public void setSubDescription(final String subDescription)
    {
         this.subDescription.set(subDescription);
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
    public int hashCode() 
    {
        int hash = 7;
        hash = 37 * hash + expenseID.get();
        hash = 37 * hash + Objects.hashCode(this.dateTime);
        hash = 37 * hash + Objects.hashCode(this.supplier);
        hash = 37 * hash + Objects.hashCode(this.mainDescription);
        hash = 37 * hash + Objects.hashCode(this.subDescription);
        hash = 37 * hash + Objects.hashCode(this.subtotal);
        hash = 37 * hash + Objects.hashCode(this.gst);
        hash = 37 * hash + Objects.hashCode(this.qst);
        hash = 37 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {        
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Expense other = (Expense) obj;
        if (expenseID.get() != other.expenseID.get()) {
            return false;
        }
        if (!dateTime.get().equals(other.dateTime.get())) {
            return false;
        }
        if (!supplier.get().equals(other.supplier.get())) {
            return false;
        }
        if (!mainDescription.get().equals(other.mainDescription.get())) {
            return false;
        }
        if (!subDescription.get().equals(other.subDescription.get())) {
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
        return true;
    }
    
    @Override
    public String toString()
    {
        return expenseID.get() + " " + dateTime.get() + " " + supplier.get() + " " + mainDescription.get() + " " + subDescription.get() +
                " " + subtotal.get() + " " + gst.get() + " " + qst.get() + " " + total.get(); 
    }
}