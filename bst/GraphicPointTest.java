package csc365.bst;

public class GraphicPointTest{

	public static void main(String[] args){

		BST<GraphicPoint<Integer>> bst = new BST<GraphicPoint<Integer>>();

		bst.insert(new GraphicPoint<Integer>(new Integer(51)));
		bst.insert(new GraphicPoint<Integer>(new Integer(100)));
		bst.insert(new GraphicPoint<Integer>(new Integer(50)));
		bst.insert(new GraphicPoint<Integer>(new Integer(44)));
		bst.insert(new GraphicPoint<Integer>(new Integer(35)));
		bst.insert(new GraphicPoint<Integer>(new Integer(10)));
		bst.insert(new GraphicPoint<Integer>(new Integer(67)));
		bst.insert(new GraphicPoint<Integer>(new Integer(11)));
		bst.insert(new GraphicPoint<Integer>(new Integer(88)));
		bst.insert(new GraphicPoint<Integer>(new Integer(45)));
		bst.insert(new GraphicPoint<Integer>(new Integer(47)));
		bst.insert(new GraphicPoint<Integer>(new Integer(46)));


		bst.remove(new GraphicPoint<Integer>(new Integer(51)));
	//	bst.remove(44);

		bst.insert(new GraphicPoint<Integer>(new Integer(43)));
		bst.insert(new GraphicPoint<Integer>(new Integer(62)));
		bst.insert(new GraphicPoint<Integer>(new Integer(1)));
		bst.insert(new GraphicPoint<Integer>(new Integer(23)));
		bst.insert(new GraphicPoint<Integer>(new Integer(0)));
		bst.insert(new GraphicPoint<Integer>(new Integer(9)));

		System.out.println(bst);


	}
}