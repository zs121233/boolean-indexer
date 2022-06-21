package entities.index;

import lombok.Data;
import utils.IdUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author zhangsheng
 */
@Data
public class Conjunction {
    /**
     *
     */
    private long conjunctionId;
    /**
     * We define the size of a conjunction to be the number of ∈ predicates
     */
    private int conjunctionSize;

    private List<BooleanExpression> booleanExpressions;

    public static Conjunction of(BooleanExpression... booleanExpressions) {
        Conjunction conjunction = new Conjunction();
        List<BooleanExpression> booleanExpressionsList = Arrays.asList(booleanExpressions);
        booleanExpressionsList.sort(Comparator.comparing(BooleanExpression::getAttribute));
        conjunction.setBooleanExpressions(booleanExpressionsList);
        conjunction.setConjunctionSize(initConjunctionSize(conjunction.getBooleanExpressions()));
        conjunction.setConjunctionId(IdUtils.generateId(conjunction.getConjunctionSize(), conjunction.getBooleanExpressions().toString()));
        return conjunction;
    }

    private static int initConjunctionSize(List<BooleanExpression> booleanExpressions) {
        int conjunctionSize = 0;
        for (BooleanExpression booleanExpression : booleanExpressions) {
            if (booleanExpression.getBooleanValues().isPredicate()) {
                conjunctionSize++;
            }
        }
        return conjunctionSize;
    }
}
