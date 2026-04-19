package app;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FactorialShared {
    public static AtomicReference<BigInteger> friendsHalf = new AtomicReference<BigInteger>(null);

}
