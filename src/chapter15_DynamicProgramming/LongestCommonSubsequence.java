package chapter15_DynamicProgramming;

/**
 * 最长公共子序列问题
 * @author Arthur
 * 			0						若i=0或j=0
 * c[i,j]=	c[i-1,j-1]+1			若i、j>0且x[i]=y[j]
 * 			max(c[i-1,j],c[i,j-1])	若i、j>0且x[i]≠y[j]
 */
public class LongestCommonSubsequence
{
	public char[][] LCSLength(char[] x,char[] y)
	{
		int m=x.length;
		int n=y.length;
		char[][] b=new char[m+1][n+1];//记录对应计算c[i][j]的子问题最优解
		int[][] c=new int[m+1][n+1];//保存LCS的长度
		
		for(int i=1;i<=m;i++)
			c[i][0]=0;
		for(int j=1;j<=n;j++)
			c[0][j]=0;
		
		//按行从左至右计算
		for (int i = 1; i <=m; i++)
		{
			for (int j = 1; j <=n; j++)
			{
				if (x[i-1]==y[j-1])
				{
					c[i][j]=c[i-1][j-1]+1;
					b[i][j]='↖';
				}
				else if (c[i-1][j]>=c[i][j-1]) {
					c[i][j]=c[i-1][j];
					b[i][j]='↑';
				}
				else {
					c[i][j]=c[i][j-1];
					b[i][j]='←';
				}
			}
		}
		
		return b;
	}
	
	public void printLCS(char[][] b,char[] x,int i,int j)
	{
		if(i==0||j==0)
			return;
		
		if (b[i][j]=='↖')
		{
			printLCS(b, x, i-1, j-1);
			System.out.print(x[i-1]);
		}
		else if (b[i][j]=='↑') 
			printLCS(b, x, i-1, j);
		else
			printLCS(b, x, i, j-1);
	}
}
