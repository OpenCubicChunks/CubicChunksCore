package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.Header;

@Header
public class MCBlockPos extends MCVec3i {
    public MCBlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public native long asLong();

    public native MCBlockPos above();
    public native MCBlockPos above(int i);

    public native MCBlockPos below();
    public native MCBlockPos below(int i);
}
