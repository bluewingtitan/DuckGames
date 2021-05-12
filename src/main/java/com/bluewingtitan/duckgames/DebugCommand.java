package com.bluewingtitan.duckgames;

import com.bluewingtitan.duckgames.drops.DropHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(Plugin.plugin.started) return true; // Don't allow drops during match!

        if(commandSender instanceof Player){
            if(commandSender.isOp()) {
                if(strings.length <= 1) return false;
                if(strings[0].contains("drop")){
                    DropHelper.spawnDrop(((Player) commandSender).getLocation(), Integer.parseInt(strings[1].trim()));
                    return true;
                }

                if(strings[0].contains("beam")){

                    int X = ((Player) commandSender).getLocation().getBlockX();
                    int Z = ((Player) commandSender).getLocation().getBlockZ();

                    Location nextDropLocation = new Location(Plugin.plugin.getServer().getWorlds().get(0), X,240,Z);

                    Location laserStartLocation = new Location(Plugin.plugin.getServer().getWorlds().get(0), X,20,Z);

                    try {
                        //Add Laser
                        Laser l = new Laser(laserStartLocation,nextDropLocation,1200,200);
                        l.start(Plugin.plugin);
                    } catch (Exception e){

                    }
                    return true;
                }


                if(strings[0].contains("ff")){
                    int time = Integer.parseInt(strings[1].trim());
                    Plugin.plugin.time += time*60;
                }


                if(strings[0].contains("t")){
                    commandSender.sendMessage(Plugin.plugin.time/60f + " minutes");
                }

                if(strings[0].contains("shrink")){
                    int size = Integer.parseInt(strings[1].trim());
                    int time = Integer.parseInt(strings[2].trim());
                    Plugin.plugin.shrink(size, time);
                }


            }
        }

        return false;
    }
}
