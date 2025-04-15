/*
 * A collection of unit tests for Assignment #7.  To make this work, you'll need to 
 * follow a few steps to get JUnit tests set up in your project.  Do this:
 * 
 *     + Make sure the Package Explorer view tab is visible in Eclipse.
 *           (You can always activate view tabs under Window->Show View.)
 *           
 *     + Right-click the ArrayExercises class, select New -> JUnit Test Case     
 *         - For the name of the test class, choose something that will not collide
 *           with this class name.  (You can delete it later, so "Deleteme" would be fine.)
 *         - Use JUnit Jupiter test if it is available, otherwise use JUnit 4.
 *         - 'Finish' this dialog.
 *         
 *     + Eclipse will warn you that JUnit 5 is not on the build path, and it will give
 *       you the option of adding it.  (We need it, it's the entire point of these steps.)
 *         - Hit 'OK' to complete the dialog.
 *         
 *     + You can delete the new class it created, or you can add unit tests to it.
 *         - You won't submit that class.
 *         
 *     + Copy this class (ArrayExercisesTest) into your assignment07 folder (if you
 *       have not done so already).  As long as JUnit 4 or JUnit 5 is set up in your
 *       project, the tests should run fine!
 *       
 *  Peter Jensen
 *  
 * Modified by:
 * @author ***Put your name here***
 * @version ***Put the date here***
 */
package assignment07;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Arrays;  // OK in tests, not allowed in ArrayExercises

// Imports for JUnit 4 (currently unused)

// import static org.junit.Assert.*;
// import org.junit.Test;

// Imports for JUnit 5

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/* I generated unit tests for all the functions in ArrayExercises.
 * To run the tests, just run this code.
 */
public class ArrayExercisesTest
{
	/* This is my first example unit test.  A unit test is just a piece of
	 * code that calls some method or builds some object, and tests 
	 * for errors.  The code/test design is up to the programmer.  
	 * 
	 * The unit test library provides functions for 'asserting' correctness.
	 * I use assertEquals below.  If the two values are not equal, the
	 * unit test library throws an exception and records a failure.
	 * 
	 * There are other assert functions in the unit test library that are
	 * useful for noting errors.  If the function below completes without
	 * recording a failure, then the test is marked as a success.
	 */
	@Test
	public void testRandomArray01()
	{
		// Call the function, ask for an array of thirty elements.
		
		int[] result;
		int size = 30;
		
		result = ArrayExercises.randomArray(size);  // This calls the function I'm testing.
		
		// The function has a specific contract that guarantees things
		//  about it's return value.  Check to make sure the method
		//  call did what it was supposed to.
		
		// Make sure the size of the result array is correct.
		
		assertEquals(size, result.length);  // If unequal, an error is recorded.
		
		// Make sure the required numbers [0..n-1] are in the array.
		
		numberLoop:  for (int n = 0; n < size; n++)  // Notice that I labeled this loop
		{
			// Search for n in the array.  When found, move on to the next n.
			
			for (int pos = 0; pos < size; pos++)
				if (result[pos] == n)
					continue numberLoop;  // Found it, continue the outer loop.
			
			// If we get this far, the number n was not found.  This is an error.
			
			fail("Number missing from random array: " + n + " in " + Arrays.toString(result));  // Record an error 	
		}		
		
		// If the number loop completes without failing, all tests pass!  
		//   (When this method ends normally, the test is marked as passing.)
	}

	/* I wanted two unit tests for my function.  The first one, above,
	 *   just makes sure the basic operation of 'randomArray' is 
	 *   correct.  This second unit test makes sure the 'randomness'
	 *   is correct.  A truly random shuffle has equal likelihood
	 *   of any outcome.  I repeatedly generate random arrays,
	 *   then I count up results, and then check to make
	 *   sure that each outcome occurred with similar probability.
	 *   
	 * Because of the fact that random numbers may produce results
	 *   that look uneven, I loop many times to reduce the likelihood
	 *   of random results looking like an error. 
	 *   
	 * I do not expect students to study this code - it is poor code.
	 *   (I don't like the way I'm counting permutations.)  There
	 *   are better ways, but you haven't seen the required lectures
	 *   yet, so I'm using a more primitive solution.  I expect your
	 *   unit tests to be much less complex.
	 */ 
	
