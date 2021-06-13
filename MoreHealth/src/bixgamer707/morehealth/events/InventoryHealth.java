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
import org.bukkit.inventory.meta.ItemMeta;

import bixgamer707.morehealth.ConfigFile;
import bixgamer707.morehealth.MessagesFile;
import bixgamer707.morehealth.MoreHealth;
import net.milkbowl.vault.economy.Economy;

public class InventoryHealth implements Listener{
public MoreHealth plugin;
	public InventoryHealth(MoreHealth plugin){
		this.plugin = plugin;
	}
	
	public void newInventory(Player player){
		ConfigFile config = new ConfigFile();	
		Inventory inv = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.title")));
		ItemStack  item = new ItemStack(Material.matchMaterial(config.getString("Inventory.heart.item-health")));
		ItemMeta meta =  item.getItemMeta();
		List<String> lore = new ArrayList<String>();		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.item-health-name")));
		lore = new ArrayList<String>();
		lore = config.getStringList("Inventory.heart.item-health-lore");
    	for(int i=0;i<lore.size();i++)
    		lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName())));
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(22, item);			
		
		ItemStack e = new ItemStack(Material.matchMaterial(config.getString("Inventory.heart.exit")));
		meta = e.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.exit-name")));
		lore = new ArrayList<String>();
		lore = config.getStringList("Inventory.heart.exit-lore");
    	for(int i=0;i<lore.size();i++)
    		lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName())));
			meta.setLore(lore);
			e.setItemMeta(meta);
			inv.setItem(40, e);
			
		if(config.getString("Inventory.heart.change-requiriment").equals("XP")){
			int xp = player.getLevel();
			ItemStack renove = new ItemStack(Material.matchMaterial(config.getString("Inventory.heart.change-requiriment-xp")));
			meta =  renove.getItemMeta();		
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.change-requiriment-xp-name")));
			lore = new ArrayList<String>();
			lore = config.getStringList("Inventory.heart.change-requiriment-xp-lore");
		    for(int i=0;i<lore.size();i++)
		    	lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName()).replaceAll("%XP_player%", ""+xp)));
				meta.setLore(lore);
				renove.setItemMeta(meta);
				inv.setItem(26, renove);	
		}else{
			if(config.getString("Inventory.heart.change-requiriment").equals("MONEY")) {
				if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null){
					ItemStack renove = new ItemStack(Material.matchMaterial(config.getString("Inventory.heart.change-requiriment-money")));
					meta =  renove.getItemMeta();		
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.change-requiriment-money-name")));
					lore = new ArrayList<String>();
					lore = config.getStringList("Inventory.heart.change-requiriment-money-lore");
				    for(int i=0;i<lore.size();i++)
				    	lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i).replaceAll("%player%", player.getName())));
						meta.setLore(lore);
						renove.setItemMeta(meta);
						inv.setItem(26, renove);
				}else {
					  Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&cThe &eVault &cplugin is not installed and some inventory functions do not work"));
					  for(Player players : Bukkit.getOnlinePlayers()) {
						  if(players.hasPermission("morehealth.admin.warnings") || players.isOp()){
							  players.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&cThe &eVault &cplugin is not installed and some inventory functions do not work"));
							  
						  }
					  }
				}
				
			}
		}
			
		ItemStack decoracion = new ItemStack(Material.matchMaterial(config.getString("Inventory.heart.decor")));
		for(int i=0;i<9;i++){
			inv.setItem(i, decoracion);		
		}
		for(int i=36;i<40;i++){
			inv.setItem(i, decoracion);
	    }
		for(int i=41;i<45;i++){
			inv.setItem(i, decoracion);
	    }
		inv.setItem(9, decoracion);
		inv.setItem(18, decoracion);
		inv.setItem(27, decoracion);
		inv.setItem(36, decoracion);
		inv.setItem(17, decoracion);
		inv.setItem(35, decoracion);
	    
	    	
		player.openInventory(inv);
		return;
		}						
	@EventHandler
	public void clickInventory(InventoryClickEvent event){	
		ConfigFile config = new ConfigFile();
		MessagesFile messages = new MessagesFile();
		String nombre = ChatColor.translateAlternateColorCodes('&', config.getString("Inventory.heart.title"));
		String nombreM = ChatColor.stripColor(nombre);
		if(ChatColor.stripColor(event.getView().getTitle()).equals(nombreM)){ 
			if(event.getCurrentItem() == null || event.getSlotType() == null || event.getCurrentItem().getType() == Material.AIR){				
				event.setCancelled(true);
				return;
			}else{
				if(event.getCurrentItem().hasItemMeta()){
				    Player player = (Player) event.getWhoClicked();					
					event.setCancelled(true);
					if(event.getSlot() == 22){							
						if(player.hasPermission("morehealth.maxhealth") || player.isOp()){
							if(config.getString("Inventory.heart.change-requiriment").equals("XP")){
								int xp = player.getLevel();
								if(xp >= config.getInt("Inventory.heart.requiriment-experience")) {
									InventoryConfirm inv = new InventoryConfirm(plugin);							
									inv.newInventory(player);		
								}else{
									player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("insuficient-experience")));
								}
							}else {
								if(config.getString("Inventory.heart.change-requiriment").equals("MONEY")) {
									Economy econ = plugin.getEconomy();
									double money = econ.getBalance(player);
									int required = config.getInt("Inventory.heart.requiriment-money");
									if(money >= required){
										InventoryConfirm inv = new InventoryConfirm(plugin);							
										inv.newInventory(player);
										
									}else {
										player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("insuficient-money")));
									}
							}
						}		
						 }else{
							 player.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', messages.getString("no-permission")));
							}        	    
				}else if(event.getSlot() == 40)	{        	 
					player.closeInventory();	
				}else if(event.getSlot() == 26)	{ 
					if(config.getString("Inventory.heart.change-requiriment").equals("XP")){
						config.set("Inventory.heart.change-requiriment", "MONEY");
						config.saveFile();
						InventoryHealth inv = new InventoryHealth(plugin);									
						player.closeInventory();
						inv.newInventory(player);
					}else {
						if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null){
							if(config.getString("Inventory.heart.change-requiriment").equals("MONEY")) {
								config.set("Inventory.heart.change-requiriment", "XP");
								config.saveFile();												
								InventoryHealth inv = new InventoryHealth(plugin);									
								player.closeInventory();
								inv.newInventory(player);
							}
						}else {
							 Bukkit.getConsoleSender().sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&cThe &eVault &cplugin is not installed and some inventory functions do not work"));
							  for(Player players : Bukkit.getOnlinePlayers()) {
								  if(players.hasPermission("morehealth.admin.warnings") || players.isOp()){
									  players.sendMessage(plugin.nombre+ChatColor.translateAlternateColorCodes('&', "&cThe &eVault &cplugin is not installed and some inventory functions do not work"));
								  }
							  }
						}
					}								
				}
				}else{
					event.setCancelled(true);
		  		    return;
				}
			}
		}		
	}   		
}