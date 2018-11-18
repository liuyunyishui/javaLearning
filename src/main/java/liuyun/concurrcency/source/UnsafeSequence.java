package liuyun.concurrcency.source;


import liuyun.concurrcency.annotations.NotThreadSafe;

/**
 * UnsafeSequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeSequence {

    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
