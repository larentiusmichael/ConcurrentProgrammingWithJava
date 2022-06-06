package assignment;

public class Suppliers implements Runnable {
	
	private RefillingSupplies refillSupplies;
	private PlaneGenerator planeGenerator;
	
	public Suppliers(RefillingSupplies refillSupplies, PlaneGenerator planeGenerator) {
		this.refillSupplies = refillSupplies;
		this.planeGenerator = planeGenerator;
	}
	
	public void run() {
		int totalPlane = 1;
		while (totalPlane <= planeGenerator.noOfPlane) {
			refillSupplies.refillSupplies();
			totalPlane++;
		}
	}
}
