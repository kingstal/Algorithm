package chapter15_DynamicProgramming;

import java.util.ArrayList;

public class Test
{
	public static void main(String[] args)
	{
		/*CutRod cutRod=new CutRod();
		int[] p={0,1,5,8,9,10,17,17,20,24,30};
		int[][] result=new int[2][11];
		result=cutRod.extendedBottomUpCut(p, 10);
		for (int i = 0; i < result.length; i++)
		{
			int[] js = result[i];
			for (int j = 0; j < js.length; j++)
			{
				System.out.print(result[i][j]+" ");
			}
			System.out.println();
		}*/
		
		/*for (int i = 1; i < p.length; i++)
		{
			System.out.println("r["+i+"]="+cutRod.cut(p, i));
			System.out.println("r["+i+"_memo]="+cutRod.memoizedCut(p, i));
			System.out.println("r["+i+"_bottom]="+cutRod.bottomUpCut(p, i));
			
		}*/
		
		/*int[]p={30,35,15,5,10,20,25};
		MatrixChainMultiplication matrixChainMultiplication=new MatrixChainMultiplication();
		ArrayList<int[][]> aList=matrixChainMultiplication.matrixChainOrder(p);
		matrixChainMultiplication.printOptimalParens(aList.get(1), 1, 6);*/
		
		/*LongestCommonSubsequence longestCommonSubsequence=new LongestCommonSubsequence();
		char[] X={'A','B','C','B','D','A','B'};
		char[] Y={'B','D','C','A','B','A'};
		
		char[][] b=longestCommonSubsequence.LCSLength(X, Y);
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b[i].length; j++)
			{
				System.out.print(b[i][j]);
			}
			System.out.println();
		}
		longestCommonSubsequence.printLCS(b, X, 7, 6);*/
		
		/*double[] p={0,0.15,0.10,0.05,0.10,0.20};
		double[] q={0.05,0.10,0.05,0.05,0.05,0.10};
		OptimalBST optimalBST=new OptimalBST();
		Result result=optimalBST.optimalBST(p, q, p.length-1);
		double[][]e=result.getE();
		int[][]root=result.getRoot();
		double[][] w=result.getW();
		System.out.println("Root:");
		for (int[] is : root)
		{
			for (int i : is)
				System.out.print(i+" ");
			System.out.println();
		}
		System.out.println("e:");
		for (double[] e1 : e)
		{
			for (double e2 : e1)
				System.out.printf("%.2f%s", e2," ");
			System.out.println();
		}
		System.out.println("w:");
		for (double[] w1 : w)
		{
			for (double w2 : w1)
				System.out.printf("%.2f%s", w2," ");
			System.out.println();
		}*/
		
		Knapsack knapsack=new Knapsack();
		int[] v={0,10,6,12};
		int[] w={0,2,1,3};
		System.out.println(knapsack.knapsack(v, w, 3, 5));
		
	}
	
}
