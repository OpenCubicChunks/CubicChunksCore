/*
 *  This file is part of Cubic Chunks Mod, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015-2019 OpenCubicChunks
 *  Copyright (c) 2015-2019 contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.github.opencubicchunks.cc_core.utils;

import static io.github.opencubicchunks.cc_core.api.CubicConstants.DIAMETER_IN_SECTIONS;

import io.github.opencubicchunks.cc_core.api.CubePos;
import io.github.opencubicchunks.cc_core.api.CubicConstants;
import io.github.opencubicchunks.cc_core.minecraft.MCBlockPos;
import io.github.opencubicchunks.cc_core.minecraft.MCChunkPos;
import io.github.opencubicchunks.cc_core.minecraft.MCEntity;
import io.github.opencubicchunks.cc_core.minecraft.MCSectionPos;

/**
 * A class that contains helper-methods for many CubicChunks related things.
 * <p>
 * General Notes:
 * <ul>
 * <li>If a parameter is called <b>val</b> such as in the method {@link Coords#blockToLocal} it refers to a single dimension of that coordinate (x, y or z).</li>
 * <li>If a parameter is called <b>pos</b> and is of type <b>long</b> it refers to the entire coordinate compressed into a long. For example SectionPos#asLong()</li>
 * </ul>
 */
public class Coords {

    public static final int NO_HEIGHT = Integer.MIN_VALUE + 32;

    private static final int LOG2_BLOCK_SIZE = MathUtil.log2(CubicConstants.DIAMETER_IN_BLOCKS);
    private static final int LOG2_DIAMETER_IN_SECTIONS = MathUtil.log2(DIAMETER_IN_SECTIONS);

    private static final int BLOCK_SIZE_MINUS_1 = CubicConstants.DIAMETER_IN_BLOCKS - 1;
    private static final int LOCAL_COLUMN_POS_MASK = DIAMETER_IN_SECTIONS - 1;
    private static final int BLOCK_SIZE_DIV_2 = CubicConstants.DIAMETER_IN_BLOCKS / 2;
    private static final int BLOCK_SIZE_DIV_16 = CubicConstants.DIAMETER_IN_BLOCKS / 16;
    private static final int BLOCK_SIZE_DIV_32 = CubicConstants.DIAMETER_IN_BLOCKS / 32;

    private static final int POS_TO_INDEX_MASK = getPosToIndexMask();
    private static final int INDEX_TO_POS_MASK = POS_TO_INDEX_MASK >> 4;

    private static final int INDEX_TO_N_X = 0;
    private static final int INDEX_TO_N_Y = LOG2_BLOCK_SIZE - 4;
    private static final int INDEX_TO_N_Z = INDEX_TO_N_Y * 2;

    /**
     * <it><b>CC INTERNAL</b></it> | Mask used for converting BlockPos to ChunkSection index within a LevelCube.
     */
    private static int getPosToIndexMask() {
        int mask = 0;
        for (int i = CubicConstants.DIAMETER_IN_BLOCKS / 2; i >= 16; i /= 2) {
            mask += i;
        }
        return mask;
    }

    public static int columnToColumnIndex(int columnCubeLocalX, int columnCubeLocalZ) {
        return columnCubeLocalX | columnCubeLocalZ << LOG2_DIAMETER_IN_SECTIONS;
    }

    public static int columnIndexToLocalX(int idx) {
        return idx & LOCAL_COLUMN_POS_MASK;
    }

    public static int columnIndexToLocalZ(int idx) {
        return (idx >> LOG2_DIAMETER_IN_SECTIONS) & LOCAL_COLUMN_POS_MASK;
    }

    /**
     * Gets the offset of a BlockPos inside its LevelCube
     *
     * @param val A single value of the position
     *
     * @return The position relative to the LevelCube this block is in
     */
    public static int blockToLocal(int val) {
        return val & (CubicConstants.DIAMETER_IN_BLOCKS - 1);
    }

    /** See {@link Coords#blockToLocal} */
    public static int localX(MCBlockPos pos) {
        return blockToLocal(pos.getX());
    }

    /** See {@link Coords#blockToLocal} */
    public static int localY(MCBlockPos pos) {
        return blockToLocal(pos.getY());
    }

    /** See {@link Coords#blockToLocal} */
    public static int localZ(MCBlockPos pos) {
        return blockToLocal(pos.getZ());
    }

