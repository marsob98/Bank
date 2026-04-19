package org.example;

import org.example.Account.Account;
import org.example.Account.CheckingAccount;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================
 *  POWTÓRKA: Streams, Lambda, Method Reference
 *  Twoje rozwiązania + wzorce + objaśnienia
 * ============================================
 */
public class StreamsPowtorka {

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.loadCustomersFromFile("Customers.csv");
        bank.loadAccountsFromFile("Accounts.csv");
        bank.loadTransactionsFromFile("Transactions.csv");

        // Dodaj testowych klientów jeśli baza pusta
        bank.registerCustomer("Anna", "Kowalska", "90010112345");
        bank.registerCustomer("Kamil", "Nowak", "85020254321");
        bank.registerCustomer("Kasia", "Kwiatkowska", "92030367890");


        // =============================================
        // 1. LAMBDA — co jest co?
        // =============================================
        //
        // Lambda to skrót na anonimową klasę z jedną metodą.
        //
        //   customer -> customer.getLastName()
        //   ▲                    ▲
        //   │                    │
        //   parametr             co z nim robisz
        //   (jeden element       (wyrażenie które
        //    z kolekcji)          zwraca wynik)
        //
        // ZAWSZE po lewej stronie strzałki jest JEDEN ELEMENT
        // z Twojej kolekcji. Stream bierze elementy jeden po
        // drugim i każdy wstawia w to miejsce.


        // =============================================
        // 2. METHOD REFERENCE — skrót na lambdę
        // =============================================
        //
        // Gdy lambda tylko wywołuje jedną metodę na elemencie,
        // możesz użyć skrótu:
        //
        //   customer -> customer.getLastName()    // lambda
        //   Customer::getLastName                 // method reference
        //
        //   account -> account.getBalance()       // lambda
        //   Account::getBalance                   // method reference
        //
        // Oba zapisy robią DOKŁADNIE to samo.
        // Method reference = "weź tę metodę i zastosuj ją"
        //
        // UWAGA: method reference działa tylko gdy lambda
        // wywołuje JEDNĄ metodę bez dodatkowej logiki.
        //
        //   customer -> customer.getPesel().startsWith("9")
        //   ^^^ tego NIE DA SIĘ zamienić na method reference
        //       bo są DWA wywołania + argument


        // =============================================
        // 3. TWOJE ROZWIĄZANIA — filter + collect
        // =============================================

        // Zadanie: klienci z nazwiskiem na "K"
        List<Customer> customersStartsWithK = bank.customers.stream()
                .filter(customer -> customer.getLastName().startsWith("K"))
                .toList();
        System.out.println("Na K: " + customersStartsWithK);

        // Zadanie: policz klientów z PESEL na "9"
        long customersWithPesel9 = bank.customers.stream()
                .filter(customer -> customer.getPesel().startsWith("9"))
                .count();
        System.out.println("PESEL na 9: " + customersWithPesel9);


        // =============================================
        // 4. TWOJE ROZWIĄZANIA — map
        // =============================================

        // Zadanie: wyciągnij same nazwiska
        List<String> onlyLastNames = bank.customers.stream()
                .map(customer -> customer.getLastName())   // lambda
                // .map(Customer::getLastName)              // method reference — to samo
                .toList();
        System.out.println("Nazwiska: " + onlyLastNames);

        // Zadanie: PESEL na "9" → wyciągnij nazwiska (filter + map)
        List<String> pesel9lastNames = bank.customers.stream()
                .filter(customer -> customer.getPesel().startsWith("9"))
                .map(customer -> customer.getLastName())
                .toList();
        System.out.println("Nazwiska z PESEL 9: " + pesel9lastNames);


        // =============================================
        // 5. TWOJE ROZWIĄZANIA — sorted
        // =============================================

        // Zadanie: posortuj po nazwisku A→Z
        List<Customer> sortedCustomer = bank.customers.stream()
                .sorted(Comparator.comparing(customer -> customer.getLastName()))
                // .sorted(Comparator.comparing(Customer::getLastName))  // method ref
                .toList();
        System.out.println("Posortowani: " + sortedCustomer);

