package me.igwb.Excavor.Logic;

public class Delay
{
	private double time;
	
	private double delay;
	
	
	public Delay(double delay)
	{
		this.time = System.currentTimeMillis();
		this.delay = delay;
	}
	
	public void expand(double Delay)
	{
		time = System.currentTimeMillis();
		
		delay = Delay;
	}
	
	public void expand()
	{
		time = System.currentTimeMillis();
	}
	
	public boolean checkDelay()
	{
		if (time + delay <= System.currentTimeMillis()) {			
			return true;
		} else {
			return false;
		}
	}
	
	public double getTimeLeft()
	{		
		return ( time + delay ) - System.currentTimeMillis();		
	}
}
