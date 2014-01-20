package chapter14;

/**
 * 区间树节点
 * @author Arthur
 *
 */
public class IntervalTreeNode
{
	public int getMax()
	{
		return max;
	}
	public void setMax(int max)
	{
		this.max = max;
	}

	public static final IntervalTreeNode GUARD_NODE=new IntervalTreeNode(null, null, null, false, null, 0);
	
	public IntervalTreeNode getLeftNode()
	{
		return leftNode;
	}
	public void setLeftNode(IntervalTreeNode leftNode)
	{
		this.leftNode = leftNode;
	}
	public IntervalTreeNode getRightNode()
	{
		return rightNode;
	}
	public void setRightNode(IntervalTreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
	public IntervalTreeNode getParent()
	{
		return parent;
	}
	public void setParent(IntervalTreeNode parent)
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
	public Interval getInterval()
	{
		return interval;
	}
	public void setInterval(Interval interval)
	{
		this.interval = interval;
	}
	
	private IntervalTreeNode leftNode;
	private IntervalTreeNode rightNode;
	private IntervalTreeNode parent;
	private boolean isRed;
	private Interval interval;
	private int max;
	
	public IntervalTreeNode(IntervalTreeNode leftNode, IntervalTreeNode rightNode, IntervalTreeNode parent, boolean isRed, Interval interval, int max)
	{
		super();
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.parent = parent;
		this.isRed = isRed;
		this.interval = interval;
		this.max = max;
	}
}
