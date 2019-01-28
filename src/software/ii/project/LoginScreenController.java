package software.ii.project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreenController implements Initializable {
    @FXML private TextField userNameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    private String db = "U05xD3";
    private String url = "jdbc:mysql://52.206.157.109/" + db;
    private String user = "U05xD3";
    private String pass = "53688636419";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    public void logInAction(ActionEvent event) throws IOException, SQLException, Exception, ClassNotFoundException 
    {
        // if(DBConnection.makeConnection(url, userNameField.getText(), passwordField.getText())) {
        if(DBConnection.makeConnection(url, "U05xD3", "53688636419")) {
            Calendar myCalendar = Calendar.getInstance();
            String timeStamp = Integer.toString(myCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(myCalendar.get(Calendar.MINUTE));
            String currentDay = Integer.toString(myCalendar.get(Calendar.MONTH) + 1) + "-" + Integer.toString(myCalendar.get(Calendar.DATE)) + "-" + Integer.toString(myCalendar.get(Calendar.YEAR));
            
            String fileName = "loginHistory.txt", item;
            FileWriter fwriter = new FileWriter(fileName, true);
            PrintWriter outputFile = new PrintWriter(fwriter);
            outputFile.println("DATE: " + currentDay + " TIME: " + timeStamp);
            outputFile.close();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainScreen/MainScreen.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else {
            Locale myLocale = Locale.getDefault();
            if(myLocale.getDisplayLanguage() == "French") {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Le nom d'utilisateur et le mot de passe ne correspondent pas !!", ButtonType.OK);
                alert.showAndWait();
            } else if(myLocale.getDisplayLanguage() == "Spanish") {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El nombre de usuario y la contraseña no coinciden !!", ButtonType.OK);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The username and password did not match!!", ButtonType.OK);
                alert.showAndWait();
            }
        }
        
    }
}
