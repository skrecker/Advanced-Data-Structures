import java.util.Iterator;

public interface PQ<E>{

	public int size();

	public boolean isEmpty();

	public boolean add(E element) throws NullPointerException;

	public void clear();

	public E peek();

	public E poll();

	public boolean remove(E element);

	public Iterator<E> iterator();

}
