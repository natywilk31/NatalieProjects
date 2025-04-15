/*
 * This program will tell us the area of a circle given the radius.
 * @Natalie Wilkins
 * 30-Aug-2022
 * 
 * 
 */




package assignment02;

import java.util.Scanner;

public class CircleArea {

	public static void main(String[] args) {
		Scanner in;
		in = new Scanner(System.in);
		double answer;
		System.out.print("Enter your radius: "); // collecting data from the user
		answer = in.nextDouble(); // storing the info from our user in the answer variable
		
		double circ = answer * answer * Math.PI; // computations for circumference
		System.out.println("The area of your circle is " + circ + "square units.");


	}

}
