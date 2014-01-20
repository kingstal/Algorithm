package chapter14;

/**
 * 区间对象
 * @author Arthur
 *
 */
public class Interval
{
	public int getLow()
	{
		return low;
	}
	public void setLow(int low)
	{
		this.low = low;
	}
	public int getHigh()
	{
		return high;
	}
	public void setHigh(int high)
	{
		this.high = high;
	}
	
	private int low;
	private int high;
	
	public Interval(int low, int high)
	{
		super();
		this.low = low;
		this.high = high;
	}
	
}
