
package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty customerID;
    private SimpleStringProperty customerName;
    private SimpleStringProperty customerAddress;
    private SimpleStringProperty customerEmail;
    private SimpleStringProperty customerNumber;

    public Customer(Integer customerID, String customerName, String customerAddress, String customerEmail, String customerNumber) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.customerAddress = new SimpleStringProperty(customerAddress);
        this.customerEmail = new SimpleStringProperty(customerEmail);
        this.customerNumber = new SimpleStringProperty(customerNumber);
    }
    
    public Integer getCustomerID() {
        return customerID.get();
    }

    public void setCustomerID(SimpleIntegerProperty customerID) {
        this.customerID = customerID;
    }
    
    public String getCustomerName() {
        return customerName.get();
    }

    public void setCustomerName(SimpleStringProperty customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public void setCustomerAddress(SimpleStringProperty customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerEmail() {
        return customerEmail.get();
    }

    public void setCustomerEmail(SimpleStringProperty customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerNumber() {
        return customerNumber.get();
    }

    public void setCustomerNumber(SimpleStringProperty customerNumber) {
        this.customerNumber = customerNumber;
    }
}
