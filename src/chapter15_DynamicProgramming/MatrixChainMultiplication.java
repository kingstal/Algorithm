package chapter15_DynamicProgramming;

import java.util.ArrayList;

/**
 * 矩阵链乘法
 * 			0											若i=j
 * m[i,j]=	min{m[i,k]+m[k+1,j]+p[i-1]p[k]p[j]} i<=k<j	若i<j
 * @author Arthur
 *
 */
public class MatrixChainMultiplication
{
	/**
	 * 自底向上
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<int[][]> matrixChainOrder(int[] p)
	{
		int n = p.length - 1;
		int[][] m = new int[n + 1][n + 1];// 保存代价m[i,j]
		int[][] s = new int[n][n + 1];// 记录最优值m[i,j]对应的分割点

		for (int i = 1; i <= n; i++)
			// 初始化：当i=j时，m[i][j]=0
			m[i][i] = 0;

		for (int l = 2; l <= n; l++)// l为链长度
		{
			for (int i = 1; i <= n - l + 1; i++)// i为链的开头
			{
				int j = i + l - 1;// j为链的结尾
				m[i][j] = Integer.MAX_VALUE;
				int q;
				for (int k = i; k <= j - 1; k++)// k为分割点
				{
					q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
					if (q < m[i][j])
					{
						m[i][j] = q;
						s[i][j] = k;
					}
				}
			}
		}

		ArrayList<int[][]> aList = new ArrayList<>();
		aList.add(m);
		aList.add(s);
		return aList;
	}

	public void printOptimalParens(int[][] s, int i, int j)
	{
		if (i == j)
			System.out.print("A" + i);
		else
		{
			System.out.print("(");
			printOptimalParens(s, i, s[i][j]);
			printOptimalParens(s, s[i][j] + 1, j);
			System.out.print(")");
		}
	}

	/**
	 * 带备忘录
	 * @param p
	 * @return
	 */
	public int memoizedMatrixChain(int[] p)
	{
		int n = p.length - 1;
		int[][] m = new int[n + 1][n + 1];// 保存代价m[i,j]

		for (int i = 1; i <= n; i++)
			for (int j = i; j <= n; j++)
				m[i][j] = Integer.MAX_VALUE;

		return lookUpChain(m, p, 1, n);
	}

	private int lookUpChain(int[][] m, int[] p, int i, int j)
	{
		if (m[i][j] < Integer.MAX_VALUE)
			return m[i][j];

		int q = 0;
		if (i == j)
			m[i][j] = 0;
		else
		{
			for (int k = i; k <= j - 1; k++)
			{
				q = lookUpChain(m, p, i, k) + lookUpChain(m, p, k + 1, j) + p[i - 1] * p[k] * p[j];
				if (q < m[i][j])
					m[i][j] = q;
			}

		}
		return m[i][j];
	}
}