	@Test
	public void testRandomArray02()
	{
		// An array of three has six permutations.
		
		// Counts of how many times each permutation appears.
		
		int count012 = 0;
		int count021 = 0;
		int count102 = 0;
		int count120 = 0;
		int count201 = 0;
		int count210 = 0;
		
		// Repeatedly generate arrays (1,000,000 times).
		
		for (int count = 0; count < 1_000_000; count++)
		{
			// Generate an array of 3 elements.
			
			int[] result = ArrayExercises.randomArray(3);  // This calls the function I'm testing.
			
			// Keep counts of each permutation in the array.
			
			if (result[0] == 0 && result[1] == 1)       // [0, 1, 2]
				count012++;
			else if (result[0] == 0 && result[1] == 2)  // [0, 2, 1]
				count021++;
			else if (result[0] == 1 && result[1] == 0)  // [1, 0, 2]
				count102++;
			else if (result[0] == 1 && result[1] == 2)  // [1, 2, 0]
				count120++;
			else if (result[0] == 2 && result[1] == 0)  // [2, 0, 1]
				count201++;
			else // only other possibility is [2, 1, 0]
				count210++;
		}
		
		// Check each permutation.  It should occur 166,666 times on average.  Accept
		//   anything within +/- 3,000.
		
		if (Math.abs(166_666 - count012) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count012); 	
		
		if (Math.abs(166_666 - count021) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count021); 	
		
		if (Math.abs(166_666 - count102) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count102); 	
		
		if (Math.abs(166_666 - count120) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count120); 	
		
		if (Math.abs(166_666 - count201) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count201); 	
		
		if (Math.abs(166_666 - count210) > 3_000)
		    fail("Permutation [0, 1, 2] appears an unexpected number of times:  " + count210); 	
		
		// If execution completes without failing, the test passes!  
		//   (When this method ends normally, the test is marked as passing.)
	}

	
	@Test
	public void testReverseOrder01()
	{
		// Set up a char[], call the ArrayExercises.reverse(...) function.
		// Test the array to make sure the function did its job.
		
		char[] chars = new char[] {'a', 'b', 'c', 'd', 'e'};
		ArrayExercises.reverseOrder(chars);
		
		if (chars.length != 5)
			fail("Wrong length.");
		
		if (chars[0] != 'e' || chars[1] != 'd' || chars[2] != 'c' ||
				chars[3] != 'b' || chars[4] != 'a')
			fail("At least one character did not get swapped correctly.");
		
	}
	
	/* Note:  You'll want more unit test functions.  Cut-and-paste existing tests, but increase
	 * the number in the function names.  Make sure to include @test before each function header.
	 * 
	 * testReverseOrder01
	 * testReverseOrder02
	 * testReverseOrder03  ...etc...
	 * 
	 * You can then put different test code in each test to be thorough.
	 */
	
	@Test 
	public void testReverseOrder02()
	{
		// Set up a char[], call the ArrayExercises.reverse(...) function.
		// Test the array to make sure the function did its job.
		
		char[] chars = new char[] {'a', 'b', 'c', 'd', 'e', '/', '1', '9' };
		ArrayExercises.reverseOrder(chars);
		
		if (chars.length != 8)
			fail("Wrong length.");
		
		if (chars[0] != '9' || chars[1] != '1' || chars[2] != '/' ||
				chars[3] != 'e' || chars[4] != 'd' || chars[5] != 'c' || chars[6] != 'b' || chars[7] != 'a')
			fail("At least one character did not get swapped correctly.");
		
	}

