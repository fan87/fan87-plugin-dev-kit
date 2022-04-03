package me.fan87.plugindevkit.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class ItemUtils {

    public static boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR;
    }

    public static ItemMatrix<ItemStack> getRecipeMin(ItemStack[] itemStacks, int width, int height) {
        return new ItemMatrix<ItemStack>(itemStacks, ItemStack.class, width, height).cleanEmpty(new Predicate<ItemStack>() {
            @Override
            public boolean test(ItemStack itemStack) {
                return isAir(itemStack);
            }
        });
    }

}
