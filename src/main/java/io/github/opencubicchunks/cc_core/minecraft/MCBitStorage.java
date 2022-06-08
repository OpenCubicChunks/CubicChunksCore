package io.github.opencubicchunks.cc_core.minecraft;

public interface MCBitStorage {
    int get(int i);
    void set(int i, int j);

    static MCBitStorage create(int i, int j) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
