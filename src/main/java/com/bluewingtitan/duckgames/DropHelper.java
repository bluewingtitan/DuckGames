package com.bluewingtitan.duckgames;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropHelper {


    static Random random = new Random();
    static List<ItemStack> basicDrops = null;
    static List<ItemStack> goodDrops = null;
    static List<ItemStack> betterDrops = null;
    static List<ItemStack> greatDrops = null;
    static List<ItemStack> awesomeDrops = null;

    public static void spawnDrop(Location location, int level){
        if(basicDrops == null) fillDrops();

        // Generate Loot.
        List<ItemStack> stacks = generateContents(level);

        // Spawn MineCart
        World w = Plugin.plugin.getServer().getWorlds().get(0);
        Entity e;
        e = w.spawnEntity(location, EntityType.MINECART_CHEST);

        StorageMinecart s = (StorageMinecart) e;

        for (ItemStack stack: stacks) {
            int r = random.nextInt(26);
            int x = 0;
            while (s.getInventory().getItem(r) != null){
                r = random.nextInt(26);
                x++;
                if(x == 10){
                    break;
                }
            }
            if(s.getInventory().getItem(r) != null){
                s.getInventory().addItem(stack);
            } else
                s.getInventory().setItem(r,stack);

        }

    }

    private static void fillDrops(){
        basicDrops = new ArrayList<ItemStack>();
        goodDrops = new ArrayList<ItemStack>();
        betterDrops = new ArrayList<ItemStack>();
        greatDrops = new ArrayList<ItemStack>();
        awesomeDrops = new ArrayList<ItemStack>();


        // basic drops
        basicDrops.add(new ItemStack(Material.APPLE, 6));
        basicDrops.add(new ItemStack(Material.APPLE, 3));
        basicDrops.add(new ItemStack(Material.APPLE, 5));
        basicDrops.add(new ItemStack(Material.APPLE, 9));
        basicDrops.add(new ItemStack(Material.APPLE, 8));
        basicDrops.add(new ItemStack(Material.BREAD, 8));
        basicDrops.add(new ItemStack(Material.BREAD, 4));
        basicDrops.add(new ItemStack(Material.BREAD, 7));
        basicDrops.add(new ItemStack(Material.PORKCHOP, 2));
        basicDrops.add(new ItemStack(Material.PORKCHOP, 3));
        basicDrops.add(new ItemStack(Material.COOKED_BEEF, 2));
        basicDrops.add(new ItemStack(Material.COOKED_BEEF, 4));
        basicDrops.add(new ItemStack(Material.CAKE, 1));
        basicDrops.add(new ItemStack(Material.WHITE_WOOL, 64));
        basicDrops.add(new ItemStack(Material.BLACK_WOOL, 64));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 48));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 16));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 29));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 17));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 34));
        basicDrops.add(new ItemStack(Material.COBBLESTONE, 25));
        basicDrops.add(new ItemStack(Material.TORCH, 6));
        basicDrops.add(new ItemStack(Material.TORCH, 4));
        basicDrops.add(new ItemStack(Material.TORCH, 6));
        basicDrops.add(new ItemStack(Material.TORCH, 9));
        basicDrops.add(new ItemStack(Material.OAK_LEAVES, 6));
        basicDrops.add(new ItemStack(Material.OAK_LEAVES, 3));
        basicDrops.add(new ItemStack(Material.OAK_LEAVES, 7));
        basicDrops.add(new ItemStack(Material.OAK_LEAVES, 11));
        basicDrops.add(new ItemStack(Material.OAK_LEAVES, 26));
        basicDrops.add(new ItemStack(Material.EMERALD, 7));
        basicDrops.add(new ItemStack(Material.EMERALD, 5));
        basicDrops.add(new ItemStack(Material.SMOKER, 1));
        basicDrops.add(new ItemStack(Material.SMOKER, 1));
        basicDrops.add(new ItemStack(Material.SMOKER, 1));
        basicDrops.add(new ItemStack(Material.SMOKER, 2));
        basicDrops.add(new ItemStack(Material.CRAFTING_TABLE, 1));
        basicDrops.add(new ItemStack(Material.CRAFTING_TABLE, 2));
        basicDrops.add(new ItemStack(Material.CRAFTING_TABLE, 4));
        basicDrops.add(new ItemStack(Material.CRAFTING_TABLE, 1));
        basicDrops.add(new ItemStack(Material.PINK_BED, 1));
        basicDrops.add(new ItemStack(Material.END_ROD, 4));
        basicDrops.add(new ItemStack(Material.END_ROD, 5));
        basicDrops.add(new ItemStack(Material.PHANTOM_MEMBRANE, 1));
        basicDrops.add(new ItemStack(Material.PHANTOM_MEMBRANE, 2));

        // good drops
        goodDrops.add(new ItemStack(Material.IRON_INGOT, 3));
        goodDrops.add(new ItemStack(Material.IRON_INGOT, 5));
        goodDrops.add(new ItemStack(Material.GOLDEN_BOOTS, 1));
        goodDrops.add(new ItemStack(Material.STRING, 2));
        goodDrops.add(new ItemStack(Material.GOLDEN_HELMET, 1));
        goodDrops.add(new ItemStack(Material.GOLDEN_LEGGINGS, 1));
        goodDrops.add(new ItemStack(Material.ARROW, 3));
        goodDrops.add(new ItemStack(Material.ARROW, 7));
        goodDrops.add(new ItemStack(Material.WATER_BUCKET, 1));
        goodDrops.add(new ItemStack(Material.DARK_OAK_BOAT, 1));
        goodDrops.add(new ItemStack(Material.LADDER, 4));
        goodDrops.add(new ItemStack(Material.PHANTOM_MEMBRANE, 4));
        goodDrops.add(new ItemStack(Material.SPECTRAL_ARROW, 2));
        goodDrops.add(new ItemStack(Material.FLINT_AND_STEEL, 1));
        goodDrops.add(new ItemStack(Material.BAT_SPAWN_EGG, 1));
        goodDrops.add(new ItemStack(Material.BAT_SPAWN_EGG, 1));
        goodDrops.add(new ItemStack(Material.BAT_SPAWN_EGG, 1));
        goodDrops.add(new ItemStack(Material.PORKCHOP, 4));
        goodDrops.add(new ItemStack(Material.PORKCHOP, 5));
        goodDrops.add(new ItemStack(Material.COOKED_BEEF, 3));
        goodDrops.add(new ItemStack(Material.COOKED_BEEF, 6));

        // better drops
        betterDrops.add(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
        betterDrops.add(new ItemStack(Material.GOLDEN_CHESTPLATE, 1));
        betterDrops.add(new ItemStack(Material.STRING, 5));
        betterDrops.add(new ItemStack(Material.STRING, 7));
        betterDrops.add(new ItemStack(Material.TNT, 2));
        betterDrops.add(new ItemStack(Material.ENDER_PEARL, 1));
        betterDrops.add(new ItemStack(Material.ENDER_PEARL, 2));
        betterDrops.add(new ItemStack(Material.LADDER, 6));
        betterDrops.add(new ItemStack(Material.LADDER, 7));
        betterDrops.add(new ItemStack(Material.LAPIS_LAZULI, 3));
        betterDrops.add(new ItemStack(Material.LAPIS_LAZULI, 4));
        goodDrops.add(new ItemStack(Material.COOKED_BEEF, 7));
        goodDrops.add(new ItemStack(Material.COOKED_BEEF, 9));

        // great drops
        greatDrops.add(new ItemStack(Material.IRON_INGOT, 11));
        greatDrops.add(new ItemStack(Material.APPLE, 15));
        greatDrops.add(new ItemStack(Material.DRAGON_HEAD, 1));
        greatDrops.add(new ItemStack(Material.ENDER_PEARL, 3));
        greatDrops.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        greatDrops.add(new ItemStack(Material.TNT, 4));
        greatDrops.add(new ItemStack(Material.TNT, 3));
        greatDrops.add(new ItemStack(Material.WATER_BUCKET, 1));
        greatDrops.add(new ItemStack(Material.WATER_BUCKET, 1));
        greatDrops.add(new ItemStack(Material.LADDER, 9));
        greatDrops.add(new ItemStack(Material.HAY_BLOCK, 5));

        // awesome drops
        awesomeDrops.add(new ItemStack(Material.DIAMOND, 1));
        awesomeDrops.add(new ItemStack(Material.DIAMOND, 1));
        awesomeDrops.add(new ItemStack(Material.DIAMOND, 2));
        awesomeDrops.add(new ItemStack(Material.ENDER_PEARL, 3));
        awesomeDrops.add(new ItemStack(Material.ENDER_PEARL, 3));
        awesomeDrops.add(new ItemStack(Material.ENDER_PEARL, 5));
        awesomeDrops.add(new ItemStack(Material.SPECTRAL_ARROW, 7));
        awesomeDrops.add(new ItemStack(Material.SPECTRAL_ARROW, 5));
        awesomeDrops.add(new ItemStack(Material.SPECTRAL_ARROW, 11));
        awesomeDrops.add(new ItemStack(Material.GOLDEN_APPLE, 1));
        awesomeDrops.add(new ItemStack(Material.GOLDEN_APPLE, 2));
        awesomeDrops.add(new ItemStack(Material.GOLDEN_APPLE, 3));
    }


    private static List<ItemStack> generateContents(int level){
        List<ItemStack> stacks = new ArrayList<>();

        int amount = 3 + random.nextInt(level*3);
        if(amount>27) amount = 27;

        for (int i = 0; i < amount; i++) {

            //0-49      : basic
            //50-69     : good
            //70-79     : better
            //80-86     : great
            //87-90     : awesome
            //>90       : re-roll

            int c = 200;
            int minus = 0;
            while (c>90){
                c = random.nextInt(40+ 10*level) + level*5;
                c -= minus;
                minus++;
            }

            if(c>=87){
                //awesome
                stacks.add(getRandomStack(awesomeDrops));
            } else if (c>=80){
                //great
                stacks.add(getRandomStack(greatDrops));
            } else if (c>=70){
                //better
                stacks.add(getRandomStack(betterDrops));
            } else if (c>=50){
                //good
                stacks.add(getRandomStack(goodDrops));
            } else {
                //basic
                stacks.add(getRandomStack(basicDrops));
            }
        }

        return stacks;
    }


    private static ItemStack getRandomStack(List<ItemStack> list){
        return list.get(random.nextInt(list.size()));
    }


}
