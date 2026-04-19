package app;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FactorialShared {

    private static final AtomicReference<BigInteger> myHalf = new AtomicReference<>(null);
    private static final AtomicReference<BigInteger> friendHalf = new AtomicReference<>(null);
    private static volatile String originalArgs = null;

    public static void reset(String args) {
        originalArgs = args;
        myHalf.set(null);
        friendHalf.set(null);
    }

    public static boolean submitMyHalf(BigInteger value) {
        myHalf.set(value);
        return friendHalf.get() != null;
    }

    public static boolean submitFriendHalf(BigInteger value) {
        friendHalf.set(value);
        return myHalf.get() != null;
    }

    public static BigInteger computeResult() {
        return myHalf.get().multiply(friendHalf.get());
    }

    public static String getOriginalArgs() {
        return originalArgs;
    }
}