package unitTests;

import org.junit.Before;
import org.junit.Test;

import implementations.MyDLL;
import utilities.Iterator;

import static org.junit.Assert.*;

/**
 * JUnit 4 test suite for the MyDLL class.
 * Tests all methods defined in the ListADT interface.
 */
public class MyDLLTest
{
    // -----------------------------------------------------------------------
    // Fields & Setup
    // -----------------------------------------------------------------------

    /** The MyDLL instance under test – re-created fresh before every test. */
    private MyDLL list;

    @Before
    public void setUp()
    {
        list = new MyDLL();
    }


    // -----------------------------------------------------------------------
    // size()
    // -----------------------------------------------------------------------

    /** A newly created list must report size 0. */
    @Test
    public void testSize_emptyList_returnsZero()
    {
        assertEquals( 0, list.size() );
    }

    /** Size increments by 1 after each append. */
    @Test
    public void testSize_afterAppend_incrementsByOne()
    {
        list.add( "A" );
        assertEquals( 1, list.size() );

        list.add( "B" );
        assertEquals( 2, list.size() );
    }

    /** Size decrements after a successful remove. */
    @Test
    public void testSize_afterRemove_decrementsByOne()
    {
        list.add( "A" );
        list.add( "B" );
        list.remove( 0 );
        assertEquals( 1, list.size() );
    }


    // -----------------------------------------------------------------------
    // isEmpty()
    // -----------------------------------------------------------------------

    /** A new list is empty. */
    @Test
    public void testIsEmpty_newList_returnsTrue()
    {
        assertTrue( list.isEmpty() );
    }

    /** A list with one element is not empty. */
    @Test
    public void testIsEmpty_afterAdd_returnsFalse()
    {
        list.add( "A" );
        assertFalse( list.isEmpty() );
    }

    /** After clearing, the list is empty again. */
    @Test
    public void testIsEmpty_afterClear_returnsTrue()
    {
        list.add( "A" );
        list.clear();
        assertTrue( list.isEmpty() );
    }


    // -----------------------------------------------------------------------
    // clear()
    // -----------------------------------------------------------------------

    /** Clearing an already-empty list must not throw. */
    @Test
    public void testClear_emptyList_noException()
    {
        list.clear();
        assertEquals( 0, list.size() );
    }

