import java.util.Scanner;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.*;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.security.SecureRandom;

/**
 * The Account_S2021_Group2 program processes user input for bank account transactions. It uses Savings_S2021_Group2 and
 * Checking_S2021_Group2 to process the transactions
 * 
 * @author Group 2: Trevin Bachan, Neander Devil, Justin Hemphill, Katarina Kobetisch, Emily Liatto, Matthew Sacktig 
 * @since 2022-09-27
 * @param name
 * @param id
 * @param balance
 */
public class Account_S2021_Group2 {
	private static SecretKeySpec secretKey;
    private static byte[] key;
	double balance;
	String name; 
	/**
	 * The name of the bank, Premier Financial
	 */
	static String PremierFinancial;
	
	/**
	 * This is the main method. It takes user input in order to view balances, withdraw money,
	 * or deposit money.
	 * @param args
	 */
	public static void main(String[] args) {
		String pkey = "Kr334!5iD";
		Scanner in = new Scanner(System.in);
		int input;
		/**
		 * The checking and savings constructors create the two user accounts being mentioned
		 */
		Checking_S2021_Group2 checking = new Checking_S2021_Group2("Chester", 4000.00);
		Savings_S2021_Group2 savings = new Savings_S2021_Group2("Alicia", 15000.00);
		double balance;
		
		/**
		 * The print statement below normalizes the name that the user inputs
		 * @param name = The name that the user input
		 * Normalizes the name into NKFC format
		 * 
		 * Risk assessment: If not normalized, special characters can be input in order to cause a DOS
		 * Risk assessment mitigation: Never execute client input as code
		 */
		System.out.println("Please enter your name: ");
		String name = in.next();
		name = Normalizer.normalize(name, Form.NFKC);
		//System.out.println(name);
		String encryptedString = encrypt(name, pkey);
		System.out.println("cipher text: " + encryptedString);
		
		do {
		System.out.println("Please select an option from the following list:\r\n" +
				"1- Please enter your name\r\n" +
				"2- View Checkings balance\r\n" +
				"3- View Savings balance\r\n" +
				"4- Deposit Money in Checkings\r\n" +
				"5- Deposit Money in Savings\r\n" +
				"6- Withdraw from checkings\r\n" +
				"7- Withdraw from Savings");
		input = in.nextInt();
		
		
		if (input == 2) {
			System.out.println(checking.balance);
		}
		else if (input ==3) {
			System.out.println(savings.balance);
		}
		else if (input ==4) {
			System.out.println("Input the amount to be deposited: ");
			checking.amount = in.nextDouble();
			checking.deposit(checking.amount);
			System.out.println("Your balance after your deposit is: " + checking.balance);
		}
		else if (input ==5) {
			System.out.println("Input the amount to be deposited: ");
			savings.amount = in.nextDouble();
			savings.deposit(savings.amount);
			System.out.println("Your balance after your deposit is: " + savings.balance);
		}
		else if (input ==6) { 
			System.out.println("Input the amount to be withdrawn: ");
			checking.amount = in.nextDouble();
			checking.withdrawl(checking.amount);
			System.out.println("Your balance after your withdraw: " + checking.balance);
		}
		else if (input ==7) {
			System.out.println("Input the amount to be withdraw: ");
			savings.amount = in.nextDouble();
			savings.withdrawl(savings.amount);
			System.out.println("Your balance after your withdraw: " + savings.balance);
		}
		} 
		while (input !=8);
			System.out.println("Thank you for using our bank!");		
	}
	
	/**
	 * Custom exception to return if an account name is invalid
	 * 
	 */
	class InvalidAccountName extends Exception  
	{  
	    public InvalidAccountName (String str)  
	    {  
	        // calling the constructor of parent Exception  
	        super(str);  
	    }  
	} 
	
	/**
	 * NameCheckException throws the InvalidAccountName exception
	 * If the account name throws an exception, normalize the account name and prompt the user for a new one 
	 *
	 */
	class NameCheckException  
	{    
	    void validate (String name) throws InvalidAccountName{    
	       if (name != Normalizer.normalize(name, Form.NFKC)){  
	        
	        throw new InvalidAccountName("The name entered is invalid, please enter a new one.");    
	    }  
	       else {   
	        System.out.println("Enjoy Premier Financial");   
	        }   
	     }    
	
	}
	
	
	public static void setKey(String myKey){
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public static void moreRandomSetKey() {
    	
		try {
			SecureRandom secureRandom = new SecureRandom();
			byte[] key = new byte[32];
			secureRandom.nextBytes(key);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
 
    /**
     * This is the method to encrypt strings
     * @param strToEncrypt
     * @param secret
     * @return null
     */
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            moreRandomSetKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    /**
     * This is the method to decrypt the encrypted string
     * @param strToDecrypt
     * @param secret
     * @return null
     */
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}