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
public class MainDescription 
{
    private IntegerProperty mainDescriptionID;
    private StringProperty mainDescriptionName;
    
    public MainDescription()
    {
        this(-1, "");
    }
    
    public MainDescription(final int mainDescriptionID, final String mainDescriptionName)
    {
        super();
        this.mainDescriptionID = new SimpleIntegerProperty(mainDescriptionID);
        this.mainDescriptionName = new SimpleStringProperty(mainDescriptionName);
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
    
    public String getMainDescriptionName()
    {
        return mainDescriptionName.get();
    }
    
    public StringProperty getMainDescriptionNameProperty()
    {
        return mainDescriptionName;
    }
    
    public void setMainDescriptionName(final String mainDescriptionName)
    {
         this.mainDescriptionName.set(mainDescriptionName);
    }
    
    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 37 * hash + mainDescriptionID.get();
        hash = 37 * hash + Objects.hashCode(this.mainDescriptionName);
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
        final MainDescription other = (MainDescription) obj;
        if (mainDescriptionID.get() != other.mainDescriptionID.get()) {
            return false;
        }
        if (!mainDescriptionName.get().equals(other.mainDescriptionName.get())) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        return mainDescriptionID.get() + " " + mainDescriptionName.get(); 
    }
}