	@Test //can i just use strings here? or what would an object be?
	public void testCount01()
	{
		Object[] objects = new Object[] {null, 'a', 2, "string"};
		if (objects.length != 4)
			fail("Wrong length.");
		
		int counter = ArrayExercises.count(objects, null);
		if (counter!= 1)
			fail("wrong count."); 
		
		Object[] empty = null;
		
		try {
			ArrayExercises.count(empty, 2);
			fail("this didn't work.");
		} catch (NullPointerException e) {
			
		}
			
	}
	
	@Test
	public void testCount02() 
	{
		Object[] things = new Object[] {"a", "b", "a", "b", "c", "a"};
		int countThings = ArrayExercises.count(things, "a");
		if (things.length != 6)
			fail("Whyd ya change the length?");
		if (countThings != 3)
			fail("Counter failed.");
	}

	
	
	@Test
	public void testReplace01()
	{
		String[] strings = new String[] {"hi", "hello", "hi ", "yo", "howdy", 
				"hallo", "hi", "hello"};
		if (strings.length != 8)
			fail("Incorrect length.");
		ArrayExercises.replace(strings, "hello", "bye");
		if (!strings[0].equals("hi") ||!strings[1].equals("bye") ||!strings[2].equals("hi ") || 
				!strings[3].equals("yo") || !strings[4].equals("howdy") || !strings[5].equals("hallo") ||
				!strings[6].equals("hi") || !strings[7].equals("bye"))
			fail("Some item was not correctly replaced.");
		
		
	}
	
	@Test
	public void testReplace02 ()
	{
		String[] empty = null;
		try {
			ArrayExercises.replace(empty, null, null);
			fail("didn't throw the exception");
		}
		catch (NullPointerException e) {
			
		}
		
		
	}
	
	@Test
	public void testReplace03()
	{
		String[] strings = new String[] {"hi", "hello", "hi", "yo", "howdy", 
				"hallo", "hi", "hello"};
		if (strings.length != 8)
			fail("Incorrect length.");
		ArrayExercises.replace(strings, "hello", "bye");
		if (!strings[0].equals("hi") ||!strings[1].equals("bye") ||!strings[2].equals("hi") || 
				!strings[3].equals("yo") || !strings[4].equals("howdy") || !strings[5].equals("hallo") ||
				!strings[6].equals("hi") || !strings[7].equals("bye"))
			fail("Some item was not correctly replaced.");
		
		
	}
	
	@Test
	public void testReplace04()
	{
		String[] strings = new String[] {"2","3","4","2","6","7","8","2"};
		if (strings.length != 8)
			fail("Incorrect length.");
		ArrayExercises.replace(strings, "2", "0");
		if (!strings[0].equals("0") ||!strings[1].equals("3") ||!strings[2].equals("4") || 
				!strings[3].equals("0") || !strings[4].equals("6") || !strings[5].equals("7") ||
				!strings[6].equals("8") || !strings[7].equals("0"))
			fail("Some item was not correctly replaced.");
		
		
	}
	

	@Test
	public void testComputeAreas01()
	{
		double[] heights = new double[] {2,3,4,5,6,7,8,9};
		double[] widths = new double[] {2,3,4,5,6,7,8,9};
		double[] areas = ArrayExercises.computeAreas(heights, widths);
		
		if (areas.length !=8)
			fail("Areas array is the wrong length.");
		
		if ((areas[0] != 4 && areas[1] != 9 || areas[2] != 16 && areas[3] != 25 || areas[4] != 36 &&
				areas[5] != 49 || areas[6] != 64 || areas[7] != 81))
			fail("There has been a false calculation.");
				
	}

