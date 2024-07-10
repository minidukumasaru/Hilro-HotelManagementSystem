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
import lk.ijse.HilroHotelManagementSystem.model.Customer;
import lk.ijse.HilroHotelManagementSystem.model.tm.CustomerTm;
import lk.ijse.HilroHotelManagementSystem.repository.CustomerRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class customerFormController {

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
    private TableColumn<?, ?> colCONTACT;

    @FXML
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colUSERID;
    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private ComboBox<String> combUSERID;

    @FXML
    private ComboBox<String> combMEALID;

    @FXML
    private ComboBox<String> combROOMID;

    @FXML
    private TableColumn<?, ?> colMEALID;



    @FXML
    private TableColumn<?, ?> colMEALNAME;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colQUANTITY;



    @FXML
    private TableColumn<?, ?> colROOMID;

    @FXML
    private TableColumn<?, ?> colTOTAL;


    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CustomerTm> tblCUSTOMER;

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
    private TextField txtNIC;
    @FXML
    private TextField txtMealQty;
    @FXML
    private TextField txtMEALNAME;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtTOTAL;


    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllCustomers();
        getCurrentCustomerId();

    }

    private void setTime(){

    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }



    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("Nic"));


    }

    private void getCurrentCustomerId() throws SQLException {
        String currentId = CustomerRepo.getCurrentId();

        String nextOrderId = generateNextCustomerId(currentId);
        txtCUSTOMERID.setText(nextOrderId);
    }

    private String generateNextCustomerId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("C");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "C" + ++idNum;
        }
        return "C1";
    }
    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getCustomerId(),
                        customer.getCustomerName(),
                        customer.getContact(),
                        customer.getAddress(),
                        customer.getDate(),
                        customer.getNic()

                );

                obList.add(tm);
            }

            tblCUSTOMER.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCUSTOMERID.setText("");
        txtCUSTOMERNAME.setText("");
        txtCONTACT.setText("");
        txtADDRESS.setText("");
        txtDATE.setText("");
        txtNIC.setText("");

    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String Customer_id = txtCUSTOMERID.getText();

        try {
            boolean isDeleted = CustomerRepo.DELETE(Customer_id);
            if(isDeleted) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        String Contact = txtCONTACT.getText();
        String Address = txtADDRESS.getText();
        Date date = Date.valueOf(LocalDate.now());
        String Nic = txtNIC.getText();





        try {
            if (isValied()) {
                boolean isSave = CustomerRepo.SAVE(CustomerId,CustomerName,Contact,Address,date,Nic);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Save Successfully!", ButtonType.OK).show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Save Unsuccessfully ", ButtonType.OK).show();
                }
            }else {
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllCustomers();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtCUSTOMERID.getText();

        Customer customer = CustomerRepo.SEARCH(id);
        if (customer != null) {
            txtCUSTOMERID.setText(customer.getCustomerId());
            txtCUSTOMERNAME.setText(customer.getCustomerName());
            txtCONTACT.setText(String.valueOf(customer.getContact()));
            txtADDRESS.setText(customer.getAddress());
            txtDATE.setText(String.valueOf(customer.getDate()));
            txtNIC.setText(customer.getNic());


        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String CustomerId = txtCUSTOMERID.getText();
        String CustomerName = txtCUSTOMERNAME.getText();
        String Contact = txtCONTACT.getText();
        String Address = txtADDRESS.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());
        String Nic = txtNIC.getText();



        Customer customer = new Customer(CustomerId,CustomerName,Contact,Address,date,Nic);

        try {
            boolean isUpdated = CustomerRepo.UPDATE(customer);
            if(isUpdated) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void tblCUSTOMEROnAction(MouseEvent mouseEvent) {
        Integer index = tblCUSTOMER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtCUSTOMERID.setText(colID.getCellData(index).toString());
        txtCUSTOMERNAME.setText(colNAME.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());
        txtNIC.setText(colNIC.getCellData(index).toString());

    }


    public void txtCUSTOMERIDOnAction(ActionEvent actionEvent) {
        txtCUSTOMERNAME.requestFocus();
    }

    public void txtCUSTOMERNAMEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtNIC.requestFocus();
    }

    public void txtNICOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtCUSTOMERID)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtCUSTOMERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NIC, txtNIC)) return false;


        return true;
    }
    public void txtCUSTOMERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtCUSTOMERID);
    }

    public void txtCUSTOMERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtCUSTOMERNAME);
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

    public void txtNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NIC, txtNIC);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/CustomerReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

}




