package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Appointment {
    private SimpleIntegerProperty customerID;
    private SimpleStringProperty appointmentDate;
    private SimpleStringProperty appointmentTime;
    private SimpleStringProperty appointmentType;
    private SimpleStringProperty consultantName;

    public Appointment(Integer customerID, String appointmentDate, String appointmentTime, String appointmentType, String consultantName) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.appointmentDate = new SimpleStringProperty(appointmentDate);
        this.appointmentTime = new SimpleStringProperty(appointmentTime);
        this.appointmentType = new SimpleStringProperty(appointmentType);
        this.consultantName = new SimpleStringProperty(consultantName);
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

    public String getAppointmentTime() {
        return appointmentTime.get();
    }

    public void setAppointmentTime(SimpleStringProperty appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentType() {
        return appointmentType.get();
    }

    public void setAppointmentType(SimpleStringProperty appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getConsultantName() {
        return consultantName.get();
    }

    public void setConsultantName(SimpleStringProperty consultantName) {
        this.consultantName = consultantName;
    }
}
