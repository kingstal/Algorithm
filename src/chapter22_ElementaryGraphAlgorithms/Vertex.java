package chapter22_ElementaryGraphAlgorithms;

public class Vertex
{
	private String key;
	private Color color = Color.WHITE;
	private int distance = Integer.MAX_VALUE;//记录从源节点到该节点的距离，用于BFS
	private int discover;//记录发现节点的时刻
	private int finish;//记录完成对节点处理的时刻
	private Vertex predecessor = null;// 前驱
	
	public Vertex(String key)
	{
		super();
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	public int getDistance()
	{
		return distance;
	}

	public void setDistance(int distance)
	{
		this.distance = distance;
	}

	public int getDiscover()
	{
		return discover;
	}

	public void setDiscover(int discover)
	{
		this.discover = discover;
	}

	public int getFinish()
	{
		return finish;
	}

	public void setFinish(int finish)
	{
		this.finish = finish;
	}

	public Vertex getPredecessor()
	{
		return predecessor;
	}

	public void setPredecessor(Vertex predecessor)
	{
		this.predecessor = predecessor;
	}
}
