package chapter4_Divide;

import java.util.Scanner;

public class FindMaxSubarray
{
	public static void main(String args[]){
		while (true)
		{
			int number;
			@SuppressWarnings("resource")
			Scanner scanner=new Scanner(System.in);
			number=scanner.nextInt();
			int array[]=new int[number];
			for (int i = 0; i < array.length; i++)
				array[i]=scanner.nextInt();
			
			Result result=findMaxSubarray(array, 0, array.length-1);
			System.out.println("result.low="+result.low+"\nresult.high="+result.high+"\nresult.sum="+result.sum);
		}
	}
	public static Result findMaxSubarray(int[] array,int low,int high)
	{
		Result result=new Result();
		Result resultL=new Result();
		Result resultR=new Result();
		Result resultCross=new Result();
		if(low==high)
		{
			result.low=low;
			result.high=high;
			result.sum=array[low];
			return result;
		}else {
			int mid=(low+high)>>1;
			resultL=findMaxSubarray(array, low, mid);
			resultR=findMaxSubarray(array, mid+1, high);
			resultCross=findMaxCrossSubarray(array,low,mid,high);
			if (resultL.sum>=resultR.sum&&resultL.sum>=resultCross.sum)
				return resultL;
			else if(resultR.sum>=resultL.sum&&resultR.sum>=resultCross.sum)
				return resultR;
			else 
				return resultCross;
		}
	}

	private static Result findMaxCrossSubarray(int[] array, int low, int mid, int high)
	{
		int left_sum=-10000;//最好为负无穷
		int max_left=mid;
		int sum=0;
		for (int i = mid; i >=low; i--)
		{
			sum+=array[i];
			if (sum>left_sum)
			{
				left_sum=sum;
				max_left=i;
			}
		}
		int right_sum=-10000;
		int max_right=mid+1;
		sum=0;
		for (int i = mid+1; i <= high; i++)
		{
			sum+=array[i];
			if (sum>right_sum)
			{
				right_sum=sum;
				max_right=i;
			}
		}
		
		Result result=new Result();
		result.low=max_left;
		result.high=max_right;
		result.sum=left_sum+right_sum;
		return result;
	}
}

class Result
{
	int low;
	int high;
	int sum;
}