package com.ciotola.entities;

import java.math.BigDecimal;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class which contains the methods used for containing the report data.
 *
 * @author Alessandro Ciotola
 * @version 2018/06/01
 *
 */
public class Report {
    private StringProperty mainDescription;
    private ObjectProperty<BigDecimal> subtotal;
    private ObjectProperty<BigDecimal> gst;
    private ObjectProperty<BigDecimal> qst;
    private ObjectProperty<BigDecimal> total;
    
    public Report(){
        this("", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
    
    public Report(final String mainDescription, final BigDecimal subtotal, final BigDecimal gst, final BigDecimal qst, final BigDecimal total) {
        super();
        this.mainDescription = new SimpleStringProperty(mainDescription);
        this.subtotal = new SimpleObjectProperty(subtotal);
        this.gst = new SimpleObjectProperty(gst);
        this.qst = new SimpleObjectProperty(qst);
        this.total = new SimpleObjectProperty(total);
    }   

    public String getMainDescription() {
        return mainDescription.get();
    }
    
    public StringProperty getMainDescriptionProperty() {
        return mainDescription;
    }

    public void setMainDescription(final String mainDescription) {
        this.mainDescription.set(mainDescription);
    }
    
    public BigDecimal getSubtotal() {
        return subtotal.get();
    }

    public ObjectProperty<BigDecimal> getSubtotalProperty() {
        return subtotal;
    }

    public void setSubtotal(final BigDecimal subtotal) {
        this.subtotal.set(subtotal);
    }
    
    public BigDecimal getGst() {
        return subtotal.get();
    }

    public ObjectProperty<BigDecimal> getGstProperty() {
        return gst;
    }

    public void setGst(final BigDecimal gst) {
        this.gst.set(gst);
    }
    
    public BigDecimal getQst() {
        return subtotal.get();
    }

    public ObjectProperty<BigDecimal> getQstProperty() {
        return qst;
    }

    public void setQst(final BigDecimal qst) {
        this.qst.set(qst);
    }

    public BigDecimal getTotal() {
        return subtotal.get();
    }
    
    public ObjectProperty<BigDecimal> getTotalProperty() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total.set(total);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Report other = (Report) obj;
        if (!Objects.equals(this.mainDescription, other.mainDescription)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "mainDescription= " + mainDescription + ", subtotal= " + subtotal + ", gst= " + gst + ", qst= " + qst + ", total= " + total;
    }
}