package core;

import entities.index.container.EntriesContainer;
import entities.index.container.KvEntriesContainer;
import lombok.Data;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 80304042
 */
@Data
public class SizeSubBooleanIndex {
    /**
     * the size K of the sub boolean index
     */
    private int size;
    /**
     * 第K层所有属性的entriesContainer,这里不用Key直接打平,而是先用attribute属性将entriesContainer
     */
    private Map<String, EntriesContainer> entriesContainers = new ConcurrentHashMap<>();

    public EntriesContainer getEntriesContainer(String attribute) {
        EntriesContainer entriesContainer = entriesContainers.get(attribute);
        if(entriesContainer != null) {
            return entriesContainer;
        }
        KvEntriesContainer kvEntriesContainer = new KvEntriesContainer();
        entriesContainers.put(attribute, kvEntriesContainer);
        return kvEntriesContainer;
    }


}
