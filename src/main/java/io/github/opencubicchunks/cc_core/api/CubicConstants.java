package io.github.opencubicchunks.cc_core.api;

import io.github.opencubicchunks.cc_core.config.EarlyConfig;

public interface CubicConstants {
    int SECTION_DIAMETER = 16;
    int DIAMETER_IN_SECTIONS = EarlyConfig.getDiameterInSections();
    int SECTION_COUNT = DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS;
    int CHUNK_COUNT = DIAMETER_IN_SECTIONS * DIAMETER_IN_SECTIONS;
    int DIAMETER_IN_BLOCKS = SECTION_DIAMETER * DIAMETER_IN_SECTIONS;
    int BLOCK_COUNT = DIAMETER_IN_BLOCKS * DIAMETER_IN_BLOCKS * DIAMETER_IN_BLOCKS;
    int BLOCK_COLUMNS_PER_SECTION = SECTION_DIAMETER * SECTION_DIAMETER;
    int SIZE_BITS = (int) Math.round(Math.log(DIAMETER_IN_BLOCKS) / Math.log(2.0D));
}
