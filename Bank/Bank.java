
package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;

import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    private final int ID;
    private String name, passcode;
    private final double DEPOSITLIMIT;
    private final double WITHDRAWLIMIT;
    private final double CREDITLIMIT;
    private double processingFee;
    private final ArrayList<Account> BANKACCOUNTS;

    public Bank(int id, String name, String passcode) {
        this(id, name, passcode, 50000.00, 50000.00, 100000.00, 10.00);
    }

    public Bank(int id, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit,
            double processingFee) {
        this.ID = id;
        this.setName(name);
        this.setPasscode(passcode);
        this.DEPOSITLIMIT = depositLimit;
        this.WITHDRAWLIMIT = withdrawLimit;
        this.CREDITLIMIT = creditLimit;
        this.setProcessingFee(processingFee);
        this.BANKACCOUNTS = new ArrayList<>();
    }

    /**
     * @param name: String
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * @param passcode: String
     */
    private void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    /**
     * sets the processing fee if fee is greater than 0
     * 
     * @param processingFee: double
     */
    private void setProcessingFee(double processingFee) {
        if (processingFee > 0) {
            this.processingFee = processingFee;
        } else {
            this.processingFee = 10.00;
        }
    }

    /**
     * @return BANK ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * @return BANK name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return BANK passcode
     */
    public String getPasscode() {
        return this.passcode;
    }

    /**
     * @return BANK DEPOSITLIMIT
     */
    public double getDepositLimit() {
        return this.DEPOSITLIMIT;
    }

    /**
     * @return BANK WITHDRAWLIMIT
     */
    public double getWithdrawLimit() {
        return this.WITHDRAWLIMIT;
    }

    /**
     * @return BANK CREDITLIMIT
     */
    public double getCreditLimit() {
        return this.CREDITLIMIT;
    }

    /**
     * @return BANK PROCESSING FEE
     */
    public double getProcessingFee() {
        return this.processingFee;
    }

    /**
     * @return BANK BANKACCOUNTS
     */
    public ArrayList<Account> getBANKACCOUNTS() {
        return this.BANKACCOUNTS;
    }

    public <T> void showAccounts(Class<T> accountType) {
        if (this.getBANKACCOUNTS().size() < 1) {
            System.out.println("There are no accounts in this Bank!");
            return;
        }
        for (Account account : this.getBANKACCOUNTS()) {
            if (accountType.isInstance(account)) {
                System.out.println(account);
            }
        }
    }

    public Account getBankAccount(Bank bank, String accountNum) {
        Account acc = null;
        for (Account account : bank.getBANKACCOUNTS()) {
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                acc = account;
            }
        }
        return acc;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        // Basic info
        // Bank

        // Account number
        // ownerFname
        // ownerLname
        // ownerEmail
        // pin

        ArrayList<Field<String, ?>> arrayOfBasicInfo = new ArrayList<>();

        // ACCOUNTNUMBER
        Field<String, Integer> accountNumber = new Field<String, Integer>("ACCOUNTNUMBER", String.class, 8,
                new Field.StringFieldLengthValidator());
        accountNumber.setFieldValue("Enter Account Number: ", true);
        arrayOfBasicInfo.add(accountNumber);

        // pin
        Field<String, Integer> pin = new Field<String, Integer>("pin", String.class, 4,
                new Field.StringFieldLengthValidator());
        pin.setFieldValue("Enter pin: ", true);
        arrayOfBasicInfo.add(pin);

        // OWNERFNAME
        Field<String, Integer> ownerFName = new Field<String, Integer>("OWNERFNAME", String.class, 3,
                new Field.StringFieldLengthValidator());
        ownerFName.setFieldValue("Enter Owner First Name: ", false);
        arrayOfBasicInfo.add(ownerFName);

        // OWNERLNAME
        Field<String, String> ownerLName = new Field<String, String>("OWNERLNAME", String.class, "",
                new Field.StringFieldValidator());
        ownerLName.setFieldValue("Enter Owner Last Name: ", false);
        arrayOfBasicInfo.add(ownerLName);

        // OWNEREMAIL
        Field<String, String> ownerEmail = new Field<String, String>("OWNEREMAIL", String.class, "",
                new Field.StringFieldValidator());
        ownerEmail.setFieldValue("Enter Owner Email: ", false);
        arrayOfBasicInfo.add(ownerEmail);

        return arrayOfBasicInfo;
    }

    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<String, ?>> arrayOfBasicInfo = this.createNewAccount();
        // Field <Double, Double> creditLimit = new Field<Double,
        // Double>("creditLimit",Double.class,1000.00,new Field.DoubleFieldValidator());
        // creditLimit.setFieldValue("");
        CreditAccount credAcc = new CreditAccount(
                this,
                arrayOfBasicInfo.get(0).getFieldValue(), // ACCOUNTNUMBER
                arrayOfBasicInfo.get(2).getFieldValue(), // OWNERFNAME
                arrayOfBasicInfo.get(3).getFieldValue(), // OWNERLNAME
                arrayOfBasicInfo.get(4).getFieldValue(), // OWNEREMAIL
                arrayOfBasicInfo.get(1).getFieldValue() // pin
        );
        this.addNewAccount(credAcc);
        return credAcc;
    }

    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<String, ?>> arrayOfBasicInfo = this.createNewAccount();
        Field<Double, Double> initialDeposit = new Field<Double, Double>("initialDeposit", Double.class, 1.00,
                new Field.DoubleFieldValidator());
        do {
            initialDeposit.setFieldValue("Enter initial deposit: ");
            if (initialDeposit.getFieldValue() > this.getDepositLimit()) {
                System.out.println("Initial deposit must not be greater than the Banks " + this.getDepositLimit()
                        + " deposit limit.");
            }
        } while (initialDeposit.getFieldValue() > this.getDepositLimit());
        SavingsAccount saveAcc = new SavingsAccount(
                this,
                arrayOfBasicInfo.get(0).getFieldValue(), // ACCOUNTNUMBER
                arrayOfBasicInfo.get(2).getFieldValue(), // OWNERFNAME
                arrayOfBasicInfo.get(3).getFieldValue(), // OWNERLNAME
                arrayOfBasicInfo.get(4).getFieldValue(), // OWNEREMAIL
                arrayOfBasicInfo.get(1).getFieldValue(), // pin
                initialDeposit.getFieldValue());
        this.addNewAccount(saveAcc);
        return saveAcc;
    }

    public void addNewAccount(Account account) {
        if (!accountExists(this, account.getAccountNumber())) {
            this.getBANKACCOUNTS().add(account);
        } else {
            System.out.println("Account with account#" + account.getAccountNumber()
                    + " already exists! \n=====Account Creation Aborted!=====");
        }
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        for (Account account : bank.getBANKACCOUNTS()) {
            if (account.getAccountNumber().equals(accountNum)) {
                return true; // Account exists
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "\n*****************************************\n" +
                        "Bank Details:\n" +
                        "  ID: %d\n" +
                        "  Name: %s\n" +
                        "  Deposit Limit: %.2f\n" +
                        "  Withdrawal Limit: %.2f\n" +
                        "  Credit Limit: %.2f\n" +
                        "  Processing Fee: %.2f\n" +
                        " Passcode: %s" +
                        "\n*****************************************",
                this.getID(),
                this.getName(),
                this.getDepositLimit(),
                this.getWithdrawLimit(),
                this.getCreditLimit(),
                this.getProcessingFee(),
                this.passcode);
    }

    public static class BankComparator implements Comparator<Bank> {
        // A comparator that compares if two bank objects are the same.
        @Override
        public int compare(Bank b1, Bank b2) {
            if (b1.getID() != b2.getID()) {
                return 1;
            }
            if (!b1.getName().equals(b2.getName().strip())) {
                return 1;
            }
            if (!b1.getPasscode().strip().equals(b2.getPasscode().strip())) {
                return 1;
            }
            if (b1.getDepositLimit() != b2.getDepositLimit()) {
                return 1;
            }
            if (b1.getWithdrawLimit() != b2.getWithdrawLimit()) {
                return 1;
            }
            if (b1.getCreditLimit() != b2.getCreditLimit()) {
                return 1;
            }
            return Double.compare(b1.getProcessingFee(), b2.getProcessingFee());
        }
    }

    public static class BankIdComparator implements Comparator<Bank> {
        // A comparator that compares if two bank objects have the same bank id.
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getID(), b2.getID());
        }
    }

    public static class BankCredentialsComparator implements Comparator<Bank> {
        // A comparator that compares if two bank objects have the same set of
        // credentials.
        @Override
        public int compare(Bank b1, Bank b2) {

            if (b1.getName().strip().equalsIgnoreCase(b2.getName().strip())
                    && b1.getPasscode().strip().equals(b2.getPasscode().strip())) {
                return 0; // all credentials are the same (name && passcode)
            }
            return -1; // no same credentials
        }
    }

}
