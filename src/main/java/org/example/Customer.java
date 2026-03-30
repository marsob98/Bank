package org.example;

import org.example.Account.Account;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    String firstName;
    String lastName;
    long pesel;
    List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName, long pesel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }


}