    /**
     * @param val A single dimension of the BlockPos (eg: BlockPos#getY())
     *
     * @return That coordinate as a CubePos
     */
    public static int blockToCube(int val) {
        return val >> LOG2_BLOCK_SIZE;
    }

    /** See {@link Coords#blockToCube(int)} */
    public static int blockToCube(double blockPos) {
        return blockToCube((int) Math.floor(blockPos));
    }

    /**
     * @param blockVal A single dimension of the BlockPos (eg: BlockPos#getY())
     *
     * @return That coordinate as a CubePos
     */
    public static int blockCeilToCube(int blockVal) {
        return -((-blockVal) >> LOG2_BLOCK_SIZE);
    }

    /**
     * @param cubeVal Single dimension of a {@link CubePos}
     * @param localVal Offset of the block from the cube
     *
     * @return Sum of cubeVal as BlockPos and localVal
     */
    public static int localToBlock(int cubeVal, int localVal) {
        return cubeToMinBlock(cubeVal) + localVal;
    }

    /**
     * @param cubeVal A single dimension of a {@link CubePos}
     *
     * @return The minimum BlockPos inside that LevelCube
     */
    public static int cubeToMinBlock(int cubeVal) {
        return cubeVal << LOG2_BLOCK_SIZE;
    }

    /**
     * @param cubeVal A single dimension of a {@link CubePos}
     *
     * @return The maximum BlockPos inside that LevelCube
     */
    public static int cubeToMaxBlock(int cubeVal) {
        return cubeToMinBlock(cubeVal) + BLOCK_SIZE_MINUS_1;
    }

    public static int cubeToCenterBlock(int cubeVal) {
        return localToBlock(cubeVal, BLOCK_SIZE_DIV_2);
    }

