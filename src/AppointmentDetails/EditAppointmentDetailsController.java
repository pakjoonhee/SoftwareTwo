package AppointmentDetails;

import Models.Appointment;
import Models.Customer;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

public class EditAppointmentDetailsController implements Initializable {
    @FXML ComboBox<String> timesComboBox;
    @FXML ComboBox<String> consultantNamesComboBox;
    ObservableList<String> times = FXCollections.observableArrayList();
    ObservableList<String> consultantNames = FXCollections.observableArrayList();
    private String selectedTime;
    private String selectedConsultant;
    @FXML TextField appointmentDate;
    @FXML TextField appointmentType;
    private Appointment appointment;
    
    @FXML
    void handleTimeBox(ActionEvent event) {
        selectedTime = timesComboBox.getValue();
    }
    
    @FXML
    void handleConsultantBox(ActionEvent event) {
        selectedConsultant = consultantNamesComboBox.getValue();
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
//            myCalendar.set(Calendar.DATE, 9);
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
    
    public void initAppointmentData(Appointment appointment, Integer rowNumber) {
        this.appointment = appointment;
        timesComboBox.getSelectionModel().select(appointment.getAppointmentTime());
        selectedTime = appointment.getAppointmentTime();
        
        appointmentDate.setText(appointment.getAppointmentDate());
        appointmentType.setText(appointment.getAppointmentType());
        
        consultantNamesComboBox.getSelectionModel().select(appointment.getConsultantName());
        selectedConsultant = appointment.getConsultantName();
     } 
    
    public void changeScreenGoBack(ActionEvent event) throws IOException 
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void saveEditedAppointmentData(ActionEvent event) throws SQLException, IOException {
        try
        {
            PreparedStatement ps = conn.prepareStatement(
              "UPDATE appointments_tbl SET AppointmentDate = ?, AppointmentTime = ?, AppointmentType = ?, CustomerName = ? WHERE KeyID = ?");

            ps.setString(1,appointmentDate.getText());
            ps.setString(2,selectedTime);
            ps.setString(3,appointmentType.getText());
            ps.setString(4,selectedConsultant);
            ps.setInt(5,appointment.getKeyID());

            ps.executeUpdate();
            ps.close();

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        catch (SQLException se)
        {
          throw se;
        }
    }
    
}
