package liuyun.concurrcency.source;

import liuyun.concurrcency.annotations.GuardedBy;
import liuyun.concurrcency.annotations.ThreadSafe;

/**
 * Sequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class Sequence {

    @GuardedBy("this")
    private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }
}
