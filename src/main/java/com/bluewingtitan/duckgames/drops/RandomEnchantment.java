package com.bluewingtitan.duckgames.drops;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

public class RandomEnchantment {
    public final Enchantment enchantment;
    public final int maxLevel;
    public final int baseMax;

    public RandomEnchantment(Enchantment enchantment, int maxLevel, int baseMax) {
        this.enchantment = enchantment;
        this.maxLevel = maxLevel;
        this.baseMax = baseMax;
    }

    public ItemStack applyEnchantment(ItemStack stack, int level){


        return stack;
    }

}
