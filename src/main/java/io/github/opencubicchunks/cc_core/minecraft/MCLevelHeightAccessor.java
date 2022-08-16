package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.DeclaresClass;

@DeclaresClass("net.minecraft.world.level.LevelHeightAccessor")
public interface MCLevelHeightAccessor {
    int getMinBuildHeight();

    int getMaxBuildHeight();
}
