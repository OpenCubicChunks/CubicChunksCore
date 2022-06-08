package io.github.opencubicchunks.cc_core.minecraft;

public interface MCVec3i {
    int getX();
    int getY();
    int getZ();

    long asLong();

    static MCVec3i of(int x, int y, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
