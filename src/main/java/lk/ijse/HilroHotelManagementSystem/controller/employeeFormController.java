package lk.ijse.HilroHotelManagementSystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.HilroHotelManagementSystem.Util.Regex;
import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Employee;
import lk.ijse.HilroHotelManagementSystem.model.tm.EmployeeTm;
import lk.ijse.HilroHotelManagementSystem.repository.EmployeeRepo;
import lk.ijse.HilroHotelManagementSystem.repository.UserRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class employeeFormController {

    public Button btnDETAILS;
    @FXML
    private Button btnBACK;

    @FXML
    private Button btnCLEAR;

    @FXML
    private Button btnDELETE;

    @FXML
    private Button btnSAVE;

    @FXML
    private Button btnSEARCH;

    @FXML
    private Button btnUPDATE;

    @FXML
    private TableColumn<?, ?> colADDRESS;

    @FXML
    private TableColumn<?, ?> colATTENDENCE;

    @FXML
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colPOSITION;

    @FXML
    private TableColumn<?, ?> colSALARY;

    @FXML
    private TableColumn<?, ?> colUSERID;

    @FXML
    private TableColumn<?, ?> colWORKHOURS;

    @FXML
    private ComboBox<String> combUSERID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEMPLOYEE;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtATTENDENCE;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtEMPLOYEEID;

    @FXML
    private TextField txtEMPLOYEENAME;

    @FXML
    private TextField txtPOSITION;

    @FXML
    private TextField txtSALARY;

    @FXML
    private TextField txtWORKHOURS;



    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllEmployee();
        getUserIds();
        getCurrentEmployeeId();

    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colWORKHOURS.setCellValueFactory(new PropertyValueFactory<>("WorkHours"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colSALARY.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colPOSITION.setCellValueFactory(new PropertyValueFactory<>("Position"));
        colATTENDENCE.setCellValueFactory(new PropertyValueFactory<>("Attendence"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colUSERID.setCellValueFactory(new PropertyValueFactory<>("UserId"));

    }
    private void loadAllEmployee() {
        ObservableList<EmployeeTm> emList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                EmployeeTm tm = new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getDate(),
                        employee.getWorkHours(),
                        employee.getContact(),
                        employee.getSalary(),
                        employee.getPosition(),
                        employee.getAttendence(),
                        employee.getAddress(),
                        employee.getUserId()

                );

                emList.add(tm);
            }

            tblEMPLOYEE.setItems(emList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEMPLOYEEID.setText("");
        txtEMPLOYEENAME.setText("");
        txtDATE.setText("");
        txtWORKHOURS.setText("");
        txtCONTACT.setText("");
        txtSALARY.setText("");
        txtPOSITION.setText("");
        txtATTENDENCE.setText("");
        txtADDRESS.setText("");
        combUSERID.setValue("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();

        try {
            boolean isDeleted = EmployeeRepo.DELETE(EmployeeId);
            if(isDeleted) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        Date date = Date.valueOf(LocalDate.now());
        String WorkHours = txtWORKHOURS.getText();
        String Contact = txtCONTACT.getText();
        double Salary = Double.parseDouble(txtSALARY.getText());
        String Position = txtPOSITION.getText();
        String Attendence = txtATTENDENCE.getText();
        String Address = txtADDRESS.getText();
        String UserId = combUSERID.getValue();


        try {
            if (isValied()) {
                boolean isSave = EmployeeRepo.SAVE(EmployeeId,EmployeeName,date,WorkHours,Contact,Salary,Position,Attendence,Address,UserId);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Save Successfully!", ButtonType.OK).show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Save Unsuccessfully ", ButtonType.OK).show();
                }
            }else {
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllEmployee();
    }





    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtEMPLOYEEID.getText();

        Employee employee = EmployeeRepo.SEARCH(id);
        if (employee != null) {
            txtEMPLOYEEID.setText(employee.getEmployeeId());
            txtEMPLOYEENAME.setText(employee.getEmployeeName());
            txtDATE.setText(String.valueOf(employee.getDate()));
            txtWORKHOURS.setText(employee.getWorkHours());
            txtCONTACT.setText(String.valueOf(employee.getContact()));
            txtSALARY.setText(String.valueOf(employee.getSalary()));
            txtPOSITION.setText(employee.getPosition());
            txtATTENDENCE.setText(employee.getAttendence());
            txtADDRESS.setText(employee.getAddress());
            combUSERID.setValue(employee.getUserId());

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Employee not found!").show();
        }
    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String EmployeeId = txtEMPLOYEEID.getText();
        String EmployeeName = txtEMPLOYEENAME.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String WorkHours = txtWORKHOURS.getText();
        String Contact = txtCONTACT.getText();
        double Salary = Double.parseDouble(txtSALARY.getText());
        String Position = txtPOSITION.getText();
        String Attendence = txtATTENDENCE.getText();
        String Address = txtADDRESS.getText();
        String UserId = combUSERID.getValue();



        Employee employee = new Employee(EmployeeId,EmployeeName,date,WorkHours,Contact,Salary,Position,Attendence,Address,UserId);

        try {
            boolean isUpdated = EmployeeRepo.UPDATE(employee);
            if(isUpdated) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
    private void getUserIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> userList = UserRepo.getUserIds();
            for (String id : userList) {
                obList.add(id);
            }
            combUSERID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCurrentEmployeeId() throws SQLException {
        String currentId = EmployeeRepo.getCurrentId();

        String nextemployeeId = generateNextEmployeeId(currentId);
        txtEMPLOYEEID.setText(nextemployeeId);
    }

    private String generateNextEmployeeId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("E00");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "E00" + ++idNum;
        }
        return "E001";
    }

    public void tblEMPLOYEEOnAction(MouseEvent mouseEvent) {
        Integer index = tblEMPLOYEE.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtEMPLOYEEID.setText(colID.getCellData(index).toString());
        txtEMPLOYEENAME.setText(colNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtWORKHOURS.setText(colWORKHOURS.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtSALARY.setText(colSALARY.getCellData(index).toString());
        txtPOSITION.setText(colPOSITION.getCellData(index).toString());
        txtATTENDENCE.setText(colATTENDENCE.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        combUSERID.setValue(colUSERID.getCellData(index).toString());



    }

    public void txtEMPLOYEEIDOnAction(ActionEvent actionEvent) {
        txtEMPLOYEENAME.requestFocus();
    }

    public void txtEMPLOYEENAMEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtWORKHOURS.requestFocus();
    }

    public void txtWORKHOURSOnAction(ActionEvent actionEvent) {
        txtSALARY.requestFocus();
    }

    public void txtSALARYOnAction(ActionEvent actionEvent) {
        txtPOSITION.requestFocus();
    }

    public void txtPOSITIONOnAction(ActionEvent actionEvent) {
        txtATTENDENCE.requestFocus();
    }

    public void txtATTENDENCEOnAction(ActionEvent actionEvent) {
        combUSERID.requestFocus();
    }

    public void combUSERIDOnAction(ActionEvent actionEvent) {

    }


    public void txtEMPLOYEEIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtEMPLOYEEID);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtEMPLOYEEID)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtEMPLOYEENAME)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ADDRESS, txtADDRESS)) return false;


        return true;
    }

    public void txtEMPLOYEENAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtEMPLOYEENAME);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtCONTACT);
    }

    public void btnSDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/EmployeeReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

}