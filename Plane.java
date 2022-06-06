package assignment;

import java.util.Date;
import java.time.Duration;
import java.time.LocalTime;

public class Plane implements Runnable {
	private String name;
    private Date requestTime;
    private LocalTime startWaitingTime;
    private LocalTime endWaitingTime;
    private int gateNo;
    private int indicator;
    private ATC atc;
    protected boolean fuelStatus = false;
    protected boolean supplyStatus = false;
    protected boolean cleanStatus = false;
 
   
    public Plane(ATC atc)
    {
        this.atc = atc;
    }
    
    public int getGateNo() {
    	return this.gateNo;
    }
    
    public int getIndicator() {
    	return this.indicator;
    }
 
    public String getName() {
        return this.name;
    }
 
    public Date getRequestTime() {
        return this.requestTime;
    }
    
    public LocalTime getStartWaitingTime() {
        return this.startWaitingTime;
    }
    
    public LocalTime getEndWaitingTime() {
        return this.endWaitingTime;
    }
    
    public void setGateNo(int gateNo)
    {
        this.gateNo = gateNo;
    }
    
    public void setIndicator(int indicator)
    {
        this.indicator = indicator;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    public void setStartWaitingTime(LocalTime startWaitingTime) {
        this.startWaitingTime = startWaitingTime;
    }
    
    public void setEndWaitingTime(LocalTime endWaitingTime) {
        this.endWaitingTime = endWaitingTime;
    }
 
    public void run() {
    	System.out.println(this.name +": Requesting for Landing on "+ this.requestTime);
        atc.requestLanding(this);
    }
    
    public long timeDifference() {
    	Duration duration = Duration.between(startWaitingTime, endWaitingTime);
        return duration.toMillis();
    }
}
