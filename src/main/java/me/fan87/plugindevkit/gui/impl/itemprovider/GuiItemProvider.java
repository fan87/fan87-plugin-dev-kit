package me.fan87.plugindevkit.gui.impl.itemprovider;

import me.fan87.plugindevkit.gui.IGuiItemProvider;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiItemProvider implements IGuiItemProvider {

    public ItemStack getPreviousPageItem(String pageName) {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aGo Back");
        List<String> lores = new ArrayList<>();
        lores.add("§7To " + pageName);
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getClosePageItem() {
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§cClose");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
