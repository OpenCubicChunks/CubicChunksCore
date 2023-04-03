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

@UsedFromASM
public class CubePos extends MCVec3i {
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
        i |= ((long) this.getX() & (1 << 21) - 1) << 43;
        i |= ((long) this.getY() & (1 << 22) - 1);
        i |= ((long) this.getZ() & (1 << 21) - 1) << 22;
        return i;
    }

    @UsedFromASM
    public static long asLong(int x, int y, int z) {
        long i = 0L;
        i |= ((long) x & (1 << 21) - 1) << 43;
        i |= ((long) y & (1 << 22) - 1);
        i |= ((long) z & (1 << 21) - 1) << 22;
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
        return (int) (packed >> 43);
    }

    public static int extractY(long packed) {
        return (int) (packed << 42 >> 42);
    }

    public static int extractZ(long packed) {
        return (int) (packed << 21 >> 43);
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