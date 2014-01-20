package chapter24_SingleSourceShortestPaths;

public class Edge implements Comparable<Edge>
{
	private String u;//起点
	private String v;//终点
	private double weight;//权重
	
	public Edge(String u, String v, double weight)
	{
		super();
		this.u = u;
		this.v = v;
		this.weight = weight;
	}

	public String getU()
	{
		return u;
	}

	public void setU(String u)
	{
		this.u = u;
	}

	public String getV()
	{
		return v;
	}

	public void setV(String v)
	{
		this.v = v;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o)
	{
		return (int) (this.weight-o.weight);
	}

	@Override
	public String toString()
	{
		return  u + "——" + v + "：" + weight ;
	}
	
}
