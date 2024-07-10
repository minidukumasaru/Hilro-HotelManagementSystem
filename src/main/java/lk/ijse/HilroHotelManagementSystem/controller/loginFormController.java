package lk.ijse.HilroHotelManagementSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.HilroHotelManagementSystem.db.dbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginFormController {

    @FXML
    private Button btnLogin;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;
    @FXML
    private Button btnREGISTERNOW;
    @FXML
    void btnREGISTEROnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/RegisterForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register Form");
    }




    @FXML
    void btnLOGINOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            checkCredential(username, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void checkCredential(String username, String password) throws SQLException, IOException {
        String sql = "SELECT UserName,Password FROM User WHERE UserName = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, username);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String dbpassword = resultSet.getString("Password");

            if (password.equals(dbpassword)) {
                navigateToTheOwnerDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user name can't be find!").show();
        }
    }

    private void navigateToTheOwnerDashboard() throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/MainForm.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Owner Dashboard Form");
        stage.centerOnScreen();

    }

    public void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
        btnLOGINOnAction(actionEvent);
    }
}
