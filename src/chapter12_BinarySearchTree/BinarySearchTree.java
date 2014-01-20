package chapter12_BinarySearchTree;

public class BinarySearchTree
{
	public static void main(String[] args)
	{
		BSTreeNode node1=new BSTreeNode(null, null, null, 12);
		BSTreeNode node2=new BSTreeNode(null, null, null, 5);
		BSTreeNode node3=new BSTreeNode(null, null, null, 18);
		BSTreeNode node4=new BSTreeNode(null, null, null, 2);
		BSTreeNode node5=new BSTreeNode(null, null, null, 9);
		BSTreeNode node6=new BSTreeNode(null, null, null, 15);
		BSTreeNode node7=new BSTreeNode(null, null, null, 19);
		BSTreeNode node8=new BSTreeNode(null, null, null, 17);
		BinarySearchTree BST=new BinarySearchTree();
		BST.insert(null, node1);
		BST.insert(node1, node2);
		BST.insert(node1, node3);
		BST.insert(node1, node4);
		BST.insert(node1, node5);
		BST.insert(node1, node6);
		BST.insert(node1, node7);
		BST.insert(node1, node8);
		BST.inorder(node1);
		System.out.println("\nmin:"+BST.minimum(node1).getValue()+"\nmax:"+BST.maximum(node1).getValue());
		System.out.println("9的后继："+BST.successor(node5).getValue()+" 12的后继："+BST.successor(node1).getValue());
		System.out.println("9的前驱："+BST.predecessor(node5).getValue()+" 12的前驱："+BST.predecessor(node1).getValue());
		BST.delete(node1, node1);
		BST.inorder(node6);
	}
	
	/**
	 * 中序遍历
	 * @param h
	 */
	public void inorder(BSTreeNode h)
	{
		if (h!=null)
		{
			inorder(h.getLeftNdoe());
			System.out.print(h.getValue()+" ");
			inorder(h.getRightNode());
		}
	}
	
	/**
	 * 查找具有指定关键字的结点
	 * @param p 树根
	 * @param key
	 * @return
	 */
	public BSTreeNode search(BSTreeNode p,int key)
	{
		if (p==null || key==p.getValue())
			return p;
		else if(key<p.getValue())
			return search(p.getLeftNdoe(), key);
		else 
			return search(p.getRightNode(), key);
	}
	
	/**
	 * 采用while代替递归进行查询
	 * @param p
	 * @param key
	 * @return
	 */
	public BSTreeNode iterativeSearch(BSTreeNode p,int key)
	{
		while(p!=null || key!=p.getValue())
		{
			if(key<p.getValue())
				p=p.getLeftNdoe();
			else p=p.getRightNode();
		}
		return p;
	}
	
	public BSTreeNode minimum(BSTreeNode p)
	{
		while(p.getLeftNdoe()!=null)
			p=p.getLeftNdoe();
		return p;
	}
	
	public BSTreeNode maximum(BSTreeNode p)
	{
		while(p.getRightNode()!=null)
			p=p.getRightNode();
		return p;
	}
	
	/**
	 * 后继，没有返回根
	 * @param x
	 * @return
	 */
	public BSTreeNode successor(BSTreeNode x)
	{
		if(x.getRightNode()!=null)
			return minimum(x.getRightNode());
		BSTreeNode parent=x.getParent();
		while(parent!=null&&parent.getRightNode()==x)
		{
			x=parent;
			parent=parent.getParent();
		}
		return parent;
	}

	/**
	 * 前驱，没有返回根
	 * @param x
	 * @return
	 */
	public BSTreeNode predecessor(BSTreeNode x)
	{
		if(x.getLeftNdoe()!=null)
			return maximum(x.getLeftNdoe());
		BSTreeNode parent=x.getParent();
		while (parent!=null&&x==parent.getLeftNdoe())
		{
			x=parent;
			parent=parent.getParent();
		}
		return parent;
	}

	/**
	 * 插入
	 * @param root
	 * @param insertNode
	 */
	public void insert(BSTreeNode root,BSTreeNode insertNode)
	{
		BSTreeNode xNode=root,yNode=null;
		while (xNode!=null)
		{
			yNode=xNode;
			if (insertNode.getValue()<xNode.getValue())
				xNode=xNode.getLeftNdoe();
			else xNode=xNode.getRightNode();
		}
		insertNode.setParent(yNode);
		if (yNode==null)	//空树	
			root=insertNode;
		else if (insertNode.getValue()<yNode.getValue())
			yNode.setLeftNdoe(insertNode);
		else	yNode.setRightNode(insertNode);
	}

	/**
	 * v为根的子树代替u为根的子树,v子树的更新由调用者完成
	 * @param root
	 * @param u
	 * @param v
	 */
	public void transplant(BSTreeNode root,BSTreeNode u,BSTreeNode v)
	{
		if(u.getParent()==null)
			root=v;
		else if(u==u.getParent().getLeftNdoe())
			u.getParent().setLeftNdoe(v);
		else u.getParent().setRightNode(v);
		if(v!=null)	v.setParent(u.getParent());
	}
	
	/**
	 * 删除
	 * @param root
	 * @param deleteNode
	 */
	public void delete(BSTreeNode root,BSTreeNode deleteNode)
	{
		if (deleteNode.getLeftNdoe()==null)//无左孩子
			transplant(root, deleteNode, deleteNode.getRightNode());
		else if (deleteNode.getRightNode()==null) //有左孩子，无有孩子
			transplant(root, deleteNode, deleteNode.getLeftNdoe());
		else {//有左右孩子
			BSTreeNode successor=minimum(deleteNode.getRightNode());//后继
			if (successor.getParent()!=deleteNode)
			{
				transplant(root, successor, successor.getRightNode());
				successor.setRightNode(deleteNode.getRightNode());
				deleteNode.getRightNode().setParent(successor);
			}
			transplant(root, deleteNode, successor);
			successor.setLeftNdoe(deleteNode.getLeftNdoe());
			successor.getLeftNdoe().setParent(successor);	
		}
	}
}
