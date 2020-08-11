package me.itsy.plushiesspigot.spigotplushieshop.Tools;

import me.itsy.plushiesspigot.spigotplushieshop.Commands.GiveCommand;
import me.itsy.plushiesspigot.spigotplushieshop.Commands.HelpCommand;
import me.itsy.plushiesspigot.spigotplushieshop.Commands.ShopCommand;
import me.itsy.plushiesspigot.spigotplushieshop.Spigotplushieshop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private Spigotplushieshop plugin = Spigotplushieshop.getInstance();

    public CommandManager() {

    }

    public String main = "plushie";
    public String shop = "shop";
    public String help = "help";
    public String give = "give";


    public void setUp() {
        plugin.getCommand(main).setExecutor(this);
        plugin.getCommand(main).setTabCompleter(this);

        this.commands.add(new ShopCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new GiveCommand());

    }

    @Override
    public boolean onCommand(CommandSender src, Command command, String label, String[] args) {


        if (command.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                return false;
            }

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.addAll(Arrays.asList(args));
            arrayList.remove(0);

            try {
                target.onCommand(src, args);
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        return false;
    }

    private SubCommand get(String name) {
        Iterator<SubCommand> subCommands = this.commands.iterator();

        while (subCommands.hasNext()) {
            SubCommand sc = (SubCommand) subCommands.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;
            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sc;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender src, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase(main)){
            if(args.length == 1){
                List<String> list = new ArrayList<>();
                list.add("shop");
                list.add("help");
                list.add("give");

                return list;
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("give")){
                List<String> list = new ArrayList<>();
                list.add("enlargementkit");
                list.add("reductionkit");
                return list;
            }
        }

        return null;
    }

}
