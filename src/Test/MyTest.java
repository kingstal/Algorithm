package Test;

public class MyTest
{
	
	public static void main(String[] args)
	{
		System.out.println("dfad");
		int[] a={1,2};
		swap(a, 0, 1);
		for (int i = 0; i < a.length; i++)
		{
			System.out.println(a[i]+" ");
		}
		System.out.println();
	}
	
	private static void swap(int[] al, int a,int b)
	{
		int t=al[a];
		al[a]=al[b];
		al[b]=t;
	}
	
}
