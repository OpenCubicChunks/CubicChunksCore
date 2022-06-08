package io.github.opencubicchunks.cc_core.world;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ColumnCubeMap {
    private final ConcurrentHashMap.KeySetView<Integer, Boolean> loadedCubes = ConcurrentHashMap.newKeySet();

    public boolean isLoaded(int cubeY) {
        return loadedCubes.contains(cubeY);
    }

    public void markLoaded(int cubeY) {
        loadedCubes.add(cubeY);
    }

    public void markUnloaded(int cubeY) {
        loadedCubes.remove(cubeY);
    }

    public Set<Integer> getLoaded() {
        return loadedCubes;
    }
}
