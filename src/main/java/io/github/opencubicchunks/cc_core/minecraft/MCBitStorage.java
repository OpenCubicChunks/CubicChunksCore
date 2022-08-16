package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.DeclaresClass;

@DeclaresClass("net.minecraft.util.BitStorage")
public class MCBitStorage {
    public MCBitStorage(int bits, int size) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public MCBitStorage(int bits, int size, long[] data) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public native int get(int i);
    public native void set(int i, int j);
}