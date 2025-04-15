/*
 * 
 * This code will take a positive number as input from the user. It will then run the Hailstone 
 * method on it, where we do one operation if its even and a different set of operations when it's odd. 
 * Then, we output each of the numbers from that sequence and how many steps it took to get there.
 * @Natalie Wilkins
 * 05 Sep 2022
 * 
 */




package assignment03;

import java.util.Scanner;

public class Hailstone {

	public static void main(String[] args) {
		

		Scanner userInput = new Scanner(System.in);
		int userNumber = -1;
		while (userNumber <= 0) { // this loop will make sure we get a positive number
			System.out.println("Please enter a positive number: ");
			userNumber = userInput.nextInt();
		
			}
		int count = 0;
		while (userNumber != 1) { // this loop takes the number, determines at each step which part of the program it should get put into, and repeats until it equals 1
			System.out.print(userNumber + " ");
			if (userNumber % 2 == 0)
				userNumber = userNumber / 2;
			else 
				userNumber = userNumber * 3 + 1;
			
			count = count + 1;
			
			
		
		
			}
		System.out.println(1);
		System.out.println("The number of steps we took to get to 1 was: " + count);
		userInput.close();
		}
		

		
		
}
		
		
		




