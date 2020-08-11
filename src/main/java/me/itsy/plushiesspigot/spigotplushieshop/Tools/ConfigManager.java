package me.itsy.plushiesspigot.spigotplushieshop.Tools;

import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class ConfigManager {

    private static Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    private static File file,file2;
    private static FileConfiguration shopConfig, playerData;

    public static void setUp(){

        file = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(),"settings.yml");
        file2 = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(),"players.yml");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (Exception e){
                plugin.getLogger().warning("Config not created properly");
            }
        }
        if(!file2.exists()){
            try{
                file2.createNewFile();
            }catch (Exception e){
                plugin.getLogger().warning("Config not created properly");
            }
        }

        shopConfig = YamlConfiguration.loadConfiguration(file);
        playerData = YamlConfiguration.loadConfiguration(file2);

    }

    public static FileConfiguration getShopConfig(){return shopConfig;}
    public static FileConfiguration getPlayerData(){return playerData;}

    public static void save(){
        try{
            shopConfig.save(file);
            playerData.save(file2);
        }catch (Exception  e){
            plugin.getLogger().warning("Config not saved properly");
        }
    }

    public static void reload(){
        shopConfig = YamlConfiguration.loadConfiguration(file);
        playerData = YamlConfiguration.loadConfiguration(file2);
    }

}
