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
import lk.ijse.HilroHotelManagementSystem.model.Meal;
import lk.ijse.HilroHotelManagementSystem.model.tm.MealTm;
import lk.ijse.HilroHotelManagementSystem.repository.MealRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class mealFormController {

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
    private TableColumn<?, ?> colDATE;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colNAME;

    @FXML
    private TableColumn<?, ?> colPRICE;

    @FXML
    private TableColumn<?, ?> colQUANTITY;

    @FXML
    private TableColumn<?, ?> colRESERVATIONID;

    @FXML
    private ComboBox<?> combRESERVATIONID;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<MealTm> tblMEAL;

    @FXML
    private TextField txtDATE;

    @FXML
    private TextField txtMEALID;

    @FXML
    private TextField txtMEALNAME;

    @FXML
    private TextField txtPRICE;

    @FXML
    private TextField txtQUANTITY;

    public void initialize() throws SQLException {
        setDate();
        setCellValueFactory();
        loadAllMeal();
        getCurrentMealId();
    }



    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDATE.setText(String.valueOf(now));
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("MealId"));
        colNAME.setCellValueFactory(new PropertyValueFactory<>("MealName"));
        colPRICE.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colDATE.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }
    private void loadAllMeal() {
        ObservableList<MealTm> mlList = FXCollections.observableArrayList();

        try {
            List<Meal> mealList = MealRepo.getAll();
            for (Meal meal : mealList) {
                MealTm tm = new MealTm(
                        meal.getMealId(),
                        meal.getMealName(),
                        meal.getPrice(),
                        meal.getDate()


                );

                mlList.add(tm);
            }

            tblMEAL.setItems(mlList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtMEALID.setText("");
        txtMEALNAME.setText("");
        txtPRICE.setText("");
        txtDATE.setText("");


    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) {
        String MealId = txtMEALID.getText();

        try {
            boolean isDeleted = MealRepo.DELETE(MealId);
            if(isDeleted) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Meal deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) {
        String MealId = txtMEALID.getText();
        String MealName = txtMEALNAME.getText();
        String Price = txtPRICE.getText();
        Date date = Date.valueOf(LocalDate.now());



        try {
            if (isValied()) {
                boolean isSave = MealRepo.SAVE(MealId,MealName, Double.parseDouble(Price),date);
                if (isSave) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Meal Save Successfully!", ButtonType.OK).show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Meal Save Unsuccessfully ", ButtonType.OK).show();
                }
            }else {
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadAllMeal();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) throws SQLException {
        String id = txtMEALID.getText();

        Meal meal = MealRepo.SEARCH(id);
        if (meal != null) {
            txtMEALID.setText(meal.getMealId());
            txtMEALNAME.setText(meal.getMealName());
            txtPRICE.setText(String.valueOf(meal.getPrice()));
            txtDATE.setText(String.valueOf(meal.getDate()));

        } else {
            new Alert(Alert.AlertType.INFORMATION, "Meal not found!").show();
        }

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) {
        String MealId = txtMEALID.getText();
        String MealName = txtMEALNAME.getText();
        Double Price = Double.valueOf(txtPRICE.getText());
        Date date = java.sql.Date.valueOf(LocalDate.now());


        Meal meal = new Meal(MealId,MealName,Price,date);

        try {
            boolean isUpdated = MealRepo.UPDATE(meal);
            if(isUpdated) {
                initialize();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Meal updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void getCurrentMealId() throws SQLException {
        String currentId = MealRepo.getCurrentId();

        String nextmealId = generateNextMealId(currentId);
        txtMEALID.setText(nextmealId);
    }

    private String generateNextMealId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("M00");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "M00" + ++idNum;
        }
        return "M001";
    }

    public void tblMEALOnAction(MouseEvent mouseEvent) {
        Integer index = tblMEAL.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtMEALID.setText(colID.getCellData(index).toString());
        txtMEALNAME.setText(colNAME.getCellData(index).toString());
        txtPRICE.setText(colPRICE.getCellData(index).toString());
        txtDATE.setText(colDATE.getCellData(index).toString());



    }

    public void txtMEALIDOnAction(ActionEvent actionEvent) {
        txtMEALNAME.requestFocus();
    }

    public void txtMEALNAMEOnAction(ActionEvent actionEvent) {
        txtPRICE.requestFocus();
    }

    public void txtPRICEOnAction(ActionEvent actionEvent) {
        txtDATE.requestFocus();
    }

    public void txtDATEOnAction(ActionEvent actionEvent) {
        btnSAVEOnAction(actionEvent);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtMEALID)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtMEALNAME)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PRICE, txtPRICE)) return false;
        if (!Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE)) return false;



        return true;
    }
    public void txtMEALIDOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.ID, txtMEALID);
    }

    public void txtMEALNAMEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.NAME, txtMEALNAME);
    }

    public void txtPRICEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.PRICE, txtPRICE);
    }

    public void txtDATEOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.HilroHotelManagementSystem.Util.TextField.DATE, txtDATE);
    }

    public void btnDETAILSOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("/home/tinka/Desktop/Hilro-HotelManagementSystem/src/main/resources/Reports/MealReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

}


