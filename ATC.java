package assignment;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.time.LocalTime;

public class ATC {
	
	private Airport airport;
	private int signal = 0;
	static Lock z = new ReentrantLock();
	static Lock y = new ReentrantLock();
	private RefillingFuel refillFuel;
	private RefillingSupplies refillSupplies;
	private CleaningPlane cleanPlane;
	private long maxWaitTime = 0;
	private long minWaitTime = 0;
	private long totalWaitTime = 0;
	private int count = 0;
	
	public ATC(Airport airport, RefillingFuel refillFuel, RefillingSupplies refillSupplies, CleaningPlane cleanPlane) {
		this.airport = airport;
		this.refillFuel = refillFuel;
		this.refillSupplies = refillSupplies;
		this.cleanPlane = cleanPlane;
	}
	
	public void requestLanding(Plane plane) {
		try {
			System.out.println("ATC: To "+ plane.getName() +", we will inform your permission to land soon. Kindly wait");
			Thread.sleep(3000);
			
			//to inform to plane number of available gates
			synchronized(this) {
				System.out.println("ATC: To "+ plane.getName() +", number of available gates now is "+ airport.gate.availablePermits());
				if(airport.gate.availablePermits() == 0) {
					plane.setIndicator(0);
				}
			}
			
			airport.gate.acquire();		//plane will wait if there is no gate available		
			landingApproved(plane);
		} catch(InterruptedException iex) {
			iex.printStackTrace();
		}
	}
	
	public void landingApproved(Plane plane) {
		//ATC assigning which gate the plane should assign after landing
		y.lock();
		if (plane.getIndicator() == 0) {
			System.out.println("ATC: To "+ plane.getName() +", a gate is already available now");
			plane.setIndicator(1);
		}
		if(airport.gate1) {
			System.out.println("ATC: "+ plane.getName() +" must assign to Gate 1 after landing");
			plane.setGateNo(1);
			airport.gate1 = false;
		} else {
			System.out.println("ATC: "+ plane.getName() +" must assign to Gate 2 after landing");
			plane.setGateNo(2);
			airport.gate2 = false;
		}
		y.unlock();
		
		//lock is being used to ensure runway must be used one by one by plane
		z.lock();
		signal = 1;
		System.out.println("ATC: "+ plane.getName() +" is allowed to land now");
		plane.setEndWaitingTime(LocalTime.now());
		long waitDuration = plane.timeDifference();
		if (count == 0)
		{
			maxWaitTime = waitDuration;
			minWaitTime = waitDuration;
			totalWaitTime = waitDuration;
			count++;
		}
		else
		{
			if (waitDuration > maxWaitTime)
			{
				maxWaitTime = waitDuration;
			}
			if (waitDuration < minWaitTime)
			{
				minWaitTime = waitDuration;
			}
			totalWaitTime = totalWaitTime + waitDuration;
			count++;
		}
		airport.runway(plane, signal);
		z.unlock();
		
		onLandOperation(plane);
	}
	
	public void onLandOperation(Plane plane) {
		airport.dockGate(plane);
		refillFuel.add(plane);
		airport.disembark(plane);
		refillSupplies.add(plane);
		cleanPlane.add(plane);
		airport.doneRefillSuppliesandCleaning(plane);
		
		takeoffApproved(plane);
	}
	
	public void takeoffApproved(Plane plane) {
		//same lock is being used as landing as this method is also about acquiring the runway
		z.lock();
		this.signal = 2;
		System.out.println("ATC: "+ plane.getName() +" is allowed to take off now");
		airport.runway(plane, signal);
		if (airport.noPlaneServed == PlaneGenerator.noOfPlane)
		{
			sanityCheck();
		}
		z.unlock();
	}
	
	//printing the statistic
	public void sanityCheck() {
		System.out.println("\n\n==============================================================");
		System.out.println("ATC: SANITY CHECK");
		if (airport.gate1 == true && airport.gate2 == true)
		{
			System.out.println("ATC: All gates are empty already");
		}
		System.out.println("ATC: Number of Planes served is "+ airport.noPlaneServed);
		System.out.println("ATC: Number of Passengers Embarked is "+ airport.noPassengerBoarded);
		System.out.println("ATC: Number of Passengers Disembarked is "+ airport.noPassengerUnboarded);
		System.out.println("ATC: Longest waiting time of a plane is "+ maxWaitTime +" millisecond(s)");
		System.out.println("ATC: Shortest waiting time of a plane is "+ minWaitTime +" millisecond(s)");
		System.out.println("ATC: Average waiting time of a plane is "+ totalWaitTime / airport.noPlaneServed +" millisecond(s)");
		System.out.println("==============================================================\n\n");
	}
}
