package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao;


import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    ArrayList<T> getAll() throws Exception;
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(String id) throws Exception;
    T search(String id) throws Exception;
}