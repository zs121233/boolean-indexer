package mock;

import com.alibaba.fastjson.JSON;
import entities.index.BooleanExpression;
import entities.index.Conjunction;
import entities.index.Document;

import java.io.*;
import java.util.*;

public class MockDocumentTest {

    /**
     * 文档数量
     */
    private static final int MAX_DOCUMENTS = 40000;

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

    private static String path = "E:/document/documents.json";

    public static void main(String[] args) {
        //路径
        File file = new File(path);
        //判断路径是否存在，不存在就创建
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        try{
            file.createNewFile();
            //写入的路径 和 编码格式
            Writer writer = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            for (int i = 0; i < MAX_DOCUMENTS; i++) {
                Document document = mockDocument(i);
                writer.write(JSON.toJSONString(document) + "\n");
            }
            writer.flush();
            writer.close();
        }catch (Exception exception){
            System.out.println("dump error !");
        }
    }

    public static Document mockDocument(int docId) {
        Conjunction conjunction = mockRandomConjunction(random.nextInt(MAX_PROPERTY_COUNT));
        return Document.of(docId, conjunction);
    }


    public static Conjunction mockRandomConjunction(int count) {
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


    public static List<Document> mockDocsByFile() {
        List<Document> documents = new ArrayList<>();
        BufferedReader bre = null;
        String str="";
        try {
            bre = new BufferedReader(new FileReader(path));//此时获取到的bre就是整个文件的缓存流
            while ((str = bre.readLine())!= null) // 判断最后一行不存在，为空结束循环
            {
                Document document = JSON.parseObject(str, Document.class);
                documents.add(document);
            }
        }catch (Exception e){
            System.out.println("read docs error !");
        } finally {
            assert bre != null;
            try {
                bre.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return documents;
    }
}
