package lk.ijse.HilroHotelManagementSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.HilroHotelManagementSystem.Util.Regex;
import lk.ijse.HilroHotelManagementSystem.repository.UserRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class registerFormController {

    public Button btnBACK;
    @FXML
    private Button btnRegisterNow;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;



    public void initialize() throws SQLException {
        setDate();
        getCurrentUserId();

    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    @FXML
    void btnREGISTEROnAction(ActionEvent event) throws IOException {
        String UserId = txtUserId.getText();
        String UserName = txtUserName.getText();
        String Contact = txtUserName.getText();
        String Position = txtUserName.getText();
        String Password = txtPassword.getText();
        Date date = Date.valueOf(txtDate.getText());

        try {
            UserRepo.RegisterNow(UserId, UserName,Contact,Position,Password,date);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }
    private void getCurrentUserId() throws SQLException {
        String currentId = UserRepo.getCurrentId();

        String nextuserId = generateNextUserId(currentId);
        txtUserId.setText(nextuserId);
    }

    private String generateNextUserId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("U");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "U00" + ++idNum;
        }
        return "U001";
    }


    public void txtUSERIDOnAction(ActionEvent actionEvent) {
        txtUserName.requestFocus();
    }

    public void txtUSERNAMEOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtPosition.requestFocus();
    }

    public void txtPOSITIONOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPASSWORDOnAction(ActionEvent actionEvent) {
        txtDate.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) throws IOException {
        btnREGISTEROnAction(actionEvent);
    }


    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtUserId)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtPosition)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PASSWORD, txtPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDate)) return false;


        return true;
    }
    public void txtUSERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtUserId);
    }

    public void txtUSERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtUserName);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtContact);
    }
    public void txtPOSITIONOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtPosition);
    }

    public void txtPASSWORDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PASSWORD, txtPassword);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDate);
    }

    public void btnBACKOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }


}
