package com.example.debtsmanager.models;

public class User {
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Email;
    private String Name;

    public User(String email, String name) {
        Email = email;
        Name = name;
    }

    public User() {
    }
}
