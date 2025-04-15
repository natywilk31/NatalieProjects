package assignment03;

import java.util.Scanner;

public class experiment01 {

	public static void main(String[] args) {
		
//		// this will print even numbers up to the desired count
//		int count = 0;
//		while (count < 5) {
//			if (count % 2 == 0) 
//				System.out.println(count);
//			count = count + 1;
//			
//		}
		
		//this will take user input of 17 numbers and add them together. 
		int count = 0;
		Scanner userInput = new Scanner(System.in);
		int total = 0;
		while (count < 10) {
			System.out.println("Enter your numbers: ");
			int userNumber = userInput.nextInt();
			total = total + userNumber;
			count = count + 1;
			}
		System.out.println(total);
		userInput.close();
		}
	}	


