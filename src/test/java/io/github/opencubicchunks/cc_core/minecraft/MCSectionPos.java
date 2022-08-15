package io.github.opencubicchunks.cc_core.minecraft;

import java.util.stream.Stream;

import io.github.opencubicchunks.cc_core.annotation.DeclaresClass;

@DeclaresClass("net.minecraft.core.SectionPos")
public abstract class MCSectionPos extends MCVec3i {
    private MCSectionPos(int x, int y, int z) {
        super(x, y, z);
    }

    public native long asLong();

    public native static MCSectionPos of(int x, int y, int z);

    public native static MCSectionPos of(long sectionPosLong);

    public native static Stream<MCSectionPos> cube(MCSectionPos sectionPos, int i);
}
