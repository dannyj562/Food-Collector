/* 
	Danny Rivera
	March 19, 2017 
	CSC 311
	Project 02
	Dr Amlan Chaterjee
	* * * * * DESCRIPTION * * * * *
	For a variety of reasons, human beings track the food they intake. Assume you are working for a healthcare analytics company, 
	and need to gather information about the eating habits of your clients. You need to create a software that helps in doing your work. 
	For this purpose, you write a menu driven program with the following options:
1. Register client and create client login (client supplies the clientID and password, and the clientID must be unique)
2. Login existing client
3. Add a food item client ate
4. Check the complete history of food a client ate in a day in reverse order (all food items listed here from dinner to breakfast)
5. Check all the different types of food items a client ate in a day (all food items listed here only once)
6. Total calories eaten in a day
7. Food with the maximum calorie in a day
8. Food that the client ate maximum number of times in a day
9. Logout
*/
import java.io.*;
import java.util.*;
public class clientDriver
{
	// File name "login.txt"
	private static String clientFile = "login.txt";
	// File 
	private static File registerFile;
	// Writer to File
	private static FileWriter writer;
	// Scanner for menu, register, userID, food, and login.
	private static Scanner menuInput, registerInput, userIDInput, foodInput, loginInput;
	// Client input for main menu
	private static int clientInput;
	// Client Information. Stored in file
	private static String firstName, lastName, userId, password;
	// Client Information of food.
	public static String foodName, foodMealType, noFood = null;
	// Number of food quantity
	public static int foodQuantity;
	// Number of food calories
	public static double foodCalorie;
	// Food class
	public static Food food;
	// total calories
	private static double totalCalories = 0;
	// maximum calories
	private static double maximumCalories = 0;
	// List of food items
	private static List<Object> foodItems = new ArrayList<Object>();
	// Creates a random userID if user is unable to create it.
	private static StringBuilder randomUserID;
	// loggedIn is set to false
	private static boolean loggedIn = false;
	// Used to verify the correct UserID and password
	private static String verifyUserID, verifyPassword;

		/* ********** MAIN ********** */
	public static void main(String[] args)
	{
		createFile(); // Function is called to Create File 
		/* Loop until user enters 9 to Logout */
		while(clientInput != 9)
		{
			displayPrompt();
		}
		goodByeMessage();
	}
	/* Conclusion Message */
	public static void goodByeMessage()
	{
		System.out.println();
		System.out.println("\t * * * * * GOOD BYE :) * * * * * ");
		System.out.println();
	}

