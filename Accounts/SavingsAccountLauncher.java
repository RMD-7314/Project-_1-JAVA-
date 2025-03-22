package Accounts;
import java.util.Scanner;
import Main.Main;
import Main.Field;
import Bank.*;

public class SavingsAccountLauncher extends AccountLauncher {
    /**
     * A method that deals with all things about savings accounts.
     * Mainly utilized for showing the main menu after Savings Account users log in to the application.
     */
    public static void savingsAccountInit() throws IllegalAccountType {
        //  "Show Balance", "Deposit", "Withdraw", "Fund Transfer",
        //            "Show Transactions", "Logout"
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            Main.showMenu(51);
            Main.setOption();
            switch (Main.getOption()) {
                case 1: // Show Balance
                    System.out.println(getLoggedAccount().getAccountBalanceStatement());
                    break;
                case 2: // Deposit
                    depositProcess();
                    break;
                case 3: // Withdraw
                    withdrawProcess();
                    break;
                case 4: // Fund Transfer
                    fundTransferProcess();
                    break;
                case 5: // Show Transactions
                    System.out.println(getLoggedAccount().getTransactionsInfo());
                    break;
                case 6: // Logout
                    System.out.println("Exiting savings account menu...");
                    isLoggedIn = false;
                    break;
                default:
                    System.out.println("Invalid Option!");
                    break;
            }
        }




//        Scanner scanner = new Scanner(System.in);
//        AccountLauncher accountLauncher = new AccountLauncher();
//
//        //attempt to login if no account is currently login
//        if (loggedAccount == null) {
//            loggedAccount = (SavingsAccount) accountLauncher.checkCredentials();
//        }
//
//        //handles login failure
//        if (loggedAccount == null) {
//            System.out.println("Login unsuccessful. Returning to the main menu...");
//            return;
//        }
//
//        System.out.println("Login successful! Welcome, " + loggedAccount.getOWNERFNAME());
//
//        while (true) {
//            displayMenu();
//
//            try {
//                int choice = scanner.nextInt();
//
//                switch (choice) {
//                    case 1:
//                        depositProcess();
//                        break;
//                    case 2:
//                        withdrawProcess();
//                        break;
//                    case 3:
//                        fundTransferProcess(accountLauncher);
//                        break;
//                    case 4:
//                        System.out.println("Logging out...");
//                        loggedAccount = null;
//                        return;
//                    case 5:
//                        System.out.println("Exiting savings account menu...");
//                        return;
//                    default:
//                        System.out.println("Invalid choice. Please choose again");
//                        break;
//                }
//            } catch (Exception e) {
//                System.out.println("Invalid input. Please enter a number: ");
//                scanner.nextLine();
//            }
//        }
    }

