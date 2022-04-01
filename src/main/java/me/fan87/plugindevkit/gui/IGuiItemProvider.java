package me.fan87.plugindevkit.gui;

import org.bukkit.inventory.ItemStack;

public interface IGuiItemProvider {

    ItemStack getPreviousPageItem(String pageName);
    ItemStack getClosePageItem();

}
