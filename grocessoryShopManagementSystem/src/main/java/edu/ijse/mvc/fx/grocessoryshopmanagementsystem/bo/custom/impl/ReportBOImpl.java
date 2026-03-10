package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.ReportBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.ReportDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ReportDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Report;

import java.util.List;

public class ReportBOImpl implements ReportBO {

    private final ReportDAO reportDAO = (ReportDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REPORT);


    @Override
    public boolean saveReport(ReportDTO dto) throws Exception {

        Report entity = new Report(
                0,
                dto.getReportType(),
                dto.getGeneratedDate(),
                dto.getOrderId(),
                dto.getUserId()
        );
        return reportDAO.save(entity);
    }

    @Override
    public ReportDTO getReport(int reportId) throws Exception {
        Report r = reportDAO.search(String.valueOf(reportId));
        if (r == null) return null;

        return new ReportDTO(
                r.getReportId(),
                r.getReportType(),
                r.getGeneratedDate(),
                r.getOrderId(),
                r.getUserId()
        );
    }

    @Override
    public List<Integer> getAllOrderIds() throws Exception {
        return reportDAO.getAllOrderIds();
    }

    @Override
    public List<Integer> getAllUserIds() throws Exception {
        return reportDAO.getAllUserIds();
    }
}
