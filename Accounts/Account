package Accounts;

import Bank.Bank;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Account {
    private final Bank bank;
    private final String accountNumber;
    private final String ownerFirstName;
    private final String ownerLastName;
    private final String ownerEmail;
    private String pin;
    private final ArrayList<Transaction> transactions;

    public Account(Bank bank, String accountNumber, String ownerFirstName, String ownerLastName, String ownerEmail, String pin) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.ownerEmail = ownerEmail;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }

    public Bank getBank() {
        return this.bank;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getOwnerFirstName() {
        return this.ownerFirstName;
    }

    public String getOwnerLastName() {
        return this.ownerLastName;
    }

    public String getOwnerEmail() {
        return this.ownerEmail;
    }

    public String getPin() {
        return this.pin;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    protected String generateTransactionDescription(Account sender, Account receiver, double amount) {
        return String.format(
            "Sender: %s | Amount: %.2f | Recipient Bank: %s",
            sender.getAccountNumber(),
            amount,
            receiver.getBank().getBankName()
        );
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return String.format(
            "Account Number: %s\n" +
            "Owner: %s %s\n" +
            "Email: %s\n" +
            "Bank: %s\n",
            this.accountNumber,
            this.ownerFirstName,
            this.ownerLastName,
            this.ownerEmail,
            this.bank.getBankName()
        );
    }
}
