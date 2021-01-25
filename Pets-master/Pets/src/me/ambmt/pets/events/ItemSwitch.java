package me.ambmt.pets.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Panda;
import me.ambmt.pets.util.AxeManager;

public class ItemSwitch implements Listener {

	@EventHandler
	public void onItemSwitch(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
		ItemStack oldItem = player.getInventory().getItem(event.getPreviousSlot());
		if (newItem == null && oldItem == null)
			return;

		Panda pet = (Panda) PetManager.getActivePet(player, PetType.PANDA);
		if (pet == null)
			return;

		if (AxeManager.isAxe(newItem)) {
			int axeEfficiency = (int) pet.getAxeEfficiencyMultiplier();
			addEfficiencyEnchantment(newItem, axeEfficiency);
			player.updateInventory();

		}
		if (AxeManager.isAxe(oldItem)) {
			AxeManager.removeEfficiencyEnchantment(oldItem);
			player.updateInventory();
		}
	}

	private void addEfficiencyEnchantment(ItemStack item, int efficiencyAmount) {
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setInteger("temporal_efficiency", efficiencyAmount);
		nbtItem.applyNBT(item);

		ItemMeta meta = item.getItemMeta();
		int currentEfficiency = meta.getEnchantLevel(Enchantment.DIG_SPEED);
		item.setItemMeta(meta);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, currentEfficiency + efficiencyAmount);
	}
}
