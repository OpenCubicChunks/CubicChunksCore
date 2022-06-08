package io.github.opencubicchunks.cc_core.minecraft;

public interface MCBlockGetter extends MCLevelHeightAccessor {
    MCBlockState getBlockState(MCBlockPos blockPos);
}
