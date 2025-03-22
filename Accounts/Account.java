    package Accounts;
    import Bank.Bank;
    import java.util.ArrayList;

    public abstract class Account {
        private final Bank BANK;
        private final String ACCOUNTNUMBER;
        private final String OWNERFNAME,OWNERLNAME,OWNEREMAIL;
        private String pin;
        private final ArrayList<Transaction> TRANSACTIONS;

        public Account(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME,String OWNERLNAME,String OWNEREMAIL, String pin) {
            this.BANK = bank;
            this.ACCOUNTNUMBER = ACCOUNTNUMBER;
            this.OWNERFNAME = OWNERFNAME;
            this.OWNERLNAME = OWNERLNAME;
            this.OWNEREMAIL = OWNEREMAIL;
            this.pin = pin;
            this.TRANSACTIONS = new ArrayList<>();
        }

        public Bank getBANK() {
            return this.BANK;
        }

        public String getACCOUNTNUMBER() {
            return this.ACCOUNTNUMBER;
        }

        public String getOWNERFNAME() {
            return this.OWNERFNAME;
        }

        public String getOWNERLNAME() {
            return this.OWNERLNAME;
        }

        public String getOwnerEmail() {
            return this.OWNEREMAIL;
        }

        public String getPin() {
            return this.pin;
        }

        public ArrayList<Transaction> getTRANSACTIONS() {
            return this.TRANSACTIONS;
        }

        protected String transactionDescription(Account accountSender, Account accountReceiver, double amount){
            // Which account money was transferred from?
            // amount of money?
            // which bank? (bank name) the recipient account was registered from
            // same whether external or internal fund transfer
            return String.format("Sender: " + accountReceiver.getACCOUNTNUMBER() + " " +
                    "Amount: " + amount + " " +
                    "Recipient Bank: " + accountReceiver.getBANK().getName() +
                    ""
                    );
        }



        public String getOwnerFullName(){
            return this.getOWNERFNAME() + " " + this.getOWNERLNAME();
        }
        public void addNewTransaction(String AccountNum, Transaction.Transactions transactionType, String transactionDescription){
            Transaction newTransaction = new Transaction(AccountNum, transactionType, transactionDescription);
            TRANSACTIONS.add(newTransaction);
        }

        public String getTransactionsInfo(){
            StringBuilder transactionsInfo = new StringBuilder();
            if (TRANSACTIONS.isEmpty()) {
                transactionsInfo.append("No transactions found.\n");
            } else {
                for (Transaction transaction : TRANSACTIONS) {
                    transactionsInfo.append("===Type: ").append(transaction.transactionType).append("===");
                    transactionsInfo.append("===Description: ").append(transaction.description).append("===\n");
                }
            }
            return transactionsInfo.toString();
        }

        public String toString()
        {
            /**Account Number;
             * Account Name;
             * Email;
             */
            return String.format(
                            "  Account Number: %s\n" +
                            "  Account Name: %s\n" +
                            "  Email: %s\n"
                            ,
                    this.getACCOUNTNUMBER(),
                    this.getOwnerFullName(),
                    this.getOwnerEmail()
            );
        }

    }
