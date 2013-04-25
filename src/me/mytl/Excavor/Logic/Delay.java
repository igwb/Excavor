package me.mytl.Excavor.Logic;

public class Delay
{
	private double time;
	
	private double delay;
	
	
	public Delay(double delay)
	{
		this.time = System.currentTimeMillis();
		this.delay = delay;
	}
	
	public void expand(double addDelay)
	{
		delay += addDelay;
	}
	
	public boolean checkDelay()
	{
		if (time + delay <= System.currentTimeMillis()) {
			return true;
		} else {
			return false;
		}
	}
	
	public double getTimeLeft() {
		
		return ( time + delay ) - System.currentTimeMillis();
		
	}
}
