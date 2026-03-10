package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.UserDTO;

import java.util.ArrayList;


public interface UserBO extends SuperBO {

        String addUser(UserDTO dto) throws Exception;
        String updateUser(UserDTO dto) throws Exception;
        String deleteUser(String id) throws Exception;
        UserDTO searchUser(String searchInput) throws Exception;
        ArrayList<UserDTO> getAllUsers() throws Exception;
        int getNextUserId() throws Exception;
}
