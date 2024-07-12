package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.Header;

@Header
public class MCChunkPos {
    public final int x, z;
    public MCChunkPos(int x, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public MCChunkPos(long packedPos) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public native long toLong();

    public native static long asLong(int x, int z);

    public native static int getX(long chunkAsLong);
    public native static int getZ(long chunkAsLong);
}
