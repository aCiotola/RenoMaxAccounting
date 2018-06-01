package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.MainDescription;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the MainDescription methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingMainDescriptionDAO {
    //Create Methods
    public int addMainDescription(MainDescription mainDescription) throws SQLException;
    
    //Read Methods
    public MainDescription findMainDescriptionById(int id) throws SQLException;
    public ObservableList<MainDescription> findAllMainDescriptions() throws SQLException;
    public MainDescription findMainDescriptionByName(String name) throws SQLException;
}