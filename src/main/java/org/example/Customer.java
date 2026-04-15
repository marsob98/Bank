package org.example;

import org.example.Account.Account;
import org.example.Exception.InvalidPeselException;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private String pesel;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName, String pesel) {
        if (pesel ==  null || pesel.length() != 11) {
            throw new InvalidPeselException("PESEL must have 11 digits");
        }
        if (!pesel.matches("\\d{11}")) {
            throw new InvalidPeselException("PESEL must contains only digits");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void showAllAccounts() {
        System.out.println(firstName + " " + lastName + " accounts");
        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + " : " + account.getBalance() + " zł");
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }
}
