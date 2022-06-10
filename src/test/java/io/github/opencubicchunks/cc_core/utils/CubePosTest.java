package io.github.opencubicchunks.cc_core.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.opencubicchunks.cc_core.api.CubePos;
import io.github.opencubicchunks.cc_core.minecraft.MCChunkPos;
import io.github.opencubicchunks.cc_core.minecraft.MCSectionPos;
import org.junit.jupiter.api.Test;

public class CubePosTest {

    @Test
    public void cubePosTest() {
        chunkPosTest();
        sectionPosTest();
    }

    public void chunkPosTest() {
        for (int x = Integer.MIN_VALUE / 16; x < Integer.MAX_VALUE / 16; x += Integer.MAX_VALUE / 16 / 500) {
            for (int y = Integer.MIN_VALUE / 16; y < Integer.MAX_VALUE / 16; y += Integer.MAX_VALUE / 16 / 500) {
                for (int z = Integer.MIN_VALUE / 16; z < Integer.MAX_VALUE / 16; z += Integer.MAX_VALUE / 16 / 500) {
                    MCChunkPos chunkPos = CubePos.of(x, y, z).asChunkPos();
                    CubePos cubePos = CubePos.from(chunkPos, y);

                    assertEquals(x, cubePos.getX());
                    assertEquals(z, cubePos.getZ());
                }
            }
        }
    }

    public void sectionPosTest() {
        for (int x = Integer.MIN_VALUE / 16; x < Integer.MAX_VALUE / 16; x += Integer.MAX_VALUE / 16 / 500) {
            for (int y = Integer.MIN_VALUE / 16; y < Integer.MAX_VALUE / 16; y += Integer.MAX_VALUE / 16 / 500) {
                for (int z = Integer.MIN_VALUE / 16; z < Integer.MAX_VALUE / 16; z += Integer.MAX_VALUE / 16 / 500) {
                    MCSectionPos sectionPos = CubePos.of(x, y, z).asSectionPos();
                    CubePos cubePos = CubePos.from(sectionPos);

                    assertEquals(x, cubePos.getX());
                    assertEquals(y, cubePos.getY());
                    assertEquals(z, cubePos.getZ());
                }
            }
        }
    }
}
