package com.bluewingtitan.duckgames;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(commandSender.isOp()) {
                //Start!
                Plugin.plugin.startShrinking();
            }
        }
            return true;
    }
}
