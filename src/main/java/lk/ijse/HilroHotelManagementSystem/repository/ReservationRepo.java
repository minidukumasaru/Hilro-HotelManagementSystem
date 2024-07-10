package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationRepo {

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT ReservationId FROM Reservation ORDER BY ReservationId DESC LIMIT 1";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String reservationId = resultSet.getString(1);
            return reservationId;
        }
        return null;

    }

    public static boolean save(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO Reservation VALUES (?, ? ,? ,?)";
        PreparedStatement pstm = dbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, reservation.getReservationId());
        pstm.setObject(2, reservation.getDate());
        pstm.setObject(3, reservation.getNic());
        pstm.setObject(4, reservation.getUserId());


        return pstm.executeUpdate() > 0;
    }
}

