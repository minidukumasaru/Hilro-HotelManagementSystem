package lk.ijse.HilroHotelManagementSystem.repository;

import javafx.scene.control.Alert;
import lk.ijse.HilroHotelManagementSystem.db.dbConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    public static void RegisterNow(String UserId, String UserName, String Contact,String Position,String Password,Date date) throws SQLException {
        String sql = "INSERT INTO User VALUES (?, ? ,?, ?,?,? )";
        PreparedStatement pstm = dbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, UserId);
        pstm.setObject(2, UserName);
        pstm.setObject(3, Contact);
        pstm.setObject(4, Position);
        pstm.setObject(5, Password);
        pstm.setObject(6, date);


        int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Register successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't register this user").show();
        }

    }
    public static List<String> getUserIds() throws SQLException {
        String sql = "SELECT UserId FROM User";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public static String getCurrentId() throws SQLException {

        String sql = "SELECT UserId FROM User ORDER BY UserId DESC LIMIT 1";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String userId = resultSet.getString(1);
            return userId;
        }
        return null;


    }
}
