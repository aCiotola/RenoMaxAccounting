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
    private StringProperty MainDescriptionName;
    
    public MainDescription()
    {
        this(-1, "");
    }
    
    public MainDescription(final int mainDescriptionID, final String MainDescriptionName)
    {
        super();
        this.mainDescriptionID = new SimpleIntegerProperty(mainDescriptionID);
        this.MainDescriptionName = new SimpleStringProperty(MainDescriptionName);
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
        return MainDescriptionName.get();
    }
    
    public StringProperty getMainDescriptionNameProperty()
    {
        return MainDescriptionName;
    }
    
    public void setMainDescriptionName(final String MainDescriptionName)
    {
         this.MainDescriptionName.set(MainDescriptionName);
    }
}
