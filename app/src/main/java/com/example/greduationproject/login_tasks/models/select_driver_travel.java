package com.example.greduationproject.login_tasks.models;

public class select_driver_travel {
    String ID,From,To,Date,Time,Numberofpassenger,Numberofbags,Smoking,Pricetravel;



    public select_driver_travel(String ID, String from, String to, String date, String time, String numberofpassenger, String numberofbags, String smoking, String pricetravel) {
        this.ID = ID;
        From = from;
        To = to;
        Date = date;
        Time = time;
        Numberofpassenger = numberofpassenger;
        Numberofbags = numberofbags;
        Smoking = smoking;
        Pricetravel=pricetravel;
    }
    public  select_driver_travel(){

    }

    public String getPricetravel() {
        return Pricetravel;
    }

    public void setPricetravel(String pricetravel) {
        Pricetravel = pricetravel;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void setTo(String to) {
        To = to;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setNumberofpassenger(String numberofpassenger) {
        Numberofpassenger = numberofpassenger;
    }

    public void setNumberofbags(String numberofbags) {
        Numberofbags = numberofbags;
    }

    public void setSmoking(String smoking) {
        Smoking = smoking;
    }

    public String getID() {
        return ID;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getNumberofpassenger() {
        return Numberofpassenger;
    }

    public String getNumberofbags() {
        return Numberofbags;
    }

    public String getSmoking() {
        return Smoking;
    }
}
