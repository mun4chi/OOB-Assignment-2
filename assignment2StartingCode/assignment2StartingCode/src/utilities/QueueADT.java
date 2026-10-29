package utilities;

import exceptions.EmptyQueueException;

/**
 * A Queue ADT interface used as the foundation for queue data structures
 * in CPRG 304 at SAIT.
 * <p>
 * This interface embodies all standard queue operations — enqueue, dequeue,
 * peek — as well as several helper methods that provide additional flexibility.
 * A queue follows a <strong>First-In, First-Out (FIFO)</strong> ordering
 * policy: the first element enqueued is the first one returned by a dequeue
 * or peek operation.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This interface is intentionally designed to work with {@link String} elements
 * only. A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code QueueADT<E>}), allowing any object type to be stored in the queue.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element parameters and return types are fixed as
 * {@link String} throughout this interface and its implementations.
 * </p>
 */
public interface QueueADT
{
    /**
     * Adds the specified {@link String} element to the end of this queue.
     * <p>
     * After this call, the enqueued element becomes the new tail of the queue.
     * It will be the last element returned relative to all elements currently
     * present. {@code null} values are not permitted.
     * </p>
     *
     * @param toAdd  the {@link String} element to add to the end of the queue;
     *               must not be {@code null}
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    public void enqueue( String toAdd ) throws NullPointerException;

    /**
     * Removes and returns the {@link String} element at the front of this queue.
     * <p>
     * The element that has been in the queue the longest is removed first,
     * consistent with FIFO ordering. The element directly behind the removed
     * front becomes the new head of the queue after this call.
     * </p>
     *
     * @return the {@link String} element removed from the front of the queue
     * @throws EmptyQueueException if the queue contains no elements
     */
    public String dequeue() throws EmptyQueueException;

    /**
     * Returns the {@link String} element at the front of this queue without
     * removing it.
     * <p>
     * The state of the queue is unchanged after this call.
     * </p>
     *
     * @return the {@link String} element currently at the front of the queue
     * @throws EmptyQueueException if the queue contains no elements
     */
    public String peek() throws EmptyQueueException;

    /**
     * Removes all elements from this queue, leaving it empty.
     * After this call, {@link #size()} returns {@code 0} and
     * {@link #isEmpty()} returns {@code true}.
     */
    public void dequeueAll();

    /**
     * Returns {@code true} if this queue contains no elements.
     *
     * @return {@code true} if {@link #size()} is {@code 0};
     *         {@code false} otherwise
     */
    public boolean isEmpty();

    /**
     * Returns {@code true} if this queue contains at least one occurrence
     * of the specified {@link String} element.
     * <p>
     * Equality is determined using {@link String#equals(Object)}.
     * </p>
     *
     * @param toFind  the {@link String} element to search for;
     *                must not be {@code null}
     * @return {@code true} if the element is present; {@code false} otherwise
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    public boolean contains( String toFind ) throws NullPointerException;

    /**
     * Returns the 1-based position of the specified {@link String} element
     * from the front of this queue.
     * <p>
     * The front element is at position {@code 1}, the element directly behind
     * it is at position {@code 2}, and so on. If the element appears more than
     * once, the position of the occurrence nearest the front is returned.
     * Equality is determined using {@link String#equals(Object)}.
     * </p>
     *
     * @param toFind  the {@link String} element to search for
     * @return the 1-based distance from the front of the queue to the nearest
     *         occurrence of the element, or {@code -1} if the element is not
     *         present in the queue
     */
    public int search( String toFind );

    /**
     * Returns an {@link Iterator} that traverses the {@link String} elements
     * of this queue in proper sequence, from front to back.
     *
     * @return an iterator over the {@link String} elements of this queue
     *         in front-to-back order
     */
    public Iterator iterator();

    /**
     * Compares this queue to the specified queue for equality.
     * <p>
     * Two queues are considered equal if and only if they contain the same
     * {@link String} elements appearing in the same order from front to back.
     * Equality of individual elements is determined using
     * {@link String#equals(Object)}.
     * </p>
     *
     * @param that  the {@link QueueADT} to compare to this queue;
     *              must not be {@code null}
     * @return {@code true} if both queues contain equal elements in the
     *         same order; {@code false} otherwise
     */
    public boolean equals( QueueADT that );

    /**
     * Copies all elements of this queue into the provided {@link String}
     * array in proper sequence, from front to back. If the provided array
     * is large enough, it is used directly; otherwise, a new array of the
     * same type and sufficient size is allocated and returned.
     *
     * @param holder  the {@link String} array into which the elements of
     *                this queue are to be stored; must not be {@code null}
     * @return a {@link String} array containing all elements of this queue
     *         in front-to-back order
     * @throws NullPointerException if {@code holder} is {@code null}
     */
    public String[] toArray( String[] holder ) throws NullPointerException;

     /**
     * Returns the number of elements currently in this queue.
     *
     * @return the current size of the queue; never negative
     */
    public int size();
}