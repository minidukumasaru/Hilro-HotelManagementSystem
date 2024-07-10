package lk.ijse.HilroHotelManagementSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFormController {

    @FXML
    private AnchorPane anpMAIN1;

    @FXML
    private AnchorPane anpMAIN2;

    @FXML
    private AnchorPane anpMAIN3;

    @FXML
    private Button btnCUSTOMER;

    @FXML
    private Button btnDASHBOARD;

    @FXML
    private Button btnEMPLOYEE;

    @FXML
    private Button btnLOGOUT;
    @FXML
    private Button btnROOMBOOKING;

    @FXML
    private Button btnMEAL;

    @FXML
    private Button btnPAYMENT;

    @FXML
    private Button btnRESERVATION;

    @FXML
    private Button btnROOM;

    @FXML
    private Button btnSUPPLIER;

    public void initialize() throws IOException {
        loadDashboardForm();
    }

    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/OwnerDashboardForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(dashboardPane);
    }

    @FXML
    void btnCUSTOMEROnAction(ActionEvent event) throws IOException {
        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(customerPane);


    }

    @FXML
    void btnDASHBOARDOnAction(ActionEvent event) throws IOException {
        AnchorPane dashbordPane = FXMLLoader.load(this.getClass().getResource("/view/OwnerDashboardForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(dashbordPane);


    }

    @FXML
    void btnEMPLOYEEOnAction(ActionEvent event) throws IOException {
        AnchorPane employeePane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(employeePane);


    }

    @FXML
    void btnLOGOUTOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.anpMAIN3.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");



    }

    @FXML
    void btnMEALOnAction(ActionEvent event) throws IOException {
        AnchorPane mealPane = FXMLLoader.load(this.getClass().getResource("/view/MealForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(mealPane);


    }



    @FXML
    void btnRESERVATIONOnAction(ActionEvent event) throws IOException {
        AnchorPane reservationPane = FXMLLoader.load(this.getClass().getResource("/view/ReservationForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(reservationPane);


    }

    @FXML
    void btnROOMOnAction(ActionEvent event) throws IOException {
        AnchorPane roomPane = FXMLLoader.load(this.getClass().getResource("/view/RoomForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(roomPane);


    }

    @FXML
    void btnSUPPLIEROnAction(ActionEvent event) throws IOException {
        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/SupplierForm.fxml"));


        anpMAIN1.getChildren().clear();
        anpMAIN1.getChildren().add(supplierPane);
    }


}





