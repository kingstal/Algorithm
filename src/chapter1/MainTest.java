package chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainTest
{
	public static void main(String[] args)
	{
		while (true)
		{
			int insertN;
			@SuppressWarnings("resource")
			Scanner scanner=new Scanner(System.in);
			insertN=scanner.nextInt();
//			ArrayList<Integer> aList=new ArrayList<>();
			int array[]=new int[insertN];
			while (insertN>0)
			{
//				aList.add(scanner.nextInt());
				array[insertN-1]=scanner.nextInt();
				insertN--;
			}
//			InsertionSort.insertionSort_ascend(aList);
//			InsertionSort.insertionSort_descend(aList);
//			MergeSort.mergeSort(array, 0, array.length-1);
			int inversions=Inversions.countInversions(array, 0, array.length-1);
//			BubbleSort.bubbleSort(array);
			for (int i = 0; i < array.length; i++)
				System.out.println(array[i]+" ");
			System.err.println("inversions:"+inversions);
//			for (Integer integer : aList)
//			for (Integer integer : array)
//			{
//				System.out.print(integer+"、");
//			}
//			System.out.println();
			

		}
	}
}
