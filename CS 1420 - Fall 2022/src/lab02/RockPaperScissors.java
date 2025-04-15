/**
 * This program plays a simple game of rock-paper-scissors (roshambo)
 * with the user.  (Students will complete this program as part of
 * lab #2.)  See the lab instructions.
 * 
 * Peter Jensen and Natalie Wilkins
 * August 30, 2022
 */
package lab02;

import java.util.Scanner;

public class RockPaperScissors
{
	public static void main(String[] args)
	{
		// Create a Scanner object.
		
		Scanner in = new Scanner(System.in);
		
		// Create two String variables.  Name them computerWord and userWord.
		// Initialize them to empty strings.

		String computerWord = "";
		String userWord = "";
		
	    // Make a random integer between [0...2].  If the random integer is a
	    //   0, set computerWord to "rock", if it's a 1, set computerWord
	    //   to "paper", otherwise set computerWord to "scissors".

		int value = (int) (Math.random() *3);
		
		if (value == 0)
			computerWord = "rock";
		else if (value == 1)
			computerWord = "paper";
		else if (value == 2)
			computerWord = "scissors";
		
		System.out.println("Experiment #1:  The computer chose: " + computerWord);
		
		// Using a Boolean flag, create an input loop that asks the player to enter
		//   their guess.  Print reasonable instructions.  Don't allow the program 
		//   to proceed until the player enters either "rock", "paper", or "scissors".
		//   Store it in userWord.

		boolean userInputIsOK = false; 
		
		// Our input loop
		
		while ( ! userInputIsOK )  // Loop as long as the user input is NOT ok
		{
		    // Get input from the user
			
			System.out.print("Experiment #2:  Type a word and press enter: ");
			userWord = in.next();
			
			if (userWord.equals("rock"))
					userInputIsOK = true;
			
			if (userWord.equals("scissors"))
					userInputIsOK = true;
			
			if (userWord.equals("paper"))
					userInputIsOK = true;
		
		// Print out the user's guess:  "You picked rock.", etc.
		// Print out the computer's guess:  "I picked rock.", etc.

		System.out.println("You picked " + userWord);
		System.out.println("I picked " + computerWord);
		
		
		if (userWord.equals("rock") && computerWord.equals("scissors"))
		    System.out.println("You win.");
		if (userWord.equals("rock") && computerWord.equals("paper"))
		    System.out.println("I win.");
		if (userWord.equals("rock") && computerWord.equals("rock"))
		    System.out.println("It's a tie.");
		
		if (userWord.equals("paper") && computerWord.equals("scissors"))
		    System.out.println("I win.");
		if (userWord.equals("paper") && computerWord.equals("rock"))
		    System.out.println("You win.");
		if (userWord.equals("paper") && computerWord.equals("paper"))
		    System.out.println("It's a tie.");
		
		if (userWord.equals("scissors") && computerWord.equals("scissors"))
		    System.out.println("It's a tie.");
		if (userWord.equals("scissors") && computerWord.equals("rock"))
		    System.out.println("I win.");
		if (userWord.equals("scissors") && computerWord.equals("paper"))
		    System.out.println("You win.");
		
		
		
					
	}
}
}
