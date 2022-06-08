package io.github.opencubicchunks.cc_core.utils;

public class Utils {
    @SuppressWarnings("unchecked")
    public static <I, O> O unsafeCast(I obj) {
        return (O) obj;
    }
}