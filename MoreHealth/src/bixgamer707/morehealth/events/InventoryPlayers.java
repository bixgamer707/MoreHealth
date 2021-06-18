package bixgamer707.morehealth.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import bixgamer707.morehealth.ConfigFile;
import bixgamer707.morehealth.MoreHealth;
import me.clip.placeholderapi.PlaceholderAPI;

public class InventoryPlayers implements Listener{
public MoreHealth plugin;
	
	public InventoryPlayers(MoreHealth plugin){
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	public void newInventory(Player player) {
		ConfigFile config = new ConfigFile();
		int position = 0;
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.players.title")));	
		for(Player players : Bukkit.getOnlinePlayers()) {
			ItemStack heads = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) heads.getItemMeta();
			meta.setOwner(players.getName());
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.players.heads-name").replaceAll("%players%", players.getName())));
			List<String> lore = new ArrayList<String>();
			lore = config.getStringList("Inventory.players.heads-lore");
	    	for(int i=0;i<lore.size();i++) {
	    		String placeholders = PlaceholderAPI.setPlaceholders(players, lore.get(i));
	    		lore.set(i, placeholders);
	    	}
	    	meta.setLore(lore);
	    	heads.setItemMeta(meta);
	    	inv.setItem(position, heads);
	    	position++;
		}
		player.openInventory(inv);
	}
	@EventHandler
	public void clickInventory(InventoryClickEvent event) {
		ConfigFile config = new ConfigFile();
		String nameInventory = ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.players.title"));
		String nameM = ChatColor.stripColor(nameInventory);
		if(ChatColor.stripColor(event.getView().getTitle()).equals(nameM)){ 
			if(event.getCurrentItem() == null){
				event.setCancelled(true);
				return;
			}
			if((event.getSlotType() == null)){
				event.setCancelled(true);
				return;
			}else{
				event.setCancelled(true);
			}
		}
	}

}
