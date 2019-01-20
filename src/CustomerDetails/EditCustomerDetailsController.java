package CustomerDetails;

import Models.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
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

public class EditCustomerDetailsController implements Initializable {
    @FXML private TextField customerName; 
    @FXML private TextField customerAddress; 
    @FXML private TextField customerEmail; 
    @FXML private TextField customerNumber; 
    private Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    public void initCustomerData(Customer customer, Integer rowNumber) {
        this.customer = customer;
        customerName.setText(customer.getCustomerName());
        customerAddress.setText(customer.getCustomerAddress());
        customerEmail.setText(customer.getCustomerEmail());
        customerNumber.setText(customer.getCustomerNumber());
     } 
    
    public void changeScreenGoBack(ActionEvent event) throws IOException 
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AppointmentDetails/AppointmentDetails.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void saveEditedData(ActionEvent event) throws SQLException, IOException {
        try
        {
            PreparedStatement ps = conn.prepareStatement(
              "UPDATE customer_tbl SET CustomerName = ?, Address = ?, Email = ?, Number = ? WHERE CustomerID = ?");

            ps.setString(1,customerName.getText());
            ps.setString(2,customerAddress.getText());
            ps.setString(3,customerEmail.getText());
            ps.setString(4,customerNumber.getText());
            ps.setInt(5,customer.getCustomerID());

            ps.executeUpdate();
            ps.close();

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AppointmentDetails/AppointmentDetails.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
        catch (SQLException se)
        {
          throw se;
        }
        
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AppointmentDetails/AppointmentDetails.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
}
