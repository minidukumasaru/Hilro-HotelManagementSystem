package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.ReservationDetails;
import lk.ijse.HilroHotelManagementSystem.model.reservationPlaced;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationPlacedRepo {
    public static boolean placereservation(reservationPlaced rl) throws SQLException {
        Connection connection = dbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean IsSaved = ReservationRepo.save(rl.getReservation());

            if (IsSaved) {
                boolean isUpdate = false;
                for (ReservationDetails rd : rl.getOdList()) {
                    if (rd.getMealId() == null) {
                        String ave = "No";
                        isUpdate = RoomRepo.updateAvailability(rd.getRoomId(), ave);

                    } else {

                        isUpdate = MealRepo.SAVE1(rd.getMealId(), rd.getReservationId());
                        System.out.println(rd.getMealId() + rd.getReservationId());
                    }
                }
                    if (isUpdate) {
                        boolean isSave1 = false;
                        for (ReservationDetails ar : rl.getOdList()) {
                            if (ar.getMealId() == null) {

                                isSave1 = RoomRepo.save(ar.getRoomId(), ar.getReservationId(), ar.getIn_Date(), ar.getOut_Date());
                            }
                            if (isSave1) {
                                connection.commit();
                                return true;
                            }
                        }
                    }

            }
            connection.rollback();
            return false;

        } catch (Exception e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }


}
