package org.example.Account;

import org.example.Bank;
import org.example.Customer;

public class SavingAccount extends Account {
    private static final  double INTEREST_RATE = 0.03;
    public SavingAccount(Customer owner, Bank bank) {
        super(owner, bank);

    }

    @Override
    protected boolean canWithdraw(double amount) {
        return (getBalance() - amount >= 0);
    }

    public void applyYearlyInterest() {
        double interest = getBalance() * INTEREST_RATE;
        deposit(interest);
        System.out.println("Accrued interest " + interest + " zł");
    }
}
