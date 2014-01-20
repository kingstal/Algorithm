package chapter22_ElementaryGraphAlgorithms;

import java.util.LinkedList;
import java.util.List;

/**
 * 拓扑排序
 * 
 * @author Arthur
 * 
 */
public class TopoLogicalSort
{
	int time = 0;

	/**
	 * 拓扑排序：调用DFS，当每个顶点完成搜索后，将其加入链表头中，最后链表顺序即为拓扑顺序
	 * @param graph
	 * @return
	 */
	public List<Vertex> topoLogicalSort(Graph graph)
	{
		List<Vertex> result = new LinkedList<>();
		DFS(graph, result);
		return result;
	}

	private void DFS(Graph graph, List<Vertex> result)
	{
		List<Vertex> vertexs = graph.getVertexs();
		for (Vertex u : vertexs)
		{
			if (u.getColor() == Color.WHITE)
				DFSVisit(graph, u, result);
		}
	}

	private void DFSVisit(Graph graph, Vertex u, List<Vertex> result)
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
				DFSVisit(graph, v, result);
			}
		}
		u.setColor(Color.BLACK);// blacken u;it is finished
		time++;
		u.setFinish(time);
		result.add(0, u);;// as each vertex is finished,insert it onto the front of
						// a linked list
	}
}
