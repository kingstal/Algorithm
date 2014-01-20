package chapter23_MinimumSpanningTrees;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph
{
	private PriorityQueue<Edge> edges = null;// Kruscal使用，保存边的优先队列
	private List<Vertex> vertexs = null;// Prim使用，保存节点

	public Graph()
	{
		super();
		this.edges = new PriorityQueue<>();
		this.vertexs = new ArrayList<>();
	}

	public PriorityQueue<Edge> getEdges()
	{
		return edges;
	}

	public List<Vertex> getVertexs()
	{
		return vertexs;
	}

	public void addAdge(String u, String v, int weight)
	{
		Vertex vertexU = search(u);
		Vertex vertexV = search(v);
		// add vertex
		if (vertexU == null)
		{
			vertexU = new Vertex(u);
			vertexs.add(vertexU);
		}
		if (vertexV == null)
		{
			vertexV = new Vertex(v);
			vertexs.add(vertexV);
		}
		// add adj
		vertexU.getAdjMap().put(v, weight);
		vertexV.getAdjMap().put(u, weight);

		// add edge
		Edge e = new Edge(u, v, weight);
		edges.add(e);
	}

	public Vertex search(String name)
	{
		for (Vertex vertex : vertexs)
		{
			if (vertex.getName().equals(name))
				return vertex;
		}
		return null;
	}

}
