package chapter15_DynamicProgramming;

/**
 * 最优搜索树
 * @author Arthur
 *
 */
public class OptimalBST
{
	public Result optimalBST(double[]p,double[]q,int n)
	{
		double[][] e=new double[n+2][n+1];//保存子树期望搜索代价
		double[][] w=new double[n+2][n+1];//保存子树成为某节点子树时期望搜索代价的增加值
		int[][] root=new int[n+1][n+1];//保存关键字k[i]到k[j]子树的根
		
		for (int i = 1; i <= n+1; i++)//初始化
		{
			e[i][i-1]=q[i-1];
			w[i][i-1]=q[i-1];
		}
		
		for (int l = 1; l <= n; l++)//计算1<=i<=j<=n所有的e[i][j]和w[i][j]
		{
			for (int i = 1; i <= n-l+1; i++)
			{
				int j=i+l-1;
				e[i][j]=Double.MAX_VALUE;
				w[i][j]=w[i][j-1]+p[j]+q[j];
				double t;
				for (int r = i; r <=j; r++)//尝试下标r
				{
					t=e[i][r-1]+e[r+1][j]+w[i][j];
					if (t<e[i][j])
					{
						e[i][j]=t;
						root[i][j]=r;
					}
				}
			}
		}
		
		Result result=new Result(root, e, w);
		return result;
	}
}

class Result
{
	private int[][] root;
	private double[][] e;
	private double[][] w;
	
	public Result()
	{
		super();
	}
	public Result(int[][] root, double[][] e ,double[][] w)
	{
		super();
		this.root = root;
		this.e = e;
		this.w = w;
	}
	public double[][] getW()
	{
		return w;
	}
	public void setW(double[][] w)
	{
		this.w = w;
	}
	public int[][] getRoot()
	{
		return root;
	}
	public void setRoot(int[][] root)
	{
		this.root = root;
	}
	public double[][] getE()
	{
		return e;
	}
	public void setE(double[][] e)
	{
		this.e = e;
	}
}