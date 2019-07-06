package me.undeadguppy.gKoth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Main instance;
	public static Economy econ = null;

	public static Main getInstance() {
		return instance;
	}

	Koth koth;

	@Override
	public void onEnable() {
		instance = this;
		koth = new Koth();
		if (!setupEconomy()) {
			Bukkit.getServer().getLogger().severe(
					String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getConfig().addDefault("SpawnPoint1.World", "world");
		getConfig().addDefault("SpawnPoint1.X", 0.0);
		getConfig().addDefault("SpawnPoint1.Y", 0.0);
		getConfig().addDefault("SpawnPoint1.Z", 0.0);
		getConfig().addDefault("SpawnPoint2.World", "world");
		getConfig().addDefault("SpawnPoint2.X", 0.0);
		getConfig().addDefault("SpawnPoint2.Y", 0.0);
		getConfig().addDefault("SpawnPoint2.Z", 0.0);
		getConfig().addDefault("SpawnPoint3.World", "world");
		getConfig().addDefault("SpawnPoint3.X", 0.0);
		getConfig().addDefault("SpawnPoint3.Y", 0.0);
		getConfig().addDefault("SpawnPoint3.Z", 0.0);
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(koth, this);
		getCommand("koth").setExecutor(koth);
	}

	@Override
	public void onDisable() {

	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
