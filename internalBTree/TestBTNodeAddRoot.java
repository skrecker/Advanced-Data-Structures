public class TestBTNodeAddRoot{
	public static void main(String[] args){
		InternalBTree<Integer> bTree = new InternalBTree<Integer>();

		System.out.println("Adding 1, 2, 3, 4, 5, 6, 7, 8, 9,");
		bTree.add(1);
		bTree.add(2);
		bTree.add(3);
		bTree.add(4);
		bTree.add(5);
		bTree.add(6);
		bTree.add(7);
		bTree.add(8);
		bTree.add(9);
		bTree.printTree();


		System.out.println("Adding 5(duplicate), 6(duplicate)");
		bTree.add(5);
		bTree.add(6);
		bTree.printTree();

		System.out.println("Adding 4(duplicate), 22, 100");
		bTree.add(4);
		bTree.add(22);
		bTree.add(100);
		bTree.printTree();


		System.out.println("Adding 78, 55, 11, 7(duplicate), 54");
		bTree.add(78);
		bTree.add(55);
		bTree.add(11);
		bTree.printTree();
		bTree.add(7);
		bTree.printTree();
		bTree.add(54);
		bTree.printTree();


		System.out.println("Adding 10, 12, 14, 1(duplicate)");
		bTree.add(10);
		bTree.printTree();
		bTree.add(12);
		bTree.printTree();
		bTree.add(14);
		bTree.printTree();
		bTree.add(1);
		bTree.printTree();
		
		
		bTree.add(23);
		bTree.printTree();
		bTree.add(27);
		bTree.printTree();
		bTree.add(36);
		bTree.printTree();
		bTree.add(44);
		bTree.printTree();
		bTree.add(45);
		bTree.printTree();
		bTree.add(100);
		bTree.add(99);
		bTree.add(207);
		bTree.add(155);
		bTree.add(200);
		bTree.add(33);
		bTree.add(34);
		bTree.add(77);
		bTree.add(65);
		bTree.add(111);
		bTree.add(37);
		bTree.add(38);
		bTree.add(66);
		bTree.add(67);
		bTree.add(68);
		bTree.add(156);
		bTree.add(157);
		bTree.add(158);
		bTree.add(159);
		bTree.add(200);
	

		bTree.printTree();
	}
}