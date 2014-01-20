package csc365.bst;

import java.lang.Comparable;
import java.util.Iterator;
import java.io.Serializable;
import java.util.Stack;

public class BSTPostIterator<E extends Comparable<E>> implements Iterator<E>, Serializable{

	public static final long serialVersionUID = 33L;

	private Stack<BTNode<E>> stack;

	private BTNode<E> root;

	/**
	* Constructs a new BSTPostIterator
	* @param root takes a Root node from a BST
	*/
	public BSTPostIterator(BTNode<E> root){
		this.root = root;
		stack = new Stack<BTNode<E>>();
		addRightStack(this.root);
	}

	/**
	* Checks to see if there is a next element in the iteration
	*@return returns true if there is a next element in the iteration, false otherwise
	*/
	public boolean hasNext(){
		return (!stack.empty());
	}

	/**
	* Get the next element in the iteration
	* @return returns the next element of type E in the iteration
	*/
	public E next(){
		BTNode<E> tempNode = stack.peek();
		if(tempNode.left() == null){
			return stack.pop().getElement();
		} else{
			E temp = stack.pop().getElement();
			addRightStack(tempNode.left());
			return temp;
		}
	}

	/**
	* Method not used from Iterator interface
	*/
	public void remove(){
		//method blank
	}

	private BTNode<E> getPeek(){
		return stack.peek();
	}

	// Recursively add right child of each node
	private void addRightStack(BTNode<E> node){
		if(node == null)
			return;
		stack.push(node);
		addRightStack(node.right());
	}


}