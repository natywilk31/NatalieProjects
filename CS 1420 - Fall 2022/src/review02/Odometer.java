package review02;

public class Odometer {
	private int distance;
	public Odometer() {
		distance = 0;
	}
	
	public int returnDistance() {
		return this.distance % 100000;
	}
	
	public void addAmount(int miles) {
		this.distance = (this.distance + miles) % 100000;
	}
	

}





