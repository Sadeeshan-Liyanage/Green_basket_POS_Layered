package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.SuperBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ReportDTO;

import java.util.List;

public interface ReportBO extends SuperBO {
    boolean saveReport(ReportDTO dto) throws Exception;
    ReportDTO getReport(int reportId) throws Exception;
    List<Integer> getAllOrderIds() throws Exception;
    List<Integer> getAllUserIds() throws Exception;
}
