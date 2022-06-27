package entities.index;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author zhangsheng
 */
@Data
@NoArgsConstructor
public class Document implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return docId == document.getDocId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(docId);
    }

}
