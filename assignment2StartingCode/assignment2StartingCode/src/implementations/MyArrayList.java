package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;


/**
 * A dynamic array-based implementation of the {@link ListADT} interface,
 * for use in CPRG 304 at SAIT.
 * <p>
 * This class stores {@link String} elements in an internally managed array
 * that grows automatically when capacity is reached. The growth amount is
 * determined by a configurable growth rate expressed as a decimal percentage
 * of the current array length (e.g., {@code 0.25} means 25% growth).
 * All index parameters are zero-based unless otherwise noted.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only.
 * A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code MyArrayList<E>}), allowing any object type to be stored in the list.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element fields, parameters, and return types are fixed
 * as {@link String} throughout this class and its implementations.
 * </p>
 */
public class MyArrayList implements ListADT
{
    private String[] elements;
    private int size;
    private double growthRate;

    /**
     * Default constructor.
     */
    public MyArrayList()
    {
        elements = new String[10];
        size = 0;
        growthRate = 0.25;
    }

    /**
     * Returns the number of elements in the list.
     */
    @Override
    public int size()
    {
        return size;
    }

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear()
    {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * Adds an element at a specific index.
     */
    @Override
    public boolean add(int index, String toAdd)
    {
        if (toAdd == null)
        {
            throw new NullPointerException();
        }

        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }

        ensureCapacity(size + 1);

        // Shift elements to the right.
        for (int i = size; i > index; i--)
        {
            elements[i] = elements[i - 1];
        }

        elements[index] = toAdd;
        size++;

        return true;
    }

    /**
     * Adds an element to the end of the list.
     */
    @Override
    public boolean add(String toAdd)
    {
        return add(size, toAdd);
    }

    /**
     * Adds all elements from another list.
     */
    @Override
    public boolean addAll(ListADT toAdd)
    {
        if (toAdd == null)
        {
            throw new NullPointerException();
        }

        // Use an iterator snapshot so adding this list to itself is safe.
        Iterator iterator = toAdd.iterator();

        boolean changed = false;

        while (iterator.hasNext())
        {
            String element = iterator.next();

            if (element == null)
            {
                throw new NullPointerException();
            }

            add(element);
            changed = true;
        }

        return changed;
    }

    /**
     * Returns the element at the specified index.
     */
    @Override
    public String get(int index)
    {
        checkIndex(index);

        return elements[index];
    }

    /**
     * Removes and returns the element at the specified index.
     */
    @Override
    public String remove(int index)
    {
        checkIndex(index);

        String removed = elements[index];

        // Shift remaining elements to the left.
        for (int i = index; i < size - 1; i++)
        {
            elements[i] = elements[i + 1];
        }

        size--;

        // Clear the unused array position.
        elements[size] = null;

        return removed;
    }

    /**
     * Removes the first occurrence of an element.
     */
    @Override
    public String remove(String toRemove)
    {
        if (toRemove == null)
        {
            throw new NullPointerException();
        }

        int index = indexOf(toRemove);

        if (index == -1)
        {
            return null;
        }

        return remove(index);
    }

    /**
     * Replaces an element and returns its previous value.
     */
    @Override
    public String set(int index, String toChange)
    {
        if (toChange == null)
        {
            throw new NullPointerException();
        }

        checkIndex(index);

        String previous = elements[index];

        elements[index] = toChange;

        return previous;
    }

    /**
     * Checks whether the list is empty.
     */
    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Checks whether the list contains an element.
     */
    @Override
    public boolean contains(String toFind)
    {
        if (toFind == null)
        {
            throw new NullPointerException();
        }

        return indexOf(toFind) != -1;
    }

    /**
     * Returns the index of the first occurrence of an element.
     */
    @Override
    public int indexOf(String toFind)
    {
        if (toFind == null)
        {
            throw new NullPointerException();
        }

        for (int i = 0; i < size; i++)
        {
            if (elements[i].equals(toFind))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Copies the elements into the supplied array.
     */
    @Override
    public String[] toArray(String[] toHold)
    {
        if (toHold == null)
        {
            throw new NullPointerException();
        }

        String[] result;

        if (toHold.length >= size)
        {
            result = toHold;
        }
        else
        {
            result = new String[size];
        }

        for (int i = 0; i < size; i++)
        {
            result[i] = elements[i];
        }

        // Null-terminate when the supplied array has extra space.
        if (result.length > size)
        {
            result[size] = null;
        }

        return result;
    }

    /**
     * Returns an independent iterator over a snapshot of the list.
     */
    @Override
    public Iterator iterator()
    {
        return new MyArrayListIterator(
            Arrays.copyOf(elements, size)
        );
    }

    /**
     * Checks whether an index is valid for accessing an existing element.
     */
    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Increases the capacity when the array becomes full.
     */
    private void ensureCapacity(int requiredCapacity)
    {
        if (requiredCapacity <= elements.length)
        {
            return;
        }

        int increase = (int) Math.ceil(elements.length * growthRate);

        // Ensure that capacity always increases.
        if (increase < 1)
        {
            increase = 1;
        }

        int newCapacity = elements.length + increase;

        if (newCapacity < requiredCapacity)
        {
            newCapacity = requiredCapacity;
        }

        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Private iterator implementation.
     * Uses its own copy of the array.
     */
    private class MyArrayListIterator implements Iterator
    {
        private String[] iteratorElements;
        private int currentIndex;

        public MyArrayListIterator(String[] copy)
        {
            iteratorElements = copy;
            currentIndex = 0;
        }

        @Override
        public boolean hasNext()
        {
            return currentIndex < iteratorElements.length;
        }

        @Override
        public String next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            return iteratorElements[currentIndex++];
        }
    }
}