package bixgamer707.morehealth;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import bixgamer707.morehealth.commands.CommandHeartGUI;
import bixgamer707.morehealth.commands.MainCommand;
import bixgamer707.morehealth.commands.Tab;
import bixgamer707.morehealth.events.InventoryConfirm;
import bixgamer707.morehealth.events.InventoryHealth;

public class MoreHealth extends JavaPlugin{
	PluginDescriptionFile pdffile = getDescription();
	private FileConfiguration messages = null;
	private File messagesFile = null;
	public String rutaConfig;
	public String version = pdffile.getVersion();
	FileConfiguration config = getConfig();
	public String nombre = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix"));			
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.LIGHT_PURPLE+"Has been activated "+ChatColor.GRAY+"(version: "+ChatColor.GREEN+version+ChatColor.LIGHT_PURPLE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.AQUA+"Thanks for using my plugin! ~bixgamer707 :)!!");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		registerCommands();
		registerEvents();
		registerConfig();
		registerMessages();
		this.getCommand("morehealth").setTabCompleter(new Tab());
	}
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Has been deactivated");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"Thanks for using my plugin! ~bixgamer707 :)!!");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
	}
	public void registerCommands(){
		this.getCommand("morehealth").setExecutor(new MainCommand(this));
		this.getCommand("hearts").setExecutor(new CommandHeartGUI(this));
	}
	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryHealth(this), this);
		pm.registerEvents(new InventoryConfirm(this), this);
	}
	public void registerConfig(){
		File config = new File(this.getDataFolder(), "config.yml");
		rutaConfig = config.getPath();
		if(!config.exists()){
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	public FileConfiguration getMessages(){
		if(messages == null){
			reloadMessages();
		}
		return messages;
	}
 
	public void reloadMessages(){
		if(messages == null){
			messagesFile = new File(getDataFolder(),"messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		Reader defConfigStream;
		try{
			defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
			if(defConfigStream != null){
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				messages.setDefaults(defConfig);
			}			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
 
	public void saveMessages(){
		try{
			messages.save(messagesFile);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
 
	public void registerMessages(){
		messagesFile = new File(this.getDataFolder(),"messages.yml");
		if(!messagesFile.exists()){
			this.getMessages().options().copyDefaults(true);
			saveMessages();
		}
	}
}