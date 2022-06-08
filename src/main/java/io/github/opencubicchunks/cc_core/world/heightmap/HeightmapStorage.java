package io.github.opencubicchunks.cc_core.world.heightmap;

import javax.annotation.Nullable;

import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerBranch;
import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerNode;

public interface HeightmapStorage {
    void unloadNode(SurfaceTrackerNode surfaceTrackerSection);
    @Nullable SurfaceTrackerNode loadNode(SurfaceTrackerBranch parent, byte heightmapType, int scale, int scaledY);
}
