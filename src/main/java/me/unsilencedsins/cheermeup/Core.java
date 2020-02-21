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

        if (command.getName().equalsIgnoreCase("cheermeup")) //want a message
            player.sendMessage(ChatColor.GREEN + getMessage());
        else if (command.getName().equalsIgnoreCase("cheermeupversion")) //gets the version
            player.sendMessage(("Current CheerMeUp Version: " + getDescription().getVersion()));
        else if (command.getName().equalsIgnoreCase("cheermeupreload") &&
                player.hasPermission("cheermeup.reload")){
            reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Config Reloaded");
        }

        return true;
    }

    private List<String> getMessagesList() {
        List<String> messages = getConfig().getStringList("messages");
        return messages;
    }

    public String getMessage(){
        List<String> messages = getMessagesList();

        if (messages.size() > 0) return messages.get((int) (Math.random() * messages.size()));

        return "Tell the admins to add some nice things to say!";
    } //sends a message
}
