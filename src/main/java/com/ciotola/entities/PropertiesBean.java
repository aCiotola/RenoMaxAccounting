package com.ciotola.entities;

/**
 * PropertiesBean class which contains the methods used for getting and setting PropertyBean data.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 * 
 */
public class PropertiesBean{
    private String dbPassword;
    private int dbPort;
    private String mySqlDBUrl;
    private String dbUserName;
    private String dbName;

    public PropertiesBean(){
        this("", -1, "", "", "");
    }
    
    public PropertiesBean(final String dbPassword, final int dbPort, final String mySqlDBUrl, final String dbUserName, final String dbName){
        this.dbPassword = dbPassword;
        this.dbPort = dbPort;
        this.mySqlDBUrl = mySqlDBUrl;
        this.dbUserName = dbUserName;
        this.dbName = dbName;
    }
    
    public final String getDbPassword() {
        return dbPassword;
    }

    public final void setDbPassword(final String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    public final int getdbPort() {
        return dbPort;
    }

    public final void setdbPort(final int dbPort) {
        this.dbPort = dbPort;
    }
    
    public final String getSqlDBUrl() {
        return mySqlDBUrl;
    }

    public final void setSqlDBUrl(final String mySqlDBUrl) {
        this.mySqlDBUrl = mySqlDBUrl;
    }
    
    public final String getdbUserName() {
        return dbUserName;
    }

    public final void setdbUserName(final String dbUserName) {
        this.dbUserName = dbUserName;
    }
    
    public final String getdbName() {
        return dbName;
    }

    public final void setdbName(final String dbName) {
        this.dbName = dbName;
    }
    
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dbPassword == null) ? 0 : dbPassword.hashCode());
        result = prime * result + dbPort;
        result = prime * result + ((mySqlDBUrl == null) ? 0 : mySqlDBUrl.hashCode());
        result = prime * result + ((dbUserName == null) ? 0 : dbUserName.hashCode());
        result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PropertiesBean other = (PropertiesBean) obj;
        if (dbPassword == null) {
            if (other.dbPassword != null) {
                return false;
            }
        } else if (!dbPassword.equals(other.dbPassword)) {
            return false;
        }
        
        if (dbPort != dbPort) {
            return false;
        }
        if (mySqlDBUrl == null) {
            if (other.mySqlDBUrl != null) {
                return false;
            }
        } else if (!mySqlDBUrl.equals(other.mySqlDBUrl)) {
            return false;
        }
        if (dbUserName == null) {
            if (other.dbUserName != null) {
                return false;
            }
        } else if (!dbUserName.equals(other.dbUserName)) {
            return false;
        }
        if (dbName == null) {
            if (other.dbName != null) {
                return false;
            }
        } else if (!dbName.equals(other.dbName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PropertiesBean{" + "dbPassword=" + dbPassword + ", dbPort=" + dbPort + ", mySqlDBUrl=" + mySqlDBUrl + 
                "dbUserName=" + dbUserName + "dbName=" + dbName +'}';
    }
}