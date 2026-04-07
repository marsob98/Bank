package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Exception.InvalidPeselException;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private long pesel;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName, long pesel) {
        String peselStr = String.valueOf(pesel);
        if (peselStr.length() != 11) {
            throw new InvalidPeselException("PESEL must have 11 digits");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public void addCustomer(Account account) {
        accounts.add(account);
    }

    public void showAllAccounts() {
        System.out.println(firstName + " " + lastName + " accounts");
        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + " : " + account.getBalance() + " zł");
        }
    }

    public CheckingAccount openCheckingAccount(Bank bank) {
        CheckingAccount acc = new CheckingAccount(this, bank);
        accounts.add(acc);
        return  acc;
    }






}
