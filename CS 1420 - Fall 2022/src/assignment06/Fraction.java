/*
 *This is our Fraction class. We take in two arguments as numerator and denominator. We have a method to convert it to a string,
 *a divide method, and a multiply method. 
 * 
 * 
 * @Natalie Wilkins
 * 25 September 2022
 * 
 */




package assignment06;

public class Fraction {
	
	private long numerator;
	private long denominator;
	
	
	
	/*
	 * This is the constructor for our fraction class. We take in two longs, then we assign them to numerator and denominator.
	 * 
	 * no return for a constructor
	 * params are two longs, first one will be numerator, second will be denominator
	 * 
	 * 
	 */
	public Fraction (long n, long d) // constructor 
	{
	   
	    // Compute the greatest common divisor of x and y using a
	    //  well-known algorithm.

	    long gcd = n;
	    long remainder = d;
	    long temp;
	    
	    while (remainder != 0)
	    {
	        temp = remainder;
	        remainder = gcd % remainder;
	        gcd = temp;
	    }
	    
	   
	    this.numerator   = n / gcd;
	    this.denominator = d / gcd;
	    
	    if (this.denominator < 0) {
	    	this.numerator = this.numerator * -1;
	    	this.denominator = this.denominator * -1;
	    }
	}
	
	
	/*
	 * A second constructor that takes a long and turns it into a fraction with a denominator of 1.
	 * 
	 * return none its a constructor
	 * params one long
	 * 
	 */
	public Fraction (long m) {
		this.numerator = m;
		this.denominator = 1;
	}
	
	

	/*
	 * This method will make it so that any time we try to use a string version of our fraction,
	 * we can actually recognize the data that comes out.
	 * 
	 * params none
	 * return a string version of the fraction
	 * 
	 * 
	 */
	public String toString ()
	{
		
		return this.numerator + "/" + this.denominator;
	}
	
	//accessors 
	
	
	/*
	 * Because these variables are private, we have an accessor so we can still get that info.
	 * 
	 * return the numerator in the current object
	 * no params
	 * 
	 */
	public long getNumerator ()
	{
	    return numerator;
	}
	
	

	/*
	 * Because these variables are private, we have an accessor so we can still get that info.
	 * 
	 * return the denominator in the current object
	 * no params
	 * 
	 */
	public long getDenominator () 
	{
		return denominator;
	}
	
	
	
	
	/*
	 * This lets us multiply two fractions. we will use a fraction and input another fraction (could be the same one) for the right hand side.
	 * 
	 * params a Fraction
	 * return the two fractions multiplied in a variable of type Fraction
	 * 
	 */
	public Fraction multiply (Fraction rightHandSide)
	{
	    // Create a variable to hold the result
	    Fraction result;  

	    // Build a new Fraction object - send the products to the constructor.
	    result = new Fraction (this.numerator * rightHandSide.numerator,
	                           this.denominator * rightHandSide.denominator);

	     // Pass the resulting fraction back to the caller.
	     return result; 
	}
	
	
	
	
	/*
	 * This lets us divide two fractions. we will use a fraction and input another fraction (could be the same one) for the right hand side. 
	 * 
	 * params a fraction
	 * return the two fractions multiplied in a variable of type Fraction
	 * 
	 */
	public Fraction divide (Fraction rightHandSide)
	{
		// create a variable to hold the result
		Fraction result;
		
		// Build a new Fraction object - send the products to the constructor.
		result = new Fraction (this.numerator * rightHandSide.denominator, this.denominator * rightHandSide.numerator);
		
		//Pass the resulting fraction back to the caller
		return result;
	}
	
	/*
	 * This will take a fraction as input and add it to the fraction in which we run it.
	 * 
	 * params a fraction to add to our fraction
	 * return a new fraction variable that holds the sum
	 * 
	 * 
	 */
	public Fraction add (Fraction rightHandSide)
	{
	    // Create a variable to hold the result
	    Fraction result;  

	    // Build a new Fraction object - send the products to the constructor.
	    result = new Fraction (this.numerator*rightHandSide.denominator + rightHandSide.numerator*this.denominator,
	                           this.denominator * rightHandSide.denominator);

	     // Pass the resulting fraction back to the caller.
	     return result; 
	}
	

	/*
	 * This will take a fraction as input and subtract it from the fraction in which we run it.
	 * 
	 * params a fraction to subtract  from our fraction
	 * return a new fraction variable that holds the difference
	 * 
	 * 
	 */
	public Fraction subtract (Fraction rightHandSide)
	{
	    // Create a variable to hold the result
	    Fraction result;  

	    // Build a new Fraction object - send the products to the constructor.
	    result = new Fraction (this.numerator*rightHandSide.denominator - rightHandSide.numerator*this.denominator,
                				this.denominator * rightHandSide.denominator);

	     // Pass the resulting fraction back to the caller.
	     return result; 
	}
	
	
	
	/*
	 * this function will convert a fraction to an approximation of it in the form
	 * of a double.
	 * 
	 * no params
	 * returns a double
	 */
	public double toDouble() {
		double fractionAsDouble = (double) this.numerator / this.denominator;
		return fractionAsDouble;

	}

	
	
	
	
	
}
