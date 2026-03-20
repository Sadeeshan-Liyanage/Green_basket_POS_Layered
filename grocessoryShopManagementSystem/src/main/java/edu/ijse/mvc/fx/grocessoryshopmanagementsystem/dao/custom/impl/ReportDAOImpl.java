package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.ReportDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Report;
//import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CRUDUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReportDAOImpl implements ReportDAO {

    @Override
    public ArrayList<Report> getAll() throws Exception {
        ArrayList<Report> list = new ArrayList<>();
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Report");
        while (rst.next()) {
            list.add(mapReport(rst));
        }
        return list;
    }

    @Override
    public boolean save(Report report) throws Exception {
        return CRUDUtil.execute(
                "INSERT INTO Report (reportType, generatedDate, orderId, userId) VALUES (?, CURDATE(), ?, ?)",
                report.getReportType(),
                report.getOrderId() == 0 ? null : report.getOrderId(),
                report.getUserId()  == 0 ? null : report.getUserId()
        );
    }

    @Override
    public boolean update(Report report) throws Exception {
        return CRUDUtil.execute(
                "UPDATE Report SET reportType=?, orderId=?, userId=? WHERE reportId=?",
                report.getReportType(), report.getOrderId(), report.getUserId(), report.getReportId()
        );
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CRUDUtil.execute("DELETE FROM Report WHERE reportId=?", Integer.parseInt(id));
    }

    @Override
    public Report search(String id) throws Exception {
        ResultSet rst = CRUDUtil.executeQuery("SELECT * FROM Report WHERE reportId=?", Integer.parseInt(id));
        if (rst.next()) return mapReport(rst);
        return null;
    }

    @Override
    public List<Integer> getAllOrderIds() throws Exception {
        List<Integer> list = new ArrayList<>();
        ResultSet rst = CRUDUtil.executeQuery("SELECT order_Id FROM Orders");
        while (rst.next()) list.add(rst.getInt(1));
        return list;
    }

    @Override
    public List<Integer> getAllUserIds() throws Exception {
        List<Integer> list = new ArrayList<>();
        ResultSet rst = CRUDUtil.executeQuery("SELECT userId FROM User");
        while (rst.next()) list.add(rst.getInt(1));
        return list;
    }


    private Report mapReport(ResultSet rst) throws Exception {
        return new Report(
                rst.getInt("reportId"),
                rst.getString("reportType"),
                rst.getString("generatedDate"),
                rst.getInt("orderId"),
                rst.getInt("userId")
        );
    }
}
