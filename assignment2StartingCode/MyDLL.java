package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import utilities.Iterator;
import utilities.ListADT;

/**
 * A doubly linked list (DLL) implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in a chain of {@link MyDLLNode}
 * objects, each of which holds a reference to both its predecessor
 * ({@code pred}) and its successor ({@code succ}). The list maintains explicit
 * {@code head} and {@code tail} references for O(1) access to both ends of the
 * chain. Insertions and removals at arbitrary positions require O(n) traversal.
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p>
 * <strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only. A
 * fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyDLL<E>}), allowing any object type to be stored in the list. Generic
 * programming is outside the scope of CPRG 304 and has therefore been omitted.
 * All element fields, parameters, and return types are fixed as {@link String}
 * throughout this class and its implementations.
 * </p>
 */
public class MyDLL implements ListADT {
	/** First node in the list or null if the list is empty */
	private MyDLLNode head;
	/**
	 * Last node in the list or null if the list is empty
	 */
	private MyDLLNode tail;
	/**
	 * The number of elements currently stored in the list
	 */
	private int size;

	/**
	 * Creates an empty list.
	 */
	public MyDLL() {
		head = null;
		tail = null;
		size = 0;

	}

	/**
	 * Returns the number of elements in the list.
	 * 
	 * @return the number of elements currently stored.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes all the elements from the list. The whole list will be empty after
	 * this method is called.
	 */
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Moves from the head to the node at the specified position
	 * 
	 * @param index the position of the node to be found.
	 * @return the node at that specific position.
	 */
	private MyDLLNode getNode(int index) {
		MyDLLNode curr = head;
		for (int i = 0; i < index; i++) {
			curr = curr.getSucc();
		}
		return curr;
	}

