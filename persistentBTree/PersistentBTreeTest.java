public class PersistentBTreeTest{

	public static void main(String[] args){


		PersistentBTree cache = new PersistentBTree();

		EBTNode<String> node = new EBTNode<String>("hello");
		node.addKey("Worldojeioqjrioewqjroefeaef1");
		node.addKey("This");
		node.addKey("is");
		node.addKey("a");
		node.addKey("test");
		node.addKey("Will");
		node.addKey("it");
		node.addLink(3000);
		node.addLink(4000);
		node.addLink(5000);
		node.addLink(6000);
		node.addLink(7000);
		node.addLink(5);
		node.addLink(6);
		node.addLink(7);

		EBTNode<String> nNode = cache.cacheTest(node);

		System.out.println(nNode);

	}

}