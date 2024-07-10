package lk.ijse.HilroHotelManagementSystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.HilroHotelManagementSystem.db.dbConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ownerdashboardFormController implements Initializable {

    public Label lblEmployeeCount;
    public Label lblSupplierCount;
    public Label lblRoomCount;
    public Label txtTIME;
    public Label txtDATE;
    public Label lblUSERNAME;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private AnchorPane rootNode;
    private String user;

    private int customerCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentTime();
        setDate();
        try {
            customerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(customerCount);

        try {
            employeeCount = getEmployeeCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmployeeCount(employeeCount);

        try {
            supplierCount = getSupplierCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);

        try {
            roomCount = getRoomCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setRoomCount(roomCount);
    }

    private void setCustomerCount(int customerCount) {
        lblCustomerCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }

    private int employeeCount;

    private void setEmployeeCount(int employeeCount) {
        lblEmployeeCount.setText(String.valueOf(employeeCount));
    }

    private int getEmployeeCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS employee_count FROM Employee";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("employee_count");
        }
        return 0;
    }

    private int supplierCount;

    private void setSupplierCount(int supplierCount) {
        lblSupplierCount.setText(String.valueOf(supplierCount));
    }

    private int getSupplierCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS supplier_count FROM Supplier";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("supplier_count");
        }
        return 0;
    }
    private int roomCount;

    private void setRoomCount(int roomCount) {
        lblRoomCount.setText(String.valueOf(roomCount));
    }

    private int getRoomCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS room_count FROM Room WHERE Avaliability = 'yes'";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("room_count");
        }
        return 0;
    }

    private void setCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        txtTIME.setText(formattedTime);
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private String getUserName(String id) throws SQLException {
        String sql = "SELECT UserName FROM User;";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("UserName");
        }
        return null;
    }

    String UserId;



    public void setId(String userId) {

        this.UserId = userId;

        try {

            user = getUserName(userId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setUserName(user);
    }

    private void setUserName(String user) {
        lblUSERNAME.setText(String.valueOf(user));
    }
}
