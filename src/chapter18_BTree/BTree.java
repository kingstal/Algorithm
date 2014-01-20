package chapter18_BTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * B树： 1、每个结点有n个关键字，n+1个指向孩子的指针
 * 2、每个结点关键字非降序排列，其孩子结点关键字满足k[1]≤x.key[1]≤k[2]≤x.key[2]... 3、叶节点有相同深度
 * 4、除根节点，每个结点关键字≥t-1，≤2t-1【t≥2】 约定： 1、根始终在主存中，无需disk-read，改变后需进行disk-write；
 * 任何被当做参数的结点传递前需先进行disk-read
 * 
 * @author Arthur
 * 
 */
public class BTree<K, V>
{
	private static final int DEFAULT_T = 2;
	private BTNode<K, V> root = null;// 根节点
	private int t = DEFAULT_T;// 非根节点的关键字数n满足(t - 1) <= n <= (2t - 1)
	private int minKeySize = t - 1;
	private int maxKeySize = 2 * t - 1;
	private Comparator<K> kComparator;

	public BTree()
	{
		root = new BTNode<K, V>();
		root.setLeaf(true);
	}

	public BTree(int t)
	{
		this();
		this.t = t;
		minKeySize = t - 1;
		maxKeySize = 2 * t - 1;
	}

	public BTree(Comparator<K> kComparator)
	{
		root = new BTNode<>(kComparator);
		root.setLeaf(true);
		this.kComparator = kComparator;
	}

	public BTree(Comparator<K> kComparator, int t)
	{
		this(kComparator);
		this.t = t;
		minKeySize = t - 1;
		maxKeySize = 2 * t - 1;
	}

	@SuppressWarnings("unchecked")
	int compare(K key1, K key2)
	{
		return kComparator == null ? ((Comparable<K>) key1).compareTo(key2) : kComparator.compare(key1, key2);
	}

	/**
	 * 搜索给定的键。
	 * 
	 * @param key
	 *            - 给定的键值
	 * @return 键关联的值，如果存在，否则null
	 */
	public V search(K key)
	{
		return search(root, key);
	}

	/**
	 * 在以给定节点为根的子树中，递归搜索给定的<code>key</code>
	 * 
	 * @param node
	 *            - 子树的根节点
	 * @param key
	 *            - 给定的键值
	 * @return 如果存在,返回键关联的值，，否则null
	 */
	private V search(BTNode<K, V> node, K key)
	{
		SearchResult<V> result = node.searchKey(key);
		if (result.isExist())
			return result.getValue();
		else
		{
			if (node.isLeaf())
				return null;
			else
				search(node.childAt(result.getIndex()), key);
		}
		return null;
	}

	/**
	 * 分裂一个满子节点<code>childNode</code>,保证给定的子节点是满节点。
	 * 
	 * @param parentNode
	 *            - 父节点
	 * @param childNode
	 *            - 满子节点
	 * @param index
	 *            - 满子节点在父节点中的索引
	 */
	private void splitNode(BTNode<K, V> parentNode, int index)
	{
		BTNode<K, V> childNode = parentNode.childAt(index);
		assert childNode.size() == maxKeySize;

		BTNode<K, V> siblingNode = new BTNode<K, V>(kComparator);
		siblingNode.setLeaf(childNode.isLeaf());
		// 将满子节点中索引为[t, 2t - 2]的(t - 1)个项插入新的节点中
		for (int i = 0; i < minKeySize; ++i)
			siblingNode.addEntry(childNode.entryAt(t + i));
		// 提取满子节点中的中间项，其索引为(t - 1)
		Entry<K, V> entry = childNode.entryAt(t - 1);
		// 删除满子节点中索引为[t - 1, 2t - 2]的t个项
		for (int i = maxKeySize - 1; i >= t - 1; --i)
			childNode.removeEntry(i);
		if (!childNode.isLeaf()) // 如果满子节点不是叶节点，则还需要处理其子节点
		{
			// 将满子节点中索引为[t, 2t - 1]的t个子节点插入新的节点中
			for (int i = 0; i < minKeySize + 1; ++i)
				siblingNode.addChild(childNode.childAt(t + i));
			// 删除满子节点中索引为[t, 2t - 1]的t个子节点
			for (int i = maxKeySize; i >= t; --i)
				childNode.removeChild(i);
		}
		// 将entry插入父节点
		parentNode.insertEntry(entry, index);
		// 将新节点插入父节点
		parentNode.insertChild(siblingNode, index + 1);
	}

