package exceptions;

/**
 * Thrown to indicate that an operation was attempted on an empty queue.
 * <p>
 * This exception is intended for use in CPRG 304 at SAIT, specifically in
 * implementations of {@code QueueADT} such as {@code MyQueue}. It is thrown
 * when a destructive or access operation — such as {@code dequeue()} or
 * {@code peek()} — is called on a queue that contains no elements.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * if ( queue.isEmpty() )
 * {
 *     throw new EmptyQueueException( "Cannot dequeue from an empty queue." );
 * }
 * }</pre>
 */
public class EmptyQueueException extends Exception
{
    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    /** Serialization version identifier. */
    private static final long serialVersionUID = 682267963980463371L;


    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a new {@link EmptyQueueException} with no detail message.
     * <p>
     * The cause is not initialized and may be set later by calling
     * {@link Throwable#initCause(Throwable)}.
     * </p>
     */
    public EmptyQueueException()
    {
        super();
    }

    /**
     * Constructs a new {@link EmptyQueueException} with the specified
     * detail message.
     * <p>
     * The detail message should describe the context in which the exception
     * was thrown, for example:
     * {@code "Cannot dequeue from an empty queue."}.
     * The message can be retrieved later via {@link Throwable#getMessage()}.
     * </p>
     *
     * @param message  a {@link String} describing the context in which the
     *                 exception was thrown
     */
    public EmptyQueueException( String message )
    {
        super( message );
    }
}