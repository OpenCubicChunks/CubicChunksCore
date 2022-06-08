package io.github.opencubicchunks.cc_core.minecraft;

public interface MCBlockPos extends MCVec3i {
    static MCBlockPos of(int x, int y, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    MCBlockPos above();
    MCBlockPos above(int i);

    MCBlockPos below();
    MCBlockPos below(int i);
}
