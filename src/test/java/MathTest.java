import entities.index.Entry;
import utils.MathUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathTest {


    public static void main(String[] args) {
        List<Entry> entriesList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            entriesList.add(Entry.of(true, random.nextLong()));
        }
        Collections.sort(entriesList);
        Entry[] entries = entriesList.toArray(new Entry[]{});
        int times = 1000000;
        long times1 = 0;
        long times2 = 0;
        long times3 = 0;
        for (int i = 0; i < times; i++) {
            long randomLong = random.nextLong();
            long startNanoTime1 = System.nanoTime();
            skip(entries, 0, randomLong);
            long startNanoTime2 = System.nanoTime();
            times1 += startNanoTime2 - startNanoTime1;
            int i2 = skipTo(entries, 0, randomLong);
            long startNanoTime3 = System.nanoTime();
            times2 += startNanoTime3 - startNanoTime2;
            MathUtil.searchInsert(entries, 0, randomLong);
            long startNanoTime4 = System.nanoTime();
            times3 += startNanoTime4 - startNanoTime3;
        }
        System.out.println("times1:"+ times1/times);
        System.out.println("times2:"+ times2/times);
        System.out.println("times3:"+ times3/times);

    }


    public static int skip(Entry[] entries, int currentPosition, long target) {
        for (; currentPosition < entries.length; currentPosition++) {
            if (entries[currentPosition].getConjunctionId() > target) {
                return currentPosition;
            }
        }
        return -1;
    }

    public static int skipTo(Entry[] entries, int currentPosition, long target) {
        for (; currentPosition < entries.length; currentPosition++) {
            if (entries[currentPosition].getConjunctionId() >= target) {
                return currentPosition;
            }
        }
        return -1;
    }

}
