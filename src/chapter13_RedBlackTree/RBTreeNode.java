package chapter13_RedBlackTree;

public class RBTreeNode
{
	// 哨兵
	public static final RBTreeNode GUARD_NODE = new RBTreeNode(null, null, null, 0, false);
	
	public RBTreeNode(RBTreeNode leftNdoe, RBTreeNode rightNode, RBTreeNode parent, int value, boolean color)
	{
		super();
		this.leftNdoe = leftNdoe;
		this.rightNode = rightNode;
		this.parent = parent;
		this.value = value;
		this.red = color;
	}
	
	public RBTreeNode getLeftNdoe()
	{
		return leftNdoe;
	}
	
	public void setLeftNdoe(RBTreeNode leftNdoe)
	{
		this.leftNdoe = leftNdoe;
	}
	
	public RBTreeNode getRightNode()
	{
		return rightNode;
	}
	
	public void setRightNode(RBTreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
	
	public RBTreeNode getParent()
	{
		return parent;
	}
	
	public void setParent(RBTreeNode parent)
	{
		this.parent = parent;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public boolean isRed()
	{
		return red;
	}

	public void setRed(boolean red)
	{
		this.red = red;
	}

	private RBTreeNode leftNdoe;
	private RBTreeNode rightNode;
	private RBTreeNode parent;
	private int value;
	private boolean red;// true为红，false为黑
}
