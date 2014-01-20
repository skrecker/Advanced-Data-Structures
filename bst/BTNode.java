package csc365.bst;

import java.io.Serializable;
/**
* BTNode for a binary search Tree
*
* <p> A binary search tree node holds an element of any generic type, a reference to a left node
* , and a right node
*
* @author Shawn Krecker
* @version 1.0
* 
*/

public class BTNode<E> implements Serializable{


	public static final long serialVersionUID = 22L;

	private BTNode<E> left, right;

	private E element;
	
	/**
	* BTNode constructor that takes no arguments
	*/
	public BTNode(){
		element = null;
		left = null;
		right = null;
	}

	/**
	* BTNode Constructor that takes a generic of type E
	* 
	* <p>This constructor takes an element
	* and assignes 
	*@param element element to be assigned to BTNode
	*/
	public BTNode(E element){
		this.element = element;
		this.left = null;
		this.right = null;
	}

	// Accessor Methods

	/**
	*@param element sets element of BTNode
	*/
	public void setElement(E element){
		this.element = element;
	}

	/**
	* @return returns the element of the BTNode
	*/
	public E getElement(){
		return element;
	}

	/**
	* @param node node to be set as left child
	*/
	public void setLeft(BTNode<E> node){
		this.left = node;
	}

	/**
	* @return returns the left child of the BTNode
	*/
	public BTNode<E> left(){
		return left;
	}
	/**
	* @param node node to be set as right child
	*/
	public void setRight(BTNode<E> node){
		this.right = node;
	}

	/**
	* @return returns the left child of the BTNode
	*/
	public BTNode<E> right(){
		return right;
	}

	protected boolean isLeaf(){
		return ((left() == null) && (right() == null));
	}

	/**
	* @return returns the string representation of the BTNode element
	*/
	@Override
	public String toString(){
		return element.toString();
	}

}
