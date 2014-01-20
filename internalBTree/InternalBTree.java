import java.util.Comparator;
import java.lang.Comparable;

public class InternalBTree<E extends Comparable<E>>{

	public static final boolean DEBUG = false;

	private BTNode<E> root;

	Comparator<? super E> comparator;

	private int orderM;

	private int minSize;

	private int splitSize;
	
	int size;

	int count = 0;

	/**
	* Construct an InternalBTree
	*/
	public InternalBTree(){
		size = 0;	
		orderM = 8;
		splitSize = (orderM - 1) / 2;
		minSize = (orderM /2) - 1;
		root = new BTNode<E>();
		comparator = new NaturalComparator<E>();
	}

	/**
	* Constructs an InternalBTree.
	* @param comparator Comparator object for comparisons 
	*/
	public InternalBTree(Comparator<? super E> comparator){
		orderM = 8;
		splitSize = (orderM - 1) / 2;
		minSize = (orderM /2) -1;
		root = new BTNode<E>();
		comparator = comparator;
	}


	public BTNode<E> getRoot(){return root;}

	/**
	* Add an element to the Internal BTree
	* @param element element to be added to the Internal BTree
	* @return true if element is added, otherwise false
	*/
	public boolean add(E element){

		if(DEBUG) System.out.println("Adding element: " + element);

		// root is null. create new node with element
		if(root == null){
			root = new BTNode<E>(element);
			return true;
		}

		if(root.isLeaf()){ //root is leaf
			if(root.isFull()){ // root is full
				splitLeafRoot();

				if(addRecursive(root, element)){
					return true;
				}else{
					return false;
				}


			}else{ // root is NOT full and is a leaf
				int i = 0;
				for(; i < root.size(); i++){
					if(comparator.compare(root.getKey(i), element) == 0){
						return false;
					}else if(comparator.compare(element, root.getKey(i)) < 0){
						root.addKey(element, i);
						return true;
					}
				}

				root.addKey(element, i); // add element to end of root
				return true;
			}

		}else{ // root is NOT leaf

			if(root.isFull()){ // root is full
				splitNonLeafRoot();

				if(addRecursive(root, element)){
					if(root.isFull()){
						splitNonLeafRoot();
					}
					return true;
				}else{
					return false;
				}
			}

			if(addRecursive(root, element)){
				if(root.isFull()){
					splitNonLeafRoot();
				}
				return true;
			}else{
				return false;
			}
		}

	}

	//called by add(E element)
	private boolean addRecursive(BTNode<E> node, E element){


		//find which leaf to follow 
		int i = 0;
		for(; i < node.size() ; i++){
			if(comparator.compare(element, node.getKey(i)) == 0) { // if the element is found return false
				return false;
			}

			if(comparator.compare(element, node.getKey(i)) < 0){ // find which leaf to follow and if full split first
				

				if(node.getLink(i).isLeaf()){
					if(node.getLink(i).isFull()){
						split(node,i);
						if(comparator.compare(element, node.getKey(i)) == 0) return false;

						if(comparator.compare(element, node.getKey(i)) < 0){
							if(addKey(node.getLink(i), element)){
								return true;
							}else{
								return false;
							}
						}else{
							if(addKey(node.getLink(i+1), element)){
								return true;

							}else{
								return false;
							}
						}

					}else{ //node is not full. can safely add
						if(addKey(node.getLink(i), element)){
							return true;
						}else{
							return false;
						}
					}
				}else{ // node is not leaf
					if(node.getLink(i).isFull()){
						split(node, i);

						if(comparator.compare(element, node.getKey(i)) == 0) return false;


						if(comparator.compare(element, node.getKey(i)) < 0){
							if(addRecursive(node.getLink(i), element)){
								return true;
							}else{
								return false;
							}
						}else{
							if(addRecursive(node.getLink(i+1), element)){
								return true;
							}else{
								return false;
							}
						}
					}else{

						if(addRecursive(node.getLink(i), element)){
							if(node.getLink(i).isFull()){
								split(node, i);
							}
							return true;
						}else{
							return false;
						}
					}
				}

		
			}
		}

		//add element to the last link
		if(node.getLink(i).isLeaf()){
			if(node.getLink(i).isFull()){
				split(node,i);
				
				if(comparator.compare(element, node.getKey(i)) == 0) return false;

				if(comparator.compare(element, node.getKey(i)) < 0){ // test where to add key from new nodes
					if(addKey(node.getLink(i), element)){
						return true;
					}else{
						return false;
					}
				}else{
					if(addKey(node.getLink(i + 1), element)){
						return true;
					}else{
						return false;
					}
				}
			}else{ // can safely add element
				if(addKey(node.getLink(i), element)){
					return true;
				}else{
					return false;
				}
			}
		}else{ // last node not a leaf
			if(addRecursive(node.getLink(i), element)){
				if(node.getLink(i).isFull()){
					split(node,i);
				}
				return true;
			}else{
				return false;
			}
		}

	} 	

