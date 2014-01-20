package chapter13_RedBlackTree;

public class RedBlackTree
{
	public RBTreeNode getRoot()
	{
		return root;
	}

	public void setRoot(RBTreeNode root)
	{
		this.root = root;
	}

	private RBTreeNode root=RBTreeNode.GUARD_NODE;
	
	public static void main(String[] args)
	{
		RedBlackTree RBTree=new RedBlackTree();
		
		RBTreeNode[] nodes=new RBTreeNode[9];
		for (int i = 0; i < nodes.length; i++)
		{
			nodes[i]=new RBTreeNode(null, null, null, i+1, false);
			RBTree.RBinsert(nodes[i]);
		}
		RBTree.inorder(RBTree.getRoot());
		
		RedBlackTree rbTree=new RedBlackTree();
		RBTreeNode node1=new RBTreeNode(null, null, null, 7, false);
		RBTreeNode node2=new RBTreeNode(null, null, null, 2, false);
		RBTreeNode node3=new RBTreeNode(null, null, null, 11, false);
		RBTreeNode node4=new RBTreeNode(null, null, null, 1, false);
		RBTreeNode node5=new RBTreeNode(null, null, null, 5, false);
		RBTreeNode node6=new RBTreeNode(null, null, null, 8, false);
		RBTreeNode node7=new RBTreeNode(null, null, null, 14, false);
		RBTreeNode node8=new RBTreeNode(null, null, null, 4, false);
		RBTreeNode node9=new RBTreeNode(null, null, null, 15, false);
		rbTree.RBinsert(node1);
		rbTree.RBinsert(node2);
		rbTree.RBinsert(node3);
		rbTree.RBinsert(node4);
		rbTree.RBinsert(node5);
		rbTree.RBinsert(node6);
		rbTree.RBinsert(node7);
		rbTree.RBinsert(node8);
		rbTree.RBinsert(node9);
		rbTree.inorder(rbTree.getRoot());
	}

	public void inorder(RBTreeNode root)
	{
		if (root!=RBTreeNode.GUARD_NODE)
		{
			inorder(root.getLeftNdoe());
			System.out.print(root.getValue()+":");
			if(root.isRed())
				System.out.println("红");
			else
				System.out.println("黑");
			inorder(root.getRightNode());
		}		
	}

	/**
	 * 左旋，假设旋转的结点x.right不为空
	 * 
	 * @param x
	 */
	public void leftRotate(RBTreeNode x)
	{
		RBTreeNode y = x.getRightNode();// set y
		
		x.setRightNode(y.getLeftNdoe());// 将y的左子树变成x的右子树
		if (y.getLeftNdoe() != RBTreeNode.GUARD_NODE)
			y.getLeftNdoe().setParent(x);
		
		y.setParent(x.getParent());// 设置y的父结点
		if (x.getParent() == RBTreeNode.GUARD_NODE)
			//root = y;
			this.setRoot(y);
		else if (x == x.getParent().getLeftNdoe())
			x.getParent().setLeftNdoe(y);
		else
			x.getParent().setRightNode(y);
		
		y.setLeftNdoe(x);// 将x变为y的左子树
		x.setParent(y);
	}
	
	/**
	 * 右旋
	 * 
	 * @param y
	 */
	public void rightRotate(RBTreeNode y)
	{
		RBTreeNode x = y.getLeftNdoe();
		
		y.setLeftNdoe(x.getRightNode());
		if (x.getRightNode() != RBTreeNode.GUARD_NODE)
			x.getRightNode().setParent(y);
		
		x.setParent(y.getParent());
		if (y.getParent() == RBTreeNode.GUARD_NODE)
			//root = x;
			this.setRoot(x);
		else if (y.getParent().getLeftNdoe() == y)
			y.getParent().setLeftNdoe(x);
		else
			y.getParent().setRightNode(x);
		
		x.setRightNode(y);
		y.setParent(x);
	}
	
	/**
	 * 红黑树插入
	 * 
	 * @param z
	 */
	public void RBinsert(RBTreeNode z)
	{
		RBTreeNode y = RBTreeNode.GUARD_NODE;
		RBTreeNode x = this.getRoot();
		while (x != RBTreeNode.GUARD_NODE)
		{
			y = x;
			if (z.getValue() < x.getValue())
				x = x.getLeftNdoe();
			else
				x = x.getRightNode();
		}
		z.setParent(y);
		if (y == RBTreeNode.GUARD_NODE)
			this.setRoot(z);
		else if (z.getValue() < y.getValue())
			y.setLeftNdoe(z);
		else
			y.setRightNode(z);
		z.setLeftNdoe(RBTreeNode.GUARD_NODE);
		z.setRightNode(RBTreeNode.GUARD_NODE);
		z.setRed(true);
		RBinsert_fixup(z);
	}
	
