package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.Header;

@Header
public interface MCBlockGetter extends MCLevelHeightAccessor {
    MCBlockState getBlockState(MCBlockPos blockPos);
}