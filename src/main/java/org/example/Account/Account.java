package org.example.Account;

import org.example.Bank;
import org.example.Customer;
import org.example.Transaction;
import org.example.TransactionType;

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
        if (balance - amount >= 0) {
            balance -= amount;
            Transaction t = new Transaction(TransactionType.WITHDRAWAL,
                    amount,
                    this,
                    null,
                    LocalDateTime.now());
            transactionsList.add(t);
            bank.addTransaction(t);
            System.out.println("You withdrew " + amount);
        }

    }

    public void transfer(double amount, Account targetAccount) {
        if (balance - amount >= 0) {
            balance -= amount;
            targetAccount.balance += amount;
            Transaction t = new Transaction(TransactionType.TRANSFER,
                    amount,
                    this,
                    targetAccount,
                    LocalDateTime.now());
            transactionsList.add(t);
            this.bank.addTransaction(t);
            System.out.println("You've send " + amount + " to " + targetAccount.getOwner() + " account");
        }
    }

    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void showHistory() {
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction);
        }

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
}
