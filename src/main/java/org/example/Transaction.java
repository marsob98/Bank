package org.example;

import org.example.Account.Account;

import java.time.LocalDateTime;

public class Transaction {
    private static int number = 1;
    private int transactionId;
    private double amount;
    private Account sourceAccount;
    private Account targetAccount;
    LocalDateTime timestamp;
    TransactionType type;

    public Transaction(TransactionType type, double amount, Account sourceAccount, Account targetAccount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.timestamp = timestamp;
        transactionId = number++;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }

}
