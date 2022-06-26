package entities.query.matcher;

import entities.query.QueryTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EQUAL 比较器
 * tag = value1
 * @author zhangsheng
 */
@Data
@NoArgsConstructor
public class EqualMatcher implements QueryExpressionMatcher{

    private Object[] value;

    public static EqualMatcher of(Object... values) {
        EqualMatcher equalMatcher = new EqualMatcher();
        equalMatcher.setValue(values);
        return equalMatcher;
    }

    @Override
    public QueryTypeEnum getQueryType() {
        return QueryTypeEnum.EQUAL;
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
