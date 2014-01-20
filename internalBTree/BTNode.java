import java.util.ArrayList;
import java.util.Comparator;


public class BTNode<E extends Comparable<E>>{

	private int orderM = 8;

	private ArrayList<E> keys;

	private ArrayList<BTNode<E>> links;

	/**
	* Constructs a new BTNode
	*/
	public BTNode(){
		orderM = 8;
		keys = new ArrayList<E>();
		links = new ArrayList<BTNode<E>>();
	}

	/**
	* Constructs a new BTNode with a given element
	* @param element to be added to the newly created BTNode 
	*/
	public BTNode(E element){
		orderM = 8;
		keys = new ArrayList<E>();
		keys.add(element);
		links = new ArrayList<BTNode<E>>();
	}

	/**
	* Construct a new BTNode with a given element and order
	* @param element element to be added
	* @param order orderM of the BTNode
	*/
	public BTNode(E element, int order){
		orderM = order;
		keys = new ArrayList<E>();
		keys.add(element);
		links = new ArrayList<BTNode<E>>();
	}

	/**
	* Get a key value for a specific position
	* @param pos position of the key within the BTNode
	* @return element at position 
	*/
	public E getKey(int pos){
		return keys.get(pos);
	}

	/**
	* Get a link at a specific position
	* @param pos position of link to return
	* @return BTNode of the specific link position
	*/
	public BTNode<E> getLink(int pos){
		return links.get(pos);
	}

	/**
	* Returns the number of keys
	* @return size of the keys
	*/
	public int size(){
		return keys.size();
	}
	

	/**
	* Checks if the BTNode is full
	* @return true if BTNode is full, false otherwise
	*/
	public boolean isFull(){
		return (keys.size() >= (orderM -1));
	}

	public boolean isSplitNeeded(){
		return keys.size() >= orderM;
	}

	/**
	* Checks if the BTNode is empty
	* @return true if empty, false otherwise
	*/
	public boolean isEmpty(){
		return keys.isEmpty();
	}

	/**
	* Checks if the BTNode is a leaf node
	* @return true is node is a leaf, false otherwise
	*/
	public boolean isLeaf(){
		return (links.size() == 0);
	}

	/**
	* Returns the orderM of the BTNode
	* @return orderM
	*/
	public int order(){
		return orderM;
	}

	/**
	* Returns the middle key of the BTNode
	* @return middle key of the BTNode to be promoted
	*/
	public E promote(){
		return keys.remove((orderM-1)/2);
	}

	/**
	* Add a key of a specific element
	* @param element to be added
	* @return true if the key is added, false otherwise
	*/ 
	public boolean addKey(E element){	
		keys.add(element);
		return true;
	}

	/**
	* Add a key of a specific element and a specific position
	* @param element to be added
	* @param pos position to be added
	* @return true if the key is added, false otherwise
	*/
	public boolean addKey(E element, int pos){
		keys.add(pos,element);
		return true;
	}

	/**
	* Remove a key at a specific position
	* @param pos position to remove
	* @return element that was removed
	*/
	public E removeKey(int pos){
		return keys.remove(pos);
	}


	/**
	* Add a link at a specific position
	* @param node link to be added
	* @param pos position where link is added
	*/
	public void addLink(BTNode<E> node, int pos){
		links.add(pos, node);
	}

	public void addLink(BTNode<E> node){
		links.add(node);
	}

	/**
	* Remove Link at a specific position
	* @param pos position where link is to be removed
	* @return BTNode that was removed
	*/
	public BTNode<E> removeLink(int pos){
		return links.remove(pos);
	}

	/**
	* Returns a string representation of the BTNode
	* @return string representation of the BTNode
	*/
	public String toString(){
		return toString(0);
	}

	private String toString(int level){
		String string = "Keys at level " + level + " : ";


		for(E e: keys){
			string += e + " ";
			
		}	
		string +="\n";


		for(BTNode<E> node: links){
			string += node.toString(level + 1);
		}

		return string;
	}


}