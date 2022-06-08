package io.github.opencubicchunks.cc_core.utils;

public class MathUtil {
    public static int ceilDiv(int a, int b) {
        return -Math.floorDiv(-a, b);
    }

    public static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    public static float unlerp(final float v, final float min, final float max) {
        return (v - min) / (max - min);
    }

    public static float lerp(final float a, final float min, final float max) {
        return min + a * (max - min);
    }

    public static float positiveModulo(float f, float g) {
        return (f % g + g) % g;
    }

    public static int clamp(int i, int j, int k) {
        if (i < j) {
            return j;
        } else {
            return Math.min(i, k);
        }
    }

}