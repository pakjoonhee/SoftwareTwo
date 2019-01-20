package software.ii.project;

import CustomerDetails.AddCustomerDetailsController;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static software.ii.project.DBConnection.conn;

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainScreen/MainScreen.fxml"));
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
