package io.github.opencubicchunks.cc_core.chunk.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.opencubicchunks.cc_core.api.CubePos;
import org.junit.jupiter.api.Test;

public class CubePosTest {

    @Test
    public void asLong() {
        for (int x = -(1 << 20); x < (1 << 20) - 1; x += (1 << 20) / 500) {
            for (int y = -(1 << 21); y < (1 << 21) - 1; y += (1 << 21) / 500) {
                for (int z = -(1 << 20); z < (1 << 20) - 1; z += (1 << 20) / 500) {
                    test(x, y, z);
                }
            }
        }
    }

    void test(int x, int y, int z) {
        long pos = CubePos.asLong(x, y, z);
        CubePos cubePos = CubePos.from(pos);
        int xAfter = cubePos.getX();
        int yAfter = cubePos.getY();
        int zAfter = cubePos.getZ();

        assertEquals(x, xAfter);
        assertEquals(y, yAfter);
        assertEquals(z, zAfter);
    }
}