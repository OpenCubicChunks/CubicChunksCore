package io.github.opencubicchunks.cc_core.minecraft;

import io.github.opencubicchunks.javaheaders.api.DeclaresClass;

@DeclaresClass("net.minecraft.world.entity.Entity")
public abstract class MCEntity {
    public abstract double getX();
    public abstract double getY();
    public abstract double getZ();
}
