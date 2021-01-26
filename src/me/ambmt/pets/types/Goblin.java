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

public class Goblin extends Pet {
	private int obtainedMoney;

	public Goblin(PetRarity rarity) {
		super(PetType.GOBLIN, rarity, 1, 100);
		obtainedMoney = 1;
	}

	public Goblin(PetRarity rarity, int petLevel, double experience) {
		super(PetType.GOBLIN, rarity, 1, 100, petLevel, experience);
		calculateObtainedMoney();
	}

	@Override
	public void levelUpPet() {
		increaseLevel();
		calculateObtainedMoney();
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
		lore.add(ChatColor.YELLOW + "Money Per Kill: " + obtainedMoney);
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

	private void calculateObtainedMoney() {
		obtainedMoney = getCurrentLevel();
	}

	public int getObtainedMoney() {
		return obtainedMoney;
	}

}
