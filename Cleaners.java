package assignment;

public class Cleaners implements Runnable {
	
	private CleaningPlane cleanPlane;
	private PlaneGenerator planeGenerator;
	
	public Cleaners(CleaningPlane cleanPlane, PlaneGenerator planeGenerator) {
		this.cleanPlane = cleanPlane;
		this.planeGenerator = planeGenerator;
	}
	
	public void run() {
		int totalPlane = 1;
		while (totalPlane <= planeGenerator.noOfPlane) {
			cleanPlane.cleaningPlane();
			totalPlane++;
		}
	}
}
