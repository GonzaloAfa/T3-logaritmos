/**
 * Created by Ian on 30-06-2014.
 */
public final class Entry {
    public int mDegree = 0;       // Number of children
    public boolean mIsMarked = false; // Whether this node is marked

    public Entry mNext;   // Next and previous elements in the list
    public Entry mPrev;

    public Entry mParent; // Parent in the tree, if any.

    public Entry mChild;  // Child node, if any.

    private HeapNode mElem;     // Element being stored here
    public double mPriority; // Its priority

    /**
     * Returns the element represented by this heap entry.
     *
     * @return The element represented by this heap entry.
     */
    public HeapNode getValue() {
        return mElem;
    }

    /**
     * Sets the element associated with this heap entry.
     *
     * @param value The element to associate with this heap entry.
     */
    public void setValue(HeapNode value) {
        mElem = value;
    }

    /**
     * Returns the priority of this element.
     *
     * @return The priority of this element.
     */
    public double getPriority() {
        return mPriority;
    }

    /**
     * Constructs a new Entry that holds the given element with the indicated
     * priority.
     *
     * @param elem     The element stored in this node.
     * @param priority The priority of this element.
     */
    public Entry(HeapNode elem, double priority) {
        mNext = mPrev = this;
        mElem = elem;
        mPriority = priority;
    }

}

