package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.DeclaresClass;

@DeclaresClass("net.minecraft.world.level.BlockGetter")
public interface MCBlockGetter extends MCLevelHeightAccessor {
    MCBlockState getBlockState(MCBlockPos blockPos);
}