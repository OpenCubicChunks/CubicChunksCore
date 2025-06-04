package io.github.opencubicchunks.cc_core.api;

import static io.github.opencubicchunks.cc_core.utils.Coords.blockToCube;
import static io.github.opencubicchunks.cc_core.utils.Coords.cubeToSection;
import static io.github.opencubicchunks.cc_core.utils.Coords.sectionToCube;

import java.util.stream.Stream;

import com.google.common.base.MoreObjects;
import io.github.opencubicchunks.cc_core.annotation.UsedFromASM;
import io.github.opencubicchunks.cc_core.minecraft.MCBlockPos;
import io.github.opencubicchunks.cc_core.minecraft.MCChunkPos;
import io.github.opencubicchunks.cc_core.minecraft.MCEntity;
import io.github.opencubicchunks.cc_core.minecraft.MCSectionPos;
import io.github.opencubicchunks.cc_core.minecraft.MCVec3i;
import io.github.opencubicchunks.cc_core.utils.Coords;

/**
 * A representation of the position of a Cube.
 * <br><br>
 * When packed as a long, cube positions are packed with 21 bits per axis. The parity of the top two bits of the long is used to distinguish between chunks and cubes internally
 * (if bit 0 XOR bit 1, it is a cube, otherwise it is a chunk). Thus, packed cube positions will always begin with 01 or 10.
 * <br>
 * Also note that for cubes the top two bits (the parity bit, and the top bit of the Z coordinate) are inverted, as otherwise {@link Long#MAX_VALUE} would be a valid position (-1, -1, -1).
 * <br>
 * Invalid CubePos long:          <br> <code> 0b01111111 11111111 11111111 11111111 11111111 11111111 11111111 11111111 </code> <br>
 * Positive Z CubePos long:  <br> <code> 0b01ZZZZZZ ZZZZZZZZ ZZZZZZYY YYYYYYYY YYYYYYYY YYYXXXXX XXXXXXXX XXXXXXXX </code> <br>
 * Negative Z CubePos long:  <br> <code> 0b10ZZZZZZ ZZZZZZZZ ZZZZZZYY YYYYYYYY YYYYYYYY YYYXXXXX XXXXXXXX XXXXXXXX </code>
 * <br>
 * @see io.github.opencubicchunks.cc_core.world.level.CloPos
 */
@UsedFromASM
public class CubePos extends MCVec3i {
    /**
     * long representing an invalid CubePos. This is the same value as {@link MCChunkPos#INVALID_CHUNK_POS} and {@link io.github.opencubicchunks.cc_core.world.level.CloPos#INVALID_CLO_POS}.
     */
    public static final long INVALID_CUBE_POS = Long.MAX_VALUE;
    public static final int MAX_COORDINATE_VALUE = Coords.blockToCube(33554431);
    public static final CubePos ZERO = new CubePos(0, 0, 0);
    private static final long TOP_TWO_BITS_MASK = (0b11L << 62);

    private CubePos(int x, int y, int z) {
        super(x, y, z);
    }

    // Used from ASM, do not change
    public CubePos(long cubePosIn) {
        this(extractX(cubePosIn), extractY(cubePosIn), extractZ(cubePosIn));
    }

    // Used from ASM, do not change
    public CubePos(MCBlockPos pos) {
        this(blockToCube(pos.getX()), blockToCube(pos.getY()), blockToCube(pos.getZ()));
    }

    public static CubePos of(int x, int y, int z) {
        return new CubePos(x, y, z);
    }

    @UsedFromASM
    public long asLong() {
        long i = 0L;
        i |= ((long) this.getX() & (1 << 21) - 1);
        i |= ((long) this.getY() & (1 << 21) - 1) << 21;
        i |= ((long) this.getZ() & (1 << 21) - 1) << 42;
        // If 2nd bit isn't set, set 1st bit, since cubes are marked by starting with 0b01 or 0b10
        if (i < (1L << 62)) i |= (1L << 63);
        // invert the top two bits for storage (as explained in the class javadoc)
        i ^= TOP_TWO_BITS_MASK;
        return i;
    }

    @UsedFromASM
    public static long asLong(int x, int y, int z) {
        long i = 0L;
        i |= ((long) x & (1 << 21) - 1);
        i |= ((long) y & (1 << 21) - 1) << 21;
        i |= ((long) z & (1 << 21) - 1) << 42;
        // If 2nd bit isn't set, set 1st bit, since cubes are marked by starting with 0b01 or 0b10
        if (i < (1L << 62)) i |= (1L << 63);
        // invert the top two bits for storage (as explained in the class javadoc)
        i ^= TOP_TWO_BITS_MASK;
        return i;
    }

    @UsedFromASM
    public static long asLong(MCBlockPos pos) {
        return asLong(Coords.blockToCube(pos.getX()), Coords.blockToCube(pos.getY()), Coords.blockToCube(pos.getZ()));
    }

    public static boolean isLongInsideInclusive(long pos, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        int x = extractX(pos);
        if (x < minX || x > maxX) {
            return false;
        }
        int y = extractY(pos);
        if (y < minY || y > maxY) {
            return false;
        }
        int z = extractZ(pos);
        return z >= minZ && z <= maxZ;
    }

