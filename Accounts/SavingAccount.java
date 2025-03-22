
package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Deposit, Withdrawal, FundTransfer {
    private double balance;

    /**
     * Creates a new savings account.
     *
     * @param bank           The bank associated with this account.
     * @param accountNumber  The account number.
     * @param ownerFirstName The first name of the account owner.
     * @param ownerLastName  The last name of the account owner.
     * @param ownerEmail     The email of the account owner.
     * @param pin            The account PIN.
     * @param initialDeposit The initial deposit amount.
     */
    public SavingsAccount(Bank bank, String accountNumber, String ownerFirstName, String ownerLastName, String ownerEmail, String pin, double initialDeposit) {
        super(bank, accountNumber, ownerFirstName, ownerLastName, ownerEmail, pin);
        this.adjustAccountBalance(initialDeposit);
    }

    /**
     * @return The bank associated with this account.
     */
    public Bank getBank() {
        return this.getBANK();
    }

    /**
     * @return The current account balance.
     */
    public double getAccountBalance() {
        return this.balance;
    }

    /**
     * @return A string representation of the current account balance.
     */
    public String getAccountBalanceStatement() {
        return String.format("Current savings account balance: $%.2f", this.balance);
    }

    /**
     * Checks if the account has enough balance for a transaction.
     *
     * @param amount The amount to be checked.
     * @return True if the balance is sufficient, false otherwise.
     */
    private boolean hasEnoughBalance(double amount) {
        return this.balance >= amount;
    }

    /**
     * Warns the account owner that their balance is insufficient for the transaction.
     */
    private void insufficientBalance() {
        System.out.println("Insufficient balance.");
    }

    /**
     * Transfers money from this account to another account in the same bank.
     *
     * @param account The recipient's account.
     * @param amount  The amount to be transferred.
     * @return True if the transfer is successful, false otherwise.
     * @throws IllegalAccountType If the account types are incompatible for fund transfer.
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        if (!hasEnoughBalance(amount) || !Bank.accountExists(this.getBANK(), account.getAccountNumber())) {
            return false;
        }
        SavingsAccount savingsAccount = (SavingsAccount) account;
        if (savingsAccount.deposit(amount)) {
            String descriptionSender = String.format("Transferred amount: $%.2f \nRecipient Account#: %s   Recipient Bank: %s",
                    amount, savingsAccount.getAccountNumber(), savingsAccount.getBank().getBankName());
            String descriptionReceiver = String.format("Received amount: $%.2f Sender Account#: %s   Sender Bank: %s",
                    amount, this.getAccountNumber(), this.getBank().getBankName());

            this.adjustAccountBalance(-amount);
            this.addTransaction(new Transaction(this.getAccountNumber(), Transaction.TransactionType.FUND_TRANSFER, descriptionSender));
            savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountNumber(), Transaction.TransactionType.FUND_TRANSFER, descriptionReceiver));
            return true;
        }
        return false;
    }

    /**
     * Transfers money from this account to another account in a different bank.
     * A processing fee is deducted from the source account.
     *
     * @param bank    The bank of the recipient's account.
     * @param account The recipient's account.
     * @param amount  The amount to be transferred.
     * @return True if the transfer is successful, false otherwise.
     * @throws IllegalAccountType If the account types are incompatible for fund transfer.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        double totalAmountToAdjust = this.getBank().getProcessingFee() + amount;
        if (!hasEnoughBalance(totalAmountToAdjust) || !Bank.accountExists(bank, account.getAccountNumber())) {
            return false;
        }
        SavingsAccount savingsAccount = (SavingsAccount) account;
        if (savingsAccount.deposit(amount)) {
            String descriptionSender = String.format("Transferred amount: $%.2f \nRecipient Account#: %s   Recipient Bank: %s",
                    amount, savingsAccount.getAccountNumber(), bank.getBankName());
            String descriptionReceiver = String.format("Received amount: $%.2f Sender Account#: %s   Sender Bank: %s",
                    amount, this.getAccountNumber(), this.getBank().getBankName());

            this.adjustAccountBalance(-totalAmountToAdjust);
            this.addTransaction(new Transaction(this.getAccountNumber(), Transaction.TransactionType.FUND_TRANSFER, descriptionSender));
            savingsAccount.addTransaction(new Transaction(savingsAccount.getAccountNumber(), Transaction.TransactionType.FUND_TRANSFER, descriptionReceiver));
            return true;
        }
        return false;
    }

    /**
     * Deposits an amount of money into this account.
     *
     * @param amount The amount to be deposited.
     * @return True if the deposit is successful, false otherwise.
     */
    @Override
    public boolean deposit(double amount) {
        if (amount > this.getBank().getDepositLimit()) {
            System.out.println("Deposit exceeds the bank's deposit limit.");
            return false;
        }
        adjustAccountBalance(amount);
        this.addTransaction(new Transaction(this.getAccountNumber(), Transaction.TransactionType.DEPOSIT, "Deposited amount: " + amount));
        return true;
    }

    /**
     * Withdraws an amount of money from this account.
     *
     * @param amount The amount to be withdrawn.
     * @return True if the withdrawal is successful, false otherwise.
     */
    @Override
    public boolean withdraw(double amount) {
        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }
        adjustAccountBalance(-amount);
        this.addTransaction(new Transaction(this.getAccountNumber(), Transaction.TransactionType.WITHDRAWAL, "Withdrew amount: " + amount));
        return true;
    }

    /**
     * Adjusts the account balance by the specified amount.
     * Ensures the balance does not go below 0.
     *
     * @param amount The amount to adjust the balance by.
     */
    private void adjustAccountBalance(double amount) {
        balance += amount;
        if (balance < 0.0) {
            balance = 0.0;
        }
    }

    /**
     * @return A string representation of the savings account details.
     */
    @Override
    public String toString() {
        return String.format(
                "\n*****************************************\n" +
                "  Savings Account Details:\n" +
                "%s\n" +
                "%s\n" +
                "\n*****************************************",
                super.toString(),
                this.getAccountBalanceStatement()
        );
    }
}