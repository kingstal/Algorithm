package chapter12_BinarySearchTree;

public class BSTreeNode
{
	public BSTreeNode(BSTreeNode leftNdoe, BSTreeNode rightNode, BSTreeNode parent, int value)
	{
		super();
		this.leftNdoe = leftNdoe;
		this.rightNode = rightNode;
		this.parent = parent;
		this.value = value;
	}
	public BSTreeNode getParent()
	{
		return parent;
	}
	public void setParent(BSTreeNode parent)
	{
		this.parent = parent;
	}
	public BSTreeNode getLeftNdoe()
	{
		return leftNdoe;
	}
	public void setLeftNdoe(BSTreeNode leftNdoe)
	{
		this.leftNdoe = leftNdoe;
	}
	public BSTreeNode getRightNode()
	{
		return rightNode;
	}
	public void setRightNode(BSTreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}
	private BSTreeNode leftNdoe;
	private BSTreeNode rightNode;
	private BSTreeNode parent;
	private int value;
	
}
