package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CrudDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Supplier;

import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier> {
    ArrayList<Supplier> getAll() throws Exception;
    boolean save(Supplier entity) throws Exception;
    boolean update(Supplier entity) throws Exception;
    boolean delete(String id) throws Exception;
    Supplier search(String id) throws Exception;
}
