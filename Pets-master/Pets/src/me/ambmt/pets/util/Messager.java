package me.ambmt.pets.util;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ambmt.pets.Pets;
import me.ambmt.pets.commands.PetCommand;
import me.ambmt.pets.models.Pet;

public class Messager {
	public static String PREFIX = "&6[&bPets&6] &r";

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + message));
	}

	public static void sendHelpMessage(CommandSender sender) {
		Pets plugin = Pets.getPlugin(Pets.class);
		String finalMessage = "&bPets commands\n";

		Iterator<PetCommand> iterator = plugin.getSubcommands().values().iterator();

		while (iterator.hasNext()) {
			PetCommand subcommand = iterator.next();
			if (!sender.hasPermission(subcommand.getPermission()))
				continue;

			finalMessage += "&6" + subcommand.getUsageMessage() + " &3- &b" + subcommand.getHelpMessage();
			if (iterator.hasNext())
				finalMessage += "\n";

		}

		Messager.sendMessage(sender, finalMessage);
	}

	public static void sendNoPermissionMessage(CommandSender sender) {
		sendMessage(sender, "&cYou do not have permissions to use this command!");
	}
	
	public static void sendPetReceiveMessage(Player player, Pet pet) {
		sendMessage(player, "&aYou received a " + pet.getName() + "!");
	}
}
