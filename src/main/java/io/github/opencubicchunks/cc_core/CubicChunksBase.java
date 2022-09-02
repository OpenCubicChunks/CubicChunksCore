package io.github.opencubicchunks.cc_core;

import io.github.opencubicchunks.cc_core.api.CubePos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CubicChunksBase {

    // TODO: debug and fix optimized cubeload
    public static final boolean OPTIMIZED_CUBELOAD = false;

    public static final long SECTIONPOS_SENTINEL = -1;

    public static final int MAX_SUPPORTED_HEIGHT = Integer.MAX_VALUE / 2;
    public static final int MIN_SUPPORTED_HEIGHT = -MAX_SUPPORTED_HEIGHT;
    public static final int SEA_LEVEL = 64;

    public static final String MODID = "cubicchunks";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String PROTOCOL_VERSION = "0";


    public CubicChunksBase() {
        CubePos.class.getName();
    }
}