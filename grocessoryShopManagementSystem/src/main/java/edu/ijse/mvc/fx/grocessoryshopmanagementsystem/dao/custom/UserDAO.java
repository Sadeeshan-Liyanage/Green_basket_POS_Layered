package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.SuperDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.User;

import java.util.ArrayList;

public interface UserDAO extends SuperDAO {

    boolean save(User user) throws Exception;
    boolean update(User user) throws Exception;
    boolean delete(int userId) throws Exception;
    User search(int userId) throws Exception;
    ArrayList<User> getAll() throws Exception;
    int getNextId() throws Exception;
}
