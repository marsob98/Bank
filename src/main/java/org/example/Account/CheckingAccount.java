package org.example.Account;

import org.example.Bank;
import org.example.Customer;

public class CheckingAccount extends Account {

    @Override
    protected boolean canWithdraw(double amount) {
        double newBalance = getBalance() - amount;
        return (newBalance >= -1000);
    }

    public CheckingAccount(Customer owner, Bank bank) {
        super(owner, bank);
        bank.accounts.add(this);

    }
}
