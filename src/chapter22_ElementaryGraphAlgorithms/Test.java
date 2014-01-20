package chapter22_ElementaryGraphAlgorithms;

import java.util.Scanner;

public class Test
{

	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		// 测试无向图
		/*Graph graph = new Graph();
		int edgeNum = scanner.nextInt();
		for (int i = 0; i < edgeNum; i++)
		{
			String from = scanner.next();
			String to = scanner.next();
			graph.addAdj(from, to, false);
		}
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		bfs.BFS(graph, "s");
		List<Vertex> vertexs = graph.getVertexs();
		for (Vertex vertex : vertexs)
			System.out.println("key: " + vertex.getKey() + " dis: " + vertex.getDistance());
		bfs.printPath(graph, "s", "y");*/

		//有向图
		Graph graph2 = new Graph();
		int edgeNum2 = scanner.nextInt();
		for (int i = 0; i < edgeNum2; i++)
		{
			String from = scanner.next();
			String to = scanner.next();
			graph2.addAdj(from, to, true);
		}
		
		//拓扑排序
		/*TopoLogicalSort topoLogicalSort = new TopoLogicalSort();
		List<Vertex> list = topoLogicalSort.topoLogicalSort(graph2);
		System.out.println("拓扑排序：");
		for (Vertex vertex : list)
			System.out.print(vertex.getKey() + vertex.getDiscover()+" "+vertex.getFinish()+" ");
*/	
		//强连通分量
		System.out.println("强连通分量：");
		StronglyConnectedComponents sConnectedComponents=new StronglyConnectedComponents();
		sConnectedComponents.stronglyConnectedComponents(graph2);
	}

}
