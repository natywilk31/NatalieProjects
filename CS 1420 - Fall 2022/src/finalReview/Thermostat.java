package finalReview;

public class Thermostat {

	public static void main(String[] args) {
		Heater ourHeater = new Heater();
		Cooler ourCooler = new Cooler();
		double min;
		double max;
		
		

	}
	
	public static void checkTemp (double currentTemp) {
		if (currentTemp < min) {
			ourHeater.turnOn();
			ourCooler.turnOff();}
		if (currentTemp > max) {
			ourCooler.turnOn();
			ourHeater.turnOff();}
		if (currentTemp <= max || currentTemp >= min) {
			ourHeater.turnOff();
			ourCooler.turnOff();
		}
		
	}
	
	

}
