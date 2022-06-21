package entities.index;

import lombok.Data;
import java.util.Arrays;

/**
 * BooleanExpressions expression a bool logic like: age (in) [15,16,17], city (not in) [shanghai,yz]
 * @author 80304042
 */
@Data
public class BooleanExpression{
    private String attribute;
    private BooleanValues booleanValues;

    public BooleanExpression(String attribute, boolean predicate, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("assignment values count must > 0 ");
        }
        // 对values排序
        Arrays.sort(values);
        this.attribute = attribute;
        this.booleanValues = BooleanValues.of(predicate, values);
    }
}
