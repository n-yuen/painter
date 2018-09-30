package painter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *  A doubly linked list optimized for running speed for the Painter. Why
 *  did I spend so much effort commenting this?
 * 
 *  @author nyuen
 *
 *  @param <E> the type of object stored by this list
 */
public class LList<E> implements List<E> {
	
	/**
	 *  The first node in the list.
	 */
	private Nohd<E> start;
	
	/**
	 *  The last node in the list.
	 */
	private Nohd<E> end;
	
	/**
	 *  The size of the list (the number of elements it contains).
	 */
	private int size;

	public LList(){
		size = 0;
	}

	/**
	 * Returns the size (number of elements in this list).
	 */
	@Override
	public int size(){
		return size;
	}

	@Override
	public boolean isEmpty(){
		return start == null;
	}

	@Override
	public boolean contains(Object o){
		Nohd<E> current = start;
		while (!current.value().equals(o)){
			current = current.next();
		}
		return current == null;
	}

	@Override
	public Iterator<E> iterator(){
		return new It();
	}

	private class It implements Iterator<E>{
		private Nohd<E> current;

		public It(){
			current = start;
		}

		@Override
		public boolean hasNext(){
			if (isEmpty()){
				return false;
			}
			return current != null;
		}

		@Override
		public E next(){
			E value = current.value();
			current = current.next();
			return value;
		}
	}
	
	/**
	 * Returns an Iterator looping in reverse of the sequence.
	 * 
	 * @return an Iterator looping in reverse of the sequence.
	 */
	public Iterator<E> reversIterator(){
		return new reversIt();
	}

	private class reversIt implements Iterator<E>{
		private Nohd<E> current;
		private boolean remove;

		public reversIt(){
			current = end;
			remove = false;
		}

		@Override
		public boolean hasNext(){
			if (isEmpty()){
				return false;
			}
			return current != null;
		}

		@Override
		public E next(){
			E value = current.value();
			current = current.previous();
			remove = false;
			return value;
		}
		
		/**
		 *  Removes from the underlying collection the last element returned
		 *  by this iterator. This method can be called only once per call
		 *  to next. The behavior of an iterator is unspecified if the
		 *  underlying collection is modified while the iteration is in 
		 *  progress in any way other than by calling this method.
		 */
		@Override
		public void remove(){
			if (!remove){
				if (current == null){
					Nohd<E> newStart = start.next();
					newStart.setPrevious(null);
					start = newStart;
				}
				else{
					Nohd<E> next = current.next().next();
					if (next != null){
						next.setPrevious(current);
						current.setNext(next);
						remove = true;
					}
					else {
						current.setNext(null);
						end = current;
					}
				}
				size--;
			}
		}
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public Object[] toArray(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public <T> T[] toArray(T[] a){
		throw new UnsupportedOperationException();
	}

	/**
	 * Appends the specified element to the end of this list.
	 */
	@Override
	public boolean add(E e){
		Nohd<E> newThing = new Nohd<E>(e, end, null);
		if (start == null){
			start = newThing;
		}
		else if (end != null){
			if (start.equals(end)){
				start.setNext(newThing);
			}
			end.setNext(newThing);
		}
		end = newThing;
		size++;
		return true;
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean remove(Object o){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean containsAll(Collection<?> c){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean addAll(Collection<? extends E> c){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean addAll(int index, Collection<? extends E> c){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean removeAll(Collection<?> c){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public boolean retainAll(Collection<?> c){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Removes all of the elements from this list. The list will be empty
	 *  and <tt>size</tt> will be zero after this call returns.
	 */
	@Override
	public void clear(){
		start = null;
		end = null;
		size = 0;
	}

	/**
	 *  @throws IndexOutOfBoundsException if the
	 * index is out of range <tt>(index < 0 || index >= size)</tt>
	 */
	@Override
	public E get(int index){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		Nohd<E> current = start;
		for (int i=0; i<index; i++){
			current = current.next();
		}
		return current.value();
	}

	/**
	 *  Replaces the element at the specified position in this list with the
	 *  specified element.
	 * 
	 * 
	 *  @throws IndexOutOfBoundsException if the index is out of range 
	 *  <tt>(index < 0 || index >= size)</tt>
	 */
	@Override
	public E set(int index, E element){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		Nohd<E> current = start;
		for (int i=0; i<=index; i++){
			current = current.next();
		}
		current.setValue(element);
		return current.value();
	}

	/**
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * <tt>(index < 0 || index > size)</tt>
	 */
	@Override
	public void add(int index, E element){
		if (index > size || index < 0){
			throw new IndexOutOfBoundsException();
		}
		if (index == size){
			add(element);
			return;
		}
		Nohd<E> current = start;
		for (int i=0; i<=index; i++){
			current = current.next();
		}
		Nohd<E> newThing = new Nohd<E>(element, current, current.previous());
		current.setPrevious(newThing);
		current.previous().setNext(newThing);
		size++;
	}

	/**
	 *  Removes the element at the specified position in this list. Shifts
	 *  any subsequent elements to the left (subtracts one from their
	 *  indices). Runs from the end of the list to the beginning.
	 *
	 *  @throws IndexOutOfBoundsException if the index is out of range 
	 *  <tt>(index < 0 || index >= size)</tt>
	 */
	@Override
	public E remove(int index){
		if (index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		int count = size-1;
		Nohd<E> current = end;
		while (count != index){
			current = current.previous();
			count--;	
		}
		Nohd<E> next = current.next();
		Nohd<E> prev = current.previous();
		if (next == null){
			if (prev == null){
				start = null;
				end = null;
			}
			else {
				prev.setNext(null);
				end = prev;
			}
		}
		else if (prev == null){
			next.setPrevious(null);
			start = next;
		}
		else {
			next.setPrevious(prev);
			prev.setNext(next);
		}
		size--;
		return current.value();
	}

	@Override
	public int indexOf(Object o){
		Nohd<E> current = start;
		int count = 0;
		while (!current.value().equals(o)){
			current = current.next();
			if (current == null){
				return -1;
			}
			count++;
		}
		return count;
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public int lastIndexOf(Object o){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public ListIterator<E> listIterator(){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public ListIterator<E> listIterator(int index){
		throw new UnsupportedOperationException();
	}

	/**
	 *  Operation is not supported by LList
	 *  
	 *  @throws UnsupportedOperationException
	 */
	@Override
	public List<E> subList(int fromIndex, int toIndex){
		throw new UnsupportedOperationException();
	}

	//  The Node that makes up the LList.
	private class Nohd<T> {
		
		//  The element stored in the node.
		private T valuE;
		
		//  The node that immediately follows this node.
		private Nohd<T> next;
		
		//  The node that immediately precedes this node.
		private Nohd<T> prev;

		public Nohd(T e, Nohd<T> prev, Nohd<T> next){
			valuE = e;
			this.prev = prev;
			this.next = next;
		}

		public void setValue(T e){
			valuE = e;
		}

		public void setNext(Nohd<T> next){
			this.next = next;
		}

		public void setPrevious(Nohd<T> prev){
			this.prev = prev;
		}

		public T value(){
			return valuE;
		}

		public Nohd<T> next(){
			return next;
		}

		public Nohd<T> previous(){
			return prev;
		}
	}
}