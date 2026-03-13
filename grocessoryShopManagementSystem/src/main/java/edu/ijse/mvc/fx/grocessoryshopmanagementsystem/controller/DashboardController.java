package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.ItemBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.OrderBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ItemDTO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class DashboardController {

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnStockManagement;

    @FXML
    private Button btnSupplierManagement;


    @FXML
    private Button btnUserManagement;

    @FXML
    private BarChart<String, Number> ordersBarChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label lblExpired;

    @FXML
    private Label lblLowStock;

    @FXML
    private Label lblTotalItems;

    @FXML
    private Label lblLowItem1;

    @FXML
    private Label lblLowItem2;

    @FXML
    private Label lblLowItem3;

    @FXML
    private Label lblLowItem4;

    @FXML
    private Label lblLowQty1;

    @FXML
    private Label lblLowQty2;

    @FXML
    private Label lblLowQty3;

    @FXML
    private Label lblLowQty4;


    @FXML
    void handleUserManagementClick(ActionEvent event) {
        try {
            App.setRoot("UserManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load User View: " + e.getMessage());
        }
    }


    @FXML
    void handleCustomerClick(ActionEvent event) {
        try {
            App.setRoot("CustomerManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Customer View: " + e.getMessage());
        }
    }

    @FXML
    void handleEmployeeClick(ActionEvent event) {
        try {
            App.setRoot("EmployeeManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Employee View: " + e.getMessage());
        }
    }

    @FXML
    void handleLogoutClick(ActionEvent event) {
        try {
            App.setRoot("Login");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Login View: " + e.getMessage());
        }
    }

    @FXML
    void handleOrderClick(ActionEvent event) {
        try {
            App.setRoot("OrderManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Order View: " + e.getMessage());
        }
    }

    @FXML
    void handleReportClick(ActionEvent event) {
        try {
            App.setRoot("ReportManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Report View: " + e.getMessage());
        }
    }

    @FXML
    void handleStockManagementClick(ActionEvent event) {
        try {
            App.setRoot("StockManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Stock Management View: " + e.getMessage());
        }
    }

    @FXML
    void handleSupplierManagementClick(ActionEvent event) {
        try {
            App.setRoot("SupplierManage");
        } catch (IOException e) {
            showAlert("Error Loading Scene", "Could not load Supplier Management View: " + e.getMessage());
        }
    }


    public void initialize() throws Exception {
            loadMonthlyOrders();
            loadDashboardCards();
            loadLowStockAlerts();

            NumberAxis yAxis = (NumberAxis) ordersBarChart.getYAxis();
            yAxis.setAutoRanging(true);
    }


    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);
    private final ItemBO itemBO   = (ItemBO)  BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    private void loadMonthlyOrders() throws Exception {
        ordersBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Orders - 2025");

        Map<String, Integer> dataMap = orderBO.getMonthlyOrderCount();


        System.out.println("Debug - Chart Data: " + dataMap);

        if (dataMap.isEmpty()) {
            System.out.println("No data found in database for the chart!");
        } else {
            dataMap.forEach((month, count) -> {
                series.getData().add(new XYChart.Data<>(month, count));
            });
            ordersBarChart.getData().add(series);
        }

        for (XYChart.Data<String, Number> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-fill: #13F803;");
        }
    }

    private void loadDashboardCards() {
        try {


            lblTotalItems.setText(String.format("%04d", itemBO.getTotalItemCount()));
            lblLowStock.setText(String.format("%02d", itemBO.getLowStockCount()));
            lblExpired.setText(String.format("%02d", itemBO.getExpiredItemCount()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLowStockAlerts() {
        try {

            ArrayList<ItemDTO> lowStockList = itemBO.getLowStockItems();

            Label[] itemLabels = {lblLowItem1, lblLowItem2, lblLowItem3, lblLowItem4};
            Label[] qtyLabels = {lblLowQty1, lblLowQty2, lblLowQty3, lblLowQty4};


            for (int i = 0; i < lowStockList.size(); i++) {
                itemLabels[i].setText(lowStockList.get(i).getItemName());
                qtyLabels[i].setText(String.valueOf(lowStockList.get(i).getQuantity()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}