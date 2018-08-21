///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 1
// Files:            Cloud.java, DblListnode.java, DLinkedList.java, ListADT.java,
//					 InsufficientCreditException.java, Machine.java, User.java
// Semester:         (course) Summer 2017
//
// Author:           Utkarsh Maheshwari 
// Email:            umaheshwari@wisc.edu
// CS Login:         maheshwari
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Jared Akers
// Email:            jakers@wisc.edu
// CS Login:         akers
// Lecturer's Name:  Meenakshi Syamkumar
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but tutors, roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class which simulates the cloud environment.
 * 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */
public class Cloud {

	/**Store record of users and machines*/
	private static ListADT<Machine> machines = new DLinkedList<Machine>();
	private static ListADT<User> users = new DLinkedList<User>();
	/**Current user logged in*/
	private static User currentUser = null;
	private static String userInfo; // TODO Are we using this anywhere? Where are we using it?
	private static Object String; // TODO where is this used?

	//scanner for console input
	public static final Scanner stdin = new Scanner(System.in);

	//main method
	public static void main(String args[]) {

		//Populate the two lists using the input files: Machines.txt User1.txt 
		//User2.txt ... UserN.txt
		if (args.length < 2) {
			System.out.println("Usage: java Cloud [MACHINE_FILE] [USER1_FILE] [USER2_FILE] ...");
			System.exit(0);
		}

		//load store machines
		loadMachines(args[0]);

		//load users one file at a time
		for(int i = 1; i< args.length; i++) {
			loadUser(args[i]);
		}

		//User Input for login
		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter username : ");
			String username = stdin.nextLine();
			System.out.print("Enter password : ");
			String passwd = stdin.nextLine();

			if(login(username, passwd) != null)
			{
				//generate random items in stock based on this user's machine 
				//list
				ListADT<Machine> inStock = currentUser.generateMachineStock();
				//show user menu
				userMenu(inStock);
			}
			else
				System.out.println("Incorrect username or password");

			System.out.println("Enter 'exit' to exit program or anything else to go back to login");
			if(stdin.nextLine().equals("exit"))
				done = true;
		}

	}

	/**
	 * Tries to login for the given credentials. Updates the currentUser if 
	 * successful login
	 * 
	 * @param username name of user
	 * @param passwd password of user
	 * @returns the currentUser 
	 */
	public static User login(String username, String passwd) {

		for (int i=0; i<users.size();i++) {

			//Checks User class for successful login
			if(users.get(i).checkLogin(username, passwd)) {
				currentUser = users.get(i);
				break;
			}
		}
		return currentUser;
	}

	/**
	 * Reads the specified file to create and load machines into the store.
	 * Every line in the file has the format: <NAME>#<CATEGORY>#<PRICE>#<RATING>
	 * Create new machines based on the attributes specified in each line and 
	 * insert them into the machines list
	 * Order of machines list should be the same as the machines in the file
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadMachines(String fileName) {

		/**Create File object to parse through file content*/
		File machineFile = new File(fileName);
		Scanner input;

		try{
			/** Create Scanner object for file traversal*/
			input = new Scanner(machineFile);

			while (input.hasNextLine())
			{	
				/**In the block of code below, we first split the NextLine and
				 * the content between two #'s is stored in its
				 * own separate array element. We know the format in which 
				 * the lines in the file are formatted, thus enabling us to 
				 * know exactly in which element each attribute lies, thus
				 * enabling easy retrieval. 
				 */

				String [] machineInfo = input.nextLine().split("#"); 

				String machineName = machineInfo[0];
				int numCPUs = Integer.parseInt(machineInfo[1]);
				int memorySize = Integer.parseInt(machineInfo[2]);
				int diskSize = Integer.parseInt(machineInfo[3]);
				double price = Double.parseDouble(machineInfo[4]);

				/** Creates newMachine object from Scanner input. */
				Machine newMachine = new Machine
						(machineName,numCPUs,memorySize,diskSize,price);

				machines.add(newMachine);
			}
			input.close();
		}

		// Catches all file related exceptions
		catch (IOException excpt) {
			System.out.println("Error: Cannot access file");
		} 
	}

	/**
	 * Reads the specified file to create and load a user into the store.
	 * The first line in the file has the format:<NAME>#<PASSWORD>#<CREDIT>
	 * Every other line after that is a name of a machine in the user's 
	 * machinelist, format:<NAME>
	 * For any problem in reading the file print: 'Error: Cannot access file'
	 * 
	 * @param fileName name of the file to read
	 */
	public static void loadUser(String fileName) {

		/**Create File object to parse through file content*/
		File userFile = new File(fileName);
		Scanner input;

		try{
			input = new Scanner(userFile);

			if (input.hasNextLine()) {

				/**In the block of code below, we split the first line (if any)
				 * separately, before loop for the rest of file, since it has a
				 * different format. We split the first line to store the username
				 * in element 0, the password in element 1, and the user's credit
				 * amount in element 3 of Array userInfo[].
				 */
				String[] userInfo = input.nextLine().split("#");

				//Extracting attributes from array and storing in respective variables.
				String username = userInfo[0];
				String passwd = userInfo[1];
				Double credit = Double.parseDouble(userInfo[2]);

				// Loading the read user into the list using newUser
				User newUser = new User(username,passwd,credit);
				users.add(newUser);   

				/** The below block of code works the same way as the block in
				 * loadMachines.
				 */
				while (input.hasNextLine())
				{
					String [] machineInfo = input.nextLine().split("#"); 

					String machineName = machineInfo[0];
					int numCPUs = Integer.parseInt(machineInfo[1]);
					int memorySize = Integer.parseInt(machineInfo[2]);
					int diskSize = Integer.parseInt(machineInfo[3]);
					double price = Double.parseDouble(machineInfo[4]);	

					Machine newMachine = new Machine(machineName,numCPUs,memorySize,diskSize,price);
					newUser.addToMachineList(newMachine);
				} 
			}
			input.close();
		}

		// Catches all file related exceptions
		catch (IOException excpt) {
			System.out.println("Error: Cannot access file");
		}
	}


	/** 
	 * Prints the entire machine inventory.
	 * The order of the machines should be the same as in input machines file.
	 * The output format should be the consolidated String format mentioned
	 * in Machine class.
	 */

	public static void printMachines() {
		for(int i =0; i < machines.size(); i++){
			System.out.println(machines.get(i).toString());
		}
	}

	/**
	 * Interacts with the user by processing commands
	 * 
	 * @param inStock list of machines that are in stock
	 */
	public static void userMenu(ListADT<Machine> inStock) {

		boolean done = false;
		while (!done) 
		{
			System.out.print("Enter option : ");
			String input = stdin.nextLine();

			//only do something if the user enters at least one character
			if (input.length() > 0) 
			{
				String[] commands = input.split(":");//split on colon, because 
				//names have spaces in them
				if(commands[0].length()>1)
				{
					System.out.println("Invalid Command");
					continue;
				}

				//Switch block begins here
				switch(commands[0].charAt(0)) {

				case 'v':

					if(commands.length==1) {
						System.out.println("Invalid Command");
						break;
					}

					/*Print all machines in the cloud.*/
					if (commands[1].equals("all"))
						printMachines();

					/* For the current user print all machines in their 
					 * machinelist. The order is the same as the machinelist.
					 */					
					else if (commands[1].equals("machinelist"))
						currentUser.printMachineList();

					/*Print all machines in the inStock list.*/
					else if (commands[1].equals("instock"))
						for(int i =0; i < inStock.size(); i++){
							System.out.println(inStock.get(i).toString());
						}

					/*Print "Invalid Command" if wrong string is entered.*/
					else
						System.out.println("Invalid Command");

					break;

				case 's': 
					/*Search for the String string in all of the machine names 
					 * in the cloud and print out all the matching machines. 
					 * Print format for each machine is the same as specified
					 * in the toString method of Machine. 
					 * For no matching machines, print nothing.
					 */

					if(commands.length==1) {
						System.out.println("Invalid Command");
						break;
					}

					for (int i=0; i<machines.size(); i++) {
						if (machines.get(i).getName().contains(commands[1]))
							System.out.println(machines.get(i).toString());
					}
					break;

				case 'a':
					/* Add the Machine whose name is specified by machineName
					 * to the user's machinelist. 
					 * Print "Machine not found" if no such machine.
					 */
					if(commands.length==1) {
						System.out.println("Invalid Command");
						break;
					}

					Machine addMachine = null;
					boolean machineFound = false;

					for(int i =0; i < machines.size(); i++){

						if (machines.get(i).getName().equals(commands[1])) {
							addMachine = machines.get(i);
							machineFound = true;
							break;
						}
					}

					if(machineFound)
						currentUser.addToMachineList(addMachine);

					else
						System.out.println("Machine not found");

					break;

				case 'r':
					/* Remove the Machine whose name is specified by machineName
					 * from the user's machinelist. 
					 * Print "Machine not found" if no such machine.
					 */
					machineFound = false;

					if(commands.length==1) {
						System.out.println("Invalid Command");
						break;
					}					

					for(int i =0; i < machines.size(); i++){

						if (machines.get(i).getName().equals(commands[1])) {
							currentUser.removeFromMachineList(commands[1]);
							machineFound = true;
						}
					}

					if(!machineFound)
						System.out.println("Machine not found");

					break;

				case 'b':
					/* Rent the Machines shown to be in stock by the list inStock.
					 *  This removes them from the user's machinelist and charges
					 *  the user for it. While doing the rent operation, check 
					 *  whether the machine in inStock is actually there in the 
					 *  user's machine list. If the user has insufficient credit,
					 *  print out: "For renting <MACHINE-NAME>: Insufficient credit
					 *  for <PRODUCT-NAME>! Available credit is $<credit>" and
					 *  proceed with processing the user input for the commands
					 *  again.  If rent operation was not successful because of 
					 *  machine not being on machinelist, then print 
					 *  "Machine not needed: <MACHINE-NAME>".
					 */

					for (int i=0; i<inStock.size(); i++)
					{
						try{

							if (currentUser.rent(inStock.get(i).getName())) {
								System.out.println("Rented "+
										inStock.get(i).getName());	
								continue;
							}

							else
								System.out.println("Machine not needed: "+
										inStock.get(i).getName());
						}
						
						catch (InsufficientCreditException excpt){

							System.out.println("For renting " + 
									inStock.get(i).getName()+ ": " +excpt.getMessage());
						}
					}

					break;

				case 'c': /*Shows the credit of the current user as $<CREDIT>.*/
					System.out.println("$"+currentUser.getCredit());
					break;

				case 'l': /*Logs out current user and goes back to login screen.*/
					done = true;
					System.out.println("Logged Out");
					break;

				default:  //a command with no argument
					System.out.println("Invalid Command");
					break;
				}
			}
		}
	}

}
