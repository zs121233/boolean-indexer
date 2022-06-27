package utils;

import entities.index.Entry;
import entities.query.EntriesCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author zhangsheng
 */
public class MathUtil {

    /**
     * todo: 合并两个排序列表
     */
    public static <T extends Comparator<T>> T[] mergeSortedList(List<T> e) {
        return null;
    }

    /**
     * 二分查找
     * 实现后发现不如直接遍历
     */
    public static int searchInsert(Entry[] entries, int currentPosition, long target) {
        int left = currentPosition;
        int right = entries.length - 1;
        while(left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (entries[mid].getConjunctionId() == target) {
                return mid;
            }
            if (entries[mid].getConjunctionId() > target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        //跳出while循环之后根据target值与A[left]、A[right]的关系确定返回值
        if (target <= entries[left].getConjunctionId()) {
            return left;
        } else if (target <= entries[right].getConjunctionId()) {
            return right;
        } else {
            return right + 1;
        }
    }

    public static EntriesCursor mergeKLists(List<EntriesCursor> entriesCursors) {
        if (entriesCursors.size() == 0) {
            return null;
        }
        int i = 0;
        int j = entriesCursors.size() - 1;
        while (j > 0) {
            // ?
            i = 0;
            while (i < j) {
                entriesCursors.set(i, mergeTwoLists(entriesCursors.get(i), entriesCursors.get(j)));
                i++;
                j--;
            }
        }
        return entriesCursors.get(0);
    }

    public static EntriesCursor mergeTwoLists(EntriesCursor l1, EntriesCursor l2) {
        if(l1 == null) {
            return l2;
        }
        if(l2 == null) {
            return l1;
        }

        int l1Length = l1.getEntries().length;
        int l2Length = l2.getEntries().length;
        List<Entry> newList = new ArrayList<>(l1Length + l2Length);
        // 临时变量
        int l1Node = 0;
        int l2Node = 0;
        // 循环处理
        while (l1Node < l1Length && l2Node < l2Length) {
            Entry valOne = l1.getEntries()[l1Node];
            Entry valTwo = l2.getEntries()[l2Node];
            // 插入小的元素节点
            if(valOne.compareTo(valTwo) <= 0){
                if(newList.isEmpty() || !valOne.equals(newList.get(newList.size()-1)))
                {
                    newList.add(valOne);
                }
                l1Node++;
            }else {
                if(newList.isEmpty() || !valTwo.equals(newList.get(newList.size()-1)))
                {
                    newList.add(valTwo);
                }
                l2Node++;
            }
        }
        // 如果长度不一样
        //l2长
        if(l1Node >= l1.getEntries().length){
            for (int i = l2Node; i < l2.getEntries().length; i++) {
                if(newList.isEmpty() || !l2.getEntries()[i].equals(newList.get(newList.size()-1)))
                {
                    newList.add(l2.getEntries()[i]);
                }
            }
        }
        //l1长
        if(l2Node >= l2.getEntries().length){
            for (int i = l1Node; i < l1.getEntries().length; i++) {
                if(newList.isEmpty() || !l1.getEntries()[i].equals(newList.get(newList.size()-1)))
                {
                    newList.add(l1.getEntries()[i]);
                }
            }
        }
        return EntriesCursor.of(newList.toArray(Entry[]::new));
    }

    /**
     * todo 完善k个排序列表取交集的逻辑
     */
    public static List<Entry> intersectionKSortedList(List<EntriesCursor> entriesCursors) {
        List<Entry> entries = new ArrayList<>();
        Collections.sort(entriesCursors);
        return entries;
    }


    /**
     * 数组取最小值
     */
    public static <T extends Comparable<T>> T arrayMin(T[] array) {
        if(array.length == 1) {
            return array[0];
        }
        T candidate = array[0];
        for (T t: array) {
            if (t.compareTo(candidate) < 0) {
                candidate = t;
            }
        }
        return candidate;
    }

}
