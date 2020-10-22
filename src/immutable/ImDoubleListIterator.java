package immutable;

import java.util.Iterator;

public class ImDoubleListIterator implements Iterator<String> {
    /* rep invariant
     * remaining != null
     */
    ImDoubleList remaining;

    public ImDoubleListIterator (ImDoubleList list) {
        remaining = list;
    }

    public boolean hasNext () {
        return !remaining.isEmpty();
    }

    public String next () {
        String first = remaining.first();
        remaining = remaining.rest();
        return first;
    }
    
    public void remove () {
        throw new UnsupportedOperationException ();
    }
}