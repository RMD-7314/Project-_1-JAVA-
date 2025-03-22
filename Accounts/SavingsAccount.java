package Accounts;
import Bank.Bank;


public class SavingsAccount extends Account implements Deposit, Withdrawal, FundTransfer{
    private double balance;

    /*
    create new savings account
     */
    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, Double initialDeposit) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.adjustAccountBalance(initialDeposit);
    }

    public Bank getBank(){
        return this.getBANK();
    }

    public double getAccountBalance(){
        return this.balance;
    }

    /**
     *
     * @return string representation of current account balance.
     */
    public String getAccountBalanceStatement(){
        return String.format("Current savings account balance: $%.2f", this.balance);
    }

    /**
     * Validates whether this savings account has enough balance to proceed with such a transaction
     * based on the amount that is to be adjusted.
     * Params:
     * amount – Amount of money to be supposedly adjusted from this account’s balance.
     * @return true if transaction can proceed, false if balance is insufficient
     */
    private boolean hasEnoughBalance(double amount){
        return this.balance >= amount;
    }

    /**
     * warns the account owner that their balance is not enough for the transaction to proceed
     */
    private void insufficientBalance(){
        System.out.println("Insufficient balance.");
    }

    /**
     * Transfers an amount of money from this account to another savings account.
     * Is extensively used by the other transfer() method.
     *
     * @param account Accounts.Account number of the recepient.
     * @param amount Amount of money to be transferred.
     * @return boolean flag if fund transfer is successful or not.
     * @throws IllegalAccountType
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (account instanceof CreditAccount){
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        if(!hasEnoughBalance(amount) || !Bank.accountExists(this.getBANK(), account.getACCOUNTNUMBER())){
            return false;
        }
        else{
            SavingsAccount saveAcc = (SavingsAccount) account;
            if(saveAcc.cashDeposit(amount)){
                String descriptionSender = String.format("Transferred amount: $%.2f \nRecipient Account#: %s   Recipient Bank: %s", amount, saveAcc.getACCOUNTNUMBER(), saveAcc.getBANK().getName());
                String descriptionReceiver = String.format("Received amount: $%.2f Sender Account#: %s   Sender Bank: %s", amount, this.getACCOUNTNUMBER(), this.getBANK().getName());
                this.adjustAccountBalance(-amount);

                this.addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer, descriptionSender);

                saveAcc.addNewTransaction(saveAcc.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer, descriptionReceiver);
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * Transfers an amount of money from this account to another savings account.
     * Should be used when transferring to other banks.
     * @param bank object of the recipient account.
     * @param account Recipient's account number.
     * @param amount Amount of money to be transferred.
     * @return boolean flag if fund transfer transaction is successful or not.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType{
        if (account instanceof CreditAccount){
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        double totalAmountToAdjust = this.getBANK().getProcessingFee() + amount;
        if(!this.hasEnoughBalance(totalAmountToAdjust) || !Bank.accountExists(bank, account.getACCOUNTNUMBER())){
            return false;
        }
        else{
            SavingsAccount saveAcc = (SavingsAccount) account;
            if(saveAcc.cashDeposit(amount)){
                this.adjustAccountBalance(-totalAmountToAdjust);
                String descriptionSender = String.format("Transferred amount: $%.2f \nRecipient Account#: %s   Recipient Bank: %s", amount, saveAcc.getACCOUNTNUMBER(), bank.getName());
                String descriptionReceiver = String.format("Received amount: $%.2f Sender Account#: %s   Sender Bank: %s", amount, this.getACCOUNTNUMBER(), this.getBANK().getName());

                this.addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer, descriptionSender);

                saveAcc.addNewTransaction(saveAcc.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer, descriptionReceiver);
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     *Deposit some cash into this account. Cannot be greater than the bank's deposit limit.
     *
     * @param amount Amount to be deposited.
     * if deposit amount exceeds bank deposit limit
     * @return false meaning deposit was not successful
     * if deposit amount is within the limit, increase balance
     * @return true meaning deposit was successful
     */
    @Override
    public boolean cashDeposit(double amount){
        //check if the deposit amount exceeds the bank's deposit limit
        if (amount > this.getBANK().getDepositLimit()){
            System.out.println("Deposit exceeds the bank's deposit limit.");
            return false;
        }
        adjustAccountBalance(amount);
        this.addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.Deposit, "Deposited amount: "+ amount);
        return true;
    }

    /**
     *
     * @param amount Amount of money to be withdrawn from.
     * if balance is insufficient
     * @return false informs the user that balance is insufficient for withdrawal
     * if balance is enough, decrease balance
     * @return true transaction was successful
     */
    @Override
    public boolean withdrawal(double amount){
        //checks if the has enough balance for withdrawal
        if (!hasEnoughBalance(amount)){
            insufficientBalance();
            return false;
        }
        adjustAccountBalance(-amount);
        addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.Withdraw, "Withdrew Amount: "+ amount);
        return true;
    }

    /**
     * adjust the account balance based on the provided amount
     * if the resulting balance is less than 0, then it is forcibly reset to 0
     * @param amount – Amount to be added or subtracted from the account balance.
     */
    private void adjustAccountBalance(double amount){
        balance += amount;
        if(balance < 0.0){
            balance = 0.0;
        }
    }

    /*
    returns the account details
     */
    @Override
    public String toString(){
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
