package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Employee WHERE EmployeeId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM Employee";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> empList = new ArrayList<>();

        while (resultSet.next()) {
            String EmployeeId = resultSet.getString(1);
            String EmployeeName = resultSet.getString(2);
            Date Date = java.sql.Date.valueOf(resultSet.getString(3));
            String WorkHours = resultSet.getString(4);
            String Contact = resultSet.getString(5);
            double Salary = Double.parseDouble(resultSet.getString(6));
            String Position = resultSet.getString(7);
            String Attendence = resultSet.getString(8);
            String Address = resultSet.getString(9);
            String UserId = resultSet.getString(10);



            Employee employee = new Employee(EmployeeId, EmployeeName,Date,WorkHours,Contact,Salary,Position,Attendence,Address,UserId);
            empList.add(employee);
        }
        return empList;
    }
    public static boolean UPDATE(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET EmployeeName = ?, Date = ?, WorkHours = ? , Contact = ? ,Salary = ? ,Position = ? ,Attendence = ? ,Address = ?,UserId = ? WHERE EmployeeId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, employee.getEmployeeName());
        pstm.setObject(2, employee.getDate());
        pstm.setObject(3, employee.getWorkHours());
        pstm.setObject(4, employee.getContact());
        pstm.setObject(5, employee.getSalary());
        pstm.setObject(6, employee.getPosition());
        pstm.setObject(7, employee.getAttendence());
        pstm.setObject(8, employee.getAddress());
        pstm.setObject(9, employee.getUserId());
        pstm.setObject(10,employee.getEmployeeId());

        return pstm.executeUpdate() > 0;
    }
    public static Employee SEARCH(String id) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE EmployeeId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String EmployeeId = resultSet.getString(1);
            String EmployeeName = resultSet.getString(2);
            Date Date = java.sql.Date.valueOf(resultSet.getString(3));
            String WorkHours = resultSet.getString(4);
            String Contact = resultSet.getString(5);
            double Salary = Double.parseDouble(resultSet.getString(6));
            String Position = resultSet.getString(7);
            String Attendence = resultSet.getString(8);
            String Address = resultSet.getString(9);
            String UserId = resultSet.getString(10);




            Employee employee = new Employee(EmployeeId, EmployeeName,Date,WorkHours,Contact,Salary,Position,Attendence,Address,UserId);

            return employee;
        }

        return null;
    }
    public static boolean SAVE(String EmployeeId, String EmployeeName, Date Date, String WorkHours, String Contact, double Salary, String Position, String Attendence, String Address, String UserId) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?,?,?,?,?,?,?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, EmployeeId);
        pstm.setObject(2, EmployeeName);
        pstm.setObject(3, Date);
        pstm.setObject(4, WorkHours);
        pstm.setObject(5, Contact);
        pstm.setObject(6, Salary);
        pstm.setObject(7, Position);
        pstm.setObject(8, Attendence);
        pstm.setObject(9, Address);
        pstm.setObject(10,UserId);
        return pstm.executeUpdate() >0;

        /*int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this Employee").show();
        }
        return false;*/
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT EmployeeId FROM Employee ORDER BY EmployeeId DESC LIMIT 1";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String employeeId = resultSet.getString(1);
            return employeeId;
        }
        return null;

    }

}
