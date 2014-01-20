package chapter16_GreedyAlgorithm;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree
{
	private PriorityQueue<Node> nodeQueue;// 优先队列，用于构造Huffman树
	private Map<String, String> codeMap;

	public HuffmanTree()
	{
		super();
		codeMap = new HashMap<>();
	}

	/**
	 * 用于生成Huffman数
	 * 
	 * @param collection
	 *            节点集合
	 * @return 根节点
	 */
	public Node huffman(Collection<Node> collection)
	{
		int n = collection.size();
		nodeQueue=new PriorityQueue<>(n, weightComparator);
		for (Node node : collection)
			nodeQueue.add(node);
		//nodeQueue.addAll(collection,weightComparator);
		Node xNode = null;
		Node yNode = null;
		for (int i = 1; i <= n - 1; i++)
		{
			xNode = nodeQueue.poll();
			yNode = nodeQueue.poll();
			Node zNode = new Node();
			zNode.setLeftNode(xNode);
			zNode.setRightNode(yNode);
			zNode.setWeight(xNode.getWeight() + yNode.getWeight());
			nodeQueue.add(zNode);
		}
		return nodeQueue.poll();
	}

	public Map<String, String> coding(Node root)
	{
		process(root, "");
		return codeMap;
	}
	/**
	 * 给节点分配变长编码
	 * 
	 * @param node
	 *            每个节点
	 * @param content
	 *            每个节点新增的编码
	 */
	private void process(Node node, String content)
	{
		// 叶子结点
		if (node.getLeftNode() == null)
		{
			codeMap.put(node.getData(), content);
			return;
		}
		// 对左子树分配代码"0"
		process(node.getLeftNode(), content + "0");
		// 对右子树分配代码"1"
		process(node.getRightNode(), content + "1");
	}
	
	public static Comparator<Node> weightComparator=new Comparator<Node>()
	{

		@Override
		public int compare(Node o1, Node o2)
		{
			return (int) (o1.getWeight()-o2.getWeight());
		}
	};
}

class Node
{
	private String data;
	private double weight;
	private Node leftNode;
	private Node rightNode;

	public Node()
	{
		super();
	}

	public Node(String dataString, double weight)
	{
		super();
		this.data = dataString;
		this.weight = weight;
	}

	@Override
	public String toString()
	{
		return "Node [data=" + data + ", weight=" + weight + "]";
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}

	public Node getLeftNode()
	{
		return leftNode;
	}

	public void setLeftNode(Node leftNode)
	{
		this.leftNode = leftNode;
	}

	public Node getRightNode()
	{
		return rightNode;
	}

	public void setRightNode(Node rightNode)
	{
		this.rightNode = rightNode;
	}
}