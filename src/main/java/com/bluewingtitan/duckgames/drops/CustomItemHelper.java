package com.bluewingtitan.duckgames.drops;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemHelper {
    private CustomItemHelper(){

    }


    public static ItemStack getKnockbackStick(){
        ItemStack stack = new ItemStack(Material.STICK,1);

        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 5,true);
        meta.setDisplayName("KNOCKBACK STICK");
        stack.setItemMeta(meta);

        return stack;
    }

}
