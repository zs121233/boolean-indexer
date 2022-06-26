package entities.query.matcher;

import entities.query.QueryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OR 比较器
 * tag ∈ {value1, value2, value3}
 * @author zhangsheng
 */
@Data
@NoArgsConstructor
public class OrMatcher implements QueryExpressionMatcher{

    private Object[] values;

    public static OrMatcher of(Object... values) {
        OrMatcher orMatcher = new OrMatcher();
        orMatcher.setValues(values);
        return orMatcher;
    }

    @Override
    public QueryTypeEnum getQueryType() {
        return QueryTypeEnum.OR;
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
