package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
//@Data
public class ReportDTO {

    private int reportId;
    private String reportType;
    private String generatedDate;


    private int orderId;
    private int userId;

    @Override
    public String toString() {
        return "ReportDTO{" +
                "reportId=" + reportId +
                ", reportType='" + reportType + '\'' +
                ", generatedDate='" + generatedDate + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}