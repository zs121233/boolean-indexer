package entities.index;

import lombok.Data;

/**
 * BoolValues expression a bool logic like: (in) [15,16,17], (not in) [shanghai,yz] eg
 * @author 80304042
 */
@Data
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
