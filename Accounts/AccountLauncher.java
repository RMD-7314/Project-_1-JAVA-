package Accounts;
import Bank.*;
import Main.Main;
import Main.Field;

public class AccountLauncher {
    private static Account loggedAccount; //Account object of logged account user.
    private static Bank assocBank; // Selected associated bank when attempting to log-in in the account module

    /**
     * Verifies if some account is currently logged in.
     *
     * @return boolean flag if loggedAccount is empty or not
     */
    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }


    /**
     * Login an account. Bank must be selected first before logging in.
     * Account existence will depend on the selected bank.
     */
    public static void accountLogin() throws IllegalAccountType {
        // Implementation of account login logic
        if (BankLauncher.bankSize() < 1){
            System.out.println("There are currently no existing banks in this session!");
            return;
        }
        else{
            assocBank = selectBank();
            if(assocBank != null){
                System.out.println(assocBank);
                if(assocBank.getbAccounts().size() < 1){
                    System.out.println("There are currently no existing accounts in this bank!");
                    return;

                }else{
                    //accountNum
                    Field<String, Integer> accountNum = new Field<String,Integer>("ACCOUNTNUMBER",String.class,0, new Field.StringFieldLengthValidator());
                    accountNum.setFieldValue("Enter Account Number: ");
                    String accountNumFieldValue = accountNum.getFieldValue();
                    // validate the pin should be 4 digits
                    Field<String, Integer> pin = new Field<String,Integer>("PIN",String.class,4, new Field.StringFieldLengthValidator());
                    pin.setFieldValue("Enter Pin: ");

                    Account targeted = checkCredentials(accountNumFieldValue, pin.getFieldValue());
                    if(targeted!=null){
                        setLogSession(targeted);
                        if(targeted instanceof CreditAccount){
                            CreditAccountLauncher.creditAccountInit();
                        }else if(targeted instanceof SavingsAccount){
                            SavingsAccountLauncher.savingsAccountInit();
                        }

                        setLogSession(null);
                    }
                    else{
                        System.out.println("Account Credentials Does Not Match, Invalid Account Number or Pin!");
                    }

                }
            }
            else{
                System.out.println("Bank does not Exists!");
            }
        }


    }


    /**
     * Bank selection screen before the user is prompted to log in.
     * User is prompted for the Bank ID with corresponding bank name.
     *
     * @return Bank object based on selected ID.
     */
    private static Bank selectBank() {
        String bankNAME;
        String bankPasscode;
        BankLauncher.showBanksMenu();
        Field<String, String> bankName = new Field<String, String>("bankName",String.class,"",new Field.StringFieldValidator());
        bankName.setFieldValue("Enter Bank Name: ",false);
        bankNAME = bankName.getFieldValue();
        Field<String, Integer> passcode = new Field<String, Integer>("bankId", String.class,4, new Field.StringFieldLengthValidator());
        passcode.setFieldValue("Enter Bank passcode: ");
        bankPasscode = passcode.getFieldValue();

        Bank tempBank = new Bank(0, bankNAME, bankPasscode, 0.00, 0.00, 0.00, 10.00);
        Bank foundBank = BankLauncher.getBank(new Bank.BankCredentialsComparator(), tempBank);
        return foundBank;
    }



    /**
     * Creates a login session based on the logged user account.
     *
     * @param account Account that has successfully logged in.
     */
    private static void setLogSession(Account account) {
        loggedAccount = account;
    }



    /**
     * Destroys the log session of the previously loggeded user account.
     */
    private static void destroyLogSession() {
        loggedAccount = null;
    }



    /**
     * Checks inputted credentials during account login.
     *
     * @param accountNum - Account number.
     * @param pin - 4-digit pin
     * @return Account object if it passes verification. Null if not
     */
    public static Account checkCredentials(String accountNum, String pin) {
        Account trialAccount = assocBank.getBankAccount(assocBank, accountNum);
        if(trialAccount != null){
            if(trialAccount.getPin().equals(pin)){
                return trialAccount;
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }
    }

    /**
     * Gets the currently logged account.
     *
     * @return Account object representing the currently logged user.
     */
    protected static Account getLoggedAccount() {
        // Getter for the logged account
        return loggedAccount;
    }
}