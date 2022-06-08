package io.github.opencubicchunks.cc_core.world.heightmap;

import javax.annotation.Nonnull;

import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerLeaf;

public interface HeightmapNode {

    default void sectionLoaded(@Nonnull SurfaceTrackerLeaf surfaceTrackerLeaf, int localSectionX, int localSectionZ) {
        throw new IllegalStateException("Should not be reached");
    }

    void unloadNode(@Nonnull HeightmapStorage storage);

    int getHighest(int x, int z, byte heightmapType);

    int getNodeY();
}
