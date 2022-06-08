package io.github.opencubicchunks.cc_core.config;

import java.io.File;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;

public class CommonConfig extends BaseConfig {
    private static final String FILE_NAME = "cubicchunks_common.toml";
    private static final File FILE_PATH = new File(getConfigDirectory(), FILE_NAME);

    private static final String KEY_GENERAL = "general";
    private static final String KEY_VERTICAL_VIEW_DISTANCE = KEY_GENERAL + ".verticalViewDistance";
    private static final String KEY_GENERATE_NEW_WORLDS_AS_CC = KEY_GENERAL + ".generateNewWorldsAsCC";

    private final CommentedConfig config;

    private CommonConfig(CommentedConfig config) {
        this.config = config;
    }

    private static CommentedConfig createDefaultConfig() {
        Config.setInsertionOrderPreserved(true);
        var config = CommentedConfig.inMemory();
        config.set(KEY_VERTICAL_VIEW_DISTANCE, 8);
        // TODO more detailed config comment?
        config.setComment(KEY_VERTICAL_VIEW_DISTANCE, """
 The vertical view distance for players in Cubic Chunks dimensions (similar to vanilla render distance for the horizontal axes).\
""");
        config.set(KEY_GENERATE_NEW_WORLDS_AS_CC, true);
        config.setComment(KEY_GENERATE_NEW_WORLDS_AS_CC, """
 Whether or not newly-created worlds generate using Cubic Chunks. (On the client, this is toggled by the button in the world creation GUI.)\
""");
        return config;
    }

    // TODO save on game exit, etc, instead of every time config is marked dirty
    public void markDirty() {
        write(FILE_PATH, config);
    }

    // TODO do we want config values in fields on this class, instead of doing a get() each time?
    public int getVerticalViewDistance() {
        return config.getInt(KEY_VERTICAL_VIEW_DISTANCE);
    }

    public boolean shouldGenerateNewWorldsAsCC() {
        return config.get(KEY_GENERATE_NEW_WORLDS_AS_CC);
    }

    public void setVerticalViewDistance(int verticalViewDistance) {
        config.set(KEY_VERTICAL_VIEW_DISTANCE, verticalViewDistance);
    }

    public void setGenerateNewWorldsAsCC(boolean generateNewWorldsAsCC) {
        config.set(KEY_GENERATE_NEW_WORLDS_AS_CC, generateNewWorldsAsCC);
    }

    public static CommonConfig getConfig() {
        var config = createDefaultConfig();
        // Read existing values to the config
        if (FILE_PATH.exists()) {
            read(FILE_PATH, config);
        }
        var commonConfig = new CommonConfig(config);
        // Write the config again even if we loaded an existing file, in case any keys were missing or invalid
        write(FILE_PATH, config);
        return commonConfig;
    }
}
