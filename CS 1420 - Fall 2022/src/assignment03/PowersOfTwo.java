/**
 * 
 * @Natalie Wilkins
 * 05 September 2022
 * This will output the values of the powers of two from 2^0 up to 2^16. 
 * 
 * 
 */



package assignment03;

public class PowersOfTwo {

	public static void main(String[] args) {
		int count = 0;
		while (count < 17) {//get out loop to go 16 times and print each of the powers
			System.out.println(Math.pow(2, count));
			count = count + 1;
		}

	}

}
