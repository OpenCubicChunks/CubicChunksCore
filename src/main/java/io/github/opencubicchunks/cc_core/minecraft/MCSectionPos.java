package io.github.opencubicchunks.cc_core.minecraft;

import java.util.stream.Stream;

public interface MCSectionPos extends MCVec3i {
    static MCSectionPos of(int x, int y, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    static MCSectionPos of(long sectionPosLong) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    static Stream<MCSectionPos> cube(MCSectionPos sectionPos, int i) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
