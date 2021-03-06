package com.bluewingtitan.duckgames;

import com.bluewingtitan.duckgames.drops.DropHelper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BorderCode implements Runnable{

    private Plugin plugin;
    
    Random random = new Random();

    private Step currentStep = null;

    private Location nextDropLocation = null;
    private GameEvent nextReveal = null;
    private GameEvent nextDrop = null;
    private int dropLevel = 0;
    private boolean noRevealLeft = false;
    private boolean noDropLeft = false;

    private Laser laser;

    public BorderCode(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(!plugin.started) return;

        handleDisconnectedPlayers();
        handleReveals();
        handleDrops();

        plugin.time--;


        if(plugin.time == 0){
            for (Player p: plugin.getServer().getOnlinePlayers()) {
                if(p.getGameMode() != GameMode.SURVIVAL) continue;

                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 3000, 1, true, false));
            }

            plugin.announce(ChatColor.RED + " Wir haben Minute 0 erreicht!");
            plugin.announce("Möge die beste Ente gewinnen.");
            plugin.shrink(1, 120);
        }

        if(plugin.time < 0){
            for (Player p: plugin.getServer().getOnlinePlayers()) {
                if(p.getGameMode() != GameMode.SURVIVAL) continue;
                if(!p.hasPotionEffect(PotionEffectType.GLOWING)) // Re-Apply Potion Effect if there are very smort players.
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 3000, 1, true, false));
            }
        }




        if(plugin.steps.isEmpty()) return;




        // Get current step
        if(currentStep == null){
            //Pop out

            currentStep = plugin.steps.get(0);
            plugin.steps.remove(0);
        }

        //Check for time
        int timeUntilStart = plugin.time - currentStep.SecondsAfter;

        if(timeUntilStart == 1200) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 20 + ChatColor.WHITE + " Minuten bis die Border schrumpft.");
        if(timeUntilStart == 600) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 10 + ChatColor.WHITE + " Minuten bis die Border schrumpft.");
        if(timeUntilStart == 300) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 5 + ChatColor.WHITE + " Minuten bis die Border schrumpft.");
        if(timeUntilStart == 120) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 2 + ChatColor.WHITE + " Minuten bis die Border schrumpft.");
        if(timeUntilStart == 60) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 60 + ChatColor.WHITE + " Sekunden bis die Border schrumpft.");
        if(timeUntilStart == 30) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 30 + ChatColor.WHITE + " Sekunden bis die Border schrumpft.");

        if(timeUntilStart < 10 && timeUntilStart > 0){
            plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + timeUntilStart + ChatColor.WHITE + " Sekunden bis die Border schrumpft.");
        }

        if(timeUntilStart == 0){
            plugin.announce(ChatColor.RED + "DIE BORDER SCHRUMPFT!");
            plugin.shrink(currentStep.WorldBorderSize, currentStep.TimeToShrink);
            currentStep = null;
        }


    }


    private void handleDisconnectedPlayers(){
        for (DisconnectedPlayer p: plugin.disconnectedPlayers.toArray(new DisconnectedPlayer[0])) {
            p.secondsUntilDeath--;
            if(p.secondsUntilDeath <= 0){
                plugin.disconnectedPlayers.remove(p);
                plugin.announce(ChatColor.RED + p.playerName + ChatColor.WHITE + " ist ausgeschieden.");
                plugin.playerDropOutLogic(null);
            }
        }
    }


    private void handleReveals(){
        if(noRevealLeft) return;

        if(nextReveal == null){
            for (int i = 0; i < plugin.events.size(); i++){
                GameEvent e = plugin.events.get(i);
                if(e.eventType == EventType.Reveal && e.SecondsAfter < plugin.time){
                    nextReveal = e;
                    break;
                }
            }

            if(nextReveal == null){
                noRevealLeft = true;
                return;
            }

        }

        int timeUntilReveal = plugin.time - nextReveal.SecondsAfter;

        if(timeUntilReveal == 1200) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 20 + ChatColor.WHITE + " Minuten bis zum nächsten Reveal.");
        if(timeUntilReveal == 600) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 10 + ChatColor.WHITE + " Minuten bis zum nächsten Reveal.");
        if(timeUntilReveal == 300) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 5 + ChatColor.WHITE + " Minuten bis zum nächsten Reveal.");
        if(timeUntilReveal == 120) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 2 + ChatColor.WHITE + " Minuten bis zum nächsten Reveal.");
        if(timeUntilReveal == 60) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 60 + ChatColor.WHITE + " Sekunden bis zum nächsten Reveal.");
        if(timeUntilReveal == 30) plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 30 + ChatColor.WHITE + " Sekunden bis zum nächsten Reveal.");

        if(timeUntilReveal < 10 && timeUntilReveal > 0){
            plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + timeUntilReveal + ChatColor.WHITE + " Sekunden bis zum nächsten Reveal.");
        }

        if(timeUntilReveal == 0){
            //Reveal
            nextReveal = null;

            for (Player p: plugin.getServer().getOnlinePlayers()) {
                if(p.getGameMode() == GameMode.SPECTATOR) continue;
                plugin.announce(p.getDisplayName() + " : " + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ());
            }
        }
    }


    private void handleDrops(){
        if(noDropLeft) return;

        if(nextDrop == null){
            for (int i = 0; i < plugin.events.size(); i++){
                GameEvent e = plugin.events.get(i);
                if(e.eventType == EventType.Drop && e.SecondsAfter < plugin.time){
                    nextDrop = e;
                    dropLevel++;
                    setRandomDropLocation();
                    break;
                }
            }

            if(nextDrop == null){
                noDropLeft = true;
                return;
            }

        }

        int timeUntilDrop = plugin.time - nextDrop.SecondsAfter;


        if(timeUntilDrop == 600) {
            plugin.announce(ChatColor.WHITE + "Noch " + ChatColor.RED + 10 + ChatColor.WHITE + " Minuten bis zum nächsten Drop.");

            try {
                //Add Laser
                laser = new Laser(laserStartLocation,nextDropLocation,1200,200);
                laser.start(plugin);
            } catch (Exception e){
                plugin.getServer().getLogger().info(e.toString());
            }

        }

        if(timeUntilDrop < 300){
            //Show Distance and Time until Drop.

            for (Player p: plugin.getServer().getOnlinePlayers()) {
                if(p.getGameMode() == GameMode.SPECTATOR) continue;
                double xHelper = Math.pow(p.getLocation().getX() - nextDropLocation.getX(),2);
                double yHelper = Math.pow(p.getLocation().getZ() - nextDropLocation.getZ(),2);
                int distance = (int) Math.round(Math.sqrt(xHelper + yHelper));
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + "Du bist " + ChatColor.RED + distance + ChatColor.WHITE + "m vom Drop entfernt - in " + ChatColor.YELLOW + timeUntilDrop + "s"));
            }
        }

        if(timeUntilDrop == 0){
            // DROP!
            DropHelper.spawnDrop(nextDropLocation, dropLevel);
            nextDrop = null;
        }
    }


    private Location laserStartLocation;

    private void setRandomDropLocation(){
        int size = plugin.steps.get(0).WorldBorderSize;

        int max = size/2;
        int min = -max;

        int X = (int) Math.round(Math.random() * (double) (max - min + 1) + (double) min);
        int Z = (int) Math.round(Math.random() * (double) (max - min + 1) + (double) min);

        nextDropLocation = new Location(plugin.getServer().getWorlds().get(0), X,240,Z);
        plugin.getServer().getWorlds().get(0).loadChunk(nextDropLocation.getChunk()); // Load Chunk
        plugin.getServer().getWorlds().get(0).setChunkForceLoaded(X, Z, true); // Force load drop location chunks to force drop.

        laserStartLocation = new Location(plugin.getServer().getWorlds().get(0), X,20,Z);


    }


}
