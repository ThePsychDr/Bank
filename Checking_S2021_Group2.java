import java.util.*;
import java.util.Scanner;
/**
 * This program processes user action requests for their checking account.
 * @since 2022-09-27
 * @author Group 2
 * @param name
 * @param id
 * @param balance
 */
public class Checking_S2021_Group2 {
	
	double balance;
	double amount;
	String name; //Risk assessment: name is considered sensitive because it can be used to correlate information about a user's account
	final double fee = 20; //Risk mitigation/logging: the fee is static, cannot be manipulated by the user
	
	/**
	 * This method is the method used to create a user account
	 * @param name
	 * @param initialBalance
	 */
	public Checking_S2021_Group2(String name, double initialBalance)
   {
        this.name = name;
        balance = initialBalance;
    }
	
	
	/**
	 * The Withdrawal method handles user withdrawals from their Checking account
	 * @param amount
	 * @return the balance after a withdrawal
	 * 
	 * Risk assessment: The withdrawal method is sensitive, as the user can input a value that exceeds the balance, breaking the code by withdrawing money that doesn't exist.
	 */
	public double withdrawl(double amount) {
		
		if (amount > balance) {
			System.out.println("Your account does not contain this amount of money. Please enter a valid amount.");
		}
		else if (amount < 0) {
			System.out.println("Your withdrawal amount cannot be negative. Please enter a valid amount."); 
		}
		else {
			 double newBalance = balance-amount;
			 balance = newBalance;
		}
		double newBalance = balance-amount;
		 return newBalance;
		
			
	}
	
	/**
	 * The Deposit method handles user deposits in their Checking account
	 * @param amount
	 * @return the balance after the deposit
	 */
	public double deposit(double amount) {
		if (amount < 0) {
			System.out.println("Your deposit amount cannot be negative. Please enter a valid amount.");//Risk mitigation: The user is prompted if the withdrawal attempt is not within the bounds
		}
		else {
			this.balance = this.balance+amount;
		}
		return this.balance;
	}
	
	
	class InvalidBalanceAmount extends Exception  
	{  
	    public InvalidBalanceAmount (String str)  
	    {  
	        // calling the constructor of parent Exception  
	        super(str);  
	    }  
	}  
	
	/**
	 * This class provides the definition for the BalanceCheckException
	 * If user's balance falls below $3000, an exception is thrown
	 *
	 */
	public class BalanceCheckException  
	{  
	    // method to check the balance  
	    void validate (double balance) throws InvalidBalanceAmount{    
	       if (balance < 3000){  
	        // throw an object of user defined exception  
	        throw new InvalidBalanceAmount("Your balance falls beneath the $3000 minimum");    
	    }  
	       else {   
	        System.out.println("Enjoy Premier Financial");   
	        }   
	     }    
	
	} 
}