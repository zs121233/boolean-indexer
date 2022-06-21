package core;

import entities.index.*;
import entities.index.container.EntriesContainer;
import entities.query.Assignment;
import entities.query.AttributeCursor;
import entities.query.QueryExpressions;
import java.util.*;


/**
 * @author zhangsheng
 */
public class BooleanIndex {


    /**
     * todo 根据属性id的总数来确定数组长度
     */
    private SizeSubBooleanIndex[] sizeSubBooleanIndices = new SizeSubBooleanIndex[100];

    private final Map<Long, Set<Document>> conjunctionToDocuments = new HashMap<>();


    public void addDocument(Document document) {
        //check doc valid
        if(!checkValidDoc(document)) {
            return;
        }
        this.buildDocEntries(document);
    }

    private void buildDocEntries(Document document) {
        //DNF表达式由多个Conj组成
        Collection<Conjunction> conjunctions = document.getConjunctions();
        for (Conjunction conj: conjunctions) {
            int conjunctionSizeK = conj.getConjunctionSize();
            //获取第K层的sub子索引
            SizeSubBooleanIndex sizeSubBooleanIndex = getOrInitKSizeContainers(conjunctionSizeK);
            for (BooleanExpression booleanExpression : conj.getBooleanExpressions()) {
                String attribute = booleanExpression.getAttribute();
                //获取该层attribute属性对应的container
                EntriesContainer entriesContainer = sizeSubBooleanIndex.getEntriesContainer(attribute);
                //将booleanValues加入容器entriesContainer中
                entriesContainer.addEntry(booleanExpression.getBooleanValues(), conj.getConjunctionId());
            }
            Set<Document> documents = this.conjunctionToDocuments.get(conj.getConjunctionId());
            if (documents != null) {
                documents.add(document);
            } else {
                Set<Document> documentSet = new HashSet<>();
                documentSet.add(document);
                conjunctionToDocuments.put(conj.getConjunctionId(), documentSet);
            }
        }
    }

    /**
     * get K size sub BooleanIndex
     */
    private SizeSubBooleanIndex getOrInitKSizeContainers(int conjunctionSizeK) {
        SizeSubBooleanIndex kSizeSubBooleanIndices = sizeSubBooleanIndices[conjunctionSizeK];
        if (kSizeSubBooleanIndices != null) {
            return kSizeSubBooleanIndices;
        } else {
            SizeSubBooleanIndex sizeSubBooleanIndex = new SizeSubBooleanIndex();
            sizeSubBooleanIndices[conjunctionSizeK] = sizeSubBooleanIndex;
            return sizeSubBooleanIndex;
        }
    }

    private SizeSubBooleanIndex getKSizeContainers(int conjunctionSizeK) {
        return sizeSubBooleanIndices[conjunctionSizeK];
    }

    private boolean checkValidDoc(Document document) {
        return true;
    }


    /**
     * Algorithm 1 The Conjunction algorithm
     * Retrieve scan index data and retrieve satisfied document
     */
    public List<Document> retrieve(QueryExpressions queries) {
        int expressionsSize = queries.getExpressionsSize();
        List<Document> documents = new ArrayList<>();
        for (int k = 0; k <= expressionsSize; k++) {
            retrieveKSizeDoc(queries, documents, k);
        }
        return documents;


    }

    private void retrieveKSizeDoc(QueryExpressions queries, List<Document> documents, int k) {
        //获取第k层索引
        SizeSubBooleanIndex kSizeSubBooleanIndex = getKSizeContainers(k);
        if(kSizeSubBooleanIndex == null) {
            return;
        }
        //查询第k层的attributeCursor
        List<AttributeCursor> attributeCursors = this.initKSizeAttributeCursor(queries, kSizeSubBooleanIndex);
        //Processing K=0 and K=1 are identical if K=0 then K ←1
        if(k == 0) {
            k = 1;
        }
        // if PLists.size() < K then continue to next for loop iteration
        if(attributeCursors.size() < k) {
            return;
        }
        //SortByCurrentEntries(PLists)
        Collections.sort(attributeCursors);
        //while PLists[K-1].CurrEntry != EOL do
        while (attributeCursors.get(k - 1).getCurrentEntry() != null) {
            long nextId = attributeCursors.get(k - 1).getCurrentEntry().getConjunctionId();
            // Check if the first K posting lists have the same conjunction ID in their current entries
            if (attributeCursors.get(0).getCurrentEntry().getConjunctionId() == nextId) {
                // Reject conjunction if a ∉ predicate is violated
                if (!attributeCursors.get(0).getCurrentEntry().isPredicate()) {
                    // 如果当前entry是不属于的关系,则表示不满足匹配条件,因为给的的检索条件都是属于.
                    long rejectId = attributeCursors.get(0).getCurrentEntry().getConjunctionId();
                    // 由于当前entry(conjunctionId,∈/∉)不满足,因此判断下一个entry是否满足.
                    // 判断下一个entry实际上就是看下一个conjunction是否匹配.
                    for (int l = k; l < attributeCursors.size(); l++) {
                        if (attributeCursors.get(l).getCurrentEntry().getConjunctionId() != rejectId) {
                            break;
                        }
                        // Skip to smallest ID where ID > RejectID
                        attributeCursors.get(l).skip(rejectId);
                    }
                } else {
                    // conjunction is fully satisfied  O ← O ∪ {PLists[K-1].CurrEntry.ID}
                    Set<Document> documents1 = this.conjunctionToDocuments.get(nextId);
                    documents.addAll(documents1);
                }
                nextId = nextId + 1;
            }
            // 第一层遍历结束,判断下一个
            for (int l = 0; l < k; l++) {
                attributeCursors.get(l).skipTo(nextId);
            }
            //SortByCurrentEntries(PLists)
            Collections.sort(attributeCursors);
        }
    }

    /**
     * 对第k层的sub index子索引，根据查询表达式queryExpressions每个属性获取对应的attributeCursors
     * @param queryExpressions 查询表达式
     * @param kSizeSubBooleanIndex 第k层索引
     */
    private List<AttributeCursor> initKSizeAttributeCursor(QueryExpressions queryExpressions, SizeSubBooleanIndex kSizeSubBooleanIndex) {
        List<AttributeCursor> attributeCursors = new ArrayList<>(queryExpressions.getExpressionsSize());
        //获取第k层子索引subBooleanIndex
        for (Assignment assignment: queryExpressions.getAssignments()) {
            //拉取第k层attribute属性的entriesContainer
            EntriesContainer entriesContainer = kSizeSubBooleanIndex.getEntriesContainer(assignment.getAttribute());
            //entriesContainer中传入该assignment的查询表达式(查询表达式包括 "=","|","&&","<",">")
            AttributeCursor attributeCursor = entriesContainer.initAttributeCursor(assignment.getQueryExpressionMatcher());
            //未查询到该attribute的entries则跳过
            if (null == attributeCursor) {
                continue;
            }
            //加入候选
            attributeCursors.add(attributeCursor);
        }
        return attributeCursors;
    }

}
