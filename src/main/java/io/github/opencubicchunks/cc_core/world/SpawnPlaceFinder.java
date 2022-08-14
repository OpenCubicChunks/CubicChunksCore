package io.github.opencubicchunks.cc_core.world;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import io.github.opencubicchunks.cc_core.CubicChunks;
import io.github.opencubicchunks.cc_core.minecraft.MCBlockGetter;
import io.github.opencubicchunks.cc_core.minecraft.MCBlockPos;
import io.github.opencubicchunks.cc_core.minecraft.MCLevelHeightAccessor;
import io.github.opencubicchunks.cc_core.utils.MathUtil;

public final class SpawnPlaceFinder {

    private static final int MIN_FREE_SPACE_SPAWN = 32;

    private SpawnPlaceFinder() {
        throw new Error();
    }

    @Nullable
    public static MCBlockPos getTopBlockBisect(MCBlockGetter level, MCBlockPos pos, boolean checkValid, Predicate<MCBlockPos> isValidSpawnBlock,
                                               Predicate<MCBlockPos> isEmptyCollision) {
        MCBlockPos minPos, maxPos;
        if (findNonEmpty(pos, isEmptyCollision) == null) {
            CubicChunks.LOGGER.debug("Starting bisect with empty space at init {}", pos);
            maxPos = pos;
            minPos = findMinPos(level, pos, isEmptyCollision);
        } else {
            CubicChunks.LOGGER.debug("Starting bisect without empty space at init {}", pos);
            minPos = pos;
            maxPos = findMaxPos(level, pos, isEmptyCollision);
        }
        CubicChunks.LOGGER.debug("Found minPos {} and maxPos {}", minPos, maxPos);
        if (minPos == null || maxPos == null) {
            CubicChunks.LOGGER.error("No suitable spawn found, using original input {} (min={}, max={})", pos, minPos, maxPos);
            return null;
        }
        assert findNonEmpty(maxPos, isEmptyCollision) == null && findNonEmpty(minPos, isEmptyCollision) != null;
        MCBlockPos groundPos = bisect(minPos.below(MIN_FREE_SPACE_SPAWN), maxPos.above(MIN_FREE_SPACE_SPAWN), isEmptyCollision);
        if (checkValid && !isValidSpawnBlock.test(groundPos)) {
            return null;
        }
        return groundPos.above();
    }

    private static MCBlockPos bisect(MCBlockPos min, MCBlockPos max, Predicate<MCBlockPos> isEmptyCollision) {
        while (min.getY() < max.getY() - 1) {
            CubicChunks.LOGGER.debug("Bisect step with min={}, max={}", min, max);
            MCBlockPos middle = middleY(min, max);
            if (findNonEmpty(middle, isEmptyCollision) != null) {
                // middle has solid space, so it can be used as new minimum
                min = middle;
            } else {
                // middle is empty, so can be used as new maximum
                max = middle;
            }
        }
        // now max should contain the all-empty part, but min should still have filled part.
        return min;
    }

    private static MCBlockPos middleY(MCBlockPos min, MCBlockPos max) {
        return new MCBlockPos(min.getX(), (int) ((min.getY() + (long) max.getY()) >> 1), min.getZ());
    }

    @Nullable
    private static MCBlockPos findMinPos(MCBlockGetter level, MCBlockPos pos, Predicate<MCBlockPos> isEmptyCollision) {
        // go down twice as much each time until we hit filled space
        double dy = 16;
        while (findNonEmpty(inWorldUp(level, pos, -dy), isEmptyCollision) == null) {
            if (dy > Integer.MAX_VALUE) {
                CubicChunks.LOGGER.debug("Error finding spawn point: can't find solid start height at {}", pos);
                return null;
            }
            dy *= 2;
        }
        return inWorldUp(level, pos, -dy);
    }

    @Nullable
    private static MCBlockPos findMaxPos(MCBlockGetter level, MCBlockPos pos, Predicate<MCBlockPos> isEmptyCollision) {
        // go up twice as much each time until we hit empty space
        double dy = 16;
        while (findNonEmpty(inWorldUp(level, pos, dy), isEmptyCollision) != null) {
            if (dy > Integer.MAX_VALUE) {
                CubicChunks.LOGGER.debug("Error finding spawn point: can't find non-solid end height at {}", pos);
                return null;
            }
            dy *= 2;
        }
        return inWorldUp(level, pos, dy);
    }

    @Nullable
    private static MCBlockPos findNonEmpty(MCBlockPos pos, Predicate<MCBlockPos> isEmptyCollision) {
        for (int i = 0; i < MIN_FREE_SPACE_SPAWN; i++, pos = pos.above()) {
            if (!isEmptyCollision.test(pos)) {
                return pos;
            }
        }
        return null;
    }

    private static MCBlockPos inWorldUp(MCLevelHeightAccessor level, MCBlockPos original, double up) {
        int y = (int) (original.getY() + up);
        y = MathUtil.clamp(y, level.getMinBuildHeight(), level.getMaxBuildHeight());
        return new MCBlockPos(original.getX(), y, original.getZ());
    }
}