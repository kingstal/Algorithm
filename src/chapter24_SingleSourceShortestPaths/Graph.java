package chapter24_SingleSourceShortestPaths;

import java.util.ArrayList;
import java.util.List;

/**
 * 有向图
 * @author Arthur
 *
 */
public class Graph
{
	private List<Edge> edges = null;
	private List<Vertex> vertexs = null;

	public Graph()
	{
		super();
		this.edges = new ArrayList<>();
		this.vertexs = new ArrayList<>();
	}

	public List<Edge> getEdges()
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
