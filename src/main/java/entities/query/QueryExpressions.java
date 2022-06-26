package entities.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * 查询表达式为多个assignments的集合
 * @author zhangsheng
 */
@Data
@NoArgsConstructor
public class QueryExpressions {

    /**
     * age > 15 ; city = SZ ;  tag1 ∈ {1, 2, 3} ; tag2 = {A and B and C}
     */
    private List<Assignment> assignments;

    /**
     * 表达式长度
     */
    private int expressionsSize;

    public static QueryExpressions of(Assignment... assignments) {
        QueryExpressions queryExpressions = new QueryExpressions();
        queryExpressions.setAssignments(Arrays.asList(assignments));
        queryExpressions.setExpressionsSize(assignments.length);
        return queryExpressions;

    }
}
