package io.github.opencubicchunks.cc_core.testutils;

import static io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerNode.WIDTH_BLOCKS;

import java.util.function.BiConsumer;

import io.github.opencubicchunks.cc_core.api.CubicConstants;

public class Utils {
    public static void forEachBlockColumnSurfaceTrackerNode(BiConsumer<Integer, Integer> xzConsumer) {
        for (int x = 0; x < WIDTH_BLOCKS; x++) {
            for (int z = 0; z < WIDTH_BLOCKS; z++) {
                xzConsumer.accept(x, z);
            }
        }
    }

    public static void forEachBlockColumnCube(BiConsumer<Integer, Integer> xzConsumer) {
        for (int x = 0; x < CubicConstants.DIAMETER_IN_BLOCKS; x++) {
            for (int z = 0; z < CubicConstants.DIAMETER_IN_BLOCKS; z++) {
                xzConsumer.accept(x, z);
            }
        }
    }
}
