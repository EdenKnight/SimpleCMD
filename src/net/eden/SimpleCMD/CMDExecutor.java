/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eden.SimpleCMD;

import javax.activation.CommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.World;

/**
 *
 * @author Eques
 */
public class CMDExecutor implements CommandExecutor {
    SimpleCMD plugin;
    String cmndname="ie";
    
    public CMDExecutor(SimpleCMD plugin) {
        this.plugin=plugin;
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if(string.equalsIgnoreCase(cmndname)){
            Player player = plugin.getServer().getPlayer(cs.getName());
            
            if(strings.length==0){
                cs.sendMessage(cs.getName()+" hat Edens Plugin benutzt : P");
                }
            if(strings.length==1){
                if(strings[0].equals("server")){CommandServer(cs);}
                if(strings[0].equals("eden")){CommandEden(cs);}
                if(strings[0].equals("help")){CommandHelp(cs);}
                if(strings[0].equals("info")){CommandInfo(cs);}
                }
            if(strings.length==2){
                boolean perm;
                try {
                    perm = player.hasPermission("eden.set");
                    } catch (NoSuchMethodError e) {
                    perm = false;
                }
                CommandSet(player, strings, perm, cs);
            }
            
            return true;
        }
        return false;
    }

    private void CommandSet(Player player, String[] strings, boolean perm, CommandSender cs) throws NumberFormatException {
        if (player instanceof Player) {
        if(strings[0].equals("set") && (!perm && !player.isOp())){cs.sendMessage(ChatColor.RED+"Sie haben keine Befugnis auf diesen Befehl!");                }
        if(strings[0].equals("set") && (perm || player.isOp())){
                World world = plugin.getServer().getPlayer(cs.getName()).getWorld();

                String[] block = strings[2].split(":");
                int[] loc = new int[]{player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ()};
                world.getBlockAt(loc[0],loc[1],loc[2]).setTypeIdAndData(Integer.parseInt(block[0]), Byte.parseByte(block[1]),false);
                cs.sendMessage(ChatColor.BLUE+"Block gesetzt! X"+loc[0]+" Y"+loc[1]+" Z"+loc[2]);
                }
            }else{cs.sendMessage(ChatColor.RED+"Sie sind kein Spieler! Oo");}
    }

    private void CommandServer(CommandSender cs) {
        String[] s = (ChatColor.BLUE+"Servername: "+plugin.getServer().getServerName()+"\nMaximale Spieler: "+plugin.getServer().getMaxPlayers()+"\nBukkitversion: "+plugin.getServer().getBukkitVersion()).split("\n");
            for (String m : s){
                cs.sendMessage(m);
            }
    }

    private void CommandEden(CommandSender cs) {
        cs.sendMessage(ChatColor.BLUE+"Ja das bin ich ! MrKnightcraft on YT !");
    }

    private void CommandHelp(CommandSender cs) {
        String[] s = (ChatColor.BLUE+"Befehle:\n/"+cmndname+" | gibt eine Nachricht aus\n/"+cmndname+" server | gibt Serverinformationen\n/"+cmndname+" set [Block] | setzt einen Block an die momentane Position des Spielers (OP oder eden.set Permission benötigt)\n/"+cmndname+" eden | gibt eine andere Nachricht aus (Achtung Schleichwerbung)").split("\n");
            for (String m : s){
                cs.sendMessage(ChatColor.YELLOW+m);
            }
    }

    private void CommandInfo(CommandSender cs) {
        cs.sendMessage(ChatColor.YELLOW+"Du hast bereits "+plugin.GetActualPlaytimeMinutes(plugin.getServer().getPlayer(cs.getName()))+" Minuten auf dem Server verbacht! ("+plugin.GetActualPlaytimeSeconds(plugin.getServer().getPlayer(cs.getName()))+")");
    }
    
    
}
