package lk.ijse.HilroHotelManagementSystem.repository;

import lk.ijse.HilroHotelManagementSystem.db.dbConnection;
import lk.ijse.HilroHotelManagementSystem.model.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealRepo {
    public static boolean DELETE(String id) throws SQLException {
        String sql = "DELETE FROM Meal WHERE MealId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static List<Meal> getAll() throws SQLException {
        String sql = "SELECT * FROM Meal";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Meal> mealList = new ArrayList<>();

        while (resultSet.next()) {
            String MealId = resultSet.getString(1);
            String MealName = resultSet.getString(2);
            double Price = Double.valueOf(resultSet.getString(3));
            Date Date = java.sql.Date.valueOf(resultSet.getString(4));

            Meal meal = new Meal(MealId,MealName,Price,Date);
            mealList.add(meal);
        }
        return mealList;
    }
    public static boolean UPDATE(Meal meal) throws SQLException {
        String sql = "UPDATE Meal SET MealName = ?, Price = ? , Date = ? WHERE MealId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, meal.getMealName());
        pstm.setObject(2, meal.getPrice());
        pstm.setObject(3,meal.getDate());
        pstm.setObject(4,meal.getMealId());
        return pstm.executeUpdate() > 0;
    }

    public static Meal SEARCH(String id) throws SQLException {
        String sql = "SELECT * FROM Meal WHERE MealId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String MealId = resultSet.getString(1);
            String MealName = resultSet.getString(2);
            double Price = Double.valueOf(resultSet.getString(3));
            Date Date = java.sql.Date.valueOf(resultSet.getString(4));


            Meal meal = new Meal(MealId,MealName,Price,Date);

            return meal;
        }

        return null;
    }

    public static boolean SAVE(String MealId, String MealName, double Price, Date Date) throws SQLException {
        String sql = "INSERT INTO Meal VALUES(?, ?, ?,?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, MealId);
        pstm.setObject(2, MealName);
        pstm.setObject(3, Price);
        pstm.setObject(4, Date);
        return pstm.executeUpdate() >0;

        /*int effectedRows = pstm.executeUpdate();
        if (effectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Meal save successfully!!!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Can't save this Meal").show();
        }
        return false;*/
    }
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MealId FROM Meal ORDER BY MealId DESC LIMIT 1";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String mealId = resultSet.getString(1);
            return mealId;
        }
        return null;

    }


    public static Meal SEARCHbyMealId(String mealId) throws SQLException {
        String sql = "SELECT * FROM Meal WHERE MealId = ?";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, mealId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String MealId = resultSet.getString(1);
            String name = resultSet.getString(2);
            double Price = Double.valueOf(resultSet.getString(3));
            Date Date = java.sql.Date.valueOf(resultSet.getString(4));


            Meal meal = new Meal(mealId, name, Price, Date);


            return meal;
        }

        return null;
    }

    public static List<String> getMealIds() throws SQLException {
        String sql = "SELECT MealId FROM Meal";

        PreparedStatement pstm = dbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }

    public static boolean SAVE1(String mealId, String reservationId) throws SQLException {
        String sql = "INSERT INTO MealReservationInfo VALUES(?, ?)";

        Connection connection = dbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, reservationId);
        pstm.setObject(2, mealId);

        return pstm.executeUpdate()>0;


    }
}
