package me.fan87.plugindevkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginInstanceGrabber {

    public static Plugin getPluginInstance() {
        return Bukkit.getServer().getPluginManager().getPlugins()[0];
    }

}
