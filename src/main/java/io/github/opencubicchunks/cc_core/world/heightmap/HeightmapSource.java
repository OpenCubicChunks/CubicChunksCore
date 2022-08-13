package io.github.opencubicchunks.cc_core.world.heightmap;

import javax.annotation.Nonnull;

import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerLeaf;

public interface HeightmapSource {

    default void sectionLoaded(@Nonnull SurfaceTrackerLeaf surfaceTrackerLeaf, int localSectionX, int localSectionZ) {
        throw new IllegalStateException("Should not be reached");
    }

    void unloadSource(@Nonnull HeightmapStorage storage);

    int getHighest(int x, int z, byte heightmapType);

    int getSourceY();
}
