package software.ii.project;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

public class FXMLDocumentController implements Initializable {
    @FXML private TextField userNameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    private String db = "U05xD3";
    private String url = "jdbc:mysql://52.206.157.109/" + db;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    public void logInAction(ActionEvent event) throws IOException, SQLException, Exception, ClassNotFoundException 
    {
        if(DBConnection.makeConnection(url, userNameField.getText(), passwordField.getText())) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AppointmentDetails/AppointmentDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The username and password did not match!!", ButtonType.OK);
            alert.showAndWait();
        }
        
    }
}
