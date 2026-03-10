package edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao;

import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl.CustomerDAOImpl;
import edu.ijse.mvc.fx.grocessoryshopmanagementsystem.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory instance;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return instance == null ? instance = new DAOFactory() : instance;
    }

    public enum DAOType {
        CUSTOMER, ITEM, EMPLOYEE, SUPPLIER, ORDER, REPORT, USER
    }

    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CUSTOMER:     return new CustomerDAOImpl();
            case ITEM:         return new ItemDAOImpl();
            case EMPLOYEE:     return new EmployeeDAOImpl();
            case SUPPLIER:     return new SupplierDAOImpl();
            case ORDER:        return new OrderDAOImpl();
            case REPORT:        return new ReportDAOImpl();
            case USER:         return new UserDAOImpl();
            default:           return null;
        }
    }
}