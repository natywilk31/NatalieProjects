/*
 * This program will tell us the hypotenuse of a triangle given the lengths of the two legs.
 * @Natalie Wilkins
 * 30-Aug-2022
 * 
 * 
 */




package assignment02;

import java.util.Scanner;

public class Hypotenuse {

	public static void main(String[] args) {
		Scanner in;
		in = new Scanner(System.in);
		double leg1;
		double leg2;
		
		System.out.print("Enter the length of leg one: "); // collecting data from the user
		leg1 = in.nextDouble(); // storing the info from our user in the answer variable
		System.out.print("Enter the length of leg two: "); // for our second side of the triangle
		leg2 = in.nextDouble();
		
		double hypo = Math.sqrt(leg1*leg1 + leg2*leg2); // computations for the hypotenuse
		
		System.out.println("The length of the hypotenuse is: " + hypo + " units.");
		
		


	}

}
