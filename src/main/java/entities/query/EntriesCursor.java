package entities.query;

import entities.index.Entry;
import lombok.Data;

/**
 * @author zhangsheng
 */
@Data
public class EntriesCursor implements Comparable<EntriesCursor>{
    /**
     * current cur cursor
     */
    private int currentPosition;
    /**
     * postingList中 entry的副本
     */
    private Entry[] entries;

    public static EntriesCursor of(Entry[] entries) {
        EntriesCursor entriesCursor = new EntriesCursor();
        entriesCursor.setEntries(entries);
        return entriesCursor;
    }

    public Entry getCurrentEntry() {
        if(this.currentPosition < this.entries.length) {
            return this.entries[currentPosition];
        }
        return null;
    }

    /**
     * todo： 优化成二分查找?
     * 由于skip是从左到右逐渐增加，二分查找并不能优化多少。甚至是负作用。
     */
    public Entry skip(long id) {
        for (; currentPosition < entries.length; this.currentPosition++) {
            if (entries[currentPosition].getConjunctionId() > id) {
                return entries[currentPosition];
            }
        }
        return null;
    }

    public Entry skipTo(long id) {
        for (; currentPosition < entries.length; this.currentPosition++) {
            if (entries[currentPosition].getConjunctionId() >= id) {
                return entries[currentPosition];
            }
        }
        return null;
    }

    @Override
    public int compareTo(EntriesCursor o) {
        Entry oEntry = o.getCurrentEntry();
        Entry currentEntry = this.getCurrentEntry();
        return currentEntry == null ? (oEntry == null ? 0 : 1) : (oEntry == null ? -1 : currentEntry.compareTo(oEntry));
    }
}
