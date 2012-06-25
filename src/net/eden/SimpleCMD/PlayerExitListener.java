/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eden.SimpleCMD;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Eques
 */
public class PlayerExitListener {
    Plugin plugin;
    FileConfiguration config;
    
    public PlayerExitListener(final Plugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event){
        event.getPlayer().getName();
    }
}
