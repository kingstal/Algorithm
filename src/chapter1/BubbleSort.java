package chapter1;

public class BubbleSort
{
	public static void bubbleSort(int[]array)
	{
		for (int i = 0; i < array.length-1; i++)
		{
			for (int j = array.length-1; j > i; j--)
			{
				if (array[j]<array[j-1])
				{
					int t=array[j-1];
					array[j-1]=array[j];
					array[j]=t;
				}
			}
		}
	}
}