	/**
	 * 红黑树插入修正：观察其父结点和叔结点
	 * 
	 * @param z
	 */
	private void RBinsert_fixup(RBTreeNode z)
	{
		RBTreeNode y = RBTreeNode.GUARD_NODE;
		while (z.getParent().isRed())
		{
			if (z.getParent() == z.getParent().getParent().getLeftNdoe())
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
				y = z.getParent().getParent().getLeftNdoe();
				if (y.isRed())
				{
					z.getParent().setRed(false);
					y.setRed(false);
					z.getParent().getParent().setRed(true);
					z = z.getParent().getParent();
				} else
				{
					if (z == z.getParent().getLeftNdoe())
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
	public void rbDelete(RBTreeNode z)
	{
		RBTreeNode x=RBTreeNode.GUARD_NODE;
		RBTreeNode y=z;
		boolean yOriginalColor=y.isRed();
		if (z.getLeftNdoe()==RBTreeNode.GUARD_NODE)
		{//无左孩子
			x=z.getRightNode();
			rbTransplant(z, z.getRightNode());
		}
		else if (z.getRightNode()==RBTreeNode.GUARD_NODE)
		{//有左孩子，无右孩子
			x=z.getLeftNdoe();
			rbTransplant(z, z.getLeftNdoe());
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
			rbTransplant(z, y);
			y.setLeftNdoe(z.getLeftNdoe());
			y.getLeftNdoe().setParent(y);
			y.setRed(z.isRed());
		}
		if(!yOriginalColor)
			rbDeleteFixup(x);
	}
	
	/**
	 * 红黑树删除修正
	 * x始终指向具有双重黑色的非根节点，w始终指向x的兄弟
	 * @param x
	 */
	private void rbDeleteFixup(RBTreeNode x)
	{
		RBTreeNode w=RBTreeNode.GUARD_NODE;
		while (x!=this.getRoot()&& !x.isRed())
		{
			if (x==x.getParent().getLeftNdoe())
			{
				w=x.getParent().getRightNode();
				if (w.isRed())//case1：兄弟w为红-->case2、3、4
				{
					w.setRed(false);
					w.getParent().setRed(true);
					leftRotate(x.getParent());
					w=x.getParent().getRightNode();
				}
				if (!w.getLeftNdoe().isRed()&&!w.getRightNode().isRed())//case2：兄弟w两孩子为黑，子树正常，继续循环
				{
					w.setRed(true);
					x=x.getParent();
				}else 
				{
					if (!w.getRightNode().isRed()) //case3：兄弟w左孩子为红，右孩子为黑-->case4
					{
						w.getLeftNdoe().setRed(false);
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
				w=x.getParent().getLeftNdoe();
				if (w.isRed())
				{
					w.setRed(false);
					w.getParent().setRed(true);
					rightRotate(x.getParent());
					w=x.getParent().getLeftNdoe();
				}
				if (!w.getLeftNdoe().isRed()&&!w.getRightNode().isRed())
				{
					w.setRed(true);
					x=x.getParent();
				}else {
					if (!w.getLeftNdoe().isRed())
					{
						w.getRightNode().setRed(false);
						w.setRed(true);
						leftRotate(w);
						w=x.getParent().getLeftNdoe();
					}
					w.setRed(x.getParent().isRed());
					w.getParent().setRed(false);
					w.getLeftNdoe().setRed(false);
					rightRotate(x.getParent());
					x=this.getRoot();
				}
			}
		}
		x.setRed(false);
	}

	private void rbTransplant(RBTreeNode u,RBTreeNode v)
	{
		if(u.getParent()==RBTreeNode.GUARD_NODE)
			this.root=v;
		else if(u==u.getParent().getLeftNdoe())
			u.getParent().setLeftNdoe(v);
		else 
			u.getParent().setRightNode(v);
		v.setParent(u.getParent());
	}
	public RBTreeNode minimum(RBTreeNode p)
	{
		while(p.getLeftNdoe()!=RBTreeNode.GUARD_NODE)
			p=p.getLeftNdoe();
		return p;
	}
}
