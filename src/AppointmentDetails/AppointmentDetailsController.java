/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentDetails;

import Models.Days;
import static Models.Utility.getDay;
import static Models.Utility.getDayOfWeekAsInt;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AppointmentDetailsController implements Initializable {
    @FXML private Button loginButton;
    @FXML private TableView<Days> monthTable;
    @FXML private TableColumn<Days, String> sunday;
    @FXML private TableColumn<Days, String> monday;
    @FXML private TableColumn<Days, String> tuesday;
    @FXML private TableColumn<Days, String> wednesday;
    @FXML private TableColumn<Days, String> thursday;
    @FXML private TableColumn<Days, String> friday;
    @FXML private TableColumn<Days, String> saturday;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthTable.getSelectionModel().setCellSelectionEnabled(true);
        
        sunday.setCellValueFactory(new PropertyValueFactory<Days, String>("sunday"));
        monday.setCellValueFactory(new PropertyValueFactory<Days, String>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<Days, String>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<Days, String>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<Days, String>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<Days, String>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<Days, String>("saturday"));
        
        LocalDate date = LocalDate.of(2019, 01, 01);
        int numDays = date.lengthOfMonth();
        
        String firstDay = getDay("01", "01", "2019");
        int convertDay = getDayOfWeekAsInt(firstDay);
        
        ArrayList<String> dayList = new ArrayList<>();
        ObservableList<Days> days = FXCollections.observableArrayList();

        int count = 0;
        int day = 1;

        while(numDays >= 0) {
            if(dayList.size() == 7) {
                days.add(new Days(dayList.get(0), dayList.get(1), dayList.get(2), 
                    dayList.get(3), dayList.get(4), dayList.get(5),dayList.get(6)));
                dayList.clear();
            } else if(numDays == 0) {
                while(!dayList.isEmpty()) {
                    dayList.add(" ");
                    if(dayList.size() == 7) {
                        days.add(new Days(dayList.get(0), dayList.get(1), dayList.get(2), 
                            dayList.get(3), dayList.get(4), dayList.get(5),dayList.get(6)));
                        dayList.clear();
                    }
                } 
            }
            if(count >= convertDay) {
                if (count>=1) {
                    dayList.add(Integer.toString(day));
                    day++;
                    numDays--;
                } 
            } else if(count <= convertDay) {
                dayList.add(" ");
                count++;
            }
        }

        monthTable.setItems(days);
    }    
    
}
