package csc365.bst;

import java.util.Scanner;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Iterator;

public class BSTTest{
	public static void main(String[] args){

		BST<Integer> bst = new BST<Integer>();

	
		bst.insert(new Integer(51));
		bst.insert(new Integer(100));
		bst.insert(new Integer(50));
		bst.insert(new Integer(44));
		bst.insert(new Integer(35));
		bst.insert(new Integer(10));
		bst.insert(new Integer(67));
		bst.insert(new Integer(11));
		bst.insert(new Integer(88));
		bst.insert(new Integer(45));
		bst.insert(new Integer(47));
		bst.insert(new Integer(46));


		bst.remove(51);
		bst.remove(44);

		bst.insert(new Integer(43));
		bst.insert(new Integer(62));
		bst.insert(new Integer(1));
		bst.insert(new Integer(23));
		bst.insert(new Integer(0));
		bst.insert(new Integer(9));

		bst.remove(23);
		bst.remove(45);

		Iterator<Integer> itr = bst.iterator();

		System.out.println(bst);

	}
}