    public static long asChunkPosLong(long cubePosIn, int localX, int localZ) {
        return MCChunkPos.asLong(Coords.cubeToSection(CubePos.extractX(cubePosIn), localX), Coords.cubeToSection(CubePos.extractZ(cubePosIn), localZ));
    }

    public MCChunkPos asChunkPos() {
        return new MCChunkPos(cubeToSection(this.getX(), 0), cubeToSection(this.getZ(), 0));
    }

    public MCChunkPos asChunkPos(int dx, int dz) {
        return new MCChunkPos(cubeToSection(this.getX(), dx), cubeToSection(this.getZ(), dz));
    }

    public static CubePos from(long cubePosIn) {
        return new CubePos(cubePosIn);
    }

    public static CubePos from(MCBlockPos blockPosIn) {
        return new CubePos(blockToCube(blockPosIn.getX()), blockToCube(blockPosIn.getY()), blockToCube(blockPosIn.getZ()));
    }

    public static CubePos from(MCChunkPos position, int yPos) {
        return new CubePos(sectionToCube(position.x), yPos, sectionToCube(position.z));
    }

    public static CubePos from(MCSectionPos sectionPos) {
        return new CubePos(
            Coords.sectionToCube(sectionPos.getX()),
            Coords.sectionToCube(sectionPos.getY()),
            Coords.sectionToCube(sectionPos.getZ()));
    }

    public static CubePos from(MCEntity entity) {
        return new CubePos(blockToCube(Math.floor(entity.getX())),
            blockToCube(Math.floor(entity.getY())),
            blockToCube(Math.floor(entity.getZ())));
    }

    public static CubePos from(double x, double y, double z) {
        return new CubePos(blockToCube(Math.floor(x)),
            blockToCube(Math.floor(y)),
            blockToCube(Math.floor(z)));
    }

    public static int extractX(long packed) {
        return (int) (packed << 43 >> 43);
    }

    public static int extractY(long packed) {
        return (int) (packed << 22 >> 43);
    }

    public static int extractZ(long packed) {
        // re-invert the top two bits, since they were inverted for storage
        packed ^= TOP_TWO_BITS_MASK;
        return (int) (packed << 1 >> 43);
    }

    public int minCubeX() {
        return Coords.cubeToMinBlock(getX());
    }

    public int minCubeY() {
        return Coords.cubeToMinBlock(getY());
    }

    public int minCubeZ() {
        return Coords.cubeToMinBlock(getZ());
    }

    public int maxCubeX() {
        return Coords.cubeToMaxBlock(getX());
    }

    public int maxCubeY() {
        return Coords.cubeToMaxBlock(getY());
    }

    public int maxCubeZ() {
        return Coords.cubeToMaxBlock(getZ());
    }

    public MCSectionPos asSectionPos() {
        return MCSectionPos.of(cubeToSection(this.getX(), 0), cubeToSection(this.getY(), 0), cubeToSection(this.getZ(), 0));
    }

    public MCBlockPos asBlockPos() {
        return new MCBlockPos(minCubeX(), minCubeY(), minCubeZ());
    }

    public MCBlockPos asBlockPos(int localX, int localY, int localZ) {
        return new MCBlockPos(minCubeX() + localX, minCubeY() + localY, minCubeZ() + localZ);
    }

    public int blockX(int localX) {
        return Coords.localToBlock(getX(), localX);
    }

    public int blockY(int localY) {
        return Coords.localToBlock(getY(), localY);
    }

    public int blockZ(int localZ) {
        return Coords.localToBlock(getZ(), localZ);
    }

    public int getRegionX() {
        return getX() >> 3;
    }

    public int getRegionY() {
        return getY() >> 3;
    }

    public int getRegionZ() {
        return getZ() >> 3;
    }

    public int getLocalRegionX() {
        return getX() & 15;
    }

    public int getLocalRegionY() {
        return getY() & 15;
    }

    public int getLocalRegionZ() {
        return getZ() & 15;
    }

    public static long sectionToCubeSectionLong(long sectionPosIn) {
        return CubePos.from(MCSectionPos.of(sectionPosIn)).asSectionPos().asLong();
    }
  
    public static Stream<MCSectionPos> sectionsAroundCube(CubePos center, int radiusSections) {
        return MCSectionPos.cube(center.asSectionPos(), radiusSections);
    }

    public boolean isInsideInclusive(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return getX() >= minX && getY() >= minY && getZ() >= minZ
            && getX() <= maxX && getY() <= maxY && getZ() <= maxZ;
    }

    public int getChessboardDistance(CubePos cubePos) {
        return this.getChessboardDistance(cubePos.getX(), cubePos.getY(), cubePos.getZ());
    }

    public int getChessboardDistance(int x, int y, int z) {
        return Math.max(Math.max(Math.abs(this.getX() - x), Math.abs(this.getZ() - z)), Math.abs(this.getY() - y));
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CubePos cubePos = (CubePos) o;
        return getX() == cubePos.getX() && getY() == cubePos.getY() && getZ() == cubePos.getZ();
    }

    @Override public int hashCode() {
        return super.hashCode();
    }
}