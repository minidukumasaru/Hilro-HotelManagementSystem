package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class RoomTm {
    private String RoomId;
    private double Price;
    private java.sql.Date Date;
    private String Avaliability;
    private String Description;
    private String Type;
}