	/**
	 * Inserts specific element at a specified position in the list.
	 * 
	 * @param index the position to be insert at(0 to size)
	 * @param toAdd the element to be inserted
	 * @return true if the element was added.
	 * @throws NullPointerException      if toAdd is null.
	 * @throws IndexOutOfBoundsException if index is negative or greater than size.
	 *
	 */
	@Override
	public boolean add(int index, String toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null to the list");

		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size:" + size);
		}
		MyDLLNode newNode = new MyDLLNode(toAdd);

		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} else if (index == 0) {
			newNode.setSucc(head);
			head.setPred(newNode);
			head = newNode;
		} else if (index == size) {
			tail.setSucc(newNode);
			newNode.setPred(tail);
			tail = newNode;
		} else {
			MyDLLNode curr = getNode(index);
			MyDLLNode before = curr.getPred();
			before.setSucc(newNode);
			newNode.setPred(before);
			newNode.setSucc(curr);
			curr.setPred(newNode);
		}
		size++;
		return true;
	}

	/**
	 * Adds a specific element to the end of the list
	 * 
	 * @param toAdd the element to add.
	 * @return true if the element was added.
	 * @throws NullPointerException if toAdd is null.
	 */
	@Override
	public boolean add(String toAdd) throws NullPointerException {
		return add(size, toAdd);
	}

	/**
	 * Adds all elements of a specific list to the end of the current list in the
	 * same order.
	 * 
	 * @param toAdd the list whose elements are to be added
	 * @return true if the elements were added
	 * @throws NullPointerException if toAdd is null.
	 */
	@Override
	public boolean addAll(ListADT toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add a null list.");
		}
		int count = toAdd.size();
		for (int i = 0; i < count; i++) {
			add(toAdd.get(i));
		}
		return true;
	}

	/**
	 * Returns the element at a specified position in the list.
	 * 
	 * @param index the position of the element to be returned.
	 * @return the element at the specified position.
	 * @throws IndexOutOfBoundsException if the index is negative or not less than
	 *                                   size
	 */
	@Override
	public String get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return getNode(index).getElement();
	}

	/**
	 * Removes the element at a specific position in the list
	 * 
	 * @param index the position of the element to be removed.
	 * @return the element is removed.
	 * @throws IndexOutOfBoundsException if the index is negative or not less than
	 *                                   size.
	 */
	@Override
	public String remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		MyDLLNode toRemove = getNode(index);

		if (size == 1) {
			head = null;
			tail = null;
		} else if (index == 0) {
			head = head.getSucc();
			head.setPred(null);
		} else if (index == size - 1) {
			tail = tail.getPred();
			tail.setSucc(null);
		} else {
			MyDLLNode before = toRemove.getPred();
			MyDLLNode after = toRemove.getSucc();
			before.setSucc(after);
			after.setPred(before);
		}

		size--;
		return toRemove.getElement();
	}

	/**
	 * Removes the first occurrence of a specific element from the list.
	 * 
	 * @param toRemove the element to be removed
	 * @return the removed element or null if it is not found.
	 * @throws NullPointerException if toRemove is null.
	 */
	@Override
	public String remove(String toRemove) throws NullPointerException {
		int index = indexOf(toRemove);
		if (index == -1) {
			return null;
		}
		return remove(index);
	}

	/**
	 * Replaces the specified element at the specified position with a new element.
	 * 
	 * @param index    the position of the element to be replaced.
	 * @param toChange the new element to be stored
	 * @return the element previously at that position
	 * @throws NullPointerException      if toChange is null.
	 * @throws IndexOutOfBoundsException if index is negative or not less than the
	 *                                   size.
	 */
	@Override
	public String set(int index, String toChange) throws NullPointerException, IndexOutOfBoundsException {
		if (toChange == null) {
			throw new NullPointerException(" Cannot store a null element");
		}
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		MyDLLNode node = getNode(index);
		String old = node.getElement();
		node.setElement(toChange);
		return old;
	}

	/**
	 * Checks whether this list contains no elements
	 * 
	 * @return true if the list is empty or false if the list has elements.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Check whether if the list contains a specific element
	 * 
	 * @param toFind the element to be found.
	 * @return true if the element is in the list. false if not.
	 * @throws NullPointerException if toFind is null
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		return indexOf(toFind) != -1;

	}

	/**
	 * Returns the index of the first occurrence of the specified element
	 * 
	 * @param toFind the element to be found.
	 * @return the index of the first match or -1 if not found
	 * @throws NullPointerException if toFind is null.
	 */
	@Override
	public int indexOf(String toFind) throws NullPointerException {
		if (toFind == null) {
			throw new NullPointerException("Cannot find a null element");

		}
		MyDLLNode curr = head;
		int index = 0;
		while (curr != null) {
			if (toFind.equals(curr.getElement())) {
				return index;
			}
			curr = curr.getSucc();
			index++;
		}
		return -1;
	}

	/**
	 * Copies the elements of this list into an array. it will be in list order. If
	 * given array is large enough, it will be returned and filled. Or else a new
	 * array in the exact size of the list is created.
	 * 
	 * @param toHold the array to store the elements in it if it is large enough.
	 * @return an array containing all elements in the list
	 * @throws NullPointerException if toHold is null.
	 */
	@Override
	public String[] toArray(String[] toHold) throws NullPointerException {
		if (toHold == null) {
			throw new NullPointerException("Cannot copy to a null array.");
		}
		if (toHold.length < size) {
			toHold = Arrays.copyOf(toHold, size);
		}
		MyDLLNode curr = head;
		for (int i = 0; i < size; i++) {
			toHold[i] = curr.getElement();
			curr = curr.getSucc();
		}
		return toHold;
	}

	/**
	 * Returns the iterator over the elements in the this list in the list order.
	 * 
	 * @return a new iterator for this list.
	 */
	@Override
	public Iterator iterator() {
		return new DLLIterator();
	}

	/**
	 * A private inner class that iterates over a snapshot copy of the elements in
	 * this {@link MyDLL}. Later changes to the list do not affect it.
	 */
	private class DLLIterator implements Iterator {
		/** Copy of the list's elements taken when this iterator was created. */
		private String[] copyOfElements;

		/** Position of the next element to return. */
		private int pos;

		/**
		 * Creates an iterator by copying the current elements of the list, walking from
		 * head to tail.
		 */
		public DLLIterator() {
			copyOfElements = new String[size];
			MyDLLNode curr = head;
			for (int i = 0; i < size; i++) {
				copyOfElements[i] = curr.getElement();
				curr = curr.getSucc();
			}
			pos = 0;
		}

		/**
		 * Checks whether there are more elements to return.
		 *
		 * @return true if at least one more element remains, false otherwise
		 */
		@Override
		public boolean hasNext() {
			return pos < copyOfElements.length;
		}

		/**
		 * Returns the next element and moves the position forward by one.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException if there are no more elements
		 */
		@Override
		public String next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			String result = copyOfElements[pos];
			pos++;
			return result;
		}
	}
}
