package com.ciotola.properties;

import com.ciotola.entities.PropertiesBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.util.Properties;
import org.slf4j.LoggerFactory;

/**
 * Class which contains methods to both create and read Properties
 * 
 * @author Alessandro Ciotola
 * @version 2018/01/15
 * 
 */
public class PropsManager 
{
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    /**
     * Method which loads the Properties file and creates a new Properties object.
     * 
     * @return Properties
     * @throws IOException 
     */
    public final PropertiesBean loadTextProperties() throws IOException 
    {
        PropertiesBean propsBean = new PropertiesBean();
        Properties props = new Properties();

        Path txtFile = get("src/main/resources", "DBMSProperties" + ".properties");

        if (Files.exists(txtFile)) 
        {
            try (InputStream propFileStream = newInputStream(txtFile);) 
            {
                props.load(propFileStream);
            }
            
            propsBean.setDbPassword(props.getProperty("DBPassword"));
            propsBean.setSqlDBUrl(props.getProperty("MySqlDBUrl"));
            propsBean.setdbName(props.getProperty("DBName"));
            propsBean.setdbPort(Integer.valueOf(props.getProperty("DBPort")));
            propsBean.setdbUserName(props.getProperty("DBUserName"));
        }
        return propsBean;
    }

    /**
     * Method which saves the properties to a text file
     * 
     * @param propsBean
     * @throws IOException 
     */
    public final void writeTextProperties(PropertiesBean propsBean) throws IOException 
    {
        Properties prop = new Properties();
        
        prop.setProperty("MySqlDBUrl", propsBean.getSqlDBUrl());
        prop.setProperty("DBName", propsBean.getdbName());
        prop.setProperty("DBPort", String.valueOf(propsBean.getdbPort()));
        prop.setProperty("DBUserName", propsBean.getdbUserName());
        prop.setProperty("DBPassword", propsBean.getDbPassword());

        Path txtFile = get("src/main/resources", "DBMSProperties" + ".properties");
        
        try (OutputStream propFileStream = newOutputStream(txtFile)) 
        {
            prop.store(propFileStream, "DataBase Properties");
        }
    }
}