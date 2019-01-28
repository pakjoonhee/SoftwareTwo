package MainScreen;

import AppointmentDetails.AddAppointmentDetailsController;
import CustomerDetails.AddCustomerDetailsController;
import CustomerDetails.EditCustomerDetailsController;
import AppointmentDetails.EditAppointmentDetailsController;
import Models.Appointment;
import Models.Customer;
import Models.Days;
import Models.NumberAppTypes;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static software.ii.project.DBConnection.conn;

public class MainScreenController implements Initializable {

    @FXML
    private ComboBox<String> comboReports;
    
    @FXML
    private ComboBox<String> weekMonthCalendar;
    
    ObservableList<String> reportTypes = FXCollections.observableArrayList();
    ObservableList<String> weekMonthList = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerID;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> customerAddress;
    @FXML
    private TableColumn<Customer, String> customerEmail;
    @FXML
    private TableColumn<Customer, String> customerNumber;

    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentKeyID;
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerID;
    @FXML
    private TableColumn<Appointment, String> appointmentDate;
    @FXML
    private TableColumn<Appointment, String> appointmentTime;
    @FXML
    private TableColumn<Appointment, String> appointmentType;
    @FXML
    private TableColumn<Appointment, String> consultantName;
    private Integer customerSelectedID;
    private Integer appointmentSelectedID;
    private String selectedReport;
    private String selectedMonthWeek;
    private ArrayList<NumberAppTypes> SQLresult;

