package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.UserDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.User;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User entity) throws Exception {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO User (userName, password, role) VALUES (?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, entity.getUserName());
        pstm.setString(2, entity.getPassword());
        pstm.setString(3, entity.getRole());
        int rows = pstm.executeUpdate();
        if (rows > 0) {
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) entity.setUserId(rs.getInt(1));
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User entity) throws Exception {
        return CrudUtil.execute(
                "UPDATE User SET userName=?, password=?, role=? WHERE UserID=?",
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getUserId()
        );
    }

    @Override
    public boolean delete(int id) throws Exception {
        return CrudUtil.execute(
                "DELETE FROM User WHERE UserID=?",
                id
        );
    }

    @Override
    public User search(int id) throws Exception {
        ResultSet rs = CrudUtil.executeQuery(
                "SELECT * FROM User WHERE UserID=?",
                id
        );
        if (rs.next()) {
            return new User(
                    rs.getInt("UserID"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getString("role")
            );
        }
        return null;
    }

    @Override
    public ArrayList<User> getAll() throws Exception {
        ArrayList<User> list = new ArrayList<>();
        ResultSet rs = CrudUtil.executeQuery("SELECT * FROM User");
        while (rs.next()) {
            list.add(new User(
                    rs.getInt("UserID"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getString("role")
            ));
        }
        return list;
    }

    @Override
    public int getNextId() throws Exception {
        ResultSet rs = CrudUtil.executeQuery("SELECT MAX(UserID) FROM User");
        return rs.next() ? rs.getInt(1) + 1 : 1;
    }

}
