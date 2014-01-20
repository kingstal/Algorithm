package chapter16_GreedyAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelector
{

	public static void main(String[] args)
	{
		int[] s =
		{ 0, 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
		int[] f =
		{ 0, 4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16 };
		ActivitySelector aSelector = new ActivitySelector();
		List<Integer> recursiveActivityIndex = new ArrayList<>();
		aSelector.recursiveActivitySelector(s, f, 0, 11, recursiveActivityIndex);
		for (Integer integer : recursiveActivityIndex)
			System.out.print(integer + " ");
		
		System.out.println();
		
		List<Integer> iterativeActivityIndex = aSelector.iterativeActivitySelector(s, f);
		for (Integer integer : iterativeActivityIndex)
			System.out.print(integer + " ");}

	/**
	 * A：递归调用版本
	 * 假设开始时间和结束时间按单调递增排序
	 * 添加虚拟活动:f[0]=0
	 * 
	 * @param s 活动开始时间
	 * @param f 后动结束时间
	 * @param k 求解子问题k
	 * @param n 问题规模
	 * @param activityIndex 用于保存结果
	 */
	public void recursiveActivitySelector(int[] s, int[] f, int k, int n, List<Integer> activityIndex)
	{
		int m = k + 1;
		while (m <= n && s[m] < f[k])
			m = m + 1;
		if (m <= n)
		{
			activityIndex.add(m);
			recursiveActivitySelector(s, f, m, n, activityIndex);
		}
	}
	
	/**
	 * B：迭代版本
	 * 假设开始时间和结束时间按单调递增排序
	 * @param s
	 * @param f
	 * @return
	 */
	public List<Integer> iterativeActivitySelector(int[] s,int[] f)
	{
		int n=s.length-1;
		List<Integer> activityIndex=new ArrayList<>();
		activityIndex.add(1);
		int k=1;
		for (int m = 2; m <=n; m++)
		{
			if (s[m]>=f[k])
			{
				activityIndex.add(m);
				k=m;
			}
		}
		return activityIndex;
	}
}
