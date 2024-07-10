package lk.ijse.HilroHotelManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Meal {
    private String MealId;
    private String MealName;
    private double Price;
    private Date Date;



}
