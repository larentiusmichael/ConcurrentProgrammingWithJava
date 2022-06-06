package assignment;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RefillingFuel {
	
	private LinkedList<Plane> listPlane = new LinkedList<Plane>();
	
	//adding the plane to the Linked List, this will ensure first come first serve
	public void add(Plane plane) {
		synchronized (listPlane) {
			listPlane.offer(plane);
	        
	        if(listPlane.size()== 1) {
	        	listPlane.notify();
	        }
		}
	}
	
	public void refillFuel() {
        Plane plane;
        synchronized (listPlane) {
            while(listPlane.size()== 0)
            {
                try
                {
                    listPlane.wait();
                }
                catch (InterruptedException iex)
                {
                    iex.printStackTrace();
                }
            }
            long duration=0;
            try {
            	plane = listPlane.poll();
            	System.out.println("Fuel Truck: We are heading to "+ plane.getName());
            	Thread.sleep(new Random().nextInt(3)*1000);
        		System.out.println("Fuel Truck: We are refilling fuel of "+ plane.getName());
        		duration = (long)(Math.random()*10);
	            TimeUnit.SECONDS.sleep(duration);
        		plane.fuelStatus = true;
        		System.out.println("Fuel Truck: We have done refilling fuel of "+ plane.getName() +" in "+ duration +" seconds");
            } catch (InterruptedException iex) {
            	iex.printStackTrace();
            }
        }
    }
}
	      
