package com.bluewingtitan.duckgames.drops;

import com.bluewingtitan.duckgames.Plugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RandomDrop implements Drop{

    private final ItemStack dummyStack;
    private final int minAmount;
    private final int maxAmount;
    private final float levelEffect;

    public RandomDrop(ItemStack dummyStack, int minAmount, int maxAmount, float levelEffect) {
        this.dummyStack = dummyStack;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.levelEffect = levelEffect;
    }


    public RandomDrop(Material material, int minAmount, int maxAmount, float levelEffect) {
        this.dummyStack = new ItemStack(material);
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.levelEffect = levelEffect;
    }

    @Override
    public ItemStack getStack(int level) {
        int maxAmountDraw = Math.round(maxAmount-minAmount+1 + level*levelEffect);
        int amount = Math.round(Plugin.random.nextInt(maxAmountDraw)+minAmount);
        ItemStack stack =  new ItemStack(dummyStack);
        stack.setAmount(amount);
        return stack;
    }
}
