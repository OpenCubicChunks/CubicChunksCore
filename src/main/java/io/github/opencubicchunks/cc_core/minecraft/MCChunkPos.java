package io.github.opencubicchunks.cc_core.minecraft;

public interface MCChunkPos {
    int getX();
    int getZ();

    long toLong();

    static MCChunkPos of(int x, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
    static long asLong(int x, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
