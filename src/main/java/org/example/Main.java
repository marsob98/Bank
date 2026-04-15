package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Account.SavingAccount;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.loadCustomersFromFile("Transactions.csv");
        bank.loadAccountsFromFile("Accounts.csv");
        bank.loadTransactionsFromFile("Transactions.csv");
        bank.showAllCustomers();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("MENU");
            System.out.println("1. Add customer");
            System.out.println("2. Add account");
            System.out.println("3. Make transaction");
            System.out.println("4. Show all customers");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.println("Name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Last name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("PESEL: ");
                    String pesel = scanner.nextLine();
                    bank.registerCustomer(firstName, lastName, pesel);
                    System.out.println("Customer added");
                }
            case 4 -> bank.showAllCustomers();

            case 5 -> {
                bank.saveCustomersToFile("Customers.csv");
                bank.saveAccountsToFile("Accounts.csv");
                bank.saveTransactionToFile("Transactions.csv");
                System.out.println("Exit program");
                running = false;
            }
            }
        }









    }
}