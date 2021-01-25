package me.ambmt.pets.events;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Dolphin;
import net.minecraft.server.v1_12_R1.EntityFishingHook;

public class PlayerFish implements Listener {

	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		Dolphin dolphin = (Dolphin) PetManager.getActivePet(player, PetType.DOLPHIN);
		if (dolphin == null)
			return;

		double speedMultiplier = dolphin.getFishingSpeedMultiplier();
		double luckMultiplier = dolphin.getFishingLuckMultiplier();
		FishHook hook = event.getHook();

		setFishingSpeedField(hook, speedMultiplier);
		setLuckField(hook, luckMultiplier);
	}

	private void setFishingSpeedField(FishHook hook, double speedMultiplier) {
		Field fishCatchTime = null;
		try {
			fishCatchTime = net.minecraft.server.v1_12_R1.EntityFishingHook.class.getDeclaredField("h");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// Simulating fishing system
		net.minecraft.server.v1_12_R1.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();
		Random random = new Random();
		int fishTime = random.nextInt(600 - 100) + 100;
		fishTime /= speedMultiplier;

		// Set fishing speed
		fishCatchTime.setAccessible(true);
		try {
			fishCatchTime.set(hookCopy, fishTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fishCatchTime.setAccessible(false);
	}

	private void setLuckField(FishHook hook, double luckMultiplier) {
		Field luck = null;
		try {
			luck = net.minecraft.server.v1_12_R1.EntityFishingHook.class.getDeclaredField("aw");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		// Getting original luck
		net.minecraft.server.v1_12_R1.EntityFishingHook hookCopy = (EntityFishingHook) ((CraftEntity) hook).getHandle();
		int luckValue = 0;
		luck.setAccessible(true);
		try {
			luckValue = luck.getInt(hookCopy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Setting new luck
		luckValue *= luckMultiplier;
		try {
			luck.setInt(hookCopy, luckValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		luck.setAccessible(false);
	}
}
