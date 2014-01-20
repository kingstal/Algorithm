package chapter24_SingleSourceShortestPaths;

import java.util.Scanner;

public class Test
{

	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		Graph graph = new Graph();
		int edgeNum = scanner.nextInt();
		String u = null;
		String v = null;
		int weight = 0;
		for (int i = 0; i < edgeNum; i++)
		{
			u = scanner.next();
			v = scanner.next();
			weight = scanner.nextInt();
			graph.addAdge(u, v, weight);
		}

		SingleSourceShortestPaths sPaths = new SingleSourceShortestPaths();
		// BellmanFord算法
		/*boolean b = sPaths.bellmanFord(graph, "s");
		if (b)
			for (Vertex vertex : graph.getVertexs())
				System.out.println(vertex);*/

		// DAG单源最短路径
		/*sPaths.dagShortestPaths(graph, "s");
		for (Vertex vertex : graph.getVertexs())
			System.out.println(vertex);*/
		
		//Dijkstra算法
		sPaths.dijkstra(graph, "s");
		for (Vertex vertex : graph.getVertexs())
			System.out.println(vertex);
	}

}
