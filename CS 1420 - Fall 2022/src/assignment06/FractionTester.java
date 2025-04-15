/*
 * This helps us test our Fraction class. We test the constructor and the multiply and divide methods. 
 * 
 * @Natalie Wilkins
 * 25 September 2022
 * 
 */


package assignment06;

public class FractionTester {

	public static void main(String[] args) {
		System.out.println ("Fraction tester:");
		
		boolean errorOrNot = false;

		Fraction f = new Fraction (2, 4);
		Fraction g = new Fraction (1, -4);
		Fraction h = new Fraction (5, 1);
		Fraction j = new Fraction (12);
		
		Fraction n = j.add(g);
		System.out.println(n);
		
		System.out.println(g.toDouble());
		
//		System.out.println ("Fraction f contains: " + f);
//		System.out.println ("Fraction g contains: " + g);
//		System.out.println ("Fraction h contains: " + h);
//		System.out.println ("Fraction j contains: " + j);
		
		System.out.println ("G's denominator is: " + g.getDenominator());
		
		Fraction p;
		p = f.multiply(g);
		System.out.println("Left hand fraction: " + f.toString() + ", right hand fraction: " + g.toString() + ", product: " + p);
		p = g.multiply(h);
		System.out.println("Left hand fraction: " + g.toString() + ", right hand fraction: " + h.toString() + ", product: " + p);
		
		Fraction l;
		l = h.divide(f);
		System.out.println("Left hand fraction: " + h.toString() + ", right hand fraction: " + f.toString() + ", quotient: " + l);
		l = g.divide(f);
		System.out.println("Left hand fraction: " + g.toString() + ", right hand fraction: " + f.toString() + ", quotient: " + l);
		
		
		
	    
	    // this test checks our multiply method.
	    Fraction a = new Fraction (2, 3);
	    Fraction b = new Fraction (1, 5);
	    Fraction c = a.multiply(b);
	    System.out.println (a + " * " + b + " = " + c);
	    if (a.getNumerator() != 2 || a.getDenominator() != 3 ||
	        b.getNumerator() != 1 || b.getDenominator() != 5 ||
	        c.getNumerator() != 2 || c.getDenominator() != 15)
	        return;
	    else
	    	errorOrNot=true;
	    
	    
	    // this tests our divide method.
	    Fraction newC = a.divide(b);
	    System.out.println (a + " / " + b + " = " + newC);
	    if (a.getNumerator() != 2 || a.getDenominator() != 3 ||
	        b.getNumerator() != 1 || b.getDenominator() != 5 ||
	        newC.getNumerator() != 10 || newC.getDenominator() != 3)
	       return;
	    else
	    	errorOrNot = true;
	    
	    // this tests our add method.
	    Fraction newD = a.add(b);
	    System.out.println (a + " + " + b + " = " + newD);
	    if (a.getNumerator() != 2 || a.getDenominator() != 3 ||
	        b.getNumerator() != 1 || b.getDenominator() != 5 ||
	        newD.getNumerator() != 13 || newD.getDenominator() != 15)
	        return;
	    else
	    	errorOrNot = true;
	    
	    
	    //this tests our subtract method.
	    Fraction newE = a.subtract(b);
	    System.out.println (a + " / " + b + " = " + newC);
	    if (a.getNumerator() != 2 || a.getDenominator() != 3 ||
	        b.getNumerator() != 1 || b.getDenominator() != 5 ||
	        newE.getNumerator() != 7 || newE.getDenominator() != 15)
	        return;
	    else
	    	errorOrNot = true;
	    
	    
	    // this test checks our reducing method along with our multiply method.
	    Fraction aa = new Fraction (2, 4);
	    Fraction bb = new Fraction (1, 5);
	    Fraction cc = aa.multiply(bb);
	    System.out.println (aa + " * " + bb + " = " + cc);
	    if (aa.getNumerator() != 1 || aa.getDenominator() != 2 ||
	        bb.getNumerator() != 1 || bb.getDenominator() != 5 ||
	        cc.getNumerator() != 1 || cc.getDenominator() != 10)
	        return;
	    else
	    	errorOrNot = true;
	    
	    
	    
	 // this test checks our adding method along with our reducing method.
	    Fraction d = new Fraction (2, 4);
	    Fraction e = new Fraction (17, 5);
	    Fraction q = d.add(e);
	    System.out.println (d + " + " + e + " = " + q);
	    if (d.getNumerator() != 1 || d.getDenominator() != 2 ||
	        e.getNumerator() != 17 || e.getDenominator() != 5 ||
	        q.getNumerator() != 39 || q.getDenominator() != 10)
	        return;
	    else
	    	errorOrNot = true;
	    
	    
	    // this tests our divide and toDouble methods. 
	    Fraction r = d.divide(e);
	    double rDouble = r.toDouble();
	    System.out.println(rDouble);
	    System.out.println (d + " / " + e + " = " + r);
	    if (d.getNumerator() != 1 || d.getDenominator() != 2 ||
	        e.getNumerator() != 17 || e.getDenominator() != 5 ||
	        r.getNumerator() != 5 || r.getDenominator() != 34 
	        || rDouble != 0.14705882352941177)
	        return;
	    else
	    	errorOrNot = true;
	    
	    
	    // this will test our subtraction and our toDouble.
	    Fraction newQ = d.subtract(e);
	    double QDouble = newQ.toDouble();
	    System.out.println(newQ);
	    if (d.getNumerator() != 1 || d.getDenominator() != 2 ||
		        e.getNumerator() != 17 || e.getDenominator() != 5 ||
		        newQ.getNumerator() != -29 || newQ.getDenominator() != 10 
		        || QDouble != -2.9)
		        return;
		    else
		    	errorOrNot = true;
	    
	    
	    // testing one input fraction constructor
	   Fraction R = new Fraction(12); 
	   if (R.getNumerator() != 12 || R.getDenominator() !=1 )
		   return;
	    else
	    	errorOrNot = true;
	   
	   String RString = R.toString();
	   
	   
	   // testing toString method
	   System.out.println(R);
	   if (!RString.equals("12/1"))
			return;
	    else
	    	errorOrNot = true;
	   
	   long RDenominator = R.getDenominator();
	   long RNumerator = R.getNumerator();
	   System.out.println("Numerator: " + RNumerator + ", Denominator: " + RDenominator);
	   
	   // testing getNumerator() and getDenominator
	   if (RDenominator != 12 || RNumerator != 1)
		   errorOrNot = true;
	    else
	    	return;
	   
	   
	   Fraction S = new Fraction(24,12);
	   long SNumerator = S.getNumerator();
	   long SDenominator = S.getDenominator();
	   String SString = S.toString();
	   System.out.println("The fraction is " + S);
	   
	   // testing simplifying, toString, getNumerator, getDenominator
	   if (SNumerator != 2 || SDenominator != 2 || !SString.equals("2/1"))
		  errorOrNot = true;
	    else
	    	return;
	   
	   
	   // testing the one input fraction constructor
	   Fraction XR = new Fraction(7);
	   if (XR.getDenominator() != 1 || XR.getNumerator() != 7)
		   errorOrNot = true;
	    else
	    	return;
	   
	   if (errorOrNot == false)
		   System.out.println("All tests completed, no errors.");
	   else
		   System.out.println("All tests completed, errors found.");
	   
	}
	
	


}
