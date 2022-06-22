package entities.query.matcher;

import entities.query.QueryType;
import lombok.Data;

/**
 * EQUAL 比较器
 * tag = value1
 * @author zhangsheng
 */
@Data
public class EqualMatcher implements QueryExpressionMatcher{

    private Object[] value;

    public static EqualMatcher of(Object... values) {
        EqualMatcher equalMatcher = new EqualMatcher();
        equalMatcher.setValue(values);
        return equalMatcher;
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.EQUAL;
    }

    @Override
    public boolean isMatch(Object value) {
        return this.value.equals(value);
    }

    @Override
    public Object[] getValues() {
        return this.value;
    }
}
