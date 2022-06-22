package entities.query.matcher;

import entities.query.QueryType;
import lombok.Setter;

/**
 * OR 比较器
 * tag ∈ {value1, value2, value3}
 * @author zhangsheng
 */
@Setter
public class OrMatcher implements QueryExpressionMatcher{

    private Object[] values;

    public static OrMatcher of(Object... values) {
        OrMatcher orMatcher = new OrMatcher();
        orMatcher.setValues(values);
        return orMatcher;
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.OR;
    }

    @Override
    public boolean isMatch(Object values) {
        return false;
    }

    @Override
    public Object[] getValues() {
        return this.values;
    }
}
