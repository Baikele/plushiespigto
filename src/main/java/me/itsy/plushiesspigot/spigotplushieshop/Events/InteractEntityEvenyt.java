package me.itsy.plushiesspigot.spigotplushieshop.Events;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityStatue;
import com.pixelmonmod.pixelmon.enums.EnumGrowth;
import net.minecraft.entity.Entity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEntityEvenyt implements Listener {


    @EventHandler
    public void interactEntityEvent(PlayerInteractEntityEvent e){

        CraftEntity craftEntity = (CraftEntity) e.getRightClicked();
        ItemStack item = e.getPlayer().getItemInHand();

        Material reducetionKit = Material.matchMaterial("pixelmon:redbadgecase");
        Material enlargementKit = Material.matchMaterial("pixelmon:bluebadgecase");

        if(craftEntity.getHandle() instanceof EntityStatue){
            EntityStatue statue = (EntityStatue)craftEntity.getHandle();
            if(item.getType() == enlargementKit){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "Plushie Enlargement Kit (PEK)")){
                    if(!(statue.getGrowth().index+1 > 7)){
                        statue.setGrowth(EnumGrowth.getGrowthFromIndex(statue.getGrowth().index+1));

                        item.setAmount(item.getAmount()-1);
                        e.getPlayer().getInventory().setItemInMainHand(item);

                    }else{
                        e.getPlayer().sendMessage(ChatColor.RED + "That plushie cant get any bigger!");
                    }
                }
            }else if(item.getType() == reducetionKit){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Plushie Reduction Kit (PRK)")){
                    if(!(statue.getGrowth().index-1 < 0)){
                        statue.setGrowth(EnumGrowth.getGrowthFromIndex(statue.getGrowth().index-1));


                        item.setAmount(item.getAmount()-1);
                        e.getPlayer().getInventory().setItemInMainHand(item);

                        e.setCancelled(true);

                    }else{
                        e.getPlayer().sendMessage(ChatColor.RED + "That plushie cant get any smaller!");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

}
