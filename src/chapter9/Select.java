package chapter9;

import java.util.Scanner;

/**
 * 期望为线性时间的选择算法
 * 
 * @author Arthur
 * 
 */
public class Select
{
	public static void main(String[] args)
	{
		while (true)
		{
			int number;
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			number = scanner.nextInt();
			int array[] = new int[number];
			for (int i = 0; i < array.length; i++)
				array[i] = scanner.nextInt();
			
			Select select = new Select();
			System.out.println(select.select(array, 0, number - 1, number >> 1));
		}
	}
	
	private int select(int[] A, int p, int r, int i)
	{
		if (p == r)
			return A[p];
		
		int q = patition(A, p, r);
		int k = q - p + 1;
		if (i == k)
			return A[q];
		else if (i < k)
			return select(A, p, q - 1, i);
		else
			return select(A, q + 1, r, i - k);
		
	}
	
	private int patition(int[] A, int p, int r)
	{
		int x = A[r];
		int i = p - 1;
		for (int j = p; j < r; j++)
		{
			if (A[j] < x)
			{
				i++;
				exchange(A, i, j);
			}
		}
		exchange(A, r, i + 1);
		return i + 1;
	}
	
	private void exchange(int[] a, int i, int j)
	{
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
}
