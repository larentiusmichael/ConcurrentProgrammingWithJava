package assignment;

import java.util.*;
import java.time.LocalTime;

public class PlaneGenerator implements Runnable {
	
	private ATC atc;
	protected static int noOfPlane = 6;
	
	public PlaneGenerator(ATC atc) {
		this.atc = atc;
	}
	
	public void run() {
		for(int i = 1; i <=noOfPlane; i++) {
			Plane plane = new Plane(atc);
			plane.setName("Plane "+ i);
			plane.setRequestTime(new Date());
			plane.setStartWaitingTime(LocalTime.now());
			plane.setGateNo(0);
			plane.setIndicator(1);
			Thread thePlane = new Thread(plane);
			thePlane.start();
			
			try {
				Thread.sleep(new Random().nextInt(3)*1000); 	//to make new airplane arrive every 0/1/2/3 second(s)
            } catch(InterruptedException iex) {
                iex.printStackTrace();
            }
		}
	}
}
