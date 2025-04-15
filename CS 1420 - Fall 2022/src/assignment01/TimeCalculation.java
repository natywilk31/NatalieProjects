/**
 * This class will take my U Number and tell us how many hours, minutes, 
 * and seconds will fit into it.  
 * 
 *
 * @author  Natalie Wilkins
 * @version August 22, 2022
 */

package assignment01;

public class TimeCalculation {
//this function will calculate how many hours, minutes, seconds in my U Number. 
	public static void main(String[] args) {
		int uID = 1303426;  // Your uID number goes here, without the 'u'.
		int hours = uID / 3600; //calculating how many total hours
		int minutes = (uID % 3600) / 60; //calculating our minutes
		int seconds = uID % 60; //calculating the seconds
		System.out.print("My uID number is u"); //print our info
		System.out.print(uID);  // Print does not move on to the next line.  Use println to move on to the next line when done.
		System.out.println(".");
		System.out.print(uID + " seconds is " + hours + " hour(s), " + minutes +  " minute(s), and " + seconds + " second(s).  "); //print our final results
	}

}



