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
    public int hashCode() 
    {
        int hash = 7;
        hash = 37 * hash + subDescriptionID.get();
        hash = 37 * hash + Objects.hashCode(this.subDescriptionName);
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
        final SubDescription other = (SubDescription) obj;
        if (subDescriptionID.get() != other.subDescriptionID.get()) {
            return false;
        }
        if (!subDescriptionName.get().equals(other.subDescriptionName.get())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return subDescriptionID.get() + " " + subDescriptionName.get(); 
    }
}
