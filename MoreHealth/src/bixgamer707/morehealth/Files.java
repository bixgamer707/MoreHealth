package bixgamer707.morehealth;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class Files extends YamlConfiguration {

    private File file = null;
    private MoreHealth main;

    public Files(String fileName){
    	main = MoreHealth.getPlugin(MoreHealth.class);
        if(file == null){
            file = new File(main.getDataFolder(), fileName);
        }

        if(!file.exists()){
            saveDefaultFile();
            reloadFile();
        }
        reloadFile();

    }

    public void reloadFile(){
        try{
            super.load(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveFile(){
        try{
            super.save(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveDefaultFile(){
        main.saveResource(file.getName(), false);
    }
}


