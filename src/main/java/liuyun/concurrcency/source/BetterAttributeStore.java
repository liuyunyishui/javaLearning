package liuyun.concurrcency.source;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import liuyun.concurrcency.annotations.GuardedBy;
import liuyun.concurrcency.annotations.ThreadSafe;

/**
 * BetterAttributeStore
 * <p/>
 * Reducing lock duration
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BetterAttributeStore {

    @GuardedBy("this")
    private final Map<String, String>
            attributes = new HashMap<String, String>();

    public boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null) {
            return false;
        } else {
            return Pattern.matches(regexp, location);
        }
    }
}
