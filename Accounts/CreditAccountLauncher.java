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
        // "Show Credits", "Pay", "Recompense", "Show Transactions", "Logout"
        boolean isInCreditAccount = true;
        while(isInCreditAccount){
            Main.showMenu(41);
            Main.setOption();
            switch(Main.getOption()){
                case 1:
                    // show credits
                    char currencyUsed = '$';
                    double loanAmount = Double.parseDouble( getLoggedAccount().getLoanStatement().substring(getLoggedAccount().getLoanStatement().indexOf(currencyUsed)+1, getLoggedAccount().getLoanStatement().length()));
                    System.out.println("Available Credits: " + (getLoggedAccount().getBANK().getCreditLimit() - loanAmount));
                    System.out.println("Credit Limit: " + getLoggedAccount().getBANK().getCreditLimit());
                    System.out.println("Transactions using Credit: \n" + getLoggedAccount().getTransactionsInfo());
                    break;
                case 2:
                    creditPaymentProcess(); // process the credit payment transaction
                    break;
                case 3:
                    creditRecompenseProcess(); // process the credit recompense transaction
                    break;
                case 4:
                    System.out.println(getLoggedAccount().getTransactionsInfo());
                    break;
                case 5:
                    System.out.println("Logging out...");
                    isInCreditAccount = false;
                    break;
                default:
                    System.out.println("Invalid Option!");
                    break;
            }
        }


    //closing part of the method
    }

    /**
     * Method that is utilized to process the credit payment transaction.
     */
    private static void creditPaymentProcess() throws IllegalAccountType {
        Field<String, Integer> accountNumber = new Field<String,Integer>("ACCOUNTNUMBER",String.class,0, new Field.StringFieldLengthValidator());
        accountNumber.setFieldValue("Enter Account Number: ");
        String accountNum = accountNumber.getFieldValue();
        Account acc = BankLauncher.findAccount(accountNum);
        Field<Integer, Integer> amount = new Field<Integer, Integer>("Amount", Integer.class, 0, new Field.IntegerFieldValidator());
        amount.setFieldValue("Enter Amount: ");
        double amountFieldValue = amount.getFieldValue();

        if(getLoggedAccount().pay(acc, amountFieldValue) == true){
            System.out.println("Payment Successful!");
        }
        else{
            System.out.println("Payment Failed! Account Must be of Savings Account type");
        }

    }

    /**
     * Method that is utilized to process the credit compensation transaction.
     */
    private static void creditRecompenseProcess() {
        Field<Double, Double> amount = new Field<Double, Double>("Amount", Double.class, 0.00, new Field.DoubleFieldValidator());
        amount.setFieldValue("Enter Amount: ");
        getLoggedAccount().recompense(amount.getFieldValue());
    }

    protected static CreditAccount getLoggedAccount(){
        return (CreditAccount)AccountLauncher.getLoggedAccount();
    }
}
