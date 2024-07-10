package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE SupplierId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supList = new ArrayList<>();

        while (resultSet.next()) {
            String SupplierId = resultSet.getString(1);
            String SupplierName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Quantity = resultSet.getString(4);
            String Contact = resultSet.getString(5);
            String ProductName = resultSet.getString(6);
            Date Date = java.sql.Date.valueOf(resultSet.getString(7));

            Supplier supplier = new Supplier(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,Date);
            supList.add(supplier);
        }
        return supList;
    }

    public static boolean UPDATE(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET SupplierName = ?, Address = ?, Quantity = ? , Contact = ? ,ProductName = ? ,Date = ? WHERE SupplierId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, supplier.getSupplierName());
        pstm.setObject(2, supplier.getAddress());
        pstm.setObject(3,supplier.getQuantity());
        pstm.setObject(4,supplier.getContact());
        pstm.setObject(5,supplier.getProductName());
        pstm.setObject(6,supplier.getDate());
        pstm.setObject(7, supplier.getSupplierId());
        return pstm.executeUpdate() > 0;
    }

    public static Supplier SEARCH(String id) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String SupplierId = resultSet.getString(1);
            String SupplierName = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Quantity = resultSet.getString(4);
            String Contact = resultSet.getString(5);
            String ProductName = resultSet.getString(6);
            Date Date = java.sql.Date.valueOf(resultSet.getString(7));


            Supplier supplier = new Supplier(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,Date);

            return supplier;
        }

        return null;
    }
    public static boolean SAVE(String SupplierId, String SupplierName, String Address, String Quantity, String Contact, String ProductName, Date Date) throws SQLException {
        String sql = "INSERT INTO Supplier VALUES(?, ?, ?, ?,?,?,?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, SupplierId);
        pstm.setObject(2, SupplierName);
        pstm.setObject(3, Address);
        pstm.setObject(4, Quantity);
        pstm.setObject(5, Contact);
        pstm.setObject(6, ProductName);
        pstm.setObject(7, Date);
        return pstm.executeUpdate() >0;

       /* int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this Supplier").show();
        }
        return false;*/
    }

    public static String getCurrentId() throws SQLException {

            String sql = "SELECT SupplierId FROM Supplier ORDER BY SupplierId DESC LIMIT 1";

            PreparedStatement pstm = dbConnection.getInstance().getConnection()
                    .prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String supplierId = resultSet.getString(1);
                return supplierId;
            }
            return null;

        }



}

