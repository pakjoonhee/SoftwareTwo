package AppointmentDetails;

import CustomerDetails.CustomerDetailsController;
import Models.Customer;
import Models.Days;
import static Models.Utility.getDay;
import static Models.Utility.getDayOfWeekAsInt;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

public class AppointmentDetailsController implements Initializable {
    @FXML private TableView<Days> monthTable;
    @FXML private TableColumn<Days, String> sunday;
    @FXML private TableColumn<Days, String> monday;
    @FXML private TableColumn<Days, String> tuesday;
    @FXML private TableColumn<Days, String> wednesday;
    @FXML private TableColumn<Days, String> thursday;
    @FXML private TableColumn<Days, String> friday;
    @FXML private TableColumn<Days, String> saturday;
    
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerID;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> customerAddress;
    @FXML private TableColumn<Customer, String> customerEmail;
    @FXML private TableColumn<Customer, String> customerNumber;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initMonthTable();
        initCustomerTable();
    }    
    public void initMonthTable() {
        monthTable.getSelectionModel().setCellSelectionEnabled(true);
        
        sunday.setCellValueFactory(new PropertyValueFactory<Days, String>("sunday"));
        monday.setCellValueFactory(new PropertyValueFactory<Days, String>("monday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<Days, String>("tuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<Days, String>("wednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<Days, String>("thursday"));
        friday.setCellValueFactory(new PropertyValueFactory<Days, String>("friday"));
        saturday.setCellValueFactory(new PropertyValueFactory<Days, String>("saturday"));
        
        LocalDate date = LocalDate.of(2019, 01, 01);
        int numDays = date.lengthOfMonth();
        
        String firstDay = getDay("01", "01", "2019");
        int convertDay = getDayOfWeekAsInt(firstDay);
        
        ArrayList<String> dayList = new ArrayList<>();
        ObservableList<Days> days = FXCollections.observableArrayList();

        int count = 0;
        int day = 1;

        while(numDays >= 0) {
            if(dayList.size() == 7) {
                days.add(new Days(dayList.get(0), dayList.get(1), dayList.get(2), 
                    dayList.get(3), dayList.get(4), dayList.get(5),dayList.get(6)));
                dayList.clear();
            } else if(numDays == 0) {
                while(!dayList.isEmpty()) {
                    dayList.add(" ");
                    if(dayList.size() == 7) {
                        days.add(new Days(dayList.get(0), dayList.get(1), dayList.get(2), 
                            dayList.get(3), dayList.get(4), dayList.get(5),dayList.get(6)));
                        dayList.clear();
                    }
                } 
            }
            if(count >= convertDay) {
                if (count>=1) {
                    dayList.add(Integer.toString(day));
                    day++;
                    numDays--;
                } 
            } else if(count <= convertDay) {
                dayList.add(" ");
                count++;
            }
        }

        monthTable.setItems(days);
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
            Logger.getLogger(CustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        customerTable.setItems(customers);
    }
    
    public void addCustomerDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CustomerDetails/CustomerDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
}
