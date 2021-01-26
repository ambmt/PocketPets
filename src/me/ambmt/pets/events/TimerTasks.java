package me.ambmt.pets.events;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.ambmt.pets.Pets;
import me.ambmt.pets.models.ConfigManager;
import me.ambmt.pets.models.Pet;
import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Dragon;

public class TimerTasks {
	private Pets plugin;

	public TimerTasks(Pets instance) {
		plugin = instance;
	}

	public void startLevelingTask() {
		ConfigManager config = new ConfigManager(plugin);
		long timer = config.getLevelingTimer();

		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Map<Integer, Pet> activePets = PetManager.getActivePets(player);
				if (activePets.isEmpty())
					continue;

				levelUpAllActivePets(player, activePets);
			}
		}, 0, timer);
	}

	public void startDragonEffectTask() {
		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Dragon dragon = (Dragon) PetManager.getActivePet(player, PetType.DRAGON);
				if (dragon == null)
					continue;

				int speedLevel = dragon.getSpeedLevel();
				int strengthLevel = dragon.getStrengthLevel();
				int regenerationLevel = dragon.getRegenerationLevel();

				player.addPotionEffects(getDragonEffects(speedLevel, strengthLevel, regenerationLevel));
			}
		}, 0, 100);
	}

	private List<PotionEffect> getDragonEffects(int speed, int strength, int regeneration) {
		PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 200, --speed, true, false);
		PotionEffect strengthEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, --strength, true, false);
		PotionEffect regenerationEffect = new PotionEffect(PotionEffectType.REGENERATION, 200, --regeneration, true,
				false);

		return Arrays.asList(new PotionEffect[] { speedEffect, strengthEffect, regenerationEffect });
	}

	private void levelUpAllActivePets(Player owner, Map<Integer, Pet> activePets) {
		ConfigManager config = new ConfigManager(plugin);
		double experienceGain = config.getExperienceGain();

		for (Map.Entry<Integer, Pet> entry : activePets.entrySet()) {
			int slot = entry.getKey();
			Pet pet = entry.getValue();
			if (pet.getCurrentLevel() >= pet.getMaxLevel())
				continue;
			
			pet.increaseExperience(experienceGain);
			
			if (pet.getExperience() < pet.getExperienceToNextLevel()) {
				owner.getInventory().setItem(slot, pet.getPetItem());
				continue;
			}

			pet.levelUpPet();
			pet.setExperience(0);
			owner.getInventory().setItem(slot, pet.getPetItem());
		}
	}
}
