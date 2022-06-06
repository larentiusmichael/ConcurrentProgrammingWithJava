package assignment;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RefillingSupplies {
	
	private LinkedList<Plane> listPlane = new LinkedList<Plane>();
	
	public void add(Plane plane) {
		synchronized (listPlane) {
			listPlane.offer(plane);
	        
	        if(listPlane.size()== 1) {
	        	listPlane.notify();
	        }
		}
	}
	
	public void refillSupplies() {
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
            	System.out.println("Suppliers: We are going to refill supplies of "+ plane.getName());
            	Thread.sleep(new Random().nextInt(3)*1000);
            	System.out.println("Suppliers: We are starting refilling supplies of "+ plane.getName());
        		duration = (long)(Math.random()*10);
	            TimeUnit.SECONDS.sleep(duration);
        		plane.supplyStatus = true;
        		System.out.println("Suppliers: We have done refilling supplies of "+ plane.getName() +" in "+ duration +" seconds");
            } catch (InterruptedException iex) {
            	iex.printStackTrace();
            }
        }
    }
}
	      
