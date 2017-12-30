package com.ciotola.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shado
 */
public class Client
{
    private IntegerProperty clientID; 
    private StringProperty clientName; 
    private StringProperty street; 
    private StringProperty city; 
    private StringProperty province; 
    private StringProperty postalCode; 
    private StringProperty homePhone; 
    private StringProperty cellPhone; 
    private StringProperty email;
    
    public Client()
    {
        this(-1, "", "", "", "", "", "", "", "");
    }
    
    public Client(final int clientID, final String clientName, final String street, final String city, 
            final String province, final String postalCode, final String homePhone, final String cellPhone, final String email)
    {
        super();
        this.clientID = new SimpleIntegerProperty(clientID);
        this.clientName = new SimpleStringProperty(clientName);
        this.street = new SimpleStringProperty(street);
        this.city = new SimpleStringProperty(city);
        this.province = new SimpleStringProperty(province);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.homePhone = new SimpleStringProperty(homePhone);
        this.cellPhone = new SimpleStringProperty(cellPhone);
        this.email = new SimpleStringProperty(email);
    }
    
    public int getClientID()
    {
        return clientID.get();
    }
    
    public IntegerProperty getClientIDProperty()
    {
        return clientID;
    }
    
    public void setClientID(final int clientID)
    {
         this.clientID.set(clientID);
    }

    public String getClientName()
    {
        return clientName.get();
    }
    
    public StringProperty getClientNameProperty()
    {
        return clientName;
    }
    
    public void setClientName(final String clientName)
    {
         this.clientName.set(clientName);
    }
    
    public String getStreet()
    {
        return street.get();
    }
    
    public StringProperty getStreetIDProperty()
    {
        return street;
    }
    
    public void setStreet(final String street)
    {
         this.street.set(street);
    }
    
    public String getCity()
    {
        return city.get();
    }
    
    public StringProperty getCityProperty()
    {
        return city;
    }
    
    public void setCity(final String city)
    {
         this.city.set(city);
    }
    
    public String getProvince()
    {
        return province.get();
    }
    
    public StringProperty getProvinceProperty()
    {
        return province;
    }
    
    public void setProvince(final String province)
    {
         this.province.set(province);
    }
    
    public String getPostalCode()
    {
        return postalCode.get();
    }
    
    public StringProperty getPostalCodeProperty()
    {
        return postalCode;
    }
    
    public void setPostalCode(final String postalCode)
    {
         this.postalCode.set(postalCode);
    }
    
    public String getHomePhone()
    {
        return homePhone.get();
    }
    
    public StringProperty getHomePhoneProperty()
    {
        return homePhone;
    }
    
    public void setHomePhone(final String homePhone)
    {
         this.homePhone.set(homePhone);
    }
    
    public String getCellPhone()
    {
        return cellPhone.get();
    }
    
    public StringProperty getCellPhoneProperty()
    {
        return cellPhone;
    }
    
    public void setCellPhone(final String cellPhone)
    {
         this.cellPhone.set(cellPhone);
    }
    
    public String getEmail()
    {
        return email.get();
    }
    
    public StringProperty getEmailProperty()
    {
        return email;
    }
    
    public void setEmail(final String email)
    {
         this.email.set(email);
    }
    
    @Override
    public String toString()
    {
        return clientID.get() + " " + clientName.get() + " " + street.get() + " " + city.get() + " " + province.get() +
                " " + postalCode.get() + " " + homePhone.get() + " " + cellPhone.get() + " " + email.get(); 
    }
}
