package me.fan87.plugindevkit.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerMap<T> {

    private final HashMap<UUID, T> map = new HashMap<>();

    private final T defaultValue;

    public PlayerMap(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public T get(Player player) {
        T value = map.getOrDefault(player.getUniqueId(), defaultValue);
        set(player, value);
        return value;
    }

    public void set(Player player, T value) {
        map.getOrDefault(player.getUniqueId(), value);
    }

}
