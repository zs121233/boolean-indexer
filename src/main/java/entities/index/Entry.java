package entities.index;

import lombok.Data;

import java.io.Serializable;
/**
 * Each entry of a posting list represents a conjunction c and contains the ID of c and a bit
 * indicating whether the key (A, v) is involved in an ∈ or 6∈ predicate in c (ignore the third value for now).
 * like : (C4, ∈)
 * @author zhangsheng
 */
@Data
public class Entry implements Comparable<Entry>, Serializable {
    /**
     * include: true exclude: false
     */
    private boolean predicate;

    /**
     * conjunctionId
     */
    private long conjunctionId;

    public static Entry of(boolean predicate, long conjunctionId) {
        Entry entry = new Entry();
        entry.setConjunctionId(conjunctionId);
        entry.setPredicate(predicate);
        return entry;
    }

    @Override
    public int compareTo(Entry o) {
        // 1. 优先比较conjunction id大小
        // 2. 如果conjunction id一样,∉ < ∈
        if (this.conjunctionId != o.conjunctionId) {
            return Long.compare(this.conjunctionId, o.conjunctionId);
        } else {
            return (predicate == o.predicate) ? 0 : (predicate ? 1 : -1);
        }
    }

    @Override
    public String toString() {
        return "(" + conjunctionId + "," + (predicate? "∈" : "∉") + ")";
    }



}
