//import required classes and packages
import java.sql.*;
import java.util.*;
import java.time.*;


public class Main {
	//main class
	public static void main(String[] args) {
		//Declare and initialize variable to be used to capture user input for menu choice;
		int userChoice = 0;	

		//try catch block containing the connection to the database (including credentials to be used to access the database and the address on the localhost);
		try {
			Connection connection;
			connection = DriverManager.getConnection(
			         "jdbc:mysql://localhost:3306/poisepms?useSSL=false",
			         "otheruser",
			         "swordfish"
			         );
		
			//Creating connection to the database and declaring a statement to be used in further queries
			Statement statement = connection.createStatement();	

			//Welcome message to be displayed 
			System.out.println("Welcome to POISE PROJECT MANAGEMENT SYSTEM version 1.0 \n");
        
			//do while loop which contains the methods to be run based on the user choice made in console
			do {  	
				userChoice = operationSelect();	
				//switch statement using userChoice variable as key
				/*Note that the statement parameter is required for the methods in each case of the 
				 * switch statement as this eliminates the need to unnecessarily duplicate the code 
				 * in each * method that is run*/
				switch (userChoice) {
    				case 0:
    					//Message to be displayed when program is ended by user
    					System.out.println("Good bye!");
    					break;
    				case 1:
    					//Message to display choice selection and method to run when user wants to add a new project to the database;    		
    					addProject(statement);
    					break;
    				
    				case 2:
    					//Message to display choice selection and method to run when user wants to update existing project
    					System.out.println("Update information about an existing project selected.");
    					updateProject(statement);
    				break;
    				
    				case 3:
    					//Message to display choice selection and method to run when user wants to remove an existing project
    					System.out.println("Remove an existing project from the database selected.");
    					removeProject(statement);	
    				break;
    				
    				case 4:
    					//Message to display choice selection and method to run when user wants to finalize an existing project
    					System.out.println("Finalise an existing project selected.");
    					finalizeProject(statement);
    				break;
    			
    				case 5:
    					//Message to display choice selection and method to run when user wants to display all incomplete/pending projects
    					System.out.println("Find and display incomplete projects;");
    					incompleteProjects(statement);
    				break;
    	
    				case 6:
    					//Message to display choice selection and method to run when user wants to display all projects past the due date based on local date
    					System.out.println("Find and display projects past due date selected;");
    					lateProjects(statement);
    				break;
    			
    				case 7:
    					//Message to display choice selection and method to run user wants search for a project based on project number
    					System.out.println("Find and display projects via project number;");
    					findProjectNumber(statement);
    				break;
    			
    				case 8:
    					//Message to display choice selection and method to run when user wants search for a project based on project name
    					System.out.println("Find and display projects via project name;");
    					findProjectName(statement);
    				break;	
    				
    				case 9:
    					//Message to display choice selection and method to run when user wants to display all of the books in the projects
    					System.out.println("Display all projects;");
    					displayAll(statement);
    				break;	
    				
    				default:
    				break;				
    		}
			//while loop to limit user choices to an integer number between 0 and 9
			}while(userChoice != 0 || userChoice <0 || userChoice >9);
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
    		
}
	
//methods and functions
	
//method to display main menu and operations that can be performed
	public static int operationSelect() {
		//declaring and initiating variable for user input
		int userChoice = 0;
		//declaring scanner to be used for the main menu
		Scanner menuScanner = new Scanner(System.in);

		//displaying the main menu
	    System.out.println( "\nMENU:" + "\n" 
	    + "1. Add new project." + "\n" 
	    + "2. Update project information." + "\n"
	    + "3. Remove project." + "\n"
	    + "4. Finalise project." + "\n"
	    + "5. Display incomplete projects." + "\n"
	    + "6. Display projects past due date." + "\n"
	    + "7. Find projects via project number." + "\n"
	    + "8. Find projects via project name" + "\n"
	    + "9. Display all projects" + "\n"
	    + "0. Quit the program.");
			
	    //do while loop to request user input
		do {
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userChoice = menuScanner.nextInt();
		
				//if statement to alert user to invalid input and request new input
				if(userChoice <0 || userChoice >9) {
					System.out.println("You have entered an invalid choice. Please try again.");
					userChoice = operationSelect();
				}
		}
		//while loop to limit user choices to an integer number between 0 and 9
		while(userChoice <0 || userChoice >9);
			
		return userChoice;
	};
	
	//method to add a new project to the database 
	public static void addProject(Statement statement) {
		//declaring and initiating variables for user input
		//scanner for integer inputs
		Scanner menuScanner = new Scanner (System.in);
		//scanner for string inputs
			
		//declaring and initiating default variables to be replaced by user input	
		
		//variables to hold values for projects table
		int PRJ_NUM = 0;
		String PRJ_NAME = "";
		
		//variables to hold values for cost table
		float PRJ_TOTAL_COST = 0;
		float PRJ_TOTAL_PAID = 0;
		
		//variables to hold values for information table
		int PRJ_ERF_NR = 0;
		String PRJ_TYPE = "";
		String PRJ_ADDRESS = "";
		
		//variables to hold values for status table
		String PRJ_STATUS = "";
		String PRJ_DEADLINE = "";
		
		//variables to hold values for architects table
		String PRJ_ARCH_NAME = "";
		String PRJ_ARCH_CONTACT = "";
		String PRJ_ARCH_EMAIL = "";
		String PRJ_ARCH_ADDRESS = "";
		
		//variables to hold values for customers table
		String PRJ_CUST_NAME = "";
		String PRJ_CUST_CONTACT = "";
		String PRJ_CUST_EMAIL = "";
		String PRJ_CUST_ADDRESS = "";
		
		//variables to hold values for managers table
		String PRJ_MAN_NAME = "";
		String PRJ_MAN_CONTACT = "";
		String PRJ_MAN_EMAIL = "";
		String PRJ_MAN_ADDRESS = "";
		
		//variables to hold values for engineers table
		String PRJ_SENG_NAME = "";
		String PRJ_SENG_CONTACT = "";
		String PRJ_SENG_EMAIL = "";
		String PRJ_SENG_ADDRESS = "";
			
		//try catch block to capture user input for new projects
		try {
			//capturing data for PROJECTS TABLE
			//capturing PRJ_NUM data from user for use in projects table
			System.out.println("Please enter the PROJECT CODE (Format: 6 number code):");
			PRJ_NUM = menuScanner.nextInt();
			menuScanner.nextLine();
			System.out.println("Please enter the PROJECT NAME(Format: Type, single space, Surname):");
			PRJ_NAME = menuScanner.nextLine();
								
			//add the newly added project number to all tables
			statement.executeUpdate("INSERT INTO projects VALUES "+"("+ PRJ_NUM +","+"'"+ PRJ_NAME+"'"+")");
			statement.executeUpdate("INSERT INTO info(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO cost(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO status(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO architects(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO customers(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO engineers(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
			statement.executeUpdate("INSERT INTO managers(PRJ_NUM) VALUES " + "(" + PRJ_NUM + ")");
				
			//capturing data for COST TABLE
			System.out.println("Please enter TOTAL COST of project(Format: Number only no currency symbol):");
			PRJ_TOTAL_COST = menuScanner.nextFloat();
			menuScanner.nextLine();
			System.out.println("Please enter TOTAL AMOUNT PAID TO DATE(Format: Number only no currency symbol):");
			PRJ_TOTAL_PAID = menuScanner.nextFloat();
			//writing data to COST TABLE
			statement.executeUpdate("UPDATE cost SET PRJ_TOTAL_COST =" + "'" + PRJ_TOTAL_COST + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );
			statement.executeUpdate("UPDATE cost SET PRJ_TOTAL_PAID =" + "'" + PRJ_TOTAL_PAID + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );
				
			//capturing data for INFO TABLE
			System.out.println("Please enter the ERF number of the project(Format: Numbers only)");
			PRJ_ERF_NR = menuScanner.nextInt();
			statement.executeUpdate("UPDATE info SET PRJ_ERF_NR =" + "'" + PRJ_ERF_NR + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );
			menuScanner.nextLine();
			System.out.println("Please enter the TYPE of project(Format: House/Town house/Apartment/Office etc.)");
			
			PRJ_TYPE = menuScanner.nextLine();			
			statement.executeUpdate("UPDATE info SET PRJ_TYPE =" + "'" + PRJ_TYPE + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );
			System.out.println("Please enter the ADDRESS of the project(Format: Lines seperated by comma)");
			
			PRJ_ADDRESS =menuScanner.nextLine();
			statement.executeUpdate("UPDATE info SET PRJ_ADDRESS =" + "'" + PRJ_ADDRESS + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );	
			//writing data to INFO TABLE
			
			
			
				
			//capturing data for STATUS TABLE				
			System.out.println("This is a new project. The default value 'IN PROGRESS' will be set as status");
			PRJ_STATUS = "IN PROGRESS";
				
			//writing data to STATUS TABLE
			statement.executeUpdate("UPDATE status SET PRJ_STATUS =" + "'" + PRJ_STATUS + "'" + "WHERE PRJ_NUM = " + PRJ_NUM );
			System.out.println("Please enter the project deadline(Format:YYYY-MM-DD):");
			PRJ_DEADLINE = menuScanner.nextLine();
			statement.executeUpdate("UPDATE status SET PRJ_DEADLINE =" + "'"+PRJ_DEADLINE +"'" + "WHERE PRJ_NUM = " + PRJ_NUM );
			System.out.println("The default value NULL will be set as completion date." +
								   "The default value PENDING will be set as status.");
				
			//capturing data for CUSTOMER DATA				
			System.out.println("Please enter the CUSTOMER NAME:");
			PRJ_CUST_NAME = menuScanner.nextLine();
			System.out.println("Please enter the CUSTOMER CONTACT NUMBER:");
			PRJ_CUST_CONTACT = menuScanner.nextLine();
			System.out.println("Please enter the CUSTOMER EMAIL:");
			PRJ_CUST_EMAIL = menuScanner.nextLine();
			System.out.println("Please enter the CUSTOMER ADDRESS:");
			PRJ_CUST_ADDRESS = menuScanner.nextLine();
				
			//writing data to CUSTOMER TABLE
			statement.executeUpdate("UPDATE customers SET PRJ_CUST_NAME =" + "'" + PRJ_CUST_NAME + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE customers SET PRJ_CUST_CONTACT =" + "'" + PRJ_CUST_CONTACT + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE customers SET PRJ_CUST_EMAIL =" + "'" + PRJ_CUST_EMAIL + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE customers SET PRJ_CUST_ADDRESS =" + "'" + PRJ_CUST_ADDRESS + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
				
			//capturing data for ARCHITECT TABLE
			System.out.println("Please enter the ARCHITECT NAME:");
			PRJ_ARCH_NAME = menuScanner.nextLine();
			System.out.println("Please enter the ARCHITECT CONTACT NUMBER:");
			PRJ_ARCH_CONTACT = menuScanner.nextLine();
			System.out.println("Please enter the ARCHITECT EMAIL:");
			PRJ_ARCH_EMAIL = menuScanner.nextLine();
			System.out.println("Please enter the ARCHITECT ADDRESS:");
			PRJ_ARCH_ADDRESS = menuScanner.nextLine();
				
			//writing data to ARCHITECT TABLE
			statement.executeUpdate("UPDATE architects SET PRJ_ARCH_NAME =" + "'" + PRJ_ARCH_NAME + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE architects SET PRJ_ARCH_CONTACT =" + "'" + PRJ_ARCH_CONTACT + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE architects SET PRJ_ARCH_EMAIL =" + "'" + PRJ_ARCH_EMAIL + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE architects SET PRJ_ARCH_ADDRESS =" + "'" + PRJ_ARCH_ADDRESS + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
				
			//capturing data for ENGINEER TABLE
			System.out.println("Please enter the ENGINEER(STRUCTURAL) NAME:");
			PRJ_SENG_NAME = menuScanner.nextLine();
			System.out.println("Please enter the ENGINEER(STRUCTURAL) CONTACT NUMBER:");
			PRJ_SENG_CONTACT = menuScanner.nextLine();
			System.out.println("Please enter the ENGINEER(STRUCTURAL) EMAIL:");
			PRJ_SENG_EMAIL = menuScanner.nextLine();
			System.out.println("Please enter the ENGINEER(STRUCTURAL) ADDRESS:");
			PRJ_SENG_ADDRESS = menuScanner.nextLine();
				
			//writing data to ENGINEER TABLE
			statement.executeUpdate("UPDATE engineers SET PRJ_SENG_NAME =" + "'" + PRJ_SENG_NAME + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE engineers SET PRJ_SENG_CONTACT =" + "'" + PRJ_SENG_CONTACT + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE engineers SET PRJ_SENG_EMAIL =" + "'" + PRJ_SENG_EMAIL + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE engineers SET PRJ_SENG_ADDRESS =" + "'" + PRJ_SENG_ADDRESS + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
				
			////capturing data for MANAGER TABLE
			System.out.println("Please enter the PROJECT MANAGER NAME:");
			PRJ_MAN_NAME = menuScanner.nextLine();
			System.out.println("Please enter the PROJECT MANAGER CONTACT NUMBER:");
			PRJ_MAN_CONTACT = menuScanner.nextLine();
			System.out.println("Please enter the PROJECT MANAGER EMAIL:");
			PRJ_MAN_EMAIL = menuScanner.nextLine();
			System.out.println("Please enter the PROJECT MANAGER ADDRESS:");
			PRJ_MAN_ADDRESS = menuScanner.nextLine();
				
			//writing data to MANAGER TABLE
			statement.executeUpdate("UPDATE managers SET PRJ_MAN_NAME =" + "'" + PRJ_MAN_NAME + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE managers SET PRJ_MAN_CONTACT =" + "'" + PRJ_MAN_CONTACT + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE managers SET PRJ_MAN_EMAIL =" + "'" + PRJ_MAN_EMAIL + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			statement.executeUpdate("UPDATE managers SET PRJ_MAN_ADDRESS =" + "'" + PRJ_MAN_ADDRESS + "'" + "WHERE PRJ_NUM =" + PRJ_NUM );
			}
		
			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error encountered while adding a new project.");
			}
	}
		
	//method to display UPDATE choices and update tables based on selection
	public static int updateProject(Statement statement) {
			
			//declare and initiate variable to hold user input selection
			int userUpdateChoice = 0;
	       	//do while loop to request user input
			do {
				//declaring scanner to be used for the main menu
				Scanner menuScanner = new Scanner(System.in);
				//displaying the update menu
				System.out.println( "\n UPDATE MENU:" + "\n" 
		        				  + "1. Update PROJECT GENERAL INFORMATION." + "\n" 
		         				  + "2. Update PROJECT INFORMATION." + "\n"
		         				  + "3. Update PROJECT COST INFORMATION." + "\n"
		         				  + "4. Update PROJECT STATUS INFORMATION." + "\n"
		         				  + "5. Update PROJECT ARCHITECT INFORMATION." + "\n"
		         				  + "6. Update PROJECT CUSTOMER INFORMATION." + "\n"
		         				  + "7. Update PROJECT ENGINEER (STRUCTURAL) INFORMATION." + "\n"
		         				  + "8. Update PROJECT MANAGER INFORMATION name" + "\n"
		         				  + "0. RETURN TO MENU.\n");	
				//request input from user
				System.out.println("\nPlease enter the number of the operation you would like to perform:");
				userUpdateChoice = menuScanner.nextInt();
				menuScanner.nextLine();
				//if statement to alert user to invalid input and request new input
				if(userUpdateChoice <0 || userUpdateChoice >8) {
					System.out.println("You have entered an invalid choice. Please try again.");
					userUpdateChoice = updateProject(statement);
				}
					
				//switch block to run appropriate update methods
				switch (userUpdateChoice) {
					//case to return to main menu
					case 0: {
							System.out.println("Returning to menu...");
							break;
					}
					//case to update project name and number
					case 1: {
							updateProjectGeneralInformation(statement);
							break;
					}
					//case to update project information
					case 2: {
							updateProjectInformation(statement);
							break;
					}
					//case to update project cost information
					case 3: {
							updateProjectCost(statement);	
							break;
					}
					//case to update project status information
					case 4: {
							updateProjectStatus(statement);
							break;
					}
					//case to update project architect information
					case 5: {
							updateProjectArchitect(statement);
							break;
					}
					//case to update project customer information
					case 6: {
							updateProjectCustomer(statement);
							break;
					}
					//case to update project engineer information
					case 7: {
							updateProjectEngineer(statement);
							break;
					}
					//case to update project manager information
					case 8: {
							updateProjectManager(statement);
							break;
					}
					default:
							throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
					}	
				}
				//while loop to limit user choices to an integer number between 0 and 8
				while(userUpdateChoice <0 || userUpdateChoice >8);
				
				return userUpdateChoice;
	}
		
	//method to update project general information 
	public static void updateProjectGeneralInformation(Statement statement){
			
		//declare and initiate variable to hold user input selection
		int userUpdateChoice = 0;
		
		//do-while loop to display sub menu regarding update selection to user
		do {
			//declaring scanner to be used for the main menu
			Scanner menuScanner = new Scanner(System.in);
			
			//displaying the main menu
		    System.out.println( "\nMENU:" + "\n" 
		       				  + "1. Update PROJECT NUMBER." + "\n"
		       				  + "2. Update PROJECT NAME." + "\n"
		       				  + "0. RETURN TO MENU.");	
		   
		   //getting user input for menu selection
		   System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = menuScanner.nextInt();
			menuScanner.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >2) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectGeneralInformation(statement);
			}
			//else if statement to update project number
			else if(userUpdateChoice == 1) {
				//try catch block to obtain user input and perform update query.
				try {
					System.out.println("Please enter the PROJECT NUMBER TO UPDATE:");
					int currentPRJ_NUM = menuScanner.nextInt();
					menuScanner.nextLine();
					System.out.println("Please enter the NEW PROJECT NUMBER:");
					int newPRJ_NUM = menuScanner.nextInt();
					menuScanner.nextLine();
					
					//perform update query on table
					statement.executeUpdate("UPDATE projects SET PRJ_NUM =" + "'" + newPRJ_NUM + "'" + "WHERE PRJ_NUM =" + currentPRJ_NUM );
					}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error occured during update operation.");
				}		
			}
			//else if statement to update project name
			else if(userUpdateChoice == 2) {
				//try catch block to obtain user input and perform update query.
				try {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT NAME to UPDATE:");
					String currentPRJ_NAME = menuScanner.nextLine();
					System.out.println("Please enter the NEW PROJECT NAME:");
					String newPRJ_NAME = menuScanner.nextLine();
					//perform update query on table
					statement.executeUpdate("UPDATE projects SET PRJ_NAME =" + "'" + newPRJ_NAME + "'" + "WHERE PRJ_NUM =" + currentPRJ_NAME );
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error occured during update operation.");
				}
			}
		}
		//while loop to limit user choices to an integer number between 0 and 5
		while(userUpdateChoice <0 || userUpdateChoice >2);		
	}
		
	//method to update project information
	public static void updateProjectInformation(Statement statement){
	
		//declare variables and scanners for use in method
		int userUpdateChoice = 0;
		Scanner menuScanner = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
		
		//do-while loop to display sub menu regarding update selection to user
		do {
			//displaying the main menu
		    System.out.println( "\nMENU:" + "\n" 
		       				  + "1. Update PROJECT ERF NUMBER." + "\n"
		       				  + "2. Update PROJECT TYPE." + "\n"
		       				  + "3. Update PROJECT ADDRESS" + "\n"
		       				  + "0. RETURN TO MENU.");	
		    
		    System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = menuScanner.nextInt();
			menuScanner.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >3) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectInformation(statement);
			}
			
			//switch block to perform user select operation based on user input
			switch (userUpdateChoice) {
				//case to return to menu
				case 0: {
					System.out.println("Returning to menu...");
						break;
				}
				//case to update project ERF number
				case 1: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ERF NUMBER to update:");
					int updatePRJ_NUM = menuScanner.nextInt();
					menuScanner.nextLine();
					System.out.println("Please enter the updated PROJECT ERF NUMBER:");
					int updatePRJ_ERF_NR = menuScanner.nextInt();
					menuScanner.nextLine();
					
					//try catch block to execute update query
					try {
						statement.executeUpdate("UPDATE info SET PRJ_ERF_NR =" + "'"+updatePRJ_ERF_NR+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}	
						break;
				}
				//case to update project type
				case 2: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT TYPE to update:");
					int updatePRJ_NUM = menuScanner.nextInt();
					menuScanner.nextLine();
					System.out.println("Please enter the UPDATED PROJECT TYPE.");
					String updatePRJ_TYPE = scannerString.nextLine();
					menuScanner.nextLine();
				
					//try catch block to execute update query
					try {
							statement.executeUpdate("UPDATE info SET PRJ_TYPE =" + "'"+updatePRJ_TYPE +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
						break;
				}
				//case to update project address
				case 3: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ADDRESS to update:");
					int updatePRJ_NUM = menuScanner.nextInt();
					menuScanner.nextLine();
					System.out.println("Please enter the UPDATED PROJECT ADDRESS.(Use comma'''s as seperators)");
					String updatePRJ_ADDRESS = scannerString.nextLine();
					
					//try catch block to execute update query
					try {
						statement.executeUpdate("UPDATE info SET PRJ_ADDRESS =" + "'"+updatePRJ_ADDRESS +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");	
					}
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}						
		}
		//while loop to limit user choices to an integer number between 0 and 2
		while(userUpdateChoice <0 || userUpdateChoice >2);	
		}
		
	//method to update project cost information
	public static void updateProjectCost(Statement statement){
	
		//declare variables and scanners for use in method
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerDouble = new Scanner(System.in);
		
		//do while loop to display sub menu and obtain user input
		do {
			//displaying the main menu
			System.out.println( "\nMENU:" + "\n" 
								+ "1. Update PROJECT TOTAL COST." + "\n"
								+ "2. Update PROJECT TOTAL PAID." + "\n"
								+ "0. RETURN TO MENU.");	
        
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = scannerInt.nextInt();
			scannerInt.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >2) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectCost(statement);
			}
			
			//switch block to perform user select operation based on user input
			switch (userUpdateChoice) {
		
			//case to return to menu
			case 0: {
				System.out.println("Returning to menu.");
				break;
			}
			//case to update project total cost
			case 1: {
				System.out.println("Please enter the PROJECT NUMBER of the PROJECT TOTAL COST to update:");
				int updatePRJ_NUM = scannerInt.nextInt();
				scannerInt.nextLine();
				System.out.println("Please enter the updated PROJECT TOTAL COST (Use COMMA as seperator):");
				double updatePRJ_TOTAL_COST = scannerInt.nextDouble();
				scannerInt.nextLine();
				
				//try catch block to execute update query
				try {
					statement.executeUpdate("UPDATE cost SET PRJ_TOTAL_COST =" + "'"+updatePRJ_TOTAL_COST+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
				
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during update operation.");
				}
				break;
			}
			//case to update project total paid
			case 2: {
				System.out.println("Please enter the PROJECT NUMBER of the PROJECT TOTAL PAID to update:");
				int updatePRJ_NUM = scannerInt.nextInt();
				scannerInt.nextLine();
				System.out.println("Please enter the updated PROJECT TOTAL PAID(Use COMMA as seperator).");
				double updatePRJ_TOTAL_PAID = scannerDouble.nextDouble();
				
				//try catch block to execute update query
				try {
					statement.executeUpdate("UPDATE cost SET PRJ_TOTAL_PAID =" + "'"+updatePRJ_TOTAL_PAID +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
				} 
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during update operation.");
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
			}	
		}
		//while loop to limit user choices to an integer number between 0 and 2
		while(userUpdateChoice <0 || userUpdateChoice >2);
	}
		
	//method to update project status information
	public static void updateProjectStatus(Statement statement){
	
		//declare variables and scanners for use in method 
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//do while loop to display menu and obtain user input 
		do {
		
		//displaying the main menu
        System.out.println( "\nMENU:" + "\n" 
          				  + "1. Update PROJECT STATUS." + "\n"
          				  + "2. Update PROJECT DEADLINE." + "\n"
          				  + "3. Update PROJECT COMPLETION DATE." + "\n"
          				  + "0. RETURN TO MENU.");	
        
        System.out.println("\nPlease enter the number of the operation you would like to perform:");
		userUpdateChoice = scannerInt.nextInt();
		scannerInt.nextLine();
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >3) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectStatus(statement);
			}
			
			//switch block to perform selected update methods
			switch (userUpdateChoice) {
				//case to return to menu
				case 0: {
					System.out.println("Returning to menu...");
					break;
				}
				//case to update project status
				case 1: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT STATUS to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT STATUS :");
					String updatePRJ_STATUS = scannerString.nextLine();
					
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE status SET PRJ_STATUS =" + "'"+updatePRJ_STATUS+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );	
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
					break;
				}
				//case to update project deadline
				case 2: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT DEADLINE to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT DEADLINE(FORMAT: YYYY-MM-DD):");
					String updatePRJ_DEADLINE = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE status SET PRJ_DEADLINE =" + "'"+updatePRJ_DEADLINE +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
					break;
				}
				//case to update project completion date 
				case 3: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT COMPLETION DATE to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT COMPLETION DATE(FORMAT: YYYY-MM-DD):");
					String updatePRJ_COMPL_DATE = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE status SET PRJ_COMPL_DATE =" + "'"+updatePRJ_COMPL_DATE +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
	
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
					break;
				}
			
				default:
					throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}
			
		}
		//while loop to limit user choices to an integer number between 0 and 3
		while(userUpdateChoice <0 || userUpdateChoice >3);	
		}		
		
	//method to update project architect information
	public static void updateProjectArchitect(Statement statement){
	
		//declare variables and scanners to be used in method	
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//do while loop to display menu and obtain user input
		do {
			//displaying the update menu
			System.out.println( "\nMENU:" + "\n" 
								+ "1. Update PROJECT ARCHITECT NAME." + "\n"
								+ "2. Update PROJECT ARCHITECT CONTACT." + "\n"
								+ "3. Update PROJECT ARCHITECT EMAIL." + "\n"
								+ "4. Update PROJECT ARCHITECT ADDRESS." + "\n"
								+ "0. RETURN TO MENU.");	
        
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = scannerInt.nextInt();
			scannerInt.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >4) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectArchitect(statement);
			}
			
			//switch block to perform selected update methods
			switch (userUpdateChoice) {
				//case to return to menu
				case 0: {
					System.out.println("Returning to menu...");
					break;
				}
				//case to update project architect name
				case 1: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ARCHITECT NAME to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT ARCHITECT NAME :");
					String updatePRJ_ARCH_NAME = scannerString.nextLine();
					
					//try catch block to execute update query
					try {
						statement.executeUpdate("UPDATE architects SET PRJ_ARCH_NAME =" + "'"+updatePRJ_ARCH_NAME+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
					break;
				}
				//case to update project architect contact
				case 2: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ARCHITECT CONTACT to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT ARCHITECT CONTACT:");
					String updatePRJ_ARCH_CONTACT = scannerString.nextLine();
					
					//try catch block to execute update query
					try {
						statement.executeUpdate("UPDATE architects SET PRJ_ARCH_CONTACT =" + "'"+updatePRJ_ARCH_CONTACT +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				
				//case to update project architect email
				case 3: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ARCHITECT EMAIL to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT ARCHITECT EMAIL:");
					String updatePRJ_ARCH_EMAIL = scannerString.nextLine();
				
					//try catch block to perform update query
					try {statement.executeUpdate("UPDATE architects SET PRJ_ARCH_EMAIL =" + "'"+updatePRJ_ARCH_EMAIL +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during ipdate operation.");
					}
					break;
				}
				
				//case to update project architect address
				case 4: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT ARCHITECT ADDRESS to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT ARCHITECT ADDRESS:");
					String updatePRJ_ARCH_ADDRESS = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE architects SET PRJ_ARCH_ADDRESS =" + "'"+updatePRJ_ARCH_ADDRESS +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation.");
					}
					break;
				}
			
				default:
					throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}
			
			}
		//while loop to limit user choices to an integer number between 0 and 2
		while(userUpdateChoice <0 || userUpdateChoice >4);
	}		
	
	//method to update project customer information
	public static void updateProjectCustomer(Statement statement){
	
		//declare variables and scanners to be used in method
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
		
		//do while block to display menu and obtain user input
		do {
			//displaying the update menu
			System.out.println( "\nMENU:" + "\n" 
								+ "1. Update PROJECT ARCHITECT NAME." + "\n"
								+ "2. Update PROJECT ARCHITECT CONTACT." + "\n"
								+ "3. Update PROJECT ARCHITECT EMAIL." + "\n"
								+ "4. Update PROJECT ARCHITECT ADDRESS." + "\n"
								+ "0. RETURN TO MENU.");	
      
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = scannerInt.nextInt();
			scannerInt.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >4) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectCustomer(statement);
			}
			
			//switch block to perform update requests 
			switch (userUpdateChoice) {
				//case to return to menu
				case 0: {
					System.out.println("Returning to menu...");
					break;
				}
				//case to update project customer name
				case 1: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT CUSTOMER NAME to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT CUSTOMER NAME :");
					String updatePRJ_CUST_NAME = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE customers SET PRJ_CUST_NAME =" + "'"+updatePRJ_CUST_NAME+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project customer contact
				case 2: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT CUSTOMER CONTACT to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT CUSTOMER CONTACT:");
					String updatePRJ_CUST_CONTACT = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE customers SET PRJ_CUST_CONTACT =" + "'"+updatePRJ_CUST_CONTACT +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project customer email
				case 3: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT CUSTOMER EMAIL to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT CUSTOMER EMAIL:");
					String updatePRJ_CUST_EMAIL = scannerString.nextLine();
				
					//try catch block to perform update query
					try {
						statement.executeUpdate("UPDATE customers SET PRJ_CUST_EMAIL =" + "'"+updatePRJ_CUST_EMAIL +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project customer address
				case 4: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT CUSTOMER ADDRESS to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT CUSTOMER ADDRESS:");
					String updatePRJ_CUST_ADDRESS = scannerString.nextLine();
				
					try {
						statement.executeUpdate("UPDATE customers SET PRJ_CUST_ADDRESS =" + "'"+updatePRJ_CUST_ADDRESS +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
			
				default:
					throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}
		}
		//while loop to limit user choices to an integer number between 0 and 2
		while(userUpdateChoice <0 || userUpdateChoice >4);
	}				

	//method to perform project engineer updates
	public static void updateProjectEngineer(Statement statement){
		//declare variables and scanners to be used in the method
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
		
			//do while loop to display menu and obtain user input
			do {
		
				//displaying the menu
				System.out.println( "\nMENU:" + "\n" 
									+ "1. Update PROJECT ENGINEER (STRUCTURAL) NAME." + "\n"
									+ "2. Update PROJECT ENGINEER (STRUCTURAL) CONTACT." + "\n"
									+ "3. Update PROJECT ENGINEER (STRUCTURAL) EMAIL." + "\n"
									+ "4. Update PROJECT ENGINEER (STRUCTURAL) ADDRESS." + "\n"
									+ "0. RETURN TO MENU.");	
        
				System.out.println("\nPlease enter the number of the operation you would like to perform:");
				userUpdateChoice = scannerInt.nextInt();
				scannerInt.nextLine();
				//if statement to alert user to invalid input and request new input
				if(userUpdateChoice <0 || userUpdateChoice >4) {
					System.out.println("You have entered an invalid choice. Please try again.");
					updateProjectEngineer(statement);
				}
			
				//switch block to perform selected operations	
				switch (userUpdateChoice) {
					//case to return to menu
					case 0: {
						System.out.println("Returining to menu...");
						break;
					}
					//case to update project engineer name
					case 1: {
						System.out.println("Please enter the PROJECT NUMBER of the PROJECT ENGINEER (STRUCTURAL) NAME to update:");
						int updatePRJ_NUM = scannerInt.nextInt();
						scannerInt.nextLine();
						System.out.println("Please enter the updated PROJECT CUSTOMER NAME :");
						String updatePRJ_SENG_NAME = scannerString.nextLine();
				
						//try catch block to execute update query
						try {
							statement.executeUpdate("UPDATE engineers SET PRJ_SENG_NAME =" + "'"+updatePRJ_SENG_NAME+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
						} 
						catch (SQLException e) {
							e.printStackTrace();
							System.out.println("Error occured during update operation");
						}
				
						break;
					}
					//case to update project engineer contact
					case 2: {
						System.out.println("Please enter the PROJECT NUMBER of the PROJECT ENGINEER (STRUCTURAL) CONTACT to update:");
						int updatePRJ_NUM = scannerInt.nextInt();
						scannerInt.nextLine();
						System.out.println("Please enter the updated PROJECT ENGINEER (STRUCTURAL) CONTACT:");
						String updatePRJ_SENG_CONTACT = scannerString.nextLine();
						
						//try catch block to execute query
						try {
							statement.executeUpdate("UPDATE engineers SET PRJ_SENG_CONTACT =" + "'"+updatePRJ_SENG_CONTACT +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
						} 
						catch (SQLException e) {
							e.printStackTrace();
							System.out.println("Error occured during update operation");
						}
						break;
					}
					//case to update project engineer email
					case 3: {
						System.out.println("Please enter the PROJECT NUMBER of the PROJECT ENGINEER (STRUCTURAL) EMAIL to update:");
						int updatePRJ_NUM = scannerInt.nextInt();
						scannerInt.nextLine();
						System.out.println("Please enter the updated PROJECT ENGINEER (STRUCTURAL) EMAIL:");
						String updatePRJ_SENG_EMAIL = scannerString.nextLine();
				
						//try catch block to execute update
						try {
							statement.executeUpdate("UPDATE engineers SET PRJ_SENG_EMAIL =" + "'"+updatePRJ_SENG_EMAIL +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
						} 
						catch (SQLException e) {
							e.printStackTrace();
							System.out.println("Error occured during update operation");
						}				
						break;
					}
					//case to update project engineer address
					case 4: {
						System.out.println("Please enter the PROJECT NUMBER of the PROJECT ENGINEER (STRUCTURAL) ADDRESS to update:");
						int updatePRJ_NUM = scannerInt.nextInt();
						scannerInt.nextLine();
						System.out.println("Please enter the updated PROJECT ENGINEER (STRUCTURAL) ADDRESS:");
						String updatePRJ_SENG_ADDRESS = scannerString.nextLine();
				
						//try catch block to execute update
						try {
							statement.executeUpdate("UPDATE engineers SET PRJ_SENG_ADDRESS =" + "'"+updatePRJ_SENG_ADDRESS +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
						} 
						catch (SQLException e) {
							e.printStackTrace();
							System.out.println("Error occured during update operation");
						}				
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}
			}
			//while loop to limit user choices to an integer number between 0 and 2
			while(userUpdateChoice <0 || userUpdateChoice >4);
	}				

	//method to perform project manager updates
	public static void updateProjectManager(Statement statement){
		
		//variables and scanners to be used in method
		int userUpdateChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
		
		//do while loop to display menu and obtain user input
		do {
			//displaying the menu
			System.out.println( "\nMENU:" + "\n" 
								+ "1. Update PROJECT MANAGER NAME." + "\n"
								+ "2. Update PROJECT MANAGER CONTACT." + "\n"
								+ "3. Update PROJECT MANAGER EMAIL." + "\n"
								+ "4. Update PROJECT MANAGER ADDRESS." + "\n"
								+ "0. RETURN TO MENU.");	
        
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userUpdateChoice = scannerInt.nextInt();
			scannerInt.nextLine();
			
			//if statement to alert user to invalid input and request new input
			if(userUpdateChoice <0 || userUpdateChoice >4) {
				System.out.println("You have entered an invalid choice. Please try again.");
				updateProjectManager(statement);
			}
			
			//switch block to perform selected operation
			switch (userUpdateChoice) {
				//case to return to menu
				case 0: {
					System.out.println("Returning to menu...");
					break;
				}
				//case to update project manager name
				case 1: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT MANAGER NAME to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT CUSTOMER NAME :");
					String updatePRJ_MAN_NAME = scannerString.nextLine();
				
					//try catch block to execute update
					try {
						statement.executeUpdate("UPDATE managers SET PRJ_MAN_NAME =" + "'"+updatePRJ_MAN_NAME+"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project manager contact
				case 2: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT MANAGER CONTACT to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT MANAGER CONTACT:");
					String updatePRJ_MAN_CONTACT = scannerString.nextLine();
				
					//try catch block to execute update
					try {
						statement.executeUpdate("UPDATE managers SET PRJ_MAN_CONTACT =" + "'"+updatePRJ_MAN_CONTACT +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project manager email
				case 3: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT MANAGER EMAIL to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT MANAGER EMAIL:");
					String updatePRJ_MAN_EMAIL = scannerString.nextLine();
				
					//try catch block to execute update
					try {
					statement.executeUpdate("UPDATE managers SET PRJ_MAN_EMAIL =" + "'"+updatePRJ_MAN_EMAIL +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );
					} 
					catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Error occured during update operation");
					}
					break;
				}
				//case to update project manager address
				case 4: {
					System.out.println("Please enter the PROJECT NUMBER of the PROJECT MANAGER ADDRESS to update:");
					int updatePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					System.out.println("Please enter the updated PROJECT MANAGER ADDRESS:");
					String updatePRJ_MAN_ADDRESS = scannerString.nextLine();
				
					//try catch block to execute update
				try {
					statement.executeUpdate("UPDATE managers SET PRJ_MAN_ADDRESS =" + "'"+updatePRJ_MAN_ADDRESS +"'" + "WHERE PRJ_NUM = " + updatePRJ_NUM );			
				} 
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during update operation");
				}
				break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + userUpdateChoice);
				}
			}
		//while loop to limit user choices to an integer number between 0 and 2
		while(userUpdateChoice <0 || userUpdateChoice >4);
	}				

	//method to remove project from database
	public static void removeProject(Statement statement){
	
		//variables and scanners to be used in method
		int userRemoveChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//do while loop to display menu and and obtain user input
		do {
			//displaying the main menu
			System.out.println( "\nMENU:" + "\n" 
								+ "1. Remove project." + "\n"
								+ "0. RETURN TO MENU.");	
        
			System.out.println("\nPlease enter the number of the operation you would like to perform:");
			userRemoveChoice = scannerInt.nextInt();
			
			
			//if, else if statement to alert user to invalid input and request new input and to perform selected operation
			if(userRemoveChoice <0 || userRemoveChoice >1) {
				System.out.println("You have entered an invalid choice. Please try again.");
			}
			else if(userRemoveChoice == 1) {
				//try catch block to perform delete
				try {
					System.out.println("Please enter the PROJECT NUMBER you wish to remove:");
					int removePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					statement.executeUpdate("DELETE FROM projects WHERE PRJ_NUM = " + removePRJ_NUM );
				} 
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during delete operation");
				}
								
			}
		}
		//while loop to limit user choices to an integer number between 0 and 1
		while(userRemoveChoice <0 || userRemoveChoice >1);
	}				

	//method to finalise a project
	public static void finalizeProject(Statement statement){
	
		//variables and scanners to be used in method
		int userRemoveChoice = 0;
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//do while loop to display menu and obtain user input
		do {
		
		//displaying the main menu
        System.out.println( "\nMENU:" + "\n" 
          				  + "1. FINALIZE project." + "\n"
          				  + "0. RETURN TO MENU.");	
        
        System.out.println("\nPlease enter the number of the operation you would like to perform:");
		userRemoveChoice = scannerInt.nextInt();
			//if,else if statement to alert user to invalid input and request new input and perform requested operation
			if(userRemoveChoice <0 || userRemoveChoice >1) {
				System.out.println("You have entered an invalid choice. Please try again.");
			}
			else if(userRemoveChoice == 1) {
				//try catch block to execute update
				try {
					System.out.println("Please enter the PROJECT NUMBER you wish to finalize:");
					int finalizePRJ_NUM = scannerInt.nextInt();
					scannerInt.nextLine();
					statement.executeUpdate("UPDATE status SET PRJ_STATUS = " + "'FINALIZED'" + " WHERE PRJ_NUM = " + finalizePRJ_NUM );
					System.out.println("Please enter the PROJECT COMPLETION DATE(FORMAT: YYYY-MM-DD):");
					String PRJ_COMPL_DATE = scannerString.nextLine();
					statement.executeUpdate("UPDATE status SET PRJ_COMPL_DATE =" + "'"+ PRJ_COMPL_DATE +"'" + "WHERE PRJ_NUM = " + finalizePRJ_NUM );	
				} 
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during finalise operation");
				}	
			}
		}
		//while loop to limit user choices to an integer number between 0 and 1
		while(userRemoveChoice <0 || userRemoveChoice >1);
	}				

	//method to display projects that are incomplete
	public static void incompleteProjects(Statement statement){
		
		//displaying the title
        System.out.println( "\nINCOMPLETE PROJECTS:" + "\n");	
        	//try catch block to display incomplete projects based on PENDING value of PRJ_STATUS in status table.
        	try {
				ResultSet results = statement.executeQuery(
						"SELECT "
						+ "projects.PRJ_NUM, projects.PRJ_NAME, "
						+ "status.PRJ_STATUS, status.PRJ_DEADLINE FROM status "
						+ "INNER JOIN projects "
						+ "ON status.PRJ_NUM = projects.PRJ_NUM "
						+ "WHERE status.PRJ_STATUS = 'PENDING'" );
				
				//while loop to display information
				while (results.next()) {
					System.out.println("PROJECT NUMBER   : "+ "\t\t" + results.getInt("PRJ_NUM") + "\n" +
			           				   "PROJECT NAME     : "+ "\t\t"+ results.getString("PRJ_NAME") + "\n" +
			           				   "PROJECT STATUS   : "+ "\t\t"+ results.getString("PRJ_STATUS") + "\n" +        
			           				   "PROJECT DEADLINE : "+ "\t\t"+ results.getString("PRJ_DEADLINE")+ "\n"
			        );
				};
        	} 
        	catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during display incomplete projects operation");
			}
	}
		
	//method to display projects that are past the deadline, e.g. that are late
	public static void lateProjects(Statement statement){
	
		//displaying the heading
        System.out.println( "\nPROJECTS PAST DEADLINE:" + "\n");	
        
        //try catch block to display late projects by comparing the deadline date PRJ_DEADLINE with the current local date and where the PRJ_STATUS value = pending.
				try {
					LocalDate currentDate = LocalDate.now();
					ResultSet results = statement.executeQuery(
							"SELECT "
							+ "projects.PRJ_NUM, projects.PRJ_NAME, "
							+ "status.PRJ_STATUS, status.PRJ_DEADLINE "
							+ "FROM status "
							+ "INNER JOIN projects "
							+ "ON status.PRJ_NUM = projects.PRJ_NUM "
							+ "WHERE status.PRJ_DEADLINE < " + "'" +currentDate +"'" 
							+ "AND status.PRJ_STATUS = 'PENDING'" );
					//while loop to display information
					while (results.next()) {
			            System.out.println("PROJECT NUMBER   : "+ "\t\t" + results.getInt("PRJ_NUM") + "\n" +
			            				   "PROJECT NAME     : "+ "\t\t"+ results.getString("PRJ_NAME") + "\n" +
			            				   "PROJECT STATUS   : "+ "\t\t"+ results.getString("PRJ_STATUS") + "\n" +        
			            				   "PROJECT DEADLINE : "+ "\t\t"+ results.getString("PRJ_DEADLINE")+ "\n"
			            );
					};
					
				} 
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during display late projects operation");
				}
	}

	//method to find and display a project based on the project number value (PRJ_NUM)
	public static void findProjectNumber(Statement statement){
	
		//declare scanners to be used in method
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//displaying the heading
        System.out.println( "\nSearch PROJECTS vie PROJECT NUMBER:" + "\n");	
        //request project number to be searched from user
        System.out.println("Please enter PROJECT NUMBER:");
        	int searchPRJ_NUM = scannerInt.nextInt();
        	scannerInt.nextLine();
				//try catch block to obtain information from tables
        		try {
					LocalDate currentDate = LocalDate.now();
					ResultSet results = statement.executeQuery("SELECT "
							+ "projects.PRJ_NUM, projects.PRJ_NAME, "
							+ "status.PRJ_STATUS, status.PRJ_DEADLINE, status.PRJ_COMPL_DATE, "
							+ "info.PRJ_ERF_NR, info.PRJ_TYPE, info.PRJ_ADDRESS,                                                           "
							+ "cost.PRJ_TOTAL_COST, cost.PRJ_TOTAL_PAID,    "
							+ "architects.PRJ_ARCH_NAME, architects.PRJ_ARCH_CONTACT,architects.PRJ_ARCH_EMAIL,architects.PRJ_ARCH_ADDRESS,    "
							+ "customers.PRJ_CUST_NAME, customers.PRJ_CUST_CONTACT,customers.PRJ_CUST_EMAIL,customers.PRJ_CUST_ADDRESS,    "
							+ "engineers.PRJ_SENG_NAME, engineers.PRJ_SENG_CONTACT,engineers.PRJ_SENG_EMAIL,engineers.PRJ_SENG_ADDRESS,    "
							+ "managers.PRJ_MAN_NAME, managers.PRJ_MAN_CONTACT,managers.PRJ_MAN_EMAIL,managers.PRJ_MAN_ADDRESS    "
							+ "FROM projects "
							+ "INNER JOIN status ON status.PRJ_NUM = projects.PRJ_NUM "
							+ "INNER JOIN info ON info.PRJ_NUM = projects.PRJ_NUM      "	
							+ "INNER JOIN cost ON cost.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN architects ON architects.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN customers ON customers.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN engineers ON engineers.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN managers ON managers.PRJ_NUM = projects.PRJ_NUM      "
							+ "WHERE status.PRJ_NUM = " + "'" +searchPRJ_NUM +"'");
					
					//while loop to display information
					while (results.next()) {
			            System.out.println("PROJECT NUMBER          	: "+ "\t\t" + results.getInt("PRJ_NUM") + "\n" +
			            				   "PROJECT NAME            	: "+ "\t\t"+ results.getString("PRJ_NAME") + "\n" +
			            				   "PROJECT STATUS          	: "+ "\t\t"+ results.getString("PRJ_STATUS") + "\n" +        
			            				   "PROJECT DEADLINE        	: "+ "\t\t"+ results.getString("PRJ_DEADLINE")+ "\n" +
			            				   "PROJECT COMPLETION DATE 	: "+ "\t\t"+ results.getString("PRJ_COMPL_DATE")+ "\n" +
			            				   "PROJECT ERF NUMBER      	: "+ "\t\t"+ results.getString("PRJ_ERF_NR")+ "\n" +
			            				   "PROJECT TYPE            	: "+ "\t\t"+ results.getString("PRJ_TYPE")+ "\n" +
			            				   "PROJECT ADDRESS         	: "+ "\t\t"+ results.getString("PRJ_ADDRESS")+ "\n" +
			            				   "PROJECT TOTAL COST      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_COST")+ "\n" +
			            				   "PROJECT TOTAL PAID      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_PAID")+ "\n" +
			            				   "PROJECT ARCHITECT NAME  	: "+ "\t\t"+ results.getString("PRJ_ARCH_NAME")+ "\n" +
			            				   "PROJECT ARCHITECT CONTACT	: "+ "\t\t"+ results.getString("PRJ_ARCH_CONTACT")+ "\n" +
			            				   "PROJECT ARCHITECT EMAIL    	: "+ "\t\t"+ results.getString("PRJ_ARCH_EMAIL")+ "\n" +
			            				   "PROJECT ARCHITECT ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_ARCH_ADDRESS")+ "\n" +
			            				   "PROJECT CUSTOMER NAME  		: "+ "\t\t"+ results.getString("PRJ_CUST_NAME")+ "\n" +
			            				   "PROJECT CUSTOMER CONTACT	: "+ "\t\t"+ results.getString("PRJ_CUST_CONTACT")+ "\n" +
			            				   "PROJECT CUSTOMER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_CUST_EMAIL")+ "\n" +
			            				   "PROJECT CUSTOMER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_CUST_ADDRESS")+ "\n" +
			            				   "PROJECT ENGINEER NAME  		: "+ "\t\t"+ results.getString("PRJ_SENG_NAME")+ "\n" +
			            				   "PROJECT ENGINEER CONTACT	: "+ "\t\t"+ results.getString("PRJ_SENG_CONTACT")+ "\n" +
			            				   "PROJECT ENGINEER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_SENG_EMAIL")+ "\n" +
			            				   "PROJECT ENGINEER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_SENG_ADDRESS")+ "\n" +
			            				   "PROJECT MANAGER NAME  		: "+ "\t\t"+ results.getString("PRJ_MAN_NAME")+ "\n" +
			            				   "PROJECT MANAGER CONTACT   	: "+ "\t\t"+ results.getString("PRJ_MAN_CONTACT")+ "\n" +
			            				   "PROJECT MANAGER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_MAN_EMAIL")+ "\n" +
			            				   "PROJECT MANAGER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_MAN_ADDRESS")+ "\n" 			   
			            );
					};
					
					
				}
        		catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during display projects based on project number operation.");
				}
	}

	public static void findProjectName(Statement statement){
	
		//declare scanner to be used in method
		Scanner scannerInt = new Scanner(System.in);
		Scanner scannerString = new Scanner(System.in);
	
		//displaying the heading and requesting input from user
        System.out.println( "\nSearch PROJECTS via PROJECT NUMBER:" + "\n");	
        System.out.println("Please enter PROJECT NAME:");
        String searchPRJ_NAME = scannerString.nextLine();
			
        	//try catch block to obtain information from tables
        	try {
					LocalDate currentDate = LocalDate.now();
					ResultSet results = statement.executeQuery(
							"SELECT "
							+ "projects.PRJ_NUM, projects.PRJ_NAME, "
							+ "status.PRJ_STATUS, status.PRJ_DEADLINE, status.PRJ_COMPL_DATE, "
							+ "info.PRJ_ERF_NR, info.PRJ_TYPE, info.PRJ_ADDRESS,                                                           "
							+ "cost.PRJ_TOTAL_COST, cost.PRJ_TOTAL_PAID,    "
							+ "architects.PRJ_ARCH_NAME, architects.PRJ_ARCH_CONTACT,architects.PRJ_ARCH_EMAIL,architects.PRJ_ARCH_ADDRESS,    "
							+ "customers.PRJ_CUST_NAME, customers.PRJ_CUST_CONTACT,customers.PRJ_CUST_EMAIL,customers.PRJ_CUST_ADDRESS,    "
							+ "engineers.PRJ_SENG_NAME, engineers.PRJ_SENG_CONTACT,engineers.PRJ_SENG_EMAIL,engineers.PRJ_SENG_ADDRESS,    "
							+ "managers.PRJ_MAN_NAME, managers.PRJ_MAN_CONTACT,managers.PRJ_MAN_EMAIL,managers.PRJ_MAN_ADDRESS    "
							+ "FROM projects "
							+ "INNER JOIN status ON status.PRJ_NUM = projects.PRJ_NUM "
							+ "INNER JOIN info ON info.PRJ_NUM = projects.PRJ_NUM      "	
							+ "INNER JOIN cost ON cost.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN architects ON architects.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN customers ON customers.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN engineers ON engineers.PRJ_NUM = projects.PRJ_NUM      "
							+ "INNER JOIN managers ON managers.PRJ_NUM = projects.PRJ_NUM      "
							+ "WHERE projects.PRJ_NAME = " + "'" +searchPRJ_NAME +"'");
					
					//while loop to display information
					while (results.next()) {
			            System.out.println("PROJECT NUMBER          	: "+ "\t\t" + results.getInt("PRJ_NUM") + "\n" +
			            				   "PROJECT NAME            	: "+ "\t\t"+ results.getString("PRJ_NAME") + "\n" +
			            				   "PROJECT STATUS          	: "+ "\t\t"+ results.getString("PRJ_STATUS") + "\n" +        
			            				   "PROJECT DEADLINE        	: "+ "\t\t"+ results.getString("PRJ_DEADLINE")+ "\n" +
			            				   "PROJECT COMPLETION DATE 	: "+ "\t\t"+ results.getString("PRJ_COMPL_DATE")+ "\n" +
			            				   "PROJECT ERF NUMBER      	: "+ "\t\t"+ results.getString("PRJ_ERF_NR")+ "\n" +
			            				   "PROJECT TYPE            	: "+ "\t\t"+ results.getString("PRJ_TYPE")+ "\n" +
			            				   "PROJECT ADDRESS         	: "+ "\t\t"+ results.getString("PRJ_ADDRESS")+ "\n" +
			            				   "PROJECT TOTAL COST      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_COST")+ "\n" +
			            				   "PROJECT TOTAL PAID      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_PAID")+ "\n" +
			            				   "PROJECT ARCHITECT NAME  	: "+ "\t\t"+ results.getString("PRJ_ARCH_NAME")+ "\n" +
			            				   "PROJECT ARCHITECT CONTACT	: "+ "\t\t"+ results.getString("PRJ_ARCH_CONTACT")+ "\n" +
			            				   "PROJECT ARCHITECT EMAIL    	: "+ "\t\t"+ results.getString("PRJ_ARCH_EMAIL")+ "\n" +
			            				   "PROJECT ARCHITECT ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_ARCH_ADDRESS")+ "\n" +
			            				   "PROJECT CUSTOMER NAME  		: "+ "\t\t"+ results.getString("PRJ_CUST_NAME")+ "\n" +
			            				   "PROJECT CUSTOMER CONTACT	: "+ "\t\t"+ results.getString("PRJ_CUST_CONTACT")+ "\n" +
			            				   "PROJECT CUSTOMER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_CUST_EMAIL")+ "\n" +
			            				   "PROJECT CUSTOMER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_CUST_ADDRESS")+ "\n" +
			            				   "PROJECT ENGINEER NAME  		: "+ "\t\t"+ results.getString("PRJ_SENG_NAME")+ "\n" +
			            				   "PROJECT ENGINEER CONTACT	: "+ "\t\t"+ results.getString("PRJ_SENG_CONTACT")+ "\n" +
			            				   "PROJECT ENGINEER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_SENG_EMAIL")+ "\n" +
			            				   "PROJECT ENGINEER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_SENG_ADDRESS")+ "\n" +
			            				   "PROJECT MANAGER NAME  		: "+ "\t\t"+ results.getString("PRJ_MAN_NAME")+ "\n" +
			            				   "PROJECT MANAGER CONTACT   	: "+ "\t\t"+ results.getString("PRJ_MAN_CONTACT")+ "\n" +
			            				   "PROJECT MANAGER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_MAN_EMAIL")+ "\n" +
			            				   "PROJECT MANAGER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_MAN_ADDRESS")+ "\n\n" 
			            );
					};
			} 
        	catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during display projects based on project number operation.");
        	}	
	}

	//method to display information of all projects 
	public static void displayAll(Statement statement){
	
		//displaying the heading
        System.out.println( "\nSearch PROJECTS vie PROJECT NUMBER:" + "\n");
        
			//try catch block t obtain information from tables	
        	try {
				ResultSet results = statement.executeQuery(
						"SELECT "
						+ "projects.PRJ_NUM, projects.PRJ_NAME, "
						+ "status.PRJ_STATUS, status.PRJ_DEADLINE, status.PRJ_COMPL_DATE, "
						+ "info.PRJ_ERF_NR, info.PRJ_TYPE, info.PRJ_ADDRESS,                                                           "
						+ "cost.PRJ_TOTAL_COST, cost.PRJ_TOTAL_PAID,    "
						+ "architects.PRJ_ARCH_NAME, architects.PRJ_ARCH_CONTACT,architects.PRJ_ARCH_EMAIL,architects.PRJ_ARCH_ADDRESS,    "
						+ "customers.PRJ_CUST_NAME, customers.PRJ_CUST_CONTACT,customers.PRJ_CUST_EMAIL,customers.PRJ_CUST_ADDRESS,    "
						+ "engineers.PRJ_SENG_NAME, engineers.PRJ_SENG_CONTACT,engineers.PRJ_SENG_EMAIL,engineers.PRJ_SENG_ADDRESS,    "
						+ "managers.PRJ_MAN_NAME, managers.PRJ_MAN_CONTACT,managers.PRJ_MAN_EMAIL,managers.PRJ_MAN_ADDRESS    "
						+ "FROM projects "
						+ "INNER JOIN status ON status.PRJ_NUM = projects.PRJ_NUM "
						+ "INNER JOIN info ON info.PRJ_NUM = projects.PRJ_NUM      "	
						+ "INNER JOIN cost ON cost.PRJ_NUM = projects.PRJ_NUM      "
						+ "INNER JOIN architects ON architects.PRJ_NUM = projects.PRJ_NUM      "
						+ "INNER JOIN customers ON customers.PRJ_NUM = projects.PRJ_NUM      "
						+ "INNER JOIN engineers ON engineers.PRJ_NUM = projects.PRJ_NUM      "
						+ "INNER JOIN managers ON managers.PRJ_NUM = projects.PRJ_NUM "
						+ "ORDER BY PRJ_NUM");
				
				//while loop to iterate over and display information	
					while (results.next()) {
			            System.out.println("PROJECT NUMBER          	: "+ "\t\t" + results.getInt("PRJ_NUM") + "\n" +
			            				   "PROJECT NAME            	: "+ "\t\t"+ results.getString("PRJ_NAME") + "\n" +
			            				   "PROJECT STATUS          	: "+ "\t\t"+ results.getString("PRJ_STATUS") + "\n" +        
			            				   "PROJECT DEADLINE        	: "+ "\t\t"+ results.getString("PRJ_DEADLINE")+ "\n" +
			            				   "PROJECT COMPLETION DATE 	: "+ "\t\t"+ results.getString("PRJ_COMPL_DATE")+ "\n" +
			            				   "PROJECT ERF NUMBER      	: "+ "\t\t"+ results.getString("PRJ_ERF_NR")+ "\n" +
			            				   "PROJECT TYPE            	: "+ "\t\t"+ results.getString("PRJ_TYPE")+ "\n" +
			            				   "PROJECT ADDRESS         	: "+ "\t\t"+ results.getString("PRJ_ADDRESS")+ "\n" +
			            				   "PROJECT TOTAL COST      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_COST")+ "\n" +
			            				   "PROJECT TOTAL PAID      	: "+ "\t\tR"+ results.getString("PRJ_TOTAL_PAID")+ "\n" +
			            				   "PROJECT ARCHITECT NAME  	: "+ "\t\t"+ results.getString("PRJ_ARCH_NAME")+ "\n" +
			            				   "PROJECT ARCHITECT CONTACT	: "+ "\t\t"+ results.getString("PRJ_ARCH_CONTACT")+ "\n" +
			            				   "PROJECT ARCHITECT EMAIL    	: "+ "\t\t"+ results.getString("PRJ_ARCH_EMAIL")+ "\n" +
			            				   "PROJECT ARCHITECT ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_ARCH_ADDRESS")+ "\n" +
			            				   "PROJECT CUSTOMER NAME  		: "+ "\t\t"+ results.getString("PRJ_CUST_NAME")+ "\n" +
			            				   "PROJECT CUSTOMER CONTACT	: "+ "\t\t"+ results.getString("PRJ_CUST_CONTACT")+ "\n" +
			            				   "PROJECT CUSTOMER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_CUST_EMAIL")+ "\n" +
			            				   "PROJECT CUSTOMER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_CUST_ADDRESS")+ "\n" +
			            				   "PROJECT ENGINEER NAME  		: "+ "\t\t"+ results.getString("PRJ_SENG_NAME")+ "\n" +
			            				   "PROJECT ENGINEER CONTACT	: "+ "\t\t"+ results.getString("PRJ_SENG_CONTACT")+ "\n" +
			            				   "PROJECT ENGINEER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_SENG_EMAIL")+ "\n" +
			            				   "PROJECT ENGINEER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_SENG_ADDRESS")+ "\n" +
			            				   "PROJECT MANAGER NAME  		: "+ "\t\t"+ results.getString("PRJ_MAN_NAME")+ "\n" +
			            				   "PROJECT MANAGER CONTACT   	: "+ "\t\t"+ results.getString("PRJ_MAN_CONTACT")+ "\n" +
			            				   "PROJECT MANAGER EMAIL    	: "+ "\t\t"+ results.getString("PRJ_MAN_EMAIL")+ "\n" +
			            				   "PROJECT MANAGER ADDRESS 	: "+ "\t\t"+ results.getString("PRJ_MAN_ADDRESS")+ "\n\n" 
			            			   
			            );
					};
        	} 
        	catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Error occured during display all projects operation.");
        	}	
	};
};

