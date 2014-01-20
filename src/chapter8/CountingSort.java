package chapter8;

import java.util.Scanner;

public class CountingSort
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
			
			CountingSort countingSort =new CountingSort();
			array = countingSort.counting_sort(array, 10);
			
			for (int i = 0; i < array.length; i++)
				System.out.println(array[i]+" ");
		}
	}
	/**
	 * B[]存放排序的输出	C[]记录<=对应下标的个数
	 * @param A
	 * @param k
	 * @return
	 */
	private int[] counting_sort(int[] A,int k)
	{
		int[] B =new int[A.length];
		int[] C =new int[k];
		
		for (int i = 0; i < A.length; i++)
			C[A[i]]++;
		
		for (int i = 1; i < C.length; i++)
			C[i]+=C[i-1];
		
		for (int i = A.length-1; i >=0; i--)
		{
			B[C[A[i]]] = A[i];
			C[A[i]]--;
		}
		return B;
	}
}
