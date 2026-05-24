package com.banking.model;

public class Account {

    private int id;

    private String ownerName;

    private String accountNumber;

    private double balance;

    public Account() {
    }

    public Account(
            int id,
            String ownerName,
            String accountNumber,
            double balance
    ) {
        this.id = id;
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {

        return "\nACCOUNT ID: " + id +
                "\nOWNER: " + ownerName +
                "\nACCOUNT NUMBER: " + accountNumber +
                "\nBALANCE: $" + balance +
                "\n--------------------------------";
    }
}