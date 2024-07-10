package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class CustomerTm {
    private String CustomerId;
    private String CustomerName;
    private String Contact;
    private String Address;
    private java.sql.Date Date;
    private  String Nic;



}
