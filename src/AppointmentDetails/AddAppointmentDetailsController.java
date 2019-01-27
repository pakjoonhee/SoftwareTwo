package AppointmentDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

public class AddAppointmentDetailsController implements Initializable {
    @FXML ComboBox<String> timesComboBox;
    @FXML ComboBox<String> consultantNamesComboBox;
    @FXML ComboBox<String> months;
    @FXML ComboBox<String> days;
    @FXML ComboBox<String> years;
    @FXML ComboBox<String> appType;
    
    ObservableList<String> times = FXCollections.observableArrayList();
    ObservableList<String> consultantNames = FXCollections.observableArrayList();
    ObservableList<String> monthList = FXCollections.observableArrayList();
    ObservableList<String> dayList = FXCollections.observableArrayList();
    ObservableList<String> yearList = FXCollections.observableArrayList();
    ObservableList<String> appTypeList = FXCollections.observableArrayList();
    
    private String selectedTime;
    private String selectedConsultant;
    private String selectedMonth;
    private String selectedDay;
    private String selectedYear;
    @FXML TextField appointmentDate;
    @FXML TextField appointmentType;
    private Integer customerID;
    private String selectedType;
    
    @FXML
    void handleTimeBox(ActionEvent event) {
        selectedTime = timesComboBox.getValue();
    }
    @FXML
    void handleConsultantBox(ActionEvent event) {
        selectedConsultant = consultantNamesComboBox.getValue();
    }
    @FXML
    void handleMonthBox(ActionEvent event) {
        selectedMonth = months.getValue();
    }
    @FXML
    void handleDayBox(ActionEvent event) {
        selectedDay = days.getValue();
    }
    @FXML
    void handleYearBox(ActionEvent event) {
        selectedYear = years.getValue();
    }
    @FXML
    void handleAppType(ActionEvent event) {
        selectedType = appType.getValue();
    }
    
    public void initCustomerID(Integer customerID) {
        this.customerID = customerID;
     } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TimeZone myTimeZone = TimeZone.getDefault();
//        TimeZone myTimeZone = TimeZone.getTimeZone("GB");
        
        for(int i = 9; i <= 17; i++) {
            Integer hr = i;
            TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
            Calendar myCalendar = Calendar.getInstance();
            myCalendar.set(Calendar.HOUR_OF_DAY, hr);
            myCalendar.set(Calendar.MINUTE, 0);
            System.out.println("US: " + myCalendar.getTime());
            TimeZone.setDefault(myTimeZone);
            System.out.println("Local Time: " + myCalendar.getTime());
            
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");  
            String strDate = dateFormat.format(myCalendar.getTime());
            System.out.println(strDate);
            times.add(strDate);
        }

        
        consultantNames.addAll("Consultant1", "Consultant2", "Consultant3");
        monthList.addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        dayList.addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", 
                       "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        yearList.addAll("2019");
        appTypeList.addAll("Counseling", "Mentoring", "Tutoring");
        
        timesComboBox.setItems(times);
        consultantNamesComboBox.setItems(consultantNames);
        months.setItems(monthList);
        days.setItems(dayList);
        years.setItems(yearList);
        appType.setItems(appTypeList);
    }

    public void saveFunction(ActionEvent event) throws SQLException, IOException {
        String date = selectedMonth + "-" + selectedDay + "-" + selectedYear;
        Statement statement = conn.createStatement();
        String sqlStatement = ("INSERT INTO `appointments_tbl`(CustomerID, AppointmentDate, AppointmentTime, AppointmentType, CustomerName) VALUE ('"+customerID+"','"+date+"','"+selectedTime+"','"+selectedType+"','"+selectedConsultant+"')");
        statement.executeUpdate(sqlStatement);
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }    
    
    public void changeScreenGoBack(ActionEvent event) throws IOException 
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
