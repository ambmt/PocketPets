package me.ambmt.pets.types;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.ambmt.pets.Pets;
import me.ambmt.pets.models.ConfigManager;
import me.ambmt.pets.models.Pet;
import me.ambmt.pets.models.PetRarity;
import me.ambmt.pets.models.PetType;

public class Dragon extends Pet {
	private double strengthLevel;
	private double speedLevel;
	private double regenerationLevel;

	public Dragon(PetRarity rarity) {
		super(PetType.DRAGON, rarity, 1, 100);
		strengthLevel = 1;
		speedLevel = 1;
		regenerationLevel = 1;
	}

	public Dragon(PetRarity rarity, int petLevel, double experience) {
		super(PetType.DRAGON, rarity, 1, 100, petLevel, experience);
		calculateMultipliers();
	}

	@Override
	public void levelUpPet() {
		increaseLevel();
		calculateMultipliers();
	}

	@Override
	public ItemStack getPetItem() {
		ItemStack pet = new ItemStack(Material.SKULL_ITEM);
		SkullMeta meta = (SkullMeta) pet.getItemMeta();
		ConfigManager config = new ConfigManager(Pets.getPlugin(Pets.class));

		meta.setOwningPlayer(config.getPetSkin(getType()));

		meta.setDisplayName(getName());
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "Pet Level: " + getCurrentLevel());
		lore.add("");
		lore.add(ChatColor.LIGHT_PURPLE + "Strength Level: " + strengthLevel);
		lore.add(ChatColor.LIGHT_PURPLE + "Speed Level: " + speedLevel);
		lore.add(ChatColor.LIGHT_PURPLE + "Regeneration Level: " + regenerationLevel);
		lore.add("");
		if (getCurrentLevel() < getMaxLevel())
			lore.add(ChatColor.GRAY + "Level Progress: " + getExperience() + "/" + getExperienceToNextLevel());
		meta.setLore(lore);
		pet.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(pet);
		nbtItem.setString("pet_type", getType().name());
		nbtItem.setInteger("pet_level", getCurrentLevel());
		nbtItem.setDouble("pet_experience", getExperience());
		nbtItem.setString("pet_rarity", getRarity().name());
		nbtItem.applyNBT(pet);

		return pet;
	}

	private void calculateMultipliers() {
		strengthLevel = 1 + getCurrentLevel() / 10.0;
		speedLevel = 1 + getCurrentLevel() / 10.0;
		regenerationLevel = 1 + getCurrentLevel() / 10.0;
	}

	public int getStrengthLevel() {
		return (int) Math.floor(strengthLevel);
	}

	public int getSpeedLevel() {
		return (int) Math.floor(speedLevel);
	}

	public int getRegenerationLevel() {
		return (int) Math.floor(regenerationLevel);
	}
}
