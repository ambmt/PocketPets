package me.ambmt.pets.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;

import me.ambmt.pets.models.PetManager;
import me.ambmt.pets.models.PetType;
import me.ambmt.pets.types.Tiger;

public class PlayerAttack implements Listener {

	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player))
			return;
		if (!(event.getEntity() instanceof LivingEntity))
			return;
		
		Player attacker = (Player) event.getDamager();
		LivingEntity damaged = (LivingEntity) event.getEntity();
		Tiger tiger = (Tiger) PetManager.getActivePet(attacker, PetType.TIGER);
		if (tiger == null)
			return;
		
		double damage = event.getDamage();
		double multiplier = tiger.getRegularDamageMultiplier();
		if (attacker.getFallDistance() > 0)
			multiplier += tiger.getCriticalDamageMultiplier() - 1;
		if (hasArmor(damaged))
			multiplier += tiger.getArmorPiercingMultiplier() - 1;
		
		damage *= multiplier;
		event.setDamage(damage);
	}
	
	private boolean hasArmor(LivingEntity entity) {
		EntityEquipment equipment = entity.getEquipment();
		if (equipment == null)
			return false;
		
		return true;
	}
}