    /** Clearing a populated list resets size to 0. */
    @Test
    public void testClear_populatedList_sizeBecomesZero()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        list.clear();
        assertEquals( 0, list.size() );
    }

    /** The list can be used normally after a clear. */
    @Test
    public void testClear_thenAdd_listFunctionsNormally()
    {
        list.add( "A" );
        list.clear();
        list.add( "B" );
        assertEquals( 1, list.size() );
        assertEquals( "B", list.get( 0 ) );
    }


    // -----------------------------------------------------------------------
    // add(String)
    // -----------------------------------------------------------------------

    /** Appending returns true. */
    @Test
    public void testAdd_validElement_returnsTrue()
    {
        assertTrue( list.add( "Hello" ) );
    }

    /** Appended elements appear in insertion order. */
    @Test
    public void testAdd_multipleElements_maintainsInsertionOrder()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        assertEquals( "A", list.get( 0 ) );
        assertEquals( "B", list.get( 1 ) );
        assertEquals( "C", list.get( 2 ) );
    }

    /** Appending a null element throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testAdd_null_throwsNullPointerException()
    {
        list.add( (String) null );
    }


    // -----------------------------------------------------------------------
    // add(int, String)
    // -----------------------------------------------------------------------

    /** Inserting at index 0 prepends the element. */
    @Test
    public void testAddAtIndex_atZero_prependsElement()
    {
        list.add( "B" );
        list.add( 0, "A" );
        assertEquals( "A", list.get( 0 ) );
        assertEquals( "B", list.get( 1 ) );
    }

    /** Inserting in the middle shifts later elements right. */
    @Test
    public void testAddAtIndex_inMiddle_shiftsElementsRight()
    {
        list.add( "A" );
        list.add( "C" );
        list.add( 1, "B" );
        assertEquals( "A", list.get( 0 ) );
        assertEquals( "B", list.get( 1 ) );
        assertEquals( "C", list.get( 2 ) );
        assertEquals( 3, list.size() );
    }

    /** Inserting at index == size() effectively appends. */
    @Test
    public void testAddAtIndex_atSize_appendsElement()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( 2, "C" );
        assertEquals( "C", list.get( 2 ) );
        assertEquals( 3, list.size() );
    }

    /** Inserting null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testAddAtIndex_null_throwsNullPointerException()
    {
        list.add( 0, null );
    }

    /** A negative index throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddAtIndex_negativeIndex_throwsIndexOutOfBoundsException()
    {
        list.add( -1, "A" );
    }

    /** An index greater than size throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testAddAtIndex_beyondSize_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.add( 5, "B" );
    }


    // -----------------------------------------------------------------------
    // addAll(ListADT)
    // -----------------------------------------------------------------------

    /** All elements from the source list are appended in order. */
    @Test
    public void testAddAll_validList_appendsAllElements()
    {
        list.add( "A" );
        MyDLL other = new MyDLL();
        other.add( "B" );
        other.add( "C" );
        assertTrue( list.addAll( other ) );
        assertEquals( 3, list.size() );
        assertEquals( "B", list.get( 1 ) );
        assertEquals( "C", list.get( 2 ) );
    }

    /** addAll into an empty list yields a copy of the source list. */
    @Test
    public void testAddAll_intoEmptyList_copiesSourceList()
    {
        MyDLL other = new MyDLL();
        other.add( "X" );
        other.add( "Y" );
        list.addAll( other );
        assertEquals( 2, list.size() );
        assertEquals( "X", list.get( 0 ) );
        assertEquals( "Y", list.get( 1 ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testAddAll_null_throwsNullPointerException()
    {
        list.addAll( null );
    }


    // -----------------------------------------------------------------------
    // get(int)
    // -----------------------------------------------------------------------

    /** get returns the correct element at each valid index. */
    @Test
    public void testGet_validIndex_returnsCorrectElement()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        assertEquals( "A", list.get( 0 ) );
        assertEquals( "B", list.get( 1 ) );
        assertEquals( "C", list.get( 2 ) );
    }

    /** get on an empty list throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testGet_emptyList_throwsIndexOutOfBoundsException()
    {
        list.get( 0 );
    }

    /** A negative index throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testGet_negativeIndex_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.get( -1 );
    }

    /** An index equal to size throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testGet_indexEqualToSize_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.get( 1 );      // valid indices are 0..0
    }


    // -----------------------------------------------------------------------
    // remove(int)
    // -----------------------------------------------------------------------

    /** Removing the first element returns it and shifts the rest left. */
    @Test
    public void testRemoveByIndex_first_shiftsRemaining()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        String removed = list.remove( 0 );
        assertEquals( "A", removed );
        assertEquals( "B", list.get( 0 ) );
        assertEquals( "C", list.get( 1 ) );
        assertEquals( 2, list.size() );
    }

    /** Removing a middle element returns it and closes the gap. */
    @Test
    public void testRemoveByIndex_middle_returnsCorrectElement()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        assertEquals( "B", list.remove( 1 ) );
        assertEquals( 2, list.size() );
        assertEquals( "C", list.get( 1 ) );
    }

    /** Removing the last element returns it and decrements size. */
    @Test
    public void testRemoveByIndex_last_returnsLastElement()
    {
        list.add( "A" );
        list.add( "B" );
        assertEquals( "B", list.remove( 1 ) );
        assertEquals( 1, list.size() );
    }

    /** Removing the only element leaves an empty list. */
    @Test
    public void testRemoveByIndex_onlyElement_listBecomesEmpty()
    {
        list.add( "A" );
        list.remove( 0 );
        assertTrue( list.isEmpty() );
    }

    /** A negative index throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testRemoveByIndex_negativeIndex_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.remove( -1 );
    }

    /** An out-of-range index throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testRemoveByIndex_beyondSize_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.remove( 5 );
    }

    /** Removing from an empty list throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testRemoveByIndex_emptyList_throwsIndexOutOfBoundsException()
    {
        list.remove( 0 );
    }


    // -----------------------------------------------------------------------
    // remove(String)
    // -----------------------------------------------------------------------

    /** Removing an existing element returns that element. */
    @Test
    public void testRemoveByValue_existingElement_returnsElement()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        assertEquals( "B", list.remove( "B" ) );
        assertEquals( 2, list.size() );
    }

    /** Only the first occurrence is removed when duplicates exist. */
    @Test
    public void testRemoveByValue_duplicates_removesFirstOccurrenceOnly()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "A" );
        list.remove( "A" );
        assertEquals( 2, list.size() );
        assertEquals( "B", list.get( 0 ) );
        assertEquals( "A", list.get( 1 ) );
    }

    /** Removing a value not in the list returns null. */
    @Test
    public void testRemoveByValue_notFound_returnsNull()
    {
        list.add( "A" );
        assertNull( list.remove( "Z" ) );
    }

    /** Removing from an empty list returns null (element not found). */
    @Test
    public void testRemoveByValue_emptyList_returnsNull()
    {
        assertNull( list.remove( "A" ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testRemoveByValue_null_throwsNullPointerException()
    {
        list.add( "A" );
        list.remove( (String) null );
    }


    // -----------------------------------------------------------------------
    // set(int, String)
    // -----------------------------------------------------------------------

    /** set replaces the element and returns the previous value. */
    @Test
    public void testSet_validIndex_returnsPreviousElement()
    {
        list.add( "A" );
        list.add( "B" );
        String previous = list.set( 1, "Z" );
        assertEquals( "B", previous );
        assertEquals( "Z", list.get( 1 ) );
    }

    /** set does not change the size of the list. */
    @Test
    public void testSet_validIndex_sizeUnchanged()
    {
        list.add( "A" );
        list.add( "B" );
        list.set( 0, "Z" );
        assertEquals( 2, list.size() );
    }

    /** Setting null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testSet_null_throwsNullPointerException()
    {
        list.add( "A" );
        list.set( 0, null );
    }

    /** A negative index throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testSet_negativeIndex_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.set( -1, "Z" );
    }

    /** An index equal to size throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testSet_indexEqualToSize_throwsIndexOutOfBoundsException()
    {
        list.add( "A" );
        list.set( 1, "Z" );
    }

    /** Setting on an empty list throws IndexOutOfBoundsException. */
    @Test( expected = IndexOutOfBoundsException.class )
    public void testSet_emptyList_throwsIndexOutOfBoundsException()
    {
        list.set( 0, "Z" );
    }


    // -----------------------------------------------------------------------
    // contains(String)
    // -----------------------------------------------------------------------

    /** Returns true when the element is present. */
    @Test
    public void testContains_existingElement_returnsTrue()
    {
        list.add( "A" );
        list.add( "B" );
        assertTrue( list.contains( "A" ) );
    }

    /** Returns false when the element is absent. */
    @Test
    public void testContains_missingElement_returnsFalse()
    {
        list.add( "A" );
        assertFalse( list.contains( "Z" ) );
    }

    /** Returns false on an empty list. */
    @Test
    public void testContains_emptyList_returnsFalse()
    {
        assertFalse( list.contains( "A" ) );
    }

    /** Returns false for an element that was removed. */
    @Test
    public void testContains_afterRemove_returnsFalse()
    {
        list.add( "A" );
        list.remove( "A" );
        assertFalse( list.contains( "A" ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testContains_null_throwsNullPointerException()
    {
        list.contains( null );
    }


    // -----------------------------------------------------------------------
    // indexOf(String)
    // -----------------------------------------------------------------------

    /** Returns the correct index of a present element. */
    @Test
    public void testIndexOf_existingElement_returnsCorrectIndex()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        assertEquals( 1, list.indexOf( "B" ) );
    }

    /** Returns -1 when the element is not in the list. */
    @Test
    public void testIndexOf_missingElement_returnsNegativeOne()
    {
        list.add( "A" );
        assertEquals( -1, list.indexOf( "Z" ) );
    }

    /** Returns the index of the FIRST occurrence when duplicates exist. */
    @Test
    public void testIndexOf_duplicates_returnsFirstOccurrence()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "A" );
        assertEquals( 0, list.indexOf( "A" ) );
    }

    /** Returns -1 on an empty list. */
    @Test
    public void testIndexOf_emptyList_returnsNegativeOne()
    {
        assertEquals( -1, list.indexOf( "A" ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testIndexOf_null_throwsNullPointerException()
    {
        list.indexOf( null );
    }


    // -----------------------------------------------------------------------
    // toArray(String[])
    // -----------------------------------------------------------------------

    /** The returned array matches the list's contents and order. */
    @Test
    public void testToArray_populatedList_returnsCorrectArray()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        String[] arr = list.toArray( new String[ 0 ] );
        assertEquals( 3, arr.length );
        assertEquals( "A", arr[ 0 ] );
        assertEquals( "B", arr[ 1 ] );
        assertEquals( "C", arr[ 2 ] );
    }

    /** An empty list produces a zero-length array. */
    @Test
    public void testToArray_emptyList_returnsEmptyArray()
    {
        String[] arr = list.toArray( new String[ 0 ] );
        assertEquals( 0, arr.length );
    }

    /** A large-enough holder array is filled without creating a new one. */
    @Test
    public void testToArray_holderLargeEnough_usesHolderArray()
    {
        list.add( "A" );
        list.add( "B" );
        String[] holder = new String[ 5 ];
        String[] result = list.toArray( holder );
        assertEquals( "A", result[ 0 ] );
        assertEquals( "B", result[ 1 ] );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testToArray_null_throwsNullPointerException()
    {
        list.toArray( null );
    }


    // -----------------------------------------------------------------------
    // iterator()
    // -----------------------------------------------------------------------

    /** iterator() never returns null. */
    @Test
    public void testIterator_alwaysReturnsNonNull()
    {
        assertNotNull( list.iterator() );
    }

    /** An iterator over an empty list has no elements. */
    @Test
    public void testIterator_emptyList_hasNextReturnsFalse()
    {
        Iterator it = list.iterator();
        assertFalse( it.hasNext() );
    }

    /** An iterator traverses all elements in insertion order. */
    @Test
    public void testIterator_populatedList_traversesAllElementsInOrder()
    {
        list.add( "A" );
        list.add( "B" );
        list.add( "C" );
        Iterator it = list.iterator();

        assertTrue( it.hasNext() );
        assertEquals( "A", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "B", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "C", it.next() );

        assertFalse( it.hasNext() );
    }

    /** Each call to iterator() returns an independent cursor. */
    @Test
    public void testIterator_calledTwice_returnsTwoIndependentIterators()
    {
        list.add( "A" );
        list.add( "B" );

        Iterator it1 = list.iterator();
        Iterator it2 = list.iterator();

        // advance it1 past the first element
        it1.next();

        // it2 should still point to the beginning
        assertEquals( "A", it2.next() );
    }
}