package com.example.trabwsmutantes.Model;

public class User {
    private int id;
    private String email;
    private  String password;

    public User(int id, String email, String password) {
        id = id;
        email = email;
        password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }
}
