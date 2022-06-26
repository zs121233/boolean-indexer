
import org.roaringbitmap.longlong.Roaring64Bitmap;

import java.util.*;

public class RoaringBitMapTest {
    public static void main(String[] args) {
        HashMap<Long, Set<Long>> longSetHashMap1 = new HashMap<>();
        HashMap<Long, Roaring64Bitmap> longSetHashMap2 = new HashMap<>();
        HashMap<Long, Long> longIntegerHashMap = new HashMap<>();
        for (long i = 0; i < 100000; i++) {
            long i1 = i % 40;
            Set<Long> longs1 = longSetHashMap1.computeIfAbsent(i1, v->new HashSet<Long>());
            Roaring64Bitmap longs2 = longSetHashMap2.computeIfAbsent(i1, v->new Roaring64Bitmap());
            longIntegerHashMap.put(i, i+1);
            longs1.add(i);
            longs2.add(i);
        }
        int testCount = 100000;
        List<Long> conjIds = random();
        testHashMap(testCount, conjIds, longSetHashMap1, longIntegerHashMap);
        testRoaringBitMap(testCount, conjIds, longSetHashMap2, longIntegerHashMap);
    }

    private static void testHashMap(int testCount, List<Long> conjIds, HashMap<Long, Set<Long>> longSetHashMap1,
                                    Map<Long, Long> longIntegerHashMap) {
        int times = 0;
        for (int i = 0; i < testCount; i++) {
            long nanoTime = System.nanoTime();
            List<Long> docIds = new ArrayList<>();
            for (Long l : conjIds) {
                Set<Long> longs1 = longSetHashMap1.get(l);
                if(longs1 != null) {
                    docIds.addAll(longs1);
                }
            }
            ArrayList<Long> longs = new ArrayList<>(docIds.size());
            for (Long docId : docIds) {
                longs.add(longIntegerHashMap.get(docId));
            }
            times += (System.nanoTime() - nanoTime)/1000;
            //System.out.println(docs.size());
            //testRoaringBitMap();
        }
        System.out.println("hashSet 合并耗时:" + times/testCount + "ns");
    }

    private static List<Long> random() {
        Random random = new Random();
        long i1 = random.nextInt(20);
        long i2 = random.nextInt(20);
        long i3 = random.nextInt(20);
        long i4 = random.nextInt(20);
        return Arrays.asList(i1,i2,i3,i4);
    }

    private static void testRoaringBitMap(int testCount, List<Long> conjIds, Map<Long, Roaring64Bitmap> longSetHashMap,
                                          Map<Long, Long> longIntegerHashMap) {
        int times = 0;
        for (int i = 0; i < testCount; i++) {
            long nanoTime = System.nanoTime();
            Roaring64Bitmap roaring64Bitmap = new Roaring64Bitmap();
            for (Long l : conjIds) {
                Roaring64Bitmap roaring64Bitmap1 = longSetHashMap.get(l);
                if(roaring64Bitmap1 != null) {
                    roaring64Bitmap.or(roaring64Bitmap1);
                }
            }
            List<Long> docs = new ArrayList<Long>(roaring64Bitmap.getIntCardinality());
            Iterator<Long> iterator = roaring64Bitmap.iterator();
            while (iterator.hasNext()) {
                docs.add(longIntegerHashMap.get(iterator.next()));
            }
            times += (System.nanoTime() - nanoTime)/1000;
           // testRoaringBitMap();
           //System.out.println(roaring64Bitmap.toArray().length);
        }
        System.out.println("hashBitMap 合并耗时:" + times/testCount + "ns");
    }


}
