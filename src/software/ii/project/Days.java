
package software.ii.project;

import javafx.beans.property.SimpleStringProperty;

public class Days {
    private SimpleStringProperty sunday;
    private SimpleStringProperty monday;
    private SimpleStringProperty tuesday;
    private SimpleStringProperty wednesday;
    private SimpleStringProperty thursday;
    private SimpleStringProperty friday;
    private SimpleStringProperty saturday;

    public Days(String sunday, String monday, String tuesday, String wednesday, String thursday, String friday, String saturday) {
        this.sunday = new SimpleStringProperty(sunday);
        this.monday = new SimpleStringProperty(monday);
        this.tuesday = new SimpleStringProperty(tuesday);
        this.wednesday = new SimpleStringProperty(wednesday);
        this.thursday = new SimpleStringProperty(thursday);
        this.friday = new SimpleStringProperty(friday);
        this.saturday = new SimpleStringProperty(saturday);
    }

    public String getSunday() {
        return sunday.get();
    }

    public void setSunday(SimpleStringProperty sunday) {
        this.sunday = sunday;
    }

    public String getMonday() {
        return monday.get();
    }

    public void setMonday(SimpleStringProperty monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday.get();
    }

    public void setTuesday(SimpleStringProperty tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday.get();
    }

    public void setWednesday(SimpleStringProperty wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday.get();
    }

    public void setThursday(SimpleStringProperty thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday.get();
    }

    public void setFriday(SimpleStringProperty friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday.get();
    }

    public void setSaturday(SimpleStringProperty saturday) {
        this.saturday = saturday;
    }
}
