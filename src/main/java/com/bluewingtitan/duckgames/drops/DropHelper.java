package com.bluewingtitan.duckgames.drops;

import com.bluewingtitan.duckgames.Plugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropHelper {


    static Random random = new Random();
    static List<Drop> basicDrops = null;
    static List<Drop> goodDrops = null;
    static List<Drop> betterDrops = null;
    static List<Drop> greatDrops = null;
    static List<Drop> awesomeDrops = null;

    public static void spawnDrop(Location location, int level){

        if(level>1000) level = 1000; // Deny timeouts, anything above 1000 won't make a difference anyways.

        if(basicDrops == null) fillDrops();

        // Generate Loot.
        List<ItemStack> stacks = generateContents(level);

        // Spawn MineCart
        World w = Plugin.plugin.getServer().getWorlds().get(0);
        Entity e;
        e = w.spawnEntity(location, EntityType.MINECART_CHEST);

        // Do this dump shit conversion
        StorageMinecart s = (StorageMinecart) e;

        // What does this do? WTF
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

        w.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 20f, 1f);
    }

    private static void fillDrops(){
        // Yes! This is dump code. Look at you,
        // How smort you are, pointing that out.
        // You know?
        // I wrote the following code just to annoy you. Yes. You.
        // Because fuck you, it works and works well, okay?


        basicDrops = new ArrayList<Drop>();
        goodDrops = new ArrayList<Drop>();
        betterDrops = new ArrayList<Drop>();
        greatDrops = new ArrayList<Drop>();
        awesomeDrops = new ArrayList<Drop>();


        // basic drops
        basicDrops.add(new RandomDrop(Material.APPLE, 2, 8, 0.8f));
        basicDrops.add(new RandomDrop(Material.BREAD, 3, 11, 0.5f));
        basicDrops.add(new RandomDrop(Material.PORKCHOP, 3, 5, 1f));
        basicDrops.add(new RandomDrop(Material.COOKED_BEEF, 3, 5, 1f));
        basicDrops.add(new StaticDrop(Material.CAKE, 1));
        basicDrops.add(new RandomDrop(Material.WHITE_WOOL, 17, 64, 0f));
        basicDrops.add(new RandomDrop(Material.BLACK_WOOL, 17, 64, 0f));
        basicDrops.add(new RandomDrop(Material.COBBLESTONE, 17, 64, 0f));
        basicDrops.add(new RandomDrop(Material.TORCH, 4, 8, 2f));
        basicDrops.add(new RandomDrop(Material.OAK_LEAVES, 4, 8, 8f));
        basicDrops.add(new RandomDrop(Material.EMERALD, 1, 8, 0.4f));
        basicDrops.add(new RandomDrop(Material.SMOKER, 1, 2, 0.3f));
        basicDrops.add(new RandomDrop(Material.CRAFTING_TABLE, 1, 2, 1f));
        basicDrops.add(new StaticDrop(Material.PINK_BED,1));
        basicDrops.add(new RandomDrop(Material.END_ROD, 4, 10, 2f));
        basicDrops.add(new RandomDrop(Material.PHANTOM_MEMBRANE, 1, 4, 0.4f));
        basicDrops.add(new StaticDrop(CustomItemHelper.getKnockbackStick()));
        basicDrops.add(new StaticDrop(CustomItemHelper.getLoonieToonies()));

        // good drops
        goodDrops.add(new RandomDrop(Material.IRON_INGOT, 2, 3, 1f));
        goodDrops.add(new StaticDrop(Material.GOLDEN_BOOTS,1));
        goodDrops.add(new StaticDrop(Material.GOLDEN_HELMET,1));
        goodDrops.add(new StaticDrop(Material.GOLDEN_LEGGINGS,1));
        goodDrops.add(new RandomDrop(Material.STRING, 1, 3, 0.5f));
        goodDrops.add(new RandomDrop(Material.ARROW, 2, 2, 0.8f));
        goodDrops.add(new StaticDrop(Material.BOW,1));
        goodDrops.add(new StaticDrop(Material.DARK_OAK_BOAT,1));
        goodDrops.add(new RandomDrop(Material.LADDER, 2, 10, 3f));
        goodDrops.add(new RandomDrop(Material.SPECTRAL_ARROW, 1, 3, 0.3f));
        goodDrops.add(new StaticDrop(Material.FLINT_AND_STEEL,1));
        goodDrops.add(new RandomDrop(Material.BAT_SPAWN_EGG, 1, 2, 1f));

        // better drops
        betterDrops.add(new RandomDrop(Material.TNT, 1, 4, 0.6f));
        betterDrops.add(new RandomDrop(Material.ENDER_PEARL, 1, 1, 0.15f));
        betterDrops.add(new RandomDrop(Material.LAPIS_LAZULI, 4, 5, 1.4f));

        // great drops
        greatDrops.add(new RandomDrop(Material.IRON_INGOT, 2, 4, 1f));
        greatDrops.add(new RandomDrop(Material.APPLE, 8, 12, 4f));
        greatDrops.add(new RandomDrop(Material.DRAGON_HEAD, 1, 1, 0.2f));
        greatDrops.add(new RandomDrop(Material.ENDER_PEARL, 2, 2, 0.35f));
        greatDrops.add(new StaticDrop(Material.GOLDEN_APPLE,1));
        greatDrops.add(new RandomDrop(Material.TNT, 3, 6, 0.3f));
        greatDrops.add(new StaticDrop(Material.WATER_BUCKET,1));
        greatDrops.add(new RandomDrop(Material.HAY_BLOCK, 1, 7, 2f));

        // awesome drops
        awesomeDrops.add(new RandomDrop(Material.DIAMOND, 1, 2, 0.3f));
        awesomeDrops.add(new RandomDrop(Material.SPECTRAL_ARROW, 3, 10, 1.3f));
        awesomeDrops.add(new StaticDrop(Material.GOLDEN_APPLE,2));
        awesomeDrops.add(new StaticDrop(CustomItemHelper.fakeGapple()));
    }


    private static List<ItemStack> generateContents(int level){
        List<ItemStack> stacks = new ArrayList<>();

        int amount = 3 + random.nextInt(level*2 + 1) + level;
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
                c = random.nextInt(50+ 10*level) + level*5;
                c -= minus;
                minus++;
            }

            if(c>=87){
                //awesome
                stacks.add(getRandomDrop(awesomeDrops).getStack(level));
            } else if (c>=80){
                //great
                stacks.add(getRandomDrop(greatDrops).getStack(level));
            } else if (c>=70){
                //better
                stacks.add(getRandomDrop(betterDrops).getStack(level));
            } else if (c>=50){
                //good
                stacks.add(getRandomDrop(goodDrops).getStack(level));
            } else {
                //basic
                stacks.add(getRandomDrop(basicDrops).getStack(level));
            }
        }

        return stacks;
    }


    private static Drop getRandomDrop(List<Drop> list){
        return list.get(random.nextInt(list.size()));
    }


}
