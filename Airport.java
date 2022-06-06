package assignment;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Airport {
	
	public static Semaphore gate = new Semaphore(2);	//semaphore is being set to 2 as there are only 2 gates in the airport
	protected boolean gate1 = true;
	protected boolean gate2 = true;
	protected int noPlaneServed = 0;
	protected int noPassengerBoarded = 0;
	protected int noPassengerUnboarded = 0;
	
	
	public void runway(Plane plane, int signal) {
		long duration=0;
		if(signal == 1) {
			try
	        {    
	            System.out.println(plane.getName() +": We are landing now");
	            duration = (long)(Math.random()*10);
	            TimeUnit.SECONDS.sleep(duration);
	        }
	        catch(InterruptedException iex)
	        {
	            iex.printStackTrace();
	        }
	        System.out.println(plane.getName() +": We have landed successfully in "+ duration+ " seconds");
		} else {
			System.out.println(plane.getName() +": We have undocked from Gate "+ plane.getGateNo());
			if(plane.getGateNo() == 1) {
				gate1 = true;
			} else {
				gate2 = true;
			}
			plane.setGateNo(0);
			gate.release();
			try
	        {    
	            System.out.println(plane.getName() +": We are coasting to runway now");
	            duration = (long)(Math.random()*10);
	            TimeUnit.SECONDS.sleep(duration);
	        }
	        catch(InterruptedException iex)
	        {
	            iex.printStackTrace();
	        }
	        System.out.println(plane.getName() +": We have taken off successfully in "+ duration+ " seconds");
	        noPlaneServed = noPlaneServed + 1;
		}
	}
	
	public void dockGate(Plane plane) {
		try
        {    
			System.out.println(plane.getName() +": We are coasting to Gate "+ plane.getGateNo());
            Thread.sleep(new Random().nextInt(3)*1000);
            System.out.println(plane.getName() +": We have docked to Gate "+ plane.getGateNo());
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        } 
	}
	
	public void disembark(Plane plane) {
		try {
			Random rand = new Random();
			int totalPassengers = rand.nextInt(50);
			String planeName = plane.getName();
			System.out.println(planeName +": Our passengers are disembarking now");
			for(int i = 1; i <= totalPassengers; i++) {
				System.out.println(planeName +": Passenger "+ i +" is disembarking from the plane");
				Thread.sleep(new Random().nextInt(2)*100);
			}
			noPassengerUnboarded = noPassengerUnboarded + totalPassengers;
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		}
	}
	
	public void doneRefillSuppliesandCleaning(Plane plane) {
		try {
			while(!plane.supplyStatus || !plane.cleanStatus) {
				Thread.sleep(3000);
			}
			plane.supplyStatus = false;
			plane.cleanStatus = false;
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		} finally {
			embark(plane);
		}
	}
	
	public void embark(Plane plane) {
		try {
			Random rand = new Random();
			int totalPassengers = rand.nextInt(50);
			String planeName = plane.getName();
			System.out.println(planeName +": Our passengers are embarking now");
			for(int i = 1; i <= totalPassengers; i++) {
				System.out.println(planeName +": Passenger "+ i +" is embarking to the plane");
				Thread.sleep(new Random().nextInt(2)*100);
			}
			noPassengerBoarded = noPassengerBoarded + totalPassengers;
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		} finally {
			doneRefillFuel(plane);
		}
	}
	
	public void doneRefillFuel(Plane plane) {
		try {
			while(!plane.fuelStatus) {
				Thread.sleep(3000);
			}
			plane.fuelStatus = false;
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		}
	}
}
