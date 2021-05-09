package com.bluewingtitan.duckgames.drops;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomItemHelper {
    private CustomItemHelper(){}



    public static ItemStack getKnockbackStick(){
        ItemStack stack = new ItemStack(Material.STICK,1);

        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.KNOCKBACK, 5,true);
        meta.setDisplayName(ChatColor.BLUE + "Knockback Stiiigggaaa waaas?!?");
        stack.setItemMeta(meta);
        return stack;
    }


    public static ItemStack fakeGapple(){
        ItemStack stack = new ItemStack(Material.GOLDEN_APPLE,1);

        ItemMeta meta = stack.getItemMeta();
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.DURABILITY,42,true);
        meta.setDisplayName(ChatColor.GOLD + "Enchänted Golden Apple");

        stack.setItemMeta(meta);
        return stack;
    }



    public static ItemStack getLoonieToonies(){
        ItemStack stack = new ItemStack(Material.HONEYCOMB,9);

        ItemMeta meta = stack.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY,5,true);
        meta.setDisplayName(ChatColor.GOLD + "TurbulenzToonies");
        meta.setLore(Arrays.asList(new String[]{"DEFINITLEY CREATED BY ECOMMUNY."}));
        stack.setItemMeta(meta);
        return stack;
    }

}
