package AppointmentDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
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

public class AddAppointmentDetailsController implements Initializable {
    @FXML ComboBox<String> timesComboBox;
    @FXML ComboBox<String> consultantNamesComboBox;
    ObservableList<String> times = FXCollections.observableArrayList();
    ObservableList<String> consultantNames = FXCollections.observableArrayList();
    private String selectedTime;
    private String selectedConsultant;
    @FXML TextField appointmentDate;
    @FXML TextField appointmentType;
    
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

    public void saveFunction(ActionEvent event) throws SQLException, IOException {
        Statement statement = conn.createStatement();
        String sqlStatement = ("INSERT INTO `appointments_tbl`(CustomerID, AppointmentDate, AppointmentTime, AppointmentType, CustomerName) VALUE ('"+"1"+"','"+appointmentDate.getText()+"','"+selectedTime+"','"+appointmentType.getText()+"','"+selectedConsultant+"')");
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
