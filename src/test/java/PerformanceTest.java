import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import core.BooleanIndex;
import entities.index.BooleanExpression;
import entities.index.Conjunction;
import entities.index.Document;
import entities.query.Assignment;
import entities.query.QueryExpressions;
import entities.query.matcher.OrMatcher;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class PerformanceTest {

    private static final String booleanExpression = "{\"assignments\":[{\"attribute\":\"type_38\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[0,61,33,23,57]}},{\"attribute\":\"type_4\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[36,89,38,18,65]}},{\"attribute\":\"type_0\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[1,96,28,69,59]}},{\"attribute\":\"type_81\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[55,12,75,9,39]}},{\"attribute\":\"type_82\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[17,99,11,0,91]}},{\"attribute\":\"type_63\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[63,40,14,36,48]}},{\"attribute\":\"type_46\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[17,4,90,47,56]}},{\"attribute\":\"type_58\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[84,40,96,9,44]}},{\"attribute\":\"type_6\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[24,87,2,18,73]}},{\"attribute\":\"type_50\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[99,0,99,57,78]}},{\"attribute\":\"type_19\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[61,24,62,59,47]}},{\"attribute\":\"type_36\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[19,82,33,87,23]}},{\"attribute\":\"type_24\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[3,34,49,66,92]}},{\"attribute\":\"type_98\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[1,68,94,0,33]}},{\"attribute\":\"type_93\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[98,48,46,52,62]}},{\"attribute\":\"type_54\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[36,62,7,42,13]}},{\"attribute\":\"type_55\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[71,22,76,40,2]}},{\"attribute\":\"type_16\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[6,29,24,81,83]}},{\"attribute\":\"type_99\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[68,45,6,77,10]}},{\"attribute\":\"type_65\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[27,37,33,30,87]}},{\"attribute\":\"type_78\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[0,13,12,91,56]}},{\"attribute\":\"type_67\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[61,32,49,52,33]}},{\"attribute\":\"type_12\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[99,54,2,88,5]}},{\"attribute\":\"type_14\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[33,85,30,15,88]}},{\"attribute\":\"type_5\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[86,41,4,71,47]}},{\"attribute\":\"type_28\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[2,82,85,79,58]}},{\"attribute\":\"type_10\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[36,74,48,57,69]}},{\"attribute\":\"type_40\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[64,7,84,11,91]}},{\"attribute\":\"type_89\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[91,18,40,2,99]}},{\"attribute\":\"type_33\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[46,9,93,9,47]}},{\"attribute\":\"type_69\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[70,33,89,96,49]}},{\"attribute\":\"type_8\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[13,62,46,54,81]}},{\"attribute\":\"type_73\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[35,67,66,40,98]}},{\"attribute\":\"type_97\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[31,38,68,55,68]}},{\"attribute\":\"type_1\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[49,33,42,88,72]}},{\"attribute\":\"type_42\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[99,46,43,94,3]}},{\"attribute\":\"type_49\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[47,11,15,51,14]}},{\"attribute\":\"type_71\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[8,41,2,33,96]}},{\"attribute\":\"type_77\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[89,42,20,21,96]}},{\"attribute\":\"type_83\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[24,55,43,13,31]}},{\"attribute\":\"type_7\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[8,64,57,43,1]}},{\"attribute\":\"type_95\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[1,48,84,44,0]}},{\"attribute\":\"type_32\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[11,96,74,9,59]}},{\"attribute\":\"type_68\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[21,96,59,95,79]}},{\"attribute\":\"type_23\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[85,5,22,35,6]}},{\"attribute\":\"type_84\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[25,55,24,61,42]}},{\"attribute\":\"type_21\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[67,91,4,63,55]}},{\"attribute\":\"type_57\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[99,98,45,20,31]}},{\"attribute\":\"type_86\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[34,42,58,94,81]}},{\"attribute\":\"type_22\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[79,98,73,56,82]}},{\"attribute\":\"type_45\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[47,34,49,51,72]}},{\"attribute\":\"type_35\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[73,0,94,25,89]}},{\"attribute\":\"type_25\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[49,54,46,65,59]}},{\"attribute\":\"type_47\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[36,0,46,68,69]}},{\"attribute\":\"type_90\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[97,54,1,51,16]}},{\"attribute\":\"type_74\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[14,91,49,94,12]}},{\"attribute\":\"type_79\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[59,26,39,39,49]}},{\"attribute\":\"type_18\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[99,93,63,81,29]}},{\"attribute\":\"type_88\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[90,25,78,39,16]}},{\"attribute\":\"type_41\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[14,49,38,22,52]}},{\"attribute\":\"type_39\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[96,97,33,8,43]}},{\"attribute\":\"type_30\",\"queryExpressionMatcher\":{\"queryType\":\"OR\",\"values\":[82,84,16,72,47]}}],\"expressionsSize\":62}\n";

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
            Assignment assignment = Assignment.of(attribute, OrMatcher.of(value1,value2,value3,value4,value5));
            assignments.add(assignment);
            keys.add(attribute);
        }
        return assignments.toArray(new Assignment[0]);
    }
}
