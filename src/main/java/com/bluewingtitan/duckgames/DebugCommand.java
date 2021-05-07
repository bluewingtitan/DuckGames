package com.bluewingtitan.duckgames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Plugin.plugin.announce(strings[0] + " " + strings[1]);
        if(commandSender instanceof Player){
            if(commandSender.isOp()) {
                if(strings.length <= 1) return false;
                if(strings[0].contains("drop")){
                    DropHelper.spawnDrop(((Player) commandSender).getLocation(), Integer.parseInt(strings[1].trim()));
                    return true;
                }
            }
        }

        return false;
    }
}
