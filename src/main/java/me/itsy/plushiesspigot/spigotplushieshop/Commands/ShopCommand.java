package me.itsy.plushiesspigot.spigotplushieshop.Commands;

import ca.landonjw.gooeylibs.inventory.api.Button;
import ca.landonjw.gooeylibs.inventory.api.Page;
import ca.landonjw.gooeylibs.inventory.api.Template;
import com.pixelmonmod.pixelmon.client.gui.custom.GuiCustomDrops;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.ConfigManager;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.Plushie;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.SubCommand;
import me.itsy.plushiesspigot.spigotplushieshop.Tools.Util;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import red.mohist.api.PlayerAPI;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopCommand extends SubCommand {

    private Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    @Override
    public void onCommand(CommandSender src, String[] args) {

        Economy eco = Spigotplushieshop.getEconomy();

        Template.Builder template = Template.builder(4);

        if (args.length != 2) {
            src.sendMessage(ChatColor.RED + "Invalid Syntax. See /plushie help");
            return;
        }

        if (!Util.isOnline(args[1])) {
            src.sendMessage(ChatColor.RED + "Cannot find specified player");
            return;
        }


        Player target = Bukkit.getPlayer(args[1]);




        for (int i = 0; i < Spigotplushieshop.regPLushies.size() / 2; i++) {
            final Plushie plush = Spigotplushieshop.regPLushies.get(i);
            final int thing = i;
            net.minecraft.item.ItemStack sprite = Util.getPokemonItem(plush.getSpecies(), 0, false, plush.isShiny());


            String price = ChatColor.YELLOW + "Price: $" + plush.getPrice();
            String stock = ChatColor.GREEN + "In Stock: " + plush.getStock();

            Button button = Button.builder()
                    .item(sprite)
                    .displayName(ChatColor.LIGHT_PURPLE + plush.getSpecies().name + " Plushie")
                    .lore(Arrays.asList(price, stock))
                    .onClick(() -> {
                        List species;

                        if (ConfigManager.getPlayerData().get("players." + target.getName()) != null) {
                            species = ConfigManager.getPlayerData().getList("players." + target.getName());
                        } else {
                            species = new ArrayList();
                        }

                        if (!species.contains(plush.getSpecies().name)) {
                            EconomyResponse r = eco.withdrawPlayer(target, plush.getPrice());
                            if (r.transactionSuccess()) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plushiegive " + target.getName() + " " + plush.getSpecies().name + " growth:3");
                                target.closeInventory();
                                species.add(plush.getSpecies().name);
                                Spigotplushieshop.regPLushies.get(thing).setStock(Spigotplushieshop.regPLushies.get(thing).getStock()-1);

                                ConfigManager.getPlayerData().set("players." + target.getName(), species);
                                ConfigManager.save();
                            } else {
                                target.sendMessage(ChatColor.RED + "Insufficient Funds!");
                                target.closeInventory();
                            }

                        } else {
                            target.sendMessage(ChatColor.RED + "You can only buy one species of plushie per day!");


                        }
                    })
                    .build();

                    template.set(1,i+1,button);

        }

        for (int i = 0; i < Spigotplushieshop.regPLushies.size() / 2; i++) {
            final Plushie plush = Spigotplushieshop.regPLushies.get(i+5);
            final int thing = i;
            net.minecraft.item.ItemStack sprite = Util.getPokemonItem(plush.getSpecies(), 0, false, plush.isShiny());


            String price = ChatColor.YELLOW + "Price: $" + plush.getPrice();
            String stock = ChatColor.GREEN + "In Stock: " + plush.getStock();

            Button button = Button.builder()
                    .item(sprite)
                    .displayName(ChatColor.LIGHT_PURPLE + "Shiny " + plush.getSpecies().name + " Plushie")
                    .lore(Arrays.asList(price, stock))
                    .onClick(() -> {
                        List species;

                        if (ConfigManager.getPlayerData().get("players." + target.getName()) != null) {
                            species = ConfigManager.getPlayerData().getList("players." + target.getName());
                        } else {
                            species = new ArrayList();
                        }

                        if (!species.contains(plush.getSpecies().name)) {
                            EconomyResponse r = eco.withdrawPlayer(target, plush.getPrice());
                            if (r.transactionSuccess()) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plushiegive " + target.getName() + " " + plush.getSpecies().name + " shiny growth:3");
                                target.closeInventory();
                                species.add(plush.getSpecies().name);



                                Spigotplushieshop.regPLushies.get(thing+5).setStock(Spigotplushieshop.regPLushies.get(thing+5).getStock()-1);

                                ConfigManager.getPlayerData().set("players." + target.getName(), species);
                                ConfigManager.save();
                            } else {
                                target.sendMessage(ChatColor.RED + "Insufficient Funds!");
                                target.closeInventory();
                            }

                        } else {
                            target.sendMessage(ChatColor.RED + "You can only buy one species of plushie per day!");


                        }
                    })
                    .build();

            template.set(2,i+1,button);

        }

        Button pink = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.PINK.func_176765_a()));
        Button red = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.RED.func_176765_a()));
        Button orange = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.ORANGE.func_176765_a()));
        Button yellow = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.YELLOW.func_176765_a()));
        Button lgreen = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.LIME.func_176765_a()));
        Button blue = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.BLUE.func_176765_a()));
        Button lblue = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.LIGHT_BLUE.func_176765_a()));
        Button mag = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.MAGENTA.func_176765_a()));
        Button purple = Button.of(new ItemStack(Blocks.field_150397_co, 1, EnumDyeColor.PURPLE.func_176765_a()));


        template.set(0,0,pink);
        template.set(1,0,pink);
        template.set(2,0,pink);
        template.set(3,0,pink);

        template.set(0,6,lblue);
        template.set(1,6,lblue);
        template.set(2,6,lblue);
        template.set(3,6,lblue);

        template.set(0,8,purple);
        template.set(1,8,purple);
        template.set(2,8,purple);
        template.set(3,8,purple);

        template.set(0,1,red);
        template.set(3,1,red);

        template.set(0,2,orange);
        template.set(3,2,orange);

        template.set(0,3,yellow);
        template.set(3,3,yellow);

        template.set(0,4,lgreen);
        template.set(3,4,lgreen);

        template.set(0,5,blue);
        template.set(3,5,blue);

        template.set(0,7,mag);
        template.set(3,7,mag);


        ItemStack enlar = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.matchMaterial("pixelmon:bluebadgecase")));
        ItemStack reduc = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.matchMaterial("pixelmon:redbadgecase")));


        int kitPrice = ConfigManager.getShopConfig().getInt("stuffing");

        Button enlargeButton = Button.builder()
                .displayName(ChatColor.BLUE + "Plushie Enlargement Kit (PEK)")
                .item(enlar)
                .onClick(()->{
                    EconomyResponse r = eco.withdrawPlayer(target, kitPrice);
                    if (r.transactionSuccess()) {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plushie give enlargementkit " +target.getName() + " 1" );
                        target.closeInventory();


                    } else {
                        target.sendMessage(ChatColor.RED + "Insufficient Funds!");
                        target.closeInventory();
                    }


                })
                .lore(Arrays.asList(ChatColor.GRAY + "Right click any plushie to enlarge its size by one size (One time use!)",ChatColor.YELLOW + "Price:" + kitPrice))
                .build();

        Button reductionButton = Button.builder()
                .displayName(ChatColor.RED + "Plushie Reduction Kit (PRK)")
                .onClick(()->{
                    EconomyResponse r = eco.withdrawPlayer(target, kitPrice);
                    if (r.transactionSuccess()) {
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "plushie give reductionkit " +target.getName() + " 1" );
                        target.closeInventory();


                    } else {
                        target.sendMessage(ChatColor.RED + "Insufficient Funds!");
                        target.closeInventory();
                    }


                })
                .item(reduc)
                .lore(Arrays.asList(ChatColor.GRAY + "Right click any plushie to reduce its size by one size (One time use!)",ChatColor.YELLOW + "Price:" + kitPrice))
                .build();

        template.set(1,7,enlargeButton);
        template.set(2,7,reductionButton);


        String[] plushie = "plushie".split("");

        String rainbow = "";

        rainbow += ChatColor.RED +"P";
        rainbow += ChatColor.GOLD +"l";
        rainbow += ChatColor.YELLOW +"u";
        rainbow += ChatColor.GREEN +"s";
        rainbow += ChatColor.BLUE +"h";
        rainbow += ChatColor.DARK_BLUE +"i";
        rainbow += ChatColor.DARK_PURPLE +"e";
        rainbow += ChatColor.RED +" ";
        rainbow += ChatColor.LIGHT_PURPLE +"S";
        rainbow += ChatColor.RED +"h";
        rainbow += ChatColor.GOLD +"o";
        rainbow += ChatColor.YELLOW +"p";

        Page page = Page.builder()
                .title(rainbow)
                .template(template.build())
                .build();

        CraftPlayer craftPlayer = (CraftPlayer) target;


        page.openPage(craftPlayer.getHandle());

    }

    @Override
    public String name() {
        return plugin.commandManager.shop;
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