	/**
	 * 在一个非满节点中插入给定的项
	 * 
	 * @param node
	 *            - 非满节点
	 * @param entry
	 *            - 给定的项
	 * @return true，如果B树中不存在给定的项，否则false
	 */
	private boolean insertNotFull(BTNode<K, V> node, Entry<K, V> entry)
	{
		assert node.size() < maxKeySize;

		if (node.isLeaf()) // 如果是叶子节点，直接插入
			return node.insertEntry(entry);
		else
		{
			// 找到entry在给定节点应该插入的位置，那么entry应该插入该位置对应的子树中
			SearchResult<V> result = node.searchKey(entry.getKey());
			// 如果存在，则直接返回失败
			if (result.isExist())
				return false;
			BTNode<K, V> childNode = node.childAt(result.getIndex());
			if (childNode.size() == 2 * t - 1) // 如果子节点是满节点
			{
				// 则先分裂
				splitNode(node, result.getIndex());
				// 如果给定entry的键大于分裂之后新生成项的键，则需要插入该新项的右边，否则左边。
				if (compare(entry.getKey(), node.entryAt(result.getIndex()).getKey()) > 0)
					childNode = node.childAt(result.getIndex() + 1);
			}
			return insertNotFull(childNode, entry);
		}
	}

	/**
	 * 在B树中插入给定的键值对
	 * 
	 * @param key
	 *            - 键
	 * @param value
	 *            - 值
	 * @param true，如果B树中不存在给定的项，否则false
	 */
	public boolean insert(K key, V value)
	{
		if (root.size() == maxKeySize) // 如果根节点满了，则B树长高
		{
			BTNode<K, V> newRoot = new BTNode<K, V>(kComparator);
			newRoot.setLeaf(false);
			newRoot.addChild(root);
			splitNode(newRoot, 0);
			root = newRoot;
		}
		return insertNotFull(root, new Entry<K, V>(key, value));
	}

	/**
	 * 如果存在给定的键，则更新键关联的值，否则插入给定的项。
	 * 
	 * @param node
	 *            - 非满节点
	 * @param entry
	 *            - 给定的项
	 * @return true，如果B树中不存在给定的项，否则false
	 */
	private V putNotFull(BTNode<K, V> node, Entry<K, V> entry)
	{
		assert node.size() < maxKeySize;

		if (node.isLeaf()) // 如果是叶子节点，直接插入
			return node.putEntry(entry);
		else
		{
			// 找到entry在给定节点应该插入的位置，那么entry应该插入该位置对应的子树中
			SearchResult<V> result = node.searchKey(entry.getKey());
			// 如果存在，则更新
			if (result.isExist())
				return node.putEntry(entry);
			BTNode<K, V> childNode = node.childAt(result.getIndex());
			if (childNode.size() == 2 * t - 1) // 如果子节点是满节点
			{
				// 则先分裂
				splitNode(node, result.getIndex());
				// 如果给定entry的键大于分裂之后新生成项的键，则需要插入该新项的右边，否则左边。
				if (compare(entry.getKey(), node.entryAt(result.getIndex()).getKey()) > 0)
					childNode = node.childAt(result.getIndex() + 1);
			}
			return putNotFull(childNode, entry);
		}
	}

	/**
	 * 若B树中存在给定的键，则更新值,否则插入。
	 * 
	 * @param key
	 *            - 键
	 * @param value
	 *            - 值
	 * @return 如果B树中存在给定的键，则返回之前的值，否则null
	 */
	public V put(K key, V value)
	{
		if (root.size() == maxKeySize) // 如果根节点满了，则B树长高
		{
			BTNode<K, V> newRoot = new BTNode<K, V>(kComparator);
			newRoot.setLeaf(false);
			newRoot.addChild(root);
			splitNode(newRoot, 0);
			root = newRoot;
		}
		return putNotFull(root, new Entry<K, V>(key, value));
	}

	/**
	 * 从B树中删除一个与给定键关联的项。
	 * 
	 * @param key
	 *            - 给定的键
	 * @return 如果B树中存在给定键关联的项，则返回删除的项，否则null
	 */
	public Entry<K, V> delete(K key)
	{
		return delete(root, key);
	}