	/* This method is used to create a file if it does not exist
		The file is called "login.txt"  */
	public static void createFile()
	{
		registerFile = new File(clientFile);	// clientFile = 'login.txt'
		/* if file does not exist then 
		create the file for the new clients */
		if(!registerFile.exists())
		{
			System.out.println("\tWelcome New User ");
			System.out.println("\tFile: " + clientFile + " does not exist. Creating File ...." );
			try
			{
				registerFile.createNewFile();	// Create File process
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

			// Calls method to register clients
			registerClient();
		}
		System.out.println("\n");	// Empty line for a seperator 
	}

	/* This method is used to register clients by asking
		clients first and last name. 
		It also ask for a user ID and password. */
	public static void registerClient()
	{
		// Scanner for registering clients
		registerInput = new Scanner(System.in);
		// First Name
		System.out.println("Enter First Name");	
		firstName = registerInput.nextLine();
		// Last Name
		System.out.println("Enter Last Name");	
		lastName = registerInput.nextLine();
		// User ID
		System.out.println("Enter UserID");	
		userId = registerInput.nextLine();
		// Calls function to check if UserId is unique 
		int check = checkUniqueUserID();
		// if UserId is not unique
		if(check == -1) 
		{
			System.out.println("UserID is not unique");
			System.out.println("Please enter a unique UserID");
			userId = registerInput.nextLine();
			// Calls function to check if UserId is unique 
			check = checkUniqueUserID();
			// if UserId is not unique
			if(check == -1)
			{
				System.out.println("UserID is NOT unique");
				System.out.println("Creating a UserID......");
				// Calls function to create a random UserID
				createRandomUserID();
				userId = randomUserID.toString();	// Stores random UserID 
				System.out.println("Your UserID is " + userId);
			}
		}
		// Password
		System.out.println("Enter password of at least 8 characters"); // Password
		password = registerInput.nextLine();

		/* LOOP INFINITELY if password is not at leat 8 characters */
		while(password.length() < 8)
		{
			System.out.println("Password must be at least 8 characters.");
			System.out.println("Please enter password");
			password = registerInput.nextLine();	
			
		}
		// This function is used to Write all Information to the file 'login.txt'
		writeInformationToFile(); 	
	}

	/* This method is used to check if userID is unique.
		The following method scans the file 'login.txt'
		and returns -1 if is NOT unique. It returns 0 if its unique */
	public static int checkUniqueUserID()
	{
		try
		{
			// Scanner is used to read from file
			userIDInput = new Scanner(registerFile);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		// Variable reads a string of the whole line of the file
		String line;
		// while there is still input in the file 'login.txt'
		while(userIDInput.hasNext())
		{
			line = userIDInput.nextLine(); // Gets characters from the file line by line
			if(userId.equals(line))
			{
				return -1; // Error. UserID is NOT unique
			}
		}
		return 0; // Successful. UserID is unique
	}

	/* This method is used to create a random UserID 
		It uses 4 letters of the clients last name 
		and 4 random digits from 0-9 */
	public static void createRandomUserID()
	{

		Random random = new Random();	// Initialize Random
		// Modify the String
		randomUserID = new StringBuilder();
		for(int i=0; i<4; i++)
		{
			randomUserID.append(lastName.charAt(i));
		}
		for(int i=0; i<4; i++)
		{
			int randomNumber = random.nextInt(10);	// generates random number between 0 - 9
			randomUserID.append(randomNumber);		// append it to StringBuilder
		}

	}

	/* This method is used to write clients information to a file
		such as first name, last name, userID, and password. */
	public static void writeInformationToFile()
	{
		try
		{
			writer = new FileWriter(registerFile, true);
			writer.write(firstName + "\n");	// Writes first name on a single line
			writer.flush();					// flush it. Store it.
			writer.write(lastName + "\n");	// Writes last name on a single line
			writer.flush();
			writer.write(userId + "\n");	// Writes userID on a single line
			writer.flush();
			writer.write(password + "\n");	// Writes password on a single line
			writer.flush();
		}
		catch(Exception e)
		{
			System.out.println(e);	// May throw an exception. 
		}
		
	}

	/* Displays Main Menu */
	public static void displayPrompt()
	{
		System.out.println("\t * * * * * * * * * * MAIN MENU * * * * * * * * * * "); 
		System.out.println();

		System.out.println("1:  Register client and create login");
		System.out.println("2:  Login");
		System.out.println("3:  Add food item");
		System.out.println("4:  Display the history of food in reverse order");
		System.out.println("5:  Display all the different types of food items without duplicates");
		System.out.println("6:  Total calories eaten in a day");
		System.out.println("7:  Food with the maximum calorie in a day");
		System.out.println("8:  Food ate maximum number of times in a day");
		System.out.println("9:  Logout");
			
		System.out.println();
		System.out.println("\t * * * * * * * * * * * * * * * * * * * * * * * * * ");
		System.out.println();

		menuInput = new Scanner(System.in);	// Scanner
		clientInput = menuInput.nextInt();	// Store input from user
		switch(clientInput)
		{
			case 1:
					// Function is called to Register client
					registerClient();
					break;
			case 2:
					// Function is called to Login
					loginClient();
					break;
			case 3:
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					// Add food item from client
					addFoodItem();
					break;
			case 4:
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					// Display the history of food in reverse order
					displayFoodInReverseOrder();
					break;
			case 5:
					
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					// Display all the different types of food items only once
					displayFoodListOnce();
					break;

			case 6:
					// Total calories eaten in a day
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					totalCaloriesEaten();
					break;
			case 7:
					// Food with maximum calorie in a day
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					maximumFoodCaloriesPerDay();
					break;
			case 8:
					// Food that client ate maximum number of times in a day
					if(loggedIn == false)
					{
						userNotLoggedIn(); // Calls method to display that User is not logged in
						break;
					}
					popularFoodClientAte();
					break;

		}
		
	}

	/* Display Prompt if client is NOT logged in */
	public static void userNotLoggedIn()
	{
	
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
		System.out.println("\tPlease Login First");
		System.out.println();
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
	}

	/* This method is used to login 
		Requires UserID and password */
	public static void loginClient()
	{
		int check;

		while(loggedIn == false)
		{
			loginInput = new Scanner(System.in);
			System.out.println("\tEnter UserID: ");
			verifyUserID = loginInput.nextLine();
			System.out.println("\tEnter Password");
			verifyPassword = loginInput.nextLine();
			// calls function to verify that userID and password is correct
			check = verifyLogin();
			if(check == -1)
			{
				System.out.println("\tUserID or password is incorrect");
			}
			else
				loggedIn = true;
		}
		
	}

	/* This method is used to check if client entered the correct
		UserID and password. Returns -1 if its Incorrect.
		If it is Correct. Return 0. */
	public static int verifyLogin()
	{
		try
		{
			loginInput = new Scanner(registerFile);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		String line;	// Gets the whole character in a line
		// Loop while there is still input on the file
		while(loginInput.hasNext())
		{
			line = loginInput.nextLine();	// Gets character from file line by line
			if(verifyUserID.equals(line)) 
			{
				// UserID correct. Don't return anything
				// Check for password	
			}
			if(verifyPassword.equals(line))
			{
				return 0;
			}
		}
		return -1; 	// FAIL. USERID or PASSWORD IS NOT CORRECT.
	}

	/* This method is used to add Food Items that the client ate
		such as Food Name, Food Quantity, Food Calorie, and Food Meal Type */
	public static void addFoodItem()
	{
		foodInput = new Scanner(System.in); // input for food items
		while(true) // infinite loop
		{
			// FOOD NAME
			System.out.println("\tEnter name of food");
			foodName = foodInput.next();
			foodItems.add(foodName);	// add to ArrayList
			// QUANTITY OF FOOD
			System.out.println("\tEnter quantity of " + foodName);
			foodQuantity = foodInput.nextInt();
			foodItems.add(foodQuantity);	// add to ArrayList
			// CALORIES OF FOOD
			System.out.println("\tEnter calories of " + foodName);
			foodCalorie = foodInput.nextDouble();
			foodItems.add(foodCalorie);		// add to ArrayList

			totalCalories += foodCalorie;	// Stores total calories
			// replace variable if its a maximum number
			if(maximumCalories <= foodCalorie)
			{
				maximumCalories = foodCalorie;	// Stores maximum calories
			}	
			// MEAL TYPE OF FOOD
			System.out.println("\tEnter Meal-Type of " + foodName);
			foodMealType = foodInput.next();
			foodItems.add(foodMealType);	// add to ArrayList
			// Calls Food class
			food = new Food(foodItems);

			System.out.println("\t ***** Type 'exit' or 'EXIT' to go back to Main Menu *****");
			System.out.println("\t ******* Type anything to continue ******* ");
			noFood = foodInput.next();
			if(noFood.equalsIgnoreCase("EXIT")) // If user typed 'exit' leave loop
			{		
				break;
			}	
		}		
	}

	/* This method is used to display food in reverse order */
	public static void displayFoodInReverseOrder()
	{
		ArrayStack<Object> foodStack = new ArrayStack<Object>();
		for(int i=0; i<foodItems.size(); i++)
		{
			foodStack.push(foodItems.get(i));
		}
	
		System.out.println("\t * * * * * * * * * *");
		System.out.println();

		while(foodStack.getStackIndex() != -1)
		{
			System.out.println("\tMeal Type: " + foodStack.pop());
			System.out.println("\tCalories: " + foodStack.pop());
			System.out.println("\tQuantity: " + foodStack.pop());
			System.out.println("\tFood Name: " + foodStack.pop());
		}

		System.out.println();
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
	}

	/* This method is used to display the list of food without any duplicates */
	public static void displayFoodListOnce()
	{
		// Variable is used to store repeated food Names
		List<Object> foodRepeated = new ArrayList<Object>();
		// Stack is used to Store food attributes without duplicates	
		ArrayStack<Object> singleFoodList = new ArrayStack<Object>();
		int counter = 0;		
		for(int i=foodItems.size()-1; i>=0; i--)
		{
			// push Food Attributes in chronological order
			singleFoodList.push(foodItems.get(i));
		}
		// Loop until the top of the stack is -1. Until the Stack is empty.
		while(singleFoodList.getStackIndex() != -1)
		{
			Object name = singleFoodList.peek(); // Stores the current food  
			// Food Name is repeated. Pop it from Stack but dont print it out
			if(foodRepeated.contains(name))		
			{
				singleFoodList.pop();	// Pop Food Name
				singleFoodList.pop();	// Pop Food Quantity
				singleFoodList.pop();	// Pop Food Calorie
				singleFoodList.pop();	// Pop Food Meal Type
			}
			// Food Name is NOT repeated
			if(!foodRepeated.contains(name))
			{	
				if(counter % 4 == 0)  
				{
					// Add Food Name to Array List 
					foodRepeated.add(name);
				}
				System.out.println("\tFood Name: " + singleFoodList.pop());
				System.out.println("\tFood Quantity: " + singleFoodList.pop());
				System.out.println("\tFood Calories: " + singleFoodList.pop());
				System.out.println("\tFood Meal Type: " + singleFoodList.pop());
			}
			counter += 4;	// count by 4 to compare the FOOD NAME only
		}
	}

	/* This method is used to Display the total calories that the client ate */
	public static void totalCaloriesEaten()
	{
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
		System.out.println("Total calories eaten in a day : " + totalCalories);
		System.out.println();
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
	}

	/* This method is used to display the name of the food 
		and the maximum calories a client ate */
	public static void maximumFoodCaloriesPerDay()
	{
		for(int i=0; i<foodItems.size(); i++)	// Loop over the Food List
		{
			if(foodItems.get(i).equals(maximumCalories)) // Found the maximum calories 
			{
				System.out.println("\t * * * * * * * * * *");
				System.out.println();
				System.out.println("\tFood Name: " + foodItems.get(i-2) );	// Gets Food Name
				System.out.println("\tMaximum Calories: " + maximumCalories);	
				System.out.println();
				System.out.println("\t * * * * * * * * * *");
				System.out.println();
				break;
			}
		}

	}

	/* This method is used to display the most popular food that the client ate */
	public static void popularFoodClientAte()
	{
		Object duplicateItem = null;	// Object used to store Duplicate Food Name
		Object name = null;
		ArrayStack<Object> foodCounter = new ArrayStack<Object>();	// Stack is used 

		for(int index=0; index<foodItems.size(); index+=4)	// Loop by incrementing by 4 to capture Food Name
		{
			duplicateItem = food.searchFood(foodItems.get(index), index+4); // linear search in Food Class
			if(foodItems.contains(duplicateItem))
			{
				foodCounter.push(duplicateItem);
			}
		}

		System.out.println("\t * * * * * * * * * * ");	// Used as seperator. Make It easier to read during run-time
		System.out.println();
		// Loop until the top of Stack = -1 
		while(foodCounter.getStackIndex() != -1)
		{
			name = foodCounter.pop();
			System.out.println("\tPopular Food : " + name);
		}
		System.out.println();							// Used as seperator. Make It easier to read during run-time
		System.out.println("\t * * * * * * * * * *");
		System.out.println();
	}
}