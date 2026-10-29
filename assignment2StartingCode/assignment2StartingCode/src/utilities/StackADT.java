package utilities;

import java.util.EmptyStackException;

/**
 * A Stack ADT interface used as the foundation for stack data structures
 * in CPRG 304 at SAIT.
 * <p>
 * This interface embodies all standard stack operations — push, pop, peek —
 * as well as several helper methods that provide additional flexibility.
 * A stack follows a <strong>Last-In, First-Out (LIFO)</strong> ordering
 * policy: the last element pushed onto the stack is the first one returned
 * by a pop or peek operation.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This interface is intentionally designed to work with {@link String} elements
 * only. A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code StackADT<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element parameters and return types are fixed as
 * {@link String} throughout this interface and its implementations.
 * </p>
 */
public interface StackADT
{
	
    /**
     * Pushes the specified {@link String} element onto the top of this stack.
     * <p>
     * After this call, the pushed element becomes the new top of the stack
     * and will be the first element returned by the next call to
     * {@link #pop()} or {@link #peek()}.
     * </p>
     *
     * @param toAdd  the {@link String} element to push onto the top of the
     *               stack; must not be {@code null}
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    public void push( String toAdd ) throws NullPointerException;

    /**
     * Removes and returns the {@link String} element at the top of this stack.
     * <p>
     * The element directly beneath the removed top becomes the new top of
     * the stack after this call.
     * </p>
     *
     * @return the {@link String} element removed from the top of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
    public String pop() throws EmptyStackException;

    /**
     * Returns the {@link String} element at the top of this stack without
     * removing it.
     * <p>
     * The state of the stack is unchanged after this call.
     * </p>
     *
     * @return the {@link String} element currently at the top of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
    public String peek() throws EmptyStackException;

    /**
     * Removes all elements from this stack, leaving it empty.
     * After this call, {@link #size()} returns {@code 0} and
     * {@link #isEmpty()} returns {@code true}.
     */
    public void clear();

    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if {@link #size()} is {@code 0};
     *         {@code false} otherwise
     */
    public boolean isEmpty();

    /**
     * Copies all elements of this stack into the provided {@link String}
     * array in proper sequence, from bottom to top. If the provided array
     * is large enough, it is used directly; otherwise, a new array of the
     * same type and sufficient size is allocated and returned.
     *
     * @param holder  the {@link String} array into which the elements of
     *                this stack are to be stored; must not be {@code null}
     * @return a {@link String} array containing all elements of this stack
     *         in bottom-to-top order
     * @throws NullPointerException if {@code holder} is {@code null}
     */
    public String[] toArray( String[] holder ) throws NullPointerException;

    /**
     * Returns {@code true} if this stack contains at least one occurrence
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
     * from the top of this stack.
     * <p>
     * The topmost element is at position {@code 1}, the element beneath it
     * is at position {@code 2}, and so on. If the element appears more than
     * once, the position of the occurrence nearest the top is returned.
     * Equality is determined using {@link String#equals(Object)}.
     * </p>
     *
     * @param toFind  the {@link String} element to search for
     * @return the 1-based distance from the top of the stack to the nearest
     *         occurrence of the element, or {@code -1} if the element is not
     *         present in the stack
     */
    public int search( String toFind );

    /**
     * Returns an {@link Iterator} that traverses the {@link String} elements
     * of this stack in proper sequence, from bottom to top.
     *
     * @return an iterator over the {@link String} elements of this stack
     *         in bottom-to-top order
     */
    public Iterator iterator();

    /**
     * Compares this stack to the specified stack for equality.
     * <p>
     * Two stacks are considered equal if and only if they contain the same
     * {@link String} elements appearing in the same order from bottom to top.
     * Equality of individual elements is determined using
     * {@link String#equals(Object)}.
     * </p>
     *
     * @param that  the {@link StackADT} to compare to this stack;
     *              must not be {@code null}
     * @return {@code true} if both stacks contain equal elements in the
     *         same order; {@code false} otherwise
     */
    public boolean equals( StackADT that );

    /**
     * Returns the number of elements currently in this stack.
     *
     * @return the current depth of the stack; never negative
     */
    public int size();
    
}