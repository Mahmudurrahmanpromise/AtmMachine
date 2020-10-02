/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atm.application;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author promise
 */
public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;
    /**
     * create a new Bank object with empty lists of users and accounts
     * @param name the name of the bank
     */
    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }
    
    /**
     * generate a new universal unique ID for a user
     *
     * @return the uuid
     */
    public String getNewUserUUID() {
        //inits
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;

        //continuing looping until we get a unique ID
        do {
            //generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += random.nextInt(10);
            }

            //check to make sure its unique
            nonUnique = false;
            for (User user : this.users) {
                if (uuid.compareTo(user.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * generate a new universal unique ID for an account
     *
     * @return the uuid
     */
    public String getNewAccountUUID() {
        //inits
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;

        //continuing looping until we get a unique ID
        do {
            //generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += random.nextInt(10);
            }

            //check to make sure its unique
            nonUnique = false;
            for (Account account : this.accounts) {
                if (uuid.compareTo(account.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * add an account
     *
     * @param anAcct the account to add
     */
    public void addAccount(Account anAcct) {
        this.accounts.add(anAcct);
    }

    public User addUser(String name, String pin) {
        //create a new User object and add it to our list
        User newUser = new User(name, pin, this);
        this.users.add(newUser);

        //create a savings account for the user
        //and add to User and Bank accounts list
        Account newAccount = new Account("savings", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     *
     * @param userID
     * @param pin
     * @return
     */
    public User userLogin(String userID, String pin) {
        //search through list of users
        for (User u : this.users) {
            //check user ID is correct
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }

        }
        //if we haven't found the user or hava an incorrect pin
        return null;

    }
    
    /**
     * Get the name of the Bank
     * @return the name of the bank
     */
    public String getName(){
        return this.name;
    }
    
    

}
