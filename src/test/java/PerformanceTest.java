import com.google.common.base.Stopwatch;
import core.BooleanIndex;
import entities.index.BooleanExpression;
import entities.index.Conjunction;
import entities.index.Document;
import entities.query.Assignment;
import entities.query.QueryExpressions;
import entities.query.matcher.EqualMatcher;
import entities.query.matcher.OrMatcher;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {

    /**
     * 文档数量
     */
    private static final int MAX_DOCUMENTS = 20000;

    /**
     * 维度数量
     */
    private static final int MAX_PROPERTY_COUNT = 100;

    /**
     * 维度值数量
     */
    private static final int MAX_PROPERTY_VALUES_COUNT = 100;

    private static final int MIN_PROPERTY_VALUES_COUNT = 5;
    /**
     * 维度值范围
     */
    private static final int MAX_PROPERTY_VALUE_RANGE = 100;

    private static final int HIGH_DIMENSION_RATIO = 20;

    private static Random random = new Random();


    /**
     * 性能对比
     * @param args
     */
    public static void main(String[] args) {
        BooleanIndex booleanIndex = new BooleanIndex();

        for (int i = 0; i < MAX_DOCUMENTS; i++) {
            Conjunction conjunction = randomConjunction(random.nextInt(MAX_PROPERTY_COUNT));
            booleanIndex.addDocument(Document.of(i, conjunction));
        }
        QueryExpressions queryExpressions = QueryExpressions.of(randomAssignmentWithSingleValue(100));
        int retrievalSize = 0;
        int retrievalTimes = 100;
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < retrievalTimes; i++) {
            List<Document> retrieve = booleanIndex.retrieve(queryExpressions);
            retrievalSize += retrieve.size();
        }
        stopwatch.stop();
        System.out.println("retrieve " + retrievalTimes + " times cost : " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
        System.out.println("retrieve " + retrievalTimes + " times of total documents size : " + retrievalSize);
        System.out.println("retrieve " + retrievalTimes + " times of avg costs : " + stopwatch.elapsed(TimeUnit.MICROSECONDS) / retrievalTimes + " micro seconds");
    }

    private static Assignment[] randomAssignmentWithSingleValue(int count) {
        if (count >= MAX_PROPERTY_COUNT) {
            count = MAX_PROPERTY_COUNT;
        }
        if (count == 0) {
            count = 1;
        }
        List<Assignment> assignments = new ArrayList<>();
        Set<String> keys = new HashSet<>();
        for (int i = 0; i < count; i++) {
            // 100个属性
            String attribute = "type_" + random.nextInt(MAX_PROPERTY_COUNT);
            if (keys.contains(attribute)) {
                continue;
            }
            Object value1 = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
            Object value2 = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
            Object value3 = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
            Object value4 = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
            Object value5 = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
            Assignment assignment = Assignment.of(attribute, OrMatcher.of(value1,value2,value3,value4,value5));
            assignments.add(assignment);
            keys.add(attribute);
        }
        return assignments.toArray(new Assignment[0]);
    }

    private static Conjunction randomConjunction(int count) {
        if (count >= MAX_PROPERTY_COUNT) {
            count = MAX_PROPERTY_COUNT;
        }
        if (count == 0) {
            count = 1;
        }
        Set<String> attributes = new HashSet<>();
        List<BooleanExpression> assignments = new ArrayList<>();
        int ratio = random.nextInt(100);
        for (int i = 0; i < count; i++) {
            // 100个属性
            String attribute = "type_" + random.nextInt(MAX_PROPERTY_COUNT);
            if (attributes.contains(attribute)) {
                continue;
            }
            // value size must > 0
            int valuesSize = random.nextInt(MIN_PROPERTY_VALUES_COUNT) + 1;
            if (ratio <= HIGH_DIMENSION_RATIO) {
                valuesSize = random.nextInt(MAX_PROPERTY_VALUES_COUNT) + 1;
            }

            Set<Object> values = new HashSet<>();
            for (int j = 0; j < valuesSize; j++) {
                // 值范围100以内
                Object value = random.nextInt(MAX_PROPERTY_VALUE_RANGE);
                values.add(value);
            }
            BooleanExpression booleanExpression = new BooleanExpression(attribute, true, values.toArray(new Object[0]));
            assignments.add(booleanExpression);
            attributes.add(attribute);
        }
        return Conjunction.of(assignments.toArray(new BooleanExpression[0]));
    }
}
