package utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;


/**
 * @author
 */
public class IdUtils {

    public static long generateId(int seed, String assignments) {
        // hashing
        HashFunction hashFunction = Hashing.murmur3_128(seed);
        HashCode hashCode = hashFunction.newHasher().putString(assignments, Charset.defaultCharset()).hash();
        return hashCode.asLong();
    }

}