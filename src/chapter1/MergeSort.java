package chapter1;

public class MergeSort
{
	public static void mergeSort(int[] al,int p,int r)
	{
		if (p<r)
		{
			int q=(r+p)>>1;
			mergeSort(al, p, q);
			mergeSort(al, q+1, r);
			merge(al,p,q,r);
		}
	}
	
	private static void merge(int[] al,int p,int q,int r)
	{
		int i=p,j=q+1,k=0;
		int[] temp=new int[r-p+1];
		
		while (i<=q&&j<=r)
		{
			if(al[i]<al[j])
				temp[k++]=al[i++];
			else 
				temp[k++]=al[j++];
		}
		
		while(i<=q)
			temp[k++]=al[i++];
		while(j<=r)
			temp[k++]=al[j++];
		
		for(i=p,k=0;i<=r;i++,k++)
			al[i]=temp[k];
	}
}
