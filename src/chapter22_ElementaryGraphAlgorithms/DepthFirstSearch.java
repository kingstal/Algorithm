package chapter22_ElementaryGraphAlgorithms;

import java.util.List;

public class DepthFirstSearch
{
	int time = 0;

	public void DFS(Graph graph)
	{
		List<Vertex> vertexs = graph.getVertexs();
		for (Vertex u : vertexs)
		{
			if (u.getColor() == Color.WHITE)
				DFSVisit(graph, u);
		}
	}

	private void DFSVisit(Graph graph, Vertex u)
	{
		time++;// white vertex u has just been discovered
		u.setDiscover(time);
		u.setColor(Color.GRAY);
		List<Vertex> list = graph.getAdjMap().get(u.getKey());
		for (Vertex v : list)// explore edge(u,v)
		{
			if (v.getColor() == Color.WHITE)
			{
				v.setPredecessor(u);
				DFSVisit(graph, v);
			}
		}
		u.setColor(Color.BLACK);// blacken u;it is finished
		time++;
		u.setFinish(time);
	}
}
