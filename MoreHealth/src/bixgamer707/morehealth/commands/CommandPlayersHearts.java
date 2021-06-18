package bixgamer707.morehealth.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import bixgamer707.morehealth.MessagesFile;
import bixgamer707.morehealth.MoreHealth;
import bixgamer707.morehealth.events.InventoryPlayers;

public class CommandPlayersHearts implements CommandExecutor{
	
	private MoreHealth plugin;
	
	public CommandPlayersHearts(MoreHealth plugin){
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if(!(sender instanceof Player)){
			Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.RED+"You cannot run commands from the console");
			return false;
		}else{
			Player player = (Player) sender;
			MessagesFile messages = new MessagesFile();	
			if(player.hasPermission("morehealth.playershearts") || player.isOp()){
				InventoryPlayers inv = new InventoryPlayers(plugin);
				inv.newInventory(player);
				return true;
			}else{
				player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));	
				}
			}
		return true;
	}
}