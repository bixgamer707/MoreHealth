package bixgamer707.morehealth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import bixgamer707.morehealth.commands.CommandHeartGUI;
import bixgamer707.morehealth.commands.CommandPlayersHearts;
import bixgamer707.morehealth.commands.MainCommand;
import bixgamer707.morehealth.commands.Tab;
import bixgamer707.morehealth.events.InventoryConfirm;
import bixgamer707.morehealth.events.InventoryHealth;
import bixgamer707.morehealth.events.InventoryPlayers;
import bixgamer707.morehealth.events.MessageCheckUpdater;
import bixgamer707.morehealth.events.PlayerChatListener;
import bixgamer707.morehealth.utils.Hearts;
import bixgamer707.morehealth.utils.HeartsPlayer;
import net.milkbowl.vault.economy.Economy;

public class MoreHealth extends JavaPlugin{
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String latestVersion;
	private Economy econ = null;
	
	public String getVersion() {
		return this.version;
	}
	public String latestVersion(){
		return this.latestVersion;
	}
	
	private List<UUID> editxp = new ArrayList<>();
	private List<UUID> editmoney = new ArrayList<>();
	private ArrayList<Hearts> hearts = new ArrayList<Hearts>();
	private int startHearts = 0;
	public int maxHearts;
	FileConfiguration config = getConfig();
	public String nombre = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix"));			
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.LIGHT_PURPLE+"Has been activated "+ChatColor.GRAY+"(version: "+ChatColor.GREEN+version+ChatColor.LIGHT_PURPLE+")");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.AQUA+"Thanks for using my plugin! ~bixgamer707 :)!!");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		registerCommands();
		registerEvents();
		setupEconomy();
		checkUpdate();
		new ConfigFile();
		new MessagesFile();
		this.getCommand("morehealth").setTabCompleter(new Tab());
		load();
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new HeartsPlayer(this).register();
		}
	}
	public void onDisable(){
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Has been deactivated");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"Thanks for using my plugin! ~bixgamer707 :)!!");
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"+--------------------------------------------+");
		savePlayers();
	}
	
	private boolean setupEconomy(){
		if(getServer().getPluginManager().getPlugin("Vault") == null){
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null){
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public Economy getEconomy(){
		return this.econ;
	}
	public void registerCommands(){
		this.getCommand("morehealth").setExecutor(new MainCommand(this));
		this.getCommand("hearts").setExecutor(new CommandHeartGUI(this));
		this.getCommand("players").setExecutor(new CommandPlayersHearts(this));
	}
	public void registerEvents(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryHealth(this), this);
		pm.registerEvents(new InventoryConfirm(this), this);
		pm.registerEvents(new InventoryPlayers(this), this);
		pm.registerEvents(new MessageCheckUpdater(this), this);
		pm.registerEvents(new PlayerChatListener(this), this);
	}

	public void checkUpdate() {
		try {
			  HttpURLConnection con = (HttpURLConnection) new URL(
	                  "https://api.spigotmc.org/legacy/update.php?resource=93100").openConnection();
	          int timed_out = 1250;
	          con.setConnectTimeout(timed_out);
	          con.setReadTimeout(timed_out);
	          latestVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
	          if (latestVersion.length() <= 7) {
	        	  if(!version.equals(latestVersion)){
	        		  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.translateAlternateColorCodes('&', "&a&lThere is a version available of"));
	        		  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.translateAlternateColorCodes('&', "&e&lYou can download it at:"));
	        		  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.translateAlternateColorCodes('&', "&b&lhttps://www.spigotmc.org/resources/morehealth.93100/"));
	        	  }      	  
	          }
	      } catch (Exception ex) {
	    	  Bukkit.getConsoleSender().sendMessage(nombre + ChatColor.RED +"Error while checking update.");
	      }
	  }
	public boolean contains(UUID uuid) {
		return editxp.contains(uuid);
	}
	public void add(UUID uuid) {
		if(!contains(uuid)) {
			editxp.add(uuid);
		}
	}
	public void remove(UUID uuid) {
		if(contains(uuid)) {
			editxp.remove(uuid);
		}
	}
	public List<UUID> getEditXp(){
		return editxp;
	}
	public boolean containss(UUID uuid) {
		return editmoney.contains(uuid);
	}
	public void addd(UUID uuid) {
		if(!contains(uuid)) {
			editmoney.add(uuid);
		}
	}
	public void removee(UUID uuid) {
		if(contains(uuid)) {
			editmoney.remove(uuid);
		}
	}
	public List<UUID> getEditMoney(){
		return editmoney;
	}
	
	public int getHearts() {
		return this.startHearts;
	}
	
	public Hearts getHeartsPlayer(String uuid) {
		for(Hearts heart : hearts) {
			if(heart.getUuid().equals(uuid)) {
				return heart;
			}
		}
		return null;
	}
	
	public void addHeart(String uuid) {
		hearts.add(new Hearts(uuid,startHearts+1));
	}

	public void savePlayers() {
		ConfigFile config = new ConfigFile();
		config.set("Players", null);
		for(Hearts h : hearts) {
			config.set("Players."+h.getUuid()+".hearts-claim", h.getHearts());
			config.saveFile();
		}
		config.saveFile();
		
	}
	
	public void load() {
		ConfigFile config = new ConfigFile();
		if(config.contains("Players")) {
			for(String uuid : config.getConfigurationSection("Players").getKeys(false)) {
				int hearts = config.getInt("Players."+uuid+".hearts-claim");
				Hearts h = new Hearts(uuid, hearts);
				this.hearts.add(h);
			}
			
		}
	}
}