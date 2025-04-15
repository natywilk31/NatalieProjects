package assignment06;

import java.util.Scanner;

public class FractionRoot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please input your numerator: ");
		long numerator = keyboard.nextLong();
		System.out.println("Please input your denominator: ");
		long denominator = keyboard.nextLong();
		System.out.println("Please input your approximation count: " );
		int approximationCount = keyboard.nextInt();
		
		
		Fraction userFract = new Fraction(numerator, denominator);
		
		Fraction currentX = new Fraction(numerator, denominator);
		
		Fraction helpfulHalf = new Fraction(1,2);
		
		System.out.println(currentX);
		currentX = new Fraction(numerator, denominator * 2);
		int count = 1;
		while (count < approximationCount + 1) {
			Fraction nextX = helpfulHalf.multiply(currentX.add(userFract.divide(currentX))); 
			currentX = nextX;
			count++;
			System.out.println(currentX);
			
			
		}
		System.out.println("The square root of " + userFract + " is approximately " + currentX + ".");
		
		keyboard.close();
	}

}
