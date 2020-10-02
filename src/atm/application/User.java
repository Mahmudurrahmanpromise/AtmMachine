/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author promise
 */
public class User {

    /**
     * The username
     */
    private String name;
    /**
     * applying MD5 hash of the users pin number
     */
    private byte[] pinHash;
    /**
     * The ID number of user.
     */
    private String uuid;
    /**
     * list of accounts of this user.
     */
    private ArrayList<Account> accounts;

    /**
     * create a new user
     *
     * @param name username
     * @param pin user's account pin number
     * @param theBank the Bank object that the user is a customer of
     */
    public User(String name, String pin, Bank theBank) {
        //set user's name
        this.name = name;

        //store the pin's MD5 hash rather than avoiding store the original value
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error,caught NoSuchAlgorithm");
            ex.printStackTrace();
            System.exit(1);
        }

        //get a new universal unique identifier for the user.
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<>();

        //print log message
        System.out.printf("new user %s with ID %s is created.\n", name, this.uuid);

    }

    /**
     * Add an account for the user
     *
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    /**
     * get the uuid of user
     *
     * @return user uuid
     */
    public String getUUID() {
        return this.uuid;
    }

    /**
     * check whether a given pin matches the true User pin
     *
     * @param oPin the pin to check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String oPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(oPin.getBytes()),
                    this.pinHash);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error,caught NoSuchAlgorithm");
            ex.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    /**
     * Get the name of user
     *
     * @return the name of user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Print summaries for the accounts of this user
     */
    public void printAccountSummary() {
        System.out.printf("\n\n %s's account's summary\n", this.name);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a + 1,
                    this.accounts.get(a).getSummaryLine());
        }
    }

    /**
     * Get the number of accounts of the user
     *
     * @return the number of accounts
     */
    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * print transaction history for a particular account
     *
     * @param accIdx the index of the account to use
     */
    public void printAcctransHistory(int accIdx) {
        this.accounts.get(accIdx).printTranHistory();
    }

    /**
     * get balance of a particular bank account
     *
     * @param accIdx the index of the account to use
     * @return the balance of the account
     */
    public double getAccountBalance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();
    }

    /**
     * Get the UUID for specific account 
     * @param accIdx the index of the account
     * @return the UUID of the account
     */
    public String getAccUUID(int accIdx) {
        return this.accounts.get(accIdx).getUUID();
    }
    
    /**
     * Add a transaction to a particular account
     * @param accIdx the index of the account
     * @param amount the amount of the transaction
     * @param memo  the memo of the transaction
     */
    public void addAcctransactaion(int accIdx, double amount,String memo){
        this.accounts.get(accIdx).addTransaction(amount,memo);
    }
    
    /**
     * Adding money to the user specific account
     * @param accIdx the index of the account
     * @param amount the amount of money
     */
    public void addAmountToAcc(int accIdx,double amount){
        this.accounts.get(accIdx).addAmount(amount);
    }
    
    /**
     * Withdraw amount from the user account
     * @param accIdx index of the account
     * @param amount the withdraw amount
     * @param memo the memo
     */
    public void withdrawAmountFromAcc(int accIdx,double amount,String memo){
        this.accounts.get(accIdx).withdrawAmount(amount,memo);
    }
    
    
}
