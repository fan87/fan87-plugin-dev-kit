package me.fan87.plugindevkit.utils;

public class Timer {

    private long resetTime = System.currentTimeMillis();

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
        resetTime = System.currentTimeMillis();
    }

    public long getPassedTime() {
        return System.currentTimeMillis() - resetTime;
    }

}
