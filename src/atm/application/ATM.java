/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.application;

/**
 *
 * @author promise
 */
import com.sun.org.apache.bcel.internal.generic.BALOAD;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        //init scanner
        Scanner sc = new Scanner(System.in);

        //init Bank
        Bank theBank = new Bank("Sonali Bank");

        //add user,which also creates a saving account
        User oUser = theBank.addUser("promise", "1234");

        //add a checking account for our user
        Account newAccount = new Account("checking", oUser, theBank);
        oUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            //stay in the login promt until successful login
            curUser = ATM.mainMenuPromt(theBank, sc);

            //stay in main menu until user quits
            ATM.printUserMenu(curUser, sc);

        }
    }

    /**
     *
     * @param theBank
     * @param sc
     * @return
     */
    public static User mainMenuPromt(Bank theBank, Scanner sc) {
        //initis
        String userId;
        String pin;
        User authUser;

        //promt the user for user ID/PIN combo until a correct one is reached
        do {
            System.out.printf("\n\n welcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userId = sc.nextLine();
            System.out.print("Enter Pin:");
            pin = sc.nextLine();

            //try to get the user object corresponding to the ID and the pin combo
            authUser = theBank.userLogin(userId, pin);
            if (authUser == null) {
                System.out.println("incorrect information!please try again.");

            }
        } while (authUser == null);//continue looping until successful login
        return authUser;
    }

    /**
     *
     * @param theUser
     * @param sc
     */
    public static void printUserMenu(User theUser, Scanner sc) {
        //print a summary of user accounts
        theUser.printAccountSummary();

        //init
        int choice;

        //user menu
        do {
            System.out.printf("\n\nWelcome %s.what you like to do?\n",
                    theUser.getName());
            System.out.println(" 1) show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            
            //ensuring for taking only integer as input
            while(!sc.hasNextInt()){
                System.out.println("invalid input. please enter a number");
                sc.nextLine();
            }
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("invalid choice, Please choose 1-5");
            }

        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransHistory(theUser, sc);
                break;
            case 2:
                ATM.withdrawFunds(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFund(theUser, sc);
                break;
            case 5:
                sc.nextLine();
                break;

        }
        //re-display this menu unless the user wants to quit
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }

    }

    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;

        //get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "whose transaction you want to see: ",
                    theUser.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("invalid account.please try again");

            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        //print the transaction history
        theUser.printAcctransHistory(theAcct);
    }

    public static void transferFund(User theUser, Scanner sc) {
        int toAcct;
        int fromAcct;
        double accBal;
        double amount;
        //get the account to transfer from  
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to transfer from: ",
                    theUser.numAccounts());
            fromAcct = sc.nextInt();
            if (fromAcct <= 0 || fromAcct > theUser.numAccounts()) {
                System.out.println("invalid account. please try again");
            }

        } while (fromAcct <= 0 || fromAcct > theUser.numAccounts());

        //get the total amount of this account
        accBal = theUser.getAccountBalance(fromAcct - 1);

        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"
                    + "to transfer to : ",
                    theUser.numAccounts());
            toAcct = sc.nextInt();
            if (toAcct <= 0 || toAcct > theUser.numAccounts()) {
                System.out.println("invalid account. please try again");
            }

        } while (toAcct <= 0 || toAcct > theUser.numAccounts());

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer(max: $%.02f) :", accBal);
            amount = sc.nextDouble();
            if (amount <= 0) {
                System.out.println("amount must be greater than zero");
            } else if (amount > accBal) {
                System.out.printf("amount must not be greater than balance of %.02f", accBal);
            }

        } while (amount <= 0 || amount > accBal);

        //finally do the transfer
        theUser.addAcctransactaion(fromAcct - 1, -1 * amount, String.format("Transfer from account %s", theUser.getAccUUID(toAcct - 1)));
        theUser.addAcctransactaion(toAcct - 1, amount, String.format("Transfer to account %s", theUser.getAccUUID(fromAcct - 1)));

    }

    public static void withdrawFunds(User theUser, Scanner sc) {
        int withdrawAcc;
        double amount;
        double accBal;

        //get the withdraw account 
        do {
            System.out.printf("Enter the number (1-%d) of the account : ", theUser.numAccounts());
            withdrawAcc = sc.nextInt();
            if (withdrawAcc <= 0 || withdrawAcc > theUser.numAccounts()) {
                System.out.println("invalid input. please try again");
            }
        } while (withdrawAcc <= 0 || withdrawAcc > theUser.numAccounts());

        //get total amount of this account
        accBal = theUser.getAccountBalance(withdrawAcc - 1);

        //get the withdraw amount
        do {
            System.out.printf("Enter the withdraw amount max(%.02f): ", accBal);
            amount = sc.nextDouble();
            if (amount <= 0) {
                System.out.println("you can not withdraw amount less than equal zero");
            } else if (amount > accBal) {
                System.out.println("insufficient balance!");
            }
        } while (amount <= 0 || amount > accBal);

        //finally withdrawing from account
        theUser.withdrawAmountFromAcc(withdrawAcc - 1, -amount,
                String.format("withdraw amount %.02f has been succeed", amount));
    }

    public static void depositFunds(User theUser, Scanner sc) {
        int depositAcc;
        double amount;
        //get the deposit account
        do {
            System.out.printf("Enter the number (1-%d) of the account : ", theUser.numAccounts());
            depositAcc = sc.nextInt();
            if (depositAcc <= 0 || depositAcc > theUser.numAccounts()) {
                System.out.println("invalid input");
            }
        } while (depositAcc <= 0 || depositAcc > theUser.numAccounts());

        //get the deposit amount
        do {
            System.out.println("Enter the amount you want to deposit:");
            amount = sc.nextDouble();
            if (amount <= 0) {
                System.out.println("you can not deposit amount less than equal zero");
            }
        } while (amount <= 0);

        //finally adding amount in the account
        theUser.addAmountToAcc(depositAcc - 1, amount);
    }
}
