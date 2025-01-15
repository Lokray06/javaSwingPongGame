package utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random
{
    public static int inRange(int n1, int n2)
    {
        if (n1 > n2) {
            throw new IllegalArgumentException("n1 should not be greater than n2");
        }
        return ThreadLocalRandom.current().nextInt(n1, n2 + 1);
    }
}
