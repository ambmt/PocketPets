package me.ambmt.pets.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import me.ambmt.pets.Pets;
import me.ambmt.pets.util.AxeManager;

public class AxeEnchantmentRemover implements Listener {

	@EventHandler
	public void onAxeClick(InventoryClickEvent event) {
		ItemStack clickedItem = null;
		Player player = (Player) event.getWhoClicked();
		if (event.getClick().isKeyboardClick())
			clickedItem = player.getInventory().getItem(event.getHotbarButton());
		else
			clickedItem = event.getCurrentItem();

		if (!AxeManager.isAxe(clickedItem))
			return;

		AxeManager.removeEfficiencyEnchantment(clickedItem);
		Bukkit.getScheduler().runTaskLater(Pets.getPlugin(Pets.class), () -> {
			player.updateInventory();
		}, 1);
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		ItemStack droppedItem = event.getItemDrop().getItemStack();
		if (!AxeManager.isAxe(droppedItem))
			return;
		AxeManager.removeEfficiencyEnchantment(droppedItem);
	}

	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {
		List<ItemStack> drops = event.getDrops();
		for (ItemStack item : drops) {
			if (!AxeManager.isAxe(item))
				continue;
			AxeManager.removeEfficiencyEnchantment(item);
		}
	}

	@EventHandler
	public void onHopperPickUpItem(InventoryPickupItemEvent event) {
		ItemStack pickedItem = event.getItem().getItemStack();
		if (!AxeManager.isAxe(pickedItem))
			return;

		AxeManager.removeEfficiencyEnchantment(pickedItem);
	}
}