//    private static void displayMenu() {
//        System.out.println("\nSavings Account Menu");
//        System.out.println("1. Deposit");
//        System.out.println("2. Withdraw");
//        System.out.println("3. Fund Transfer");
//        System.out.println("4. Logout");
//        System.out.println("5. Exit");
//
//        System.out.print("Enter your choice: ");
//    }

    /**
     * A method that deals with the deposit process transaction.
     * allows user to deposit any amount they enter as long as the deposit amount is greater than 0 and valid
     */
    private static void depositProcess() {
        Field<Double, Double> amount = new Field<Double, Double>("Amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount to deposit: ");
        double amountFieldValue = amount.getFieldValue();

        if(getLoggedAccount().cashDeposit(amountFieldValue)){
            System.out.println("Deposited successfully.");
            System.out.println("New balance: " + getLoggedAccount().getAccountBalanceStatement());
        }
        else{
            System.out.println("Deposit failed. Deposit amount must not be greater than Bank Deposit Limit.");
        }
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter amount to deposit: ");
//        try {
//            double amount = scanner.nextDouble();
//
//            //validate deposit amount
//            if (amount <= 0) {
//                System.out.println("Deposit amount must be greater than zero.");
//                return;
//            }
//
//
//            if (loggedAccount.cashDeposit(amount)) {
//                System.out.println("Deposited successfully.");
//                System.out.println("New balance: " + loggedAccount.getAccountBalanceStatement());
//            } else {
//                System.out.println("Deposit failed. Try again");
//            }
//        } catch (Exception e) {
//            System.out.println("Invalid input. Please enter a valid amount.");
//            scanner.nextLine();
//        }
    }


    /**
     * A method that deals with the withdrawal process transaction.
     */
    private static void withdrawProcess() {
        Field<Double, Double> amount = new Field<Double, Double>("Amount", Double.class, 0.0, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter amount to withdraw: ");
        double amountFieldValue = amount.getFieldValue();

        if(getLoggedAccount().withdrawal(amountFieldValue)){
            System.out.println("Withdrew successfully.");
            System.out.println("Remaining balance: " + getLoggedAccount().getAccountBalanceStatement());
        }
        else{
            System.out.println("Insufficient balance. Withdrawal failed");
        }
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Enter amount to withdraw: ");
//        try {
//            double amount = scanner.nextDouble();
//
//            if (amount <= 0) {
//                System.out.println("Withdrawal must be greater than zero.");
//            }
//
//            if (loggedAccount.withdrawal(amount)) {
//                System.out.println("Withdrew successfully.");
//                System.out.println("Remaining balance: " + loggedAccount.getAccountBalanceStatement());
//            } else {
//                System.out.println("Insufficient balance. Withdrawal failed");
//            }
//        } catch (Exception e) {
//            System.out.println("Invalid input. Please input a valid amount.");
//            scanner.nextLine();
//        }
    }

    /**
     * A method that deals with the fund transfer process transaction.
     */
    private static void fundTransferProcess() throws IllegalAccountType {
        boolean isFundTransfer = true;
        while(isFundTransfer){
            System.out.println("[1] Internal Fund Transfer\n[2] External Fund Transfer\n[3] Go Back");
            String choice = Main.prompt("Select an option: ", true);
            switch (choice) {
                case "1":
                    Field<String, Integer> accountNumber = new Field<String,Integer>("ACCOUNTNUMBER",String.class,0, new Field.StringFieldLengthValidator());
                    accountNumber.setFieldValue("Enter Account Number of account to transfer funds to: ");
                    String accountNum = accountNumber.getFieldValue();
                    Field <Double, Double> amount = new Field<Double, Double>("Amount", Double.class, 0.00, new Field.DoubleFieldValidator());
                    amount.setFieldValue("Enter Amount: ");
                    double amountFieldValue = amount.getFieldValue();
                    Account targetAccount = getLoggedAccount().getBANK().getBankAccount(getLoggedAccount().getBANK(), accountNum);

                    if(targetAccount != null){
                        if(getLoggedAccount().transfer(targetAccount, amountFieldValue)){
                            System.out.println("Internal Fund Transfer Successful!");
                            System.out.println("Transferred amount: " + amountFieldValue);
                            System.out.println("New balance: " + getLoggedAccount().getAccountBalanceStatement());
                        }
                        else{
                            System.out.println("Internal Fund Transfer Failed, Must be within the same bank and savings account and have sufficient balance.");
                        }
                    }
                    else{
                        System.out.println("Account not found.");
                    }
                    break;
                case "2":
                    //Enter Bank Name
                    Field<String, String> bankName = new Field<String, String>("bankName",String.class,"",new Field.StringFieldValidator());
                    bankName.setFieldValue("Enter Bank Name: ");
                    //Enter Bank ID
                    Field<Integer, Integer> bankId = new Field<Integer, Integer>("bankId", Integer.class,0, new Field.IntegerFieldValidator());
                    bankId.setFieldValue("Enter Bank ID: ");
                    // Enter Account Number of target account for fund transfer
                    Field<String, Integer> accountNumber2 = new Field<String,Integer>("ACCOUNTNUMBER",String.class,0, new Field.StringFieldLengthValidator());
                    accountNumber2.setFieldValue("Enter Account Number of account to transfer funds to: ");
                    // Enter Amount
                    Field <Double, Double> amount2 = new Field<Double, Double>("Amount", Double.class, 0.00, new Field.DoubleFieldValidator());
                    amount2.setFieldValue("Enter Amount: ");
                    // Get target bank
                    Bank targetBank = new Bank(bankId.getFieldValue(), bankName.getFieldValue(), "", 0.00, 0.00, 0.00, 10.00);
                    Bank foundBank = BankLauncher.getBank(new Bank.BankCredentialsComparator(), targetBank);
                    if(foundBank != null){
                        Account targeted = foundBank.getBankAccount(foundBank, accountNumber2.getFieldValue());
                        if(targeted != null) {
                            if (getLoggedAccount().transfer(foundBank, targeted, amount2.getFieldValue())) {
                                System.out.println("External Fund Transfer Successful!");
                                System.out.println("Transferred amount: " + amount2.getFieldValue());
                                System.out.println("New balance: " + getLoggedAccount().getAccountBalanceStatement());
                            }
                            else {
                                System.out.println("External Fund Transfer Failed, Must have sufficient balance and must be Savings Account.");
                            }
                        }
                    }
                    else{
                        System.out.println("Bank not found.");
                        break;
                    }
                    break;
                case "3":
                    isFundTransfer = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

//        Scanner scanner = new Scanner(System.in);
//
//        try {
//            System.out.println("Enter recipient account number: ");
//            String recipientNumber = scanner.next();
//
//            //verifies recipient account
//            recipientAccount = (SavingsAccount) accountLauncher.checkCredentials();
//            if (recipientAccount == null || !recipientAccount.getACCOUNTNUMBER().equals(recipientNumber)) {
//                System.out.println("Recipient account or account number not found.");
//                return;
//            }
//
//            System.out.println("Enter amount to transfer: ");
//            double amount = scanner.nextDouble();
//
//            if (amount <= 0) {
//                System.out.println("Transfer amount must be greater than zero.");
//                return;
//            }
//
//            double processingFee = loggedAccount.getBANK().getProcessingFee();
//            double totalamount = amount - processingFee;
//
//            if (loggedAccount.transfer(loggedAccount.getBANK(), recipientAccount, totalamount)) {
//                System.out.println("Transfer successful.");
//                System.out.println("Transferred amount: " + totalamount);
//                System.out.println("Processing fee: " + processingFee);
//                System.out.println("New balance: " + loggedAccount.getAccountBalanceStatement());
//            } else {
//                System.out.println("Transfer failed.");
//            }
//        } catch (IllegalAccountType e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Invalid input. Please enter a valid amount.");
//            scanner.nextLine();
//        }
    }

    /**
     * Get the Savings Account instance of the currently logged in account.
     *
     * @return null if no account is currently logged in
     */

    protected static SavingsAccount getLoggedAccount() {
        return (SavingsAccount) AccountLauncher.getLoggedAccount();
    }
}

