package assignment05;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class pickRandomWord {
	
	
	public static String pickRandomWord (String filename) {
		
		
		
		
		try {
		Scanner in = new Scanner(new File(filename)); 
		int filecount = countWords(filename);
		int wordNumber = (int) (Math.random() * filecount);
		int currentWordNumber = 0;
		String currentWord = "what";
		//System.out.println(filecount + "   " + wordNumber + "   " + currentWordNumber + "   " + currentWord);
		while (currentWordNumber <= wordNumber) {
			currentWord = in.next();
			currentWordNumber++;
		}
		System.out.println(currentWord);
		return ".";
		}
		catch (IOException e) {
			System.err.println();
			return "";
		}
		

	}
	
	
	public static int countWords(String filename) {
		try {
			Scanner in = new Scanner(new File(filename));
			int count = 0;
			while (in.hasNext()) {
				count++;
				in.next();
			}
			return count;
		} catch (IOException e) {
			System.err.println();
			return 0;
		}

	}
	
	
}
