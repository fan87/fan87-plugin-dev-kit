package me.fan87.plugindevkit.utils;

public class CoolDownTimer extends Timer {

    private final long coolDown;

    public CoolDownTimer(long coolDown) {
        this.coolDown = coolDown;
    }

    public boolean useItem() {
        return checkPassedAndReset(coolDown);
    }

    public long getTimeLeft() {
        return coolDown - getPassedTime();
    }

}
