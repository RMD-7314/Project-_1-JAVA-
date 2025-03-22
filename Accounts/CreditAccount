package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense{
    private double loan;

    public CreditAccount(Bank Bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        super(Bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = 0;
    }

    public double getLoan() {
        return this.loan;
    }

    /**
     * Loan statement of this credit account
     * @return String loan statement
     */
    public String getLoanStatement() {
        return String.format("Current Loan Amount: $%.2f", loan);
    }

    /**
     * Checks if this credit account can do additional credit transactions if the amount to credit will not
     * exceeded the credit limit set by the bank associated to this Credit Account.
     * @param amountAdjustment The amount of credit to be adjusted once the said transaction is
     * processed.
     * @return Flag if this account can continue with the credit transaction.
     */
    private boolean canCredit(double amountAdjustment) {
        double availableCredit = this.getBANK().getCreditLimit() - this.loan;
        return (amountAdjustment < availableCredit);
    }

    /**
     * Adjust the owner’s current loan. Result of adjustment cannot be less than 0.
     * @param amountAdjustment Amount to be adjusted to the loan of this credit account.
     */
    private void adjustLoanAmount(double amountAdjustment) {
        //for payments
        if(amountAdjustment>=0){
            if(canCredit(amountAdjustment)) {
                this.loan += amountAdjustment;
            }
            else{
                System.out.println("Loan adjustment cannot result in a negative loan amount.");
            }
        }
        //for recompense
        else{
            amountAdjustment = Math.abs(amountAdjustment);
            if(this.loan-amountAdjustment < 0){
                System.out.println("Loan adjustment cannot result in a negative loan amount.");
            }
            else{
                this.loan -= amountAdjustment;
            }
        }
    }

    /**
     * Pay a certain amount of money into a given account object.
     * This is different from Fund Transfer as paying does not have any sort of
     * processing fee.
     *
     * @param account Target account to pay money into.
     * @param amount
     * @throws IllegalAccountType Payment can only be processed between legal account types.
     */
    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("Payments can only be made to Savings Accounts.");
        }

        SavingsAccount savingsAccount = (SavingsAccount) account;

        // Check if the credit account has enough available credit
        if (this.canCredit(amount)) {
            this.adjustLoanAmount(amount);

            // Add the amount to the savings account's balance
            if(savingsAccount.cashDeposit(amount)){ //amount to pay is within savings account deposit limit.
                // Log the transaction
                this.addNewTransaction(account.getACCOUNTNUMBER(), Transaction.Transactions.Payment, "Payment of " + amount + " to account#" + account.getACCOUNTNUMBER());
                savingsAccount.addNewTransaction(savingsAccount.getACCOUNTNUMBER(), Transaction.Transactions.Payment, "Payment from: " + this.getACCOUNTNUMBER() + " of bank " + this.getBANK().getName() + " with the amount of " + amount);
                return true;
            }else{
                return false; //Savings account deposit exceeds deposit limit. Payment failed.
            }
        } else {
            System.out.println("Insufficient credit available.");
            return false; // Payment failed
        }
    }

    /**
     * Recompense some amount of money to the bank and reduce the value of loan recorded in this account.
     * Must not be greater than the current credit.
     *
     * @param amount Amount of money to be recompensed.
     * @return Flag if compensation was successful.
     */
    @Override
    public boolean recompense(double amount) {
        if(amount<this.getBANK().getCreditLimit()) {
            adjustLoanAmount(-amount);
            this.addNewTransaction(this.getBANK().getName(), Transaction.Transactions.Recompense, "Recompense of " + amount + " to bank " + this.getBANK().getName());
            return true; //recompense successful
        }
        return false; //recompense fails as Amount must not be greater than the current credit.
    }

    /**
     * Override toString method to include loan information
     * @return String representation of the CreditAccount
     */
    @Override
    public String toString() {
        return String.format(
                "\n*****************************************\n" +
                "Credit Account Details:\n" +
                        "%s\n" +
                        "%s\n" +
                        "\n*****************************************",
                super.toString(),
                this.getLoanStatement()
        );
    }
}

