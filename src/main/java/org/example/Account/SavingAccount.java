package org.example.Account;

import org.example.Bank;
import org.example.Customer;

public class SavingAccount extends Account {
    public SavingAccount(Customer owner, Bank bank) {
        super(owner, bank);
    }
}
