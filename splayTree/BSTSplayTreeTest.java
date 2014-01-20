public class BSTSplayTreeTest{


	public static void main (String[] args){

		SplayBST<Integer> splay = new SplayBST<Integer>();


		System.out.println();
		System.out.println("This BSTSplayTreeTest class is desigened to run test cases for a SplayBST.");
		System.out.println("The splay tree that takes in Integer values then adds them according to Splay Tree Rules\n");


		//FIRST TEST
		System.out.println("FIRST TEST: Testing insert in an empty SplayBST\n");

		System.out.println("Adding 5 to the tree.\n");
		System.out.println("Result: ");

		splay.add(5);
		splay.printTreeToText();

		System.out.println("\nShould be: \n5\n");

		//SECOND TEST
		System.out.println("SECOND TEST: Testing for insert of 10. (right-right)");
		System.out.println("Insert method should add 10 as the right node of 1. Then 10 moves to the root\n");
		System.out.println("Adding 10 to the tree\n");
		System.out.println("Result: ");

		splay.add(10);
		splay.printTreeToText();

		System.out.println("\nShould be:");
		System.out.println("     10     ");
		System.out.println("  5          \n");


		//THIRD TEST
		System.out.println("THIRD TEST: Testing for insert of 7. (left-right)");
		System.out.println("Insert method should add 7 as the right node of 5.");
		System.out.println("Then rotate left about 5. Then rotate right about 10\n");


		System.out.println("Adding 7 to the tree\n");
		System.out.println("Result: ");
		splay.add(7);
		splay.printTreeToText();

		System.out.println("\nShould be:");
		System.out.println("     7     ");
		System.out.println("  5     10     \n");


		//FOURTH TEST
		System.out.println("FOURTH TEST: Testing for insert of 8. (right-left)");
		System.out.println("Insert method should add 8 as the left node of 10.");
		System.out.println("Then rotate right about 10. Then rotate left about 7\n");

		System.out.println("Adding 8 to the tree\n");
		System.out.println("Result: ");
		
		splay.add(8);
		splay.printTreeToText();

		System.out.println("\nShould be:");
		System.out.println("     	8     ");
		System.out.println("   7     	10     ");
		System.out.println("5	     	      \n");

		//splay.splayTree(5);

		//splay.printTreeToText();


		//FIFTH TEST
		System.out.println("FIFTH TEST: Testing for insert of 12 (right-right)");
		System.out.println("Insert method should add 12 as the right node of 10.");
		System.out.println("Then rotate right about 10. Then rotate right about 8\n");

		System.out.println("Adding 12 to the tree");
		System.out.println("Result: ");
		splay.add(12);
		splay.printTreeToText();

		System.out.println("\nShould be:");
		System.out.println("    				12     ");
		System.out.println("   			10         ");
		System.out.println("		8	     	   ");
		System.out.println("	7	     	       ");
		System.out.println("5	     	           \n");

		//SIXTH TEST
		System.out.println("SIXTH TEST: Testing for insert of 4 (left-left)");
		System.out.println("Insert method should add 4 as the left node of 5.");
		System.out.println("Then rotate right about 10. Then rotate right about 8\n");

		System.out.println("Adding 4 to the tree\n");
		System.out.println("Result: ");
		splay.add(4);
		splay.printTreeToText();

		System.out.println("\nShould be:");
		System.out.println("4");
		System.out.println("\t\t\t10");
		System.out.println("\t\t7\t\t12");
		System.out.println("\t5\t\t8");

		System.out.println("\n\n\n");


		//START OF SEARCH TEST
		System.out.println("START OF SEARCH TEST\n");	
		
		//SEARCH TEST 1
		System.out.println("\nSEARCH TEST 1");
		System.out.println("Searching for 10:\n ");

		splay.search(10);
		splay.printTreeToText();

		System.out.println("\nResult should be: ");
		System.out.println("\t10");
		System.out.println("4\t\t12");
		System.out.println("\t7");
		System.out.println("5\t8");
		System.out.println("4");


		//SEARCH TEST 2
		System.out.println("\nSEARCH TEST 2");
		System.out.println("Searching for 7:\n ");
		splay.search(7);
		splay.printTreeToText();

		System.out.println("\nResult should be: ");
		System.out.println("\t\t7");
		System.out.println("4\t\t\t10");
		System.out.println("\t5\t    8\t12");
	


		//SEARCH TEST 3: element does not exist

		System.out.println("\nSEARCH TEST 3");
		System.out.println("Searching for 88:\n ");
		splay.search(88);
		splay.printTreeToText();

		System.out.println("The element does not exist so it should return null");
		System.out.println("\nResult should be: ");
		System.out.println("\t\t\t\t12");
		System.out.println("\t\t\t10");
		System.out.println("\t7");
		System.out.println("4\t\t8");
		System.out.println("\t5");

		


	}
}