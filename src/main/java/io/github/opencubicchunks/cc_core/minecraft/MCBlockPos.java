package io.github.opencubicchunks.cc_core.minecraft;

public interface MCBlockPos extends MCVec3i {
    static MCBlockPos of(int x, int y, int z) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    default MCBlockPos above() {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
    default MCBlockPos above(int i) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }

    default MCBlockPos below() {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
    default MCBlockPos below(int i) {
        throw new IllegalStateException("Per-version doesn't overwrite method");
    }
}
