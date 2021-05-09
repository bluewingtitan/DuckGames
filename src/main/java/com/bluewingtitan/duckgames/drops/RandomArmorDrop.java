package com.bluewingtitan.duckgames.drops;

import com.bluewingtitan.duckgames.Plugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RandomArmorDrop implements Drop{

    private final RandomEnchantment[] possibleEnchantments;
    private final int maxEnchantments;
    private final Material armorPiece;

    public RandomArmorDrop(Material armorPiece, RandomEnchantment[] possibleEnchantments, int maxEnchantments) {
        this.possibleEnchantments = possibleEnchantments;
        this.armorPiece = armorPiece;
        this.maxEnchantments = maxEnchantments;
    }

    @Override
    public ItemStack getStack(int level) {
        int enchantments = Plugin.random.nextInt(Math.min(Plugin.random.nextInt(level),Plugin.random.nextInt(maxEnchantments+1)));
        //TODO: Do stuff!!
        return null;
    }
}
