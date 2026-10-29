package utilities;

import java.util.NoSuchElementException;

/**
 * A forward-only iterator interface for traversing elements of a custom
 * linear data structure in CPRG 304 at SAIT.
 * <p>
 * Unlike {@link java.util.Iterator}, this iterator is designed to operate on
 * a deep copy of the underlying collection, ensuring safe and independent
 * traversal that is unaffected by structural modifications made to the
 * original data structure after the iterator is created.
 * </p>
 *
 * <p><strong>Design Note – String-Specific Implementation:</strong><br>
 * This interface is intentionally designed to work with {@link String} elements
 * only. A fully reusable, type-safe design would use Java generics (e.g.,
 * {@code Iterator<E>}), allowing iteration over any object type.
 * Generic programming is outside the scope of CPRG 304 and has therefore
 * been omitted. All element return types are fixed as {@link String}
 * throughout this interface and its implementations.
 * </p>
 *
 * <h3>Implementation Requirements</h3>
 * <p>
 * All implementations of this interface <strong>must</strong> adhere to the
 * following contract to guarantee safe traversal:
 * </p>
 * <ul>
 *   <li>The constructor of the implementing class must create a <em>deep copy</em>
 *       of the underlying collection's elements at the time the iterator is
 *       instantiated.</li>
 *   <li>All subsequent calls to {@link #hasNext()} and {@link #next()} must
 *       operate exclusively on this internal copy, never on the original
 *       data structure.</li>
 *   <li>The iterator must not reflect any insertions, deletions, or replacements
 *       made to the original collection after the iterator was created.</li>
 *   <li>The internal copy must not be exposed or modified by any external
 *       reference after construction.</li>
 * </ul>
 * <p>
 * A typical usage pattern is:
 * </p>
 * <pre>{@code
 * Iterator it = list.iterator();
 * while ( it.hasNext() )
 * {
 *     String element = it.next();
 *     // process element
 * }
 * }</pre>
 */
public interface Iterator
{
    /**
     * Returns {@code true} if the iteration has more elements remaining.
     * <p>
     * This method should be called before each invocation of {@link #next()}
     * to guard against a {@link NoSuchElementException}. This method must
     * operate on the internal deep copy of the collection established at
     * iterator creation and must not query the original data structure.
     * </p>
     *
     * @return {@code true} if at least one more element exists in the
     *         iteration sequence; {@code false} otherwise
     */
    public boolean hasNext();

    /**
     * Returns the next {@link String} element in the iteration sequence and
     * advances the iterator position by one.
     * <p>
     * {@link #hasNext()} should be checked prior to calling this method to
     * ensure elements remain; calling {@code next()} when no elements remain
     * will result in a {@link NoSuchElementException}. This method must
     * retrieve elements from the internal deep copy of the collection and
     * must never access or modify the original data structure.
     * </p>
     *
     * @return the next {@link String} element in the iteration sequence
     * @throws NoSuchElementException if the iteration has no more elements
     */
    public String next() throws NoSuchElementException;
}