package chapter23_MinimumSpanningTrees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MinimumSpanningTrees
{
	public List<Edge> kruskal(Graph graph)
	{
		Queue<Edge> queue = graph.getEdges();
		int n = graph.getVertexs().size();// 图的顶点个数
		List<Edge> treeEdges = new ArrayList<>();// 返回的最小生成树的边的集合
		int edgeCount = 0;// 记录生成树边的数量
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		// map用于标识边的某个顶点属于哪个集合，认为顶点刚开始属于不同的集合，当选择一条边时，就合并两个集合，如果选择的边在同一个集合内，就代表出现环路

		Edge e = null;
		while (edgeCount < n - 1 && !queue.isEmpty())
		{
			e = queue.poll();
			Set<String> setU = map.get(e.getU());
			Set<String> setV = map.get(e.getV());
			// 两顶点都未出现在其他集合中
			if (setU == null && setV == null)
			{
				Set<String> set = new HashSet<>();
				set.add(e.getU());
				set.add(e.getV());
				map.put(e.getU(), set);
				map.put(e.getV(), set);
			}// 有一个顶点在其他集合中，一个不在，将不在的那个顶点集合合并进去
			else if (setU == null && setV != null)
			{
				setV.add(e.getU());
				map.put(e.getU(), setV);
			} else if (setU != null && setV == null)
			{
				setU.add(e.getV());
				map.put(e.getV(), setU);
			}
			// 分别在不同的集合中，合并两个集合
			else if (setU != setV)
			{
				for (String string : setV)
					map.put(string, setU);
				setU.addAll(setV);
			}
			// 两个顶点在同一个结合中，会出现环路，舍弃
			else
			{
				continue;
			}
			edgeCount++;
			treeEdges.add(e);
		}

		return treeEdges;
	}

	/**
	 * 运行时间取决于最小优先队列Q的实现方式，可采用二叉最小优先队列（第6章）
	 * @param graph
	 * @param start
	 * @return
	 */
	public List<Vertex> prim(Graph graph,String start)
	{
		List<Vertex> treeVertexs=new ArrayList<>();
		Vertex r=graph.search(start);
		r.setKey(0);
		//PriorityQueue<Vertex> queue=new PriorityQueue<>(graph.getVertexs());
		List<Vertex> vertexs=graph.getVertexs();
		Vertex u=null,v=null;
		Map<String, Integer> uMap=null;
		Set<String> uKeySet=null;
		while (!vertexs.isEmpty())
		{
			u=extractMin(vertexs);
			treeVertexs.add(u);
			uMap=u.getAdjMap();
			uKeySet=uMap.keySet();
			for (String string : uKeySet)
			{
				v=graph.search(string);
				if (vertexs.contains(v)&&uMap.get(string)<v.getKey())
				{
					v.setParent(u);
					v.setKey(uMap.get(string));
				}
			}
		}
		return treeVertexs;
	}

	private Vertex extractMin(List<Vertex> vertexs)
	{
		Vertex result=vertexs.get(0);
		for (Vertex vertex : vertexs)
			if(vertex.getKey()<result.getKey())
				result=vertex;
		vertexs.remove(result);
		return result;
	}
}
