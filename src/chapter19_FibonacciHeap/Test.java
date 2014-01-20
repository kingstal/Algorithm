package chapter19_FibonacciHeap;

public class Test
{

	public static void main(String[] args)
	{
		FibonacciHeap fibonacciHeap = new FibonacciHeap();
		int[] keys =
		{ 1, 2, 3, 4, 5, 6, 7, 9, 10, 11 };
		for (int i = 0; i < keys.length; i++)
		{
			FibonacciHeapNode fNode = new FibonacciHeapNode(keys[i]);
			fibonacciHeap.fibHeapInsert(fNode);
		}
		fibonacciHeap.fibHeapPrint();

		FibonacciHeapNode x = fibonacciHeap.fibHeapExtractMin();
		System.out.printf("抽取最小值%d之后：\n", x.getKey());
		fibonacciHeap.fibHeapPrint();

		x = fibonacciHeap.fibHeapSearch(11);
		if (null != x)
		{
			System.out.printf("查找%d成功,", x.getKey());
			fibonacciHeap.fibHeapDecreaseKey(x, 8);
			System.out.printf("减小到%d后：\n", x.getKey());
			fibonacciHeap.fibHeapPrint();
		}

		x = fibonacciHeap.fibHeapSearch(7);
		if (null != x)
		{
			System.out.printf("删除%d成功:\n", x.getKey());
			fibonacciHeap.fibHeapDelete(x);
			fibonacciHeap.fibHeapPrint();
		}
	}
}
