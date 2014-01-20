package chapter22_ElementaryGraphAlgorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BreadthFirstSearch
{
	/**
	 * 通过广度优先搜索可以生成广度优先树，求从源节点到各个节点的最短路径
	 * 
	 * @param graph
	 * @param s
	 */
	public void BFS(Graph graph, String key)
	{
		Vertex s = graph.search(key);
		s.setColor(Color.GRAY);
		s.setDistance(0);
		s.setPredecessor(null);
		Queue<Vertex> queue = new LinkedList<>();
		queue.add(s);

		Map<String, List<Vertex>> adjMap = graph.getAdjMap();
		List<Vertex> list = null;
		while (!queue.isEmpty())
		{
			Vertex u = queue.poll();
			list = adjMap.get(u.getKey());
			for (Vertex vertex : list)
			{
				if (vertex.getColor() == Color.WHITE)
				{
					vertex.setColor(Color.GRAY);
					vertex.setDistance(u.getDistance() + 1);
					vertex.setPredecessor(u);
					queue.add(vertex);
				}
			}
			u.setColor(Color.BLACK);
		}
	}

	/**
	 * 打印广度优先树
	 * @param graph
	 * @param s
	 * @param v
	 */
	public void printPath(Graph graph, String s, String v)
	{
		if (s.equals(v))
			System.out.print(s);
		else
		{
			Vertex vertex = graph.search(v);
			if (vertex.getPredecessor() == null)
				System.out.println("no path form" + s + "to" + v + "exists");
			else
			{
				printPath(graph, s, vertex.getPredecessor().getKey());
				System.out.print(v);

			}
		}
	}
}
