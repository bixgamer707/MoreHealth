package bixgamer707.morehealth.utils;

import org.bukkit.entity.Player;

import bixgamer707.morehealth.ConfigFile;
import bixgamer707.morehealth.MoreHealth;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class HeartsPlayer extends PlaceholderExpansion{
    private MoreHealth plugin;

    public HeartsPlayer(MoreHealth plugin) {
    	this.plugin = plugin;
    }
    
    @Override
    public boolean persist(){
        return true;
    }
    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "bixgamer707";
    }
 
    
    @Override
    public String getIdentifier(){
        return "morehealth";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }
  
    
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        if(identifier.equals("hearts")){        
        	Hearts hearts = plugin.getHeartsPlayer(player.getUniqueId().toString());
        	if(hearts == null) {
        		return plugin.getHearts()+"";
        	}else {
        		return hearts.getHearts()+"";
        	}
        }
        if(identifier.equals("max_hearts")){        
        	ConfigFile config = new ConfigFile();
        	plugin.maxHearts = config.getInt("max-hearts");
        	return plugin.maxHearts+"";
        }
        return null;
    }
}
