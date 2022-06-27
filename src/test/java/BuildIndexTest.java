import com.alibaba.fastjson.JSON;
import core.BooleanIndex;
import dump.DumpUtil;
import entities.index.BooleanExpression;
import entities.index.Conjunction;
import entities.index.Document;
import mock.MockDocumentTest;

import java.util.*;

public class BuildIndexTest {

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


    public static void main(String[] args) {
        BooleanIndex booleanIndex = buildIndexByRandom(MAX_DOCUMENTS);
    }
    public static BooleanIndex buildIndexByRandom(int docNums) {
        BooleanIndex booleanIndex = new BooleanIndex();
        for (int i = 0; i < docNums; i++) {
            Conjunction conjunction = randomConjunction(random.nextInt(MAX_PROPERTY_COUNT));
            Document document = Document.of(i, conjunction);
            booleanIndex.addDocument(document);
        }
        return booleanIndex;
    }


    public static BooleanIndex buildIndexByFile() {
        BooleanIndex booleanIndex = new BooleanIndex();
        List<Document> documents = MockDocumentTest.mockDocsByFile();
        int docNums = 0;
        for (Document document : documents) {
            booleanIndex.addDocument(document);
            docNums ++;
        }
        System.out.println("index docNums=" + docNums);
        return booleanIndex;
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
