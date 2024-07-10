package lk.ijse.HilroHotelManagementSystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


    public class dbConnection {
        private static dbConnection dbConnection;
        private Connection connection;

        private dbConnection() throws SQLException {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Hilro_hotel",
                    "root",
                    "Ijse@123"
            );
        }

        public static dbConnection getInstance() throws SQLException {
            if(dbConnection == null) {
                dbConnection = new dbConnection();
            }
            return dbConnection;
        }

        public Connection getConnection() {
            return connection;
        }
    }

