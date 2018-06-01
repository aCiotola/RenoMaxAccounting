package com.ciotola.persistence.Implementations;

import com.ciotola.entities.MainDescription;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.persistence.Interfaces.IAccountingMainDescriptionDAO;
import com.ciotola.properties.PropsManager;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO implementation class which contains the MainDescription methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingMainDescriptionDAOImp implements IAccountingMainDescriptionDAO{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());   
    private final PropsManager pm = new PropsManager();
    private final PropertiesBean propsBean;
    
    private String url = "";
    private String user = "";
    private String password = "";
    
    /**
     * No parameter constructor which gets the database information from the
     * properties bean and sets the database variables.
     * 
     * @throws IOException 
     */
    public AccountingMainDescriptionDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }

    /**
     * Method which will insert the Main Description data into the Main Descriptions Table.
     * 
     * @param mainDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int addMainDescription(MainDescription mainDescription) throws SQLException {
        int records = 0;
        String query = "INSERT INTO MAINDESCRIPTION(MAINDESCRIPTIONNAME) VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            pStmt.setString(1, mainDescription.getMainDescriptionName());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    mainDescription.setMainDescriptionID(recordNum);
                    log.debug("New record added to MAINDESCRIPTION: " + mainDescription.toString());
                }
            }
        }catch(SQLException ex){
            log.error("Exception thrown ADDING MAINDESCRIPTION: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    /**
     * Method which will Search the Main Descriptions table for a record with a certain mainDescriptionID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public MainDescription findMainDescriptionById(int id) throws SQLException{
        MainDescription mainDescription = new MainDescription();
        String query = "SELECT * FROM MAINDESCRIPTION WHERE MAINDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    mainDescription = createMainDescription(rs);                    
                    log.debug("Found MAINDESCRIPTION: " + mainDescription.getMainDescriptionName());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING MAINDESCRIPTION BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescription;
    }

    /**
     * Method which will return an ObservableList containing every record in the Main Descriptions table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<MainDescription> findAllMainDescriptions() throws SQLException{
        ObservableList<MainDescription> mainDescriptionList = FXCollections.observableArrayList();
        String query = "SELECT * FROM MAINDESCRIPTION";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    MainDescription mainDescription = createMainDescription(rs);
                    mainDescriptionList.add(mainDescription);
                }
                log.debug("Found MAINDESCRIPTIONS: " + mainDescriptionList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL MAINDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescriptionList;
    }

    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public MainDescription findMainDescriptionByName(String name) throws SQLException{
        MainDescription mainDescription = new MainDescription();
        String query = "SELECT * FROM MAINDESCRIPTION WHERE MAINDESCRIPTIONNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()){
                if(rs.next()){                    
                    mainDescription = createMainDescription(rs);
                    log.debug("Found MAIN DESCRIPTION: " + mainDescription.getMainDescriptionName());
                }
            }
        }catch(SQLException ex){
            log.error("Exception FINDING MAIN DESCRIPTION BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return mainDescription;
    }
    
    /**
     * Method which will take data from the ResultSet and create a MainDescription object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private MainDescription createMainDescription(ResultSet rs) throws SQLException{
        MainDescription mainDescription = new MainDescription();
        mainDescription.setMainDescriptionID(rs.getInt("MAINDESCRIPTIONID"));                    
        mainDescription.setMainDescriptionName(rs.getString("MAINDESCRIPTIONNAME")); 
        return mainDescription;
    }  
}