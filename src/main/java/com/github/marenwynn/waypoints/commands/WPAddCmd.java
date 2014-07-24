package com.github.marenwynn.waypoints.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.marenwynn.waypoints.SelectionManager;
import com.github.marenwynn.waypoints.Util;
import com.github.marenwynn.waypoints.WaypointManager;
import com.github.marenwynn.waypoints.data.DataManager;
import com.github.marenwynn.waypoints.data.Msg;
import com.github.marenwynn.waypoints.data.Waypoint;

public class WPAddCmd implements PluginCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        DataManager dm = DataManager.getManager();
        WaypointManager wm = WaypointManager.getManager();
        Player p = (Player) sender;

        if (args.length == 0) {
            Msg.USAGE_WP_ADD.sendTo(p);
            return;
        }

        String waypointName = Util.color(Util.buildString(args, 0, ' '));

        if (ChatColor.stripColor(waypointName).length() > dm.WP_NAME_MAX_LENGTH) {
            Msg.MAX_LENGTH_EXCEEDED.sendTo(p, dm.WP_NAME_MAX_LENGTH);
            return;
        }

        if (wm.getWaypoint(waypointName) != null || waypointName.equals("Bed") || waypointName.equals("Spawn")) {
            Msg.WP_DUPLICATE_NAME.sendTo(p, waypointName);
            return;
        }

        Location playerLoc = p.getLocation();

        for (Waypoint wp : wm.getWaypoints().values()) {
            if (Util.isSameLoc(playerLoc, wp.getLocation(), true)) {
                Msg.WP_ALREADY_HERE.sendTo(p, wp.getName());
                return;
            }
        }

        Waypoint wp = new Waypoint(waypointName, playerLoc);

        wm.addWaypoint(wp);
        dm.saveWaypoints();
        SelectionManager.getManager().setSelectedWaypoint(sender, wp);
    }

    @Override
    public boolean isConsoleExecutable() {
        return false;
    }

    @Override
    public boolean hasRequiredPerm(CommandSender sender) {
        return sender.hasPermission("wp.add");
    }

}
