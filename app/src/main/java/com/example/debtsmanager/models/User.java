package com.example.debtsmanager.models;

import androidx.annotation.NonNull;

public class User
{
    private String Email;
    private String Name;
    private boolean ismanager;


    public boolean isIsmanager() {
        return ismanager;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

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

    public User(String email, String name, boolean ismanager) {
        Email = email;
        Name = name;
        this.ismanager = ismanager;
    }

    public User() {
    }

    @NonNull
    @Override
    public String toString() {
        return this.Name;
    }
}
