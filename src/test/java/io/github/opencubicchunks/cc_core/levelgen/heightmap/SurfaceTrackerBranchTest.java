package io.github.opencubicchunks.cc_core.levelgen.heightmap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.opencubicchunks.cc_core.levelgen.heightmap.SurfaceTrackerNodesTest.NullHeightmapStorage;
import io.github.opencubicchunks.cc_core.levelgen.heightmap.SurfaceTrackerNodesTest.TestHeightmapSource;
import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerBranch;
import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerLeaf;
import io.github.opencubicchunks.cc_core.world.heightmap.surfacetrackertree.SurfaceTrackerNode;
import org.junit.jupiter.api.Test;

public class SurfaceTrackerBranchTest {
    /**
     * Tests that SurfaceTrackerBranch throws when given scales beyond its bounds
     * and that nothing is thrown within bounds
     */
    @Test
    public void testValidScaleBounds() {
        assertThrows(SurfaceTrackerBranch.InvalidScaleException.class, () -> new SurfaceTrackerBranch(0, 0, null, (byte) 0));
        assertThrows(SurfaceTrackerBranch.InvalidScaleException.class, () -> new SurfaceTrackerBranch(SurfaceTrackerNode.MAX_SCALE + 1, 0, null, (byte) 0));

        for (int i = 1; i <= SurfaceTrackerNode.MAX_SCALE; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> new SurfaceTrackerBranch(finalI, 0, null, (byte) 0),
                String.format("SurfaceTrackerBranch threw when given scale %d", i));
        }
    }

    /**
     * Tests that when a cube is inserted into root, it properly creates the appropriate leaf
     */
    @Test
    public void testLeafInsertionIntoRoot() {
        NullHeightmapStorage storage = new NullHeightmapStorage();
        SurfaceTrackerBranch root = new SurfaceTrackerBranch(SurfaceTrackerNode.MAX_SCALE, 0, null, (byte) 0);
        root.loadSource(0, 0, storage, new TestHeightmapSource(0, 0, 0));

        SurfaceTrackerLeaf leaf = root.getLeaf(0);
        assertNotNull(leaf, "Appropriate leaf was null after loading node into root");
        assertNotNull(leaf.getSource(), "Leaf had null node after node was loaded into root");
    }
}
