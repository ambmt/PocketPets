package me.ambmt.pets.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.ambmt.pets.Pets;
import me.ambmt.pets.models.Pet;
import me.ambmt.pets.models.PetRarity;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Dolphin;
import me.ambmt.pets.types.Dragon;
import me.ambmt.pets.types.Goblin;
import me.ambmt.pets.types.Panda;
import me.ambmt.pets.types.Tiger;
import me.ambmt.pets.util.Messager;

public class CommandGivePet extends PetCommand {
	private Pets plugin;

	public CommandGivePet(Pets instance) {
		plugin = instance;
		setName("givepet");
		setHelpMessage("Gives the specified player the specified pet.");
		setPermission("pets.givepet");
		setUsageMessage("/pets givepet <Player> <Pet>");
		setArgumentLength(3);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player receiver = Bukkit.getPlayer(args[1]);
		if (receiver == null) {
			Messager.sendMessage(sender, "&cPlayer not found.");
			return;
		}
		PetType type = null;
		try {
			type = PetType.valueOf(args[2].toUpperCase());
		} catch (Exception e) {
			Messager.sendMessage(sender, "&cUnknown pet.");
			return;
		}
		if (receiver.getInventory().firstEmpty() == -1) {
			Messager.sendMessage(receiver, "&cFree space in your inventory before you can receive your pet.");
			return;
		}

		Pet pet = null;
		switch (type) {
		case TIGER:
			pet = (Tiger) new Tiger(PetRarity.EPIC);
			break;
		case DOLPHIN:
			pet = (Dolphin) new Dolphin(PetRarity.RARE);
			break;
		case PANDA:
			pet = (Panda) new Panda(PetRarity.RARE);
			break;
		case DRAGON:
			pet = (Dragon) new Dragon(PetRarity.LEGENDARY);
			break;
		case GOBLIN:
			if (plugin.getEconomy() == null) {
				Messager.sendMessage(receiver, "&cYou need an economy plugin and Vault to use the Goblin Pet.");
				return;
			}
			pet = (Goblin) new Goblin(PetRarity.COMMON);
			break;
		default:
			break;
		}
		if (pet == null)
			return;

		receiver.getInventory().addItem(pet.getPetItem());
		Messager.sendPetReceiveMessage(receiver, pet);
	}
}
