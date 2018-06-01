package com.ciotola.persistence.Implementations;

import com.ciotola.entities.PropertiesBean;
import com.ciotola.entities.SubDescription;
import com.ciotola.persistence.Interfaces.IAccountingSubDescriptionDAO;
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
 * DAO implementation class which contains the SubDescription methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/20
 */
public class AccountingSubDescriptionDAOImp implements IAccountingSubDescriptionDAO{
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
    public AccountingSubDescriptionDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }

    /**
     * Method which will insert the Sub Description data into the Sub Descriptions Table.
     * 
     * @param subDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int addSubDescription(SubDescription subDescription) throws SQLException {
        int records = 0;
        String query = "INSERT INTO SUBDESCRIPTION(SUBDESCRIPTIONNAME) VALUES(?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            pStmt.setString(1, subDescription.getSubDescriptionName());
            
            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    subDescription.setSubDescriptionID(recordNum);
                    log.debug("New record added to SUBDESCRIPTION: " + subDescription.toString());
                }
            }
        }catch(SQLException ex){
            log.error("Exception thrown ADDING SUBDESCRIPTION: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    /**
     * Method which will Search the Sub Descriptions table for a record with a certain subDescriptionID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public SubDescription findSubDescriptionById(int id) throws SQLException{
        SubDescription subDescription = new SubDescription();
        String query = "SELECT * FROM SUBDESCRIPTION WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    subDescription = createSubDescription(rs);                    
                    log.debug("Found SUBDESCRIPTION: " + subDescription.getSubDescriptionName());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING SUBDESCRIPTION BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return subDescription;
    }

    /**
     * Method which will return an ObservableList containing every record in the Sub Descriptions table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<SubDescription> findAllSubDescriptions() throws SQLException{
        ObservableList<SubDescription> subDescriptionList = FXCollections.observableArrayList();
        String query = "SELECT * FROM SUBDESCRIPTION";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    SubDescription subDescription = createSubDescription(rs);
                    subDescriptionList.add(subDescription);
                }
                log.debug("Found SUBDESCRIPTIONS: " + subDescriptionList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL SUBDESCRIPTIONS: " + ex.getMessage());
            throw ex; 
        }    
        return subDescriptionList;
    } 

    /**
     * Method which will update a record in the Sub Descriptions table.
     * 
     * @param subDescription
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateSubDescription(SubDescription subDescription) throws SQLException{
        int records = 0; 
        String query = "UPDATE SUBDESCRIPTION SET SUBDESCRIPTIONNAME = ? WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){            
            pStmt.setString(1, subDescription.getSubDescriptionName());
            pStmt.setInt(2, subDescription.getSubDescriptionID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from SUBDESCRIPTION is: " + subDescription.toString());
        }catch(SQLException ex){
            log.debug("Exception UPDATING SUBDESCRIPTION: " + ex.getMessage());
            throw ex;
        }   
        return records;
    }

    /**
     * Method which will delete a record in the Sub Descriptions table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteSubDescription(int id) throws SQLException{
        int records = 0;
        String query = "DELETE FROM SUBDESCRIPTION WHERE SUBDESCRIPTIONID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from SUBDESCRIPTION: " + records + " id: " + id);
        }
        catch(SQLException ex){
            log.debug("Exception DELETING SUBDESCRIPTION: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create a SubDescription object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private SubDescription createSubDescription(ResultSet rs) throws SQLException{
        SubDescription subDescription = new SubDescription();
        subDescription.setSubDescriptionID(rs.getInt("SUBDESCRIPTIONID"));                    
        subDescription.setSubDescriptionName(rs.getString("SUBDESCRIPTIONNAME")); 
        return subDescription;
    }  
}