	// iterates though the node to add element in the proper order
	private boolean addKey(BTNode<E> node, E element){
		int i = 0;
		for(; i < node.size(); i++ ){

			if(comparator.compare(element,node.getKey(i)) == 0){ // element found and is not added
				return false;

			}else if(comparator.compare(element,node.getKey(i)) < 0){ // element is added in 
				node.addKey(element,i);
				return true;
			}
		}
		node.addKey(element); // element is added to the end of the node
		return true;

	}

	private void split(BTNode<E> node, int pos){
		node.addKey(node.getLink(pos).promote(),pos);

		BTNode<E> left = node.getLink(pos);
		BTNode<E> right = new BTNode<E>();
		node.addLink(right, pos + 1);

		int i = 0;
		for(; i < splitSize; i++){
			right.addKey(left.removeKey(splitSize));
			if(!left.isLeaf()) {
				right.addLink(left.removeLink(splitSize +1));
			}
		}
		if(!left.isLeaf()) right.addLink(left.removeLink(splitSize + 1));

	}

	private void splitLeafRoot(){
		if(DEBUG) System.out.println("Root is Full");
		BTNode<E> temp = new BTNode<E>(root.promote());
		BTNode<E> leftLink = root;
		temp.addLink(leftLink);
					
		BTNode<E> linkRight = new BTNode<E>();
		temp.addLink(linkRight); 

		for(int i = 0; i < splitSize; i++){
			linkRight.addKey(leftLink.removeKey(splitSize));
		}

		root = temp;
	}

	private void splitNonLeafRoot(){
		if(DEBUG) System.out.println("Root is Full");
		BTNode<E> temp = new BTNode<E>(root.promote());
		BTNode<E> leftLink = root;
		temp.addLink(leftLink);
					
		BTNode<E> linkRight = new BTNode<E>();
		temp.addLink(linkRight);

		for(int i = 0; i < splitSize; i++){
			linkRight.addKey(leftLink.removeKey(splitSize));
			linkRight.addLink(leftLink.removeLink(splitSize + 1));				
		}
		linkRight.addLink(leftLink.removeLink(splitSize + 1));

		root = temp;
	}



	private E removeKey(BTNode<E> node, E element){
		for(int i = 0; i < node.size(); i++){
			if(comparator.compare(node.getKey(i), element) == 0){
				if(DEBUG) System.out.println("removeKey: " + element + " removed");
				return node.removeKey(i);
			}
		}
		return null;
	}



	//promotes the middle key for node, to then be split
	private E promote(BTNode<E> node){
		return node.removeKey(orderM / 2);
	}


	/**
	*	Removes a specific element if it exists
	* @param element element to be removed
	* @return true if element exists, false otherwise
	*/
	public boolean remove(E element){

		if(root == null || root.isEmpty()){
			return false;
		}

		if(root.isLeaf()){	// root is a leaf
			for(int i = 0; i < root.size(); i ++){
				if(comparator.compare(root.getKey(i), element) == 0){ // element found, can remove
					root.removeKey(i);
					if(root.size() == 0) root = null;
					return true;
				}

				
			}

		return false;	// root is a leaf and element NOT found 

		}else{	// root is NOT a leaf 
			for(int i = 0; i < root.size(); i ++){
				if(comparator.compare(root.getKey(i), element) == 0){ // element found, can remove
					E temp = stealPredeccessor(root.getLink(i));
					root.removeKey(i);

					root.addKey(temp,i);
	

					if(root.getLink(i).size() < minSize ){
						swapOrMerge(root, i);
					}

					return true;
					
				}
	
			}


			// element not in root. recursively remove
			if(remove(root, element)){
				
				if(root.isEmpty()){
					root = root.getLink(0);
					return true;
				}
				return true;
			}else{
				return false; // OVERWRITE		
			}	
		}


	}

