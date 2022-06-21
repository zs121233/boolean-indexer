package entities.index;

import lombok.Data;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author zhangsheng
 */
@Data
public class Document {

    /**
     * 文档id
     */
    private long docId;

    /**
     * conjunction之间的关系是或，具体描述可以看论文的表述
     */
    private Collection<Conjunction> conjunctions;

    public static Document of(long docId, Conjunction... conjunctions) {
        Document document = new Document();
        document.setDocId(docId);
        document.setConjunctions(Arrays.asList(conjunctions));
        return document;
    }


}
