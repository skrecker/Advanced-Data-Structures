import java.util.Comparator;
import java.lang.Comparable;

/**
*@author EWenderholm
*/

public class NaturalComparator<T extends Comparable<T>> implements Comparator<T>{

	public int compare(T a, T b){
		return a.compareTo(b);
	}

	public boolean equals(Object obj){
		return (obj!=null) && (obj instanceof NaturalComparator); 
	}	

}
