package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserModel {
    private static final String TABLE_NAME = "User";
    private static final String ID_COLUMN = "user_id";


    public String addUser(UserDTO userDTO) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO " + TABLE_NAME + " (userName, password, role) VALUES (?, ?, ?)";


        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstm.setString(1, userDTO.getUserName());
        pstm.setString(2, userDTO.getPassword());
        pstm.setString(3, userDTO.getRole());

        int affectedRows = pstm.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userDTO.setUserId(generatedKeys.getInt(1));
                }
            }
            return "User Saved Successfully with ID: " + userDTO.getUserId();
        } else {
            return "User Save Failed";
        }
    }


    public UserDTO searchUser(String searchInput) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();


        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UserID = ? OR userName LIKE ? LIMIT 1";
        PreparedStatement pstm = conn.prepareStatement(sql);

        int userId = -1;
        try {
            userId = Integer.parseInt(searchInput);
        } catch (NumberFormatException e) {
        }

        pstm.setInt(1, userId);
        pstm.setString(2, "%" + searchInput + "%");

        ResultSet rst = pstm.executeQuery();

        if (rst.next()) {
            return new UserDTO(
                    rst.getInt("UserID"),
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("role")
            );
        }
        return null;
    }


    public ArrayList<UserDTO> getAllUsers() throws Exception {
        ArrayList<UserDTO> userList = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM " + TABLE_NAME;
        PreparedStatement pstm = conn.prepareStatement(sql);

        ResultSet rst = pstm.executeQuery();

        while (rst.next()) {
            userList.add(new UserDTO(
                    rst.getInt("UserID"),
                    rst.getString("userName"),
                    rst.getString("password"),
                    rst.getString("role")
            ));
        }
        return userList;
    }
}