/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.eden.SimpleCMD;

import javax.persistence.Entity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import net.milkbowl.vault.permission.Permission;
/**
 *
 * @author Eques
 */
public class CMDExecutorInvitors implements CommandExecutor {
    
    private FileConfiguration config;
    private SimpleCMD plugin;
    private Permission perms;
    
    public CMDExecutorInvitors(SimpleCMD plugin) {
        this.config = plugin.getConfig();
        this.plugin = plugin;
        this.perms = SimpleCMD.perms;
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        if (cs instanceof Player) {
            Player player = (Player)cs;
            if(strings.length==1 && config.getBoolean(cs.getName()+".hasSettet")==false){
                if(plugin.GetActualPlaytimeMinutes(player)>=10){
                    config.set(cs.getName()+".hasSettet",true);
                    config.set(cs.getName()+".hasSettetTo",strings[0]);
                    int gotSubsCount = config.getInt(strings[0]+".gotSubs");
                    config.set(strings[0]+".gotSubs",gotSubsCount+1);
                    if(gotSubsCount+1==2){
                    Player tar_player = plugin.getServer().getPlayer(strings[0]);
                    perms.playerAddGroup(tar_player, "VIP");
                    plugin.saveConfig();
                    } 
                    return true;
                }else{cs.sendMessage("Du musst 10 Minuten Spielzeit haben bevor du jemanden Promoten kannst!");
                }
            }
            if(strings.length==2 && strings[0].equals("remedy")){
                if(perms.has(cs, "simplecmd.can_remedy_promotion")){
                    Player tar_player = plugin.getServer().getPlayer(strings[0]);
                    config.set(tar_player.getName()+".hasSettet",false);
                    String SubTo = config.getString(tar_player.getName()+".hasSettetTo","");
                    int gotSubsCount = config.getInt(SubTo+".gotSubs");
                    if(gotSubsCount<0){
                        config.set(SubTo+".gotSubs",gotSubsCount-1);
                    }
                    config.set(tar_player.getName()+".hasSettetTo","");
                    plugin.saveConfig();
                    return true;
                }else{cs.sendMessage("Du hast kein Recht auf diesen Befehl");
                }
            }

            if(strings[0].equals("player") && strings[1].equals("set") && strings.length>=3){
                if(perms.has(cs, "simplecmd.can_set_player")){
                    int gotSubsCount = config.getInt(strings[2]+".gotSubs");
                    if(strings.length==4){
                        config.set(strings[2]+".gotSubs",Integer.parseInt(strings[3]));
                        cs.sendMessage("Spieler "+strings[2]+" erhaltene Promotionen wurden auf "+strings[3]+" gesetzt.");
                    }else{
                        config.set(strings[2]+".gotSubs",0);
                        cs.sendMessage("Spieler "+strings[2]+" erhaltene Promotionen wurden zurückgesetzt.");
                    }
                    plugin.saveConfig();
                    return true;
                }else{cs.sendMessage("Du hast kein Recht auf diesen Befehl");
                }
            }
            cs.sendMessage(cmnd.getName());
        }
        return true;
    }
    
}
