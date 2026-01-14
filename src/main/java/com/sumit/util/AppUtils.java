package com.sumit.util;

import java.security.SecureRandom;

public class AppUtils {

    private static final SecureRandom random = new SecureRandom();

    public static long generate10DigitId() {
        // Generate random number between 1000000000 and 9999999999
        return 1_000_000_000L + (Math.abs(random.nextLong()) % 9_000_000_000L);
    }


}