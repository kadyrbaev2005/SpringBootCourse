package com.medieval.taxcollector.client;

public final class OracleFallbackContext {

    private static final ThreadLocal<Boolean> FALLBACK_USED = new ThreadLocal<>();

    private OracleFallbackContext() {
    }

    public static void markFallbackUsed() {
        FALLBACK_USED.set(Boolean.TRUE);
    }

    public static boolean pollFallbackUsed() {
        Boolean v = FALLBACK_USED.get();
        FALLBACK_USED.remove();
        return Boolean.TRUE.equals(v);
    }
}
