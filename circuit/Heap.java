import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Heap<E extends Comparable<E>> implements PQ<E>{

	public static final boolean DEBUG = false;

	ArrayList<E> heap;

	Comparator<? super E> comparator;

	/**
	* Constructs a new Heap Object
	*/
	public Heap(){
		heap = new ArrayList<E>();
		comparator = new NaturalComparator<E>();

	}

	/**
	* Constructs a new Heap Object with a provided comparator
	*@param comparator comparator object used for comparison of objects
	*/
	public Heap(Comparator<? super E> comparator){
		heap = new ArrayList<E>();
		this.comparator = comparator;

	}

	/**
	* Get the number of elements in the heap
	* @return returns the number of elements in the heap
	*/
	public int size(){
		return heap.size();
	}

	/**
	* Return a boolean whether the heap is empty or not
	*@return return true if the heap is empty, false otherwise
	*/
	public boolean isEmpty(){
		return heap.isEmpty();
	}

	/**
	*@param element element to be added to the heap
	*@return true if the element can be added, false otherwise
	*@throws NullPointerException
	*/
	public boolean add(E element) throws NullPointerException{

		if(element == null) throw new NullPointerException("Null Pointer at element");
		
		heap.add(element); 						// add element to the end of the ArrayList
		percolateUp(element, size() - 1);		// percolate up until element is in the correct position in the heap
		return true;

	}

	/**
	* Remove the first element in the heap
	*@return true if the element can be added, false otherwise 
	*@param element element to be removed
	*@throws NullPointerException
	*/
	public boolean remove(E element) throws NullPointerException{

		if(element == null) throw new NullPointerException("Null Pointer at element");

		if(isEmpty()) return false;

		if(!heap.contains(element)) return false; // if the heap does not contain element return false

		int index = heap.indexOf(element);
		
		E temp = heap.get(size()-1);		//
		heap.remove(size() - 1);			// remove the element and replace it with the largest element in the heap
		heap.set(index, temp);				//
		percolateDown(temp, index); 		// then rearrange heap by percolating down
		return true;
		
	}

	/**
	*Clears the heap
	*/
	public void clear(){
		heap.clear();
	}

	/**
	* Get the element at the top of the heap
	*@return element at the top of the heap
	*/
	public E peek(){
		return heap.get(0); // returns the first element in the heap
	}

	/**
	* Get and remove the first element of the heap
	*@return the first element of the heap
	*/
	public E poll(){
		if(heap.size() <= 0) return null; 	
			// if the heap is empty return null
		E temp = peek();						
		heap.set(0, heap.get(size()-1));		// set last element in heap to the first element in the heap
		heap.remove(size()-1);					// remove the last element 
												
		if(!isEmpty()) percolateDown(heap.get(0), 0);			// rearrange heap by percolating down

		if(DEBUG) System.out.println("Poll: removed " + temp + "\n");
		return temp;
	}

	/**
	* Return a heap iterator
	*@return heap iterator
	*/
	public Iterator<E> iterator(){
		return heap.iterator();
	}


	//percolates the element up 
	private void percolateUp(E element, int pos){
		int parent = findParent(pos); // finds parent position in ArrayList

		if(parent > 0 && comparator.compare(element, heap.get(parent)) < 0){ // if element is less than parent, swap 
			E temp = heap.get(parent); 
			heap.set(parent,element);  
			heap.set(pos, temp);		
			percolateUp(element, parent); 	// recursively calls percolateUp unit element is in the correct position

		}
		
	}

	//percolate the element down
	private void percolateDown(E element, int pos){
		int left = findLeft(pos);
		int right = findRight(pos);

		if(left < size() && comparator.compare(element, heap.get(left)) > 0){ 
			E temp = heap.get(left);
			heap.set(left, element);				// swaps elements at current position  
			heap.set(pos, temp);				
			percolateDown(element, left);	// continues percolating down for the rest of the elements
			percolateDown(temp, pos);		// percolates again in case the new element is out of place

		}else {
			if(right < size() && comparator.compare(element, heap.get(right)) > 0){
				E temp = heap.get(right);
				heap.set(right, element);
				heap.set(pos, temp);
				percolateDown(element, right);	// continues percolating down for the rest of the elements
				percolateDown(temp, pos);		// percolates again in case the new element is out of place
			}
		}

	}

	

	private int findLeft(int pos){
		return (2 * pos) + 1;
	}

	private int findRight(int pos){
		return (2 * pos) + 2;
	}

	private int findParent(int pos){
		return (pos/2);
	}

	/*
	*Returns a string representation of the heap
	*@return string representation of the heap
	*/
	public String toString(){
		String temp = "Printing Heap";
		int count = 0 ;
		int scale = 1;
		for(E e: heap){
			
			if(count + 1 == scale){
				temp += "\n";
				scale *= 2;
			 }

			 temp += e + "\t";
			 count++;
		}
		return temp + "\n";
			
	}

}
