package com.example.travelog;

public class User1 {
    private static String name ="";
    private static String real_name ="";
    private static String address ="";
    private static String email ="";
    private static String phone ="";
    private static String gender ="";


    public static String getName() {
        return name;
    }
    public static void setName(String name) {
        com.example.travelog.User1.name = name;
    }

    public static String getReal_Name() {
        return real_name;
    }
    public static void setReal_Name(String realname) {
        com.example.travelog.User1.real_name = realname;
    }

    public static String getaddress() {
        return address;
    }
    public static void setaddress(String address) { com.example.travelog.User1.address = address; }

    public static String getemail() {
        return email;
    }
    public static void setemail(String email) { com.example.travelog.User1.email = email; }

    public static String getphone() {
        return phone;
    }
    public static void setphone(String phone) { com.example.travelog.User1.phone = phone; }

    public static String getgender() {
        return gender;
    }
    public static void setgender(String gender) { com.example.travelog.User1.gender = gender; }
}