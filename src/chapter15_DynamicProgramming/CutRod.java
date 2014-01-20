package chapter15_DynamicProgramming;

/**
 * 钢条切割：最优子结构（问题的最优解由相关子问题的最优解组合而成）
 * @author Arthur
 * r[n]=max(p[i]+r[n-i])  (1≤i≤n)
 */
public class CutRod
{
	/**
	 * 自顶向下递归实现：反复调用相同的子问题，工作量爆炸性增长，运行时间为2的n次方
	 * @param p 长度为i对应的价格
	 * @param n  钢条长度
	 * @return
	 */
	public int cut(int[] p,int n)
	{
		if(n==0)
			return 0;
		int q=0;
		for(int i=1;i<=n;i++)
			q=Math.max(q, p[i]+cut(p, n-i));
		return q;
	}

	/**
	 * 带备忘的自顶向下法：使用数组记录子问题的值（空间换时间）
	 * @param p
	 * @param n
	 * @return
	 */
	public int memoizedCut(int[] p,int n)
	{
		int[] r=new int[n+1];
		for (int i = 0; i < r.length; i++)
			r[i]=-1;//"未知值"表示法（收益非负）
		return memoizedCutAux(p,n,r);
	}

	private int memoizedCutAux(int[] p, int n, int[] r)
	{
		if(r[n]>=0)//检查所需值是否已知
			return r[n];
		int q;//未知则计算后放入数组r[n]中保存
		if(n==0)
			q=0;
		else 
		{
			q=-1;
			for(int i=1;i<=n;i++)
				q=Math.max(q, p[i]+memoizedCutAux(p, n-i, r));
		}
		r[n]=q;
		return q;
	}

	/**
	 * 自底向上：将子问题按规模排序，由小到大顺序求解
	 * @param p
	 * @param n
	 * @return
	 */
	public int bottomUpCut(int[] p,int n)
	{
		int[] r=new int[n+1];
		r[0]=0;
		int q;
		for(int j=1;j<=n;j++)//按升序求解规模为j的子问题解
		{
			q=-1;
			for (int i = 1; i <=j; i++)
				q=Math.max(q, p[i]+r[j-i]);
			r[j]=q;
		}
		return r[n];
	}
	
	/**
	 * 
	 * @param p
	 * @param n
	 * @return
	 * r[j]：长度为j钢条的最大收益
	 * s[j]：最优解对应的第一段钢条的切割长度
	 */
	public int[][] extendedBottomUpCut(int[] p,int n)
	{
		int[] r=new int[n+1];
		int[] s=new int[n+1];
		int[][] result=new int[2][n+1];
		r[0]=0;
		int q;
		for (int j = 1; j <=n; j++)
		{
			q=-1;
			for (int i = 1; i <=j; i++)
			{
				if (q<p[i]+r[j-i])
				{
					q=p[i]+r[j-i];
					s[j]=i;
				}
			}
			r[j]=q;
		}
		result[0]=r;
		result[1]=s;
		return result;
	}
}
