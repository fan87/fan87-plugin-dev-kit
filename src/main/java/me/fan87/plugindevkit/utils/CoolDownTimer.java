package me.fan87.plugindevkit.utils;

public class CoolDownTimer extends Timer {

    private final long coolDown;

    public CoolDownTimer(long coolDown) {
        this.coolDown = coolDown;
        setLastResetTime(0);
    }

    public boolean useItem() {
        return checkPassedAndReset(coolDown);
    }

    public long getTimeLeft() {
        return Math.max(0, coolDown - getPassedTime());
    }

}
