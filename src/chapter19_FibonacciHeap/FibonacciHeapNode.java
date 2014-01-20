package chapter19_FibonacciHeap;

public class FibonacciHeapNode
{
	private FibonacciHeapNode child;// 子节点
	private FibonacciHeapNode left;// 左兄弟
	private FibonacciHeapNode right;// 右兄弟
	private FibonacciHeapNode parent;// 父结点
	private boolean mark;// 成为孩子结点后其孩子是否被删除过
	private int degree;// 子节点数目
	private int key;// 结点关键字

	//结点构造函数：degree=0 parent=null child=null mark=false
	public FibonacciHeapNode(int key)
	{
		super();
		this.key = key;
		this.right = this;
		this.left = this;
	}
	
	public FibonacciHeapNode getChild()
	{
		return child;
	}


	public void setChild(FibonacciHeapNode child)
	{
		this.child = child;
	}


	public FibonacciHeapNode getLeft()
	{
		return left;
	}


	public void setLeft(FibonacciHeapNode left)
	{
		this.left = left;
	}


	public FibonacciHeapNode getRight()
	{
		return right;
	}


	public void setRight(FibonacciHeapNode right)
	{
		this.right = right;
	}


	public FibonacciHeapNode getParent()
	{
		return parent;
	}


	public void setParent(FibonacciHeapNode parent)
	{
		this.parent = parent;
	}


	public boolean isMark()
	{
		return mark;
	}


	public void setMark(boolean mark)
	{
		this.mark = mark;
	}


	public int getDegree()
	{
		return degree;
	}


	public void setDegree(int degree)
	{
		this.degree = degree;
	}


	public int getKey()
	{
		return key;
	}


	public void setKey(int key)
	{
		this.key = key;
	}

	public String toString()
	{
		if (mark)
		{
			return Double.toString(key);
		} else
		{
			StringBuffer buf = new StringBuffer();
			buf.append("Node=[parent = ");

			if (parent != null)
			{
				buf.append(Double.toString(parent.key));
			} else
			{
				buf.append("---");
			}

			buf.append(", key = ");
			buf.append(Double.toString(key));
			buf.append(", degree = ");
			buf.append(Integer.toString(degree));
			buf.append(", right = ");

			if (right != null)
			{
				buf.append(Double.toString(right.key));
			} else
			{
				buf.append("---");
			}

			buf.append(", left = ");

			if (left != null)
			{
				buf.append(Double.toString(left.key));
			} else
			{
				buf.append("---");
			}

			buf.append(", child = ");

			if (child != null)
			{
				buf.append(Double.toString(child.key));
			} else
			{
				buf.append("---");
			}

			buf.append(']');

			return buf.toString();
		}
	}
}
