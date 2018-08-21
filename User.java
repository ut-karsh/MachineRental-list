///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  Cloud.java
// File:             User.java
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

import java.util.Random;

/**
 * The User class uses DLinkedList to build a price ordered list called 
 * 'machineList' of machines.
 * Machines with higher price fields should come earlier in the list.
 * 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */
public class User {
	//Random number generator, used for generateMachineStock. DO NOT CHANGE
	private static Random randGen = new Random(1234);

	/** Username of user*/
	private String username;

	/** Password of user*/
	private String passwd;

	/** $Credit in user's account*/
	private double credit;

	/** List of machines in the user's account*/
	private ListADT<Machine> machineList;

	/**
	 * Constructs a User instance with a name, password, credit and an empty 
	 * machineList. 
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @param credit amount of credit the user had in $ 
	 */
	public User(String username, String passwd, double credit) {
		this.username = username;
		this.passwd = passwd;
		this.credit = credit;
		machineList = new DLinkedList<Machine>();
	}

	/**
	 * Checks if login for this user is correct.
	 *
	 * @param username the name to check
	 * @param passwd the password to check
	 * @return true if credentials correct, false otherwise
	 * @throws IllegalArgumentException if arguments are null 
	 */
	public boolean checkLogin(String username, String passwd)
			throws IllegalArgumentException {

		if (username.equals(null) || passwd.equals(null)) 
			throw new IllegalArgumentException();

		// If both username and password match correct data, return true, else false
		if(this.username.equals(username) && this.passwd.equals(passwd)){
			return true;
		}
		return false;     
	}

	/**
	 * Adds a machine to the user's machineList. 
	 * Maintain the order of the machineList from highest priced to lowest 
	 * priced machines.
	 * @param machine the Machine to add
	 * @throws IllegalArgumentException if arguments are null 
	 */
	public void addToMachineList(Machine machine) 
			throws IllegalArgumentException {

		if (machine==null)
			throw new IllegalArgumentException();

		/* if List is empty, then there is no comparison required and machine 
		 * can simply be added to the (end of) list.
		 */
		if (machineList.isEmpty())
			machineList.add(machine);

		// Adding machine in descending order of price
		for (int i=0; i<machineList.size(); i++) {

			// checkMachine: temporary variable to compare prices
			Machine checkMachine = machineList.get(i);

			/* If @param machine has a higher price than machine at (index i)
			 * adds machine*/
			if (machine.getPrice()>checkMachine.getPrice()) {   
				machineList.add(i, machine);
				break; 
			}

			/* If @param machine's price is less than machine at (index i),
			 * compare @param machine to (index i+1).
			 */
			else  if (machine.getPrice()<checkMachine.getPrice()){  
				
				/* Case that the machine being added is not a higher price than
				 * any machines already in the list*/
				if (i == machineList.size()-1) {
					machineList.add(machine);
				}
				
				/* If price of machine at (index i+1) is greater than 
				 * or equal to @param machine then continue.*/
				else if((machineList.get(i+1).getPrice() > machine.getPrice()) || 
						(machineList.get(i+1).getPrice() == machine.getPrice())) {
					continue;
				}
				
				/* If price of machine at (index i+1) is less than 
				 * @param machine then add machine.
				 */
				else{
					machineList.add(i+1, machine);
					break; 
				}
			}
		}
	}

	/**
	 * Removes a machine from the user's machineList. 
	 * Do not charge the user for the price of this machine.
	 * 
	 * @param machineName the name of the machine to remove
	 * @return the machine on success, null if no such machine found
	 * @throws IllegalArgumentException if arguments are null
	 */
	public Machine removeFromMachineList(String machineName)
			throws IllegalArgumentException {

		if (machineName == null) 
			throw new IllegalArgumentException();

		// Loop which performs removal operation if machine is found in list
		for (int i=0; i<machineList.size(); i++) {
			Machine checkMachine = machineList.get(i);

			if (checkMachine.getName().equals(machineName)) {
				
				machineList.remove(i);
				return checkMachine;
			}
		}
		return null;
	}


	/**
	 * Print each machine in the user's machineList in its own line by using
	 * the machine's toString function.
	 */
	public void printMachineList() {

		for (int i=0; i<machineList.size(); i++) {

			Machine printMachine = machineList.get(i);
			System.out.println(printMachine.toString());
		}
	}

	/**
	 * Rents the specified machine in the user's machineList.
	 * Charge the user according to the price of the machine by updating the 
	 * credit.
	 * Remove the machine from the machineList as well.
	 * Throws an InsufficientCreditException if the price of the machine is 
	 * greater than the credit available. Throw the message as:
	 * Insufficient credit for <username>! Available credit is $<credit>.
	 * 
	 * @param machineName name of the machine
	 * @return true if successfully bought, false if machine not found 
	 * @throws InsufficientCreditException if price > credit 
	 */
	public boolean rent(String machineName) throws InsufficientCreditException { 

		for (int i=0; i<machineList.size(); i++) {
			
			Machine checkMachine = machineList.get(i);

			// True if machine found
			if (checkMachine.getName().equals(machineName)) { 

				// If machine costs more than available credit, throw exception 
				if (checkMachine.getPrice() > credit)
					throw new InsufficientCreditException(username, credit);

				// Executes if credit is sufficient to rent machine
				credit = credit - checkMachine.getPrice();
				machineList.remove(i);
				return true;
			}
		}
		return false;
	}

	/** 
	 * Returns the credit of the user
	 * 
	 * @return the credit
	 */
	public double getCredit() {
		return credit;
	}

	/**
	 * This method is already implemented for you. Do not change.
	 * Declare the first n machines in the currentUser's machineList to be 
	 * available.
	 * n is generated randomly between 0 and size of the machineList.
	 * 
	 * @returns list of machines in stock 
	 */
	public ListADT<Machine> generateMachineStock() {
		ListADT<Machine> availableMachines = new DLinkedList<Machine>();

		int size = machineList.size();
		if(size == 0)
			return availableMachines;

		//N items in stock where n >= 0 and n < size
		int n = randGen.nextInt(size+1); 

		//pick first n items from machineList
		for(int ndx = 0; ndx < n; ndx++)
			availableMachines.add(machineList.get(ndx));

		return availableMachines;
	}

}