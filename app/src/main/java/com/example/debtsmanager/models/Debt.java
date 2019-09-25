package com.example.debtsmanager.models;

public class Debt {

    User FromUser;
    User ToUser;
    int Amount;

    public User getFromUser() {
        return FromUser;
    }

    public void setFromUser(User fromUser) {
        FromUser = fromUser;
    }

    public User getToUser() {
        return ToUser;
    }

    public void setToUser(User toUser) {
        ToUser = toUser;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public Debt(User fromUser, User toUser, int amount) {
        FromUser = fromUser;
        ToUser = toUser;
        Amount = amount;
    }
}
