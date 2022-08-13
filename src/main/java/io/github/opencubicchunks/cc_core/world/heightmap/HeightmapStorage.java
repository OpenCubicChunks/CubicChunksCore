package io.github.opencubicchunks.cc_core.world.heightmap;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;


import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerBranch;
import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HeightmapStorage extends Closeable, Flushable {
    void saveNode(int globalSectionX, int globalSectionZ, @NotNull SurfaceTrackerNode surfaceTrackerSection);
    @Nullable SurfaceTrackerNode loadNode(int globalSectionX, int globalSectionZ, @Nullable SurfaceTrackerBranch parent, byte heightmapType, int scale, int scaledY);

    File storageDirectory();
}
