package com.example.greduationproject.login_tasks.models;

public class Driver {
    private String Fullname, Email, Phone, Gender, City, CarName, Model, Age;
    private String url_img, url_img_id_card, url_img_lic_car, url_img_lic_driver, url_img_crim;
    private String ID,From,To,Date,Time,Numberofpassenger,Numberofbags,Smoking;


    public Driver() {
    }

    @Override
    public String toString() {
        return "Driver{" +
                "Fullname='" + Fullname + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Gender='" + Gender + '\'' +
                ", City='" + City + '\'' +
                ", CarName='" + CarName + '\'' +
                ", Model='" + Model + '\'' +
                ", Age='" + Age + '\'' +
                ", url_img='" + url_img + '\'' +
                ", url_img_id_card='" + url_img_id_card + '\'' +
                ", url_img_lic_car='" + url_img_lic_car + '\'' +
                ", url_img_lic_driver='" + url_img_lic_driver + '\'' +
                ", url_img_crim='" + url_img_crim + '\'' +
                ", ID='" + ID + '\'' +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", Date='" + Date + '\'' +
                ", Time='" + Time + '\'' +
                ", Numberofpassenger='" + Numberofpassenger + '\'' +
                ", Numberofbags='" + Numberofbags + '\'' +
                ", Smoking='" + Smoking + '\'' +
                '}';
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

    public String getUrl_img_id_card() {
        return url_img_id_card;
    }

    public void setUrl_img_id_card(String url_img_id_card) {


        this.url_img_id_card = url_img_id_card;
    }

    public String getUrl_img_lic_car() {
        return url_img_lic_car;
    }

    public void setUrl_img_lic_car(String url_img_lic_car) {
        this.url_img_lic_car = url_img_lic_car;
    }

    public String getUrl_img_lic_driver() {
        return url_img_lic_driver;
    }

    public void setUrl_img_lic_driver(String url_img_lic_driver) {
        this.url_img_lic_driver = url_img_lic_driver;
    }

    public String getUrl_img_crim() {
        return url_img_crim;
    }

    public void setUrl_img_crim(String url_img_crim) {
        this.url_img_crim = url_img_crim;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }







}

