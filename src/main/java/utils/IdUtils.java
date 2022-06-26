package utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;


/**
 * @author zhangsheng
 * @date 2022.06.17
 */
public class IdUtils {

    /**
     * We internally keep the DNF ID within each conjunction
     * so that we can later on return the IDs of the satisfied DNFs
     * based on the satisfied conjunctions.
     * @param seed use the conj size as the seed of the hash function.
     * @param conjunction conjunction string
     * @return conjunction id
     */
    public static long generateConjId(int seed, String conjunction) {
        HashFunction hashFunction = Hashing.murmur3_128(seed);
        HashCode hashCode = hashFunction.newHasher().putString(conjunction, Charset.defaultCharset()).hash();
        return hashCode.asLong();
    }

}