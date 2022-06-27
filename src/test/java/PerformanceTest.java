import com.google.common.base.Stopwatch;
import core.BooleanIndex;
import entities.index.Document;
import entities.query.Assignment;
import entities.query.QueryExpressions;
import entities.query.matcher.OrMatcher;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {

    /**
     * 维度数量
     */
    private static final int MAX_PROPERTY_COUNT = 100;
    /**
     * 维度值范围
     */
    private static final int MAX_PROPERTY_VALUE_RANGE = 100;

    private static Random random = new Random();


    /**
     * 性能对比
     * @param args
     */
    public static void main(String[] args) {
        BooleanIndex booleanIndex = BuildIndexTest.buildIndexByFile();
        int retrievalSize = 0;
        int retrievalTimes = 1000;
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < retrievalTimes; i++) {
            QueryExpressions queryExpressions = QueryExpressions.of(randomAssignmentWithSingleValue(30));
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
            Assignment assignment = Assignment.of(attribute, OrMatcher.of(value1, value2, value3, value4, value5));
            assignments.add(assignment);
            keys.add(attribute);
        }
        return assignments.toArray(new Assignment[0]);
    }
}
