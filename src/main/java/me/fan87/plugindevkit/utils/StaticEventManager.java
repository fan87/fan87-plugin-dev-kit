package me.fan87.plugindevkit.utils;

import me.fan87.plugindevkit.PluginInstanceGrabber;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class StaticEventManager {

    public static void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, PluginInstanceGrabber.getPluginInstance());
    }

}
