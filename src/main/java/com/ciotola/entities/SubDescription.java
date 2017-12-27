package com.ciotola.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shado
 */
public class SubDescription 
{
    private IntegerProperty subDescriptionID;
    private StringProperty subDescriptionName;
    
    public SubDescription()
    {
        this(-1, "");
    }
    
    public SubDescription(final int subDescriptionID, final String subDescriptionName)
    {
        super();
        this.subDescriptionID = new SimpleIntegerProperty(subDescriptionID);
        this.subDescriptionName = new SimpleStringProperty(subDescriptionName);
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
    
    public String getSubDescriptionName()
    {
        return subDescriptionName.get();
    }
    
    public StringProperty getSubDescriptionNameProperty()
    {
        return subDescriptionName;
    }
    
    public void setSubDescriptionName(final String subDescriptionName)
    {
         this.subDescriptionName.set(subDescriptionName);
    }
    
    @Override
    public String toString()
    {
        return subDescriptionID + " " + subDescriptionName; 
    }
}
