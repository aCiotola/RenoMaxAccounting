package com.ciotola.entities;

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
    public String toString()
    {
        return mainDescriptionID + " " + mainDescriptionName; 
    }
}
