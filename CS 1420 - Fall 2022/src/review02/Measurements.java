package review02;

public class Measurements {
	
	private double feet;
	private double inches;
	public Measurements (double f, double i) {
		this.feet = f + i/12;
		this.inches = i%12;
	}
	
	public double getFeet() {
		return this.feet;
	}
	
	public double getInches() {
		return this.inches;
	}
	
	public void changeMeasurement(double f, double i) {
		this.feet = f + i / 12;
		this.inches = i % 12;
	}
	
	public String toString () {
		return this.feet + " feet " + this.inches + "inches";
	}
	
	
}
