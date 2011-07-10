package com.onarandombox.MultiverseCore.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.command.BaseCommand;

public class ModifyRemoveCommand extends BaseCommand {

    public ModifyRemoveCommand(MultiverseCore plugin) {
        super(plugin);
        this.name = "Modify a World";
        this.description = "Modify various aspects of worlds. See the help wiki for how to use this command properly. If you do not include a world, the current world will be used";
        this.usage = "/mvmodify" + ChatColor.GREEN + "REMOVE {PROPERTY} {VALUE}" + ChatColor.GOLD + " [WORLD]";
        this.minArgs = 2;
        this.maxArgs = 3;
        this.identifiers.add("mvmodify remove");
        this.identifiers.add("mvmodify r");
        this.identifiers.add("mvmremove");
        this.identifiers.add("mvmr");
        this.permission = "multiverse.world.modify";
        this.requiresOp = true;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // We NEED a world from the command line
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        }

        if (args.length == 2 && p == null) {
            sender.sendMessage(ChatColor.RED + "From the console, WORLD is required.");
            sender.sendMessage(this.description);
            sender.sendMessage(this.usage);
            sender.sendMessage("Nothing changed.");
            return;
        }

        MVWorld world;
        String value = args[0];
        String property = args[1];

        if (args.length == 2) {
            world = this.plugin.getMVWorld(p.getWorld().getName());
        } else {
            world = this.plugin.getMVWorld(args[2]);
        }

        if (world == null) {
            sender.sendMessage("That world does not exist!");
            return;
        }

        if (!ModifyCommand.validateAction(Action.Remove, property)) {
            sender.sendMessage("Sorry, you can't REMOVE anything from" + property);
            sender.sendMessage("Please visit our wiki for more information: URLGOESHERE FERNFERRET DON'T FORGET IT!");
            return;
        }
        if (world.removeFromList(property, value)) {
            sender.sendMessage(value + " was removed from " + property);
        } else {
            sender.sendMessage(value + " could not be removed from " + property);
        }
    }

}