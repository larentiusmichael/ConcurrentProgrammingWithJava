package assignment;

public class RefuellingTruck implements Runnable {
	
	private RefillingFuel refillFuel;
	private PlaneGenerator planeGenerator;
	
	public RefuellingTruck(RefillingFuel refillFuel, PlaneGenerator planeGenerator) {
		this.refillFuel = refillFuel;
		this.planeGenerator = planeGenerator;
	}
	
	public void run() {
		int totalPlane = 1;
		while (totalPlane <= planeGenerator.noOfPlane) {
			refillFuel.refillFuel();
			totalPlane++;
		}
	}
}
