package io.github.opencubicchunks.cc_core.server;

import javax.annotation.Nullable;

import io.github.opencubicchunks.cc_core.config.ServerConfig;

public interface CubicMinecraftServer {
    @Nullable ServerConfig getServerConfig();
}
