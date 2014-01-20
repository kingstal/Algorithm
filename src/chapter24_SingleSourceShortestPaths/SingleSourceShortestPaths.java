package chapter24_SingleSourceShortestPaths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SingleSourceShortestPaths
{
	/**
	 * 初始化
	 * 
	 * @param graph
	 * @param s
	 */
	public void initialize(Graph graph, String s)
	{
		Vertex sVertex = graph.search(s);
		sVertex.setDistance(0);
	}

	/**
	 * 松弛操作
	 * 
	 * @param u
	 * @param v
	 */
	public void relax(Vertex u, Vertex v)
	{
		int temp = u.getDistance() + u.getAdjMap().get(v.getName());
		if (v.getDistance() > temp)
		{
			v.setDistance(temp);
			v.setParent(u);
		}
	}

	/**
	 * Bellman-Ford算法:O(VE)对图进行V-1次松弛操作，得到所有可能的最短路径
	 * 
	 * @param graph
	 * @param start
	 * @return 不包含从源节点可到达的权重为负值的环路，返回true
	 */
	public boolean bellmanFord(Graph graph, String start)
	{
		// 初始化所有节点的distance值和parent
		initialize(graph, start);

		// 重复对每一条边进行松弛操作[V-1次]
		int vertexNum = graph.getVertexs().size();
		List<Edge> edges = graph.getEdges();
		for (int i = 1; i < vertexNum; i++)
			for (Edge edge : edges)
				relax(graph.search(edge.getU()), graph.search(edge.getV()));

		// 检查负权环,如果发现第n次操作仍可降低花销，就一定存在负权环
		for (Edge edge : edges)
		{
			Vertex u = graph.search(edge.getU());
			Vertex v = graph.search(edge.getV());
			if (v.getDistance() > u.getDistance() + u.getAdjMap().get(edge.getV()))
				return false;
		}
		return true;
	}

	/**
	 * 有向无环图中单源最短路径问题：先拓扑排序确定节点间的线性次序，再对每个结点处理（从该节点出发的所有边进行松弛操作）
	 * 
	 * @param graph
	 * @param start
	 */
	public void dagShortestPaths(Graph graph, String start)
	{
		// 拓扑排序
		TopoLogicalSort topoLogicalSort = new TopoLogicalSort();
		List<Vertex> topoList = topoLogicalSort.topoLogicalSort(graph);

		// 初始化
		initialize(graph, start);

		// 松弛操作:从开始节点进行（开始节点以前的节点到达不了，距离为无穷）
		int index = topoList.indexOf(graph.search(start));
		Vertex temp = null;
		Set<String> set = null;
		for (int i = index; i < topoList.size(); i++)
		{
			temp = topoList.get(i);
			set = temp.getAdjMap().keySet();
			for (String string : set)
				relax(temp, graph.search(string));
		}
	}
	
	/**
	 * Dijkstra算法：解决带权重有向图单源最短路径，要求所有边权重非负
	 * 		算法维护节点集合S。重复从V-S中选择最短路径估计最小的节点u加入到S，然后对从u发出的边进行松弛
	 * @param graph
	 * @param start
	 */
	public void dijkstra(Graph graph,String start)
	{
		initialize(graph, start);
		Set<Vertex> set=new HashSet<>();
		List<Vertex> vertexs=new ArrayList<>();
		List<Vertex> graphVertexs=graph.getVertexs();
		for (Vertex vertex : graphVertexs)
			vertexs.add(vertex);
		
		Vertex u=null;
		Vertex v=null;
		Map<String, Integer> adjMap=null;
		Set<String> keySet=null;
		while (!vertexs.isEmpty())
		{
			u=extractMin(vertexs);
			set.add(u);
			adjMap=u.getAdjMap();
			keySet=adjMap.keySet();
			for (String string : keySet)
			{
				v=graph.search(string);
				relax(u, v);
			}
		}
	}
	
	private Vertex extractMin(List<Vertex> vertexs)
	{
		Vertex result=vertexs.get(0);
		for (Vertex vertex : vertexs)
			if(vertex.getDistance()<result.getDistance())
				result=vertex;
		vertexs.remove(result);
		return result;
	}
}
