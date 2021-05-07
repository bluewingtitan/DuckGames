package com.bluewingtitan.duckgames;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Plugin extends JavaPlugin implements Listener {


    public static Plugin plugin;

    FileConfiguration config;
    public List<Step> steps = new ArrayList<Step>();
    public List<GameEvent> events = new ArrayList<GameEvent>();
    public boolean started = false;
    int task;
    public int time;
    ConsoleCommandSender console;

    @Override
    public void onEnable() {
        plugin = this;
        console = Bukkit.getServer().getConsoleSender();

        getLogger().info("DuckGames was enabled!");
        File configFile = new File(this.getDataFolder(), "config.yml");
        if(!configFile.exists()){
            saveDefaultConfig();
        }
        config = getConfig();

        // Get Steps
        List<String> phasesRaw = config.getStringList("phases");

        for (String s: phasesRaw) {
            String[] parts = s.split(":");
            steps.add(new Step(Integer.parseInt(parts[0])*60,Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
        }


        // Get Events
        List<String> eventsRaw = config.getStringList("events");

        for (String s: eventsRaw) {
            String[] parts = s.split(":");
            GameEvent event = null;
            int time = Integer.parseInt(parts[0]) * 60;

            switch (parts[1].trim().toLowerCase()){
                case "reveal":
                    event = new GameEvent(EventType.Reveal, time);
                    break;
                case "glow":
                    event = new GameEvent(EventType.Glow, time);
                    break;
                case "drop":
                    event = new GameEvent(EventType.Drop, time);
                    break;
            }

            if(event != null){
                events.add(event);
            }
        }

        time = config.getInt("time")*60;


        this.getCommand("duckstart").setExecutor(new StartCommand());

        this.getCommand("duckbug").setExecutor(new DebugCommand());

        getServer().getPluginManager().registerEvents(this, this);


        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(console, "gamerule logAdminCommands false");
                Bukkit.getServer().dispatchCommand(console, "gamerule commandBlockOutput false");
                Bukkit.getServer().dispatchCommand(console, "gamerule reducedDebugInfo true");
                Bukkit.getServer().dispatchCommand(console, "gamerule doInsomnia false");
                Bukkit.getServer().dispatchCommand(console, "gamerule announceAdvancements false");
                Bukkit.getServer().dispatchCommand(console, "gamerule announceAdvancements false");
                shrink(config.getInt("startBorder"),0);
            }
        },120l);

    }




    public void startShrinking() {
        // Spread Players
        spreadPlayers();

        // Remove blindness
        Bukkit.getServer().dispatchCommand(console,"effect clear @a");


        started = true;
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BorderCode(this), 0l, 20l);
    }



    private void spreadPlayers(){
        Bukkit.getServer().dispatchCommand(console,"spreadplayers 0 0 100 200 false @a");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500000,255));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500000,128));
    }



    public void announce(String message){
        Bukkit.broadcastMessage(message);
    }

    public void shrink(int goal, int seconds){
        Bukkit.getServer().dispatchCommand(console,"worldborder set " + goal + " " + seconds);
    }

}
