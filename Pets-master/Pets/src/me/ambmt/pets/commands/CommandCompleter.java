package me.ambmt.pets.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.ambmt.pets.Pets;
import me.ambmt.pets.models.PetType;

public class CommandCompleter implements TabCompleter {
	private Pets plugin;

	public CommandCompleter(Pets instance) {
		plugin = instance;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> arguments = new ArrayList<String>();
		if (arguments.isEmpty()) {
			for (PetCommand subcommand : plugin.getSubcommands().values()) {
				if (!sender.hasPermission(subcommand.getPermission()))
					continue;

				arguments.add(subcommand.getName());
			}
		}

		ArrayList<String> results = new ArrayList<String>();
		if (args.length >= 0 && args.length < 2) {
			for (String argument : arguments) {
				if (!argument.toLowerCase().startsWith(args[0].toLowerCase()))
					continue;

				results.add(argument);
			}
			return results;

		} else if (args.length >= 1 && args.length < 3 && args[0].equalsIgnoreCase("givepet"))
			return null;
		
		else if (args.length >= 2 && args.length < 4 && args[0].equalsIgnoreCase("givepet")) {
			for (PetType type : PetType.values())
				results.add(type.name().toLowerCase());

			return results;
		}
		return results;
	}
}
