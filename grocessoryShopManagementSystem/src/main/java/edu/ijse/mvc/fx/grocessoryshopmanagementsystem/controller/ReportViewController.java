package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.ReportBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ReportDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model.ReportModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class ReportViewController {

    private final ReportBO reportBO = (ReportBO) BOFactory.getInstance().getBO(BOFactory.BOType.REPORT);

    @FXML private ComboBox<Integer> cmbOrderID;
    @FXML private ComboBox<String> cmbReportType;
    @FXML private ComboBox<Integer> cmbUserID;
    @FXML private TextField txtReportID;
    @FXML private TextField txtSearch;

    @FXML
    public void initialize() {
        ObservableList<String> reportTypes = FXCollections.observableArrayList(
                "Sales", "Inventory", "Customers", "Suppliers", "Monthly Summary"
        );
        cmbReportType.setItems(reportTypes);

        txtReportID.setEditable(false);
        txtReportID.setPromptText("Auto-generated");

        loadComboBoxData();
    }

    private void loadComboBoxData() {
        try {
            cmbOrderID.setItems(FXCollections.observableArrayList(reportBO.getAllOrderIds()));
            cmbUserID.setItems(FXCollections.observableArrayList(reportBO.getAllUserIds()));
        } catch (Exception e) {
            System.out.println("Error loading IDs: " + e.getMessage());
        }
    }

    @FXML
    void handleGenerateReport(ActionEvent event) {
        String type = cmbReportType.getSelectionModel().getSelectedItem();


        int oId = (cmbOrderID.getValue() == null) ? 0 : cmbOrderID.getValue();
        int uId = (cmbUserID.getValue() == null) ? 0 : cmbUserID.getValue();

        if (type == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a Report Type!").show();
            return;
        }

        try {

            ReportDTO dto = new ReportDTO(0, type, null, oId, uId);

            if (reportBO.saveReport(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Report Generated & Saved Successfully!").show();
                handleCancel(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "System Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        cmbReportType.getSelectionModel().clearSelection();
        cmbOrderID.getSelectionModel().clearSelection();
        cmbUserID.getSelectionModel().clearSelection();
        txtReportID.clear();
    }

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        App.setRoot("Dashboard");
    }


    @FXML
    void handleSearchReport(ActionEvent event) {
        try {
            String searchIdText = txtSearch.getText().trim();
            if (searchIdText.isEmpty()) return;

            int reportId = Integer.parseInt(searchIdText);

            ReportDTO reportDTO = reportBO.getReport(reportId);

            if (reportDTO != null) {
                fillFields(reportDTO);
            } else {
                new Alert(Alert.AlertType.WARNING, "Report not found for ID: " + reportId).show();
                handleCancel(null);
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid numeric Report ID.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Search Error: " + e.getMessage()).show();
        }
    }


    private void fillFields(ReportDTO dto) {
        txtReportID.setText(String.valueOf(dto.getReportId()));
        cmbReportType.setValue(dto.getReportType());
        cmbOrderID.setValue(dto.getOrderId());
        cmbUserID.setValue(dto.getUserId());
    }
}