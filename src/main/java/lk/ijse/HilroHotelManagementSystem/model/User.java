package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data@NoArgsConstructor@AllArgsConstructor
public class User {
    private String UserId;
    private  String UserName;
    private String Contact;
    private String Position;
    private String Password;
    private Date Date;
}
