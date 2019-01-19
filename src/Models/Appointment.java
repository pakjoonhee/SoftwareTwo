package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private SimpleIntegerProperty customerID;
    private SimpleStringProperty appointmentDate;
    private SimpleStringProperty appointmentName;
    private SimpleStringProperty appointmentType;
    private SimpleStringProperty customerName;

    public Appointment(Integer customerID, String appointmentDate, String appointmentName, String appointmentType, String customerName) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.appointmentDate = new SimpleStringProperty(appointmentDate);
        this.appointmentName = new SimpleStringProperty(appointmentName);
        this.appointmentType = new SimpleStringProperty(appointmentType);
        this.customerName = new SimpleStringProperty(customerName);
    }

    public Integer getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(SimpleIntegerProperty customerID) {
        this.customerID = customerID;
    }

    public String getAppointmentDate() {
        return appointmentDate.get();
    }

    public void setAppointmentDate(SimpleStringProperty appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentName() {
        return appointmentName.get();
    }

    public void setAppointmentName(SimpleStringProperty appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public void setAppointmentType(SimpleStringProperty appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(SimpleStringProperty customerName) {
        this.customerName = customerName;
    }
}
