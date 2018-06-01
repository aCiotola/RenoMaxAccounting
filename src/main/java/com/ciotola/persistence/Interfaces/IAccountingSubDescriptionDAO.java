package com.ciotola.persistence.Interfaces;

import com.ciotola.entities.SubDescription;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 * DAO Interface which contains the SubDescription methods required to interact with the database.
 * 
 * @author Alessandro Ciotola
 * @version 2018/05/19
 */
public interface IAccountingSubDescriptionDAO {
    //Create Methods
    public int addSubDescription(SubDescription subDescription) throws SQLException;
    
    //Read Methods
    public SubDescription findSubDescriptionById(int id) throws SQLException; 
    public ObservableList<SubDescription> findAllSubDescriptions() throws SQLException;
    
    //Update Methods
    public int updateSubDescription(SubDescription subDescription) throws SQLException;
    
    //Delete Methods
    public int deleteSubDescription(int id) throws SQLException;
}