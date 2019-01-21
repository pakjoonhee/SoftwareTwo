package MainScreen;

import AppointmentDetails.AddAppointmentDetailsController;
import CustomerDetails.AddCustomerDetailsController;
import CustomerDetails.EditCustomerDetailsController;
import AppointmentDetails.EditAppointmentDetailsController;
import Models.Appointment;
import Models.Customer;
import Models.Days;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

public class MainScreenController implements Initializable {
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerID;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> customerAddress;
    @FXML private TableColumn<Customer, String> customerEmail;
    @FXML private TableColumn<Customer, String> customerNumber;
    
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentKeyID;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerID;
    @FXML private TableColumn<Appointment, String> appointmentDate;
    @FXML private TableColumn<Appointment, String> appointmentTime;
    @FXML private TableColumn<Appointment, String> appointmentType;
    @FXML private TableColumn<Appointment, String> consultantName;
    private Integer customerSelectedID;
    private Integer appointmentSelectedID;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCustomerTable();
    }    
    
    public void ifRowClicked() {
        Customer selectedRow = customerTable.getSelectionModel().getSelectedItem();
        customerSelectedID = selectedRow.getCustomerID();
        
        System.out.print(customerSelectedID);
        initAppointmentTable(customerSelectedID);
    }
    
    public void ifAppointmentRowClicked() {
        Appointment selectedRow = appointmentTable.getSelectionModel().getSelectedItem();
        appointmentSelectedID = selectedRow.getKeyID();
        
        System.out.print(appointmentSelectedID);
    }
    
    public void initCustomerTable() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        
        customerID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerEmail"));
        customerNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerNumber"));
        
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT * FROM customer_tbl";
            ResultSet result = statement.executeQuery(sqlStatement);
            
            while(result.next()) 
            {
                customers.add(new Customer(result.getInt("CustomerID"), result.getString("CustomerName"), 
                              result.getString("Address"), result.getString("Email"), result.getString("Number")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerTable.setItems(customers);
    }
    
    public void initAppointmentTable(Integer selectedID) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        appointmentKeyID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("keyID"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDate"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentTime"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentType"));
        consultantName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("consultantName"));
        
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT * FROM appointments_tbl WHERE CustomerID=" + selectedID;
            ResultSet result = statement.executeQuery(sqlStatement);
            
            while(result.next()) 
            {
                //double check this please
                appointments.add(new Appointment(result.getInt("KeyID"), result.getInt("CustomerID"), result.getString("AppointmentDate"), 
                              result.getString("AppointmentTime"), result.getString("AppointmentType"), result.getString("CustomerName")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        appointmentTable.setItems(appointments);
    }
    
    public void addCustomerDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CustomerDetails/AddCustomerDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
    public void changeScreenModifyCustomer(ActionEvent event) throws IOException 
    {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/CustomerDetails/EditCustomerDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            EditCustomerDetailsController controller = loader.getController();
            controller.initCustomerData(customerTable.getSelectionModel().getSelectedItem(), 
                    customerTable.getSelectionModel().selectedIndexProperty().get());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }
    
    public void deleteCustomerDetails() throws SQLException {
        Statement statement = conn.createStatement();
        String sqlStatement = ("DELETE FROM customer_tbl WHERE CustomerID = " + customerSelectedID + ";");
        statement.executeUpdate(sqlStatement);
        initCustomerTable();
    }
    
    public void deleteAppointmentDetails() throws SQLException {
        Statement statement = conn.createStatement();
        String sqlStatement = ("DELETE FROM appointments_tbl WHERE KeyID = " + appointmentSelectedID + ";");
        statement.executeUpdate(sqlStatement);
        initAppointmentTable(customerSelectedID);
    }
    
    public void addAppointmentDetails(ActionEvent event) throws IOException {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AppointmentDetails/AddAppointmentDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);
            AddAppointmentDetailsController controller = loader.getController();
            controller.initCustomerID(customerSelectedID);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Click on a Customer in the customer table before adding an appointment", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    public void changeScreenModifyAppointment(ActionEvent event) throws IOException 
    {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AppointmentDetails/EditAppointmentDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            EditAppointmentDetailsController controller = loader.getController();
            controller.initAppointmentData(appointmentTable.getSelectionModel().getSelectedItem(), 
                    appointmentTable.getSelectionModel().selectedIndexProperty().get());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }
}
