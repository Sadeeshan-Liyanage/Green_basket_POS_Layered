package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static PreparedStatement getPreparedStatement(String sql, Object... args) throws SQLException,ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }

        return pstm;
    }

    public static boolean execute(String sql, Object... args) throws SQLException,ClassNotFoundException {
        PreparedStatement pstm = getPreparedStatement(sql, args);
        return pstm.executeUpdate() > 0;
    }

    public static ResultSet executeQuery(String sql, Object... args) throws SQLException,ClassNotFoundException {
        PreparedStatement pstm = getPreparedStatement(sql, args);
        return pstm.executeQuery();
    }
}