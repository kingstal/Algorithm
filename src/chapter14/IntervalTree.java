package chapter14;

/**
 * 区间树：结点包含一个区间对象，插入时以区间左端点为关键值
 * 		 ：结点还包含最大值max，为以此为根的子树中所有区间端点的最大值
 * 维护max有问题
 * @author Arthur
 *
 */
public class IntervalTree
{
	private IntervalTreeNode root=IntervalTreeNode.GUARD_NODE;
	public IntervalTreeNode getRoot()
	{
		return root;
	}
	public void setRoot(IntervalTreeNode root)
	{
		this.root = root;
	}
	
	/**
	 * 左旋，假设旋转的结点x.right不为空
	 * 
	 * @param x
	 */
	public void leftRotate(IntervalTreeNode x)
	{
		IntervalTreeNode y = x.getRightNode();// set y
		
		x.setRightNode(y.getLeftNode());// 将y的左子树变成x的右子树
		if (y.getLeftNode() != IntervalTreeNode.GUARD_NODE)
			y.getLeftNode().setParent(x);
		
		y.setParent(x.getParent());// 设置y的父结点
		if (x.getParent() == IntervalTreeNode.GUARD_NODE)
			//root = y;
			this.setRoot(y);
		else if (x == x.getParent().getLeftNode())
			x.getParent().setLeftNode(y);
		else
			x.getParent().setRightNode(y);
		
		y.setLeftNode(x);// 将x变为y的左子树
		x.setParent(y);
		
		//维护size属性
		/*y.setSize(x.getSize());
		x.setSize(x.getLeftNode().getSize()+x.getRightNode().getSize()+1);*/
	}
	
	/**
	 * 右旋
	 * 
	 * @param y
	 */
	public void rightRotate(IntervalTreeNode y)
	{
		IntervalTreeNode x = y.getLeftNode();
		
		y.setLeftNode(x.getRightNode());
		if (x.getRightNode() != IntervalTreeNode.GUARD_NODE)
			x.getRightNode().setParent(y);
		
		x.setParent(y.getParent());
		if (y.getParent() == IntervalTreeNode.GUARD_NODE)
			//root = x;
			this.setRoot(x);
		else if (y.getParent().getLeftNode() == y)
			y.getParent().setLeftNode(x);
		else
			y.getParent().setRightNode(x);
		
		x.setRightNode(y);
		y.setParent(x);
		
		//维护size属性
		/*x.setSize(y.getSize());
		y.setSize(y.getLeftNode().getSize()+y.getRightNode().getSize()+1);*/
	}
	
	/**
	 * 红黑树插入
	 * 
	 * @param z
	 */
	public void RBinsert(IntervalTreeNode z)
	{
		IntervalTreeNode y = IntervalTreeNode.GUARD_NODE;
		IntervalTreeNode x = this.getRoot();
		while (x != IntervalTreeNode.GUARD_NODE)
		{
			x.setMax(calMax(x.getMax(),z.getMax()));//维护max属性，每经过一个结点，比较并设置max
			
			y = x;
			if (z.getInterval().getLow() < x.getInterval().getLow())
				x = x.getLeftNode();
			else
				x = x.getRightNode();
		}
		z.setParent(y);
		if (y == IntervalTreeNode.GUARD_NODE)
			this.setRoot(z);
		else if (z.getInterval().getLow() < y.getInterval().getLow())
			y.setLeftNode(z);
		else
			y.setRightNode(z);
		z.setLeftNode(IntervalTreeNode.GUARD_NODE);
		z.setRightNode(IntervalTreeNode.GUARD_NODE);
		z.setRed(true);
		RBinsert_fixup(z);
	}
	
	private int calMax(int max, int max2)
	{
		return max<max2?max2:max;
	}
	
	/**
	 * 红黑树插入修正：观察其父结点和叔结点
	 * 
	 * @param z
	 */
	private void RBinsert_fixup(IntervalTreeNode z)
	{
		IntervalTreeNode y = IntervalTreeNode.GUARD_NODE;
		while (z.getParent().isRed())
		{
			if (z.getParent() == z.getParent().getParent().getLeftNode())
			{
				y = z.getParent().getParent().getRightNode();
				if (y.isRed())// case1：叔节点为红
				{
					z.getParent().setRed(false);
					y.setRed(false);
					z.getParent().getParent().setRed(true);
					z = z.getParent().getParent();
				} else
				{
					if (z == z.getParent().getRightNode()) // case2：叔节点为黑，z为右孩子
					{ // ：左旋转换成case3
						z = z.getParent();
						leftRotate(z);
					}// case3：叔节点为黑，z为左孩子
					z.getParent().setRed(false);
					z.getParent().getParent().setRed(true);
					rightRotate(z.getParent().getParent());
				}
			} else
			{
				y = z.getParent().getParent().getLeftNode();
				if (y.isRed())
				{
					z.getParent().setRed(false);
					y.setRed(false);
					z.getParent().getParent().setRed(true);
					z = z.getParent().getParent();
				} else
				{
					if (z == z.getParent().getLeftNode())
					{ 
						z = z.getParent();
						rightRotate(z);
					}
					z.getParent().setRed(false);
					z.getParent().getParent().setRed(true);
					leftRotate(z.getParent().getParent());
				}
			}
		}
		this.getRoot().setRed(false);
	}

