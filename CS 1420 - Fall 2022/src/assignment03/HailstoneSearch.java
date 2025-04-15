/*
 * In this code we let the user input a desired length that their Hailstone search will take on.
 * We want the smallest integer possible that takes the exact number of specified steps. 
 * 
 * @Natalie Wilkins
 * 06 Sep 2022
 * 
 */




package assignment03;

import java.util.Scanner;

public class HailstoneSearch {

	public static void main(String[] args) {
		
		//this chunk of code just gets the input from the user and assigns it to a variable
		Scanner userInput = new Scanner(System.in);
		int desiredLength = 0;
		System.out.println("Please enter your desired length: ");
		desiredLength = userInput.nextInt();

		
		//establishing variables to use for the rest of the code
		int startNumber = 2;
		int count = 0;
		int sequence = 0;
	
		while (startNumber < 1000) { //we're gonna test each number 2 - 1000 until we find the desired length
				sequence = startNumber;
				count = 0;
				while (sequence != 1) { // repeat as long as we haven't reached 1
					if (sequence % 2 == 0) {
						sequence = sequence / 2;
						count = count + 1;
					}
					else {
						sequence = sequence * 3 + 1;
						count = count + 1;
						}
					}
				if (count == desiredLength) { //when the count is equal to desired length, we print and break.
					System.out.println("The Hailstone sequence starting at " +  startNumber +  " takes " + desiredLength + " steps to converge to 1.");
					break;
				}
				startNumber = startNumber + 1; //increase our counter so we can text the next number
				userInput.close();
		}
	
	}
}


			
			
				
				
				
				
				
				
				
			
