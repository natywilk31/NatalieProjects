/*
 * This code uses the hailstone sequence again. This time, we want to find the starting number of a hailstone
 * sequence with a given count of steps taken to reach 1.
 * 
 * @Natalie Wilkins
 * 07 Sep 2022
 * 
 */



package assignment03;

import java.util.Scanner;

public class Experiment02 {

	public static void main(String[] args) {
		//our goal is to get the program to print out the value and the steps for each number 2 - 1000.
		
		Scanner userInput = new Scanner(System.in);
		int desiredLength = 0;
		System.out.println("Please enter your desired length: ");
		desiredLength = userInput.nextInt();

		
		
		int startNumber = 2;
		int count = 0;
		int sequence = 0;
		
		while (startNumber < 1000) {
				sequence = startNumber;
				count = 0;
				while (sequence != 1) {
					if (sequence % 2 == 0) {
						sequence = sequence / 2;
						count = count + 1;
					}
					else {
						sequence = sequence * 3 + 1;
						count = count + 1;
						}
					}
				if (count == desiredLength) {
					System.out.println("The Hailstone sequence starting at " +  startNumber +  " takes " + desiredLength + " steps to converge to 1.");
					break;
				}
				startNumber = startNumber + 1;
		}
	}
}
	


