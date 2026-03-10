package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.CrudDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Report;

import java.util.List;

public interface ReportDAO extends CrudDAO<Report> {
    List<Integer> getAllOrderIds() throws Exception;
    List<Integer> getAllUserIds() throws Exception;
}
