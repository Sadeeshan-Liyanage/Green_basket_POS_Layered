package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.UserBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.UserDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.UserDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.User;

import java.util.ArrayList;


public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String addUser(UserDTO dto) throws Exception {
        User entity = new User(0, dto.getUserName(), dto.getPassword(), dto.getRole());
        return userDAO.save(entity) ? "User Saved Successfully" : "User Save Failed";
    }

    @Override
    public String updateUser(UserDTO dto) throws Exception {
        User entity = new User(dto.getUserId(), dto.getUserName(), dto.getPassword(), dto.getRole());
        return userDAO.update(entity) ? "User Updated!" : "Update Failed!";
    }

    @Override
    public String deleteUser(String id) throws Exception {
        return userDAO.delete(Integer.parseInt(id)) ? "User Deleted!" : "Delete Failed!";
    }

    @Override
    public UserDTO searchUser(String searchInput) throws Exception {
        int id = -1;
        try { id = Integer.parseInt(searchInput); } catch (NumberFormatException ignored) {}
        User user = userDAO.search(id);
        if (user == null) return null;
        return new UserDTO(user.getUserId(), user.getUserName(),
                user.getPassword(), user.getRole());
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() throws Exception {
        ArrayList<UserDTO> dtoList = new ArrayList<>();
        for (User u : userDAO.getAll()) {
            dtoList.add(new UserDTO(u.getUserId(), u.getUserName(), u.getPassword(), u.getRole()));
        }
        return dtoList;
    }

    @Override
    public int getNextUserId() throws Exception {
        return userDAO.getNextId();
    }
}
