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
import lk.ijse.HilroHotelManagementSystem.model.Supplier;
import lk.ijse.HilroHotelManagementSystem.model.tm.SupplierTm;
import lk.ijse.HilroHotelManagementSystem.repository.SupplierRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class supplierFormController {

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
    private TableColumn<?, ?> colPRODUCTNAME;

    @FXML
    private TableColumn<?, ?> colQUANTITY;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<SupplierTm> tblSUPPLIER;

    @FXML
    private TextField txtADDRESS;

    @FXML
    private TextField txtCONTACT;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtPRODUCTNAME;

    @FXML
    private TextField txtQUANTITY;

    @FXML
    private TextField txtSUPPLIERID;

    @FXML
    private TextField txtSUPPLIERNAME;

    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllSupplier();
        getCurrentSupplierId();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colADDRESS.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colQUANTITY.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colCONTACT.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colPRODUCTNAME.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }
    private void loadAllSupplier() {
        ObservableList<SupplierTm> supList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm tm = new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getSupplierName(),
                        supplier.getAddress(),
                        supplier.getQuantity(),
                        supplier.getContact(),
                        supplier.getProductName(),
                        supplier.getDate()
                );

                supList.add(tm);
            }

            tblSUPPLIER.setItems(supList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSUPPLIERID.setText("");
        txtSUPPLIERNAME.setText("");
        txtADDRESS.setText("");
        txtQUANTITY.setText("");
        txtCONTACT.setText("");
        txtPRODUCTNAME.setText("");
        txtDATE.setText("");



    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String SupplierId = txtSUPPLIERID.getText();

        try {
            boolean isDeleted = SupplierRepo.DELETE(SupplierId);
            if(isDeleted) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        String Quantity = txtQUANTITY.getText();
        String Contact = txtCONTACT.getText();
        String ProductName = txtPRODUCTNAME.getText();
        Date date = Date.valueOf(LocalDate.now());


        try {
            if (isValied()) {
                boolean isSave = SupplierRepo.SAVE(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,date);
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
        loadAllSupplier();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtSUPPLIERID.getText();

        Supplier supplier = SupplierRepo.SEARCH(id);
        if (supplier != null) {
            txtSUPPLIERID.setText(supplier.getSupplierId());
            txtSUPPLIERNAME.setText(supplier.getSupplierName());
            txtADDRESS.setText(supplier.getAddress());
            txtQUANTITY.setText(supplier.getQuantity());
            txtCONTACT.setText(String.valueOf(supplier.getContact()));
            txtPRODUCTNAME.setText(supplier.getProductName());
            txtDATE.setText(String.valueOf(supplier.getDate()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Supplier not found!").show();
        }


    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String SupplierId = txtSUPPLIERID.getText();
        String SupplierName = txtSUPPLIERNAME.getText();
        String Address = txtADDRESS.getText();
        String Quantity = txtQUANTITY.getText();
        String Contact = txtCONTACT.getText();
        String ProductName = txtPRODUCTNAME.getText();
        Date date = java.sql.Date.valueOf(LocalDate.now());


        Supplier supplier = new Supplier(SupplierId,SupplierName,Address,Quantity,Contact,ProductName,date);

        try {
            boolean isUpdated = SupplierRepo.UPDATE(supplier);
            if(isUpdated) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void getCurrentSupplierId() throws SQLException {
        String currentId = SupplierRepo.getCurrentId();

        String nextsupplierId = generateNextSupplierId(currentId);
        txtSUPPLIERID.setText(nextsupplierId);
    }

    private String generateNextSupplierId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S00");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "S00" + ++idNum;
        }
        return "S001";
    }

    public void tblSUPPLIEROnAction(MouseEvent mouseEvent) {
        Integer index = tblSUPPLIER.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtSUPPLIERID.setText(colID.getCellData(index).toString());
        txtSUPPLIERNAME.setText(colNAME.getCellData(index).toString());
        txtADDRESS.setText(colADDRESS.getCellData(index).toString());
        txtQUANTITY.setText(colQUANTITY.getCellData(index).toString());
        txtCONTACT.setText(colCONTACT.getCellData(index).toString());
        txtPRODUCTNAME.setText(colPRODUCTNAME.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());


    }

    public void txtSUPPLIERIDOnAction(ActionEvent actionEvent) {
        txtSUPPLIERNAME.requestFocus();
    }

    public void txtSUPPLIERNAMEOnAction(ActionEvent actionEvent) {
        txtADDRESS.requestFocus();
    }

    public void txtADDRESSOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        txtQUANTITY.requestFocus();
    }

    public void txtQUANTITYOnAction(ActionEvent actionEvent) {
        txtCONTACT.requestFocus();
    }

    public void txtCONTACTOnAction(ActionEvent actionEvent) {
        txtPRODUCTNAME.requestFocus();
    }

    public void txtPRODUCTNAMEOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }



    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtSUPPLIERID)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtSUPPLIERNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ADDRESS, txtADDRESS)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.QUANTITY, txtQUANTITY)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT, txtCONTACT)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtPRODUCTNAME)) return false;


        return true;
    }
    public void txtSUPPLIERIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtSUPPLIERID);
    }

    public void txtSUPPLIERNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtSUPPLIERNAME);
    }

    public void txtADDRESSOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ADDRESS, txtADDRESS);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE);
    }
    public void txtQUANTITYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.QUANTITY, txtQUANTITY);
    }

    public void txtCONTACTOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.CONTACT,txtCONTACT);
    }

    public void txtPRODUCTNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtPRODUCTNAME);
    }


    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/SupplierReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }


}





