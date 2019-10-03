package com.example.debtsmanager.models;

import androidx.annotation.Nullable;

public class Debt {

    private String From;
    private String To;
    private int Amount;

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

    public Debt(String from, String to, int amount)
    {
        From = from;
        To = to;
        Amount = amount;
    }

    @Override
    public boolean equals(@Nullable Object obj)
    {
        Debt otherDebt = (Debt) obj;

        return (otherDebt.From.equals(getFrom()) && otherDebt.To.equals(getTo()));
    }
}
