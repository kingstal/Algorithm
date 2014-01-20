package chapter22_ElementaryGraphAlgorithms;

import java.util.List;

/**
 * 计算强连通分量分为4步：
 * 1、对图进行第一次DFS，计算节点的finish，并按其降序排列（伪拓扑排序）
 * 2、计算图的转置（即原图的后向边变为树边）
 * 3、对转置图进行第二次DFS（按照第一次DFS获得的降序进行遍历）
 * 4、输出各棵深度优先树
 * @author Arthur
 *
 */
public class StronglyConnectedComponents
{
	public void stronglyConnectedComponents(Graph graph)
	{
		//1.call dfs(g) to compute finishing times u.f for each vertex u
		TopoLogicalSort tSort=new TopoLogicalSort();
		List<Vertex> firstDFSResult=tSort.topoLogicalSort(graph);
		
		//2.compute transpose of graph
		Graph graph2=graph.transpose();
		
		//3.call dfs(tarnspose of g),以1中计算的伪拓扑排序调用
		Vertex temp=null;
		for (Vertex vertex : firstDFSResult)
		{
			temp=graph2.search(vertex.getKey());
			if (temp.getColor()==Color.WHITE)
			{
				dfs_visit(graph2,temp);
				//4.output the vertices for each tree
				System.out.println();
			}
		}
	}

	private void dfs_visit(Graph graph, Vertex vertex)
	{
		vertex.setColor(Color.GRAY);
		List<Vertex> list = graph.getAdjMap().get(vertex.getKey());
		for (Vertex v : list)// explore edge(u,v)
		{
			if (v.getColor() == Color.WHITE)
			{
				v.setPredecessor(vertex);
				dfs_visit(graph, v);
			}
		}
		vertex.setColor(Color.BLACK);// blacken u;it is finished
		System.out.print(vertex.getKey()+" ");
	}
}
