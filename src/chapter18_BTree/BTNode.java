package chapter18_BTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * B树结点类：结点存储的是键值对
 * @author Arthur
 *
 */
public class BTNode<K,V>
{
	private List<Entry<K, V>> entrys;//结点存储对象
	private List<BTNode<K, V>> children;//孩子结点
	private boolean leaf;//是否为叶节点
	private Comparator<K> kComparator;//键的比较函数对象
	
	
	public BTNode()
	{
		entrys=new ArrayList<Entry<K, V>>();
		children=new ArrayList<BTNode<K, V>>();
		leaf=false;
	}
	
	
	public BTNode(Comparator<K> kComparator)
	{
		this();
		this.kComparator = kComparator;
	}


	public boolean isLeaf()
	{
		return leaf;
	}
	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}
	
	/**
	 * 返回关键字个数，若为非叶结点，孩子个数为size+1
	 * @return
	 */
	public int size()
	{
		return entrys.size();
	}
	
	@SuppressWarnings("unchecked")
	int compare(K key1,K key2)
	{
		return kComparator == null ? ((Comparable<K>)key1).compareTo(key2) : kComparator.compare(key1, key2);	
	}

	/**
	 * 在节点中查找给定的键。
	 * 如果节点中存在给定的键，则返回一个<code>SearchResult</code>，
	 * 标识此次查找成功，给定的键在节点中的索引和给定的键关联的值；
	 * 如果不存在，则返回<code>SearchResult</code>，
	 * 标识此次查找失败，给定的键应该插入的位置，该键的关联值为null。
	 * <p/>
	 * 如果查找失败，返回结果中的索引域为[0, {@link #size()}]；
	 * 如果查找成功，返回结果中的索引域为[0, {@link #size()} - 1]
	 * <p/>
	 * 这是一个二分查找算法，可以保证时间复杂度为O(log(t))。
	 * 
	 * @param key - 给定的键值
	 * @return - 查找结果
	 */
	public SearchResult<V> searchKey(K key)
	{
		int low = 0;
		int high = entrys.size() - 1;
		int mid = 0;
		while(low <= high)
		{
			mid = (low + high) / 2; // 先这么写吧，BTree实现中，l+h不可能溢出
			Entry<K, V> entry = entrys.get(mid);
			if(compare(entry.getKey(), key) == 0) // entrys.get(mid).getKey() == key
				break;
			else if(compare(entry.getKey(), key) > 0) // entrys.get(mid).getKey() > key
				high = mid - 1;
			else // entry.get(mid).getKey() < key
				low = mid + 1;
		}
		boolean exist = false;
		int index = 0;
		V value = null;
		if(low <= high) // 说明查找成功
		{
			exist = true;
			index = mid; // index表示元素所在的位置
			value = entrys.get(index).getValue();
		}
		else
		{
			exist = false;
			index = low; // index表示元素应该插入的位置
		}
		return new SearchResult<V>(exist, index, value);
	}
	
	/**
	 * 将给定的项追加到节点的末尾，
	 * 确保调用该方法之后，节点中的项还是按照关键字以非降序存放。
	 * 
	 * @param entry - 给定的项
	 */
	public void addEntry(Entry<K, V> entry)
	{
		entrys.add(entry);
	}
	
	/**
	 * 在该节点中给定索引的位置插入给定的项，保证项插入了正确的位置
	 * 
	 * @param entry - 给定的键值
	 * @param index - 给定的索引
	 */
	public void insertEntry(Entry<K, V> entry, int index)
	{
		//通过新建一个ArrayList来实现插入
		List<Entry<K, V>> newEntrys = new ArrayList<Entry<K, V>>();
		int i = 0;
		for(; i < index; ++ i)
			newEntrys.add(entrys.get(i));
		newEntrys.add(entry);
		for(; i < entrys.size(); ++ i)
			newEntrys.add(entrys.get(i));
		entrys.clear();
		entrys = newEntrys;
	}
	
	/**
	 * 在该节点中插入给定的项，保证插入之后其键值还是以非降序存放。
	 * 注意：B树中不允许键值重复。
	 * 
	 * @param entry - 给定的键值
	 * @return true:插入成功，false:插入失败
	 */
	public boolean insertEntry(Entry<K, V> entry)
	{
		SearchResult<V> result = searchKey(entry.getKey());
		if(result.isExist())
			return false;
		else
		{
			insertEntry(entry, result.getIndex());
			return true;
		}
	}

	/**
	 * 删除给定索引的<code>entry</code>。
	 * 保证给定的索引合法
	 * 
	 * @param index - 给定的索引
	 * @param 给定索引处的项
	 */
	public Entry<K, V> removeEntry(int index)
	{
		return entrys.remove(index);
	}
	
	/**
	 * 得到节点中给定索引的项
	 * 你需要自己保证给定的索引是合法的。
	 * 
	 * @param index - 给定的索引
	 * @return 节点中给定索引的项
	 */
	public Entry<K, V> entryAt(int index)
	{
		return entrys.get(index);
	}
	
	/**
	 * 若节点中存在给定的键，则更新其关联的值,否则插入
	 * 
	 * @param entry - 给定的项
	 * @return null，若节点之前不存在给定的键，否则返回给定键之前关联的值
	 */
	public V putEntry(Entry<K, V> entry)
	{
		SearchResult<V> result = searchKey(entry.getKey());
		if(result.isExist())
		{
			V oldValue = entrys.get(result.getIndex()).getValue();
			entrys.get(result.getIndex()).setValue(entry.getValue());
			return oldValue;
		}
		else
		{
			insertEntry(entry, result.getIndex());
			return null;
		}
	}
	
	/**
	 * 返回节点中给定索引的子节点
	 * 
	 * @param index - 给定的索引
	 * @return 给定索引对应的子节点
	 */
	public BTNode<K, V> childAt(int index)
	{
		if(isLeaf())
			throw new UnsupportedOperationException("Leaf node doesn't have children.");
		return children.get(index);
	}
	
	/**
	 * 将给定的子节点追加到该节点的末尾。
	 * 
	 * @param child - 给定的子节点
	 */
	public void addChild(BTNode<K, V> child)
	{
		children.add(child);
	}
	
	/**
	 * 删除该节点中给定索引位置的子节点。
	 * 
	 * @param index - 给定的索引
	 */
	public void removeChild(int index)
	{
		children.remove(index);
	}
	
	/**
	 * 将给定的子节点插入到该节点中给定索引的位置
	 * 
	 * @param child - 给定的子节点
	 * @param index - 子节点带插入的位置
	 */
	public void insertChild(BTNode<K, V> child, int index)
	{
		List<BTNode<K, V>> newChildren = new ArrayList<BTNode<K, V>>();
		int i = 0;
		for(; i < index; ++ i)
			newChildren.add(children.get(i));
		newChildren.add(child);
		for(; i < children.size(); ++ i)
			newChildren.add(children.get(i));
		children = newChildren;
	}
}

//结点存储类型
class Entry<K, V>
{
	private K key;
	private V value;
	
	public Entry(K k, V v)
	{
		this.key = k;
		this.value = v;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
	
	public void setValue(V value)
	{
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return key + ":" + value;
	}
}

/**
 * 在B树节点中搜索给定键值的返回结果。
 * 该结果有两部分组成。第一部分表示此次查找是否成功，
 * 如果查找成功，第二部分表示给定键值在B树节点中的位置，
 * 如果查找失败，第二部分表示给定键值应该插入的位置。
 */
class SearchResult<V>
{
	private boolean exist;//是否存在
	private int index;//键值在B树结点的位置
	private V value;
	
	public SearchResult(boolean exist, int index)
	{
		this.exist = exist;
		this.index = index;
	}
	
	public SearchResult(boolean exist, int index, V value)
	{
		this(exist, index);
		this.value = value;
	}
	
	public boolean isExist()
	{
		return exist;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public V getValue() 
	{
		return value;
	}
}