	/**
	 * 从以给定<code>node</code>为根的子树中删除与给定键关联的项。
	 * 
	 * @param node
	 *            - 给定的节点
	 * @param key
	 *            - 给定的键
	 * @return 如果B树中存在给定键关联的项，则返回删除的项，否则null
	 */
	private Entry<K, V> delete(BTNode<K, V> node, K key)
	{
		// 该过程需要保证，对非根节点执行删除操作时，其关键字个数至少为t。
		assert node.size() >= t || node == root;

		SearchResult<V> result = node.searchKey(key);
		/*
		 * 因为这是查找成功的情况，0 <= result.getIndex() <= (node.size() - 1)，
		 * 因此(result.getIndex() + 1)不会溢出。
		 */
		if (result.isExist())
		{
			// 1.如果关键字在节点node中，并且是叶节点，则直接删除。
			if (node.isLeaf())
				return node.removeEntry(result.getIndex());
			else
			{
				/*
				 * 2.a 如果节点node中前于key的子节点包含至少t个项 （1）从node中删除关键字
				 * （2）leftChildNode中最后一项插入node代替删除的关键字 （3）递归从leftChildNode删除最后一项
				 */
				BTNode<K, V> leftChildNode = node.childAt(result.getIndex());
				if (leftChildNode.size() >= t)
				{
					// 使用leftChildNode中的最后一个项代替node中需要删除的项
					node.removeEntry(result.getIndex());
					node.insertEntry(leftChildNode.entryAt(leftChildNode.size() - 1), result.getIndex());
					// 递归删除左子节点中的最后一个项
					return delete(leftChildNode, leftChildNode.entryAt(leftChildNode.size() - 1).getKey());
				} else
				{
					/*
					 * 2.b 如果节点node中后于key的子节点包含至少t个关键字 （1）从node中删除关键字
					 * （2）rightChildNode中第一项插入node代替删除的关键字
					 * （3）递归从rightChildNode删除第一项
					 */
					BTNode<K, V> rightChildNode = node.childAt(result.getIndex() + 1);
					if (rightChildNode.size() >= t)
					{
						// 使用rightChildNode中的第一个项代替node中需要删除的项
						node.removeEntry(result.getIndex());
						node.insertEntry(rightChildNode.entryAt(0), result.getIndex());
						// 递归删除右子节点中的第一个项
						return delete(rightChildNode, rightChildNode.entryAt(0).getKey());
					} else
					{
						/*
						 * 2.c 前于key和后于key的子节点都只包含t-1个项：
						 * （1）从node中删除关键字和rightChildNode
						 * （2）删除的关键字和rightChildNode都合并到leftChildNode
						 * ，若rightChildNode有子节点将其合并进leftChildNode
						 * （3）递归从leftChildNode删除关键字
						 */
						Entry<K, V> deletedEntry = node.removeEntry(result.getIndex());
						node.removeChild(result.getIndex() + 1);
						// 将node中与key关联的项和rightChildNode中的项合并进leftChildNode
						leftChildNode.addEntry(deletedEntry);
						for (int i = 0; i < rightChildNode.size(); ++i)
							leftChildNode.addEntry(rightChildNode.entryAt(i));
						// 将rightChildNode中的子节点合并进leftChildNode，如果有的话
						if (!rightChildNode.isLeaf())
						{
							for (int i = 0; i <= rightChildNode.size(); ++i)
								leftChildNode.addChild(rightChildNode.childAt(i));
						}
						return delete(leftChildNode, key);
					}
				}
			}
		} else
		{
			/*
			 * 因为这是查找失败的情况，0 <= result.getIndex() <= node.size()，
			 * 因此(result.getIndex() + 1)会溢出。
			 */
			if (node.isLeaf()) // 如果关键字不在节点node中，并且是叶节点，则什么都不做，因为该关键字不在该B树中
			{
				// logger.info("The key: " + key + " isn't in this BTree.");
				System.out.println("The key: " + key + " isn't in this BTree.");
				return null;
			}
			BTNode<K, V> childNode = node.childAt(result.getIndex());
			if (childNode.size() >= t) // // 如果子节点有不少于t个项，则递归删除
				return delete(childNode, key);
			else
			/*
			 * 3、删除的关键字不在内节点中 （1）查找其兄弟节点关键字个数是否≥t （2）若存在其一兄弟节点关键字个数≥t，则
			 * A、左兄弟满足：将siblingNode所在下标的node关键字插到childNode开头，并将其从node中删除；
			 * 将siblingNode的最后一个关键字插入node，并删除siblingNode最后一个关键字
			 * 若siblingNode不为叶，将其最后一个孩子移到childNode的第一个孩子，并删除其最后一个孩子
			 * B、右兄弟满足：将childNode所在下标的node关键字插到childNode末尾，并将其从node中删除；
			 * 将siblingNode的第一个关键字插入node，并删除siblingNode第一个关键字
			 * 若siblingNode不为叶，将其第一个孩子移到childNode的最后一个孩子，并删除其第一个孩子
			 * 最后，递归从childNode中删除关键字 （3）若两兄弟节点关键字个数都为t-1，则
			 * A、若存在右兄弟：将childNode所在下标的node关键字插到childNode末尾，并将其从node中删除；
			 * 将siblingNode从node的孩子节点中删除，并将siblingNode添加到childNode末尾
			 * 若siblingNode不为叶，将其孩子变为childNode的孩子的末尾
			 * B、若存在左兄弟：将siblingNode所在下标的node关键字插到childNode开头，并将其从node中删除；
			 * 将siblingNode从node的孩子节点中删除，并将siblingNode添加到childNode的开头
			 * 若siblingNode不为叶，将其孩子插到childNode孩子的开头
			 * 最后，若node是root并且node不包含任何项，将childNode赋给root，递归从childNode中删除关键字
			 */
			{
				// 先查找右边的兄弟节点
				BTNode<K, V> siblingNode = null;
				int siblingIndex = -1;
				if (result.getIndex() < node.size()) // 存在右兄弟节点
				{
					if (node.childAt(result.getIndex() + 1).size() >= t)
					{
						siblingNode = node.childAt(result.getIndex() + 1);
						siblingIndex = result.getIndex() + 1;
					}
				}
				// 如果右边的兄弟节点不符合条件，则试试左边的兄弟节点
				if (siblingNode == null)
				{
					if (result.getIndex() > 0) // 存在左兄弟节点
					{
						if (node.childAt(result.getIndex() - 1).size() >= t)
						{
							siblingNode = node.childAt(result.getIndex() - 1);
							siblingIndex = result.getIndex() - 1;
						}
					}
				}
				// 3.a 有一个相邻兄弟节点至少包含t个项
				if (siblingNode != null)
				{
					if (siblingIndex < result.getIndex()) // 左兄弟节点满足条件
					{
						childNode.insertEntry(node.entryAt(siblingIndex), 0);
						node.removeEntry(siblingIndex);
						node.insertEntry(siblingNode.entryAt(siblingNode.size() - 1), siblingIndex);
						siblingNode.removeEntry(siblingNode.size() - 1);
						// 将左兄弟节点的最后一个孩子移到childNode
						if (!siblingNode.isLeaf())
						{
							childNode.insertChild(siblingNode.childAt(siblingNode.size()), 0);
							siblingNode.removeChild(siblingNode.size());
						}
					} else
					// 右兄弟节点满足条件
					{
						childNode.insertEntry(node.entryAt(result.getIndex()), childNode.size() - 1);
						node.removeEntry(result.getIndex());
						node.insertEntry(siblingNode.entryAt(0), result.getIndex());
						siblingNode.removeEntry(0);
						// 将右兄弟节点的第一个孩子移到childNode
						if (!siblingNode.isLeaf())
						{
							childNode.addChild(siblingNode.childAt(0));
							siblingNode.removeChild(0);
						}
					}
					return delete(childNode, key);
				} else
				// 3.b 如果其相邻左右节点都包含t-1个项
				{
					if (result.getIndex() < node.size()) // 存在右兄弟，直接在后面追加
					{
						BTNode<K, V> rightSiblingNode = node.childAt(result.getIndex() + 1);
						childNode.addEntry(node.entryAt(result.getIndex()));
						node.removeEntry(result.getIndex());
						node.removeChild(result.getIndex() + 1);
						for (int i = 0; i < rightSiblingNode.size(); ++i)
							childNode.addEntry(rightSiblingNode.entryAt(i));
						if (!rightSiblingNode.isLeaf())
						{
							for (int i = 0; i <= rightSiblingNode.size(); ++i)
								childNode.addChild(rightSiblingNode.childAt(i));
						}
					} else
					// 存在左节点，在前面插入
					{
						BTNode<K, V> leftSiblingNode = node.childAt(result.getIndex() - 1);
						childNode.insertEntry(node.entryAt(result.getIndex() - 1), 0);
						node.removeEntry(result.getIndex() - 1);
						node.removeChild(result.getIndex() - 1);
						for (int i = leftSiblingNode.size() - 1; i >= 0; --i)
							childNode.insertEntry(leftSiblingNode.entryAt(i), 0);
						if (!leftSiblingNode.isLeaf())
						{
							for (int i = leftSiblingNode.size(); i >= 0; --i)
								childNode.insertChild(leftSiblingNode.childAt(i), 0);
						}
					}
					// 如果node是root并且node不包含任何项了
					if (node == root && node.size() == 0)
						root = childNode;
					return delete(childNode, key);
				}
			}
		}
	}

	/**
	 * 一个简单的层次遍历B树实现，用于输出B树。
	 */
	public void output()
	{
		Queue<BTNode<K, V>> queue = new LinkedList<BTNode<K, V>>();
		queue.offer(root);
		while (!queue.isEmpty())
		{
			BTNode<K, V> node = queue.poll();
			for (int i = 0; i < node.size(); ++i)
				System.out.print(node.entryAt(i) + " ");
			System.out.println();
			if (!node.isLeaf())
			{
				for (int i = 0; i <= node.size(); ++i)
					queue.offer(node.childAt(i));
			}
		}
	}
}
