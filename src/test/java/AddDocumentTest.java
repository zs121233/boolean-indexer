import core.BooleanIndex;
import entities.index.BooleanExpression;
import entities.index.Conjunction;
import entities.index.Document;
import entities.query.Assignment;
import entities.query.QueryExpressions;
import entities.query.matcher.EqualMatcher;

import java.util.Collections;
import java.util.List;

public class AddDocumentTest {

    public static void main(String[] args) {
        BooleanIndex booleanIndex = new BooleanIndex();
        Document document = mockDoc();
        booleanIndex.addDocument(document);
        List<Document> tag = booleanIndex.retrieve(QueryExpressions.of(Assignment.of("tag", EqualMatcher.of(1))));
        System.out.println(tag);
    }

    private static Document mockDoc(){
        Document document = new Document();
        document.setDocId(1);
        document.setConjunctions(Collections.singletonList(mockConj()));
        return document;
    }

    private static Conjunction mockConj(){
        return Conjunction.of(new BooleanExpression("tag", true, 1,2,3));
    }
}
