package chapter23_MinimumSpanningTrees;

import java.util.HashMap;
import java.util.Map;

public class Vertex implements Comparable<Vertex>
{
	private String name=null;//节点名称
	private int key=Integer.MAX_VALUE;//记录连接该节点与树中节点的所有边中最小边的权重
	private Vertex parent=null;//节点在树中是父结点
	private Map<String, Integer>adjMap;
	
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
	public int getKey()
	{
		return key;
	}
	public void setKey(int key)
	{
		this.key = key;
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
		return this.key-o.key;
	}
	
	@Override
	public String toString()
	{
		String string="";
		if(parent!=null)
			string+=parent.getName();
		return string+"——"+name+"："+key;
	}
}
