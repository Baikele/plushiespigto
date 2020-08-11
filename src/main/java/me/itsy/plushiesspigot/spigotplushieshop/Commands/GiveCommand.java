package me.itsy.plushiesspigot.spigotplushieshop.Commands;

import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.SubCommand;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.Util;
import org.apache.logging.log4j.core.config.ScriptsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class GiveCommand extends SubCommand {

    private Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    @Override
    public void onCommand(CommandSender src, String[] args) {



        if (args.length != 4) {
            src.sendMessage(ChatColor.RED + "Invalid Syntax. See /plushie help");
            return;
        }

        if (!Util.isOnline(args[2])) {
            src.sendMessage(ChatColor.RED + "Cannot find specified player");
            return;
        }

        String item;
        String itemnString;
        int amount = Integer.parseInt(args[3]);

        Player target = Bukkit.getPlayer(args[2]);

        if (args[1].equalsIgnoreCase("enlargementkit")) {
            item = "enlargementkit";
            itemnString = "pixelmon:bluebadgecase";
        }else if(args[1].equalsIgnoreCase("reductionkit")){
            item = "reductionkit";
            itemnString = "pixelmon:redbadgecase";
        }else{
            src.sendMessage(ChatColor.RED + "item not found! see: enlargementkit/reductionkit");
            return;
        }

        ItemStack itemStack = new ItemStack(Material.matchMaterial(itemnString),amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if(item.equalsIgnoreCase("enlargementkit")){
            itemMeta.setDisplayName(ChatColor.BLUE + "Plushie Enlargement Kit (PEK)");

            itemMeta.setLore(new ArrayList<String>() {{
                add(ChatColor.GRAY + "Right click any plushie to enlarge its size by one size (One time use!)");
            }});


        }else{
            itemMeta.setDisplayName(ChatColor.RED + "Plushie Reduction Kit (PRK)");
            itemMeta.setLore(new ArrayList<String>() {{
                add(ChatColor.GRAY + "Right click any plushie to reduce its size by one size (One time use!)");
            }});
        }

        src.sendMessage(ChatColor.GREEN + "Gave " + target.getName() + " " + amount + " kits");
        target.sendMessage(ChatColor.GREEN + "You have received " + amount + " kits");

        itemStack.setItemMeta(itemMeta);
        target.getInventory().addItem(itemStack);

    }

    @Override
    public String name() {
        return plugin.commandManager.give;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
