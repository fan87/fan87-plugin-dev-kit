package me.fan87.plugindevkit.utils;

import lombok.Getter;
import lombok.Setter;

public class Timer {

    @Setter
    @Getter
    private long lastResetTime = System.currentTimeMillis();

    public Timer() {

    }

    public boolean hasPassed(long time) {
        return getPassedTime() > time;
    }

    public boolean checkPassedAndReset(long time) {
        if (hasPassed(time)) {
            reset();
            return true;
        }
        return false;
    }

    public void reset() {
        lastResetTime = System.currentTimeMillis();
    }

    public long getPassedTime() {
        return System.currentTimeMillis() - lastResetTime;
    }

}
