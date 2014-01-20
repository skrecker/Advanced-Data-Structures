package csc365.bst;

import java.lang.Iterable;
import java.util.Iterator;
import java.lang.Comparable;
import java.util.Comparator;
import java.io.Serializable;
import java.lang.NullPointerException;

/** 
*@author Shawn Krecker
*/


public class BST<E extends Comparable<E>> implements Iterable<E>, Serializable{

	public static final long serialVersionUID = 10L;

	public static final boolean DEBUG = false; //flag to print out bebugging messages

	private BTNode<E> root;
	
	private Comparator<? super E> ordering;

	private Iterator<E> itr;

	private int size;


	/** 
	* Constructs a new BST object. 
	*/
	public BST(){
		this.root = null;
		size = 0;
		ordering = new NaturalComparator<E>();
		
	}

	/**
	* Constructs a new BST object with a provided Comparator.
	* as a parameter
	* @param comparator takes a Comparator Object to use for comparisons 
	*/
	public BST(Comparator<? super E> comparator){
		ordering = comparator;
		size = 0;
		this.root = null;
		
	}

	/**
	* Checks to see if the BST is empty
	* @return return true if BST is empty, else return false
	*/
	public boolean isEmpty(){
		return (root == null);
	}

	/**
	* Get the root of the BST
	* @return return the root of the BST 
	*/
	public BTNode<E> getRoot(){
		return root;
	}
	/**
	* Get the size of the BST
	* @return return the size of the BST
	*/
	public int getSize(){
		return size;
	}

	/**
	* @param element that will be searched for
	* @return true if BST contains the element, false otherwise
	* @throws NullPointerException
	*/
	public boolean contains(E element) throws NullPointerException{	
		
		if(element == null) throw new NullPointerException("input for get is null");

		if(isEmpty()) return false; 

		return recursiveContains(root, element);

	}


	// called by contains method
	// uses recurision to contains an return an element
	private boolean recursiveContains(BTNode<E> node, E element){
		E nodeElement = node.getElement();

		int comp = ordering.compare(element, nodeElement);

		if(comp == 0){ // Found BTNode
			return true;
		}else if(comp < 0){ //Continue recursion down left side of BTNode
			BTNode<E> left = node.left();
			if(left != null){
				return recursiveContains(left, element);
			}
		}else{ //Continue recursion down right side of BTNode
			BTNode<E> right = node.right();
			if(right != null){
				return recursiveContains(right, element);
			}
		}
		System.out.println("Not found");

		return false; // Element not found
	}	

	/** 
	* Insert an element into the BST
	* @param element inserts an element in proper order in the BST
	* @throws NullPointerException
	*/
	public boolean insert(E element) throws NullPointerException{

		if(element == null) throw new NullPointerException("Element is null");

		if(isEmpty()){ // If the BST is empty then create new node and Insert at root
			root = new BTNode<E>(element);
			if(DEBUG) System.out.println("Inserted element " + element + " at root"); 
			size++;
			return true;
		}

		return recursiveInsert(root, element); //

	}

	// called by insert
	// uses recursion to insert an element at proper orderk

	private boolean recursiveInsert(BTNode<E> node, E element){
		int comp = ordering.compare(element,node.getElement());

		if(comp == 0){
			
			return false;
		}else if(comp < 0){
			if(node.left() == null){
				if(DEBUG) System.out.println("Inserted " + element);
				node.setLeft(new BTNode<E>(element));
				size++;
				return true;
			} else{
				return recursiveInsert(node.left(), element);
			}

		}else{
			if(node.right() == null){
				if(DEBUG) System.out.println("Inserted " + element);
				node.setRight(new BTNode<E>(element));
				size++;
				return true;
			}else{
				return recursiveInsert(node.right(), element);
			}
		}

	}

	/**
	* Remove an element of the BST
	* @param element element that will be deleted from the BST
	* @return return true of the remove is successful, otherwise return false
	* @throws NullPointerException
	*/
	public boolean remove(E element) throws NullPointerException{

		if(element == null) throw new NullPointerException("Element null");

		if(root == null) throw new NullPointerException("Root is Null, Cannot remove from an empty tree");

		int comp = ordering.compare(element,root.getElement());

		if(comp == 0){
			return removeRoot();
		} else if(comp < 0){
			return removeRecursive(root.left(), root, element,true);
		} else{
			return removeRecursive(root.right(), root, element,false);
		}


	}

