package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class Customer {
    private String CustomerId;
    private String CustomerName;
    private String Contact;
    private String Address;
    private java.sql.Date Date;
    private  String Nic;
}

