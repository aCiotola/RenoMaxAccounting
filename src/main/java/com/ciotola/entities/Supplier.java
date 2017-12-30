package com.ciotola.entities;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shado
 */
public class Supplier
{
    private IntegerProperty supplierID;
    private StringProperty supplierName;
    
    public Supplier()
    {
        this(-1, "");
    }
    
    public Supplier(final int supplierID, final String supplierName)
    {
        super();
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.supplierName = new SimpleStringProperty(supplierName);
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
    
    public String getSupplierName()
    {
        return supplierName.get();
    }
    
    public StringProperty getSupplierNameProperty()
    {
        return supplierName;
    }
    
    public void setSupplierName(final String supplierName)
    {
         this.supplierName.set(supplierName);
    }
    
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 37 * hash + supplierID.get();
        hash = 37 * hash + Objects.hashCode(this.supplierName);
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
        final Supplier other = (Supplier) obj;
        if (supplierID.get() != other.supplierID.get()) {
            return false;
        }
        if (!supplierName.get().equals(other.supplierName.get())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return supplierID.get() + " " + supplierName.get(); 
    }
}
