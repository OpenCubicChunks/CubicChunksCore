package io.github.opencubicchunks.cc_core.api;

import io.github.opencubicchunks.cc_core.config.EarlyConfig;

public class CubicConstants {
    public static final int SECTION_DIAMETER = 16;
    public static final int DIAMETER_IN_SECTIONS = EarlyConfig.getDiameterInSections();
    public static final int SECTION_COUNT = DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS;
    public static final int CHUNK_COUNT = DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS;
    public static final int DIAMETER_IN_BLOCKS = SECTION_DIAMETER * DIAMETER_IN_SECTIONS;
    public static final int BLOCK_COUNT = DIAMETER_IN_BLOCKS * DIAMETER_IN_BLOCKS * DIAMETER_IN_BLOCKS;
    public static final int BLOCK_COLUMNS_PER_SECTION = SECTION_DIAMETER * SECTION_DIAMETER;
    public static final int SIZE_BITS = (int) Math.round(Math.log(DIAMETER_IN_BLOCKS) / Math.log(2.0D));
}
