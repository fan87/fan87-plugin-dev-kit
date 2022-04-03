package me.fan87.plugindevkit.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerListMap<T> extends PlayerMap<List<T>> {
    public PlayerListMap() {
        super(new ArrayList<>());
    }

    public void add(Player player, T value) {
        List<T> list = get(player);
        list.add(value);
        set(player, list);
    }

}
