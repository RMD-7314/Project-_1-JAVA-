package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;
import Main.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


//Initial Banklauncher by Rhimar

/**
 * BankLauncher is for managing bank sessions and operations
 * It allows users to log into a bank view and create accounts and log out.
 * The class maintains a static list of all banks and manages the currently logged-in bank
 */
public class BankLauncher {
    // Attributes
    private static final ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    // Methods
    public static boolean isLogged() {
        return loggedBank != null;
    }


    /**
     * Checks if the currently logged-in bank is null or not. If null, the user is not logged in.
     * @return true if the user is logged in, false otherwise.
     */
    public static void bankInit() {
        System.out.println("Bank initialized. \n***Logged in as: " + loggedBank.getName() + "***\n");
        // You can add more initialization logic here.
        while(isLogged()){
            Main.showMenu(31, 1);
            int opt = 0;
            try{
                opt = Integer.parseInt(Main.prompt("Enter Option: ", true));
            }catch(Exception e){
                System.out.println("Invalid Option!");
            }
            System.out.println(opt);
            switch(opt){
                case 1:
                    showAccounts();
                    break;
                case 2:
                    newAccounts();
                    break;
                case 3:
                    logOut();
                    break;
                default:
                    System.out.println("Invalid Option!");
            }
        }
    }

    /**
     * Displays all the accounts of the current bank session.
     */
    private static void showAccounts() {
        boolean isShowingAccounts = true;
        while(isShowingAccounts){
            Main.showMenu(32,1);
            Main.setOption();
            int opt = Main.getOption();
            System.out.println(opt);
            switch(opt){
                case 1:
                    loggedBank.showAccounts(CreditAccount.class);
                    break;
                case 2:
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;
                case 3:
                    loggedBank.showAccounts(Account.class);
                    break;
                case 4:
                    isShowingAccounts = false;
                    break;
                default:
                    System.out.println("Invalid Option!");
                    break;
            }
        }
    }

    /**
     * Displays all the accounts of the current bank session.
     */
    private static void newAccounts() {
        Main.showMenu(33,1);
        Main.setOption();
        if(Main.getOption()==1){
            loggedBank.createNewCreditAccount();
        }
        else if(Main.getOption()==2){
            loggedBank.createNewSavingsAccount();
        }
        else{
            System.out.println("Invalid Option!");
        }
    }

    /**
     * Creates a new account for the current bank.
     * @param,type The type of account to create.
     * @param,name The name of the account holder.
     * @param,balance The initial balance of the account.
     */
    public static void bankLogin() {
        //Name
        Field<String, String> name = new Field<String, String>("name",String.class,  "", new Field.StringFieldValidator());
        name.setFieldValue("Enter Name: ", false);

        //Passcode
        Field<String, Integer> passcode = new Field<String, Integer>("passcode",String.class,  4, new Field.StringFieldLengthValidator());
        passcode.setFieldValue("Enter passcode: ");

        Bank tempBank = new Bank(0,name.getFieldValue(),passcode.getFieldValue(),0.0,0.0,0.0,0.0);
        Bank foundBank = getBank(new Bank.BankCredentialsComparator(), tempBank);


        if(foundBank != null){
            setLogSession(foundBank);
            bankInit();
        }
        else{
            System.out.println("Bank does not exist!");
        }
    }

    /**
     * Handles the flow for bank login asking for id, name and passcode, to verify
     * the credentials then If the credentials are valid the program will enter the bank session and
     * will not exit until the user chooses to log out
     */
    private static void setLogSession(Bank b) {
        loggedBank = b;
    }

    /**
     * Logs out the currently logged-in bank by setting the loggedBank to null.
     * This will end the current bank session
     */
    private static void logOut() {
        setLogSession(null);
    }

    public static void createNewBank() {
        //BANK DETAILS
        //ID
        //name
        //passcode
        //DEPOSITLIMIT
        //WITHDRAWLIMIT
        //CREDITLIMIT
        //processingFee


        //Name
        Field<String, String> name = new Field<String, String>("name",String.class,  "", new Field.StringFieldValidator());
        name.setFieldValue("Enter Name: ",false);
        //Passcode
        Field<String, Integer> passcode = new Field<String, Integer>("id",String.class,  4, new Field.StringFieldLengthValidator());
        passcode.setFieldValue("Enter passcode: ");
        //DEPOSITLIMIT
        Field<Double, Double> DEPOSITLIMIT = new Field<Double, Double>("DEPOSITLIMIT",Double.class,  0.00, new Field.DoubleFieldValidator());
        DEPOSITLIMIT.setFieldValue("Enter DEPOSITLIMIT: ");

        //WITHDRAWLIMIT
        Field<Double, Double> WITHDRAWLIMIT = new Field<Double, Double>("WITHDRAWLIMIT",Double.class,  0.00, new Field.DoubleFieldValidator());
        WITHDRAWLIMIT.setFieldValue("Enter WITHDRAWLIMIT: ");

        //CREDITLIMIT
        Field<Double, Double> CREDITLIMIT = new Field<Double, Double>("CREDITLIMIT",Double.class,  0.00, new Field.DoubleFieldValidator());
        CREDITLIMIT.setFieldValue("Enter CREDITLIMIT: ");
        //processingFee
        Field<Double, Double> processingFee = new Field<Double, Double>("processingFee",Double.class,  1.00, new Field.DoubleFieldValidator());
        processingFee.setFieldValue("Enter processingFee: ");

        int bankID = bankSize();


        Bank newBank = new Bank(bankID,name.getFieldValue(),passcode.getFieldValue(),DEPOSITLIMIT.getFieldValue(),WITHDRAWLIMIT.getFieldValue(),CREDITLIMIT.getFieldValue(),processingFee.getFieldValue());
        addBank(newBank);
    }

    /**
     * Displays a menu of the available banks.
     * The menu is printed to the console and contains all the banks in the
     * BANKS list.
     */
    public static void showBanksMenu() {
        ArrayList<String> menu = new ArrayList<String>();
        int inlineTexts = 3;
        String space = inlineTexts == 0 ? "" : "%-20s";
        System.out.println("Available banks:");
        String fmt = "[%d] " + space;
        for(Bank bank: BANKS){
            menu.add(bank.getName());
        }
        int count = 0;
        for (String s : menu)
        {
            count++;
            System.out.printf(fmt, count, s);
            if (count % inlineTexts == 0)
            {
                System.out.println();
            }
        }
        System.out.print("\n");
    }

    /**
     * Add a bank to the array of banks The bank is added to the list without any
     * validation assuming that the bank is valid
     *
     * @param bank The bank to add.
     */
    private static void addBank(Bank bank) {
        BANKS.add(bank);
        System.out.println("Bank added: " + bank);
    }


    /**
     * Check if a bank exists based on some criteria
     *
     * @param comparator Criteria for searching.
     * @param bank Bank object to be compared.
     * @return Bank object if it passes the criteria. Null if none.
     */
    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        for (Bank bank1 : BANKS) {
            if (comparator.compare(bank1, bank) == 0 || comparator.compare(bank1,bank) == 2) {
                return bank1;
            }
        }
        return null;
    }


    /**
     * Finds the account based on some account number on all registered banks.
     * @param accountNum account number of target Account
     * @return Account object if it exists. Null if not found.
     */
    public static Account findAccount(String accountNum) {
        for(Bank bank: BANKS){
            Account acc = bank.getBankAccount(bank,accountNum);
            if (acc!=null){
                return acc;
            }
        }
        return null;
    }

    // Method to get the size of the BANKS list
    public static int bankSize() {
        return BANKS.size();
    }
}