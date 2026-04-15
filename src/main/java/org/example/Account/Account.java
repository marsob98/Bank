package org.example.Account;

import org.example.*;
import org.example.Exception.AccountBlockedException;
import org.example.Exception.InsufficientFundsException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private static int nextNumber = 1000;
    private String accountNumber;
    private Customer owner;
    private double balance;
    private boolean isBlocked;
    private List<Transaction> transactionsList = new ArrayList<>();
    private Bank bank;

    protected abstract boolean canWithdraw(double amount);

    public Account(Customer owner, Bank bank) {
        this.accountNumber = "PL" + nextNumber++;
        this.owner = owner;
        this.bank = bank;
        balance = 0;
        isBlocked = false;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (isBlocked) {
            throw new AccountBlockedException("Account blocked");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("More than 0!");
        }
        balance += amount;
        Transaction t = new Transaction(TransactionType.DEPOSIT,
                amount,
                null,
                this,
                LocalDateTime.now());
        transactionsList.add(t);
        bank.addTransaction(t);

        System.out.println("You donated " + amount);
    }

    public void withdraw(double amount) {
        if (isBlocked) {
            throw new AccountBlockedException("Account is blocked");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("More than 0");
        }
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException("No money" +
                    "");
        }

        balance -= amount;
        Transaction t = new Transaction(TransactionType.WITHDRAWAL,
                amount,
                this,
                null,
                LocalDateTime.now());
        transactionsList.add(t);
        bank.addTransaction(t);
        bank.detectFraud(this);

        System.out.println("You paid out " + amount + " zł");

    }


    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void showHistory() {
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction);
        }

    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void blockAccount() {
        isBlocked = true;
    }

    public void unblockAccount() {
        isBlocked = false;
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Transaction> getTransactionsList() {
        return transactionsList;
    }
}
