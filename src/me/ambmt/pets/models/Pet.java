package me.ambmt.pets.models;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public abstract class Pet {
	private PetType type;
	private PetRarity rarity;
	private int currentLevel;
	private int minLevel;
	private int maxLevel;
	private double experience;
	private double experienceToNextLevel;

	public Pet(PetType type, PetRarity rarity, int minLevel, int maxLevel) {
		this.type = type;
		this.rarity = rarity;
		this.currentLevel = 1;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		experience = 0;
		calculateExperienceToNextLevel();
	}
	
	public Pet(PetType type, PetRarity rarity, int minLevel, int maxLevel, int currentLevel, double experience) {
		this.type = type;
		this.rarity = rarity;
		this.currentLevel = 1;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.currentLevel = currentLevel;
		this.experience = experience;
		calculateExperienceToNextLevel();
	}

	public abstract void levelUpPet();

	public abstract ItemStack getPetItem();

	public String getName() {
		String name = type.name().toLowerCase();
		// Uper case first letter
		name =  ChatColor.BOLD + name.substring(0, 1).toUpperCase() + name.substring(1);

		switch (rarity) {
		case COMMON:
			return ChatColor.RESET + name;
		case UNCOMMON:
			return ChatColor.GREEN + name;
		case RARE:
			return ChatColor.BLUE + name;
		case EPIC:
			return ChatColor.DARK_PURPLE + name;
		case LEGENDARY:
			return ChatColor.GOLD + name;
		case MYTHIC:
			return ChatColor.LIGHT_PURPLE + name;
		default:
			return ChatColor.RESET + name;
		}
	}

	public PetType getType() {
		return type;
	}

	public PetRarity getRarity() {
		return rarity;
	}

	public void setRarity(PetRarity rarity) {
		this.rarity = rarity;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void increaseLevel() {
		currentLevel++;
		calculateExperienceToNextLevel();
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
	}

	public void increaseExperience(double amount) {
		experience += amount;
	}
	
	public double getExperienceToNextLevel() {
		return experienceToNextLevel;
	}
	
	public void calculateExperienceToNextLevel() {
		experienceToNextLevel = getCurrentLevel() * 3;
	}
}