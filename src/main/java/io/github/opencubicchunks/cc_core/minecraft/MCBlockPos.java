package io.github.opencubicchunks.cc_core.minecraft;

public class MCBlockPos extends MCVec3i {
    public MCBlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    @Override public native long asLong();

    public native MCBlockPos above();
    public native MCBlockPos above(int i);

    public native MCBlockPos below();
    public native MCBlockPos below(int i);
}
