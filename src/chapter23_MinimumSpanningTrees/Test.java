package chapter23_MinimumSpanningTrees;

import java.util.List;
import java.util.Scanner;

public class Test
{

	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		Graph graph = new Graph();
		int edgeNum = scanner.nextInt();
		String u=null;
		String v=null;
		int weight=0;
		for (int i = 0; i < edgeNum; i++)
		{
			 u = scanner.next();
			 v = scanner.next();
			 weight=scanner.nextInt();
			graph.addAdge(u, v, weight);
		}
		
		MinimumSpanningTrees mst=new MinimumSpanningTrees();

		System.out.println("Kruskal算法");
		List<Edge> treeEdges=mst.kruskal(graph);
		for (Edge edge : treeEdges)
			System.out.println(edge);
		
		System.out.println("Prim算法");
		List<Vertex> treeVertexs=mst.prim(graph, "a");
		for (Vertex vertex : treeVertexs)
			System.out.println(vertex);
	}

}
