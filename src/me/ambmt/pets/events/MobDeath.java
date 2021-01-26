package me.ambmt.pets.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.ambmt.pets.Pets;
import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Goblin;
import me.ambmt.pets.util.Messager;
import net.milkbowl.vault.economy.Economy;

public class MobDeath implements Listener {
	private Pets plugin;
	
	public MobDeath(Pets instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent event) {
		LivingEntity deadEntity = event.getEntity();
		if (deadEntity.getKiller() == null)
			return;
		if (!(deadEntity.getKiller() instanceof Player))
			return;
		
		Economy economy = plugin.getEconomy();
		Player killer = (Player) deadEntity.getKiller();
		Goblin pet = (Goblin) PetManager.getActivePet(killer, PetType.GOBLIN);
		if (pet == null)
			return;
		
		int killMoney = pet.getObtainedMoney();
		economy.depositPlayer(killer, killMoney);
		Messager.sendMessage(killer, "&aYou received &l" + killMoney + "$ &afrom your kill.");
	}
}
