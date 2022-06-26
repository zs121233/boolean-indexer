package entities.query;

/**
 * @author zhangsheng
 */
public enum QueryTypeEnum {
    /**
     * = only one value
     */
    EQUAL,
    /**
     * A and B
     */
    AND,
    /**
     * A OR B
     */
    OR,
    /**
     * > A
     */
    OVER,
    /**
     * [A, B]
     */
    RANGE

}
