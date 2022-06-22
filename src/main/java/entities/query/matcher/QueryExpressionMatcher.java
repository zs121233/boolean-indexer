package entities.query.matcher;

import entities.query.QueryType;

/**
 * @author zhangsheng
 */
public interface QueryExpressionMatcher {

    QueryType getQueryType();

    boolean isMatch(Object values);

    Object[] getValues();

}
