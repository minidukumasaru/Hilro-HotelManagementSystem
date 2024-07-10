package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data@AllArgsConstructor@NoArgsConstructor
public class Room {
    private String RoomId;
    private double Price;
    private Date Date;
    private String Avaliability;
    private String Description;
    private String Type;


}
