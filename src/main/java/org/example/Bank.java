package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Exception.AccountNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    public List<Customer> customers = new ArrayList<>();
    public List<Account> accounts = new ArrayList<>();
    public List<Transaction> allTransactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        allTransactions.add(t);
    }


    public Customer registerCustomer(String firstName, String lastName, String pesel) {
        Customer c = new Customer(firstName, lastName, pesel);
        customers.add(c);
        return c;
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }

        }
        return null;
    }

    public void transfer(String sourceAccount, String targetAccount, double amount) {
        Account source = findAccountByNumber(sourceAccount);
        Account target = findAccountByNumber(targetAccount);

        if (source == null || target == null) {
            throw new AccountNotFoundException("Account not found");
        }

        source.withdraw(amount);
        target.deposit(amount);

        Transaction t = new Transaction(TransactionType.TRANSFER, amount, source, target, LocalDateTime.now());
        allTransactions.add(t);
        System.out.println("You've send " + amount + " zł from " + sourceAccount + " account to " + targetAccount);
    }

    public void showAllTransactions() {
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction.toString());
        }
    }

    public void detectFraud(Account account) {
        List<Transaction> transactionsList = account.getTransactionsList();
        if (transactionsList.size() >= 10) {
            int size = transactionsList.size();
            int fromIndex = Math.max(0, size - 10);
            List<Transaction> last10Transactions = transactionsList.subList(fromIndex, size);
            int counter = 0;
            for (Transaction transaction : last10Transactions) {
                if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
                    if (transaction.getAmount() >= 1000) {
                        counter++;
                    }
                }
            }
            if (counter >= 5) {
                account.blockAccount();
                System.out.println("FRAUD DETECTED! ACCOUNT "+ account.getAccountNumber() +" IS BLOCKED");
            }
        }
    }

    public CheckingAccount openCheckingAccountFor(Customer customer) {
        CheckingAccount acc = new CheckingAccount(customer, this);
        customer.addCustomer(acc);
        accounts.add(acc);
        System.out.println("Account " + acc.getAccountNumber() + " for " + customer);

        return  acc;
    }






}
