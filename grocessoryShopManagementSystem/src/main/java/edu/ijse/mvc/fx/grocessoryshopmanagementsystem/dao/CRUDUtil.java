package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import java.sql.*;

public class CRUDUtil {
    public static <T> T execute(String sql, Object... params) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }


        if (sql.startsWith("SELECT")) {
            return (T) pstm.executeQuery();
        } else {
            return (T)(Boolean)(pstm.executeUpdate() > 0);
        }
    }

    public static ResultSet executeQuery(String sql, Object... params) throws Exception {  // ✅ new method
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            pstm.setObject(i + 1, params[i]);
        }

        return pstm.executeQuery();
    }
}