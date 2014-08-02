package com.github.marenwynn.waypoints.commands;

import org.bukkit.command.CommandSender;

public interface PluginCommand {

    public void execute(CommandSender sender, String[] args);

    public boolean isConsoleExecutable();

    public boolean hasRequiredPerm(CommandSender sender);

}