        // Odwrotnie Z→A:
        // .sorted(Comparator.comparing(Customer::getLastName).reversed())


        // =============================================
        // 6. TWOJE ROZWIĄZANIA — findFirst + Optional
        // =============================================

        // Zadanie: pierwszy klient z PESEL na "9"
        Optional<Customer> first9 = bank.customers.stream()
                .filter(customer -> customer.getPesel().startsWith("9"))
                .findFirst();
        System.out.println("Pierwszy z PESEL 9: " + first9);

        // Optional to PUDEŁKO na JEDEN obiekt:
        //   Optional.of(konto)   → pudełko z kontem w środku
        //   Optional.empty()     → puste pudełko
        //
        // Jak wyciągnąć wartość:
        first9.ifPresent(c -> System.out.println("Znalazłem: " + c));
        // Customer result = first9.orElse(null);                          // wartość albo null
        // Customer result = first9.orElseThrow(() -> new RuntimeException("Brak"));  // wartość albo wyjątek


        // =============================================
        // 7. TWOJE ROZWIĄZANIA — mapToDouble + sum, max
        // =============================================

        // Zadanie: łączne saldo wszystkich kont
        double sumTotal = bank.accounts.stream()
                .mapToDouble(Account::getBalance)   // method reference
                .sum();
        System.out.println("Suma sald: " + sumTotal);

        // Zadanie: konto z najwyższym saldem
        Optional<Account> highestBalanceAcc = bank.accounts.stream()
                .max(Comparator.comparing(Account::getBalance));
        System.out.println("Najwyższe saldo: " + highestBalanceAcc);


        // =============================================
        // 8. JESZCZE DO ZROBIENIA — groupingBy
        // =============================================
        //
        // groupingBy rozbija listę na MAPĘ grup.
        // Wynik to Map<Klucz, List<Wartość>>
        //
        // Wzorzec:
        //   Map<String, List<Account>> grouped = bank.accounts.stream()
        //       .collect(Collectors.groupingBy(
        //           account -> /* klucz grupy */
        //       ));
        //
        // Zadania do zrobienia:
        //
        // 1. Pogrupuj konta po typie ("CHECKING" vs "SAVINGS"):
        //    klucz: account instanceof CheckingAccount ? "CHECKING" : "SAVINGS"
        //
        // 2. Pogrupuj transakcje po typie i policz ile każdego:
        //    .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()))
        //
        // 3. Pogrupuj konta po właścicielu i zsumuj salda:
        //    .collect(Collectors.groupingBy(Account::getOwner, Collectors.summingDouble(Account::getBalance)))


        // =============================================
        // ŚCIĄGAWKA — wszystkie operacje
        // =============================================
        //
        // .stream()           → otwórz rurociąg
        //
        // OPERACJE POŚREDNIE (można łączyć wiele):
        //   .filter(x -> bool)   → wyrzuć co nie pasuje
        //   .map(x -> y)         → zamień na coś innego
        //   .mapToDouble(x -> d) → zamień na liczbę
        //   .sorted(Comparator)  → posortuj
        //   .limit(n)            → weź tylko n pierwszych
        //   .distinct()          → usuń duplikaty
        //
        // OPERACJE TERMINALNE (jedna na końcu):
        //   .toList()            → zbierz do listy (niemutowalnej)
        //   .collect(Collectors.toList())  → zbierz do ArrayList
        //   .collect(Collectors.groupingBy(...))  → zbierz do mapy grup
        //   .count()             → policz ile
        //   .sum()               → zsumuj (po mapToDouble)
        //   .average()           → średnia (po mapToDouble)
        //   .findFirst()         → pierwszy element (Optional)
        //   .max(Comparator)     → największy (Optional)
        //   .min(Comparator)     → najmniejszy (Optional)
        //   .forEach(x -> ...)   → zrób coś z każdym


    }
}