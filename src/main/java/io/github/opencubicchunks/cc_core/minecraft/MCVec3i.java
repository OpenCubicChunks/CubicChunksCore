package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.Header;

@Header
public class MCVec3i {
    public MCVec3i(int x, int y, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    public native int getX();
    public native int getY();
    public native int getZ();
}
