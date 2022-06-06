package assignment;

public class Main {
	
	public static void main(String[] args) {
		
		Airport airport = new Airport();
		RefillingFuel refillFuel = new RefillingFuel();
		RefillingSupplies refillSupplies = new RefillingSupplies();
		CleaningPlane cleanPlane = new CleaningPlane();
		ATC atc = new ATC(airport, refillFuel, refillSupplies, cleanPlane);
		PlaneGenerator planeGenerator = new PlaneGenerator(atc);
		RefuellingTruck truck = new RefuellingTruck(refillFuel, planeGenerator);
		Suppliers suppliers = new Suppliers(refillSupplies, planeGenerator);
		Cleaners cleaners = new Cleaners(cleanPlane, planeGenerator);
		
		Thread plg = new Thread (planeGenerator);
		Thread trk = new Thread (truck);
		Thread spl = new Thread (suppliers);
		Thread cln = new Thread (cleaners);
		
		//starting the thread will call its run method by itself
		plg.start();
		trk.start();
		spl.start();
		cln.start();
	}

}
