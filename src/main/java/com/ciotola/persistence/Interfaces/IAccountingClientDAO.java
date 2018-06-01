package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.Client;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the Client methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingClientDAO {
    //Create Methods
    public int addClient(Client client) throws SQLException;
    
    //Read Methods
    public Client findClientById(int id) throws SQLException; 
    public ObservableList<Client> findAllClients() throws SQLException;
    public ObservableList<Client> findClientLikeName(String name) throws SQLException;
    public Client findClientByName(String name) throws SQLException;
    
    //Update Methods
    public int updateClient(Client client) throws SQLException;
    
    //Delete Methods
    public int deleteClient(int id) throws SQLException;
}