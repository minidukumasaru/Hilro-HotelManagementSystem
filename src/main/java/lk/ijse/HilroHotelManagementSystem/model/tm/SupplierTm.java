package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class SupplierTm {
    private String SupplierId;
    private String SupplierName;
    private String Address;
    private String Quantity;
    private String Contact;
    private String ProductName;
    private Date Date;



}
