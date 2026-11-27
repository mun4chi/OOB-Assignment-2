package implementations;

/**
 * A single node in a doubly linked list (DLL), used as the building block for
 * {@link MyDLL} in CPRG 304 at SAIT.
 * <p>
 * Each node stores a {@link String} element and maintains two directional
 * references: a predecessor ({@code pred}) pointing to the node immediately
 * before it in the chain, and a successor ({@code succ}) pointing to the node
 * immediately after it. A {@code null} predecessor indicates the node is the
 * head of the list; a {@code null} successor indicates it is the tail.
 * </p>
 *
 * <p>
 * <strong>Design Note – String-Specific Implementation:</strong><br>
 * This class is intentionally designed to store {@link String} elements only. A
 * fully reusable, type-safe design would use Java generics (e.g.,
 * {@code DLLNode<E>}), allowing any object type to be stored in the node.
 * Generic programming is outside the scope of CPRG 304 and has therefore been
 * omitted. All element fields, parameters, and return types are fixed as
 * {@link String} throughout this class and its usages.
 * </p>
 */
public class MyDLLNode {
	/** The string element stored in this node */
	private String element;
	/** The node before this one or null if this node is the head */
	private MyDLLNode pred;
	/** The node after this one or null if the node is the tail */
	private MyDLLNode succ;

	/**
	 * Creates a node holding the given element. it will be linked to the given
	 * predecessor and successor.
	 * 
	 * @param element the string to store in the node.
	 * @param pred    the node before this one or null if this is the head.
	 * @param succ    the node after this one or null if this is the tail.
	 */
	public MyDLLNode(String element, MyDLLNode pred, MyDLLNode succ) {
		this.element = element;
		this.pred = pred;
		this.succ = succ;

	}

	/**
	 * Creates a node holding the given element with no links.
	 * 
	 * @param element the string to store in this node
	 */
	public MyDLLNode(String element) {
		this(element, null, null);
	}

	/**
	 * Returns the element stored in the node.
	 * 
	 * @return the String element held by this node.
	 */
	public String getElement() {
		return element;
	}

	/**
	 * Replaces the element stored in the node
	 * 
	 * @param element the new String to be stored.
	 */
	public void setElement(String element) {
		this.element = element;
	}

	/**
	 * Returns the node before this one
	 * 
	 * @return the predecessor node or null if the node is the head.
	 */
	public MyDLLNode getPred() {
		return pred;

	}

	/**
	 * Sets the node before this one.
	 * 
	 * @param pred the new predecessor node or null to make this node the head.
	 * 
	 */
	public void setPred(MyDLLNode pred) {
		this.pred = pred;
	}

	/**
	 * Returns the node after this one
	 * 
	 * @return the successor node or null if the node is the tail.
	 */
	public MyDLLNode getSucc() {
		return succ;

	}

	/**
	 * Sets the node after this one
	 * 
	 * @param succ the new successor node or null to make this the tail
	 * 
	 */
	public void setSucc(MyDLLNode succ) {
		this.succ = succ;
	}
}