package chapter22_ElementaryGraphAlgorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph
{
	private List<Vertex> vertexs;//顶点列表
	private Map<String, List<Vertex>> adjMap;//顶点列表对应的邻接表
	
	public Graph()
	{
		vertexs=new ArrayList<>();
		adjMap=new HashMap<>();
	}
	
	//图的转置
	public Graph transpose()
	{
		Graph graph=new Graph();
		Set<String> strings=this.adjMap.keySet();
		List<Vertex> temp=new ArrayList<>();
		for (String string : strings)
		{
			temp=this.adjMap.get(string);
			for (Vertex vertex : temp)
			{
				graph.addAdj(vertex.getKey(), string,true);
			}
		}
		return graph;
	}

	public List<Vertex> getVertexs()
	{
		return vertexs;
	}

	public void setVertexs(List<Vertex> vertexs)
	{
		this.vertexs = vertexs;
	}

	public Map<String, List<Vertex>> getAdjMap()
	{
		return adjMap;
	}

	public void setAdjMap(Map<String, List<Vertex>> adjMap)
	{
		this.adjMap = adjMap;
	}

	/**
	 * 根据edge创建邻接表：首先检查vertex表中是否存在相应顶点，若不存在先创建
	 * 
	 * @param from
	 * @param to
	 * @param directed:true有向图，false无向图
	 */
	public void addAdj(String from, String to,boolean directed)
	{
		//add vertex
		Vertex fromVertex = search(from);
		Vertex toVertex = search(to);
		if ( fromVertex== null)
		{
			fromVertex = new Vertex(from);
			vertexs.add(fromVertex);
		}
		if (toVertex == null)
		{
			toVertex = new Vertex(to);
			vertexs.add(toVertex);
		}

		//add adj
		List<Vertex> fromList = null;
		List<Vertex> toList = null;
		Set<String> fromSet = adjMap.keySet();
		if (fromSet.contains(from))
		{
			fromList = adjMap.get(from);
		} else
		{
			fromList = new ArrayList<>();
			adjMap.put(from, fromList);
		}
		fromList.add(toVertex);

		if (!directed)
		{
			if (fromSet.contains(to))
			{
				toList = adjMap.get(to);
			} else
			{
				toList = new ArrayList<>();
				adjMap.put(to, toList);
			}
			toList.add(fromVertex);
		}
	}

	
	/**
	 * 根据key查找当前图是否已存在相应顶点
	 * 
	 * @param key
	 * @return
	 */
	public Vertex search(String key)
	{
		for (Vertex vertex : vertexs)
			if (vertex.getKey().equals(key))
				return vertex;
		return null;
	}
}
