package entities.index.container;

import entities.index.BooleanValues;
import entities.query.AttributeCursor;
import entities.query.matcher.QueryExpressionMatcher;


/**
 * @author zhangsheng
 */
public class RangeEntriesContainer implements EntriesContainer {

    @Override
    public void addEntry(BooleanValues booleanValues, long conjunctionId) {

    }

    @Override
    public AttributeCursor initAttributeCursor(QueryExpressionMatcher assignment) {
        return null;
    }
}
