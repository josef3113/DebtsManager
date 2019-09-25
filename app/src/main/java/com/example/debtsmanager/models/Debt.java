package com.example.debtsmanager.models;

public class Debt {

    String From;
    String To;
    int Amount;

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        this.From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        this.To = to;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public Debt() {
    }

    public Debt(String from, String to, int amount) {
        From = from;
        To = to;
        Amount = amount;
    }
}
