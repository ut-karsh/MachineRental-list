///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Cloud.java
// File:             InsufficientCreditException.java
// Semester:         CS 367 Summer 2017
//
// Author:           Utkarsh Maheshwari umaheshwari@wisc.edu
// CS Login:         maheshwari
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Jared Akers
// Email:            jakers@wisc.edu
// CS Login:         akers
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Class creates a custom exception. An instance of this class will be thrown 
 * when a user tries to rent an item but does not have sufficient credit.  
 * 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */
public class InsufficientCreditException extends Throwable {
	
	String message;
	
	/**
	 * Default constructor
	 */
	public InsufficientCreditException(){
		message = null;	
	}
	
	/**
	 * Creates a string message that notifies the user that they lack credit
	 * to rent item.
	 * 
	 * @param nameOfUser Name of user lacking credit
	 * @param credit Credit user has available 
	 */
	public InsufficientCreditException(String nameOfUser, double credit){
		message = "Insufficient credit for " + nameOfUser +
				"! Available credit is $" + credit;
	}
	
	/**
	 * Accessor method
	 * 
	 * @return message User's error message
	 */
	public String getMessage(){
		return message;
	}
}
