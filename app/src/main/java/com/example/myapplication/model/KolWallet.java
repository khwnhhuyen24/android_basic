package com.example.myapplication.model;

public class KolWallet{
    public int getKolId() {
        return kolId;
    }

    public void setKolId(int kolId) {
        this.kolId = kolId;
    }

    public int getBalanceVn() {
        return balanceVn;
    }

    public void setBalanceVn(int balanceVn) {
        this.balanceVn = balanceVn;
    }

    public int getBalanceKr() {
        return balanceKr;
    }

    public void setBalanceKr(int balanceKr) {
        this.balanceKr = balanceKr;
    }

    public int kolId;
    public int balanceVn;
    public int balanceKr;
}
