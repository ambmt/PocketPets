package me.ambmt.pets.commands;

import org.bukkit.command.CommandSender;

import me.ambmt.pets.util.Messager;

public class CommandHelp extends PetCommand {
	public CommandHelp() {
		setName("help");
		setHelpMessage("Displays this list.");
		setPermission("pets.help");
		setUsageMessage("/pets help");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Messager.sendHelpMessage(sender);
	}
}
