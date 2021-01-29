package com.example.greduationproject.login_tasks.models;

public class Request {
   private String name_users,phone_users,image_users;
   Boolean is_accept;

    public Request(String name_users, String phone_users, String image_users, Boolean is_accept) {
        this.name_users = name_users;
        this.phone_users = phone_users;
        this.image_users = image_users;
        this.is_accept = is_accept;
    }

    public Request() {
    }

    public String getName_users() {
        return name_users;
    }

    public void setName_users(String name_users) {
        this.name_users = name_users;
    }

    public String getPhone_users() {
        return phone_users;
    }

    public void setPhone_users(String phone_users) {
        this.phone_users = phone_users;
    }

    public String getImage_users() {
        return image_users;
    }

    public void setImage_users(String image_users) {
        this.image_users = image_users;
    }

    public Boolean getIs_accept() {
        return is_accept;
    }

    public void setIs_accept(Boolean is_accept) {
        this.is_accept = is_accept;
    }
}
