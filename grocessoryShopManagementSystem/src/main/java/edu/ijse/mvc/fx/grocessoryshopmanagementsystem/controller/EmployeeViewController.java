package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.EmployeeBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.EmployeeDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model.EmployeeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class EmployeeViewController {

//    private final EmployeeModel employeeModel = new EmployeeModel();
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);
    private final ObservableList<EmployeeDTO> employeeList = FXCollections.observableArrayList();

    @FXML private TextField addressField, nameField, salaryField, employeeIDField, txtSearchID;
    @FXML private TableView<EmployeeDTO> tableEmployee;
    @FXML private TableColumn<EmployeeDTO, Integer> colEmployeeId;
    @FXML private TableColumn<EmployeeDTO, String> colEmployeeName, colEmployeeAddress;
    @FXML private TableColumn<EmployeeDTO, Double> colSalary;

    @FXML
    public void initialize() {
        setTableColumnProperties();
        loadAllEmployees();


        tableEmployee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
            }
        });

        employeeIDField.setEditable(false);
    }

    private void setTableColumnProperties() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("emp_Name"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("emp_Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("emp_salary"));
    }

    private void loadAllEmployees() {
        try {
            employeeList.setAll(employeeBO.getAllEmployees());
            tableEmployee.setItems(employeeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleSaveEmployee(ActionEvent event) {
        try {
            if (nameField.getText().isEmpty() || salaryField.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Fill required fields!").show();
                return;
            }


            EmployeeDTO dto = new EmployeeDTO(0, nameField.getText(), addressField.getText(), Double.parseDouble(salaryField.getText()));

            new Alert(Alert.AlertType.INFORMATION, employeeBO.saveEmployee(dto)).show();
            clearFields();
            loadAllEmployees();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void handleSearchIDAction(ActionEvent event) {
        try {
            String idText = txtSearchID.getText().trim();
            if (idText.isEmpty()) return;

            EmployeeDTO employee = employeeBO.getEmployee(Integer.parseInt(idText));
            if (employee != null) {
                fillFields(employee);
            } else {
                new Alert(Alert.AlertType.WARNING, "Employee Not Found").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleUpdateEmployee(ActionEvent event) {
        try {
            if (employeeIDField.getText().isEmpty()) return;
            EmployeeDTO dto = new EmployeeDTO(Integer.parseInt(employeeIDField.getText()), nameField.getText(), addressField.getText(), Double.parseDouble(salaryField.getText()));
            new Alert(Alert.AlertType.INFORMATION, employeeBO.updateEmployee(dto)).show();
            loadAllEmployees();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void handleDeleteEmployee(ActionEvent event) {
        try {
            if (employeeIDField.getText().isEmpty()) return;
            new Alert(Alert.AlertType.INFORMATION, employeeBO.deleteEmployee(employeeIDField.getText())).show();
            loadAllEmployees();
            clearFields();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML void handleResetEmployee(ActionEvent event) { clearFields(); }

    @FXML void handleBack(ActionEvent event) throws IOException { App.setRoot("Dashboard"); }

    private void fillFields(EmployeeDTO employee) {
        employeeIDField.setText(String.valueOf(employee.getEmp_id()));
        nameField.setText(employee.getEmp_Name());
        addressField.setText(employee.getEmp_Address());
        salaryField.setText(String.valueOf(employee.getEmp_salary()));
        txtSearchID.setText(String.valueOf(employee.getEmp_id()));
    }

    private void clearFields() {
        employeeIDField.clear(); nameField.clear(); addressField.clear(); salaryField.clear(); txtSearchID.clear();
    }
}