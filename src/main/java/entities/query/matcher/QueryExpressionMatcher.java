package entities.query.matcher;

import entities.query.QueryType;

/**
 * @author 80304042
 */
public interface QueryExpressionMatcher {

    QueryType getQueryType();

    boolean isMatch(Object values);

    Object[] getValues();

}
