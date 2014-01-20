package chapter19_FibonacciHeap;

import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap
{
	private static final double oneOverLogPhi = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
	private FibonacciHeapNode minNode;// 堆最小结点
	private int nodeNum;// 堆结点数目

	/**
	 * 创建新的斐波那契堆
	 */
	public FibonacciHeap()
	{
	}

	/**
	 * 寻找最小结点
	 * 
	 * @return
	 */
	public FibonacciHeapNode getMinNode()
	{
		return minNode;
	}

	/**
	 * 插入一个结点：假设结点已被初始化（key被赋值），将结点加到根列表中，让结点成为一棵单节点最小堆有序树
	 * 
	 * @param x
	 */
	public void fibHeapInsert(FibonacciHeapNode x)
	{
		if (minNode != null)
		{
			x.setLeft(minNode);
			x.setRight(minNode.getRight());
			minNode.setRight(x);
			x.getRight().setLeft(x);
			if (x.getKey() < minNode.getKey())
				minNode = x;
		} else
		{
			minNode = x;
		}
		nodeNum++;
	}

	/**
	 * 两个斐波那契堆合并
	 * 
	 * @param h1
	 * @param h2
	 * @return
	 */
	public static FibonacciHeap fibHeapUnion(FibonacciHeap h1, FibonacciHeap h2)
	{
		FibonacciHeap heap = new FibonacciHeap();
		if ((h1 != null) && (h2 != null))
		{
			heap.minNode = h1.minNode;
			if (heap.minNode != null)
			{
				if (h2.minNode != null)
				{
					heap.minNode.getRight().setLeft(h2.minNode.getLeft());
					h2.minNode.getLeft().setRight(heap.minNode.getRight());
					heap.minNode.setRight(h2.minNode);
					h2.minNode.setLeft(heap.minNode);
				}
				if (h2.minNode.getKey() < heap.minNode.getKey())
					heap.minNode = h2.minNode;
			} else
			{
				heap.minNode = h2.minNode;
			}
			heap.nodeNum = h1.nodeNum + h2.nodeNum;
		}
		return heap;
	}

	/**
	 * 抽取最小结点，将其子节点加入到根链表中，后对根链表进行合并
	 * 
	 * @return
	 */
	public FibonacciHeapNode fibHeapExtractMin()
	{
		FibonacciHeapNode z = this.minNode;
		if (z != null)
		{
			int numKids = z.getDegree();
			FibonacciHeapNode x = z.getChild();
			FibonacciHeapNode tempRight;
			while (numKids > 0)
			{
				tempRight = x.getRight();
				// remove x from child list
				x.getLeft().setRight(x.getRight());
				x.getRight().setLeft(x.getLeft());

				// add x to root list of heap
				x.setLeft(minNode);
				x.setRight(minNode.getRight());
				minNode.setRight(x);
				x.getRight().setLeft(x);

				// set parent[x] to null
				x.setParent(null);
				x = tempRight;
				numKids--;
			}
			// remove z from root list of heap
			z.getLeft().setRight(z.getRight());
			z.getRight().setLeft(z.getLeft());

			if (z == z.getRight())
			{
				minNode = null;
			} else
			{
				minNode = z.getRight();
				consolidate();
			}

			// decrement size of heap
			nodeNum--;
		}
		return z;
	}

	/**
	 * 合并H的根链表，使根链表中每个根有不同的度数 使用一个数组记录根节点对应度数的轨迹，即A[i]=y-->y.degree=i
	 * 遍历根链表的结点，当度数相同时进行连接
	 */
	private void consolidate()
	{
		// 计算最大度数上界
		int arraySize = ((int) Math.floor(Math.log(nodeNum) * oneOverLogPhi)) + 1;
		List<FibonacciHeapNode> array = new ArrayList<FibonacciHeapNode>(arraySize);

		// Initialize degree array
		for (int i = 0; i < arraySize; i++)
			array.add(null);

		// Find the number of root nodes.
		int numRoots = 0;
		FibonacciHeapNode x = minNode;
		if (x != null)
		{
			numRoots++;
			x = x.getRight();
			while (x != minNode)
			{
				numRoots++;
				x = x.getRight();
			}
		}

		// For each node in root list do...
		while (numRoots > 0)
		{
			// Access this node's degree..
			int d = x.getDegree();
			FibonacciHeapNode next = x.getRight();

			// ..and see if there's another of the same degree.
			for (;;)
			{
				FibonacciHeapNode y = array.get(d);
				if (y == null)
				{
					// Nope.
					break;
				}

				// There is, make one of the nodes a child of the other.
				// Do this based on the key value.
				if (x.getKey() > y.getKey())
				{
					FibonacciHeapNode temp = y;
					y = x;
					x = temp;
				}

				// FibonacciHeapNode y disappears from root list.
				link(y, x);

				// We've handled this degree, go to next one.
				array.set(d, null);
				d++;
			}

			// Save this node for later when we might encounter another of the
			// same degree.
			array.set(d, x);

			// Move forward through list.
			x = next;
			numRoots--;
		}

		// Set min to null (effectively losing the root list) and
		// reconstruct the root list from the array entries in array[].
		minNode = null;
		for (int i = 0; i < arraySize; i++)
		{
			FibonacciHeapNode y = array.get(i);
			if (y == null)
			{
				continue;
			}

			// We've got a live one, add it to root list.
			if (minNode != null)
			{
				// First remove node from root list.
				y.getLeft().setRight(y.getRight());
				y.getRight().setLeft(y.getLeft());

				// Now add to root list, again.
				y.setLeft(minNode);
				y.setRight(minNode.getRight());
				minNode.setRight(y);
				y.getRight().setLeft(y);

				// Check if this is a new min.
				if (y.getKey() < minNode.getKey())
				{
					minNode = y;
				}
			} else
			{
				minNode = y;
			}
		}
	}

	/**
	 * 两节点的连接操作
	 * 
	 * @param y
	 * @param x
	 */
	private void link(FibonacciHeapNode y, FibonacciHeapNode x)
	{
		// remove y from root list of heap
		y.getLeft().setRight(y.getRight());
		y.getRight().setLeft(y.getLeft());

		// make y a child of x
		y.setParent(x);
		if (x.getChild() == null)
		{
			x.setChild(y);
			y.setRight(y);
			y.setLeft(y);
		} else
		{
			y.setLeft(x.getChild());
			y.setRight(x.getChild().getRight());
			x.getChild().setRight(y);
			y.getRight().setLeft(y);
		}
		// increase degree[x]
		x.setDegree(x.getDegree() + 1);

		y.setMark(false);
	}

	public void fibHeapDecreaseKey(FibonacciHeapNode x, int k)
	{
		if (k > x.getKey())
		{
			System.out.println("new key is greater than current key");
			return;
		}
		x.setKey(k);
		FibonacciHeapNode y = x.getParent();
		if (y != null && x.getKey() < y.getKey())
		{
			cut(x, y);
			cascadingCut(y);
		}
		if (x.getKey() < minNode.getKey())
			minNode = x;
	}

	/**
	 * 切断子节点x和父结点y的连接，将x加入根链表
	 * 
	 * @param x
	 * @param y
	 */
	private void cut(FibonacciHeapNode x, FibonacciHeapNode y)
	{
		// remove x from childlist of y and decrement degree[y]
		x.getLeft().setRight(x.getRight());
		x.getRight().setLeft(x.getLeft());
		y.setDegree(y.getDegree() - 1);

		// reset y.child if necessary
		if (y.getChild() == x)
			y.setChild(x.getRight());

		if (y.getDegree() == 0)
			y.setChild(null);

		// add x to root list of heap
		x.setLeft(minNode);
		x.setRight(minNode.getRight());
		minNode.setRight(x);
		x.getRight().setLeft(x);

		// set parent[x] to nil
		x.setParent(null);

		// set mark[x] to false
		x.setMark(false);
	}

	/**
	 * 级联切断操作：父结点y第一次失去子节点x时，将标记置为true，第二次失去孩子，调用cut(y,z)切断自己与其父结点z的连接，
	 * 迭代调用cascadingCut
	 * 
	 * @param y
	 */
	private void cascadingCut(FibonacciHeapNode y)
	{
		FibonacciHeapNode z = y.getParent();
		// if there's a parent...
		if (z != null)
		{
			// if y is unmarked, set it marked
			if (!y.isMark())
			{
				y.setMark(true);
			} else
			{
				// it's marked, cut it from parent
				cut(y, z);
				// cut its parent as well
				cascadingCut(z);
			}
		}
	}

	public void fibHeapDelete(FibonacciHeapNode x)
	{
		fibHeapDecreaseKey(x, Integer.MIN_VALUE);
		fibHeapExtractMin();
	}

	// 堆内搜索关键字
	public FibonacciHeapNode fibHeapSearch(int key)
	{
		return fibonacciHeapNodeSearch(minNode, key);
	}

	// 被FibHeapSearch调用
	public FibonacciHeapNode fibonacciHeapNodeSearch(FibonacciHeapNode x, int key)
	{
		FibonacciHeapNode w = x, y = null;
		if (x != null)
		{
			do
			{
				if (w.getKey() == key)
				{
					y = w;
					break;
				} else if (null != (y = fibonacciHeapNodeSearch(w.getChild(), key)))
				{
					break;
				}
				w = w.getRight();
			} while (w != x);
		}
		return y;
	}

	// 输出打印堆
	public void fibHeapPrint()
	{
		System.out.printf("The keyNum = %d\n", nodeNum);
		fibonacciHeapNodePrint(minNode);
		System.out.println();
	};

	// 被FibHeapPrint调用
	private void fibonacciHeapNodePrint(FibonacciHeapNode x)
	{
		FibonacciHeapNode p = null;
		if (null == x)
			return;
		p = x;
		do
		{
			System.out.print(" (");
			System.out.print(p.getKey());
			if (p.getChild() != null)
			{
				fibonacciHeapNodePrint(p.getChild());
			}
			System.out.print(") ");
			p = p.getRight();
		} while (x != p);
	}

	/**
	 * Tests if the Fibonacci heap is empty or not. Running time: O(1) actual
	 * 
	 * @return true if the heap is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return minNode == null;
	}

	/**
	 * Removes all elements from this heap.
	 */
	public void clear()
	{
		minNode = null;
		nodeNum = 0;
	}
}
