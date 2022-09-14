package com.example.trabwsmutantes.Model;

public class User {
    private int Id;
    private String Email;
    private  String Password;

    public User(int id, String email, String password) {
        Id = id;
        Email = email;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
