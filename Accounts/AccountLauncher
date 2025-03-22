package Accounts;

import Bank.Bank;
import Bank.BankLauncher;
import Main.Field;
import Main.Main;

public class AccountLauncher {
    private static Account loggedAccount; // Account object of the logged-in user
    private static Bank associatedBank; // Selected associated bank when attempting to log in

    /**
     * Verifies if an account is currently logged in.
     *
     * @return boolean flag indicating whether an account is logged in
     */
    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    /**
     * Logs in an account. A bank must be selected first before logging in.
     * Account existence depends on the selected bank.
     */
    public static void accountLogin() throws IllegalAccountType {
        if (BankLauncher.getBankCount() < 1) {
            System.out.println("There are currently no existing banks in this session!");
            return;
        } else {
            associatedBank = selectBank();
            if (associatedBank != null) {
                System.out.println(associatedBank);
                if (associatedBank.getAccounts().size() < 1) {
                    System.out.println("There are currently no existing accounts in this bank!");
                    return;
                } else {
                    // Prompt for account number
                    Field<String, Integer> accountNumber = new Field<>("Account Number", String.class, 0, new Field.StringFieldLengthValidator());
                    accountNumber.setFieldValue("Enter Account Number: ");
                    String accountNumberValue = accountNumber.getFieldValue();

                    // Prompt for PIN (should be 4 digits)
                    Field<String, Integer> pin = new Field<>("PIN", String.class, 4, new Field.StringFieldLengthValidator());
                    pin.setFieldValue("Enter PIN: ");

                    // Check credentials
                    Account targetAccount = checkCredentials(accountNumberValue, pin.getFieldValue());
                    if (targetAccount != null) {
                        setLogSession(targetAccount);
                        if (targetAccount instanceof CreditAccount) {
                            CreditAccountLauncher.creditAccountInit();
                        } else if (targetAccount instanceof SavingsAccount) {
                            SavingsAccountLauncher.savingsAccountInit();
                        }
                        destroyLogSession(); // End session after logout
                    } else {
                        System.out.println("Account credentials do not match. Invalid account number or PIN!");
                    }
                }
            } else {
                System.out.println("Bank does not exist!");
            }
        }
    }

    /**
     * Bank selection screen before the user is prompted to log in.
     * User is prompted for the bank name and passcode.
     *
     * @return Bank object based on the selected name and passcode.
     */
    private static Bank selectBank() {
        BankLauncher.displayBanks(); // Show available banks
        Field<String, String> bankName = new Field<>("Bank Name", String.class, "", new Field.StringFieldValidator());
        bankName.setFieldValue("Enter Bank Name: ", false);
        String selectedBankName = bankName.getFieldValue();

        Field<String, Integer> passcode = new Field<>("Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        passcode.setFieldValue("Enter Bank Passcode: ");
        String selectedPasscode = passcode.getFieldValue();

        // Create a temporary bank object for comparison
        Bank tempBank = new Bank(0, selectedBankName, selectedPasscode, 0.00, 0.00, 0.00, 10.00);
        return BankLauncher.findBank(new Bank.BankCredentialsComparator(), tempBank);
    }

    /**
     * Creates a login session based on the logged-in account.
     *
     * @param account Account that has successfully logged in.
     */
    private static void setLogSession(Account account) {
        loggedAccount = account;
    }

    /**
     * Destroys the login session of the previously logged-in account.
     */
    private static void destroyLogSession() {
        loggedAccount = null;
    }

    /**
     * Checks inputted credentials during account login.
     *
     * @param accountNumber Account number.
     * @param pin 4-digit PIN.
     * @return Account object if credentials are valid, null otherwise.
     */
    public static Account checkCredentials(String accountNumber, String pin) {
        Account trialAccount = associatedBank.findAccount(accountNumber);
        if (trialAccount != null) {
            if (trialAccount.getPin().equals(pin)) {
                return trialAccount;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Gets the currently logged-in account.
     *
     * @return Account object representing the currently logged-in user.
     */
    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}