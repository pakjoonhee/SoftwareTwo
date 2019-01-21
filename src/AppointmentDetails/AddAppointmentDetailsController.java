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
    ObservableList<String> times = FXCollections.observableArrayList();
    ObservableList<String> consultantNames = FXCollections.observableArrayList();
    private String selectedTime;
    private String selectedConsultant;
    @FXML TextField appointmentDate;
    @FXML TextField appointmentType;
    private Integer customerID;
    
    @FXML
    void handleTimeBox(ActionEvent event) {
        selectedTime = timesComboBox.getValue();
    }
    
    @FXML
    void handleConsultantBox(ActionEvent event) {
        selectedConsultant = consultantNamesComboBox.getValue();
    }
    
    public void initCustomerID(Integer customerID) {
        this.customerID = customerID;
     } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        TimeZone myTimeZone = TimeZone.getDefault();
        TimeZone myTimeZone = TimeZone.getTimeZone("GB");
        
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

        timesComboBox.setItems(times);
        
        consultantNames.addAll("Consultant 1", "Consultant 2", "Consultant 3");
        consultantNamesComboBox.setItems(consultantNames);
    }

    public void saveFunction(ActionEvent event) throws SQLException, IOException {
        Statement statement = conn.createStatement();
        String sqlStatement = ("INSERT INTO `appointments_tbl`(CustomerID, AppointmentDate, AppointmentTime, AppointmentType, CustomerName) VALUE ('"+customerID+"','"+appointmentDate.getText()+"','"+selectedTime+"','"+appointmentType.getText()+"','"+selectedConsultant+"')");
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
