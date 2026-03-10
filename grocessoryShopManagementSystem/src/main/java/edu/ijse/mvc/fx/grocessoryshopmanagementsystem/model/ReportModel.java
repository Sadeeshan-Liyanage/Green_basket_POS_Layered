package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ReportDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportModel {


    public boolean saveReport(ReportDTO dto) throws Exception {
        return CrudUtil.execute("INSERT INTO Report (reportType, generatedDate, orderId, userId) VALUES (?, CURDATE(), ?, ?)",


                dto.getReportType(),
                dto.getOrderId() == 0 ? null : dto.getOrderId(),
                dto.getUserId() == 0 ? null : dto.getUserId()
        );
    }

    public List<Integer> getAllOrderIds() throws Exception {
        List<Integer> orderIds = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery("SELECT order_Id FROM Orders");


        while (rst.next()) {
            orderIds.add(rst.getInt(1));
        }
        return orderIds;
    }


    public List<Integer> getAllUserIds() throws Exception {
        List<Integer> userIds = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery("SELECT userId FROM User");

        while (rst.next()) {
            userIds.add(rst.getInt(1));
        }
        return userIds;
    }


    public ReportDTO getReport(int reportId) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Report WHERE reportId = ?", reportId);

        if (rst.next()) {
            return new ReportDTO(
                    rst.getInt("reportId"),
                    rst.getString("reportType"),
                    rst.getString("generatedDate"),
                    rst.getInt("orderId"),
                    rst.getInt("userId")
            );
        }
        return null;
    }

}