	/**
	 * 红黑树删除
	 * @param z
	 */
	public void rbDelete(IntervalTreeNode z)
	{
		IntervalTreeNode x=IntervalTreeNode.GUARD_NODE;
		IntervalTreeNode y=z;
		boolean yOriginalColor=y.isRed();
		if (z.getLeftNode()==IntervalTreeNode.GUARD_NODE)
		{//无左孩子
			deleteMaintaince(z);
			
			x=z.getRightNode();
			rbTransplant(z, z.getRightNode());
		}
		else if (z.getRightNode()==IntervalTreeNode.GUARD_NODE)
		{//有左孩子，无右孩子
			deleteMaintaince(z);
			
			x=z.getLeftNode();
			rbTransplant(z, z.getLeftNode());
		}else 
		{//左右孩子
			y=minimum(z.getRightNode());
			yOriginalColor=y.isRed();
			x=y.getRightNode();
			if(y.getParent()==z)
				x.setParent(y);
			else {
				rbTransplant(y, y.getRightNode());
				y.setRightNode(z.getRightNode());
				y.getRightNode().setParent(y);
			}
			
			deleteMaintaince(y);
			
			rbTransplant(z, y);
			y.setLeftNode(z.getLeftNode());
			y.getLeftNode().setParent(y);
			y.setRed(z.isRed());
			y.setMax(z.getMax());
		}
		if(!yOriginalColor)
			rbDeleteFixup(x);
	}
	
	/**
	 * 删除结点时维护max属性
	 * @param z
	 */
	private void deleteMaintaince(IntervalTreeNode z)
	{
		IntervalTreeNode q=z;
		while(q!=this.getRoot())
		{
			q=q.getParent();
			if(q.getMax()==z.getMax())
			{
				//if(z==q.getLeftNode())
					//q.setMax(calMax(calMax(q.getInterval().getHigh(), q.getRightNode().getMax()), z.ge));
			}
		}
	}
	
	/**
	 * 红黑树删除修正
	 * x始终指向具有双重黑色的非根节点，w始终指向x的兄弟
	 * @param x
	 */
	private void rbDeleteFixup(IntervalTreeNode x)
	{
		IntervalTreeNode w=IntervalTreeNode.GUARD_NODE;
		while (x!=this.getRoot()&& !x.isRed())
		{
			if (x==x.getParent().getLeftNode())
			{
				w=x.getParent().getRightNode();
				if (w.isRed())//case1：兄弟w为红-->case2、3、4
				{
					w.setRed(false);
					w.getParent().setRed(true);
					leftRotate(x.getParent());
					w=x.getParent().getRightNode();
				}
				if (!w.getLeftNode().isRed()&&!w.getRightNode().isRed())//case2：兄弟w两孩子为黑，子树正常，继续循环
				{
					w.setRed(true);
					x=x.getParent();
				}else 
				{
					if (!w.getRightNode().isRed()) //case3：兄弟w左孩子为红，右孩子为黑-->case4
					{
						w.getLeftNode().setRed(false);
						w.setRed(true);
						rightRotate(w);
						w=x.getParent().getRightNode();
					}
					//case4：兄弟右孩子为红
					w.setRed(x.getParent().isRed());
					x.getParent().setRed(false);
					w.getRightNode().setRed(false);
					leftRotate(x.getParent());
					x=this.getRoot();
				}
					
			}else {
				w=x.getParent().getLeftNode();
				if (w.isRed())
				{
					w.setRed(false);
					w.getParent().setRed(true);
					rightRotate(x.getParent());
					w=x.getParent().getLeftNode();
				}
				if (!w.getLeftNode().isRed()&&!w.getRightNode().isRed())
				{
					w.setRed(true);
					x=x.getParent();
				}else {
					if (!w.getLeftNode().isRed())
					{
						w.getRightNode().setRed(false);
						w.setRed(true);
						leftRotate(w);
						w=x.getParent().getLeftNode();
					}
					w.setRed(x.getParent().isRed());
					w.getParent().setRed(false);
					w.getLeftNode().setRed(false);
					rightRotate(x.getParent());
					x=this.getRoot();
				}
			}
		}
		x.setRed(false);
	}

	private void rbTransplant(IntervalTreeNode u,IntervalTreeNode v)
	{
		if(u.getParent()==IntervalTreeNode.GUARD_NODE)
			this.root=v;
		else if(u==u.getParent().getLeftNode())
			u.getParent().setLeftNode(v);
		else 
			u.getParent().setRightNode(v);
		v.setParent(u.getParent());
	}
	
	public IntervalTreeNode minimum(IntervalTreeNode p)
	{
		while(p.getLeftNode()!=IntervalTreeNode.GUARD_NODE)
			p=p.getLeftNode();
		return p;
	}
}