    public static int blockToIndex(MCBlockPos pos) {
        return blockToIndex(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * @param blockXVal X position
     * @param blockYVal Y position
     * @param blockZVal Z position
     *
     * @return LevelChunkSection index inside the LevelCube the position specified falls within
     *     <p>
     *     This uses the internal methods such as {@link Coords#blockToIndex16} to allow the JVM to optimise out the variable bit shifts that would occur otherwise
     */
    public static int blockToIndex(int blockXVal, int blockYVal, int blockZVal) {
        //EXAMPLE:
        //        Given x pos 33 = 0x21 = 0b0100001
        //        1100000
        //        0b0100001 & 0b1100000 = 0b0100000
        //        0b0100000 >> 4 = 0b10 = 0x2 = 2

        //        Given y pos 532 = 0x214 = 0b1000010100
        //        0b0001100000
        //        0b0001100000
        //        0b1000010100 & 0b0001100000 = 0b0
        //        0b0 >> 4 = 0b0 = 0x0 = 0

        //        Given z pos -921 = -0x399 = 0b1110011001
        //        0b0001100000
        //        0b0001100000
        //        0b1000010100 & 0b0001100000 = 0b0
        //        0b0 >> 4 = 0b0 = 0x0 = 0

        //        mask needs to be every power of 2 below IBigCube.BLOCK_SIZE that's > 16

        if (CubicConstants.DIAMETER_IN_SECTIONS == 1) {
            return blockToIndex16(blockXVal, blockYVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 2) {
            return blockToIndex32(blockXVal, blockYVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 4) {
            return blockToIndex64(blockXVal, blockYVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 8) {
            return blockToIndex128(blockXVal, blockYVal, blockZVal);
        }
        throw new UnsupportedOperationException("Unsupported cube size " + CubicConstants.DIAMETER_IN_SECTIONS);
    }

    /** See {@link Coords#blockToIndex(int, int, int)} */
    private static int blockToIndex16(int blockXVal, int blockYVal, int blockZVal) {
        return 0;
    }

    /** See {@link Coords#blockToIndex(int, int, int)} */
    private static int blockToIndex32(int blockXVal, int blockYVal, int blockZVal) {
        //1 bit
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockYVal & mask) >> 3 | (blockZVal & mask) >> 2;
    }

    /** See {@link Coords#blockToIndex(int, int, int)} */
    @SuppressWarnings("PointlessBitwiseExpression")
    private static int blockToIndex64(int blockXVal, int blockYVal, int blockZVal) {
        //2 bit
        //1011101010001, 1010101011100, 1101011101010
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockYVal & mask) >> 2 | (blockZVal & mask) >> 0;
    }

    /** See {@link Coords#blockToIndex(int, int, int)} */
    private static int blockToIndex128(int blockXVal, int blockYVal, int blockZVal) {
        //3 bit
        //1011101010001, 1010101011100, 1101011101010
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockYVal & mask) >> 1 | (blockZVal & mask) << 2;
    }

    /**
     * @param idx Index of LevelChunkSection within its LevelCube
     *
     * @return The X offset (as a SectionPos) from its {@link CubePos} (as a SectionPos)
     */
    public static int indexToX(int idx) {
        return idx >> INDEX_TO_N_X & INDEX_TO_POS_MASK;
    }

    /**
     * @param idx Index of LevelChunkSection within its LevelCube
     *
     * @return The Y offset (as a SectionPos) from its {@link CubePos} (as a SectionPos)
     */
    public static int indexToY(int idx) {
        return idx >> INDEX_TO_N_Y & INDEX_TO_POS_MASK;
    }

    /**
     * @param idx Index of the LevelChunkSection within its LevelCube
     *
     * @return The Z offset (as a SectionPos) from its {@link CubePos} (as a SectionPos)
     */
    public static int indexToZ(int idx) {
        return idx >> INDEX_TO_N_Z & INDEX_TO_POS_MASK;
    }

    /**
     * @param val Single dimension of a SectionPos
     *
     * @return That SectionPos dimension as a single dimension of a {@link CubePos}
     */
    public static int sectionToCube(int val) {
        return val >> (LOG2_BLOCK_SIZE - 4);
    }

    /**
     * @param sectionX A section X
     * @param sectionY A section Y
     * @param sectionZ A section Z
     *
     * @return The index of LevelChunkSection that the specified SectionPos describes inside its LevelCube
     */
    public static int sectionToIndex(int sectionX, int sectionY, int sectionZ) {
        return blockToIndex(sectionX << 4, sectionY << 4, sectionZ << 4);
    }

    public static int indexToSectionX(int idx) {
        return indexToX(idx << 4);
    }

    public static int indexToSectionY(int idx) {
        return indexToY(idx << 4);
    }

    public static int indexToSectionZ(int idx) {
        return indexToZ(idx << 4);
    }


    /**
     * @param blockXVal X position
     * @param blockZVal Z position
     *
     * @return LevelChunk index inside the LevelCube the position specified falls within
     *     <p>
     *     This uses the internal methods such as {@link Coords#blockToColumnIndex16} to allow the JVM to optimise out the variable bit shifts that would occur otherwise
     */
    public static int blockToColumnIndex(int blockXVal, int blockZVal) {
        if (CubicConstants.DIAMETER_IN_SECTIONS == 1) {
            return blockToColumnIndex16(blockXVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 2) {
            return blockToColumnIndex32(blockXVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 4) {
            return blockToColumnIndex64(blockXVal, blockZVal);
        } else if (CubicConstants.DIAMETER_IN_SECTIONS == 8) {
            return blockToColumnIndex128(blockXVal, blockZVal);
        }
        throw new UnsupportedOperationException("Unsupported cube size " + CubicConstants.DIAMETER_IN_SECTIONS);
    }

    /** See {@link Coords#blockToColumnIndex(int, int)} */
    private static int blockToColumnIndex16(int blockXVal, int blockZVal) {
        return 0;
    }

    /** See {@link Coords#blockToColumnIndex(int, int)} */
    private static int blockToColumnIndex32(int blockXVal, int blockZVal) {
        //1 bit
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockZVal & mask) >> 3;
    }

    /** See {@link Coords#blockToColumnIndex(int, int)} */
    private static int blockToColumnIndex64(int blockXVal, int blockZVal) {
        //2 bit
        //1011101010001, 1010101011100, 1101011101010
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockZVal & mask) >> 2;
    }

    /** See {@link Coords#blockToColumnIndex(int, int)} */
    private static int blockToColumnIndex128(int blockXVal, int blockZVal) {
        //3 bit
        //1011101010001, 1010101011100, 1101011101010
        final int mask = POS_TO_INDEX_MASK;
        return (blockXVal & mask) >> 4 | (blockZVal & mask) >> 1;
    }

    public static int columnToIndex(int sectionX, int sectionZ) {
        return blockToColumnIndex(sectionX << 4, sectionZ << 4);
    }

    /**
     * @param idx Index of LevelChunkSection within its LevelCube
     *
     * @return The X offset (as a SectionPos) from its {@link CubePos} (as a SectionPos)
     */
    public static int indexToColumnX(int idx) {
        return idx >> INDEX_TO_N_X & INDEX_TO_POS_MASK;
    }

    /**
     * @param idx Index of the LevelChunkSection within its LevelCube
     *
     * @return The Z offset (as a SectionPos) from its {@link CubePos} (as a SectionPos)
     */
    public static int indexToColumnZ(int idx) {
        // use the mask for Y instead of Z since Z is the second coordinate here
        // TODO yeah this is bad, we should fix it later
        return idx >> INDEX_TO_N_Y & INDEX_TO_POS_MASK;
    }

    /**
     * @param cubeVal A single dimension of the {@link CubePos}
     * @param sectionOffset The SectionPos offset from the {@link CubePos} as a SectionPos. Suggest you use {@link Coords#indexToX}, {@link Coords#indexToY}, {@link
     *     Coords#indexToZ} to get this offset
     *
     * @return The cubeVal as a sectionVal
     */
    public static int cubeToSection(int cubeVal, int sectionOffset) {
        return cubeVal << (LOG2_BLOCK_SIZE - 4) | sectionOffset;
    }

    public static int sectionToCubeCeil(int viewDistance) {
        return MathUtil.ceilDiv(viewDistance, CubicConstants.DIAMETER_IN_SECTIONS);
    }

    public static int sectionToCubeRenderDistance(int viewDistance) {
        return Math.max(3, sectionToCubeCeil(viewDistance));
    }

    /**
     * @param blockVal A single dimension of a BlockPos
     *
     * @return That blockVal as a sectionVal
     */
    public static int blockToSection(int blockVal) {
        return blockVal >> 4;
    }

    /**
     * @param sectionVal A single dimension of a SectionPos
     *
     * @return That sectionVal as a blockVal
     */
    public static int sectionToMinBlock(int sectionVal) {
        return sectionVal << 4;
    }

    public static int blockToSectionLocal(int pos) {
        return pos & 0xF;
    }

    /**
     * @param cubePos The {@link CubePos}
     * @param i The index of the LevelChunkSection inside the {@link CubePos}
     *
     * @return The SectionPos of the LevelChunkSection at index i
     */
    public static MCSectionPos sectionPosByIndex(CubePos cubePos, int i) {
        return MCSectionPos.of(cubeToSection(cubePos.getX(), indexToX(i)), cubeToSection(cubePos.getY(), indexToY(i)), cubeToSection(cubePos.getZ(),
            indexToZ(i)));
    }

    /**
     * @param cubePos The {@link CubePos}
     * @param i The index of the LevelChunkSection inside the {@link CubePos}
     *
     * @return The ChunkPos of the column containing the LevelChunkSection at index i
     */
    public static MCChunkPos chunkPosByIndex(CubePos cubePos, int i) {
        return new MCChunkPos(cubeToSection(cubePos.getX(), indexToX(i)), cubeToSection(cubePos.getZ(), indexToZ(i)));
    }

    public static int blockToCubeLocalSection(int x) {
        return (x >> 4) & (CubicConstants.DIAMETER_IN_SECTIONS - 1);

    }

    public static int cubeLocalSection(int section) {
        return section & (CubicConstants.DIAMETER_IN_SECTIONS - 1);
    }

    public static MCBlockPos sectionPosToMinBlockPos(MCSectionPos sectionPos) {
        return new MCBlockPos(sectionToMinBlock(sectionPos.getX()), sectionToMinBlock(sectionPos.getY()), sectionToMinBlock(sectionPos.getZ()));
    }

    /**
     * @param entity An entity
     *
     * @return The {@link CubePos} x of the entity
     */
    public static int getCubeXForEntity(MCEntity entity) {
        return blockToCube(Math.floor(entity.getX()));
    }

    /**
     * @param entity An entity
     *
     * @return The {@link CubePos} y of the entity
     */
    public static int getCubeYForEntity(MCEntity entity) {
        // the entity is in the cube it's inside, not the cube it's standing on
        return blockToCube(Math.floor(entity.getY()));
    }

    /**
     * @param entity An entity
     *
     * @return The {@link CubePos} z of the entity
     */
    public static int getCubeZForEntity(MCEntity entity) {
        return blockToCube(Math.floor(entity.getZ()));
    }
}