package com.bluewingtitan.duckgames.drops;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StaticDrop implements Drop{


    private final ItemStack dummyStack;

    public StaticDrop(ItemStack dummyStack) {
        this.dummyStack = dummyStack;
    }

    public StaticDrop(Material material, int amount) {
        this.dummyStack = new ItemStack(material, amount);
    }

    @Override
    public ItemStack getStack(int level) {
        return new ItemStack(dummyStack);
    }
}
