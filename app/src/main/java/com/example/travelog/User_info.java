package com.example.travelog;

public class User_info {
    String user;
    String password;
    String id;

    public User_info(String id,String user, String password) {
        this.user = user;
        this.password = password;
        this.id=id;
    }

    public String getUser() {
        return user;
    }

    public String getId() { return id; }

    public String getPassword() {
        return password;
    }

    public void setId(String id) { this.id = id; }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
