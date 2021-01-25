package me.ambmt.pets.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Tree;

import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Panda;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block brokeBlock = event.getBlock();
		if (!(brokeBlock.getState().getData() instanceof Tree)) // Checking if it is wood log
			return;
		
		Player player = event.getPlayer();
		Panda panda = (Panda) PetManager.getActivePet(player, PetType.PANDA);
		if (panda == null)
			return;
		
		event.setDropItems(false);
		int logMultiplier = (int) panda.getLogMultiplier();
		Location blockLocation = brokeBlock.getLocation();
		ItemStack drops = brokeBlock.getDrops().iterator().next();
		drops.setAmount(logMultiplier);
		blockLocation.getWorld().dropItemNaturally(blockLocation, drops);
	}
}
