package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.SupplierBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.SupplierDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.SupplierDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Optional;

public class SupplierViewController {

    private final SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);
    private final ObservableList<SupplierDTO> supplierList = FXCollections.observableArrayList();


    @FXML private Button btnBack;


    @FXML private TextField supplierIDField;

    @FXML private TextField supplierId;

    @FXML private TextField contactNumber;
    @FXML private TextField supplierAddress;
    @FXML private TextField supplierName;
    @FXML private TableView<SupplierDTO> supplierTable;
    @FXML private TableColumn<SupplierDTO, String> colAddress;
    @FXML private TableColumn<SupplierDTO, String> colContactNumber;
    @FXML private TableColumn<SupplierDTO, String> colSupplierName;
    @FXML private TableColumn<SupplierDTO, Integer> colSupplierID;



    @FXML
    public void initialize() {
        setTableColumnProperties();
        loadAllSuppliers();


        supplierIDField.setOnKeyPressed(this::handleSearchSupplierKeyPress);


        supplierTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillFields(newSelection);
            }
        });


        if (supplierId != null) {
            supplierId.setEditable(false);
        }
    }

    private void setTableColumnProperties() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadAllSuppliers() {
        try {

            supplierList.setAll(supplierBO.getAllSuppliers());
            supplierTable.setItems(supplierList);


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading suppliers: Check DB connection and 'Supplier' table structure. " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    private void clearFields() {
        supplierIDField.clear();
        if (supplierId != null) supplierId.clear();
        supplierName.clear();
        contactNumber.clear();
        supplierAddress.clear();
    }


    private void fillFields(SupplierDTO dto) {

        if (supplierId != null) supplierId.setText(String.valueOf(dto.getSupplierId()));

        supplierName.setText(dto.getSupplierName());
        contactNumber.setText(String.valueOf(dto.getContactNumber()));
        supplierAddress.setText(dto.getAddress());


        supplierIDField.setText(String.valueOf(dto.getSupplierId()));
    }


    @FXML
    void handleSearchSupplierKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String idText = supplierIDField.getText().trim();

            if (idText.isEmpty()) {

                loadAllSuppliers();
                clearFields();
                return;
            }

            try {
                int supplierIdInt = Integer.parseInt(idText);
                SupplierDTO dto = supplierBO.getSupplier(supplierIdInt);

                if (dto != null) {
                    fillFields(dto);

                } else {

                    if (supplierId != null) supplierId.clear();
                    supplierName.clear();
                    contactNumber.clear();
                    supplierAddress.clear();

                    new Alert(Alert.AlertType.INFORMATION, "No data found for Supplier ID: " + supplierIdInt + ".").show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "The search ID must be a valid number.").show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error searching supplier: " + e.getMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handleSearchSupplier(ActionEvent event) {

        handleSearchSupplierKeyPress(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false));
    }


    @FXML
    void saveSupplier(ActionEvent event) {
        try {
            if (supplierName.getText().isEmpty() || contactNumber.getText().isEmpty() || supplierAddress.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in Supplier Name, Contact Number, and Address.").show();
                return;
            }


            int id = (supplierId == null || supplierId.getText().isEmpty()) ? 0 : Integer.parseInt(supplierId.getText());

            SupplierDTO supplierDTO = new SupplierDTO(
                    id,
                    supplierName.getText(),
                    contactNumber.getText(),
                    supplierAddress.getText()
            );

            String rsp = supplierBO.addSupplier(supplierDTO);
            new Alert(Alert.AlertType.INFORMATION, rsp).show();


            clearFields();
            loadAllSuppliers();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Input Error: Contact Number must be a valid number.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error saving supplier: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void handleSupplierUpdate(ActionEvent event) {
        try {

            if (supplierId == null || supplierId.getText().isEmpty() || supplierName.getText().isEmpty() || contactNumber.getText().isEmpty() || supplierAddress.getText().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select a supplier or fill all fields for update.").show();
                return;
            }

            SupplierDTO supplierDTO = new SupplierDTO(
                    Integer.parseInt(supplierId.getText()),

                    supplierName.getText(),
                    contactNumber.getText(),
                    supplierAddress.getText()
            );

            String rsp = supplierBO.updateSupplier(supplierDTO);
            new Alert(Alert.AlertType.INFORMATION, rsp).show();
            clearFields();
            loadAllSuppliers();

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Input Error, ID and Contact Number must be valid numbers.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating supplier: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void handleDeleteSupplier(ActionEvent event) {
        try {

            String idText = (supplierId == null) ? "" : supplierId.getText();

            if (idText.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select a Supplier from the table or enter the Supplier ID to delete.").show();
                return;
            }

            int supplierIdInt = Integer.parseInt(idText);

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete Supplier ID: " + supplierIdInt + "?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                String rsp = supplierBO.deleteSupplier(supplierIdInt);
                new Alert(Alert.AlertType.INFORMATION, rsp).show();
                clearFields();
                loadAllSuppliers();

            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "The ID must be a valid number.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error deleting supplier: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void handleBackClick(ActionEvent event) {
        try {
            App.setRoot("Dashboard");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Dashboard view: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void handlePrintSupplierReport(ActionEvent event) {

        supplierBO.printSupplierReport();
    }
}
