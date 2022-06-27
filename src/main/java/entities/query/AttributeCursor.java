package entities.query;

import entities.index.Entry;
import lombok.Data;
import java.util.Collections;
import java.util.List;

/**
 * AttributeCursor for a boolean expression: {"attr1", "in", [value1, value2]}
 * attr1_value1: [Entry5]
 * attr1_value2: [Entry1,Entry2, Entry7]
 *
 * @author zhangsheng
 */
@Data
public class AttributeCursor implements Comparable<AttributeCursor>{

    /**
     * current entriesCursor of the min entry
     */
    private EntriesCursor currentEntriesCursor;

    /**
     * 一个属性下可能有多值查询，则命中多个entriesCursor
     * attr1_value1: [Entry5]
     * attr1_value2: [Entry1, Entry2, Entry7]
     */
    private List<EntriesCursor> entriesCursors;

    public static AttributeCursor of(List<EntriesCursor> entriesCursors) {
        AttributeCursor attributeCursor = new AttributeCursor();
        attributeCursor.setCurrentEntriesCursor(Collections.min(entriesCursors));
        attributeCursor.setEntriesCursors(entriesCursors);
        return attributeCursor;
    }

    /**
     * 返回当前指针指向的Entry
     * todo 优化直接持有该entry的引用
     */
    public Entry getCurrentEntry() {
        if (currentEntriesCursor != null) {
            return currentEntriesCursor.getCurrentEntry();
        }
        return null;
    }

    public void skipTo(long conjunctionId) {
        long minNum = Long.MAX_VALUE;
        boolean change = false;
        for (EntriesCursor entriesCursor : entriesCursors) {
            Entry entry = entriesCursor.skipTo(conjunctionId);
            if (entry == null) {
                continue;
            }
            long skipId = entry.getConjunctionId();
            if (entry.getConjunctionId() < minNum) {
                minNum = skipId;
                this.currentEntriesCursor = entriesCursor;
                change = true;
            }
        }
        if(!change) {
            this.currentEntriesCursor = null;
        }
    }

    public void skip(long conjunctionId) {
        long minNum = Long.MAX_VALUE;
        boolean change = false;
        for (EntriesCursor entriesCursor : entriesCursors) {
            Entry entry = entriesCursor.skip(conjunctionId);
            if (entry == null) {
                continue;
            }
            long skipId = entry.getConjunctionId();
            if (entry.getConjunctionId() < minNum) {
                minNum = skipId;
                this.currentEntriesCursor = entriesCursor;
                change = true;
            }
        }
        if(!change) {
            this.currentEntriesCursor = null;
        }
    }

    /**
     * 排序规则
     * @param o
     * @return
     */
    @Override
    public int compareTo(AttributeCursor o) {
        Entry oEntry = o.getCurrentEntry();
        Entry currentEntry = this.getCurrentEntry();
        return currentEntry == null ? (oEntry == null ? 0 : 1) : (oEntry == null ? -1 : currentEntry.compareTo(oEntry));
    }
}
