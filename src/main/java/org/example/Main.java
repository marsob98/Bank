package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Account.SavingAccount;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

//        Account account1 = new CheckingAccount(customer1, bank);
//        Account account2 = new SavingAccount(customer2, bank);

        Customer marcin = bank.registerCustomer("Marcin", "Sobieraj", "98011508734");
        Customer cinek = bank.registerCustomer("Cinek", "Sobieraj", "98011508735");

//        Account chekingMarcin = new CheckingAccount(marcin, bank);
        Account savingCinek = new SavingAccount(cinek, bank);

        Account chekingMarcin = bank.openCheckingAccountFor(marcin);


        chekingMarcin.deposit(100000);
        savingCinek.deposit(3000);

        System.out.println(savingCinek.getBalance());
        System.out.println(chekingMarcin.getBalance());
        bank.transfer(chekingMarcin.getAccountNumber(), savingCinek.getAccountNumber(), 3000);
        System.out.println(savingCinek.getBalance());
        System.out.println(chekingMarcin.getBalance());

        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);
        chekingMarcin.withdraw(1000);


    }
}