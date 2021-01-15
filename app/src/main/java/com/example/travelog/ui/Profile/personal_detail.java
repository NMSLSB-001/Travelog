package com.example.travelog.ui.Profile;

public class personal_detail {
    String username;//唯一的ID
    String name;//用户的姓名
    String address;
    String email;
    String phone;
    String gender;

    public personal_detail(String username, String name, String address, String email, String phone, String gender) {
        this.username = username;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

