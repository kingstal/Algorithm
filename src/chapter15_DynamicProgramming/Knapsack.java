package chapter15_DynamicProgramming;

/**
 * 0-1背包问题：c[i][w]表示在w重量下选择前i件物品的最大价值
 * @author Arthur
 *
 */
public class Knapsack
{
	public int knapsack(int[]v,int[]w,int n,int W)
	{
		int[][] c=new int[n+1][W+1];
		
		for (int i = 0; i <=W; i++)
			c[0][W]=0;
		
		for (int i = 1; i <= n; i++)
		{
			c[i][0]=0;
			for (int weight = 1; weight <=W; weight++)
			{
				if (w[i]<=weight)
				{
					if (v[i]+c[i-1][weight-w[i]]>c[i-1][weight])
						c[i][weight]=v[i]+c[i-1][weight-w[i]];
					else
						c[i][weight]=c[i-1][weight];
				}else
					c[i][weight]=c[i-1][weight];
			}
		}
		
		return c[n][W];
	}

}
