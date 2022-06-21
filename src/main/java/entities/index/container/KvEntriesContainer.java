package entities.index.container;

import entities.index.BooleanValues;
import entities.index.Entry;
import entities.query.*;
import entities.query.matcher.QueryExpressionMatcher;
import java.util.*;


/**
 * @author 80304042
 */
public class KvEntriesContainer implements EntriesContainer {
    /**
     * 用于存储values下的Entry[]的map
     * 增删改时Entry[]需要直接覆盖，类似于copyOnWrite否则会有并发问题
     */
    private Map<Object, Entry[]> postingListMap = new HashMap<>();

    @Override
    public void addEntry(BooleanValues booleanValues, long conjunctionId) {
        boolean predicate = booleanValues.isPredicate();
        Object[] values = booleanValues.getValues();
        for (Object value: values) {
            Entry[] entries = postingListMap.get(value);
            if (entries == null) {
                postingListMap.put(value, new Entry[]{Entry.of(predicate, conjunctionId)});
            } else {
                //copy on write
                Entry[] newEntries = Arrays.copyOf(entries, entries.length + 1);
                newEntries[newEntries.length - 1] = Entry.of(predicate, conjunctionId);
                Arrays.sort(newEntries);
                postingListMap.put(value, newEntries);
            }
        }
    }

    @Override
    public AttributeCursor initAttributeCursor(QueryExpressionMatcher queryExpressionMatcher) {
        //等于则根据map存储KV键值对的优势快速返回
        if(QueryType.EQUAL.equals(queryExpressionMatcher.getQueryType())) {
            Entry[] entries = postingListMap.get(queryExpressionMatcher.getValues()[0]);
            if(entries != null) {
                EntriesCursor entriesCursor = EntriesCursor.of(entries);
                return AttributeCursor.of(entriesCursor);
            }
        } else if (QueryType.OR.equals(queryExpressionMatcher.getQueryType())) {
            Object[] values = queryExpressionMatcher.getValues();
            List<EntriesCursor> entriesCursors = new ArrayList<>(values.length);
            for (Object object: queryExpressionMatcher.getValues()) {
                Entry[] entries = postingListMap.get(object);
                if(entries != null) {
                    EntriesCursor entriesCursor = EntriesCursor.of(entries);
                    entriesCursors.add(entriesCursor);
                }
            }
            if(!entriesCursors.isEmpty()) {
                Collections.sort(entriesCursors);
                return AttributeCursor.of(entriesCursors.toArray(new EntriesCursor[]{}));
            }
        }
        return null;
    }
}
