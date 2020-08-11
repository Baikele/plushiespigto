package me.itsy.plushiesspigot.spigotplushieshop.Commands;

import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.SubCommand;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    private Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    @Override
    public void onCommand(CommandSender src, String[] args) {

        src.sendMessage(ChatColor.BLUE + "/plushie shop <player> -" + ChatColor.GOLD + "Opens the plushie menu for specified target");
        src.sendMessage(ChatColor.BLUE + "/plushie give <item> <player> -" + ChatColor.GOLD + "Gives specified target specified item(Stuffing/Scissors)");


    }

    @Override
    public String name() {
        return plugin.commandManager.help;
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
