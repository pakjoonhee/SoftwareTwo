package Models;

import software.ii.project.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utility {
    public static int getDayOfWeekAsInt(String day) {
        if (day == null) {
            return -1;
        }
        
        switch (day.toLowerCase()) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            case "saturday":
                return 6;
            case "sunday":
                return 0;
            default: 
                return -1;
        }
    }
    
    public static String getDay(String day, String month, String year) {
        return LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day)
        ).getDayOfWeek().toString();
    }
    
}
