package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.CustomerBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.CustomerDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class CustomerViewController {

    //private final CustomerModel customerModel = new CustomerModel();
    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);
    private final ObservableList<CustomerDTO> customerList = FXCollections.observableArrayList();

    @FXML private TextField idField;
    @FXML private TextField txtCustomerId;
    @FXML private TextField customerName, customerNumber, customerAddress;
    @FXML private TableView<CustomerDTO> tableCustomer;
    @FXML private TableColumn<CustomerDTO, Integer> colId;
    @FXML private TableColumn<CustomerDTO, String> colName, colAddress;
    @FXML private TableColumn<CustomerDTO, Integer> colNumber;
    @FXML private Button btnBack1;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        txtCustomerId.setEditable(false);
        loadAllCustomers();

        tableCustomer.getSelectionModel().selectedItemProperty().addListener((obs, old, val) -> {
            if (val != null) fillFields(val);
        });
    }

    private void loadAllCustomers() {
        try {
            customerList.setAll(customerBO.getAllCustomers());
            tableCustomer.setItems(customerList);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void handleSearchCustomer(ActionEvent event) {
        try {
            String input = idField.getText().trim();
            if (input.isEmpty()) return;
            CustomerDTO dto = customerBO.searchCustomer(Integer.parseInt(input));
            if (dto != null) fillFields(dto);
            else new Alert(Alert.AlertType.WARNING, "Not Found").show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void saveCustomer(ActionEvent event) {
        try {

            CustomerDTO dto = new CustomerDTO(0, customerName.getText(),
                    Integer.parseInt(customerNumber.getText()), customerAddress.getText());
            String rsp = customerBO.addCustomer(dto);
            new Alert(Alert.AlertType.INFORMATION, rsp).show();
            clearFields();
            loadAllCustomers();
        } catch (Exception e) { new Alert(Alert.AlertType.ERROR, e.getMessage()).show(); }
    }

    @FXML
    void handleCustomerUpdate(ActionEvent event) {
        try {
            CustomerDTO dto = new CustomerDTO(Integer.parseInt(txtCustomerId.getText()),
                    customerName.getText(), Integer.parseInt(customerNumber.getText()), customerAddress.getText());
            new Alert(Alert.AlertType.INFORMATION, customerBO.updateCustomer(dto)).show();

            loadAllCustomers();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void handleCustomerDelete(ActionEvent event) {
        try {
            String id = txtCustomerId.getText();
            if(!id.isEmpty()){
                new Alert(Alert.AlertType.INFORMATION, customerBO.deleteCustomer(id)).show();
                loadAllCustomers();
                clearFields();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML void handleCustomerReset(ActionEvent event) { clearFields(); }
    @FXML void handleBackClick(ActionEvent event) throws IOException { App.setRoot("DashBoard"); }

    private void fillFields(CustomerDTO customer) {
        txtCustomerId.setText(String.valueOf(customer.getCustomerId()));
        customerName.setText(customer.getCustomerName());
        customerNumber.setText(String.valueOf(customer.getCustomerNumber()));
        customerAddress.setText(customer.getAddress());
        idField.setText(String.valueOf(customer.getCustomerId()));
    }

    private void clearFields() {
        idField.clear(); txtCustomerId.clear(); customerName.clear();
        customerNumber.clear(); customerAddress.clear();
    }

    CustomerModel CustomerModel = new CustomerModel();

    @FXML
    void handlePrintReport(ActionEvent event) {
        try {
            customerBO.printCustomerReports();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Report Error : " + e.getMessage()).show();
        }
    }
}