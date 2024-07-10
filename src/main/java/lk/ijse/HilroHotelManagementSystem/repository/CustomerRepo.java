package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE CustomerId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            String CustomerName = resultSet.getString(2);
            String Contact = resultSet.getString(3);
            String Address = resultSet.getString(4);
            Date Date = java.sql.Date.valueOf(resultSet.getString(5));
            String Nic = resultSet.getString(6);



            Customer customer = new Customer(CustomerId, CustomerName, Contact, Address, Date,Nic);
            cusList.add(customer);
        }
        return cusList;
    }

    public static boolean UPDATE(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET CustomerName = ?, Contact = ?, Address = ? , Date = ? ,Nic = ?  WHERE CustomerId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerName());
        pstm.setObject(2, customer.getContact());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getDate());
        pstm.setObject(5, customer.getNic());
        pstm.setObject(6,  customer.getCustomerId());


        return pstm.executeUpdate() > 0;
    }

    public static Customer SEARCH(String id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CustomerId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            String CustomerName = resultSet.getString(2);
            String Contact = resultSet.getString(3);
            String Address = resultSet.getString(4);
            Date Date = java.sql.Date.valueOf(resultSet.getString(5));
            String Nic = resultSet.getString(6);


            Customer customer = new Customer(CustomerId, CustomerName, Contact, Address, Date,Nic);

            return customer;
        }

        return null;
    }

    public static boolean SAVE(String CustomerId, String CustomerName, String Contact, String Address, Date Date, String Nic) throws SQLException {
        String sql = "INSERT INTO Customer VALUES(?, ?, ?, ?,?,?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, CustomerId);
        pstm.setObject(2, CustomerName);
        pstm.setObject(3, Contact);
        pstm.setObject(4, Address);
        pstm.setObject(5, Date);
        pstm.setObject(6, Nic);
        return pstm.executeUpdate() >0;


        /*int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this customer").show();
        }
        return false;*/
    }



    public static String getCurrentId() throws SQLException {

            String sql = "SELECT CustomerId FROM Customer ORDER BY CustomerId DESC LIMIT 1";

            PreparedStatement pstm = dbConnection.getInstance().getConnection()
                    .prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String customerId = resultSet.getString(1);
                return customerId;
            }
            return null;
        }
        public static List<String> getCustomerNIC() throws SQLException {
        String sql = "SELECT Nic FROM Customer";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> customerList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            customerList.add(id);
        }
        return customerList;
    }
    public static Customer SEARCHbyNic(String nic) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE Nic = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, nic);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String CustomerId = resultSet.getString(1);
            String UserName = resultSet.getString(2);
            String Contact = resultSet.getString(3);
            String Address = resultSet.getString(4);
            Date Date = java.sql.Date.valueOf(resultSet.getString(5));
            String Nic = resultSet.getString(6);



            Customer customer = new Customer(CustomerId,UserName,Contact,Address,Date,Nic);

            return customer;
        }

        return null;
    }


}


