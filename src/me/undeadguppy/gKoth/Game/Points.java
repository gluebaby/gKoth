package me.undeadguppy.gKoth.Game;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.undeadguppy.gKoth.Main;
import net.md_5.bungee.api.ChatColor;

public class Points extends BukkitRunnable {

	private Game game;

	public Points(Game game) {
		this.game = game;
	}

	public void winProcess(Player p) {
		Random money = new Random();
		int m = money.nextInt(2);
		if (m == 0) {
			Main.econ.depositPlayer(p, 1500);
		} else if (m == 1) {
			Main.econ.depositPlayer(p, 5000);
		}
		Random kit = new Random();
		int value = kit.nextInt(5);
		if (value == 0) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kit vip " + p.getName());
		} else if (value == 1) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kit mvp " + p.getName());
		} else if (value == 2) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kit pro " + p.getName());
		} else if (value == 3) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kit lord " + p.getName());
		} else if (value == 4) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "kit god " + p.getName());
		}
	}

	@Override
	public void run() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (game.isOnHill(p)) {
				if (game.getHashMap().containsKey(p.getUniqueId())) {
					if (game.getHashMap().get(p.getUniqueId()) == 1000) {
						Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has won the KoTH!");
						game.stop();
						this.cancel();
						winProcess(p);
						p.teleport(p.getWorld().getSpawnLocation());
						return;
					}
					game.getHashMap().put(p.getUniqueId(), game.getPoints(p) + 25);
					Bukkit.getServer().broadcastMessage(
							ChatColor.RED + p.getName() + " is capturing the hill! " + game.getPoints(p) + "/1000!");

				}
			}
		}
	}

}
