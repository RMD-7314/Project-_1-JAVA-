package Accounts;

import Bank.BankLauncher;
import Main.Main;
import Main.Field;

public class CreditAccountLauncher extends AccountLauncher {

    /**
     * Method that deals with all things about credit accounts.
     * Mainly utilized for showing the main menu after Credit Account users log in to the application.
     */
    public static void creditAccountInit() throws IllegalAccountType {
        boolean isInCreditAccount = true;
        while (isInCreditAccount) {
            Main.showMenu(41); // Show the credit account menu
            Main.setOption();
            switch (Main.getOption()) {
                case 1:
                    // Show credits
                    showCreditDetails();
                    break;
                case 2:
                    // Process credit payment
                    creditPaymentProcess();
                    break;
                case 3:
                    // Process credit recompense
                    creditRecompenseProcess();
                    break;
                case 4:
                    // Show transaction history
                    System.out.println(getLoggedAccount().getTransactionsInfo());
                    break;
                case 5:
                    // Logout
                    System.out.println("Logging out...");
                    isInCreditAccount = false;
                    break;
                default:
                    System.out.println("Invalid Option!");
                    break;
            }
        }
    }

    /**
     * Displays the credit details, including available credits, credit limit, and transaction history.
     */
    private static void showCreditDetails() {
        char currencyUsed = '$';
        String loanStatement = getLoggedAccount().getLoanStatement();
        double loanAmount = Double.parseDouble(loanStatement.substring(loanStatement.indexOf(currencyUsed) + 1));
        double availableCredits = getLoggedAccount().getBank().getCreditLimit() - loanAmount;

        System.out.println("Available Credits: " + availableCredits);
        System.out.println("Credit Limit: " + getLoggedAccount().getBank().getCreditLimit());
        System.out.println("Transactions using Credit: \n" + getLoggedAccount().getTransactionsInfo());
    }

    /**
     * Processes the credit payment transaction.
     */
    private static void creditPaymentProcess() throws IllegalAccountType {
        Field<String, Integer> accountNumber = new Field<>("Account Number", String.class, 0, new Field.StringFieldLengthValidator());
        accountNumber.setFieldValue("Enter Account Number: ");
        String accountNum = accountNumber.getFieldValue();

        Account targetAccount = BankLauncher.findAccount(accountNum);
        if (targetAccount == null) {
            System.out.println("Account not found!");
            return;
        }

        Field<Double, Double> amount = new Field<>("Amount", Double.class, 0.00, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter Amount: ");
        double paymentAmount = amount.getFieldValue();

        if (getLoggedAccount().pay(targetAccount, paymentAmount)) {
            System.out.println("Payment Successful!");
        } else {
            System.out.println("Payment Failed! Account must be of Savings Account type.");
        }
    }

    /**
     * Processes the credit recompense transaction.
     */
    private static void creditRecompenseProcess() {
        Field<Double, Double> amount = new Field<>("Amount", Double.class, 0.00, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter Amount: ");
        double recompenseAmount = amount.getFieldValue();

        if (getLoggedAccount().recompense(recompenseAmount)) {
            System.out.println("Recompense Successful!");
        } else {
            System.out.println("Recompense Failed! Amount must not exceed the current loan.");
        }
    }

    /**
     * Gets the currently logged-in CreditAccount.
     *
     * @return The logged-in CreditAccount object.
     */
    protected static CreditAccount getLoggedAccount() {
        return (CreditAccount) AccountLauncher.getLoggedAccount();
    }
}
