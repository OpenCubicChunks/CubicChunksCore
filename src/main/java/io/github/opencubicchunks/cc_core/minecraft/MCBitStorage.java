package io.github.opencubicchunks.cc_core.minecraft;

public interface MCBitStorage {
    int get(int i);
    void set(int i, int j);

    static MCBitStorage create(int bits, int size) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    static MCBitStorage create(int bits, int size, long[] data) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
