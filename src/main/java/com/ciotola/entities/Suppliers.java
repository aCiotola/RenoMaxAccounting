package com.ciotola.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shado
 */
public class Suppliers
{
    private IntegerProperty supplierID;
    private StringProperty supplierName;
    
    public Suppliers()
    {
        this(-1, "");
    }
    
    public Suppliers(final int supplierID, final String supplierName)
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
    public String toString()
    {
        return supplierID + " " + supplierName; 
    }
}
