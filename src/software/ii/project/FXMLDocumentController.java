package software.ii.project;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static software.ii.project.Utility.getDay;
import static software.ii.project.Utility.getDayOfWeekAsInt;

public class FXMLDocumentController implements Initializable {
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
        sunday.setCellValueFactory(new PropertyValueFactory<Days, String>("sunday"));
        monday.setCellValueFactory(new PropertyValueFactory<Days, String>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<Days, String>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<Days, String>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<Days, String>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<Days, String>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<Days, String>("saturday"));
        
        LocalDate date = LocalDate.of(2019, 6, 20);
        int numDays = date.lengthOfMonth();
        
        String firstDay = getDay("01", "01", "2019");
        int convertDay = getDayOfWeekAsInt(firstDay);
        
        ArrayList<String> dayList = new ArrayList<>();
        
        int count = 1;
        for(int i=0; i<7; i++) {
            if(i >= convertDay) {
                if(count > 1) {
                    dayList.add(Integer.toString(count + 1));
                    count++;
                } else if (count==1 || i == convertDay) {
                    dayList.add(Integer.toString(count));
                    count++;
                }
                
            } else {
                dayList.add(" ");
            }
        }
//        while(numDays > 0) {
//            for(int i=0; i<=6; i++) {
//                
//                numDays -= 1;
//            }
//        }
        
        ObservableList<Days> days = FXCollections.observableArrayList();
        
        days.add(new Days(dayList.get(0), dayList.get(0), dayList.get(2), 
                dayList.get(3), dayList.get(4), dayList.get(5),dayList.get(3)));
        
        monthTable.setItems(days);
        
        
    }
}
