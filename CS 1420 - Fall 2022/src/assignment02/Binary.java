/*
 * Given a number in decimal form, we will output the number in binary.
 * 
 * @Natalie Wilkins
 * 30-Aug-2022
 * 
 * 
 */



package assignment02;

import java.util.Scanner;

public class Binary {

	public static void main(String[] args) {
		Scanner in;
		in = new Scanner(System.in);
		int n;
		System.out.print("Enter a number: "); // we don't have a way to check validity, but that's irrelevant here
		
		n = in.nextInt(); // assigning the prompted number to a variable
		
		int first, second, third, fourth, fifth, sixth, seventh, eighth;
		
		//computations for each position of binary
		
		first = n / 128;
		second = (n % 128) / 64;
		third = n % 64 / 32;
		fourth = n % 32 / 16;
		fifth = n % 16 / 8;
		sixth = n % 8 / 4;
		seventh = n % 4 / 2;
		eighth = n % 2;
		
		
		System.out.println("The decimal number " + n + " is " + first + second + third + fourth + fifth + sixth + seventh + eighth + " in binary.");

		
		
		
		
		
		
		
	}

}
