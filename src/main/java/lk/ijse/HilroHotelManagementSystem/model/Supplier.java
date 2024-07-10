package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor@NoArgsConstructor@Data
public class Supplier {
    private String SupplierId;
    private String SupplierName;
    private String Address;
    private String Quantity;
    private String Contact;
    private String ProductName;
    private Date Date;


}
