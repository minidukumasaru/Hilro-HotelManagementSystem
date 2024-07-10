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
import lk.ijse.HilroHotelManagementSystem.model.Room;
import lk.ijse.HilroHotelManagementSystem.model.tm.RoomTm;
import lk.ijse.HilroHotelManagementSystem.repository.RoomRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class roomFormController {

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
    private TableColumn<?, ?> colAVALIABILITY;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colDESCRIPTION;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colType;


    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<RoomTm> tblROOM;

    @FXML
    private TextField txtAVALIABILITY;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtDESCRIPTION;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtROOMID;

    @FXML
    private TextField txtType;




    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllRoom();
        getCurrentRoomId();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("RoomId"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colAVALIABILITY.setCellValueFactory(new PropertyValueFactory<>("Avaliability"));
        colDESCRIPTION.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }

    private void loadAllRoom() {
        ObservableList<RoomTm> rmList = FXCollections.observableArrayList();

        try {
            List<Room> roomList = RoomRepo.getAll();
            for (Room room : roomList) {
                RoomTm tm = new RoomTm(
                        room.getRoomId(),
                        room.getPrice(),
                        room.getDate(),
                        room.getAvaliability(),
                        room.getDescription(),
                        room.getType()

                );

                rmList.add(tm);
            }

            tblROOM.setItems(rmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtROOMID.setText("");
        txtPRICE.setText("");
        txtDATE.setText("");
        txtAVALIABILITY.setText("");
        txtDESCRIPTION.setText("");
        txtType.setText("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String RoomId = txtROOMID.getText();

        try {
            boolean isDeleted = RoomRepo.DELETE(RoomId);
            if(isDeleted) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Room deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String RoomId = txtROOMID.getText();
        double Price = Double.parseDouble(txtPRICE.getText());
        Date date = Date.valueOf(LocalDate.now());
        String Avaliability = txtAVALIABILITY.getText();
        String Description = txtDESCRIPTION.getText();
        String Type = txtType.getText();




        try {
            if (isValied()) {
                boolean isSave = RoomRepo.SAVE(RoomId,Price,date,Avaliability,Description,Type);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Room Save Successfully!", ButtonType.OK).show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Room Save Unsuccessfully ", ButtonType.OK).show();
                }
            }else {
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllRoom();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtROOMID.getText();

        Room room = RoomRepo.SEARCH(id);
        if (room != null) {
            txtROOMID.setText(room.getRoomId());
            txtPRICE.setText(String.valueOf(room.getPrice()));
            txtDATE.setText(String.valueOf(room.getDate()));
            txtAVALIABILITY.setText(String.valueOf(room.getAvaliability()));
            txtDESCRIPTION.setText(room.getDescription());
            txtType.setText(room.getType());


        } else {
            new Alert(Alert.AlertType.INFORMATION, "Room not found!").show();
        }


    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String RoomId = txtROOMID.getText();
        double Price = Double.parseDouble(txtPRICE.getText());
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String Avaliability = txtAVALIABILITY.getText();
        String Description = txtDESCRIPTION.getText();
        String Type = txtType.getText();



        Room room = new Room(RoomId,Price,date,Avaliability,Description,Type);

        try {
            boolean isUpdated = RoomRepo.UPDATE(room);
            if(isUpdated) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Room updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getCurrentRoomId() throws SQLException {
        String currentId = RoomRepo.getCurrentId();

        String nextroomId = generateNextRoomId(currentId);
        txtROOMID.setText(nextroomId);
    }

    private String generateNextRoomId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("R00");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "R00" + ++idNum;
        }
        return "R001";
    }

    public void tblROOMOnAction(MouseEvent mouseEvent) {
        Integer index = tblROOM.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtROOMID.setText(colID.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtAVALIABILITY.setText(colAVALIABILITY.getCellData(index).toString());
        txtDESCRIPTION.setText(colDESCRIPTION.getCellData(index).toString());
        txtType.setText(colType.getCellData(index).toString());


    }

    public void txtROOMIDOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtAVALIABILITY.requestFocus();
    }

    public void txtAVALIABILITYOnAction(ActionEvent actionEvent) {
        txtDESCRIPTION.requestFocus();
    }

    public void txtDESCRIPTIONOnAction(ActionEvent actionEvent) {
        txtType.requestFocus();
    }

    public void txtTYPEOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }


    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtROOMID)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE)) return false;


        return true;
    }
    public void txtROOMIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtROOMID);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PRICE, txtPRICE);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/RoomReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }


}


