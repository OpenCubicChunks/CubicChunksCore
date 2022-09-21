package io.github.opencubicchunks.cc_core.world.heightmap;

import javax.annotation.Nonnull;

import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerLeaf;

public interface HeightmapSource {

    default void leafLoaded(@Nonnull SurfaceTrackerLeaf surfaceTrackerLeaf) {
        throw new IllegalStateException("Should not be reached");
    }

    void unloadSource(@Nonnull HeightmapStorage storage);

    int getHighest(int x, int z, byte heightmapType);

    int getSourceY();
}
