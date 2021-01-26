package me.ambmt.pets.models;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import me.ambmt.pets.Pets;

public class ConfigManager {
	private FileConfiguration config;

	public ConfigManager(Pets plugin) {
		config = plugin.getConfig();
	}

	@SuppressWarnings("deprecation")
	public OfflinePlayer getPetSkin(PetType type) {
		OfflinePlayer owner = Bukkit
				.getOfflinePlayer((config.getString("pets." + type.name().toLowerCase() + ".skin")));
		
		return owner;
	}

	public String getPetName(String petName) {
		return config.getString("pets." + petName + ".name");
	}
	
	public long getLevelingTimer() {
		double timer = config.getDouble("leveling-timer") * 20;
		timer = Math.floor(timer);
		return (long) timer;
	}
	
	public double getExperienceGain() {
		return config.getDouble("experience-gain");
	}
}