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
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (onJoin) e.getPlayer().sendMessage(getMessage());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("cheermeup")) { //want a message

            if (!showTitle) { //Message is to be sent to chat not to title.
                player.sendMessage(getMessage());
                return true;
            }//end if

            Titles.sendTitle(player, 20, 60, 20, getMessage(), " ");
        } else if (command.getName().equalsIgnoreCase("cheermeupversion")){ //gets the version

            player.sendMessage((ChatColor.GREEN + "" + ChatColor.BOLD + "Current CheerMeUp Version: " + getDescription().getVersion()));
        } else if (command.getName().equalsIgnoreCase("cheermeupconfig")) {//works with the config

            if (!player.hasPermission("cheermeup.config")) { //they didnt have the right permission

                player.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
                return true;
            } else { //they did have the right permission

                if (args.length > 0) { //doing a child command

                    if (args[0].equalsIgnoreCase("reload")) { //reload the config

                        reloadConfig();
                        player.sendMessage(ChatColor.GREEN + "Reloaded config");
                    }
                    else if (args[0].equalsIgnoreCase("edit")) { //wants to edit the config

                        if (args.length > 1) { //doing a child command

                            if (args[1].equalsIgnoreCase("onjoin")) { //wants to change the onJoin option

                                if (args.length > 2) { //doing a child command

                                    if (args[2].equalsIgnoreCase("true")) { //setting onJoin to true

                                        getConfig().set("onJoin", true);
                                        saveConfig();
                                        onJoin = true;
                                        player.sendMessage(ChatColor.GREEN + "Set onJoin to true");
                                    } else if (args[2].equalsIgnoreCase("false")) { //setting onJoin to false

                                        getConfig().set("onJoin", false);
                                        saveConfig();
                                        onJoin = false;
                                        player.sendMessage(ChatColor.GREEN + "Set onJoin to false");
                                    } else { //incorrect value

                                        player.sendMessage(ChatColor.RED + "Usage: /cheermeupconfig edit onJoin [value]");
                                        player.sendMessage(ChatColor.RED + "Valid values:");
                                        player.sendMessage(ChatColor.RED + "True");
                                        player.sendMessage(ChatColor.RED + "False");
                                    }//end if
                                } else { //didnt put a value

                                    player.sendMessage(ChatColor.RED + "Usage: /cheermeupconfig edit onJoin [value]");
                                    player.sendMessage(ChatColor.RED + "Valid values:");
                                    player.sendMessage(ChatColor.RED + "True");
                                    player.sendMessage(ChatColor.RED + "False");
                                }//end if
                            } else if (args[1].equalsIgnoreCase("showastitle")) { //wants to set showAsTitle option

                                if (args[2].equalsIgnoreCase("true")) { //setting the showAsTitle option to true

                                    getConfig().set("showAsTitle", true);
                                    saveConfig();
                                    showTitle = true;
                                    player.sendMessage(ChatColor.GREEN + "Set showAsTitle to true");
                                }
                                else if (args[2].equalsIgnoreCase("false")) { //setting showAsTitle option to false

                                    getConfig().set("showAsTitle", false);
                                    saveConfig();
                                    showTitle = false;
                                    player.sendMessage(ChatColor.GREEN + "Set showAsTitle to false");
                                }
                                else { //incorrect value entered

                                    player.sendMessage(ChatColor.RED + "Usage: /cheermeupconfig edit showAsTitle [value]");
                                    player.sendMessage(ChatColor.RED + "Valid values:");
                                    player.sendMessage(ChatColor.RED + "True");
                                    player.sendMessage(ChatColor.RED + "False");
                                }
                            } else { //the option they entered does not exist

                                player.sendMessage(ChatColor.RED + "That configuration option does not exist.");
                            }
                        } else { //didnt enter a configuration option

                            player.sendMessage(ChatColor.RED + "Usage: /cheermeupconfig edit [configuration option] [option value]");
                            player.sendMessage(ChatColor.RED + "configuration options:");
                            player.sendMessage(ChatColor.RED + "onJoin - True or False");
                            player.sendMessage(ChatColor.RED + "showAsTitle - True or False");
                        }//end if
                    }//end if
                } else { //incorrect usage of the command

                    player.sendMessage(ChatColor.RED + "Usage: /cheermeupconfig [child command]");
                    player.sendMessage(ChatColor.RED + "Child Commands:");
                    player.sendMessage(ChatColor.RED + "edit");
                    player.sendMessage(ChatColor.RED + "reload");
                }//end if
            }//end if
        } //end if

        return true;
    } //onCommand method

    private List<String> getMessagesList() {
        List<String> messages = getConfig().getStringList("messages");
        return messages;
    }

    public String getMessage() {
        List<String> messages = getMessagesList();

        if (messages.size() > 0) {
            return ChatColor.GREEN + messages.get((int) (Math.random() * messages.size()));
        }

        return "Tell the admins to add some nice things to say!";
    } //sends a message
}
