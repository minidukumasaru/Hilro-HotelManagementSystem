package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class UserTm {


        private String UserId;
        private  String UserName;
        private String Contact;
        private String Position;
        private String Password;
        private java.sql.Date Date;
}

