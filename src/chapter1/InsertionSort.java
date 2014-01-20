package chapter1;

import java.util.ArrayList;

/**
 * @author Arthur
 * 插入排序：数组A[1..n]
 * for j=2 to A.length
 * 		//Insert A[j] into the sorted sequenceA[1..j-1] 
 * 		key=A[j]
 * 		i=j-1
 * 		while i>0 and A[i]>key
 * 			A[i+1]=A[i]
 * 			i=i-1
 * 		A[i+1]=key
 */
public class InsertionSort
{
	public static void insertionSort_ascend(ArrayList<Integer> al)
	{
		for (int j = 1; j < al.size(); j++)
		{
			int key=al.get(j);
			int i=j-1;
			while (i>=0 && al.get(i)>key)
			{
				al.set(i+1, al.get(i));
				i=i-1;
			}
			al.set(i+1, key);
		}
	}
	
	public static void insertionSort_descend(ArrayList<Integer> al)
	{
		for (int i = 1; i < al.size(); i++)
		{
			int key = al.get(i);
			int j = i-1;
			while (j>=0 && al.get(j)<key)
			{
				al.set(j+1, al.get(j));
				j=j-1;
			}
			al.set(j+1, key);
		}
	}
}
