package me.itsy.plushiesspigot.spigotplushieshop.Tools;

import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlushieUpdates {

    private static Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    public static void updatePlushies(){

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run()
            {
                Spigotplushieshop.regPLushies = Util.generateRandomList(10);
                ConfigManager.getPlayerData().set("players",null);
                ConfigManager.save();
                Bukkit.getServer().broadcastMessage(ChatColor.DARK_PURPLE+"[Plushies]" + ChatColor.AQUA + "- We just received some new plushies, come check them out!");
            }
        },0,24000);

    }

}
