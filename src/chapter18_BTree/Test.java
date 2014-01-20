package chapter18_BTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test
{

	public static void main(String[] args)
	{
		Random random = new Random();
		BTree<Integer, Integer> btree = new BTree<Integer, Integer>(3);
		List<Integer> save = new ArrayList<Integer>();
		for(int i = 0; i < 20; ++ i)
		{
			int r = random.nextInt(100);
			save.add(r);
			System.out.println(r);
			btree.insert(r, r);
		}
		
		System.out.println("----------------------");
		btree.output();
		System.out.println("----------------------");
		btree.delete(save.get(0));
		btree.output();
	}

}
