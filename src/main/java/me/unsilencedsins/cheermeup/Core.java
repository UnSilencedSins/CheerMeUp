package me.unsilencedsins.cheermeup;

import me.unsilencedsins.api.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Core extends JavaPlugin implements CommandExecutor, Listener {
    private boolean onJoin;
    private boolean showTitle;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        onJoin = getConfig().getBoolean("onJoin", true);
        showTitle = getConfig().getBoolean("showAsTitle", false);

        Bukkit.getPluginManager().registerEvents(this, this);
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

        if (command.getName().equalsIgnoreCase("cheermeup")){ //want a message
            if(!showTitle) {
                player.sendMessage(getMessage());
                return true;
            }

            Titles.sendTitle(player, 20, 60, 20, getMessage(), " ");
        }
        else if (command.getName().equalsIgnoreCase("cheermeupversion")) //gets the version
            player.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "Current CheerMeUp Version: " +
                    getDescription().getVersion()));

        return true;
    }

    private List<String> getMessagesList() {
        List<String> messages = getConfig().getStringList("messages");
        return messages;
    }

    public String getMessage(){
        List<String> messages = getMessagesList();

        if (messages.size() > 0)
        {
            return ChatColor.GREEN + messages.get((int) (Math.random() * messages.size()));
        }

        return "Tell the admins to add some nice things to say!";
    } //sends a message
}
