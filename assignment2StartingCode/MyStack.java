package implementations;
 
import java.util.EmptyStackException;
 
import utilities.Iterator;
import utilities.StackADT;

/**
 * An array-backed implementation of the {@link StackADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class delegates all storage to an internal {@link MyArrayList},
 * treating the end of the list as the top of the stack. {@link #push(String)}
 * appends to the end; {@link #pop()} and {@link #peek()} access the last
 * element. A stack follows a <strong>Last-In, First-Out (LIFO)</strong>
 * ordering policy: the last element pushed is the first one returned.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyStack<E>}), allowing any object type to be stored in the stack.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyStack implements StackADT
{
    /** The array list used to store the stack elements. */
    private MyArrayList elements;
 
    /**
     * Constructs an empty stack.
     * <p>
     * A new {@link MyArrayList} is created to store the elements
     * of the stack.
     * </p>
     */
    public MyStack()
    {
        elements = new MyArrayList();
    }
 
    /**
     * Pushes the specified {@link String} element onto the top
     * of this stack.
     * <p>
     * The element is appended to the end of the underlying list,
     * making it the new top element of the stack.
     * </p>
     *
     * @param toAdd the {@link String} element to push onto the stack;
     *              must not be {@code null}
     * @throws NullPointerException if {@code toAdd} is {@code null}
     */
    @Override
    public void push(String toAdd)
    {
        if (toAdd == null)
        {
            throw new NullPointerException(
                "Null elements are not permitted.");
        }
 
        elements.add(toAdd);
    }
 
    /**
     * Removes and returns the {@link String} element at the top
     * of this stack.
     * <p>
     * The last element in the underlying list is removed.
     * The element directly beneath it becomes the new top
     * of the stack.
     * </p>
     *
     * @return the {@link String} element removed from the top
     *         of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
    @Override
    public String pop()
    {
        if (isEmpty())
        {
            throw new EmptyStackException();
        }
 
        return elements.remove(elements.size() - 1);
    }
 
    /**
     * Returns the {@link String} element at the top of this stack
     * without removing it.
     * <p>
     * The element at the end of the underlying list is returned.
     * The state of the stack remains unchanged.
     * </p>
     *
     * @return the {@link String} element currently at the top
     *         of the stack
     * @throws EmptyStackException if the stack contains no elements
     */
    @Override
    public String peek()
    {
        if (isEmpty())
        {
            throw new EmptyStackException();
        }
 
        return elements.get(elements.size() - 1);
    }
 
    /**
     * Removes all elements from this stack, leaving it empty.
     * <p>
     * After this call, {@link #size()} returns {@code 0} and
     * {@link #isEmpty()} returns {@code true}.
     * </p>
     */
    @Override
    public void clear()
    {
        elements.clear();
    }
 
    /**
     * Returns {@code true} if this stack contains no elements.
     *
     * @return {@code true} if the stack is empty;
     *         {@code false} otherwise
     */
    @Override
    public boolean isEmpty()
    {
        return elements.isEmpty();
    }
 
    /**
     * Copies all elements of this stack into the provided
     * {@link String} array in proper sequence, from bottom
     * to top.
     * <p>
     * If the provided array is large enough, it is used directly.
     * Otherwise, a new array of sufficient size is allocated
     * and returned.
     * </p>
     *
     * @param holder the {@link String} array into which the
     *               elements of this stack are to be stored;
     *               must not be {@code null}
     * @return a {@link String} array containing all elements
     *         of this stack in bottom-to-top order
     * @throws NullPointerException if {@code holder} is {@code null}
     */
    @Override
    public String[] toArray(String[] holder)
    {
        if (holder == null)
        {
            throw new NullPointerException(
                "The destination array cannot be null.");
        }
 
        return elements.toArray(holder);
    }
 
    /**
     * Returns {@code true} if this stack contains at least one
     * occurrence of the specified {@link String} element.
     * <p>
     * Equality is determined using {@link String#equals(Object)}.
     * </p>
     *
     * @param toFind the {@link String} element to search for;
     *               must not be {@code null}
     * @return {@code true} if the element is present;
     *         {@code false} otherwise
     * @throws NullPointerException if {@code toFind} is {@code null}
     */
    @Override
    public boolean contains(String toFind)
    {
        if (toFind == null)
        {
            throw new NullPointerException(
                "The search element cannot be null.");
        }
 
        return elements.contains(toFind);
    }
 
    /**
     * Returns the 1-based position of the specified {@link String}
     * element from the top of this stack.
     * <p>
     * The top element is at position {@code 1}, the element
     * directly beneath it is at position {@code 2}, and so on.
     * If the element appears more than once, the position
     * of the occurrence nearest the top is returned.
     * </p>
     *
     * @param toFind the {@link String} element to search for
     * @return the 1-based position of the element from the top
     *         of the stack, or {@code -1} if the element
     *         is not present
     */
    @Override
    public int search(String toFind)
    {
        if (toFind == null)
        {
            return -1;
        }
 
        int position = 1;
 
        for (int i = elements.size() - 1; i >= 0; i--)
        {
            if (elements.get(i).equals(toFind))
            {
                return position;
            }
 
            position++;
        }
 
        return -1;
    }
 
    /**
     * Returns an {@link Iterator} that traverses the {@link String}
     * elements of this stack in proper sequence, from bottom to top.
     * <p>
     * The iterator operates on an independent snapshot of the
     * underlying list, so subsequent modifications to the stack
     * do not affect the iteration sequence.
     * </p>
     *
     * @return an iterator over the {@link String} elements
     *         of this stack in bottom-to-top order
     */
    @Override
    public Iterator iterator()
    {
        return elements.iterator();
    }
 
    /**
     * Compares this stack to the specified stack for equality.
     * <p>
     * Two stacks are considered equal if and only if they contain
     * the same {@link String} elements appearing in the same order
     * from bottom to top.
     * Equality of individual elements is determined using
     * {@link String#equals(Object)}.
     * </p>
     *
     * @param that the {@link StackADT} to compare to this stack;
     *             must not be {@code null}
     * @return {@code true} if both stacks contain equal elements
     *         in the same order; {@code false} otherwise
     */
    @Override
    public boolean equals(StackADT that)
    {
        if (that == null || size() != that.size())
        {
            return false;
        }
 
        Iterator first = iterator();
        Iterator second = that.iterator();
 
        while (first.hasNext())
        {
            if (!first.next().equals(second.next()))
            {
                return false;
            }
        }
 
        return true;
    }
 
    /**
     * Returns the number of elements currently in this stack.
     *
     * @return the current depth of the stack; never negative
     */
    @Override
    public int size()
    {
        return elements.size();
    }
}

