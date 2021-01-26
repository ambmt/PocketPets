package me.ambmt.pets.models;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import me.ambmt.pets.types.Dolphin;
import me.ambmt.pets.types.Dragon;
import me.ambmt.pets.types.Goblin;
import me.ambmt.pets.types.Panda;
import me.ambmt.pets.types.Tiger;

public class PetManager {

	public static Pet getPet(ItemStack item) {
		if (item == null)
			return null;

		NBTItem nbtItem = new NBTItem(item);
		if (!nbtItem.hasNBTData())
			return null;
		if (!nbtItem.hasKey("pet_type"))
			return null;

		PetType type = null;
		int petLevel = 0;
		double experience = 0;
		PetRarity rarity = null;
		try {
			type = PetType.valueOf(nbtItem.getString("pet_type"));
			petLevel = nbtItem.getInteger("pet_level");
			experience = nbtItem.getDouble("pet_experience");
			rarity = PetRarity.valueOf(nbtItem.getString("pet_rarity"));
		} catch (Exception e) {
			return null;
		}

		switch (type) {
		case TIGER:
			return new Tiger(rarity, petLevel, experience);
		case DOLPHIN:
			return new Dolphin(rarity, petLevel, experience);
		case DRAGON:
			return new Dragon(rarity, petLevel, experience);
		case PANDA:
			return new Panda(rarity, petLevel, experience);
		case GOBLIN:
			return new Goblin(rarity, petLevel, experience);
		default:
			return null;

		}
	}

	public static Pet getActivePet(Player player, PetType type) {
		for (int i = 0; i < 9; i++) {
			ItemStack item = player.getInventory().getItem(i);
			Pet pet = PetManager.getPet(item);
			if (pet == null)
				continue;
			if (pet.getType() != type)
				continue;

			return pet;
		}

		return null;
	}

	public static Map<Integer, Pet> getActivePets(Player player) {
		Map<Integer, Pet> totalPets = new HashMap<Integer, Pet>();
		for (int i = 0; i < 9; i++) {
			ItemStack item = player.getInventory().getItem(i);
			Pet pet = PetManager.getPet(item);
			if (pet == null)
				continue;

			totalPets.put(i, pet);
		}

		return totalPets;
	}
}
