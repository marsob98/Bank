package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;
import org.example.Account.SavingAccount;
import org.example.Exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    @Test
    void shouldDepositAndWithdraw() {
        Bank bank = new Bank();
        Customer customer = new Customer("Marcin", "Sobieraj", "98011508734");
        Account account = new CheckingAccount(customer, bank);
        account.deposit(1000);
        assertEquals(1000, account.getBalance());
        account.withdraw(1000);
        assertEquals(0, account.getBalance());
    }

    @Test
    void shouldThrow() {
        Bank bank = new Bank();
        Customer customer = new Customer("Marcin", "Sobieraj", "98011508734");
        Account account = new SavingAccount(customer, bank);
        account.deposit(100);
        assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(200));
    }

    @Test
    void shouldAllowOverdraftUpToMinus1000OnCheckingAccount() {
        Bank bank = new Bank();
        Customer customer = new Customer("Marcin", "Sobieraj", "98011508734");
        Account account = new CheckingAccount(customer, bank);
        account.deposit(500);
        account.withdraw(1400);
        assertEquals(-900, account.getBalance());
        assertThrows(InsufficientFundsException.class,
                () -> account.withdraw(200));
    }

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        Bank bank = new Bank();
        Customer customer = new Customer("Marcin", "Sobieraj", "98011508734");
        Account account = bank.openCheckingAccountFor(customer);
        Account account1 = bank.openCheckingAccountFor(customer);
        account.deposit(1000);
        bank.transfer(account.getAccountNumber(), account1.getAccountNumber(), 500);
        assertEquals(500, account.getBalance());
        assertEquals(500, account1.getBalance());
    }

    @Test
    void shouldBlockAccountAfterFraudDetection() {
        Bank bank = new Bank();
        Customer customer = new Customer("Marcin", "Sobieraj", "98011508734");
        Account account = bank.openCheckingAccountFor(customer);
        account.deposit(100000);

        for (int i = 0; i < 9; i++) {
            account.withdraw(1000);
        }

        assertTrue(account.isBlocked());
    }

}
