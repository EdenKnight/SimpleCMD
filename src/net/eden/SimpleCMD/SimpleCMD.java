package net.eden.SimpleCMD;

import com.earth2me.essentials.Console;
import java.io.File;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleCMD extends JavaPlugin {

    public boolean timerStarted = false;
    private Server server = null;
    public FileConfiguration config;
    public FileConfiguration pconfig;
    private FileConfiguration customConfig = null;
    private File customConfigFile = null;
    public static Permission perms = null;
    
    @Override
    public void onDisable() {
        // Disable all running timers.
        this.saveConfig();
        
        
        Bukkit.getServer().getScheduler().cancelTasks(this);

        
    }

    @Override
    public void onEnable() {
        
        //FileConfiguration config;
        
        // Lets find some configs
        //config = getConfig();
        //config.options().copyDefaults(true);
        //saveConfig();
        
        
        //final PluginManager pm = getServer().getPluginManager();
        setupPermissions();
        if(perms==null){
            server.broadcast(Console.NAME,ChatColor.RED+"[SimpleCMD] Fehler: Permissions konnten nicht geladen werden!");
            server.broadcast(Console.NAME,ChatColor.RED+"[SimpleCMD] Konnte nicht geladen werden.");
        }
        else{
            server.broadcast(Console.NAME,ChatColor.BLUE+"[SimpleCMD] Vault wurde geladen!");
            }
        
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        config = getConfig();
        server = getServer();
        
        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this, config), this);
        
        
        getCommand("ie").setExecutor(new CMDExecutor(this));
        getCommand("invfrom").setExecutor(new CMDExecutorInvitors(this));
        //server.getScheduler().scheduleAsyncRepeatingTask(this, null, l, l1);
    }

    public Server getBukkitServer() {
        return server;
    }
    
    public Permission getPermission() {
        return perms;
    }
    
    private long GetActualPlaytimeFromConfig(Player player){return config.getLong("statistics.actualtime."+player.getName());}
    
    public int GetActualPlaytimeMinutes(Player player){
        long time = GetActualPlaytimeFromConfig(player);
        time+=(long)System.currentTimeMillis()/1000-config.getLong("statistics.jointime."+player.getName());
        return (int)(time/60);
    }
    
    public int GetActualPlaytimeSeconds(Player player){
        long time = GetActualPlaytimeFromConfig(player);
        time+=(long)System.currentTimeMillis()/1000-config.getLong("statistics.jointime."+player.getName());
        return (int)(time/60);
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}