	private boolean remove(BTNode<E> node, E element){

		// if(DEBUG) System.out.println(node)

		int i = 0;
		for(; i < node.size(); i++){
				if(comparator.compare(node.getKey(i), element) > 0){
					if(node.getLink(i).isLeaf()){ //node is a leaf
						if(node.getLink(i).size() >= minSize){ // link node is leaf and has enough elements

							E removedKey = removeKey(node.getLink(i), element);
							if(removedKey == null){
								return false;
							}else{
								if(node.getLink(i).size() < minSize){
									swapOrMerge(node, i);
								}
								return true;
							}

						}else{ // link node is leaf but does NOT have enough elements to remove
							E removedKey = removeKey(node.getLink(i), element);
							if(removedKey == null) return false;

							swapOrMerge(node, i);
							return true;
						}
					}else{ // link is not a leaf
						//remove(root.getKey(i), element, level + 1);
						boolean removedKey = removeInteriorKey(node.getLink(i), element);
						if(!removedKey){
							if(remove(node.getLink(i), element)){
								if(node.getLink(i).size() < minSize){
									swapOrMerge(node, i);
								}
								return true;
							}else{
								return false;
							}
						}

						if(node.getLink(i).size() < minSize){
							swapOrMerge(node,i);
						}

						return true;
						
					}				
				}
			}

			// checking last link
			if(node.getLink(i).isLeaf()){
						if(node.getLink(i).size() >= minSize){ // link node is leaf and has enough elements

							E removedKey = removeKey(node.getLink(i), element);
							if(removedKey == null){
								return false;
							}else{
								if(node.getLink(i).size() < minSize){
									swapOrMergeLast(node, i);
								}

								return true;
							} 

						}else{ // link node is leaf but does NOT have enough elements to remove
							E removedKey = removeKey(node.getLink(i), element);
							if(removedKey == null) return false;
							swapOrMergeLast(node, i);
							return true;
						}
					}else{ // link is not a leaf

						boolean removedKey = removeInteriorKey(node.getLink(i), element);
						if(!removedKey){ // link does not contain element
							if(remove(node.getLink(i), element)){
								if(node.getLink(i).size() < minSize){
									swapOrMergeLast(node, i);
								}
								return true;
							}else{
								return false;
							}
						}else{
							if(node.getLink(i).size() < minSize){
							swapOrMergeLast(node,i);
						}
							return true;
						}

						
					}


			// return false;
	}

	private boolean removeInteriorKey(BTNode<E> node, E element){
		for(int i = 0; i < node.size(); i++){
			if(comparator.compare(node.getKey(i), element) == 0){
				node.removeKey(i);
				node.addKey(stealPredeccessor(node.getLink(i)),i);

				if(node.getLink(i).size() < minSize){	
					swapOrMerge(node, i);
				
				}

				return true;
			}
		} return false;
	}

	private E stealSuccessor(BTNode<E> node){


		if(node.isLeaf()){
			return node.removeKey(0); 
		}else{
			E temp = stealSuccessor(node.getLink(0));
			if(node.getLink(0).size() < minSize){
				swapOrMerge(node,0);
			}
			return temp;
		}

	}

	private E stealPredeccessor(BTNode<E> node){
		if(DEBUG) System.out.println("Stealing stealPredeccessor");
		
		if(node.isLeaf()){
			return node.removeKey(node.size()-1);
		}else{
			E temp = stealPredeccessor(node.getLink(node.size()));
			if(node.getLink(node.size()).size() < minSize){
				swapOrMergeLast(node, node.size());
			}
			return temp;
		}
	}


