package me.unsilencedsins.cheermeup;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Core extends JavaPlugin implements CommandExecutor {
    private boolean onJoin = false;
    private List<String> messages;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (onJoin) e.getPlayer().sendMessage(getMessage());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("cheermeup")) player.sendMessage(getMessage());//want a message
        else if (command.getName().equalsIgnoreCase("cmureload") &&
                player.hasPermission("cheermeup.cmureload")) {//want to reload the file
            messages = getMessagesList();
            player.sendMessage(ChatColor.GREEN + "CMU Config reloaded");
        }
        else if (command.getName().equalsIgnoreCase("cheermeupversion"))
            player.sendMessage(("Current CheerMeUp Version: " + getDescription().getVersion()));

        return true;
    }

    private List<String> getMessagesList() {return getConfig().getStringList("messages");}

    public String getMessage(){
        messages = getMessagesList();

        if (messages.size() > 0) return messages.get((int) (Math.random() * messages.size()));

        return "Tell the admins to add some nice things to say!";
    } //sends a message
}
