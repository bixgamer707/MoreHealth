package bixgamer707.morehealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bixgamer707.morehealth.ConfigFile;
import bixgamer707.morehealth.MessagesFile;
import bixgamer707.morehealth.MoreHealth;
import bixgamer707.morehealth.events.InventoryHealth;

public class MainCommand implements CommandExecutor{	
private MoreHealth plugin;
	
	public MainCommand(MoreHealth plugin){
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+"You cannot run commands from the console");
			return false;
		}else{
			Player player = (Player) sender;
			ConfigFile config = new ConfigFile();
			MessagesFile messages = new MessagesFile();
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("version")){			
					player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&7The plugin version is &e"+plugin.version));
					return true;				
						}else if(args[0].equalsIgnoreCase("hearts")){
							if(player.hasPermission("morehealth.inventoryhearts") || player.isOp()){
								InventoryHealth inv = new InventoryHealth(plugin);
								inv.newInventory(player);		
								return true;
							}else{
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));	
								}
						}else if(args[0].equalsIgnoreCase("editxp")) {
							if(player.hasPermission("morehealth.admin.editxp") || player.isOp()){
								plugin.add(player.getUniqueId());
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("enter-edit-xp")));
							}else {
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
								return true;
							}
						}else if(args[0].equalsIgnoreCase("editmoney")) {
							if(player.hasPermission("morehealth.admin.editmoney") || player.isOp()){
								plugin.add(player.getUniqueId());
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("enter-edit-money")));
							}else {
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
								return true;
							}
						}else if(args[0].equalsIgnoreCase("reload")){
							if(player.hasPermission("morehealth.admin.reload") || player.isOp()){	
								config.reloadFile();
								messages.reloadFile();
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("reload-message")));
							}else{
								player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&c "+messages.getString("no-permission")));
							}				
					}else{
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1+-------------"+plugin.nombre+"&1----------------+"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh version &7to see the plugin version"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh reload &7to load the config"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh hearts &7Open the hearts menu"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/hearts &7Open the hearts menu"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1+-------------"+plugin.nombre+"&1----------------+"));
						return true;			
					}
				}else{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1+-------------"+plugin.nombre+"&1----------------+"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh version &7to see the plugin version"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh reload &7to load the config"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/mh hearts &7Open the hearts menu"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/hearts &7Open the hearts menu"));
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&1+-------------"+plugin.nombre+"&1----------------+"));
					return true;	
				}			
				return true;			
		}
	}
}