	private boolean swapGreaterSibling(BTNode<E> node, int pos){

		if(node.size() == pos){
			return false;
		}


		BTNode<E> temp = node.getLink(pos + 1);
		if(temp.size() > minSize){
			if(temp.isLeaf()){ // sibling is a leaf
				E stealValue = temp.removeKey(0);

				addKey(node.getLink(pos), node.removeKey(pos));
				node.addKey(stealValue, pos);
				return true;

			}else{ //sibling is NOT a leaf
				E stealValue = temp.removeKey(0);
				BTNode<E> stealNode = temp.removeLink(0); // temp link
				node.getLink(pos).addKey(node.removeKey(pos));
				node.addKey(stealValue, pos);
				node.getLink(pos).addLink(stealNode, node.getLink(pos).size());
				return true;
			}
			
		}else{
			return false; // can't steal, remove null
		}


	}

	private boolean swapLesserSibling(BTNode<E> node, int pos){
		if(pos < 1) return false; 
		BTNode<E> temp = node.getLink(pos - 1);
		if(temp.size() > minSize){ // can safely steal from the lesser sibling
			if(temp.isLeaf()){
				E stealValue = temp.removeKey(temp.size() - 1);
				addKey(node.getLink(pos), node.removeKey(pos - 1));
				node.addKey(stealValue, pos - 1);
				return true;
			}else{ //sibling is NOT a leaf, also steal link 
				E stealValue = temp.removeKey(temp.size() - 1);
				BTNode<E> stealNode = temp.removeLink(temp.size() + 1);
				addKey(node.getLink(pos), node.removeKey(pos - 1));
				node.addKey(stealValue, pos - 1);
				node.getLink(pos).addLink(stealNode, 0);
				return true;
			}
		}else{
			return false; // can't steal from, node has too few elements
		}

	}

	private void merge(BTNode<E> node, int pos){
		
		if(DEBUG) System.out.println("TESTING MERGE");

		BTNode<E> left = node.getLink(pos);
		if(DEBUG) System.out.println("left: " + left);

		BTNode<E> right = node.getLink(pos+1);
		if(DEBUG) System.out.println("right: " + right);

		E r = node.removeKey(pos);
		if(DEBUG) System.out.println("node remove " + r);


		left.addKey(r);
		if(DEBUG) System.out.println("left w/ node add: " + left);

		if(!right.isLeaf()){
			left.addLink(right.removeLink(0));
		}

		for(int i = right.size(); i > 0; i--){
			left.addKey(right.removeKey(0));
			if(!right.isLeaf()){
				left.addLink(right.removeLink(0));
			}
			if(DEBUG) System.out.println("right: " + right);
		}
		
		node.removeLink(pos + 1);

		if(DEBUG) System.out.println("Node is now " +  node);


	}

	private void mergeLeft(BTNode<E> node, int pos){
		BTNode<E> right = node.removeLink(pos);
		BTNode<E> left = node.getLink(node.size() - 1);
		left.addKey(node.removeKey(pos));
		while(right.size() != 0){
			left.addKey(right.removeKey(0));
		}

	}

	private boolean swapOrMerge(BTNode<E> node, int pos){
		if(swapLesserSibling(node, pos)){
			if(DEBUG) System.out.println("swapOrMerge: Swapped Lesser Sibling");
			return true;

		}else if(swapGreaterSibling(node, pos)){
			if(DEBUG) System.out.println("swapOrMerge: Swapped Greater Sibling");
			return true;

		}else{ //cannot swap either siblings, MERGE								
			merge(node, pos);
			if(DEBUG) System.out.println("swapOrMerge: Merged");
			return true;
		}		
	}

	private boolean swapOrMergeLast(BTNode<E> node, int pos){
		 if(swapLesserSibling(node, pos)){
			if(DEBUG) System.out.println("swapOrMergeLAST: Swapped Lesser Sibling");
			return true;

		}else{ //cannot swap sibling, MERGE!								
			merge(node, pos-1);
			if(DEBUG) System.out.println("swapOrMergeLAST: Merged");
			return true;
		}		
	}



	

	/**
	* Print a string representation to standard output 
	*/
	public void printTree(){
		System.out.println(root);
	}

	private String printTree(BTNode<E> node){
		if(root == null) return " ";	
		return node.toString();
	}

	/**
	* String representation of the InternalBTree
	* @return string representation of the InternalBTree
	*/
	public String toString(){
		return printTree(root);
	}

	


}