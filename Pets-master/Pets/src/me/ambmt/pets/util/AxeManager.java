package me.ambmt.pets.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;

public class AxeManager {

	public static boolean isAxe(ItemStack item) {
		if (item == null)
			return false;
		Material type = item.getType();
		if (type == Material.WOOD_AXE || type == Material.STONE_AXE || type == Material.IRON_AXE
				|| type == Material.GOLD_AXE || type == Material.DIAMOND_AXE)
			return true;

		return false;
	}
	
	public static void removeEfficiencyEnchantment(ItemStack item) {
		if (item == null)
			return;
		if (!item.containsEnchantment(Enchantment.DIG_SPEED))
			return;

		NBTItem nbtItem = new NBTItem(item);
		if (!nbtItem.hasNBTData())
			return;
		if (!nbtItem.hasKey("temporal_efficiency"))
			return;

		int temporalEfficiency = nbtItem.getInteger("temporal_efficiency");
		nbtItem.removeKey("temporal_efficiency");
		nbtItem.applyNBT(item);
		int currentEfficiency = item.getEnchantmentLevel(Enchantment.DIG_SPEED);
		ItemMeta meta = item.getItemMeta();

		meta.removeEnchant(Enchantment.DIG_SPEED);
		item.setItemMeta(meta);
		if (currentEfficiency - temporalEfficiency > 0)
			item.addUnsafeEnchantment(Enchantment.DIG_SPEED, currentEfficiency - temporalEfficiency);
		
	}
}
