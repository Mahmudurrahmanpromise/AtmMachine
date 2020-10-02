/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.application;

import java.util.Date;

/**
 *
 * @author promise
 */
public class Transaction {
    /**
     * the amount of this transaction
     */
    private double amount;
    /**
     * time and date of this transaction
     */
    private Date timestamp;
    /**
     * A memo for this transaction
     */
    private String memo;
    /**
     * the account in which the transaction was performed
     */
    private Account inAccount;
    
    /**
     * create a new Transaction
     * @param amount the amount transacted
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount,Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    /**
     * create a new Transaction
     * @param amount the amount transacted
     * @param memo the memo for the transaction
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount,Account inAccount,String memo){
        this(amount, inAccount);
        
        //set the memo
        this.memo = memo;
    }
    /**
     * Get the amount of the transaction
     * @return the amount
     */
    public double getAmount(){
        return this.amount;
    }
    
    /**
     * get the summary line for each transaction
     * @return The summary line 
     */
    public String getSummaryLine(){
        
        if(this.amount>=0){
            return String.format("%s : $%.02f : %s",
                    this.timestamp.toString(),this.amount,this.memo);
        }
        else{
            return String.format("%s : $(%.02f) : %s",
                    this.timestamp.toString(),this.amount,this.memo);
        }
    }
    
   
}


































