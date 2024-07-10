package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@Data@NoArgsConstructor
public class Employee {
    private String EmployeeId;
    private String EmployeeName;
    private java.sql.Date Date;
    private String WorkHours;
    private String Contact;
    private double Salary;
    private String Position;
    private String Attendence;
    private String Address;
    private  String UserId;


    public Employee(String employeeId, String employeeName, java.sql.Date date, String workHours, String contact, String salary, String position, String attendence, String address, String userId) {
    }
}

