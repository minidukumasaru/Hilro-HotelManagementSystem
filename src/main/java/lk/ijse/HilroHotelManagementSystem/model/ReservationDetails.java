package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class ReservationDetails {
    private String ReservationId;
    private String UserId;
    private String nic;
    private String RoomId;
    private String MealId;
    private Date In_Date;
    private Date Out_Date;


}
