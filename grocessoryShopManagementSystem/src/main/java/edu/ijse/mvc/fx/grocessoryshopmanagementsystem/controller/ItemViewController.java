package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.controller;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.App;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.BOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.ItemBO;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.ItemDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import javafx.scene.control.ComboBox;


public class ItemViewController {

//    private final ItemModel itemModel = new ItemModel();
    ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);
    private final ObservableList<ItemDTO> itemList = FXCollections.observableArrayList();

    @FXML private TextField itemIdField;
    @FXML private TextField itemName;
    @FXML private ComboBox<String> cmbCategory;
    @FXML private TextField unitPriceField;
    @FXML private TextField quantityField;
    @FXML private TextField expiryDateField;
    @FXML private TextField supplierField;
    @FXML private TextField txtSearchID;

    @FXML private TableView<ItemDTO> itemTable;
    @FXML private TableColumn<ItemDTO, Integer> colId;
    @FXML private TableColumn<ItemDTO, String> colItemName;
    @FXML private TableColumn<ItemDTO, String> colCategory;
    @FXML private TableColumn<ItemDTO, Double> colUnitPrice;
    @FXML private TableColumn<ItemDTO, Integer> colQuantity;
    @FXML private TableColumn<ItemDTO, String> colExpiryDate;

    @FXML
    public void initialize() {

        cmbCategory.getItems().addAll("Grains", "Dairy","Meat & Fish", "Beverages",
                "Spices", "Cleaning", "Snacks","Personal Care & Household","Frozen Foods");

        setTableColumnProperties();
        loadAllItems();

        itemIdField.setEditable(false);
    }

    private void setTableColumnProperties() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        itemTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) fillFields(newSel);
        });
    }

    private void loadAllItems() {
        try {
            itemList.setAll(itemBO.getAllItems());
            itemTable.setItems(itemList);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void fillFields(ItemDTO item) {
        itemIdField.setText(String.valueOf(item.getItemId()));
        itemName.setText(item.getItemName());
        cmbCategory.setValue(item.getCategory());
        unitPriceField.setText(String.valueOf(item.getUnitPrice()));
        quantityField.setText(String.valueOf(item.getQuantity()));
        expiryDateField.setText(item.getExpiryDate());
        supplierField.setText(String.valueOf(item.getSupplier_Id()));
    }

    @FXML
    void handleSaveItem(ActionEvent event) {
        try {

            ItemDTO dto = new ItemDTO(0, itemName.getText(), cmbCategory.getValue(),
                    Double.parseDouble(unitPriceField.getText()), Integer.parseInt(quantityField.getText()),
                    expiryDateField.getText(), Integer.parseInt(supplierField.getText()));

            new Alert(Alert.AlertType.INFORMATION, itemBO.saveItem(dto)).show();
            clearFields();
            loadAllItems();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Input Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void handleUpdateItem(ActionEvent event) {
        try {
            ItemDTO dto = new ItemDTO(Integer.parseInt(itemIdField.getText()), itemName.getText(), cmbCategory.getValue(),
                    Double.parseDouble(unitPriceField.getText()), Integer.parseInt(quantityField.getText()),
                    expiryDateField.getText(), Integer.parseInt(supplierField.getText()));


            new Alert(Alert.AlertType.INFORMATION, itemBO.updateItem(dto)).show();
            loadAllItems();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    void handleDeleteItem(ActionEvent event) {
        try {
            new Alert(Alert.AlertType.INFORMATION, itemBO.deleteItem(itemIdField.getText())).show();
            clearFields();
            loadAllItems();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void clearFields() {
        itemIdField.clear(); itemName.clear(); unitPriceField.clear();
        quantityField.clear(); expiryDateField.clear(); supplierField.clear();
        cmbCategory.getSelectionModel().clearSelection();
    }

    @FXML void handleResetItem(ActionEvent event) {
        clearFields();
    }

    @FXML void handleBack(ActionEvent event) {
        try {
            App.setRoot("Dashboard");
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error navigating back: " + e.getMessage()).show();
        }
    }

    @FXML
    void handleSearchCustomer(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                ItemDTO item = itemBO.getItem(Integer.parseInt(txtSearchID.getText()));
                if (item != null) fillFields(item);
                else new Alert(Alert.AlertType.WARNING, "Item Not Found").show();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}