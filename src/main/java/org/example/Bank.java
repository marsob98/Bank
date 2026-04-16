package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Account.SavingAccount;
import org.example.Exception.AccountNotFoundException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    public List<Customer> customers = new ArrayList<>();
    public List<Account> accounts = new ArrayList<>();
    public List<Transaction> allTransactions = new ArrayList<>();
    public void addTransaction(Transaction t) {
        allTransactions.add(t);
    }


    public Customer registerCustomer(String firstName, String lastName, String pesel) {
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

    public Customer findCustomerByPesel(String pesel) {
        for (Customer customer : customers) {
            if (customer.getPesel().equals(pesel)) {
                return customer;
            }
        }
        return null;
    }

    public void transfer(String sourceAccount, String targetAccount, double amount) {
        Account source = findAccountByNumber(sourceAccount);
        Account target = findAccountByNumber(targetAccount);

        if (source == null || target == null) {
            throw new AccountNotFoundException("Account not found");
        }

        source.withdraw(amount);
        target.deposit(amount);

        Transaction t = new Transaction(TransactionType.TRANSFER, amount, source, target, LocalDateTime.now());
        allTransactions.add(t);
        System.out.println("You've send " + amount + " zł from " + sourceAccount + " account to " + targetAccount);
    }

    public void showAllTransactions() {
        for (Transaction transaction : allTransactions) {
            System.out.println(transaction.toString());
        }
    }

    public void detectFraud(Account account) {
        List<Transaction> transactionsList = account.getTransactionsList();
        if (transactionsList.size() >= 10) {
            int size = transactionsList.size();
            int fromIndex = Math.max(0, size - 10);
            List<Transaction> last10Transactions = transactionsList.subList(fromIndex, size);
            int counter = 0;
            for (Transaction transaction : last10Transactions) {
                if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
                    if (transaction.getAmount() >= 1000) {
                        counter++;
                    }
                }
            }
            if (counter >= 5) {
                account.blockAccount();
                System.out.println("FRAUD DETECTED! ACCOUNT "+ account.getAccountNumber() +" IS BLOCKED");
            }
        }
    }

    public CheckingAccount openCheckingAccountFor(Customer customer) {
        CheckingAccount acc = new CheckingAccount(customer, this);
        customer.addAccount(acc);
        accounts.add(acc);
        System.out.println("Account " + acc.getAccountNumber() + " for " + customer);
        return  acc;
    }

    public SavingAccount openSavingAccountFor(Customer customer) {
        SavingAccount acc = new SavingAccount(customer, this);
        customer.addAccount(acc);
        accounts.add(acc);
        System.out.println("Account " + acc.getAccountNumber() + " for " + customer);
        return acc;
    }

     public void saveCustomersToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("firstName,lastName,pesel");

            for (Customer customer : customers) {
                writer.printf("%s,%s,%s%n",
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getPesel()
                );
            }

            System.out.println("Saved " + customers.size() + " customers to file " + filename);

        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());

        }
     }

     public void loadCustomersFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File " + filename + " not found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String pesel = parts[2];

                    Customer customer = new Customer(firstName, lastName, pesel);
                    customers.add(customer);
                }
            }

            System.out.println("Loaded " + customers.size() + " customers from file " + filename);

        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }

     }

     public void showAllCustomers() {
         System.out.println("All Customers");
         System.out.println("============================");
        for (Customer customer : customers) {
            System.out.println(customer);
        }

     }

     public void saveAccountsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("accountNumber,accountType,ownerPesel,balance,isBlocked");

            for (Account account : accounts) {
                String accountType = (account instanceof CheckingAccount) ? "CHECKING" : "SAVINGS";
                writer.printf("%s,%s,%s,%.2f,%b%n",
                        account.getAccountNumber(),
                        accountType,
                        account.getOwner().getPesel(),
                        account.getBalance(),
                        account.isBlocked()
                );
            }

            System.out.println("Saved " + accounts.size() + " accounts");

        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
     }

     public void loadAccountsFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File " + filename + " not found.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String accountNumber = parts[0];
                    String accountType = parts[1];
                    String ownerPesel = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    boolean isBlocked = Boolean.parseBoolean(parts[4]);

                    Customer owner = findCustomerByPesel(ownerPesel);
                    if (owner == null) {
                        continue;
                    }

                    Account account;
                    if (accountType.equals("CHECKING")) {
                        account = new CheckingAccount(owner, this);
                    } else {
                        account = new SavingAccount(owner, this);
                    }

                    account.setBalance(balance);
                    if (isBlocked) {
                        account.blockAccount();
                    }
                    accounts.add(account);
                    owner.addAccount(account);
                }
            }

            System.out.println("Loaded " + accounts.size() + " accounts from file " + filename);

        } catch (IOException e) {
            System.out.println("Something gone wrong " + e.getMessage());
        }
     }

     public void saveTransactionToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("transactionId,type,amount,sourceAccount,targetAccount,timestamp");

            for (Transaction transaction : allTransactions) {
                String source = (transaction.getSourceAccount() != null) ?
                        transaction.getSourceAccount().getAccountNumber() : "";
                String target = (transaction.getTargetAccount() != null) ?
                        transaction.getTargetAccount().getAccountNumber() : "";

                writer.printf("%d,%s,%.2f,%s,%s,%s%n",
                        transaction.getTransactionId(),
                        transaction.getType(),
                        transaction.getAmount(),
                        source,
                        target,
                        transaction.getTimestamp());
            }

            System.out.println("Saved " + allTransactions.size() + " transactions to file " + filename);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }

     public void loadTransactionsFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File does not exist");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    TransactionType type = TransactionType.valueOf(parts[1]);
                    double balance = Double.parseDouble(parts[2]);
                    String sourceNum = parts[3];
                    String targetNum = parts[4];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[5]);

                    Account source = sourceNum.isEmpty() ? null : findAccountByNumber(sourceNum);
                    Account target = targetNum.isEmpty() ? null : findAccountByNumber(targetNum);

                    Transaction transaction = new Transaction(type, balance, source, target, timestamp);

                    allTransactions.add(transaction);

                    if (source != null) {
                        source.getTransactionsList().add(transaction);
                    }

                    if (target != null && target != source) {
                        target.getTransactionsList().add(transaction);
                    }
                }
            }

            System.out.println("Loaded " + allTransactions.size() + " transactions");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
     }

}
