package chapter14;

public class OrderStatisticTreeNode
{
	public static final OrderStatisticTreeNode GUARD_NODE=new OrderStatisticTreeNode(null, null, null, false, 0, 0);
	
	public OrderStatisticTreeNode getLeftNode()
	{
		return leftNode;
	}
	public void setLeftNode(OrderStatisticTreeNode leftNode)
	{
		this.leftNode = leftNode;
	}
	public OrderStatisticTreeNode getRightNode()
	{
		return rightNode;
	}
	public void setRightNode(OrderStatisticTreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
	public OrderStatisticTreeNode getParent()
	{
		return parent;
	}
	public void setParent(OrderStatisticTreeNode parent)
	{
		this.parent = parent;
	}
	public boolean isRed()
	{
		return isRed;
	}
	public void setRed(boolean isRed)
	{
		this.isRed = isRed;
	}
	public int getKey()
	{
		return key;
	}
	public void setKey(int key)
	{
		this.key = key;
	}
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public OrderStatisticTreeNode(OrderStatisticTreeNode leftNode, OrderStatisticTreeNode rightNode, OrderStatisticTreeNode parent, boolean isRed, int key, int size)
	{
		super();
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.parent = parent;
		this.isRed = isRed;
		this.key = key;
		this.size = size;
	}

	private OrderStatisticTreeNode leftNode;
	private OrderStatisticTreeNode rightNode;
	private OrderStatisticTreeNode parent;
	private boolean isRed;
	private int key;
	private int size;//子树大小
}
