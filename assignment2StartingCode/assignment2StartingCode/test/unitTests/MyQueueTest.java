package unitTests;

import exceptions.EmptyQueueException;
import implementations.MyQueue;

import org.junit.Before;
import org.junit.Test;
import utilities.Iterator;

import static org.junit.Assert.*;

/**
 * JUnit 4 test suite for the MyQueue class.
 * Tests all methods defined in the QueueADT interface.
 */
public class MyQueueTest
{
    // -----------------------------------------------------------------------
    // Fields & Setup
    // -----------------------------------------------------------------------

    /** The MyQueue instance under test – re-created fresh before every test. */
    private MyQueue queue;

    @Before
    public void setUp()
    {
        queue = new MyQueue();
    }


    // -----------------------------------------------------------------------
    // size()
    // -----------------------------------------------------------------------

    /** A newly created queue must report size 0. */
    @Test
    public void testSize_emptyQueue_returnsZero()
    {
        assertEquals( 0, queue.size() );
    }

    /** Size increments by 1 after each enqueue. */
    @Test
    public void testSize_afterEnqueue_incrementsByOne()
    {
        queue.enqueue( "A" );
        assertEquals( 1, queue.size() );

        queue.enqueue( "B" );
        assertEquals( 2, queue.size() );
    }

    /** Size decrements by 1 after each dequeue. */
    @Test
    public void testSize_afterDequeue_decrementsByOne()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertEquals( 1, queue.size() );
    }

    /** Size is unchanged after a peek. */
    @Test
    public void testSize_afterPeek_unchanged()
    {
        queue.enqueue( "A" );
        try
        {
            queue.peek();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertEquals( 1, queue.size() );
    }


    // -----------------------------------------------------------------------
    // isEmpty()
    // -----------------------------------------------------------------------

    /** A new queue is empty. */
    @Test
    public void testIsEmpty_newQueue_returnsTrue()
    {
        assertTrue( queue.isEmpty() );
    }

    /** A queue with one element is not empty. */
    @Test
    public void testIsEmpty_afterEnqueue_returnsFalse()
    {
        queue.enqueue( "A" );
        assertFalse( queue.isEmpty() );
    }

    /** After dequeueAll, the queue is empty again. */
    @Test
    public void testIsEmpty_afterDequeueAll_returnsTrue()
    {
        queue.enqueue( "A" );
        queue.dequeueAll();
        assertTrue( queue.isEmpty() );
    }

    /** After dequeuing the only element, the queue is empty. */
    @Test
    public void testIsEmpty_afterDequeuingLastElement_returnsTrue()
    {
        queue.enqueue( "A" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertTrue( queue.isEmpty() );
    }


    // -----------------------------------------------------------------------
    // dequeueAll()
    // -----------------------------------------------------------------------

    /** Calling dequeueAll on an already-empty queue must not throw. */
    @Test
    public void testDequeueAll_emptyQueue_noException()
    {
        queue.dequeueAll();
        assertEquals( 0, queue.size() );
    }

    /** Calling dequeueAll on a populated queue resets size to 0. */
    @Test
    public void testDequeueAll_populatedQueue_sizeBecomesZero()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        queue.dequeueAll();
        assertEquals( 0, queue.size() );
    }

    /** The queue can be used normally after a dequeueAll. */
    @Test
    public void testDequeueAll_thenEnqueue_queueFunctionsNormally()
    {
        queue.enqueue( "A" );
        queue.dequeueAll();
        queue.enqueue( "B" );
        assertEquals( 1, queue.size() );
        try
        {
            assertEquals( "B", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Repeated dequeueAll calls leave the queue in a consistent state. */
    @Test
    public void testDequeueAll_calledTwice_noException()
    {
        queue.enqueue( "A" );
        queue.dequeueAll();
        queue.dequeueAll();
        assertTrue( queue.isEmpty() );
    }


    // -----------------------------------------------------------------------
    // enqueue(String)
    // -----------------------------------------------------------------------

    /** Enqueuing a valid element does not throw. */
    @Test
    public void testEnqueue_validElement_noException()
    {
        queue.enqueue( "A" );
        assertEquals( 1, queue.size() );
    }

    /** Elements are enqueued in FIFO order. */
    @Test
    public void testEnqueue_multipleElements_fifoOrderMaintained()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        try
        {
            assertEquals( "A", queue.dequeue() );
            assertEquals( "B", queue.dequeue() );
            assertEquals( "C", queue.dequeue() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** The first enqueued element is always at the front. */
    @Test
    public void testEnqueue_multipleElements_firstEnqueuedIsAtFront()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        try
        {
            assertEquals( "A", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Enqueuing duplicate elements increments size for each. */
    @Test
    public void testEnqueue_duplicateElements_sizeReflectsAllEnqueues()
    {
        queue.enqueue( "A" );
        queue.enqueue( "A" );
        queue.enqueue( "A" );
        assertEquals( 3, queue.size() );
    }

    /** Enqueuing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testEnqueue_null_throwsNullPointerException()
    {
        queue.enqueue( null );
    }


    // -----------------------------------------------------------------------
    // dequeue()
    // -----------------------------------------------------------------------

    /** Dequeuing returns the first enqueued element. */
    @Test
    public void testDequeue_singleElement_returnsFrontElement()
    {
        queue.enqueue( "A" );
        try
        {
            assertEquals( "A", queue.dequeue() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Dequeuing returns elements in FIFO order. */
    @Test
    public void testDequeue_multipleElements_returnsInFIFOOrder()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        try
        {
            assertEquals( "A", queue.dequeue() );
            assertEquals( "B", queue.dequeue() );
            assertEquals( "C", queue.dequeue() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Dequeuing reduces the queue size by 1. */
    @Test
    public void testDequeue_reducesSize()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertEquals( 1, queue.size() );
    }

    /** Dequeuing the only element leaves the queue empty. */
    @Test
    public void testDequeue_onlyElement_queueBecomesEmpty()
    {
        queue.enqueue( "A" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertTrue( queue.isEmpty() );
    }

    /** Dequeuing promotes the next element to the front. */
    @Test
    public void testDequeue_promotesNextElementToFront()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();
            assertEquals( "B", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Dequeuing from an empty queue throws EmptyQueueException. */
    @Test( expected = EmptyQueueException.class )
    public void testDequeue_emptyQueue_throwsEmptyQueueException() throws EmptyQueueException
    {
        queue.dequeue();
    }

    /** Dequeuing after dequeueAll throws EmptyQueueException. */
    @Test( expected = EmptyQueueException.class )
    public void testDequeue_afterDequeueAll_throwsEmptyQueueException() throws EmptyQueueException
    {
        queue.enqueue( "A" );
        queue.dequeueAll();
        queue.dequeue();
    }


    // -----------------------------------------------------------------------
    // peek()
    // -----------------------------------------------------------------------

    /** Peek returns the front element without removing it. */
    @Test
    public void testPeek_singleElement_returnsFrontElement()
    {
        queue.enqueue( "A" );
        try
        {
            assertEquals( "A", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Peek does not remove the front element. */
    @Test
    public void testPeek_doesNotRemoveFrontElement()
    {
        queue.enqueue( "A" );
        try
        {
            queue.peek();
            assertEquals( 1, queue.size() );
            assertEquals( "A", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Peek always returns the first enqueued element. */
    @Test
    public void testPeek_multipleElements_returnsFirstEnqueued()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            assertEquals( "A", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Peek returns the updated front after a dequeue. */
    @Test
    public void testPeek_afterDequeue_returnsNewFront()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();
            assertEquals( "B", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Peek on an empty queue throws EmptyQueueException. */
    @Test( expected = EmptyQueueException.class )
    public void testPeek_emptyQueue_throwsEmptyQueueException() throws EmptyQueueException
    {
        queue.peek();
    }

    /** Peek after dequeueAll throws EmptyQueueException. */
    @Test( expected = EmptyQueueException.class )
    public void testPeek_afterDequeueAll_throwsEmptyQueueException() throws EmptyQueueException
    {
        queue.enqueue( "A" );
        queue.dequeueAll();
        queue.peek();
    }


    // -----------------------------------------------------------------------
    // Enqueue / Dequeue / Peek Interaction
    // -----------------------------------------------------------------------

    /** Alternating enqueue and dequeue always exposes the correct front. */
    @Test
    public void testInteraction_alternateEnqueueDequeue_correctFrontMaintained()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();                        // removes A
            queue.enqueue( "C" );
            assertEquals( "B", queue.peek() );      // B is now at front
            assertEquals( 2, queue.size() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
    }

    /** Enqueuing after a dequeue restores the expected FIFO sequence. */
    @Test
    public void testInteraction_enqueueAfterDequeue_fifoRestoredCorrectly()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();                        // removes A
            queue.enqueue( "C" );
            queue.enqueue( "D" );
            assertEquals( "B", queue.dequeue() );
            assertEquals( "C", queue.dequeue() );
            assertEquals( "D", queue.dequeue() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertTrue( queue.isEmpty() );
    }

    /** Repeated dequeueAll-and-refill cycles leave the queue in a consistent state. */
    @Test
    public void testInteraction_repeatedDequeueAllAndRefill_consistentState()
    {
        for( int i = 0; i < 3; i++ )
        {
            queue.enqueue( "X" );
            queue.enqueue( "Y" );
            queue.dequeueAll();
            assertTrue( queue.isEmpty() );
            assertEquals( 0, queue.size() );
        }
        queue.enqueue( "Z" );
        try
        {
            assertEquals( "Z", queue.peek() );
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertEquals( 1, queue.size() );
    }

    /** Dequeuing all elements one by one leaves the queue empty with size 0. */
    @Test
    public void testInteraction_dequeueAllElements_queueEmptyWithSizeZero()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        try
        {
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertTrue( queue.isEmpty() );
        assertEquals( 0, queue.size() );
    }


    // -----------------------------------------------------------------------
    // contains(String)
    // -----------------------------------------------------------------------

    /** Returns true when the element is at the front. */
    @Test
    public void testContains_elementAtFront_returnsTrue()
    {
        queue.enqueue( "A" );
        assertTrue( queue.contains( "A" ) );
    }

    /** Returns true when the element is behind others. */
    @Test
    public void testContains_elementBehindFront_returnsTrue()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        assertTrue( queue.contains( "C" ) );
    }

    /** Returns false when the element is not in the queue. */
    @Test
    public void testContains_missingElement_returnsFalse()
    {
        queue.enqueue( "A" );
        assertFalse( queue.contains( "Z" ) );
    }

    /** Returns false on an empty queue. */
    @Test
    public void testContains_emptyQueue_returnsFalse()
    {
        assertFalse( queue.contains( "A" ) );
    }

    /** Returns false for an element that was dequeued. */
    @Test
    public void testContains_afterDequeue_returnsFalse()
    {
        queue.enqueue( "A" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertFalse( queue.contains( "A" ) );
    }

    /** Returns false after the queue is cleared. */
    @Test
    public void testContains_afterDequeueAll_returnsFalse()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.dequeueAll();
        assertFalse( queue.contains( "A" ) );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testContains_null_throwsNullPointerException()
    {
        queue.contains( null );
    }


    // -----------------------------------------------------------------------
    // search(String)
    // -----------------------------------------------------------------------

    /** The front element is at position 1. */
    @Test
    public void testSearch_frontElement_returnsOne()
    {
        queue.enqueue( "A" );
        assertEquals( 1, queue.search( "A" ) );
    }

    /** An element one behind the front is at position 2. */
    @Test
    public void testSearch_secondFromFront_returnsTwo()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        assertEquals( 2, queue.search( "B" ) );
    }

    /** Returns the correct 1-based distance for deeply buried elements. */
    @Test
    public void testSearch_deepElements_returnsCorrectPositions()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        assertEquals( 1, queue.search( "A" ) );
        assertEquals( 2, queue.search( "B" ) );
        assertEquals( 3, queue.search( "C" ) );
    }

    /** When duplicates exist, the occurrence nearest the front is returned. */
    @Test
    public void testSearch_duplicates_returnsNearestToFront()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "A" );
        assertEquals( 1, queue.search( "A" ) );
    }

    /** Returns -1 when the element is not in the queue. */
    @Test
    public void testSearch_missingElement_returnsNegativeOne()
    {
        queue.enqueue( "A" );
        assertEquals( -1, queue.search( "Z" ) );
    }

    /** Returns -1 on an empty queue. */
    @Test
    public void testSearch_emptyQueue_returnsNegativeOne()
    {
        assertEquals( -1, queue.search( "A" ) );
    }

    /** Returns -1 after the target element has been dequeued. */
    @Test
    public void testSearch_afterElementDequeued_returnsNegativeOne()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        try
        {
            queue.dequeue();
        }
        catch( EmptyQueueException e )
        {
            fail( "Should not have thrown EmptyQueueException." );
        }
        assertEquals( -1, queue.search( "A" ) );
    }

    /** Returns -1 after the queue is cleared. */
    @Test
    public void testSearch_afterDequeueAll_returnsNegativeOne()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.dequeueAll();
        assertEquals( -1, queue.search( "A" ) );
    }


    // -----------------------------------------------------------------------
    // toArray(String[])
    // -----------------------------------------------------------------------

    /** Returns all elements in front-to-back order using the holder. */
    @Test
    public void testToArray_populatedQueue_returnsFrontToBackOrder()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        String[] result = queue.toArray( new String[ 0 ] );
        assertEquals( 3, result.length );
        assertEquals( "A", result[ 0 ] );
        assertEquals( "B", result[ 1 ] );
        assertEquals( "C", result[ 2 ] );
    }

    /** A holder that is large enough is used directly. */
    @Test
    public void testToArray_holderLargeEnough_usesHolderArray()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        String[] holder = new String[ 5 ];
        String[] result = queue.toArray( holder );
        assertEquals( "A", result[ 0 ] );
        assertEquals( "B", result[ 1 ] );
    }

    /** An empty queue produces a zero-length array. */
    @Test
    public void testToArray_emptyQueue_returnsEmptyArray()
    {
        String[] arr = queue.toArray( new String[ 0 ] );
        assertEquals( 0, arr.length );
    }

    /** A single element is returned as a one-element array. */
    @Test
    public void testToArray_singleElement_returnsOneElementArray()
    {
        queue.enqueue( "A" );
        String[] result = queue.toArray( new String[ 0 ] );
        assertEquals( 1, result.length );
        assertEquals( "A", result[ 0 ] );
    }

    /** Passing null throws NullPointerException. */
    @Test( expected = NullPointerException.class )
    public void testToArray_null_throwsNullPointerException()
    {
        queue.toArray( null );
    }


    // -----------------------------------------------------------------------
    // equals(QueueADT)
    // -----------------------------------------------------------------------

    /** Two empty queues are equal. */
    @Test
    public void testEquals_twoEmptyQueues_returnsTrue()
    {
        MyQueue other = new MyQueue();
        assertTrue( queue.equals( other ) );
    }

    /** Two queues with the same elements in the same order are equal. */
    @Test
    public void testEquals_sameElementsSameOrder_returnsTrue()
    {
        MyQueue other = new MyQueue();
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        other.enqueue( "A" );
        other.enqueue( "B" );
        assertTrue( queue.equals( other ) );
    }

    /** Two queues with the same elements in different order are not equal. */
    @Test
    public void testEquals_sameElementsDifferentOrder_returnsFalse()
    {
        MyQueue other = new MyQueue();
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        other.enqueue( "B" );
        other.enqueue( "A" );
        assertFalse( queue.equals( other ) );
    }

    /** Two queues with different elements are not equal. */
    @Test
    public void testEquals_differentElements_returnsFalse()
    {
        MyQueue other = new MyQueue();
        queue.enqueue( "A" );
        other.enqueue( "Z" );
        assertFalse( queue.equals( other ) );
    }

    /** A populated queue is not equal to an empty queue. */
    @Test
    public void testEquals_differentSizes_returnsFalse()
    {
        MyQueue other = new MyQueue();
        queue.enqueue( "A" );
        assertFalse( queue.equals( other ) );
    }

    /** A queue is equal to itself (reflexivity). */
    @Test
    public void testEquals_sameInstance_returnsTrue()
    {
        queue.enqueue( "A" );
        assertTrue( queue.equals( queue ) );
    }

    /** Equality holds after the same sequence of enqueues on both queues. */
    @Test
    public void testEquals_afterIdenticalEnqueueSequence_returnsTrue()
    {
        MyQueue other = new MyQueue();
        queue.enqueue( "X" );
        queue.enqueue( "Y" );
        queue.enqueue( "Z" );
        other.enqueue( "X" );
        other.enqueue( "Y" );
        other.enqueue( "Z" );
        assertTrue( queue.equals( other ) );
    }


    // -----------------------------------------------------------------------
    // iterator()
    // -----------------------------------------------------------------------

    /** iterator() never returns null. */
    @Test
    public void testIterator_alwaysReturnsNonNull()
    {
        assertNotNull( queue.iterator() );
    }

    /** An iterator over an empty queue has no elements. */
    @Test
    public void testIterator_emptyQueue_hasNextReturnsFalse()
    {
        Iterator it = queue.iterator();
        assertFalse( it.hasNext() );
    }

    /** An iterator traverses all elements in front-to-back order. */
    @Test
    public void testIterator_populatedQueue_traversesFrontToBack()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );
        queue.enqueue( "C" );
        Iterator it = queue.iterator();

        assertTrue( it.hasNext() );
        assertEquals( "A", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "B", it.next() );

        assertTrue( it.hasNext() );
        assertEquals( "C", it.next() );

        assertFalse( it.hasNext() );
    }

    /** A single-element queue produces a single-step iterator. */
    @Test
    public void testIterator_singleElement_traversesOneElement()
    {
        queue.enqueue( "A" );
        Iterator it = queue.iterator();
        assertTrue( it.hasNext() );
        assertEquals( "A", it.next() );
        assertFalse( it.hasNext() );
    }

    /** Each call to iterator() returns an independent cursor. */
    @Test
    public void testIterator_calledTwice_returnsTwoIndependentIterators()
    {
        queue.enqueue( "A" );
        queue.enqueue( "B" );

        Iterator it1 = queue.iterator();
        Iterator it2 = queue.iterator();

        it1.next();

        assertEquals( "A", it2.next() );
    }

}