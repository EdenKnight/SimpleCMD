/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eden.SimpleCMD;

import java.security.Timestamp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author Eques
 */
public class PlayerListener implements Listener{
    
    Plugin plugin;
    FileConfiguration config;
    
    public PlayerListener(final Plugin plugin,FileConfiguration config) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event){
        plugin.getServer().broadcastMessage(event.getPlayer().getName()+" ist gejoint!");
        CreateTimestamp(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event){
        plugin.getServer().broadcastMessage(event.getPlayer().getName()+" ist gegangen : (");
        RemoveTimestamp(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKick(final PlayerKickEvent event){
        plugin.getServer().broadcastMessage(event.getPlayer().getName()+" ist gekickt worden!");
        RemoveTimestamp(event.getPlayer());
    }
    
    public long GetActualPlaytime(Player player){return config.getLong("statistics.actualtime."+player.getName());}
    public void SetActualPlaytime(Player player,long time){config.set("statistics.actualtime."+player.getName(),(long)time);plugin.saveConfig();}
    public void RemoveTimestamp(Player player){
        long time = GetActualPlaytime(player);
        time+=(long)System.currentTimeMillis()/1000-config.getLong("statistics.jointime."+player.getName());
        SetActualPlaytime(player,time);
        config.set("statistics.jointime."+player.getName(), (long) -1);
        plugin.saveConfig();
    }
    public void CreateTimestamp(Player player){
        config.set("statistics.jointime."+player.getName(), (long) System.currentTimeMillis()/1000);
        plugin.saveConfig();
    }
}
