package me.ambmt.pets.commands;

import org.bukkit.command.CommandSender;

import me.ambmt.pets.Pets;
import me.ambmt.pets.util.Messager;

public class CommandReload extends PetCommand {
	private Pets plugin;
	
	public CommandReload(Pets instance) {
		plugin = instance;
		
		setName("reload");
		setHelpMessage("Reloads the plugin's config.");
		setPermission("pets.reload");
		setUsageMessage("/pets reload");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		plugin.reloadConfig();
		Messager.sendMessage(sender, "&aConfig reloaded!");
	}
}
