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

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        String src = (sourceAccount != null) ?
                sourceAccount.getAccountNumber() : "EXTERNAL";
        String trg = (targetAccount != null) ?
                targetAccount.getAccountNumber() : "EXTERNAL";

        return String.format("#%d, %s, %.2f zł (%s -> %s) %s",
        transactionId,
        type,
        amount,
        src,
        trg,
        timestamp.toLocalTime()
        );
    }

}
