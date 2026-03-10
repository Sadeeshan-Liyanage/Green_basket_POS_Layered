package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.UserBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.UserDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model.UserModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class UserViewController {

    @FXML private Button btnAddUser;
    @FXML private Button btnBack;
    @FXML private TextField txtSearch;
    @FXML private TextField txtUserID;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private ComboBox<String> cmbRole;

    @FXML private TableView<UserDTO> tableUser;
    @FXML private TableColumn<UserDTO, Integer> colId;
    @FXML private TableColumn<UserDTO, String> colName;
    @FXML private TableColumn<UserDTO, String> colPassword;
    @FXML private TableColumn<UserDTO, String> colRole;

//    private final UserModel userModel = new UserModel();
private UserBO userBO =
        (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    private final ObservableList<UserDTO> userList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("Admin", "Cashier", "Manager");
        setTableColumnProperties();
        loadAllUsers();


        txtUserID.setEditable(false);

        txtSearch.setOnKeyPressed(this::handleSearchCustomer);

        tableUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
            }
        });
    }


    private void setTableColumnProperties() {
        colId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }


    private void loadAllUsers() {
        try {
            userList.clear();
            userList.addAll(userBO.getAllUsers());
            tableUser.setItems(userList);
            txtUserID.setText(String.valueOf(userBO.getNextUserId()));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load users. Check DB connection and 'User' table structure.");
        }
    }


    private void fillFields(UserDTO user) {
        txtUserID.setText(String.valueOf(user.getUserId()));
        txtUserName.setText(user.getUserName());
        txtPassword.setText(user.getPassword());
        cmbRole.setValue(user.getRole());
    }



    @FXML
    void handleAddUser(ActionEvent event) {

        if (txtUserName.getText().isEmpty() ||
                txtPassword.getText().isEmpty() || cmbRole.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill Username, Password, and select a Role.");
            return;
        }

        UserDTO newUser = new UserDTO(0, txtUserName.getText(),
                txtPassword.getText(), cmbRole.getValue());

        try {

            String result = userBO.addUser(newUser);

            if (result.contains("Saved Successfully")){

                showAlert(Alert.AlertType.INFORMATION, "Success", "User " + newUser.getUserId() + " added successfully.");
                clearFields();
                loadAllUsers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User Save Failed. Please check logs for details.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save user. Check DB connection, 'User' table columns (userName, password, role) and ensure UserID is set as AUTO_INCREMENT** in the database.");
        }
    }

    @FXML
    void handleBack(ActionEvent event) {
        try {
            App.setRoot("Dashboard");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error navigating back: " + e.getMessage()).show();
        }
    }

    @FXML
    void handleSearchCustomer(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchInput = txtSearch.getText();
            if (searchInput.trim().isEmpty()) {
                loadAllUsers();
                clearFieldsExceptSearch();
                return;
            }

            try {

                UserDTO foundUser = userBO.searchUser(searchInput);

                if (foundUser != null) {
                    fillFields(foundUser);
                } else {
                    showAlert(Alert.AlertType.INFORMATION, "Not Found", "User not found for search input: " + searchInput);
                    clearFieldsExceptSearch();
                    txtSearch.setText(searchInput);
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to search for user.");
            }
        }
    }



    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtUserID.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtSearch.clear();
        cmbRole.getSelectionModel().clearSelection();
    }

    private void clearFieldsExceptSearch() {
        txtUserID.clear();
        txtUserName.clear();
        txtPassword.clear();
        cmbRole.getSelectionModel().clearSelection();
    }
}