	//Recursively searches through the BST for a node to remove

	private boolean removeRecursive(BTNode<E> curNode, BTNode<E> parent, E element, boolean parentGreater){
		
		if(curNode == null){
			if(DEBUG) System.out.println("Element does not exist in the Tree");
			return false;
		}

		int comp = ordering.compare(element, curNode.getElement());

		if(comp == 0){
			if(DEBUG) System.out.println("removed " + curNode.getElement());

			if(parentGreater){
				if(curNode.isLeaf()){
					parent.setLeft(null);
					size--;
					return true;
				}

				if(curNode.left() == null){
					parent.setLeft(curNode.right());
					size--;
					return true;
				}

				if(curNode.right() == null){
					parent.setLeft(curNode.left());
					size--;
					return true;
				}

				parent.setLeft(curNode.left());
				BTNode<E> most = rightMost(curNode.left());
				most.setRight(curNode.right());
				size--;
				return true;


			}else{
				if(curNode.isLeaf()){
					parent.setRight(null);
					size--;
					return true;
				}

				if(curNode.left() == null){
					parent.setRight(curNode.right());
					size--;
					return true;
				}

				if(curNode.right() == null){
					parent.setRight(curNode.left());
					size--;
					return true;
				}

				parent.setRight(curNode.right());
				BTNode<E> least = leftMost(curNode.right());
				least.setLeft(curNode.left());
				size--;
				return true;

			}
		} else if(comp < 0){
			return removeRecursive(curNode.left(), curNode, element, true);

		}else{
			return removeRecursive(curNode.right(), curNode, element, false);
		}

	}

	private boolean removeRoot() throws NullPointerException{
		if(DEBUG) System.out.println("Removing root");

		if(isEmpty()) throw new NullPointerException("BST is empty");

		if(root.right() != null){
			if(root.left() == null){ //
				root = root.right();
				size--;
				return true;
			}else{
				BTNode<E> temp = leftMost(root.right());
				temp.setLeft(root.left());
				root = root.right();
				size--;
				return true;
			}
		}else if(root.left() != null){
			root = root.left();
			size--;
			return true;
		} else{
			root = null;
			size--;
			return true;
		}
	}

	//Finds the left most node, which is the element with the smallest value
	private BTNode<E> leftMost() throws NullPointerException{
		if(isEmpty()) throw new NullPointerException("BST empty");

		return leftMost(root);
	}

	//Called from leftMost()
	private BTNode<E> leftMost(BTNode<E> node){
		if(node.left() == null) {
			return node; 
		} else{
			return leftMost(node.left());
		}
	}

	//Find the right most node, which is the element with the greatest value
	private BTNode<E> rightMost() throws NullPointerException{
		if(isEmpty()) throw new NullPointerException("BST empty");

		return rightMost(root);
	}

	//Called from rightMost()
	private BTNode<E> rightMost(BTNode<E> node){
		if(node.right() == null){
			return node;
		} else{
			return rightMost(node.right());
		}
	}

	/**
	* Returns an inorder iterator of the BST
	* @return return an inorder BST Iterator
	*/
	public Iterator<E> iterator(){
			return (Iterator<E>) new BSTIterator<E>(getRoot());
		
	}

	/**
	* Returns a postorder Iterator of the BST
	* @return return a postorder BST Iterator
	*/
	public Iterator<E> postIterator(){
			return (Iterator<E>) new BSTPostIterator<E>(getRoot());	
	}

	/**
	* Erase the BST 
	**/
	public void clear(){
		root = null;
		size = 0;
	}

	/**
	* Returns a inorder string representation of the BST 
	* @return return a string representation of the BST
	*/
	public String toString(){
		String temp = "";
		for(E i: this){
			temp = i.toString() + " ";
		}
		return temp;
	}

	

	
	

}
