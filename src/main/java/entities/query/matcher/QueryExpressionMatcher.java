package entities.query.matcher;

import entities.query.QueryTypeEnum;
import java.io.Serializable;

/**
 * @author zhangsheng
 */
public interface QueryExpressionMatcher extends Serializable {

    QueryTypeEnum getQueryType();

    boolean isMatch(Object values);

    Object[] getValues();

}
