package utilities;

/**
 * A Linear List ADT interface used as the foundation for linear data
 * structures in CPRG 304 at SAIT.
 * <p>
 * Implementing classes must define standard list operations such as insertion,
 * deletion, access, search, and iteration. All index parameters are
 * zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This interface is intentionally designed to work with {@link String} elements
 * only. A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code ListADT<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element parameters and return types are fixed as
 * {@link String} throughout this interface and its implementations.
 * </p>
 */
public interface ListADT
{
    /**
     * Returns the number of elements currently in the list.
     *
     * @return the current size of the list; never negative
     */
    public int size();

    /**
     * Removes all elements from the list, leaving it empty.
     * After this call, {@link #size()} returns {@code 0}.
     */
    public void clear();

    /**
     * Inserts the specified {@link String} element at the given index, shifting
     * all existing elements at or beyond that position one place to the right.
     *
     * @param index  the zero-based position at which to insert the element;
     *               valid range is {@code [0, size()]}
     * @param toAdd  the {@link String} element to insert; must not be {@code null}
     * @return {@code true} if the insertion was successful
     * @throws NullPointerException      if {@code toAdd} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > size()}
     */
    public boolean add( int index, String toAdd ) throws NullPointerException, IndexOutOfBoundsException;

    /**
     * Appends the specified {@link String} element to the end of the list.
     *
     * @param toAdd  the {@link String} element to append; must not be {@code null}
     * @return {@code true} if the element was successfully added
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    public boolean add( String toAdd ) throws NullPointerException;

    /**
     * Appends all {@link String} elements from the specified list to the end of
     * this list, in the order they are returned by the provided list's iterator.
     *
     * @param toAdd  the list whose {@link String} elements are to be appended;
     *               must not be {@code null}
     * @return {@code true} if this list changed as a result of the call
     * @throws NullPointerException if {@code toAdd} is {@code null} or contains
     *                              {@code null} elements that are not supported
     */
    public boolean addAll( ListADT toAdd ) throws NullPointerException;

    /**
     * Returns the {@link String} element at the specified position in the list.
     *
     * @param index  the zero-based index of the element to retrieve;
     *               valid range is {@code [0, size() - 1]}
     * @return the {@link String} element stored at the given index
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= size()}
     */
    public String get( int index ) throws IndexOutOfBoundsException;

    /**
     * Removes and returns the {@link String} element at the specified position,
     * shifting all subsequent elements one place to the left.
     *
     * @param index  the zero-based index of the element to remove;
     *               valid range is {@code [0, size() - 1]}
     * @return the {@link String} element that was removed from the list
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= size()}
     */
    public String remove( int index ) throws IndexOutOfBoundsException;

    /**
     * Removes and returns the first occurrence of the specified {@link String}
     * element from the list, if it is present. Subsequent elements are shifted
     * left by one.
     *
     * @param toRemove  the {@link String} element to remove; must not be {@code null}
     * @return the removed {@link String} element, or {@code null} if not found
     * @throws NullPointerException if {@code toRemove} is {@code null}
     */
    public String remove( String toRemove ) throws NullPointerException;

    /**
     * Replaces the element at the specified position with the given
     * {@link String} element and returns the element previously stored there.
     *
     * @param index     the zero-based index of the element to replace;
     *                  valid range is {@code [0, size() - 1]}
     * @param toChange  the new {@link String} element to store at the position;
     *                  must not be {@code null}
     * @return the {@link String} element previously stored at the specified index
     * @throws NullPointerException      if {@code toChange} is {@code null}
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= size()}
     */
    public String set( int index, String toChange ) throws NullPointerException, IndexOutOfBoundsException;

    /**
     * Returns {@code true} if the list contains no elements.
     *
     * @return {@code true} if {@link #size()} is {@code 0}; {@code false} otherwise
     */
    public boolean isEmpty();

    /**
     * Returns {@code true} if the list contains at least one occurrence of
     * the specified {@link String} element.
     *
     * @param toFind  the {@link String} element to search for; must not be {@code null}
     * @return {@code true} if the element is found; {@code false} otherwise
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    public boolean contains( String toFind ) throws NullPointerException;

    /**
     * Returns the zero-based index of the first occurrence of the specified
     * {@link String} element in this list.
     * <p>
     * If the element is not found, this method returns {@code -1}.
     * </p>
     *
     * @param toFind  the {@link String} element to search for; must not be {@code null}
     * @return the index of the first matching element, or {@code -1} if the
     *         element is not present
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    public int indexOf( String toFind ) throws NullPointerException;

    /**
     * Copies the {@link String} elements of this list into the provided array
     * in proper sequence (first to last). If the array is large enough, elements
     * are stored starting at index {@code 0}; otherwise, a new array of
     * sufficient size is allocated and returned.
     *
     * @param toHold  the {@link String} array into which list elements are to be
     *                stored; must not be {@code null}
     * @return a {@link String} array containing all elements of this list in order
     * @throws NullPointerException if {@code toHold} is {@code null}
     */
    public String[] toArray( String[] toHold ) throws NullPointerException;

    /**
     * Returns an {@link Iterator} that traverses the {@link String} elements of
     * this list in proper sequence, from first element to last.
     *
     * @return an iterator over the {@link String} elements of this list in order
     */
    public Iterator iterator();
}