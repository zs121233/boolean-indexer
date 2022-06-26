package entities.index;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BoolValues expression a bool logic like: (in) [15,16,17], (not in) [shanghai,yz] eg
 * @author zhangsheng
 */
@Data
@NoArgsConstructor
public class BooleanValues {
    /**
     * include: true exclude: false
     */
    private boolean predicate;
    /**
     * values can be parser parse to id
     */
    private Object[] values;

    public static BooleanValues of(boolean predicate, Object... values){
        BooleanValues booleanValues = new BooleanValues();
        booleanValues.setPredicate(predicate);
        booleanValues.setValues(values);
        return booleanValues;
    }
}
