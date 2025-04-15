package review02;

public class odometerTester {

	public static void main(String[] args) {
		Odometer ourOdometer = new Odometer();
		ourOdometer.addAmount(17);
		ourOdometer.addAmount(110);
		ourOdometer.addAmount(99913);
		System.out.println(ourOdometer.returnDistance());
		
		
	}

}
