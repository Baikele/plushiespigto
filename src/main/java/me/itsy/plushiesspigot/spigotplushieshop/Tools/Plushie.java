package me.itsy.plushiesspigot.spigotplushieshop.Tools;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;

public class Plushie {

    EnumSpecies species;
    boolean isShiny;
    boolean isLegend;
    int stock;
    float price;


    Plushie(EnumSpecies species, boolean isShiny){

        this.species = species;
        this.isShiny = isShiny;

        this.isLegend = Util.checkIfLegend(species);
        this.price = Util.getPrice(isShiny,isLegend);
        this.stock = Util.getStock(isShiny,isLegend);

    }


    public EnumSpecies getSpecies() {
        return species;
    }

    public boolean isShiny() {
        return isShiny;
    }

    public int getStock() {
        return stock;
    }

    public float getPrice() {
        return price;
    }

    public void setStock(int stock){this.stock = stock;}
}
