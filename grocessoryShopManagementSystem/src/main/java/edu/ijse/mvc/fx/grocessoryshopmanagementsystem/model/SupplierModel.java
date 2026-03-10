package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.model;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.SupplierDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.util.CrudUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SupplierModel {

    private static final String TABLE_NAME = "Supplier";
    private static final String ID_COLUMN = "supplier_id";


    public String addSupplier(SupplierDTO supplierDTO) throws Exception {
        boolean isSaved;

        if (supplierDTO.getSupplierId() == 0) {
            String sql = "INSERT INTO " + TABLE_NAME + " (supplierName, contactNumber, address) VALUES (?, ?, ?)";
            isSaved = CrudUtil.execute(sql, supplierDTO.getSupplierName(), supplierDTO.getContactNumber(), supplierDTO.getAddress());
        } else {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + ID_COLUMN + ", supplierName, contactNumber, address) VALUES (?, ?, ?, ?)";
            isSaved = CrudUtil.execute(sql, supplierDTO.getSupplierId(), supplierDTO.getSupplierName(), supplierDTO.getContactNumber(), supplierDTO.getAddress());
        }

        return isSaved ? "Supplier Saved Successfully" : "Supplier Save Failed";
    }

    public String updateSupplier(SupplierDTO supplierDTO) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE " + TABLE_NAME + " SET supplierName = ?, contactNumber = ?, address = ? WHERE " + ID_COLUMN + " = ?";

        boolean isUpdated = CrudUtil.execute(sql,
                supplierDTO.getSupplierName(),
                supplierDTO.getContactNumber(),
                supplierDTO.getAddress(),
                supplierDTO.getSupplierId()
        );

        return isUpdated ? "Supplier Update Successfully" : "Supplier Update Failed";
    }

    public String deleteSupplier(int supplierId) throws Exception {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " = ?";
        boolean isDeleted = CrudUtil.execute(sql, supplierId);

        return isDeleted ? "Supplier Delete Successfully" : "Supplier Delete Failed";
    }

    public SupplierDTO getSupplier(int supplierId) throws Exception {

        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + " = ?";
        ResultSet rst = CrudUtil.executeQuery(sql, supplierId);
        if (rst.next()) {
            return new SupplierDTO(
                    rst.getInt(ID_COLUMN),
                    rst.getString("supplierName"),
                    rst.getString("contactNumber"),
                    rst.getString("address")
            );
        }

        return null;
    }


    public ArrayList<SupplierDTO> getAllSuppliers() throws Exception {

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME;
        ResultSet rst = CrudUtil.executeQuery(sql);

        while (rst.next()) {
            supplierDTOS.add(new SupplierDTO(
                    rst.getInt(ID_COLUMN),
                    rst.getString("supplierName"),
                    rst.getString("contactNumber"),
                    rst.getString("address")
            ));
        }

        return supplierDTOS;
    }
    public void printSupplierReport() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();

            InputStream inputStream = getClass().getResourceAsStream("/edu/ijse/mvc/fx/grocessoryshopmanagementsystem/Report/supplierreport.jrxml");

            if (inputStream == null) {
                System.err.println("Report file not found!");
                return;
            }


            JasperReport jr = JasperCompileManager.compileReport(inputStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);


            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("Supplier Report");
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}