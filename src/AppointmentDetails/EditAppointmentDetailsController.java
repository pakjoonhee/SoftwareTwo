package AppointmentDetails;

import Models.Appointment;
import Models.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
        times.addAll("9:00AM","10:00AM","11:00AM","12:00PM","1:00PM","2:00PM","3:00PM","4:00PM","5:00PM");
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
              "UPDATE appointments_tbl SET AppointmentDate = ?, AppointmentTime = ?, AppointmentType = ?, CustomerName = ? WHERE AppointmentDate = ? AND AppointmentTime = ? AND AppointmentType = ? AND CustomerName = ?");

            ps.setString(1,appointmentDate.getText());
            ps.setString(2,selectedTime);
            ps.setString(3,appointmentType.getText());
            ps.setString(4,selectedConsultant);
            ps.setString(5,appointmentDate.getText());
            ps.setString(6,selectedTime);
            ps.setString(7,appointmentType.getText());
            ps.setString(8,selectedConsultant);

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
