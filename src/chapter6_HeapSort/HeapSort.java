package chapter6_HeapSort;
/**
 * 堆：是一个数组，可看成一个近似的完全二叉树
 */
import java.util.Scanner;

public class HeapSort
{
	public static void main(String[] args)
	{
		while (true)
		{
			int number;
			@SuppressWarnings("resource")
			Scanner scanner=new Scanner(System.in);
			number=scanner.nextInt();
			int array[]=new int[number];
			for (int i = 0; i < array.length; i++)
				array[i]=scanner.nextInt();
			
			HeapSort heapSort=new HeapSort();
			heapSort.HEAPSORT(array);
			for (int i = 0; i < array.length; i++)
				System.out.println(array[i]+" ");
		}
	}

	/**
	 * 父节点
	 * @param i
	 * @return
	 */
	private int parent(int i)
	{
		return i>>1;
	}
	
	/**
	 * 左孩子
	 * @param i 堆节点
	 * @return
	 */
	private int left(int i)
	{
		return i<<1;
	}
	
	/**
	 * 右孩子
	 * @param i 堆节点
	 * @return
	 */
	private int right(int i)
	{
		return (i<<1)+1;
	}
	
	/**
	 * 交换数组中的两个数
	 * @param al
	 * @param a
	 * @param b
	 */
	private void exchange(int[] al,int a,int b)
	{
		int t=al[a];
		al[a]=al[b];
		al[b]=t;
	}
	
	/**
	 * 维护最大堆性质
	 * @param A 数组
	 * @param i 堆节点
	 * @param heapSize 堆的节点数
	 */
	private void max_heapify(int[] A,int i,int heapSize)
	{
		int l=left(i);
		int r=right(i);
		int largest=i;
		if (l<=heapSize && A[l-1]>A[i-1])
			largest=l;
		if(r<=heapSize && A[r-1]>A[largest-1])
			largest=r;
		if (largest!=i)
		{
			exchange(A,i-1, largest-1);
			max_heapify(A, largest,heapSize);
		}
	}
	
	/**
	 * 建堆
	 * @param A 数组
	 * 子数组A（n/2+1..n）中的元素都是数的节点，从非叶节点开始维护
	 */
	private void make_heap(int[] A)
	{
		for(int i=A.length>>1;i>=1;i--)
			max_heapify(A, i,A.length);
	}
	
	/**
	 * 堆排序
	 * @param A
	 */
	private void HEAPSORT(int[] A)
	{
		make_heap(A);
		int heapSize=A.length;
		for (int i = A.length; i >1; i--)
		{
			exchange(A,0, i-1);
			max_heapify(A, 1, --heapSize);
		}
	}

	/**
	 * 基于最大堆实现最大优先队列
	 */
	
	/**
	 * 返回最大优先队列中具有最大关键字的元素
	 * @param A
	 * @return
	 */
	private int maximun(int[] A)
	{
		return A[0];
	}

	/**
	 * 去掉并返回具有最大关键字的元素
	 * @param A
	 * @param heapSize
	 * @return
	 */
	private int extract_max(int[] A,int heapSize)
	{
		if(heapSize<1)	return -1;
		int max = A[0];
		A[0] = A[heapSize-1];
		heapSize--;
		max_heapify(A, 1, heapSize);
		return max;
	}

	/**
	 * 将元素的关键字增加到K
	 * @param A
	 * @param i
	 * @param key
	 */
	private void increase_key(int[] A, int i, int key)
	{
		if(key<A[i-1]) return;
		A[i-1] = key;
		while(i>1 && A[parent(i)-1]<A[i-1])
		{
			exchange(A, i-1, parent(i)-1);
			i=parent(i);
		}
	}
}
