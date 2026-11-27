package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;

/**
 * A doubly linked list-backed implementation of the {@link QueueADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyDLL}, treating index
 * {@code 0} as the front of the queue and the last index as the back.
 * {@link #enqueue(String)} appends to the back; {@link #dequeue()} and
 * {@link #peek()} access the front. A queue follows a <strong>First-In,
 * First-Out (FIFO)</strong> ordering policy: the first element enqueued is the
 * first one returned.
 * </p>
 *
 * <p>
 * <strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only. A
 * fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyQueue<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore been
 * omitted. All element fields, parameters, and return types are fixed as
 * {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyQueue implements QueueADT {
	/**
	 * The list that stores the queue's elements; the head of the list is the front
	 * of the queue.
	 */
	private MyDLL elements;

	/**
	 * creates an empty queue
	 */
	public MyQueue() {
		elements = new MyDLL();
	}

	/**
	 * Adds an element to the back of the queue.
	 * 
	 * @param toAdd the element to add
	 * @throws NullPointerException if toAdd is null.
	 */
	@Override
	public void enqueue(String toAdd) throws NullPointerException {
		if (toAdd == null) {
			throw new NullPointerException("Cannot add null to the queue");
		}
		elements.add(toAdd);
	}

	/**
	 * Removes and returns the element at the front of the queue
	 * 
	 * @return the element removed form the front.
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public String dequeue() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException("the queue is empty");
		}
		return elements.remove(0);
	}

	/**
	 * Returns the element at the front of the queue without removing it.
	 * 
	 * @return the element at the front
	 * @throws EmptyQueueException if the queue is empty
	 */
	@Override
	public String peek() throws EmptyQueueException {
		if (isEmpty()) {
			throw new EmptyQueueException(" The queue is empty");
		}
		return elements.get(0);

	}

	/**
	 * Removes all elements in the queue
	 */
	@Override
	public void dequeueAll() {
		elements.clear();
	}

	/**
	 * Checks whether the queue contains no elements.
	 * 
	 * @return true if the queue is empty, false if not.
	 */
	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * Check the whether queue contains specified element
	 * 
	 * @return true if the element is in the queue, false if not
	 * @param toFind the element to be found 
	 * @throws NullPointerException if toFind is null.
	 */
	@Override
	public boolean contains(String toFind) throws NullPointerException {
		return elements.contains(toFind);
	}

	/**
	 * Returns the 1 based position of the specified element counting from the front
	 * of the queue.
	 * 
	 * @param toFind the element to be found
	 * @return the position from the front(front = 1) or -1 if not found.
	 */
	@Override
	public int search(String toFind) {
		if (toFind == null) {
			return -1;
		}
		int index = elements.indexOf(toFind);
		if (index == -1) {
			return -1;
		}
		return index + 1;
	}

	/**
	 * Returns an iterator over the elements in the queue from front to back order
	 * 
	 * @return a new iterator for this queue.
	 */
	@Override
	public Iterator iterator() {
		return elements.iterator();
	}

	/**
	 * Compares the queue with another queue. they will be considered equal if the
	 * compared queue has the same elements in the same order.
	 * 
	 * @param that the queue to be compared with
	 * @return true if both the queues are the same
	 */
	@Override
	public boolean equals(QueueADT that) {
		if (that == null || size() != that.size()) {
			return false;
		}
		Iterator first = iterator();
		Iterator second = that.iterator();
		while (first.hasNext()) {
			if (!first.next().equals(second.next())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Copies the element of the queue into an array from the front to back
	 * 
	 * @param holder the array to store the elements if it is large enough
	 * @return an array containing all the elements in the queue.
	 * @throws NullPointerException if holder is null.
	 */
	@Override
	public String[] toArray(String[] holder) throws NullPointerException {
		return elements.toArray(holder);
	}

	/**
	 * Returns the number of elements in the queue,
	 * 
	 * @return the number of elements currently stored in the queue.
	 */
	@Override
	public int size() {
		return elements.size();
	}

}
