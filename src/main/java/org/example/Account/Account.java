package org.example.Account;

import org.example.Customer;
import org.example.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    static int number = 1;
    int accountNumber;
    Customer owner;
    double balance;
    boolean isBlocked;
    List<Transaction> transactionsList = new ArrayList<>();

    public Account(int accountNumber, Customer owner) {
        this.accountNumber = number++;
        this.owner = owner;
        isBlocked = false;
    }

    public void deposit(double amount) {
        balance += amount;

    }

    public double getBalance() {
        return balance;
    }

    public void showHistory() {
        // for czy cos tam z lista

    }

    public void blockAccount() {
        isBlocked = true;
    }

    public void unblockAccount() {
        isBlocked = false;
    }
}
