
package CustomerDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;


public class CustomerDetailsController implements Initializable {
    @FXML TextField customerName;
    @FXML TextField customerAddress;
    @FXML TextField customerEmail;
    @FXML TextField customerNumber;
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void changeScreenGoBack(ActionEvent event) throws IOException 
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void saveFunction(ActionEvent event) throws SQLException, IOException {
        Statement statement = conn.createStatement();
        String sqlStatement = ("INSERT INTO `appointments_tbl`(CustomerID, AppointmentDate, AppointmentTime, AppointmentType, CustomerName) VALUE ('"+"1"+"','"+customerName.getText()+"','"+customerAddress.getText()+"','"+customerEmail.getText()+"','"+customerNumber.getText()+"')");
        statement.executeUpdate(sqlStatement);
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/MainScreen/MainScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
