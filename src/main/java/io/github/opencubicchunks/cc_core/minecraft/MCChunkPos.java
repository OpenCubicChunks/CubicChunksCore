package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.DeclaresClass;

@DeclaresClass("net.minecraft.world.level.ChunkPos")
public class MCChunkPos {
    public final int x, z;
    public MCChunkPos(int x, int y) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public native long toLong();

    public native static long asLong(int x, int z);
}
