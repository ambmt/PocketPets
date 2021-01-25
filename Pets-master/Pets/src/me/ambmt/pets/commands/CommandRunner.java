package me.ambmt.pets.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ambmt.pets.Pets;
import me.ambmt.pets.util.Messager;

public class CommandRunner implements CommandExecutor {
	private Pets plugin;
	
	public CommandRunner(Pets instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("pets"))
			return true;
		if (args.length == 0) {
			return true;
		}
		if (!plugin.getSubcommands().containsKey(args[0].toLowerCase())) {
			Messager.sendMessage(sender, "&cUnknown command. Type &l/pets help &cto see the full command list.");
			return true;
		}
		
		PetCommand subcommand = plugin.getSubcommands().get(args[0].toLowerCase());
		if (!sender.hasPermission(subcommand.getPermission())) {
			Messager.sendNoPermissionMessage(sender);
			return true;
		}
		if (subcommand.isPlayerCommand && !(sender instanceof Player)) {
			Messager.sendMessage(sender, "&cNot available for consoles.");
			return true;
		}
		if (subcommand.isConsoleCommand && sender instanceof Player) {
			Messager.sendMessage(sender, "&cNot available for players.");
			return true;
		}
		if (args.length < subcommand.getArgumentLength()) {
			Messager.sendMessage(sender, subcommand.getUsageMessage());
			return true;
		}
		
		subcommand.execute(sender, args);
		
		return false;
	}
}
