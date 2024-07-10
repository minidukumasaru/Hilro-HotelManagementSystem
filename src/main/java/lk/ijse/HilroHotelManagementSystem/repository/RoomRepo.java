package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Room;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepo {
    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Room WHERE RoomId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Room> getAll() throws SQLException {
        String sql = "SELECT * FROM Room";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Room> rmList = new ArrayList<>();

        while (resultSet.next()) {
            String RoomId = resultSet.getString(1);
            double Price = Double.parseDouble(resultSet.getString(2));
            Date Date = java.sql.Date.valueOf(resultSet.getString(3));
            String Avaliability = resultSet.getString(4);
            String Description = resultSet.getString(5);
            String Type = resultSet.getString(6);



            Room room = new Room(RoomId,Price,Date,Avaliability,Description,Type);
            rmList.add(room);
        }
        return rmList;
    }

    public static boolean UPDATE(Room room) throws SQLException {
        String sql = "UPDATE Room SET Price = ?, Date = ?, Avaliability = ? , Description = ?,Type = ? WHERE RoomId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, room.getPrice());
        pstm.setObject(2, room.getDate());
        pstm.setObject(3,room.getAvaliability());
        pstm.setObject(4,room.getDescription());
        pstm.setObject(5, room.getType());
        pstm.setObject(6, room.getRoomId());
        return pstm.executeUpdate() > 0;
    }

    public static Room SEARCH(String id) throws SQLException {
        String sql = "SELECT * FROM Room WHERE RoomId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String RoomId = resultSet.getString(1);
            double Price = Double.parseDouble(resultSet.getString(2));
            Date Date = java.sql.Date.valueOf(resultSet.getString(3));
            String Avaliability = resultSet.getString(4);
            String Description = resultSet.getString(5);
            String Type = resultSet.getString(6);



            Room room = new Room(RoomId,Price,Date,Avaliability,Description,Type);

            return room;
        }

        return null;
    }
    public static boolean SAVE(String RoomId, double Price, Date Date , String Avaliability , String Description, String Type) throws SQLException {
        String sql = "INSERT INTO Room VALUES(?, ?, ?, ?,?,?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, RoomId);
        pstm.setObject(2, Price);
        pstm.setObject(3, Date);
        pstm.setObject(4, Avaliability);
        pstm.setObject(5, Description);
        pstm.setObject(6,Type);
        return pstm.executeUpdate() >0;

        /*int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Room save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this Room").show();
        }
        return false;*/
    }

    public static List<String> getRoomIds() throws SQLException {
        String sql = "SELECT RoomId FROM Room WHERE Avaliability='yes'";

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

    public static Room SEARCHbyRoomId(String roomId) throws SQLException {
        String sql = "SELECT * FROM Room WHERE RoomId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, roomId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String RoomId = resultSet.getString(1);
            double Price = Double.valueOf(resultSet.getString(2));
            Date Date = java.sql.Date.valueOf(resultSet.getString(3));
            String Avaliability= resultSet.getString(4);
            String Description = resultSet.getString(5);
            String Type = resultSet.getString(6);




            Room room = new Room(RoomId,Price,Date,Avaliability,Description,Type);

            return room;
        }

        return null;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT RoomId FROM Room ORDER BY RoomId DESC LIMIT 1";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String roomId = resultSet.getString(1);
            return roomId;
        }
        return null;

    }


    public static boolean updateAvailability(String roomId, String ave) throws SQLException {
        String sql = "UPDATE Room SET Avaliability = ? WHERE RoomId = ? ";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1,ave);
        pstm.setString(2,roomId);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(String roomId, String reservationId, Date inDate, Date outDate) throws SQLException {
        String sql = "INSERT INTO RoomReservationInfo VALUES(?, ?, ?, ?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, reservationId);
        pstm.setObject(2, roomId);
        pstm.setObject(3, inDate);
        pstm.setObject(4, outDate);

        return pstm.executeUpdate()>0;
    }
}
