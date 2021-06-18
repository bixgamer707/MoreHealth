package bixgamer707.morehealth.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import bixgamer707.morehealth.MoreHealth;

public class MessageCheckUpdater implements Listener{
private MoreHealth plugin;
	
	public MessageCheckUpdater(MoreHealth plugin){
		this.plugin = plugin;
	}
	@EventHandler
	public void joinChecker(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if(player.isOp() && !(plugin.getVersion().equals(plugin.latestVersion))){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l---------------"+plugin.nombre+"&c&l---------------"));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThere is a version available of"));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your version is &e" + plugin.latestVersion));
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7and the new one is "+plugin.version));
  		  	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou can download it at:"));
  		  	player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bhttps://www.spigotmc.org/resources/morehealth.93100/"));
  		    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l---------------"+plugin.nombre+"&c&l---------------"));
		}
	}

}
