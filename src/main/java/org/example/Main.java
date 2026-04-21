package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Account.SavingAccount;
import org.example.Exception.AccountNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.loadCustomersFromFile("Customers.csv");
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
                case 2 -> {
                    System.out.println("PESEL: ");
                    String pesel = scanner.nextLine();

                    Optional<Customer> customerOptional = bank.findCustomerByPesel(pesel);
                    if (customerOptional.isEmpty()) {
                        System.out.println("Customer not found");
                        break;
                    }
                    Customer customer = customerOptional.get();

                    System.out.println("1. SAVINGS");
                    System.out.println("2. CHECKING");

                    int c = scanner.nextInt();
                    scanner.nextLine();

                    if (c == 1) {
                        bank.openSavingAccountFor(customer);
                    } else if (c == 2) {
                        bank.openCheckingAccountFor(customer);
                    } else {
                        System.out.println("Wrong choice");
                    }

                }
                case 3 -> {
                    System.out.println("Account nr: ");
                    String accNum = scanner.nextLine();

                    Account acc = bank.findAccountByNumber(accNum).orElseThrow(()
                            -> new AccountNotFoundException("Account not found"));



                    System.out.println("1. Deposit");
                    System.out.println("2. Withdraw");
                    System.out.println("3. Transfer");

                    int c = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Amount:");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    if (c == 1) {
                        acc.deposit(amount);
                    } else if (c == 2) {
                        acc.withdraw(amount);
                    } else if (c == 3) {
                        System.out.println("Account nr: ");
                        String targetAcc = scanner.nextLine();
                        bank.transfer(accNum, targetAcc, amount);
                    }
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

        Map<String, List<Account>> gruppedAccounts = bank.accounts.stream()
                .collect(Collectors.groupingBy(account -> account instanceof CheckingAccount ? "CHECKING" : "SAVINGS"));

        Map<TransactionType, Long> gruppedTransactions = bank.allTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));

        Map<String, Double> gruppedAndBalance = bank.accounts.stream()
                .collect(Collectors.groupingBy(account -> account.getOwner().getPesel(), Collectors.summingDouble(Account::getBalance)));













    }
}