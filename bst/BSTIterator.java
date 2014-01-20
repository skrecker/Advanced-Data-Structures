package csc365.bst;

import java.util.Iterator;
import java.io.Serializable;
import java.util.Stack;

public class BSTIterator<E extends Comparable<E>> implements Iterator<E>, Serializable{

	public static final long serialVersionUID = 11L;

	private Stack<BTNode<E>> stack;

	private BTNode<E> root;


	/**
	* Constructs a new BSTIterator
	* @param root takes a Root node from a BST
	*/
	public BSTIterator(BTNode<E> root){
		this.root = root;
		stack = new Stack<BTNode<E>>();
		addLeftStack(this.root);
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
		if(tempNode.right() == null){
			return stack.pop().getElement();
		} else{
			E temp = stack.pop().getElement();
			addLeftStack(tempNode.right());
			return temp;
		}
	}

	/**
	* Method not overridden for Iterator interface
	*/
	public void remove(){
		//method blank
	}


	private BTNode<E> getPeek(){
		return stack.peek();
	}

	// Recursively add left child of each node
	private void addLeftStack(BTNode<E> node){
		if(node == null)
			return;
		stack.push(node);
		addLeftStack(node.left());
	}



}
