/*
 * This program will tell us the temperature in celsius given the temperature in Fahrenheit. We will only use integers, no doubles.
 * @Natalie Wilkins
 * 30-Aug-2022
 * 
 * 
 */




package assignment02;

import java.util.Scanner;

public class Temperature {

	public static void main(String[] args) {
		Scanner in;
		in = new Scanner(System.in);
		int fahrenheit;
		
		System.out.print("What is the current temperature in Fahrenheit? "); // collecting data from the user
		fahrenheit = in.nextInt(); // storing the info from our user in the answer variable
		int celsius = (fahrenheit - 32) * 5; // computations to find the celsius degrees
		
		
		System.out.println("The current temperature in Celsius is " + (int)(celsius/9) + " degrees.");
		
		


	}

}
