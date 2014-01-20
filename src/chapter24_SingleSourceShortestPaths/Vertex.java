package chapter24_SingleSourceShortestPaths;

import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex>
{
	private String name=null;//节点名称
	private int distance=Integer.MAX_VALUE;//最短路径估计
	private Vertex parent=null;//节点在树中是父结点
	private Map<String, Integer>adjMap;
	
	private int discover;//记录发现节点的时刻
	private int finish;//记录完成对节点处理的时刻
	private Color color = Color.WHITE;
	
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
	public Color getColor()
	{
		return color;
	}
	public void setColor(Color color)
	{
		this.color = color;
	}
	public Vertex(String name)
	{
		super();
		this.name = name;
		adjMap=new HashMap<>();
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getDistance()
	{
		return distance;
	}
	public void setDistance(int key)
	{
		this.distance = key;
	}
	public Vertex getParent()
	{
		return parent;
	}
	public void setParent(Vertex parent)
	{
		this.parent = parent;
	}
	public Map<String, Integer> getAdjMap()
	{
		return adjMap;
	}
	public void setAdjMap(Map<String, Integer> adjMap)
	{
		this.adjMap = adjMap;
	}
	@Override
	public int compareTo(Vertex o)
	{
		return this.distance-o.distance;
	}
	
	@Override
	public String toString()
	{
		String string="";
		if(parent!=null)
			string+=parent.getName();
		return string+"——"+name+"："+distance;
	}
}
