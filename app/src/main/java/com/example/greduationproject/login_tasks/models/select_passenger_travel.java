package com.example.greduationproject.login_tasks.models;

    public class select_passenger_travel {
    String ID,From,To,Date,Time,Numberofpassenger,Numberofbags,Smoking;

    public select_passenger_travel(String ID, String from, String to, String date, String time, String numberofpassenger, String numberofbags, String smoking) {
        this.ID = ID;
        From = from;
        To = to;
        Date = date;
        Time = time;
        Numberofpassenger = numberofpassenger;
        Numberofbags = numberofbags;
        Smoking = smoking;
    }

    public select_passenger_travel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNumberofpassenger() {
        return Numberofpassenger;
    }

    public void setNumberofpassenger(String numberofpassenger) {
        Numberofpassenger = numberofpassenger;
    }

    public String getNumberofbags() {
        return Numberofbags;
    }

    public void setNumberofbags(String numberofbags) {
        Numberofbags = numberofbags;
    }

    public String getSmoking() {
        return Smoking;
    }

    public void setSmoking(String smoking) {
        Smoking = smoking;
    }
}
