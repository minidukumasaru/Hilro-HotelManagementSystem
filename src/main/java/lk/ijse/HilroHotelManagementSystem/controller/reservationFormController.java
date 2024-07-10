package lk.ijse.HilroHotelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.*;
import lk.ijse.HilroHotelManagementSystem.model.tm.CartTm;
import lk.ijse.HilroHotelManagementSystem.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class reservationFormController {

    public ComboBox <String> combMEALID;
    public TextField txtMEALNAME;
    public TextField txtPRICE;
    public TableColumn colDescription;
    public TableColumn colAmount;
    public Button btnAddToCart;
    public TableColumn colAction;
    @FXML
    private Button btnBACK;

    @FXML
    private Button btnPLACEORDER;

    @FXML
    private TableColumn<?, ?> colCUSTOMERNIC;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colDURATION;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colTOTAL;

    @FXML
    private ComboBox<String> combCUSTOMERNIC;

    @FXML
    private ComboBox<String> combROOMID;

    @FXML
    private ComboBox<String> combUSERID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblRESERVATION;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtCUSTOMERID;

    @FXML
    private TextField txtCUSTOMERNAME;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtDURATION;

    @FXML
    private TextField txtMEALTOTAL;

    @FXML
    private TextField txtNETBALANCE;

    @FXML
    private TextField txtRESERVATIONID;

    @FXML
    private TextField txtROOMPRICE;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        getUserIds();
        getRoomIds();
        getCustomerNic();
        getCurrentReservationId();
        getMealIds();
    }

    private void getRoomIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> roomList = RoomRepo.getRoomIds();
            for (String id : roomList) {
                obList.add(id);
            }
            combROOMID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getMealIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> mealList = MealRepo.getMealIds();
            for (String id : mealList) {
                obList.add(id);
            }
            combMEALID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
        txtDURATION.setText(String.valueOf(now));
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

    private void getCustomerNic() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> customerList = CustomerRepo.getCustomerNIC();
            for (String id : customerList) {
                obList.add(id);
            }
            combCUSTOMERNIC.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("InDate"));
        colDURATION.setCellValueFactory(new PropertyValueFactory<>("OutDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));




    }


    @FXML
    void btnPLACEORDEROnAction(ActionEvent event) throws JRException, SQLException {
        String ReCode=txtRESERVATIONID.getText();
        Date date = Date.valueOf(txtDATE.getText());
        String userId =combUSERID.getValue();
        String Nic =combCUSTOMERNIC.getValue();
        String roomId= null;
        String mealId= null;
        char rId='R';

        Reservation reservation = new Reservation(ReCode, date, Nic, userId);
        ArrayList<ReservationDetails> odList = new ArrayList<>();

        for (int i = 0; i < tblRESERVATION.getItems().size(); i++){
            CartTm tm = obList.get(i);

            boolean isEqual = checkId(tm.getId(),rId);

            if (isEqual){
                roomId = tm.getId();
                mealId = null;

            }else {
                mealId= tm.getId();
                roomId = null;
            }

            ReservationDetails rd = new ReservationDetails(
                    ReCode,
                    userId,
                    Nic,
                    roomId,
                    mealId,
                    tm.getInDate(),
                    tm.getOutDate()

            );

            odList.add(rd);
            System.out.println("rd = " + rd);
        }
        reservationPlaced rl = new reservationPlaced(reservation,odList);


        try {
            boolean isPlaced = ReservationPlacedRepo.placereservation(rl);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                makeBill();

            }else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void makeBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/BillDetail.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("ReservationId1",txtRESERVATIONID.getText());
        data.put("NetTotal",txtNETBALANCE.getText());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);



    }
    private boolean checkId(String id, char rId) {
        for (int i = 0; i < id.length(); i++) {
            char ch = id.charAt(i);

            if (rId == (ch)) {
                System.out.println("chhhhhhh");
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        }
        return false;
    }




    public void combCUSTOMERNICOnAction(ActionEvent actionEvent) {
        String nic =  combCUSTOMERNIC.getValue();

        try {
            Customer customer = CustomerRepo.SEARCHbyNic(nic);
            txtADDRESS.setText(customer.getAddress());
            txtCONTACT.setText(customer.getContact());
            txtCUSTOMERNAME.setText(customer.getCustomerName());



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void combROOMIDOnAction(ActionEvent actionEvent){
        String RoomId =  combROOMID.getValue();

        try {
            Room room = RoomRepo.SEARCHbyRoomId(RoomId);


            if (room!= null){
                txtPRICE.setText(String.valueOf(room.getPrice()));
                txtMEALNAME.setText(String.valueOf(room.getType()));
                combMEALID.getSelectionModel().clearSelection();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCurrentReservationId() throws SQLException {
        String currentId = ReservationRepo.getCurrentId();

        String nextemployeeId = generateNextReservationId(currentId);
        txtRESERVATIONID.setText(nextemployeeId);
    }

    private String generateNextReservationId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("R");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "R" + ++idNum;
        }
        return "R1";
    }


    public void combMEALNAMEOnAction(ActionEvent actionEvent) {
        String MealId =  combMEALID.getValue();

        try {
           Meal meal = MealRepo.SEARCHbyMealId(MealId);


            if (meal!= null){
                txtPRICE.setText(String.valueOf(meal.getPrice()));
                txtMEALNAME.setText(String.valueOf(meal.getMealName()));
                combROOMID.getSelectionModel().clearSelection();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblRESERVATION.getItems().size(); i++) {
            netTotal += (double) colAmount.getCellData(i);
        }
        txtNETBALANCE.setText(String.valueOf(netTotal));
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String ReCode=txtRESERVATIONID.getText();
        Date Indate = Date.valueOf(txtDATE.getText());
        String time =txtDURATION.getText();
        String Nic =combCUSTOMERNIC.getValue();
        String cusName=txtCUSTOMERNAME.getText();
        String Address =txtADDRESS.getText();
        String contact=txtCONTACT.getText();
        String userId =combUSERID.getValue();
        Date OutDate = Date.valueOf(txtDURATION.getText());

        String id;
        String name=txtMEALNAME.getText();
        double price= Double.parseDouble(txtPRICE.getText());

        if (combMEALID.getValue() == null){
            id=combROOMID.getValue();
            System.out.println("name"+name);

        }else {
            id=combMEALID.getValue();
            System.out.println("name       "+id);
        }
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblRESERVATION.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblRESERVATION.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblRESERVATION.getItems().size(); i++) {
            if(ReCode.equals(colID.getCellData(i))) {

                CartTm tm = obList.get(i);

                tblRESERVATION.refresh();

                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(userId,ReCode,Indate,OutDate,id,name,price,Address,contact, btnRemove);
        obList.add(tm);

        tblRESERVATION.setItems(obList);
        calculateNetTotal();


    }



}