/*REFERENCES AND RESOURCES:
1. budhirajachinab99 (2022) Types of statements in JDBC, GeeksforGeeks. Available at: https://www.geeksforgeeks.org/types-of-statements-in-jdbc/ (Accessed: 15 September 2023). 
2. Babu, R. (2021) DELETE CASCADE AND UPDATE CASCADE IN SQL server foreign key, SQL Shack -DELETE CASCADE and UPDATE CASCADE in SQL Server foreign key. Available at: https://www.sqlshack.com/delete-cascade-and-update-cascade-in-sql-server-foreign-key/ (Accessed: 15 September 2023). 
3. Drkusic, E. (2021) Learn SQL: Join multiple tables, SQL Shack -&nbsp; Join multiple tables. Available at: https://www.sqlshack.com/learn-sql-join-multiple-tables/ (Accessed: 15 September 2023). 
4. decodejava  Update Records in a table with JDBC, How to use UPDATE statement using JDBC - Decodejava.com. Available at: https://www.decodejava.com/jdbc-update-statement.htm (Accessed: 15 September 2023). 
5. GeeksforGeeks (2021) Join multiple tables using inner join, GeeksforGeeks. Available at: https://www.geeksforgeeks.org/join-multiple-tables-using-inner-join/ (Accessed: 15 September 2023). 
6. Hyperiondev 2018 ‘Java Database Programming, Testing &amp; Documentation’. Hyperiondev. 
7. javatpoint Java resultset , www.javatpoint.com. Available at: https://www.javatpoint.com/ResultSet-interface (Accessed: 17 August 2023). 
8. javatpoint Java statement , www.javatpoint.com. Available at: https://www.javatpoint.com/Statement-interface (Accessed: 17 August 2023). 
9. javaTpoint Java database connectivity with mysql - javatpoint, www.javatpoint.com. Available at: https://www.javatpoint.com/example-to-connect-to-the-mysql-database (Accessed: 15 September 2023). 
10. MySQL Tutorial (2020) MySQL insert multiple rows by practical examples, MySQL Tutorial. Available at: https://www.mysqltutorial.org/mysql-insert-multiple-rows/ (Accessed: 15 September 2023).
11.	Oracle (2023) Class SQLException, SQLException (Java Platform SE 8 ). Available at: https://docs.oracle.com/javase/8/docs/api/java/sql/SQLException.html (Accessed: 15 September 2023). 
12.	Oracle (2023) Interface Connection, Connection (java platform SE 8 ). Available at: https://docs.oracle.com/javase/8/docs/api/java/sql/Connection.html (Accessed: 15 September 20233). 
13.	Oracle Handling Sqlexceptions, Handling SQLExceptions (The JavaTM Tutorials &gt; JDBC Database Access &gt; JDBC Basics). Available at: https://docs.oracle.com/javase/tutorial/jdbc/basics/sqlexception.html (Accessed: 15 September 2023). 
14.	Oracle Processing SQL statements with JDBC, Processing SQL Statements with JDBC (The JavaTM Tutorials &gt; JDBC Database Access &gt; JDBC Basics). Available at: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html Accessed: 15 September 2023). 
15.	tutorialsPoint JDBC - statements, preparedstatement and Callablestatement, Tutorialspoint. Available at: https://www.tutorialspoint.com/jdbc/jdbc-statements.htm#:~:text=The%20JDBC%20Statement%2C%20CallableStatement%2C%20and,types%20used%20in%20a%20database. (Accessed: 15 September 2023). 
16. w3schools (no date) Java date and Time. Available at: https://www.w3schools.com/java/java_date.asp (Accessed: 15 September 2023). 
17.	w3schools (no date) Java exceptions - try...catch, Java Exceptions (Try...Catch). Available at: https://www.w3schools.com/java/java_try_catch.asp (Accessed: 15 September 2023). 
18.	w3schools (no date) Java user input (scanner), Java User Input (Scanner class). Available at: https://www.w3schools.com/java/java_user_input.asp (Accessed: 15 September 2023). 
19.	w3schools (no date) Java While Loop, Java while loop. Available at: https://www.w3schools.com/java/java_while_loop.asp (Accessed: 15 September 2023). 
20.	w3schools (no date) Java switch. Available at: https://www.w3schools.com/java/java_switch.asp (Accessed: 15 September 2023). 
21. w3schools (no date) SQL CREATE DATABASE Statement, SQL create database statement. Available at: https://www.w3schools.com/sql/sql_create_db.asp (Accessed: 15 September 2023). 
22. w3schools (no date) SQL INNER JOIN Keyword, SQL inner join keyword. Available at: https://www.w3schools.com/sql/sql_join_inner.asp (Accessed: 15 September 2023). 
23. w3schools (no date) SQL null values, SQL NULL Values - IS NULL and IS NOT NULL. Available at: https://www.w3schools.com/sql/sql_null_values.asp (Accessed: 15 September 2023). 
24. w3schools (no date) SQL UPDATE Statement, SQL update statement. Available at: https://www.w3schools.com/sql/sql_update.asp (Accessed: 15 September 2023). 
25. w3schools (no date) SQL data types for mysql, SQL Server, and MS access. Available at: https://www.w3schools.com/sql/sql_datatypes.asp (Accessed: 15 September 2023). 
26. w3schools (no date) SQL constraints. Available at: https://www.w3schools.com/sql/sql_constraints.asp (Accessed: 15 September 2023). 
27. w3schools (no date) SQL not null constraint. Available at: https://www.w3schools.com/sql/sql_notnull.asp (Accessed: 15 September 2023). 
28. w3schools (no date) SQL unique constraint. Available at: https://www.w3schools.com/sql/sql_unique.asp (Accessed: 15 September 2023). 
29. w3schools (no date) SQL Foreign Key Constraint. Available at: https://www.w3schools.com/sql/sql_foreignkey.asp (Accessed: 15 September 2023). 
30. w3schools (no date) SQL operators. Available at: https://www.w3schools.com/sql//sql_operators.asp (Accessed: 15 September 2023). 
31. w3schools (no date) SQL Primary Key Constraint. Available at: https://www.w3schools.com/sql/sql_primarykey.asp (Accessed: 15 September 2023). 
32. w3schools (no date) SQL DELETE Statement, SQL DELETE STATEMENT. Available at: https://www.w3schools.com/sql/sql_delete.asp (Accessed: 15 September 2023).  
*/
	
	
	
	
	
	
	
	