import java.util.Random;

public class RemoveTest{



	public static void main(String[] args){

		boolean DEBUG = false;

		InternalBTree<Integer> tree = new InternalBTree<Integer>(); 

		//System.out.println("Adding 1, 2, 3, 4, 5, 6, 7, 8");

		/*
		tree.add(3);
		tree.add(5);
		tree.add(12);
		tree.add(13);
		tree.add(14);
		tree.add(1);
		tree.add(2);
		tree.add(6);
		tree.add(7);
		tree.add(8);
		tree.add(9);
		tree.add(10);
		tree.add(11);
		tree.add(4);

		tree.printTree();


		tree.remove(6);
		tree.printTree();

		tree.remove(7);
		tree.printTree();

		tree.remove(9);
		tree.printTree();

		tree.remove(3);
		tree.printTree();

		tree.remove(14);
		tree.printTree();

		tree.remove(8);
		tree.remove(5);

		tree.printTree();

		tree.remove(4);
		tree.printTree();
		*/

		Random rand = new Random();
		for(int i = 0; i <= 100; i++){
			// int next = rand.nextInt(3000);
			int next = i;
			if(tree.add(next)){
				if(DEBUG)System.out.println("added " + next);
			}else{
				System.out.println(next + " already in tree");
			}
			// tree.printTree();
		}

		tree.printTree();


		for(int i = 0; i <= 100; i++){
			int next = rand.nextInt(100);
			// int next = i;
			if(DEBUG)System.out.println("removing " + next);
			if(tree.remove(next)){
				System.out.println("removed " + next);
				 tree.printTree();
				
			}else{
				System.out.println("no element " + next + "\n");
			}
			
			
		}

		tree.printTree();


		/*
		tree.add(45);
		tree.add(46);
		tree.add(47);
		tree.add(48);
		tree.add(50);
		tree.add(51);
		tree.add(52);
		tree.add(53);
		tree.add(54);
		tree.add(56);
		tree.add(22);
		tree.add(34);
		tree.add(89);
		tree.add(76);
		tree.add(33);
		tree.add(23);
		tree.add(24);
		tree.add(67);
		tree.add(68);
		tree.add(69);
		tree.add(70);
		tree.add(15);
		tree.add(16);
		tree.add(17);
		tree.add(18);
		tree.add(1);


		tree.printTree();



		tree.remove(69);
		tree.printTree();

		tree.remove(70);
		tree.printTree();

		tree.remove(76);
		tree.printTree();

		tree.remove(89);
		tree.printTree();

		tree.remove(53);
		tree.printTree();

		tree.remove(47);
		tree.printTree();




		/*
		System.out.println(tree.remove(12));
		tree.printTree();

		System.out.println(tree.remove(11));
		tree.printTree();

		System.out.println(tree.remove(8));
		tree.printTree();
		*/

		//tree.remove(6);
		//tree.printTree();



	}
}
