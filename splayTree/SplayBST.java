import java.util.Comparator;
import java.lang.Comparable;
import java.util.Queue;
import java.util.ArrayList;
import java.lang.NullPointerException;

public class SplayBST<E extends Comparable<E>> {
    public static final boolean DEBUG = false;

    private Node<E> root;
    private int size;

    Comparator<? super E> comparator;

    /**
    *Constructs a new SplayBST object
    */
    public SplayBST() {
  		root = null;
  		size = 0;
  		comparator = new NaturalComparator<E>();
    }

    /**
    *Constructs a new SplayBST object with a provided comparator
    *@param comparator Comparator oject
    */
    public SplayBST(Comparator<? super E> comparator){
    	root = null;
    	size = 0;
    	this.comparator = comparator;
    }

    /**
    *Returns the size of the splay tree
    *@return returns the size of the splay tree
    */
    public int size(){
      return size;
    }

    /**
    *Add a new element to the splay tree then bring the element to the top of the tree
    *@param element to be added 
    */
    public void add(E element) {
      root = splayInsert(root,element);
      size++;
    }


 
	private Node<E> splayInsert(Node<E> node, E element) {
		if (node==null) return new Node<E>(element);

    //element must be added to left subtree
		if (comparator.compare(element,node.value) < 0) {

        //node.left is a leaf node
		    if (node.left == null) {
  				node.left = new Node<E>(element);
  				return rotateRight(node);
		    }

		    if (comparator.compare(element, node.left.value) < 0) { //splay left-left
  				node.left.left = splayInsert(node.left.left,element);
  				node = rotateRight(node); 
		    } else { // splay left-right
  				node.left.right = splayInsert(node.left.right,element);
  				node.left  = rotateLeft(node.left);
		    }
		    return rotateRight(node);

		} else { // element must be added to right subtree

        // node.right is a leaf node 
		    if (node.right == null) {
  				node.right = new Node<E>(element);
  				return rotateLeft(node);
		    }

		    if (comparator.compare(element,node.right.value) > 0) { // splay right-right

  				node.right.right = splayInsert(node.right.right,element);
  				node = rotateLeft(node);

		    }else { // splay right-left
  				node.right.left = splayInsert(node.right.left,element);
  				node.right = rotateRight(node.right);
		    }
		    return rotateLeft(node);
		}
	}

  /**
    *Searches for an element in the splay tree then brings that element to the top of the tree. Returns the 
    *@return returns a node if the element exist, null otherwise
    *@param element searches for element
    */
  public Node<E> search(E element){

    //element is at the root. no splay needed
    if(comparator.compare(element, root.value) == 0){
      return root;
    }

    Node<E> temp = search(root, element);
    if(temp != null){
      return root = temp;
    }else{
      System.out.println("Element not found");
      return null;
    }


  }

  //Called by search to recursively seach for an element
  private Node<E> search(Node<E> node, E element) {

    //node is a leaf and search failed
     if (node.left == null && node.right == null) return node;

        if(comparator.compare(element,node.value) == 0){ // node found
          return node;

        }else if (comparator.compare(element,node.value) < 0) { // search left subtree

          if(node.left == null) return null;

            if (comparator.compare(element, node.left.value) == 0) { // node found
              return rotateRight(node);

            }else if (comparator.compare(element, node.left.value) < 0) { //search left-left
              if(node.left.left == null) return null;

              node.left.left = search(node.left.left,element);
              node = rotateRight(node); 

            } else { //search left-right

              node.left.right = search(node.left.right,element);
              node.left  = rotateLeft(node.left);
            }
            return rotateRight(node);

        } else { //search right subtree
            if(node.right == null) return null;

            if (comparator.compare(element, node.right.value) == 0) { // node found
              return rotateLeft(node);

            }else if (comparator.compare(element,node.right.value) > 0) { //search right-right

              node.right.right = search(node.right.right,element);
              node = rotateLeft(node);

            }else { // search right-left

              node.right.left = search(node.right.left,element);
              node.right = rotateRight(node.right);
            }
            
              return rotateLeft(node);
            
        }
	
  }


    //Rotates the tree left about the node
    private Node<E> rotateLeft(Node<E> node) {
  		Node<E> element = node.right;
     //if(node.right == null) return null;
  		node.right = element.left;
  		element.left = node;
  		return element;
    }
    
    //Rotates the tree right about the node
    private Node<E> rotateRight(Node<E> node) {
  		Node<E> element = node.left;
     // if(node.left == null) return null;
  		node.left = element.right;
  		element.right = node;
  		return element;
    }

    /**
    *Prints a textual representation of the splaytree
    */
    public void printTreeToText(){
      if(root != null) printTreeToText(root, 1);
    }

    //Called by printTreeToText() to recursively print the elements in the splay tree
    private void printTreeToText(Node<E> node, int level){
      System.out.println("Node: "+ node.value + "\tLevel: " + level);

      if(node.left != null){
        System.out.println("\tLeft: " + node.left.value); 
      }
      if(node.right != null){
        System.out.println("\tRight: " + node.right.value);

      }
      if(node.left != null) printTreeToText(node.left, level + 1);
      if(node.right != null) printTreeToText(node.right, level + 1); 
    }

    private void printTree(){	
      if(root == null) return;

      printRecursive(root);

    }

    //Poorrly implemented printing of the tree
    //translated code into java from http://www.ardendertat.com/2011/12/05/programming-interview-questions-20-tree-level-order-print/
    private void printRecursive(Node<E> node){

      //Uses an arraylist for a queue for printing the splay tree
      ArrayList<Node<E>> array = new ArrayList<Node<E>>();
      array.add(node);

      int currentCount = 1;
      int nextCount = 0;
      int spacingCounter = 40;
      int totalCount = 0;

      while(array.size()!= 0){

        if(totalCount >= size()) {
          System.out.println();
          if(DEBUG) System.out.println("Total count: " + totalCount);
          return;
        }
        Node<E> n = array.remove(0);
        currentCount -= 1;


        if(n.value != null){
          for(int i = 0; i < spacingCounter; i++) System.out.print(" "); //for loop for space formatting between nodes
          
          System.out.print(n.value);
          totalCount++;

          for (int i = 0; i < (spacingCounter/2) ; i++ ) System.out.print(" "); //for loop for space formatting between nodes

        }else{
          
          for(int i = 0; i < spacingCounter; i++) System.out.print(" ");

          System.out.print(" o ");

          for (int i = 0; i < (spacingCounter/2) ; i++ ) System.out.print(" "); //for loop for space formatting between nodes
          

        }
      
       
        if(n.left != null){
          array.add(n.left);
          nextCount++;
        }else if(currentCount != 0){
         array.add(new Node<E>(null));
         nextCount++;
        }

        if(n.right != null){
          array.add(n.right);
          nextCount++;
        }else if(currentCount != 0){
          array.add(new Node<E>(null));
          nextCount++;
        }

        if(currentCount <= 0){

           System.out.println();
           if(spacingCounter > 2) spacingCounter /=2;

            int temp = currentCount;
            currentCount = nextCount;
            nextCount = temp;
        }
      }
      
    }

    private class Node<E>{
  		Node<E> left;
  		Node<E> right;
  		E value;
  	
  		public Node(E element) { 
  		    left = null;
  		    right = null;
  		    value = element;
  		}


  		public String toString(){
  			return value.toString();
  		}
    }
  
}
