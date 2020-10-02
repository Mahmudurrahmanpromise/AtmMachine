/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.application;

import java.util.ArrayList;

/**
 *
 * @author promise
 */
public class Account {
    /**
     * the name of the account
     */
    private String name;
    /**
     * account ID number
     */
    private String uuid;
    /**
     * The user object who owns this account;
     */
    private User holder;
    /**
     * the list of transaction for this account.
     */
    private ArrayList<Transaction> transactions;
    
    /**
     * create a Account
     * @param name  the name of the account
     * @param holder the user object that hold this account
     * @param theBank  the bank that issues this account
     */
    public Account(String name,User holder,Bank theBank){
        //set the account name and holder
        this.name = name;
        this.holder = holder;
        
        //get new account UUID
        this.uuid = theBank.getNewAccountUUID();
        
        //init transaction
        this.transactions = new ArrayList<>();

    }
    
    /**
     * get the uuid of the account
     * @return account uuid
     */
    public String getUUID(){
        return this.uuid;
    }
    
    /**
     * Get the summary line for the account
     * @return the string summary
     */
    public String getSummaryLine(){
        //get the account's balance
        double balance = this.getBalance();
        
        //format the summary line,depending on whether the balance
        //is negative
        if(balance >= 0){
            return String.format("%s : $%.02f :%s", this.uuid,
                    balance,this.name);
        }else {
            return String.format("%s : $(%.02f) :%s", this.uuid,
                    balance,this.name);
        }
    }
    /**
     * get the balance of this account by adding the amounts of the transactions
     * @return the balance value
     */
    public double getBalance(){
        double balance = 0;
        for(Transaction t: this.transactions){
            balance+=t.getAmount();
        }
        return balance;
    }
    
    /**
     * printing all transaction history of a particular account
     */
    public void printTranHistory(){
        System.out.printf("\nTransaction history for account %s\n",
                this.uuid);
        if(this.transactions.isEmpty()) System.out.printf("no transaction occured yet in account %s",this.getUUID());
        for(int t = this.transactions.size()-1;t>=0;t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
    
    /**
     * Add a new transaction to this account
     * @param amount the amount transacted
     * @param memo the transaction memo
     */
    public void addTransaction(double amount,String memo){
        //create new transaction and add it to our list
        Transaction newTransaction = new Transaction(amount, this,memo);
        this.transactions.add(newTransaction);
        
    }
    
    /**
     * adding money to this account
     * @param amount the amount of money to be added
     */
    public void addAmount(double amount){
        Transaction newTransaction = new Transaction(amount, this);
        this.transactions.add(newTransaction);
    }
    
    /**
     * withdraw amount from this account
     * @param amount
     * @param memo 
     */
    public void withdrawAmount(double amount,String memo){
        Transaction newTransaction = new Transaction(amount, this, memo);
        this.transactions.add(newTransaction);
    }
    
    
    
}
