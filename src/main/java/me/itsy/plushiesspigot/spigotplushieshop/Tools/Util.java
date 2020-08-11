package me.itsy.plushiesspigot.spigotplushieshop.Tools;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.entities.pixelmon.EnumSpecialTexture;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.forms.EnumNoForm;
import com.pixelmonmod.pixelmon.enums.forms.EnumPrimal;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import com.pixelmonmod.pixelmon.storage.NbtKeys;
import com.pixelmonmod.pixelmon.util.helpers.SpriteHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import scala.Int;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static ItemStack getPokemonItem(EnumSpecies species, int form, boolean isEgg, boolean isShiny){

        Pokemon pokemon = Pixelmon.pokemonFactory.create(species);

        PokemonSpec  pokemonSpec = PokemonSpec.from(species.name);
        pokemonSpec.egg = isEgg;
        pokemonSpec.shiny = isShiny;
        pokemonSpec.apply(pokemon);

        return ItemPixelmonSprite.getPhoto(pokemon);

    }


    public static List<Plushie> generateRandomList(int amount){
        List<Plushie> listOfPlushies = new ArrayList<>();
        for(int i = 0; i < amount/2;i++){
            listOfPlushies.add(new Plushie(EnumSpecies.randomPoke(true),false));
        }for(int i = 0; i < amount/2;i++){
            listOfPlushies.add(new Plushie(EnumSpecies.randomPoke(true),true));
        }
        return listOfPlushies;
    }

    public static float getPrice(boolean isShiny,boolean isLegend){
        float price = ConfigManager.getShopConfig().getInt("regular.price");
        if(isShiny){
            price *= ConfigManager.getShopConfig().getInt("shiny.priceMultiplier");
        }
        if(isLegend){
            price *= ConfigManager.getShopConfig().getInt("legend.priceMultiplier");
        }
        return price;
    }

    public static int getStock(boolean isShiny,boolean isLegend){

        if(isLegend&&!isShiny){
            return ConfigManager.getShopConfig().getInt("legend.stock");
        }else if(isShiny&&!isLegend){
            return ConfigManager.getShopConfig().getInt("shiny.stock");
        }else if(isLegend&&isShiny){
            return ConfigManager.getShopConfig().getInt("shinylegend.stock");
        }else{
            return ConfigManager.getShopConfig().getInt("regular.stock");
        }
    }

    public static boolean checkIfLegend(EnumSpecies species){
        EnumSpecies[] speciesList = EnumSpecies.LEGENDARY_ENUMS;
        for(EnumSpecies species1 : speciesList){
            if(species1 == species){
                return true;
            }
        }
        return false;
    }

    public static boolean isOnline(String name){

        if(Bukkit.getServer().getPlayer(name) !=  null){
            System.out.println(Bukkit.getServer().getPlayer(name).getName());
            if(Bukkit.getServer().getPlayer(name).isOnline()){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

}
