package chapter16_GreedyAlgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test
{
	public static void main(String[] args)
	{
		/*int[] s =
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
			System.out.print(integer + " ");*/
		
		List<Node> nodes=new ArrayList<>();
		/*nodes.add(new Node("F",5.0));
		nodes.add(new Node("E",9.0));
		nodes.add(new Node("C",12.0));
		nodes.add(new Node("B",13.0));
		nodes.add(new Node("D",16.0));
		nodes.add(new Node("A",45.0));*/
		nodes.add(new Node("A",40.0));
		nodes.add(new Node("B",8.0));
		nodes.add(new Node("C",10.0));
		nodes.add(new Node("D",30.0));
		nodes.add(new Node("E",10.0));
		nodes.add(new Node("F",2.0));
		HuffmanTree huffmanTree=new HuffmanTree();
		Node root=huffmanTree.huffman(nodes);
		Map<String, String> codeMap=huffmanTree.coding(root);
		Set<String> mapSet=codeMap.keySet();
		Iterator<String> iterator=mapSet.iterator();
		while (iterator.hasNext())
		{
			String string = (String) iterator.next();
			System.out.println(string+":"+codeMap.get(string));
		}
	}
}

