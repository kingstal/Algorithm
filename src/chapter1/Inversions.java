package chapter1;
/**
 * 逆序对
 * 基于归并排序
 * @author Arthur
 *
 */
public class Inversions
{
	public static int countInversions(int[] array,int p,int r)
	{
		int inversions=0;
		if (p<r)
		{
			int q=(p+r)>>1;
			inversions+=countInversions(array, p, q);
			inversions+=countInversions(array, q+1, r);
			inversions+=mergeInversions(array,p,q,r);
		}
		return inversions;			
	}

	private static int mergeInversions(int[] array, int p, int q, int r)
	{
		int inversions=0;
		int n1=q-p+1;
		int n2=r-q;
		int[] L=new int[n1];
		int[] R=new int[n2];
		for (int i = 0; i < L.length; i++)
			L[i]=array[p+i];
		for (int i = 0; i < R.length; i++)
			R[i]=array[q+1+i];
		
		int i=0,j=0,k=p;
		while(i<n1&&j<n2)
		{
			if (L[i]<=R[j])
				array[k++]=L[i++];
			else {
				array[k++]=R[j++];
				inversions+=n1-i;
			}
		}
		while(i<n1)
			array[k++]=L[i++];
		while(j<n2)
			array[k++]=R[j++];
		
		return inversions;
	}
}
