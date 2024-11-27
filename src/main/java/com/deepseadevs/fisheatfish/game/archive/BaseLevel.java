package com.deepseadevs.fisheatfish.game.archive;

import com.deepseadevs.fisheatfish.game.FishTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseLevel {
    private final List<FishTypes> fishTypes;
    private final String name;
    private final int fishCount;

    public BaseLevel(String name, int fishCount, FishTypes... fishTypes) {
        this.name = name;
        this.fishCount = fishCount;
        this.fishTypes = new ArrayList<>(Arrays.asList(fishTypes));
    }

    public String getName() {
        return this.name;
    }

    public int getFishCount() {
        return this.fishCount;
    }

    public List<FishTypes> getFishTypes() {
        return new ArrayList<>(this.fishTypes);
    }
}
