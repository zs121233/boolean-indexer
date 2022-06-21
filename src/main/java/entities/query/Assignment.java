package entities.query;

import entities.query.matcher.QueryExpressionMatcher;
import lombok.Data;

/**
 * @author zhangsheng
 */
@Data
public class Assignment {
    /**
     * 属性值
     */
    private String attribute;
    /**
     * 数值匹配器
     */
    private QueryExpressionMatcher queryExpressionMatcher;

    public static Assignment of(String attribute, QueryExpressionMatcher queryExpressionMatcher) {
        Assignment assignment = new Assignment();
        assignment.setAttribute(attribute);
        assignment.setQueryExpressionMatcher(queryExpressionMatcher);
        return assignment;
    }
}
