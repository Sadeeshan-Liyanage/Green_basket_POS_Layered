package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.impl;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.bo.custom.SupplierBO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.DAOFactory;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.SupplierDAO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.db.DBConnection;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dto.SupplierDTO;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.entity.Supplier;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    private final SupplierDAO supplierDAO =
            (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIER);

    @Override
    public String addSupplier(SupplierDTO dto) throws Exception {
        Supplier entity = new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getContactNumber(),
                dto.getAddress()
        );
        return supplierDAO.save(entity) ? "Supplier Saved Successfully" : "Supplier Save Failed";
    }

    @Override
    public String updateSupplier(SupplierDTO dto) throws Exception {
        Supplier entity = new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getContactNumber(),
                dto.getAddress()
        );
        return supplierDAO.update(entity) ? "Supplier Update Successfully" : "Supplier Update Failed";
    }

    @Override
    public String deleteSupplier(int id) throws Exception {
        return supplierDAO.delete(String.valueOf(id)) ? "Supplier Delete Successfully" : "Supplier Delete Failed";
    }

    @Override
    public SupplierDTO getSupplier(int id) throws Exception {
        Supplier s = supplierDAO.search(String.valueOf(id));
        if (s == null) return null;
        return new SupplierDTO(s.getSupplierId(), s.getSupplierName(), s.getContactNumber(), s.getAddress());
    }

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws Exception {
        ArrayList<SupplierDTO> dtoList = new ArrayList<>();
        for (Supplier s : supplierDAO.getAll()) {
            dtoList.add(new SupplierDTO(s.getSupplierId(), s.getSupplierName(), s.getContactNumber(), s.getAddress()));
        }
        return dtoList;
    }

    @Override
    public void printSupplierReport() {
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            InputStream inputStream = getClass().getResourceAsStream(
                    "/edu/ijse/mvc/fx/grocessoryshopmanagementsystem/Report/supplierreport.jrxml"
            );
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
