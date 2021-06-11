package bixgamer707.morehealth.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import bixgamer707.morehealth.MoreHealth;

public class InventoryConfirm implements Listener{
public MoreHealth plugin;
	
	public InventoryConfirm(MoreHealth plugin){
		this.plugin = plugin;
	}
	
	public void newInventory(Player player){
		FileConfiguration config = plugin.getConfig();	
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.confirm.title")));
		ItemStack  item = new ItemStack(Material.matchMaterial(config.getString("Inventory.confirm.item-confirm")));
		ItemMeta meta =  item.getItemMeta();
		List<String> lore = new ArrayList<String>();		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.confirm.item-confirm-name")));
		lore = new ArrayList<String>();
		lore = config.getStringList("Inventory.confirm.item-confirm-lore");
    	for(int i=0;i<lore.size();i++)
    		lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName())));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(21, item);			
			
		ItemStack  item2 = new ItemStack(Material.matchMaterial(config.getString("Inventory.confirm.item-declive")));		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.confirm.item-declive-name")));
		lore = new ArrayList<String>();
		lore = config.getStringList("Inventory.confirm.item-declive-lore");
	    for(int i=0;i<lore.size();i++)
	    	lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName())));
			meta.setLore(lore);
			item2.setItemMeta(meta);
			inv.setItem(23, item2);	
			
		ItemStack decor = new ItemStack(Material.matchMaterial(config.getString("Inventory.confirm.decor")));
		for(int i=0;i<9;i++){
			inv.setItem(i, decor);		
		}
		for(int i=36;i<40;i++){
			inv.setItem(i, decor);
	    }
		for(int i=41;i<45;i++){
			inv.setItem(i, decor);
	    }
		inv.setItem(9, decor);
		inv.setItem(18, decor);
		inv.setItem(27, decor);
		inv.setItem(26, decor);
		inv.setItem(36, decor);
		inv.setItem(17, decor);
		inv.setItem(35, decor);
		inv.setItem(40, decor);
	    
	    	
		player.openInventory(inv);
		return;
		}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void clickInventory(InventoryClickEvent event){	
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		String nombre = ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.confirm.title"));
		String nombreM = ChatColor.stripColor(nombre);
		if(ChatColor.stripColor(event.getView().getTitle()).equals(nombreM)){ 
			if(event.getCurrentItem() == null || event.getSlotType() == null || event.getCurrentItem().getType() == Material.AIR){				
				event.setCancelled(true);
				return;
			}else{
				if(event.getCurrentItem().hasItemMeta()){
				    Player player = (Player) event.getWhoClicked();					
					event.setCancelled(true);
					if(event.getSlot() == 21){							
						if(player.hasPermission("morehealth.maxhealth") || player.isOp()){
							int xp,hearts;
							xp = player.getLevel();
							hearts = (int) player.getMaxHealth();
							player.setLevel(xp-config.getInt("Inventory.heart.requiriment-experience-1hearth"));
							player.setMaxHealth(hearts+config.getDouble("Inventory.confirm.plusheart"));
							player.closeInventory();
							player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("claim-heart")));
							for(Player players : Bukkit.getOnlinePlayers()){	
								player.playSound(players.getLocation(), Sound.valueOf(config.getString("Inventory.confirm.sound-claim")), 10, 0.5F);
								players.sendTitle(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.confirm.title").replaceAll("%player%", player.getName())), ChatColor.translateAlternateColorCodes('&',config.getString("Inventory.confirm.subtitle").replaceAll("%player%", player.getName())));	
							}
					 }else{
						 player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
						}        	    
					}else if(event.getSlot() == 23) {
						player.closeInventory();
						player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("option-declive")));
				}else{
					event.setCancelled(true);
		  		    return;
				}
			}
		}		
	}   		
}
}