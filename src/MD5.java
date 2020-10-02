/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author promise
 */

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


 class MD5{
     public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        while(!sc.hasNextInt()){
            System.out.println("invalid");
            sc.nextLine();
        }
        int input = sc.nextInt();
         System.out.println(input);
        
         
     }
 }   
    
    
   