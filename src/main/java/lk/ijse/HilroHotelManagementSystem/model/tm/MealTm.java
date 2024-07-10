package lk.ijse.HilroHotelManagementSystem.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor@Data@AllArgsConstructor
public class MealTm {
    private String MealId;
    private String MealName;
    private double Price;
    private Date Date;



}
