package me.ambmt.pets;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.ambmt.pets.commands.CommandCompleter;
import me.ambmt.pets.commands.CommandGivePet;
import me.ambmt.pets.commands.CommandHelp;
import me.ambmt.pets.commands.CommandReload;
import me.ambmt.pets.commands.CommandRunner;
import me.ambmt.pets.commands.PetCommand;
import me.ambmt.pets.events.AxeEnchantmentRemover;
import me.ambmt.pets.events.BlockBreak;
import me.ambmt.pets.events.ItemSwitch;
import me.ambmt.pets.events.MobDeath;
import me.ambmt.pets.events.PlayerAttack;
import me.ambmt.pets.events.PlayerFish;
import me.ambmt.pets.events.TimerTasks;
import net.milkbowl.vault.economy.Economy;

public class Pets extends JavaPlugin {
	private Economy economy;
	
	private Map<String, PetCommand> subcommands = new HashMap<String, PetCommand>();
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getCommand("pets").setExecutor(new CommandRunner(this));
		getCommand("pets").setTabCompleter(new CommandCompleter(this));
		subcommands.put("givepet", new CommandGivePet(this));
		subcommands.put("reload", new CommandReload(this));
		subcommands.put("help", new CommandHelp());
		
		PluginManager pm = getServer().getPluginManager();
		// Tiger pet
		pm.registerEvents(new PlayerAttack(), this);
		
		// Dolphin
		pm.registerEvents(new PlayerFish(), this);
		
		// Panda
		pm.registerEvents(new ItemSwitch(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new AxeEnchantmentRemover(), this);
		
		TimerTasks tasks = new TimerTasks(this);
		tasks.startDragonEffectTask();
		tasks.startLevelingTask();
		
		if (setupEconomy()) {
			System.out.println("[Pets] Vault is loaded, enabling goblin pet.");
			pm.registerEvents(new MobDeath(this), this);
		}
	}

	@Override
	public void onDisable() {

	}

	public Map<String, PetCommand> getSubcommands() {
		return subcommands;
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economy = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economy != null)
			this.economy = economy.getProvider();

		return (economy != null);
	}

	public Economy getEconomy() {
		return economy;
	}
}
