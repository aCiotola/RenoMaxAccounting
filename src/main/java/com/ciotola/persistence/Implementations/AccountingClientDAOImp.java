package com.ciotola.persistence.Implementations;

import com.ciotola.entities.Client;
import com.ciotola.entities.PropertiesBean;
import com.ciotola.persistence.Interfaces.IAccountingClientDAO;
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
 * DAO implementation class which contains the Client methods required to interact with the database.
 * Gets database information from the properties file.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public class AccountingClientDAOImp implements IAccountingClientDAO{
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
    public AccountingClientDAOImp() throws IOException{
        super();
        propsBean = pm.loadTextProperties();
        url = propsBean.getSqlDBUrl();
        user = propsBean.getdbUserName();
        password = propsBean.getDbPassword();
    }
    
    /**
     * Method which will insert the client data into the Clients Table.
     * 
     * @param client
     * @return
     * @throws SQLException 
     */
    @Override
    public int addClient(Client client) throws SQLException{
        int records = 0;
        String query = "INSERT INTO CLIENTS(CLIENTNAME, STREET, CITY, PROVINCE, POSTALCODE, HOMEPHONE, CELLPHONE, EMAIL) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement pStmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){            
            pStmt.setString(1, client.getClientName());
            pStmt.setString(2, client.getStreet());
            pStmt.setString(3, client.getCity());
            pStmt.setString(4, client.getProvince());
            pStmt.setString(5, client.getPostalCode());
            pStmt.setString(6, client.getHomePhone());
            pStmt.setString(7, client.getCellPhone());
            pStmt.setString(8, client.getEmail());

            records = pStmt.executeUpdate();
            try(ResultSet rs = pStmt.getGeneratedKeys();){
                if(rs.next()){
                    int recordNum = rs.getInt(1);
                    client.setClientID(recordNum);
                    log.debug("New record added to CLIENTS: " + client.toString());
                }
            }            
        }catch(SQLException ex){
            log.error("Exception thrown ADDING CLIENTS: " + ex.getMessage());
            throw ex;
        }
        return records; 
    }

    /**
     * Method which will Search the Clients table for a record with a certain clientID.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public Client findClientById(int id) throws SQLException{
        Client client = new Client();
        String query = "SELECT * FROM CLIENTS WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            try (ResultSet rs = pStmt.executeQuery()){
                if (rs.next()){                    
                    client = createClient(rs);                    
                    log.debug("Found CLIENT: " + client.getClientName());
                }
            }
        }catch(SQLException ex){
            log.debug("Exception FINDING CLIENT BY ID: " + ex.getMessage());
            throw ex; 
        }    
        return client;
    }

    /**
     * Method which will return an ObservableList containing every record in the Clients table.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Client> findAllClients() throws SQLException{
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Client client;
        String query = "SELECT * FROM CLIENTS ORDER BY CLIENTNAME ASC";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    client = createClient(rs);
                    clientList.add(client);                    
                }
                log.debug("Found CLIENTS: " + clientList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING ALL CLIENTS: " + ex.getMessage());
            throw ex; 
        }    
        return clientList;
    }

    /**
     * Method which will return an ObservableList containing every record in the Clients table
     * which has a similar name to the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public ObservableList<Client> findClientLikeName(String name) throws SQLException{
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        Client client;
        String query = "SELECT * FROM CLIENTS WHERE CLIENTNAME LIKE ? ORDER BY CLIENTNAME ASC";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pStmt.executeQuery()){
                while(rs.next()){                    
                    client = createClient(rs);
                    clientList.add(client);                    
                }
                log.debug("Found CLIENTS: " + clientList.size());
            }
        }catch(SQLException ex){
            log.error("Exception FINDING CLIENT LIKE NAME: " + ex.getMessage());
            throw ex; 
        }    
        return clientList;
    }

    /**
     * Method which will return the record which contains the given name.
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    @Override
    public Client findClientByName(String name) throws SQLException{
        Client client = new Client();
        String query = "SELECT * FROM CLIENTS WHERE CLIENTNAME = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setString(1, name);
            try (ResultSet rs = pStmt.executeQuery()){
                if(rs.next()){                    
                    client = createClient(rs);
                    log.debug("Found CLIENT: " + client.getClientName());
                }
            }
        }catch(SQLException ex){
            log.error("Exception FINDING CLIENT BY NAME: " + ex.getMessage());
            throw ex; 
        }    
        return client;
    }

    /**
     * Method which will update a record in the Clients table.
     * 
     * @param client
     * @return
     * @throws SQLException 
     */
    @Override
    public int updateClient(Client client) throws SQLException{
        int records = 0;
        String query = "UPDATE CLIENTS SET CLIENTNAME = ?, STREET = ?, CITY = ?, PROVINCE = ?, " +
                "POSTALCODE = ?, HOMEPHONE = ?, CELLPHONE = ?, EMAIL = ? WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){            
            pStmt.setString(1, client.getClientName());
            pStmt.setString(2, client.getStreet());
            pStmt.setString(3, client.getCity());
            pStmt.setString(4, client.getProvince());
            pStmt.setString(5, client.getPostalCode());
            pStmt.setString(6, client.getHomePhone());
            pStmt.setString(7, client.getCellPhone());
            pStmt.setString(8, client.getEmail());
            pStmt.setInt(9, client.getClientID());
            
            records = pStmt.executeUpdate();
            log.debug("Record updated from CLIENTS is: " + client.toString());
        }catch(SQLException ex){
            log.debug("Exception UPDATING CLIENTS: " + ex.getMessage());
            throw ex;
        }   
        return records; 
    }

    /**
     * Method which will delete a record in the Clients table.
     * 
     * @param id
     * @return
     * @throws SQLException 
     */
    @Override
    public int deleteClient(int id) throws SQLException{
        int records = 0;
        String query = "DELETE FROM CLIENTS WHERE CLIENTID = ?";
        try(Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement pStmt = connection.prepareStatement(query);){
            pStmt.setInt(1, id);
            
            records = pStmt.executeUpdate();
            log.debug("Records Deleted from CLIENTS: " + records + " id: " + id);
        }catch(SQLException ex){
            log.debug("Exception DELETING CLIENTS: " + ex.getMessage());
            throw ex; 
        }   
        return records;
    }
    
    /**
     * Method which will take data from the ResultSet and create a Client object.
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Client createClient(ResultSet rs) throws SQLException{
        Client client = new Client();
        client.setClientID(rs.getInt("CLIENTID"));                    
        client.setClientName(rs.getString("CLIENTNAME"));
        client.setStreet(rs.getString("STREET"));
        client.setCity(rs.getString("CITY"));
        client.setProvince(rs.getString("PROVINCE"));
        client.setPostalCode(rs.getString("POSTALCODE"));
        client.setHomePhone(rs.getString("HOMEPHONE"));
        client.setCellPhone(rs.getString("CELLPHONE"));        
        client.setEmail(rs.getString("EMAIL"));  
        return client;
    }    
}