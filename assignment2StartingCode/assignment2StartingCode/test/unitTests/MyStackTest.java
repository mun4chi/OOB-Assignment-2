package unitTests;

import org.junit.Before;
import org.junit.Test;

import implementations.MyStack;
import utilities.Iterator;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * JUnit 4 test suite for the MyStack class.
 * Tests all methods defined in the StackADT interface.
 */
public class MyStackTest
{
    // -----------------------------------------------------------------------
    // Fields & Setup
    // -----------------------------------------------------------------------

    /** The MyStack instance under test – re-created fresh before every test. */
    private MyStack stack;

    @Before
    public void setUp()
    {
        stack = new MyStack();
    }


    // -----------------------------------------------------------------------
    // size()
    // -----------------------------------------------------------------------

    /** A newly created stack must report size 0. */
    @Test
    public void testSize_emptyStack_returnsZero()
    {
        assertEquals( 0, stack.size() );
    }

    /** Size increments by 1 after each push. */
    @Test
    public void testSize_afterPush_incrementsByOne()
    {
        stack.push( "A" );
        assertEquals( 1, stack.size() );

        stack.push( "B" );
        assertEquals( 2, stack.size() );
    }

    /** Size decrements by 1 after each pop. */
    @Test
    public void testSize_afterPop_decrementsByOne()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();
        assertEquals( 1, stack.size() );
    }

    /** Size is unchanged after a peek. */
    @Test
    public void testSize_afterPeek_unchanged()
    {
        stack.push( "A" );
        stack.peek();
        assertEquals( 1, stack.size() );
    }


    // -----------------------------------------------------------------------
    // isEmpty()
    // -----------------------------------------------------------------------

    /** A new stack is empty. */
    @Test
    public void testIsEmpty_newStack_returnsTrue()
    {
        assertTrue( stack.isEmpty() );
    }

    /** A stack with one element is not empty. */
    @Test
    public void testIsEmpty_afterPush_returnsFalse()
    {
        stack.push( "A" );
        assertFalse( stack.isEmpty() );
    }

    /** After clearing, the stack is empty again. */
    @Test
    public void testIsEmpty_afterClear_returnsTrue()
    {
        stack.push( "A" );
        stack.clear();
        assertTrue( stack.isEmpty() );
    }

    /** After popping the only element, the stack is empty. */
    @Test
    public void testIsEmpty_afterPoppingLastElement_returnsTrue()
    {
        stack.push( "A" );
        stack.pop();
        assertTrue( stack.isEmpty() );
    }


    // -----------------------------------------------------------------------
    // clear()
    // -----------------------------------------------------------------------

    /** Clearing an already-empty stack must not throw. */
    @Test
    public void testClear_emptyStack_noException()
    {
        stack.clear();
        assertEquals( 0, stack.size() );
    }

    /** Clearing a populated stack resets size to 0. */
    @Test
    public void testClear_populatedStack_sizeBecomesZero()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        stack.clear();
        assertEquals( 0, stack.size() );
    }

    /** The stack can be used normally after a clear. */
    @Test
    public void testClear_thenPush_stackFunctionsNormally()
    {
        stack.push( "A" );
        stack.clear();
        stack.push( "B" );
        assertEquals( 1, stack.size() );
        assertEquals( "B", stack.peek() );
    }


    // -----------------------------------------------------------------------
    // push(String)
    // -----------------------------------------------------------------------

    /** Pushing a valid element does not throw. */
    @Test
    public void testPush_validElement_noException()
    {
        stack.push( "A" );
        assertEquals( 1, stack.size() );
    }

    /** The most recently pushed element becomes the new top. */
    @Test
    public void testPush_multipleElements_lastPushedIsTop()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        assertEquals( "C", stack.peek() );
    }

    /** Pushing maintains LIFO order across multiple elements. */
    @Test
    public void testPush_multipleElements_lifoOrderMaintained()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        assertEquals( "C", stack.pop() );
        assertEquals( "B", stack.pop() );
        assertEquals( "A", stack.pop() );
    }

    /** Pushing the same element multiple times increments size each time. */
    @Test
    public void testPush_duplicateElements_sizeReflectsAllPushes()
    {
        stack.push( "A" );
        stack.push( "A" );
        stack.push( "A" );
        assertEquals( 3, stack.size() );
    }

    /** Pushing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testPush_null_throwsNullPointerException()
    {
        stack.push( null );
    }


    // -----------------------------------------------------------------------
    // pop()
    // -----------------------------------------------------------------------

    /** Popping returns the most recently pushed element. */
    @Test
    public void testPop_singleElement_returnsTopElement()
    {
        stack.push( "A" );
        assertEquals( "A", stack.pop() );
    }

    /** Popping removes the top element, exposing the one beneath. */
    @Test
    public void testPop_multipleElements_returnsInLIFOOrder()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        assertEquals( "C", stack.pop() );
        assertEquals( "B", stack.pop() );
        assertEquals( "A", stack.pop() );
    }

    /** Popping reduces the stack size by 1. */
    @Test
    public void testPop_reducesSize()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();
        assertEquals( 1, stack.size() );
    }

    /** Popping the only element leaves the stack empty. */
    @Test
    public void testPop_onlyElement_stackBecomesEmpty()
    {
        stack.push( "A" );
        stack.pop();
        assertTrue( stack.isEmpty() );
    }

    /** Popping from an empty stack throws EmptyStackException. */
    @Test( expected = EmptyStackException.class )
    public void testPop_emptyStack_throwsEmptyStackException()
    {
        stack.pop();
    }

    /** Popping after a clear throws EmptyStackException. */
    @Test( expected = EmptyStackException.class )
    public void testPop_afterClear_throwsEmptyStackException()
    {
        stack.push( "A" );
        stack.clear();
        stack.pop();
    }


    // -----------------------------------------------------------------------
    // peek()
    // -----------------------------------------------------------------------

    /** Peek returns the top element without removing it. */
    @Test
    public void testPeek_singleElement_returnsTopElement()
    {
        stack.push( "A" );
        assertEquals( "A", stack.peek() );
    }

    /** Peek does not remove the top element. */
    @Test
    public void testPeek_doesNotRemoveTopElement()
    {
        stack.push( "A" );
        stack.peek();
        assertEquals( 1, stack.size() );
        assertEquals( "A", stack.peek() );
    }

    /** Peek always returns the most recently pushed element. */
    @Test
    public void testPeek_multipleElements_returnsLastPushed()
    {
        stack.push( "A" );
        stack.push( "B" );
        assertEquals( "B", stack.peek() );
    }

    /** Peek returns the updated top after a pop. */
    @Test
    public void testPeek_afterPop_returnsNewTop()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();
        assertEquals( "A", stack.peek() );
    }

    /** Peek on an empty stack throws EmptyStackException. */
    @Test( expected = EmptyStackException.class )
    public void testPeek_emptyStack_throwsEmptyStackException()
    {
        stack.peek();
    }

    /** Peek after a clear throws EmptyStackException. */
    @Test( expected = EmptyStackException.class )
    public void testPeek_afterClear_throwsEmptyStackException()
    {
        stack.push( "A" );
        stack.clear();
        stack.peek();
    }


    // -----------------------------------------------------------------------
    // Push / Pop / Peek Interaction
    // -----------------------------------------------------------------------

    /** Alternating push and pop always exposes the correct top. */
    @Test
    public void testInteraction_alternatePushPop_correctTopMaintained()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();                        // removes B
        stack.push( "C" );
        assertEquals( "C", stack.peek() );  // C is now on top of A
        assertEquals( 2, stack.size() );
    }

    /** Pushing after a pop restores the expected LIFO sequence. */
    @Test
    public void testInteraction_pushAfterPop_lifoRestoredCorrectly()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();        // removes B
        stack.push( "C" );
        stack.push( "D" );
        assertEquals( "D", stack.pop() );
        assertEquals( "C", stack.pop() );
        assertEquals( "A", stack.pop() );
        assertTrue( stack.isEmpty() );
    }

    /** Repeated clear-and-refill cycles leave the stack in a consistent state. */
    @Test
    public void testInteraction_repeatedClearAndRefill_consistentState()
    {
        for( int i = 0; i < 3; i++ )
        {
            stack.push( "X" );
            stack.push( "Y" );
            stack.clear();
            assertTrue( stack.isEmpty() );
            assertEquals( 0, stack.size() );
        }
        stack.push( "Z" );
        assertEquals( "Z", stack.peek() );
        assertEquals( 1, stack.size() );
    }

    /** Popping all elements one by one leaves the stack empty with size 0. */
    @Test
    public void testInteraction_popAllElements_stackEmptyWithSizeZero()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        stack.pop();
        stack.pop();
        stack.pop();
        assertTrue( stack.isEmpty() );
        assertEquals( 0, stack.size() );
    }


    // -----------------------------------------------------------------------
    // contains(String)
    // -----------------------------------------------------------------------

    /** Returns true when the element is at the top. */
    @Test
    public void testContains_elementAtTop_returnsTrue()
    {
        stack.push( "A" );
        assertTrue( stack.contains( "A" ) );
    }

    /** Returns true when the element is buried beneath others. */
    @Test
    public void testContains_elementBelowTop_returnsTrue()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        assertTrue( stack.contains( "A" ) );
    }

    /** Returns false when the element is not in the stack. */
    @Test
    public void testContains_missingElement_returnsFalse()
    {
        stack.push( "A" );
        assertFalse( stack.contains( "Z" ) );
    }

    /** Returns false on an empty stack. */
    @Test
    public void testContains_emptyStack_returnsFalse()
    {
        assertFalse( stack.contains( "A" ) );
    }

    /** Returns false for an element that was popped. */
    @Test
    public void testContains_afterPop_returnsFalse()
    {
        stack.push( "A" );
        stack.pop();
        assertFalse( stack.contains( "A" ) );
    }

    /** Returns false after the stack is cleared. */
    @Test
    public void testContains_afterClear_returnsFalse()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.clear();
        assertFalse( stack.contains( "A" ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testContains_null_throwsNullPointerException()
    {
        stack.contains( null );
    }


    // -----------------------------------------------------------------------
    // search(String)
    // -----------------------------------------------------------------------

    /** The top element is at position 1. */
    @Test
    public void testSearch_topElement_returnsOne()
    {
        stack.push( "A" );
        assertEquals( 1, stack.search( "A" ) );
    }

    /** An element one below the top is at position 2. */
    @Test
    public void testSearch_secondFromTop_returnsTwo()
    {
        stack.push( "A" );
        stack.push( "B" );
        assertEquals( 2, stack.search( "A" ) );
    }

    /** Returns the correct 1-based distance for deeply buried elements. */
    @Test
    public void testSearch_deepElements_returnsCorrectPositions()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        assertEquals( 3, stack.search( "A" ) );
        assertEquals( 2, stack.search( "B" ) );
        assertEquals( 1, stack.search( "C" ) );
    }

    /** When duplicates exist, the occurrence nearest the top is returned. */
    @Test
    public void testSearch_duplicates_returnsNearestToTop()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "A" );
        assertEquals( 1, stack.search( "A" ) );
    }

    /** Returns -1 when the element is not in the stack. */
    @Test
    public void testSearch_missingElement_returnsNegativeOne()
    {
        stack.push( "A" );
        assertEquals( -1, stack.search( "Z" ) );
    }

    /** Returns -1 on an empty stack. */
    @Test
    public void testSearch_emptyStack_returnsNegativeOne()
    {
        assertEquals( -1, stack.search( "A" ) );
    }

    /** Returns -1 after the target element has been popped. */
    @Test
    public void testSearch_afterElementPopped_returnsNegativeOne()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.pop();
        assertEquals( -1, stack.search( "B" ) );
    }

    /** Returns -1 after the stack is cleared. */
    @Test
    public void testSearch_afterClear_returnsNegativeOne()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.clear();
        assertEquals( -1, stack.search( "A" ) );
    }


    // -----------------------------------------------------------------------
    // toArray(String[])
    // -----------------------------------------------------------------------

    /** Returns all elements in bottom-to-top order using the holder. */
    @Test
    public void testToArrayWithHolder_populatedStack_returnsBottomToTopOrder()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        String[] result = (String[]) stack.toArray( new String[ 0 ] );
        assertEquals( 3, result.length );
        assertEquals( "A", result[ 0 ] );
        assertEquals( "B", result[ 1 ] );
        assertEquals( "C", result[ 2 ] );
    }

    /** A holder that is large enough is used directly. */
    @Test
    public void testToArrayWithHolder_holderLargeEnough_usesHolderArray()
    {
        stack.push( "A" );
        stack.push( "B" );
        String[] holder = new String[ 5 ];
        String[] result = (String[]) stack.toArray( holder );
        assertEquals( "A", result[ 0 ] );
        assertEquals( "B", result[ 1 ] );
    }

    /** An empty stack produces a zero-length array. */
    @Test
    public void testToArrayWithHolder_emptyStack_returnsEmptyArray()
    {
        String[] arr = (String[]) stack.toArray( new String[ 0 ] );
        assertEquals( 0, arr.length );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testToArrayWithHolder_null_throwsNullPointerException()
    {
        stack.toArray( null );
    }


    // -----------------------------------------------------------------------
    // equals(StackADT)
    // -----------------------------------------------------------------------

    /** Two empty stacks are equal. */
    @Test
    public void testEquals_twoEmptyStacks_returnsTrue()
    {
        MyStack other = new MyStack();
        assertTrue( stack.equals( other ) );
    }

    /** Two stacks with the same elements in the same order are equal. */
    @Test
    public void testEquals_sameElementsSameOrder_returnsTrue()
    {
        MyStack other = new MyStack();
        stack.push( "A" );
        stack.push( "B" );
        other.push( "A" );
        other.push( "B" );
        assertTrue( stack.equals( other ) );
    }

    /** Two stacks with the same elements in different order are not equal. */
    @Test
    public void testEquals_sameElementsDifferentOrder_returnsFalse()
    {
        MyStack other = new MyStack();
        stack.push( "A" );
        stack.push( "B" );
        other.push( "B" );
        other.push( "A" );
        assertFalse( stack.equals( other ) );
    }

    /** Two stacks with different elements are not equal. */
    @Test
    public void testEquals_differentElements_returnsFalse()
    {
        MyStack other = new MyStack();
        stack.push( "A" );
        other.push( "Z" );
        assertFalse( stack.equals( other ) );
    }

    /** A populated stack is not equal to an empty stack. */
    @Test
    public void testEquals_differentSizes_returnsFalse()
    {
        MyStack other = new MyStack();
        stack.push( "A" );
        assertFalse( stack.equals( other ) );
    }

    /** A stack is equal to itself (reflexivity). */
    @Test
    public void testEquals_sameInstance_returnsTrue()
    {
        stack.push( "A" );
        assertTrue( stack.equals( stack ) );
    }

    /** Equality holds after the same sequence of pushes on both stacks. */
    @Test
    public void testEquals_afterIdenticalPushSequence_returnsTrue()
    {
        MyStack other = new MyStack();
        stack.push( "X" );
        stack.push( "Y" );
        stack.push( "Z" );
        other.push( "X" );
        other.push( "Y" );
        other.push( "Z" );
        assertTrue( stack.equals( other ) );
    }


    // -----------------------------------------------------------------------
    // iterator()
    // -----------------------------------------------------------------------

    /** iterator() never returns null. */
    @Test
    public void testIterator_alwaysReturnsNonNull()
    {
        assertNotNull( stack.iterator() );
    }

    /** An iterator over an empty stack has no elements. */
    @Test
    public void testIterator_emptyStack_hasNextReturnsFalse()
    {
        Iterator it = stack.iterator();
        assertFalse( it.hasNext() );
    }

    /** An iterator traverses all elements in bottom-to-top order. */
    @Test
    public void testIterator_populatedStack_traversesBottomToTop()
    {
        stack.push( "A" );
        stack.push( "B" );
        stack.push( "C" );
        Iterator it = stack.iterator();

        assertTrue( it.hasNext() );
        assertEquals( "A", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "B", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "C", it.next() );

        assertFalse( it.hasNext() );
    }

    /** A single-element stack produces a single-step iterator. */
    @Test
    public void testIterator_singleElement_traversesOneElement()
    {
        stack.push( "A" );
        Iterator it = stack.iterator();
        assertTrue( it.hasNext() );
        assertEquals( "A", it.next() );
        assertFalse( it.hasNext() );
    }

    /** Each call to iterator() returns an independent cursor. */
    @Test
    public void testIterator_calledTwice_returnsTwoIndependentIterators()
    {
        stack.push( "A" );
        stack.push( "B" );

        Iterator it1 = stack.iterator();
        Iterator it2 = stack.iterator();

        // advance it1 past the first element
        it1.next();

        // it2 should still point to the beginning (bottom)
        assertEquals( "A", it2.next() );
    }

}