	@Test
	public void testComputeAreas02()
	{
		double[] heights = new double[] {0,2,100,2,2,2,7,8};
		double[] widths = new double[] {2,3,4,5,6,7,8,9};
		double[] areas = ArrayExercises.computeAreas(heights, widths);
		
		if (areas.length !=8)
			fail("Areas array is the wrong length.");
		
		if ((areas[0] != 0 && areas[1] != 6 || areas[2] != 400 && areas[3] != 10 || areas[4] != 12 &&
				areas[5] != 14 || areas[6] != 56 || areas[7] != 72))
			fail("There has been a false calculation.");
				
	}
	
	
	@Test
	public void testRemove01()
	{
		Color[] pixels = new Color[] {Color.blue, Color.green, Color.gray};
		
		Color[] removed = ArrayExercises.remove(pixels, Color.blue);
		
		if (removed.length != 2)
			fail("didn't remove right.");
		if (!removed[0].equals(Color.green) || !removed[1].equals(Color.gray))
			fail("Didn't remove correctly.");
		
		
	}

	
	@Test
	public void testRemove02()
	{
		Color[] pixels = new Color[] {Color.blue, Color.green, Color.gray, null, null, Color.pink, null};
		
		Color[] removed = ArrayExercises.remove(pixels, null);
		
		if (removed.length != 4)
			fail("didn't remove right.");
		if (!removed[0].equals(Color.blue) || !removed[1].equals(Color.green) || !removed[2].equals(Color.gray)||
				!removed[3].equals(Color.pink))
			fail("Didn't remove correctly.");
		
		
	}
	
	
	@Test
	public void testRemove03()
	{
		Color[] pixels = new Color[] {Color.blue, Color.green, Color.gray, null, null, Color.pink, null};
		
		Color[] removed = ArrayExercises.remove(pixels, Color.blue);
		
		if (removed.length != 6)
			fail("didn't remove right.");
		if (!removed[0].equals(Color.green) || !removed[1].equals(Color.gray) || !(removed[2] == null) ||
				!(removed[3] == null) || !removed[4].equals(Color.pink)|| !(removed[5] == null) )
			fail("Didn't remove correctly.");
		
		
	}
	
	
	
	
	@Test
	public void testSort01()
	{
		int[] numbers = new int[] {1,0,7,8,9,2,3,4,5,6};
		int[] expected = new int[] {9,8,7,6,5,4,3,2,1,0};
		ArrayExercises.sort(numbers);

		if (numbers.length != 10)
			fail("The length of the array is incorrect.");
		for (int i = 0; i < expected.length; i++)
			if (numbers[i] != expected[i])
				fail("sorted wrong.");
		
	}
	
	@Test
	public void testFindSmallest01()
	{
		Rectangle[] rectangles = new Rectangle[] {new Rectangle (5,6), new Rectangle (2,3), new Rectangle (7,8)};
		Rectangle smallestRectangle = ArrayExercises.findSmallest(rectangles);
		if (smallestRectangle.width != 2 || smallestRectangle.height != 3)
			fail("Wrong rectangle was selected.");
	}
	
	@Test
	public void testFindSmallest02()
	{
		Rectangle[] rectangles = new Rectangle[] {new Rectangle (5,6), new Rectangle (2,3), new Rectangle (7,8), new Rectangle (2,3)};
		Rectangle smallestRectangle = ArrayExercises.findSmallest(rectangles);
		if (smallestRectangle.width != 2 || smallestRectangle.height != 3)
			fail("Wrong rectangle was selected.");
	}
	
	@Test
	public void testFindSmallest03()
	{
		Rectangle rect1 = new Rectangle (2,3);
		Rectangle rect2 = new Rectangle (7,8);
		Rectangle rect3 = new Rectangle (2,3);
		Rectangle rect4 = new Rectangle (5,6);
		Rectangle rect5 = new Rectangle (2,3);
		Rectangle[] rectangles = new Rectangle[] {rect1, rect2, rect3, rect4, rect5};
		Rectangle smallestRectangle = ArrayExercises.findSmallest(rectangles);
		if (!smallestRectangle.equals(rect5))
			fail("wrong rectangle.");
	}
	
	
	
	@Test
	public void testHistogram01()
	{
		int[] dataToTest = new int[] {1,1,1,1,5};
		int[] histo = ArrayExercises.histogram(dataToTest);
		
		if (histo.length != 6)
			fail("Wrong length.");
		
		if (histo[0] != 0 || histo[1] != 4 || histo[2] != 0 || histo[3] != 0 || histo[4] != 0 || histo[5] != 1)
			fail("At least one of the values was incorrect.");
		
	
	}
}
