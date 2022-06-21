package entities.index.container;

import entities.index.BooleanValues;
import entities.query.AttributeCursor;
import entities.query.matcher.QueryExpressionMatcher;


/**
 * 用于存储索引的PostingList数据
 * 目前的三种典型场景:
 * 1. 内存KV 存储所有Field值对应的EntryID列表(PostingList)
 * 2. skipList 属性值使用跳表存储以便于支持范围查询
 * @author zhangsheng
 */
public interface EntriesContainer {

    /**
     * 索引添加
     * @param booleanValues 布尔表达式
     * @param conjunctionId conjId
     */
    void addEntry(BooleanValues booleanValues, long conjunctionId);

    /**
     * 初始化对应属性的游标
     * @param queryExpressionMatcher 查询表达式匹配器
     * @return 属性游标
     */
    AttributeCursor initAttributeCursor(QueryExpressionMatcher queryExpressionMatcher);
}