    @FXML
    void handleReportType(ActionEvent event) throws IOException, SQLException {
        selectedReport = comboReports.getValue();
        if (selectedReport.equals("Num of Appointment Types")) {
            String fileName = "numAppTypes.txt", item;
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int i = 0; i < SQLresult.size(); i++) {
                outputFile.println("Month " + SQLresult.get(i).getMonth() + "-" + "Counseling:"
                        + SQLresult.get(i).getCounselingCount() + " " + "Tutoring:"
                        + SQLresult.get(i).getTutoringCount() + " " + "Mentoring:"
                        + SQLresult.get(i).getMentoringCount() + " ");
            }
            outputFile.close();
        } else if (selectedReport.equals("Consultant Schedules")) {
            File f = new File("consultantReport.txt");
            if (f.exists()) {
                f.delete();
            }
            consultantReport();
        } else if (selectedReport.equals("Customers attending WGU")) {
            wguStudentsReport();
        }
    }
    
    @FXML 
    void handleMonthWeek(ActionEvent even) throws SQLException, ParseException {
        selectedMonthWeek = weekMonthCalendar.getValue();
        if (selectedMonthWeek.equals("View By Month")) {
            viewAppointmentsMonth();
        } else if (selectedMonthWeek.equals("View By Week")) {
            viewAppointmentsWeek();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fifteenMinutes();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        reportTypes.addAll("Num of Appointment Types", "Consultant Schedules", "Customers attending WGU");
        comboReports.setItems(reportTypes);
        weekMonthList.addAll("View By Month", "View By Week");
        weekMonthCalendar.setItems(weekMonthList);
        
        initCustomerTable();
        numTypeReport();
    }

    public void fifteenMinutes() throws SQLException, ParseException {
        Calendar myCalendar = Calendar.getInstance();
        String timeStamp = Integer.toString(myCalendar.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(myCalendar.get(Calendar.MINUTE));
        String currentDay = Integer.toString(myCalendar.get(Calendar.MONTH) + 1) + "-" + Integer.toString(myCalendar.get(Calendar.DATE)) + "-" + Integer.toString(myCalendar.get(Calendar.YEAR));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        Statement statement = conn.createStatement();
        String sqlStatement = "SELECT * FROM appointments_tbl";
        ResultSet result = statement.executeQuery(sqlStatement);

        while (result.next()) {
            Date date1 = sdf.parse(currentDay);
            Date date2 = sdf.parse(result.getString("AppointmentDate"));

            if (date1.compareTo(date2) == 0) {
                System.out.println("Date1 is equal to Date2");

                String appointmentTime = militaryTime(result.getString("AppointmentTime"));
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date d1 = format.parse(timeStamp);
                Date d2 = format.parse(appointmentTime);

                long diff = d2.getTime() - d1.getTime();
                long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                if (minutes > 0 && minutes < 15) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There is an appointment in 15 minutes", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        }
    }

    public String militaryTime(String time) throws ParseException {
        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
        Date date = parseFormat.parse(time);
        System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
        return displayFormat.format(date);
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

            while (result.next()) {
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

            while (result.next()) {
                appointments.add(new Appointment(result.getInt("KeyID"), result.getInt("CustomerID"), result.getString("AppointmentDate"),
                        result.getString("AppointmentTime"), result.getString("AppointmentType"), result.getString("CustomerName")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        appointmentTable.setItems(appointments);
    }
    
    public void viewAppointmentsMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        appointmentKeyID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("keyID"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDate"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentTime"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentType"));
        consultantName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("consultantName"));
        
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM appointments_tbl");
        
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            String month = result.getString("AppointmentDate").substring(0, 2);
            if (month.equals("01")) {
                appointments.add(new Appointment(result.getInt("KeyID"), result.getInt("CustomerID"), result.getString("AppointmentDate"),
                        result.getString("AppointmentTime"), result.getString("AppointmentType"), result.getString("CustomerName")));
            }
        }
        ps.close();
        
        appointmentTable.setItems(appointments);
    }
    
    public void viewAppointmentsWeek() throws ParseException, SQLException {
        ArrayList<Date> dateArrayList = new ArrayList<>();
        Calendar myCalendar = Calendar.getInstance();
        String currentDay = Integer.toString(myCalendar.get(Calendar.MONTH) + 1) + "-" + Integer.toString(myCalendar.get(Calendar.DATE)) + "-" + Integer.toString(myCalendar.get(Calendar.YEAR));
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date date1 = sdf.parse(currentDay);
        dateArrayList.add(date1);
        
        for(int i = 1; i <= 7; i++) {
            myCalendar.add(Calendar.DAY_OF_YEAR, 1);
            String nextDay = Integer.toString(myCalendar.get(Calendar.MONTH) + 1) + "-" + Integer.toString(myCalendar.get(Calendar.DATE)) + "-" + Integer.toString(myCalendar.get(Calendar.YEAR));
            Date tempDate = sdf.parse(nextDay);
            dateArrayList.add(tempDate);
            
        }
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointmentKeyID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("keyID"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentDate.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentDate"));
        appointmentTime.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentTime"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appointmentType"));
        consultantName.setCellValueFactory(new PropertyValueFactory<Appointment, String>("consultantName"));
        
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM appointments_tbl");
        
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            String appointmentDate = result.getString("AppointmentDate");
            Date date2 = sdf.parse(appointmentDate);
            
            for(int i=0; i < dateArrayList.size(); i++) {
                Date blah = dateArrayList.get(i);
                if (dateArrayList.get(i).compareTo(date2) == 0) {
                    appointments.add(new Appointment(result.getInt("KeyID"), result.getInt("CustomerID"), result.getString("AppointmentDate"),
                            result.getString("AppointmentTime"), result.getString("AppointmentType"), result.getString("CustomerName")));
                    break;
                }
            }
        }
        ps.close();
        
        appointmentTable.setItems(appointments);
    }

    public void numTypeReport() {
        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "SELECT * FROM appointments_tbl";
            ResultSet result = statement.executeQuery(sqlStatement);

            ArrayList<String> monthList = new ArrayList<>();
            SQLresult = new ArrayList<>();

            while (result.next()) {
                String month = result.getString("AppointmentDate").substring(0, 2);
                String appType = result.getString("AppointmentType");
                if (!monthList.contains(month)) {
                    monthList.add(month);
                    if (appType.equals("Counseling")) {
                        SQLresult.add(new NumberAppTypes(month, 1, 0, 0));
                    } else if (appType.equals("Tutoring")) {
                        SQLresult.add(new NumberAppTypes(month, 0, 1, 0));
                    } else if (appType.equals("Mentoring")) {
                        SQLresult.add(new NumberAppTypes(month, 0, 0, 1));
                    }
                } else {
                    for (int i = 0; i < SQLresult.size(); i++) {
                        if (SQLresult.get(i).getMonth().equals(month)) {
                            if (appType.equals("Counseling")) {
                                Integer count = SQLresult.get(i).getCounselingCount() + 1;
                                SQLresult.get(i).setCounselingCount(count);
                            } else if (appType.equals("Tutoring")) {
                                Integer count = SQLresult.get(i).getTutoringCount() + 1;
                                SQLresult.get(i).setTutoringCount(count);
                            } else if (appType.equals("Mentoring")) {
                                Integer count = SQLresult.get(i).getMentoringCount() + 1;
                                SQLresult.get(i).setMentoringCount(count);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomerDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultantReport() throws IOException, SQLException {
        String fileName = "consultantReport.txt", item;
        FileWriter fwriter = new FileWriter(fileName, true);
        PrintWriter outputFile = new PrintWriter(fwriter);

        for (int i = 1; i <= 3; i++) {
            String string = "Consultant" + i;
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM appointments_tbl WHERE CustomerName = ?");
            ps.setString(1, string);
            ResultSet result = ps.executeQuery();
            outputFile.println("Consultant" + i);

            while (result.next()) {
                outputFile.println("DATE: " + result.getString("AppointmentDate") + " TIME: " + result.getString("AppointmentTime"));
            }
            ps.close();
        }
        outputFile.close();
    }

    public void wguStudentsReport() throws IOException, SQLException {
        String fileName = "wguStudentsReport.txt", item;
        PrintWriter outputFile = new PrintWriter(fileName);

        String string = "Consultant";
        PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM customer_tbl");
        ResultSet result = ps.executeQuery();
        outputFile.println("Customers Attending WGU");

        while (result.next()) {
            if (result.getString("Email").contains("@wgu.edu")) {
                outputFile.println("Name: " + result.getString("CustomerName") + " Email: " + result.getString("Email") + " Address: " + result.getString("Address"));
            }
        }
        ps.close();
        outputFile.close();
    }

    public void addCustomerDetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CustomerDetails/AddCustomerDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void changeScreenModifyCustomer(ActionEvent event) throws IOException {
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/CustomerDetails/EditCustomerDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            EditCustomerDetailsController controller = loader.getController();
            controller.initCustomerData(customerTable.getSelectionModel().getSelectedItem(),
                    customerTable.getSelectionModel().selectedIndexProperty().get());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
        if (customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AppointmentDetails/AddAppointmentDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);
            AddAppointmentDetailsController controller = loader.getController();
            controller.initCustomerID(customerSelectedID);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Click on a Customer in the customer table before adding an appointment", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void changeScreenModifyAppointment(ActionEvent event) throws IOException {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/AppointmentDetails/EditAppointmentDetails.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            EditAppointmentDetailsController controller = loader.getController();
            controller.initAppointmentData(appointmentTable.getSelectionModel().getSelectedItem(),
                    appointmentTable.getSelectionModel().selectedIndexProperty().get());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }
}
