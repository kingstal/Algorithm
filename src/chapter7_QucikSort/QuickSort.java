package chapter7_QucikSort;

import java.util.Scanner;

/**
 * A[p..r]:A[p..i]<=x	A[i+1..j-1]>x	A[j..r-1]不确定
 * @author Arthur
 *
 */
public class QuickSort
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
			
			QuickSort quickSort=new QuickSort();
			quickSort.quickSort(array, 0, array.length-1);
			for (int i = 0; i < array.length; i++)
				System.out.println(array[i]+" ");
		}
	}
	
	private void quickSort(int[] A,int p,int r)
	{
		if (p<r)
		{
			int q = partitition(A,p,r);
			quickSort(A, p, q-1);
			quickSort(A, q+1, r);
		}
	}

	private int partitition(int[] A, int p, int r)
	{
		int x=A[r];
		int i=p-1;
		for (int j = p; j < r; j++)
		{
			if (A[j]<=x)
			{
				i++;
				exchange(A, j, i);
			}
		}
		exchange(A, r, i+1);
		return i+1;
	}
	
	private void exchange(int[] al,int a,int b)
	{
		int t=al[a];
		al[a]=al[b];
		al[b]=t;
	}
}
