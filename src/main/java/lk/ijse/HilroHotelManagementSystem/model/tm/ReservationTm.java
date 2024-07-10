package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class ReservationTm {
    private String ReservationId;
    private Date Date;
    private String Duration;
    private String Nic;
    private String UserId;

}
