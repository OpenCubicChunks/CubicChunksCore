package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.Header;

@Header
public interface MCLevelHeightAccessor {
    int getMinBuildHeight();

    int getMaxBuildHeight();
}
