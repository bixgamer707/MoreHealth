package bixgamer707.morehealth.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import bixgamer707.morehealth.ConfigFile;
import bixgamer707.morehealth.MessagesFile;
import bixgamer707.morehealth.MoreHealth;

public class PlayerChatListener implements Listener{
private MoreHealth plugin;
		public PlayerChatListener(MoreHealth plugin) {
			this.plugin = plugin;
		}
		@EventHandler
		public void onChat(AsyncPlayerChatEvent event) {
			String msg = event.getMessage();
			Player player = event.getPlayer();
			ConfigFile config = new ConfigFile();
			MessagesFile messages = new MessagesFile();
			if(plugin.contains(player.getUniqueId())) {
				event.setCancelled(true);
				try {
					int xp = Integer.valueOf(msg);
					player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("successfully-enter-xp").replace("%enter_xp%", String.valueOf(xp))));
					plugin.remove(player.getUniqueId());
					config.set("Inventory.heart.requiriment-experience", xp);
					config.saveFile();
	 
				}catch (NumberFormatException ex) {
					player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("enter-number")));				
			}
		}
			if(plugin.containss(player.getUniqueId())) {
				event.setCancelled(true);
				try {
					int money = Integer.valueOf(msg);
					player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("successfully-enter-money").replace("%enter_money%", String.valueOf(money))));
					plugin.remove(player.getUniqueId());
					config.set("Inventory.heart.requiriment-money", money);
					config.saveFile();
	 
				}catch (NumberFormatException ex) {
					player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("enter-number")));				
			}
		}	
	}
}
