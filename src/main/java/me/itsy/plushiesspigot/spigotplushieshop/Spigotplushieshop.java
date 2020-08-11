package me.itsy.plushiesspigot.spigotplushieshop;


import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import me.itsy.plushiesspigot.spigotplushieshop.Events.InteractEntityEvenyt;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.CommandManager;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.ConfigManager;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.Plushie;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.PlushieUpdates;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class Spigotplushieshop extends JavaPlugin {

    private static Spigotplushieshop instance;
    private static Logger logger = Bukkit.getLogger();
    public CommandManager commandManager;
    private static Economy econ = null;

    public static List<Plushie> regPLushies = new ArrayList<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        registerListeners();

        ConfigManager.setUp();
        setUpDefaults();
        ConfigManager.getShopConfig().options().copyDefaults(true);
        ConfigManager.save();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PlushieUpdates.updatePlushies();

        //command manager stuff;
        commandManager = new CommandManager();
        commandManager.setUp();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InteractEntityEvenyt(),this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            Bukkit.getServer().getLogger().warning("Cant find vault");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            Bukkit.getServer().getLogger().warning("rsp == null");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Spigotplushieshop getInstance(){return instance;}

    private void setUpDefaults(){

        ConfigManager.getShopConfig().addDefault("regular.price",5000);
        ConfigManager.getShopConfig().addDefault("regular.stock",5);

        ConfigManager.getShopConfig().addDefault("shiny.priceMultiplier",2);
        ConfigManager.getShopConfig().addDefault("shiny.stock",5);

        ConfigManager.getShopConfig().addDefault("legend.priceMultiplier",2);
        ConfigManager.getShopConfig().addDefault("legend.stock",5);

        ConfigManager.getShopConfig().addDefault("shinylegend.priceMultiplier",2);
        ConfigManager.getShopConfig().addDefault("shinylegend.stock",5);

        ConfigManager.getShopConfig().addDefault("stuffing",300);

        ConfigManager.getShopConfig().addDefault("scissors",300);


    }

    public static Economy getEconomy() {
        return econ;
    }


}
