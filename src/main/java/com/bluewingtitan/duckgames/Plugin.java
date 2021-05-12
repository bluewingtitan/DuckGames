package com.bluewingtitan.duckgames;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plugin extends JavaPlugin implements Listener {

    public static Plugin plugin;

    public static Random random = new Random();

    FileConfiguration config;
    public List<Step> steps = new ArrayList<Step>();
    public List<GameEvent> events = new ArrayList<GameEvent>();
    public boolean started = false;
    int task;
    public int time;
    ConsoleCommandSender console;

    public List<DisconnectedPlayer> disconnectedPlayers = new ArrayList<DisconnectedPlayer>();


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
                Bukkit.getServer().dispatchCommand(console, "gamerule doInsomnia false");
                Bukkit.getServer().dispatchCommand(console, "gamerule announceAdvancements false");
                Bukkit.getServer().dispatchCommand(console, "gamerule announceAdvancements false");
                shrink(config.getInt("startBorder"),0);
            }
        },120l);

    }




    public void startShrinking() {
        // Set start border
        shrink(config.getInt("startBorder"),0);


        // Remove blindness, add starting invunerability
        Bukkit.getServer().dispatchCommand(console,"effect clear @a");
        Bukkit.getServer().dispatchCommand(console,"effect give @a minecraft:resistance 30 10 true");

        // Spread Players
        spreadPlayers();


        // Set gamemode and remove items
        Bukkit.getServer().dispatchCommand(console,"gamemode survival @a");
        Bukkit.getServer().dispatchCommand(console,"clear @a");


        started = true;
        task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BorderCode(this), 0l, 20l);
    }


    private void spreadPlayers(){
        Bukkit.getServer().dispatchCommand(console,"spreadplayers 0 0 50 150 false @a");
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(started){

            for (DisconnectedPlayer p: disconnectedPlayers.toArray(new DisconnectedPlayer[0])) {
                if(event.getPlayer().getDisplayName().equals(p.playerName)){
                    disconnectedPlayers.remove(p);
                    event.getPlayer().setHealth(2);
                    announce(ChatColor.AQUA + event.getPlayer().getDisplayName() + ChatColor.WHITE + " ist wieder dabei!");
                    return;
                }
            }

            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            event.getPlayer().sendMessage("Du bist nach dem Start des Matches gejoined und bist nun Spectator.");
        } else {
            // Disable dying and moving.
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 500000,255));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 500000,255));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 500000,255));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 500000,255));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500000,255));
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 500000,255));
        }
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        if(!started) return;

        playerDropOutLogic(event.getEntity());

        event.getEntity().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(!started) return;

        disconnectedPlayers.add(new DisconnectedPlayer(event.getPlayer().getDisplayName()));
        announce(ChatColor.AQUA + event.getPlayer().getDisplayName() + ChatColor.WHITE + " hat das Spiel verlassen und gilt in 30 Sekunden als ausgeschieden.");
    }

    public void playerDropOutLogic(Player playerDied) {

        int playersAlive = 0;
        Player lastPlayerCandidate = null;

        for (Player player : Bukkit.getOnlinePlayers())
        {
            //Player the sound for all players
            if(player.getGameMode() == GameMode.SURVIVAL && playerDied != player) {
                playersAlive++;
                lastPlayerCandidate = player; // save pointer in case it might be needed later.
            }
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 10f,1);
        }

        if(playersAlive == 1){
            lastPlayerCandidate.setGameMode(GameMode.CREATIVE);
            // Last man standing!
            for (Player player : Bukkit.getOnlinePlayers())
            {
                player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN , SoundCategory.MASTER, 10f,1);
                player.sendTitle(ChatColor.GOLD + "SIEGER, SIEGER, ENTENKRIEGER!",ChatColor.RED + lastPlayerCandidate.getDisplayName() + ChatColor.WHITE + " IST DIE ULTIMATIVE ENTE!",1,5000,10);
            }
        } else if(playersAlive == 0){
            // Last man standing!
            for (Player player : Bukkit.getOnlinePlayers())
            {
                player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN , SoundCategory.MASTER, 10f,1);
                player.sendTitle(ChatColor.GOLD + "D:",ChatColor.RED + "NIEMAND TRÄGT DIESMAL DEN SIEG DAVON!",1,5000,10);
            }
        } else {
            announce(ChatColor.RED + "" + playersAlive + ChatColor.WHITE + " Spieler am Leben.");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        if(started) return;
        // Lock Non-OP Players in place before game started.
        if(!e.getPlayer().isOp()) e.setCancelled(true);
    }

    public void announce(String message){
        Bukkit.broadcastMessage(message);
    }

    public void shrink(int goal, int seconds){
        for (World w: getServer().getWorlds()) {
            if(w.getName().contains("nether")){
                w.getWorldBorder().setSize(goal/2, seconds);
            } else{
                w.getWorldBorder().setSize(goal, seconds);
            }
        }
    }

}
