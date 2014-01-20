public class PBTreeTest{

	public static void main(String[] args){

		PersistentBTree pTree = new PersistentBTree();
		pTree.add(new String("hello"));
		pTree.add(new String("world"));
		pTree.add(new String("i"));
		pTree.add(new String("hope"));
		pTree.add(new String("it"));
		pTree.add(new String("works"));
		pTree.add(new String("may"));
		pTree.add(new String("be"));
		pTree.add(new String("Wei"));
		pTree.add(new String("Wu"));
		pTree.add(new String("Yuh"));
		pTree.add(new String("Zan"));
		pTree.add(new String("Zen"));
		pTree.add(new String("Zoe"));	
		pTree.add(new String("ace"));

		pTree.add(new String("hello"));
		pTree.add(new String("world"));
		pTree.add(new String("i"));
		pTree.add(new String("hope"));
		pTree.add(new String("it"));
		pTree.add(new String("works"));
		pTree.add(new String("may"));
		pTree.add(new String("be"));
		pTree.add(new String("Wei"));
		pTree.add(new String("Wu"));
		pTree.add(new String("Yuh"));
		pTree.add(new String("Zan"));
		pTree.add(new String("Zen"));
		pTree.add(new String("Zoe"));	
		pTree.add(new String("ace"));


		pTree.save();
		// pTree.add(new String("world"));
		// pTree.add(new String("works"));

		// pTree.add(new String("world"));
		// pTree.add(new String("I"));
		// pTree.add(new String("am"));
		// pTree.add(new String("ready"));
		// pTree.add(new String("to"));
		// pTree.add(new String("work"));
		// pTree.add(new String("are"));
		// pTree.add(new String("you"));

		// System.out.println("add worked");

		// EBTNode<String> node = pTree.getNodeFromBytes(0);
		// System.out.println(node);

		// pTree.save();


	}


}