package com.ciotola.entities;

import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alessandro Ciotola
 */
public class InvoiceDescription 
{
    private IntegerProperty invoiceDescriptionID;
    private IntegerProperty invoiceNumber;
    private StringProperty invoiceDescription;
    private ObjectProperty<BigDecimal> price;
    
    public InvoiceDescription()
    {
        this(-1, -1, "", BigDecimal.ZERO);
    }
    
    public InvoiceDescription(final int invoiceDescriptionID, final int invoiceNumber, final String invoiceDescription, final BigDecimal price)
    {
        super();
        this.invoiceDescriptionID = new SimpleIntegerProperty(invoiceDescriptionID);
        this.invoiceNumber = new SimpleIntegerProperty(invoiceNumber);
        this.invoiceDescription = new SimpleStringProperty(invoiceDescription);
        this.price = new SimpleObjectProperty<>(price);
    }
    
    public int getInvoiceDescriptionID()
    {
        return invoiceDescriptionID.get();
    }
    
    public IntegerProperty getInvoiceDescriptionIDProperty()
    {
        return invoiceDescriptionID;
    }
    
    public void setInvoiceDescriptionID(final int invoiceDescriptionID)
    {
         this.invoiceDescriptionID.set(invoiceDescriptionID);
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
    
    public String getInvoiceDescription()
    {
        return invoiceDescription.get();
    }
    
    public StringProperty getInvoiceDescriptionProperty()
    {
        return invoiceDescription;
    }
    
    public void setInvoiceDescription(final String invoiceDescription)
    {
         this.invoiceDescription.set(invoiceDescription);
    }
    
    public BigDecimal getPrice()
    {
        return price.get();
    }
    
    public ObjectProperty<BigDecimal> getPriceProperty()
    {
        return price;
    }
    
    public void setPrice(final BigDecimal price)
    {
         this.price.set(price);
    }
    
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 37 * hash + invoiceDescriptionID.get();
        hash = 37 * hash + invoiceNumber.get();
        hash = 37 * hash + Objects.hashCode(this.invoiceDescription);
        hash = 37 * hash + Objects.hashCode(this.price);
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
        final InvoiceDescription other = (InvoiceDescription) obj;
        if (invoiceDescriptionID.get() != other.invoiceDescriptionID.get()) {
            return false;
        }
        if (invoiceNumber.get() != other.invoiceNumber.get()) {
            return false;
        }
        if (!invoiceDescription.get().equals(other.invoiceDescription.get())) {
            return false;
        }
        if (!price.get().equals(other.price.get())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return invoiceDescriptionID.get() + " " + invoiceNumber.get() + " " + invoiceDescription.get() + " " + price.get(); 
    }
}
