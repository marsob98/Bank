package org.example;

import org.example.Account.Account;
import org.example.Exception.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    public List<Customer> customers = new ArrayList<>();
    public List<Account> accounts = new ArrayList<>();
    public List<Transaction> allTransactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        allTransactions.add(t);
    }


    public Customer registerCustomer(String firstName, String lastName, long pesel) {
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

}
