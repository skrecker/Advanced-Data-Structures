package csc365.bst;

import java.io.Serializable;
import java.lang.Comparable;

public class GraphicPoint<T extends Comparable<T>> implements Comparable<GraphicPoint<T>>, Serializable{
	
	public static final long serialVersionUID = 20L;

	int xCoord;
	int yCoord;
	T value;

	private boolean newGP;

	/**
	* Constructs a new GraphicPoint object with a generic T
	* @param v value of the GraphicPoint
	*/
	public GraphicPoint(T v){
		this(0,10,v);
	}

	/**
	* Constructs a new GraphicPoint object with a generic T, and an x and y coordinate.
	* @param x integer value of the x coordinate
	* @param y integer value of the y coordinate
	* @param v value of the GraphicPoint 
	*/
	public GraphicPoint(int x, int y, T v){
		xCoord = x;
		yCoord = y;
		value = v;
		newGP = true;
	}

	/**
	* @param that a GraphicPoint object being compared to
	* @return an int value using the T comparison 
	*/
	public int compareTo(GraphicPoint<T> that){
		return value.compareTo(that.value);
	}

	/**
	* Checks to see if the GraphicPoint was newly created
	* @return true if the GraphicPoint is new, otherwise return false
	*/
	public boolean isNew(){
		return newGP;
	}

	/**
	* Mark the GraphicPoint as no longer being newly created
	*/
	public void setOld(){
		newGP = false;
	}

	/**
	* Create a String representation of the GraphicPoint
	* @return String representation of the GraphicPoint 
	*/
	public String toString(){
		return value.toString();
	}
}